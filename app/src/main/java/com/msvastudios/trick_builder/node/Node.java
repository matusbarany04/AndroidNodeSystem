package com.msvastudios.trick_builder.node;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RelativeLayout;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.line.LinePoint;
import com.msvastudios.trick_builder.line.LinesView;
import com.msvastudios.trick_builder.node.item.NodeConnectorItem;
import com.msvastudios.trick_builder.node.item.NodeInput;
import com.msvastudios.trick_builder.node.item.NodeItem;
import com.msvastudios.trick_builder.node.item.NodeNav;
import com.msvastudios.trick_builder.node.item.NodeOutput;
import com.msvastudios.trick_builder.node.item.Type;

import java.util.ArrayList;
import java.util.Objects;

public class Node implements View.OnTouchListener {

    Context context;
    LinesView linesView;
    NodeNav nav;
    ArrayList<NodeInput> nodeInput;
    ArrayList<NodeOutput> nodeOutput;
    NodeCallbackListener listener;
    String id;

    private RelativeLayout node;
    private int xDelta, yDelta;
    private int leftMargin, topMargin;
    private int nodeWidth, nodeHeight = 700;
    private int nodeItemOrder = 1;
    RelativeLayout innerNode;

    public Node(Context context, int leftMargin, int topMargin, LinesView linesView, NodeCallbackListener listener){
        this.leftMargin = leftMargin;
        this.topMargin = topMargin;
        nodeWidth = NodeDimensionsCalculator.nodeWidth();

        this.listener = Objects.requireNonNull(listener);
        this.linesView = Objects.requireNonNull(linesView);
        this.context = Objects.requireNonNull(context);

        nodeOutput = new ArrayList<>();
        nodeInput = new ArrayList<>();

        id = NodeItem.generateId();

        init(context);
    }

    public void addNodeOutput(Type type){
        nodeOutput.add(new NodeOutput(context, listener, this, 2, type));
        nodeItemOrder++;
    }


    public void addNodeParam(Type type){
        //nodeOutput.add(new NodeOutput(context, listener, this, 2, type));
        nodeItemOrder++;
    }

    public void addNodeInput(Type type){
        nodeInput.add(new NodeInput(context, listener, this, 2, type));
        nodeItemOrder++;
    }

    public void setOnChangedStateListener(NodeCallbackListener listener) {
        this.listener = listener;
    }


    public NodeInput hoveringOn(int innerX, int innerY) {
        for (NodeInput input : nodeInput) {
//            System.out.println(innerY + " " + (input.getOrder() + 1) * NodeDimensionsCalculator.nodeItemHeight());
            if (innerY > input.getOrder() * NodeDimensionsCalculator.nodeItemHeight()) {
//                System.out.println("yes");
                if (innerY < (input.getOrder() + 1) * NodeDimensionsCalculator.nodeItemHeight()) {
//                    System.out.println("absolute yes!!");
                    return input;
                }
            }
        }
        return null;
    }

    void init(Context context) {
        node = new RelativeLayout(context);
        node.setClipChildren(false);
        node.setLayoutParams(new RelativeLayout.LayoutParams(nodeWidth, nodeHeight));
        node.setBackgroundResource(R.drawable.back_node);
//        node.setElevation(20f);
        node.setOutlineProvider(ViewOutlineProvider.PADDED_BOUNDS);

        innerNode = new RelativeLayout(context);
        RelativeLayout.LayoutParams innerViewParams = new RelativeLayout.LayoutParams(nodeWidth - 50, nodeHeight - 50);
        int innerMargin = NodeDimensionsCalculator.innerNodeMargin() / 2;
        innerViewParams.setMargins(innerMargin, innerMargin, 0, 0);
        innerNode.setLayoutParams(innerViewParams);
        innerNode.setClipChildren(false);
        innerNode.setBackgroundResource(R.drawable.node);
        node.addView(innerNode);

        nav = new NodeNav(context);
        nav.setOnTouchListener(this);

        innerNode.addView(nav);

        setPosition(leftMargin, topMargin);

        //updatePositionVars();
    }
    public void build(){

        for (NodeInput node : nodeInput) {
            innerNode.addView(node.getView());
        }

        for (NodeOutput node : nodeOutput) {
            innerNode.addView(node.getView());
        }

        nodeHeight = NodeDimensionsCalculator.nodeItemHeight() * nodeItemOrder;

        node.setLayoutParams(new RelativeLayout.LayoutParams(nodeWidth, nodeHeight));

        RelativeLayout.LayoutParams innerViewParams = new RelativeLayout.LayoutParams(nodeWidth - 50, nodeHeight - 50);
        innerNode.setLayoutParams(innerViewParams);
    }

    private void updatePositionVars() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) node.getLayoutParams();
        leftMargin = params.leftMargin;
        topMargin = params.topMargin;
        for (NodeOutput node : nodeOutput) {
            node.updatePositionVars();
        }
        for (NodeInput node : nodeInput) {
            node.updatePositionVars();
        }

    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public int getTopMargin() {
        return topMargin;
    }

    private Pair<Integer, Integer> getPosition() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) node.getLayoutParams();
        return new Pair<>(params.leftMargin, params.topMargin);
    }

    private Pair<Integer, Integer> getSize() {
        return new Pair<>(nodeWidth, nodeHeight);
    }

    public void setPosition(int leftMargin, int topMargin) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) node.getLayoutParams();
        params.leftMargin = leftMargin;
        params.topMargin = topMargin;
    }

    public RelativeLayout getNode() {
        return node;
    }

    private void callCallback() {
        if (listener != null)
            listener.onMoved(this);
    }

    public int getNodeWidth() {
        return nodeWidth;
    }

    public int getNodeHeight() {
        return nodeHeight;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();

        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) node.getLayoutParams();

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                xDelta = rawX - params.leftMargin;
                yDelta = rawY - params.topMargin;
                break;

            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) node.getLayoutParams();
                layoutParams.leftMargin = rawX - xDelta;
                layoutParams.topMargin = rawY - yDelta;

                node.setLayoutParams(layoutParams);

                break;
        }

        updatePositionVars();

        callCallback();

        node.bringToFront();

        linesView.invalidate();

        //linePoint.setPosition(leftMargin, topMargin);

        return true;
    }

    public String getId() {
        return id;
    }
}

