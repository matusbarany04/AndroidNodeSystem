package com.msvastudios.trick_builder.node.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node.NodeDimensionsCalculator;

public class NodeOutput extends NodeConnector implements View.OnTouchListener {
    Context context;

    ImageView imagePoint;
    NodeCallbackListener listener;

    public NodeOutput(Context context, NodeCallbackListener listener, Node parent, int order) {
        super(context, parent, order);
        this.listener = listener;
        this.context = context;

        this.parent = parent;
        init(null, 0);
    }

    @Override
    public void updatePositionVars(){
        point.setPosition(
                parent.getLeftMargin() + NodeDimensionsCalculator.getInnerNodeWidth()  + NodeDimensionsCalculator.innerNodeMargin() / 2,
                parent.getTopMargin() + NodeDimensionsCalculator.nodeItemHeight() * order + NodeDimensionsCalculator.nodeItemHeight() / 2  + NodeDimensionsCalculator.innerNodeMargin() / 2
        );
    }

    private void init(AttributeSet attrs, int defStyle) {
        disableClipOnParents(getView());
        getView().setOrientation(LinearLayout.HORIZONTAL);
        getView().setGravity(1);
//        getView().setBackgroundColor(Color.BLUE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width + 100, height);
        params.topMargin = height * order;
        params.rightMargin = -NodeDimensionsCalculator.innerNodeMargin();// /2 ;
        getView().setLayoutParams(params);

        View.inflate(context, R.layout.view_node_output, getView());
        imagePoint = getView().findViewById(R.id.output_dragpoint);
        imagePoint.setLayoutParams(new LinearLayout.LayoutParams(height / 2, height));
//        imagePoint.setTranslationX(20);
//        imagePoint.setLayoutParams(imageViewParams);
        getView().setOnTouchListener(this);

        getView().setClipChildren(false);


        disableClipOnParents(getView());
//        setBackgroundColor(Color.rgb(100, 100, 142));
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            listener.onOutputDragged(parent,this);
        }
        return false;
    }

}

