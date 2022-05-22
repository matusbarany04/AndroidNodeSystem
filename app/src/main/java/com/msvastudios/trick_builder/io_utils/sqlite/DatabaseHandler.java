package com.msvastudios.trick_builder.io_utils.sqlite;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.msvastudios.trick_builder.io_utils.sqlite.algorithms.AlgorithmEntity;
import com.msvastudios.trick_builder.io_utils.sqlite.algorithms.AlgorithmDatabase;
import com.msvastudios.trick_builder.io_utils.sqlite.lines.LineEntity;
import com.msvastudios.trick_builder.io_utils.sqlite.lines.LineDatabase;
import com.msvastudios.trick_builder.io_utils.sqlite.nodes.NodeEntity;
import com.msvastudios.trick_builder.io_utils.sqlite.nodes.NodeDatabase;
import com.msvastudios.trick_builder.node_editor.line.Line;
import com.msvastudios.trick_builder.node_editor.node.Node;

import java.util.ArrayList;


public class DatabaseHandler {

    private AlgorithmDatabase algorithmDatabase;
    private NodeDatabase nodeDatabase;
    private LineDatabase lineDatabase;

    private DatabaseHandler() {}

    public static DatabaseHandler build(Context context){
        instance = new DatabaseHandler();
        instance.algorithmDatabase = Room.databaseBuilder(context, AlgorithmDatabase.class, AlgorithmDatabase.DATABASE_NAME).build();
        instance.nodeDatabase = Room.databaseBuilder(context, NodeDatabase.class, NodeDatabase.DATABASE_NAME).build();
        instance.lineDatabase = Room.databaseBuilder(context, LineDatabase.class, LineDatabase.DATABASE_NAME).build();

        return instance;
    }



    public void saveAlgorithm(AlgorithmEntity algorithm) {
        new Thread(() -> algorithmDatabase.algorithmDao().insertAll(algorithm)).start();
    }

    //TODO xxx make a method that will join insert and delete -> update
    //TODO make all thread async callbacks
    public void removeAlgorithm(AlgorithmEntity algorithm){
        new Thread(() -> {
            algorithmDatabase.algorithmDao().delete(algorithm);
            lineDatabase.lineDao().deleteByAlgorithmId(algorithm.nodeNetworkUUID);
            nodeDatabase.nodeDao().deleteByAlgorithmId(algorithm.nodeNetworkUUID);
        }
        ).start();
    }


    /**
     * Method does not remove old occurrences of deleted lines, points, nodes
     * @param algorithm
     * @param lines
     * @param nodes
     */
    public void insertAlgorithm(AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes) {
        new Thread(() -> {
            algorithmDatabase.algorithmDao().insertAll(algorithm);
            for (Line line: lines){
                LineEntity lineEntity = new LineEntity(line.getId(), algorithm.nodeNetworkUUID, line.getStartPoint().getId(), line.getEndPoint().getId());
                lineDatabase.lineDao().insertAll(lineEntity);
            }
            for (Node node: nodes){
                NodeEntity nodeEntity = new NodeEntity(algorithm.nodeNetworkUUID, node.getLeftMargin(), node.getTopMargin(), node.getId());
                nodeDatabase.nodeDao().insertAll(nodeEntity);
            }
        }
        ).start();
    }

    public void printAlgorithms(){
        new Thread(()-> {
            for (AlgorithmEntity algo : algorithmDatabase.algorithmDao().getAll()) {
                Log.d("algo", algo.name);
            }
        }).start();
    }

    public static DatabaseHandler instance;
    public static DatabaseHandler getInstance() {
        return instance;
    }
}
