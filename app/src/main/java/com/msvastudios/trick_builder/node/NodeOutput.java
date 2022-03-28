package com.msvastudios.trick_builder.node;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.line.LinePoint;

public class NodeOutput extends NodeItem implements View.OnTouchListener {
    Context context;
    int width, height;
    ImageView imagePoint;
    NodeCallbackListener listener;
    Node parent;
    LinePoint point;
    String id;
    int order;
    RelativeLayout view;

    public NodeOutput(Context context, NodeCallbackListener listener, Node parent, int order) {
//        super(context);
        this.listener = listener;
        this.context = context;
        this.width = NodeDimensionsCalculator.getInnerNodeWidth() + NodeDimensionsCalculator.innerNodeMargin();
        this.height = NodeDimensionsCalculator.nodeItemHeight();
        this.parent = parent;
        this.order = order;
        id = NodeItem.generateId();


        init(null, 0);
    }
//
//    @Override
//    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
//        super.onLayout(b, i, i1, i2, i3);
//    }


    @SuppressLint("ClickableViewAccessibility")
    private void init(AttributeSet attrs, int defStyle) {
        view = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.topMargin = height * order;
        params.leftMargin = -NodeDimensionsCalculator.innerNodeMargin() / 2;
        view.setLayoutParams(params);

        View.inflate(context, R.layout.view_node_output, view);
        imagePoint = view.findViewById(R.id.output_dragpoint);

        RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(height / 2, height / 2);
        imageViewParams.topMargin = height / 4;
        imageViewParams.leftMargin = width - imageViewParams.width;


        imagePoint.setLayoutParams(imageViewParams);
        view.setOnTouchListener(this);

        view.setClipChildren(false);
        disableClipOnParents(view);
        setNewLinePoint();
//        setBackgroundColor(Color.rgb(100, 100, 142));

    }


    public RelativeLayout getView() {
        return view;
    }

    public void updatePositionVars() {
        point.setPosition(
                parent.getLeftMargin() + NodeDimensionsCalculator.getInnerNodeWidth()  + NodeDimensionsCalculator.innerNodeMargin() / 2,
                parent.getTopMargin() + NodeDimensionsCalculator.nodeItemHeight() * order + NodeDimensionsCalculator.nodeItemHeight() / 2  + NodeDimensionsCalculator.innerNodeMargin() / 2
        );
    }

    private void setNewLinePoint() {
        point = new LinePoint(0,0, parent);
        updatePositionVars();
//        point = new LinePoint(
//                parent.getLeftMargin() + width,//+ NodeDimensionsCalculator.innerNodeMargin() / 2,
//                parent.getTopMargin() + NodeDimensionsCalculator.nodeItemHeight() * order + NodeDimensionsCalculator.nodeItemHeight() / 2  + NodeDimensionsCalculator.innerNodeMargin() / 2,
//                parent
//        );
    }

    //
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(width, height);
//        super.onMeasure(
//                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
//                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
//        );
//    }


    public void disableClipOnParents(View v) {
        if (v == null) {
            return;
        }
        if (v instanceof ViewGroup) {
            ((ViewGroup) v).setClipChildren(false);
            ((ViewGroup) v).setClipToOutline(false);
            ((ViewGroup) v).setClipToPadding(false);

        }
        disableClipOnParents((View) v.getParent());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            listener.onOutputDragged(parent,this);
        }
        return false;
    }


    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public boolean hasOutput() {
        return true;
    }

    @Override
    public String getID() {
        return id;
    }
}

