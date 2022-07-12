package com.msvastudios.trick_builder.node_editor.run;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.msvastudios.trick_builder.node_editor.line.Line;
import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.NodeManager;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.algorithms.AlgorithmEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class NodeRunner {
    Context context;
    String algorithmId;
    HashMap<String, Node> nodeList;
    LinesView dummyLinesView;
    AlgorithmEntity algorithmEntity;
    public NodeRunner(Context context, String algorithmId){
        this.algorithmId = algorithmId;
        this.context = context;
        nodeList = new HashMap<>();
        init();
    }

    private void init(){
        dummyLinesView = new LinesView(context);
        loadSavedNodeNetwork(algorithmId);

    }
    public void loadSavedNodeNetwork(String id) { // TODO make a lot faster
        ArrayList<Line> lines;
        ArrayList<Node> nodes;
        // ATTENTION node callback listener is null possible problems
        DatabaseHandler.getInstance(context).getAlgorithm(id, context, dummyLinesView, null, new DatabaseHandler.Data() {
            @Override
            public void onAlgorithmBuilt(AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes) {
                for (Node node : nodes) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            nodeList.put(node.getId(), node);
                        }
                    });

                }
                dummyLinesView.setLines(lines);
                algorithmEntity = algorithm;
            }
        });
    }


    public void play(RunnerCallback runnerCallback) {
        for (Node node : nodeList.values()) {
            if (node.isStartingNode()) {
                node.process();
                node.sendData(runnerCallback);
            }
        }
    }


}
