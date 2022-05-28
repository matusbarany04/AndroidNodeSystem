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
import com.msvastudios.trick_builder.node_editor.line.LinePoint;
import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DatabaseHandler {

    public static DatabaseHandler instance;
    ExecutorService executor;
    private AlgorithmDatabase algorithmDatabase;
    private NodeDatabase nodeDatabase;
    private LineDatabase lineDatabase;

    private DatabaseHandler() {
    }

    public static DatabaseHandler build(Context context) {
        instance = new DatabaseHandler();
        instance.algorithmDatabase = Room.databaseBuilder(context, AlgorithmDatabase.class, AlgorithmDatabase.DATABASE_NAME).build();
        instance.nodeDatabase = Room.databaseBuilder(context, NodeDatabase.class, NodeDatabase.DATABASE_NAME).build();
        instance.lineDatabase = Room.databaseBuilder(context, LineDatabase.class, LineDatabase.DATABASE_NAME).build();
        instance.executor = Executors.newSingleThreadExecutor();
        return instance;
    }

    public static DatabaseHandler getInstance() {
        return instance;
    }

    //TODO xxx make a method that will join insert and delete -> update
    //TODO make all thread async callbacks
    public void updateAlgorithm(AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes) {
        this.removeAlgorithm(algorithm);
        this.insertAlgorithm(algorithm, lines, nodes);
    }

    /**
     * @param algorithm
     * @deprecated
     */
    public void saveAlgorithm(AlgorithmEntity algorithm) {
        executor.execute(() -> algorithmDatabase.algorithmDao().insertAll(algorithm));
    }

    public void removeAlgorithm(AlgorithmEntity algorithm) {
        executor.execute(() -> {
            algorithmDatabase.algorithmDao().delete(algorithm);
            lineDatabase.lineDao().deleteByAlgorithmId(algorithm.nodeNetworkUUID);
            nodeDatabase.nodeDao().deleteByAlgorithmId(algorithm.nodeNetworkUUID);
        });
    }

    /**
     * Method does not remove old occurrences of deleted lines, points, nodes
     *
     * @param algorithm
     * @param lines
     * @param nodes
     */
    public void insertAlgorithm(AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes) {
        executor.execute(() -> {
                    algorithmDatabase.algorithmDao().insertAll(algorithm);
                    for (Line line : lines) {
                        lineDatabase.lineDao().insertAll(AlgorithmLoader.lineToLineEntity(line, algorithm.nodeNetworkUUID));
                    }

                    for (Node node : nodes) {
                        NodeEntity nodeEntity = new NodeEntity(
                                algorithm.nodeNetworkUUID,
                                node.getLeftMargin(),
                                node.getTopMargin(),
                                node.getId(),
                                node.getType());
                        nodeDatabase.nodeDao().insertAll(nodeEntity);
                    }
                }
        );
    }


    public void getAlgorithm(String algoID, Context context, LinesView linesView, NodeCallbackListener callbackListener) {
        //return AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes
        executor.execute(() -> {


                    AlgorithmEntity algo = algorithmDatabase.algorithmDao().getByAlgorithmId(algoID);

                    ArrayList<LineEntity> lineEntities = lineDatabase.lineDao().findByAlgorithmUUID(algoID);

                    ArrayList<NodeEntity> nodeEntities = nodeDatabase.nodeDao().getByAlgorithmId(algoID);


                    ArrayList<Line> lines = new ArrayList<>();

                    HashMap<String, Node> nodeHashMap = new HashMap<>();
                    for (NodeEntity entitity : nodeEntities) {
                        Node node = entitity.type.createNode(context, entitity.cordinateX, entitity.cordinateY, linesView, callbackListener);
                        nodeHashMap.put(entitity.algorithmUUID, node);
                    }

                    for (LineEntity entity : lineEntities) {

                        LinePoint startPoint = nodeHashMap.get(entity.endPointNodeId).getNodeOutput().get(0).getPoint();

                        Line line = new Line()
                    }

//                    for (Line line : lines) {
//                        LineEntity lineEntity = new LineEntity(line.getId(), algorithm.nodeNetworkUUID, line.getStartPoint().getId(), line.getEndPoint().getId());
//                        lineDatabase.lineDao().insertAll(lineEntity);
//                    }
//
//                    for (Node node : nodes) {
//                        NodeEntity nodeEntity = new NodeEntity(
//                                algorithm.nodeNetworkUUID,
//                                node.getLeftMargin(),
//                                node.getTopMargin(),
//                                node.getId(),
//                                node.getType());
//                        nodeDatabase.nodeDao().insertAll(nodeEntity);
//                    }
                }
        );
    }


    public void printAlgoData() {
        new Thread(() -> {
            for (AlgorithmEntity algo : algorithmDatabase.algorithmDao().getAll()) {
                Log.d("algo", algo.name);
            }
            System.out.println("-------------------------------");
            for (NodeEntity node : nodeDatabase.nodeDao().getAll()) {
                Log.d("algo", node.toString());
            }
            System.out.println("-------------------------------");
            for (LineEntity line : lineDatabase.lineDao().getAll()) {
                Log.d("algo", line.toString());
            }
        }).start();
    }
}
