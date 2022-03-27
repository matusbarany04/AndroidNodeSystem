package com.msvastudios.trick_builder.node;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.line.LinePoint;
import com.msvastudios.trick_builder.line.LinesView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Node implements View.OnTouchListener {

    Context context;
    LinesView linesView;
    NodeNav nav;
    ArrayList<NodeInput> nodeInput;
    ArrayList<NodeOutput> nodeOutput;
    NodeCallbackListener listener;

    private RelativeLayout node;
    private int xDelta, yDelta;
    private int leftMargin, topMargin;

    private int nodeWidth, nodeHeight = 700;

    String id;


    public Node(Context context, int leftMargin, int topMargin, LinesView linesView, NodeCallbackListener listener) {
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


    public void setOnChangedStateListener(NodeCallbackListener listener) {
        this.listener = listener;
    }


    public void attachLinePoint(LinePoint point){

        //calculate position of nearest item to finger and attach point
        //linePoint = point;
    }




    void init(Context context) {
        node = new RelativeLayout(context);
        node.setClipChildren(false);
        node.setLayoutParams(new RelativeLayout.LayoutParams(nodeWidth, nodeHeight));
        node.setBackgroundResource(R.drawable.back_node);
//        node.setElevation(20f);
        node.setOutlineProvider(ViewOutlineProvider.PADDED_BOUNDS);


        RelativeLayout innerNode = new RelativeLayout(context);
        RelativeLayout.LayoutParams innerViewParams = new RelativeLayout.LayoutParams(nodeWidth-50, nodeHeight-50);
        int innerMargin = NodeDimensionsCalculator.innerNodeMargin() / 2;
        innerViewParams.setMargins(innerMargin,innerMargin,0,0);
        innerNode.setLayoutParams(innerViewParams);
        innerNode.setClipChildren(true);
        innerNode.setBackgroundResource(R.drawable.node);
        node.addView(innerNode);

        nav = new NodeNav(context);
        nav.setOnTouchListener(this);


        nodeInput.add(new NodeInput(context, listener, this,1));
        nodeOutput.add(new NodeOutput(context, listener, this,2));


        innerNode.addView(nav);

        for (NodeInput node: nodeInput) {
            innerNode.addView(node);
        }

        for (NodeOutput node: nodeOutput) {
            innerNode.addView(node);
        }

        setPosition(leftMargin, topMargin);

        //updatePositionVars();
    }

    private void updatePositionVars() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) node.getLayoutParams();
        leftMargin = params.leftMargin;
        topMargin = params.topMargin;
        for (NodeOutput node: nodeOutput) {
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

    private void callCallback(){
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
        return  id;
    }
}

