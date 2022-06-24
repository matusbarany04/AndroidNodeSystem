package com.msvastudios.trick_builder.node_editor.node;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.msvastudios.trick_builder.generator_editor.GeneratorEditorActivity;
import com.msvastudios.trick_builder.io_utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.io_utils.sqlite.algorithms.AlgorithmEntity;
import com.msvastudios.trick_builder.node_editor.io.NodesSaver;
import com.msvastudios.trick_builder.node_editor.line.Line;
import com.msvastudios.trick_builder.node_editor.line.LinePoint;
import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class NodeManager implements NodeCallbackListener, View.OnTouchListener {

    Line helpLine;
    LinesView linesView;
    HashMap<String, Node> nodeList;
    Context context;
    RelativeLayout dragArea;
    int nodeWidth = 500;
    NodeOutput draggingOutput;
    NodesSaver nodesSaver;

    public NodeManager(Context context, LinesView linesView, RelativeLayout dragArea) {
        NodeDimensionsCalculator.init(context);
        this.context = context;
        nodeList = new HashMap<>();
        this.linesView = Objects.requireNonNull(linesView);
        this.dragArea = dragArea;
        nodesSaver = new NodesSaver(context);

        // TODO presunut to potom do main activity a pridávať dynamicky
//        addNode(EndNode.class,10,0);
//
//        addNode(DummyNode.class,0,500);
//
//        addNode(RepeaterNode.class,100,300);
//
//        addNode(RepeaterNode.class,200,200);
//
//        addNode(TrickArrayNode.class,200,600);

        this.linesView.setOnTouchListener(this);
        // dragArea.addView(node2.getNode());

        this.linesView = linesView;

    }
    AlgorithmEntity algorithmEntity;
    public void loadSavedNodeNetwork(String id) { // TODO make a lot faster
//        Pair<ArrayList<Node>, HashMap<String, ArrayList<String>>> out = Node.readNodes(id);
//        ArrayList<Node> nodes = out.first;
//        HashMap<String, ArrayList<String>> lines = out.second;

        DatabaseHandler.getInstance(context).getAlgorithm(id, context, linesView, this, new DatabaseHandler.Data() {
            @Override
            public void onAlgorithmBuilt(AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes) {
                for (Node node: nodes) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            dragArea.addView(node.getNode());
                            nodeList.put(node.getId(), node);

                            // code goes here
                        }
                    });

                }

                linesView.setLines(lines);
                algorithmEntity = algorithm;
            }
        });

    }

    public void saveCurrentNodes(String id) {

        DatabaseHandler.getInstance(context).insertAlgorithm(
                new AlgorithmEntity(algorithmEntity.name, id, nodeList.values().size()),
                linesView.getLines(),
                new ArrayList<>(nodeList.values()));
    }



    public <T extends Node> void addNode(Class<T> sup, int leftMargin, int topMargin) {
        try {
            String myClassName = sup.getName();

            Class<?> myClass = Class.forName(myClassName);

            Constructor<T> ctr = (Constructor<T>) myClass.getConstructors()[0];

            T object = ctr.newInstance(context, leftMargin, topMargin, linesView, this);

            nodeList.put((object).getId(), object);
            dragArea.addView((object).getNode());

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onMoved(Node node) {
        return 0;
    }

    @Override
    public int onInputDropped(Node node) {
        return 0;
    }

    @Override
    public int onOutputDragged(Node node, NodeOutput nodeOutput) {
        draggingOutput = nodeOutput;
//        NodeOutput nodeOutput = node.nodeOutput.get(node.getId());
//        Log.d("output dragged", "dragging!!!");
        helpLine = new Line(nodeOutput.getPoint(), new LinePoint(nodeOutput.getPoint(), node));
        linesView.addLine(helpLine);
        return 0;
    }

    private void checkIfInNode(int x, int y) { // TODO
        for (Node node : nodeList.values()) {
            if (x > node.getLeftMargin() && x < node.getLeftMargin() + node.getNodeWidth()) {
                if (y > node.getTopMargin() && y < node.getTopMargin() + node.getNodeHeight()) {
                    NodeInput input = node.hoveringOn(x - node.getLeftMargin(), y - node.getTopMargin());
                    if (input != null && draggingOutput != null && input.getType().equals(draggingOutput.getType())) {
                        linesView.addLine(new Line(draggingOutput.getPoint(), input.getPoint())); // TODO check for multiple lines between same points
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getRawX();
        int y = (int) motionEvent.getRawY() - NodeDimensionsCalculator.getStatusBarHeight(context);

        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            if (helpLine != null) {
                helpLine.updateEndPoint(new LinePoint(x, y, null)); // null pointer exception checkifinnode funct
            }
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (helpLine != null)
                linesView.removeLine(helpLine.getId());
            checkIfInNode(x, y);
        }

        linesView.invalidate();
        return true;
    }

    public void play() {
        for (Node node : nodeList.values()) {
            if (node.isStartingNode()) {
//                System.out.println("NodeEntity found ! " + node.getId());
                node.process();
                node.sendData();
            }
        }
    }
}
