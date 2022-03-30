package com.msvastudios.trick_builder.node;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.msvastudios.trick_builder.line.Line;
import com.msvastudios.trick_builder.line.LinePoint;
import com.msvastudios.trick_builder.line.LinesView;
import com.msvastudios.trick_builder.node.item.NodeInput;
import com.msvastudios.trick_builder.node.item.NodeOutput;

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

    public NodeManager(Context context, LinesView linesView, RelativeLayout dragArea) {
        NodeDimensionsCalculator.init(context);
        this.context = context;
        nodeList = new HashMap<>();
        this.linesView = Objects.requireNonNull(linesView);
        this.dragArea = dragArea;

        addNode(10, 10);
        addNode(100, 100);
        addNode(200, 100);
        this.linesView.setOnTouchListener(this);
        // dragArea.addView(node2.getNode());

        this.linesView = linesView;
    }

    public void addNode(int leftMargin, int rightMargin) {
        Node node = new Node(context, leftMargin, rightMargin, linesView, this);
        nodeList.put(node.getId(), node);
        dragArea.addView(node.getNode());

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
        Log.d("output dragged", "dragging!!!");
        helpLine = new Line(nodeOutput.getPoint(), new LinePoint(nodeOutput.getPoint(), node));
        linesView.addLine(helpLine);
        return 0;
    }

    private void checkIfInNode(int x, int y) { // TODO
        for (Node node : nodeList.values()) {
            if (x > node.getLeftMargin() && x < node.getLeftMargin() + node.getNodeWidth()) {
                if (y > node.getTopMargin() && y < node.getTopMargin() + node.getNodeHeight()) {
                    NodeInput input = node.hoveringOn(x - node.getLeftMargin(), y - node.getTopMargin());
                    if (input != null && input.getType().equals(draggingOutput.getType())) {
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
                helpLine.updateEndPoint(new LinePoint(x, y, null));
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
}
