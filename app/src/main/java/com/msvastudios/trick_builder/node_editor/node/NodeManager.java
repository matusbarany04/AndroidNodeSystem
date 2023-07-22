package com.msvastudios.trick_builder.node_editor.node;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.msvastudios.trick_builder.node_editor.node.item.Type;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.algorithms.AlgorithmEntity;

import com.msvastudios.trick_builder.node_editor.node.item.line.Line;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinePoint;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
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

    NodeOutput draggingOutput;
    RelativeLayout relativePoint;
    boolean deleteEnabled = false;

    @SuppressLint("ClickableViewAccessibility")
    public NodeManager(Context context, LinesView linesView, RelativeLayout dragArea, RelativeLayout relativePoint) {
        NodeDimensionsCalculator.init(context);
        this.context = context;
        nodeList = new HashMap<>();
        this.linesView = Objects.requireNonNull(linesView);
        this.dragArea = dragArea;
        this.linesView.setOnTouchListener(this);
        this.relativePoint = relativePoint;

        this.linesView = linesView;

    }

    AlgorithmEntity algorithmEntity;

    public void loadSavedNodeNetwork(String id) { // TODO make a lot faster
        DatabaseHandler.getInstance(context).getAlgorithm(id, context, linesView, this, new DatabaseHandler.Data() {
            @Override
            public void onAlgorithmBuilt(AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes) {
                for (Node node : nodes) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            dragArea.addView(node.getNode());
                            nodeList.put(node.getId(), node);
                            if (!isScalerEmpty()){
                                dragArea.setScaleX(scalers.first);
                                dragArea.setScaleY(scalers.second);
                                linesView.setScaleX(scalers.first);
                                linesView.setScaleY(scalers.second);
                            }
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
                new AlgorithmEntity(algorithmEntity.name, id, nodeList.values().size(), algorithmEntity.imageId),
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


    public <T extends Node> void addNode(CustomNodes node, int leftMargin, int topMargin) {
        T createdNode = (T) node.createNode(context, leftMargin, topMargin, "", linesView, this);
        nodeList.put(createdNode.getId(), createdNode);
        dragArea.addView((createdNode).getNode());
    }

    @Override
    public int onMoved(Node node) { //TODO not quite working
        if (isDeleteEnabled()) {

            nodeList.remove(node.getId());
            //TODO xxx remove view
            dragArea.removeView(node.getNode());
            ArrayList<String> nodeOutputPoints = new ArrayList<>();
            for (NodeOutput a : node.getNodeOutput()) {
                String id = a.getPoint().getId();
                nodeOutputPoints.add(id);
            }

            ArrayList<String> nodeInputPoints = new ArrayList<>();
            for (NodeInput a : node.getNodeInput()) {
                String id = a.getPoint().getId();
                nodeInputPoints.add(id);
            }

            linesView.removeAllLinesWith(nodeOutputPoints);
            linesView.removeAllLinesWith(nodeInputPoints);

        }
        return 0;
    }

    @Override
    public int onInputDropped(Node node) {
        return 0;
    }

    @Override
    public int onOutputDragged(Node node, NodeOutput nodeOutput) {
        draggingOutput = nodeOutput;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        helpLine = new Line(nodeOutput.getPoint(), new LinePoint(nodeOutput.getPoint(), node));


        linesView.addLine(helpLine);
        return 0;
    }

    private Line checkIfInNode(int x, int y) {

        for (Node node : nodeList.values()) {
            if (x > node.getLeftMargin() && x < node.getLeftMargin() + node.getNodeWidth()) {
                if (y > node.getTopMargin() && y < node.getTopMargin() + node.getNodeHeight()) {
                    NodeInput input = node.hoveringOn(x - node.getLeftMargin(), y - node.getTopMargin());
                    if (input != null && draggingOutput != null) {
                        if (input.getType().equals(draggingOutput.getType()) || input.getType().equals(Type.ANY)) {
                            return  new Line(draggingOutput.getPoint(), input.getPoint());
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getRawX();
        int y = (int) motionEvent.getRawY() - NodeDimensionsCalculator.getStatusBarHeight(context);

        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            if (helpLine != null) {

                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;
                FrameLayout.LayoutParams dragAreaLayoutParams = (FrameLayout.LayoutParams) dragArea.getLayoutParams();
                int dragAreaX = dragAreaLayoutParams.width;
                int dragAreaY = dragAreaLayoutParams.height;

                helpLine.updateEndPoint(new LinePoint(
                        (int) (x / dragArea.getScaleX() + (dragAreaX  - (width / dragArea.getScaleX()) ) / 2),
                        (int) ((y / dragArea.getScaleY() + (dragAreaY  - (height / dragArea.getScaleY()) ) / 2)
                           + (NodeDimensionsCalculator.getStatusBarHeight(view.getContext())/2.f)/dragArea.getScaleY()
                         )
                        ,null)
                ); // null pointer exception checkifinnode function
            }
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

            if (helpLine != null) {
                Line line = checkIfInNode(helpLine.getEndPoint().getX(), helpLine.getEndPoint().getY());
                linesView.removeLine(helpLine.getId());//TODO check if its really removed, too many lines in sqlite
                if(line != null){
                    //remove All other occurrences
                    linesView.removeAllLinesWith(line.getEndPoint().getId());
                    linesView.addLine(line);

                }
            }

        }

        linesView.invalidate();
        return true;
    }

    Pair<Float, Float> scalers;

    public void scale(float x) {
        for (Node node : nodeList.values()) {
            node.setScale(x);
        }
        scalers = new Pair<>(x, x);
    }

    private boolean isScalerEmpty() {
        return scalers == null;
    }

    public void play(RunnerCallback runnerCallback) {
        for (Node node : nodeList.values()) {
            if (node.isStartingNode()) {
                node.process();
                node.sendData(runnerCallback);
            }
        }
    }

    public void toggleDeletionMode() {
        deleteEnabled = !deleteEnabled;
    }

    public void setDeleteEnabled(boolean deleteEnabled) {
        this.deleteEnabled = deleteEnabled;
    }

    public boolean isDeleteEnabled() {
        return deleteEnabled;
    }

    public void moveAll(int deltaX, int deltaY) {
        for (Node node : nodeList.values()) {
            node.updateNodeCordinatesRelatively(deltaX, deltaY);
        }
    }
}
