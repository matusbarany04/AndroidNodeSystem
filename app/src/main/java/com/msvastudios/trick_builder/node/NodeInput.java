package com.msvastudios.trick_builder.node;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.msvastudios.trick_builder.R;

public class NodeInput extends LinearLayout implements NodeItem {
    private final String id = NodeItem.generateId();
    Context context;
    int width, height;
    ImageView imagePoint;
    NodeCallbackListener listener;
    Node parent;
    int order;

    public NodeInput(Context context, NodeCallbackListener listener, Node parent, int order) {
        super(context);
        this.listener = listener;
        this.context = context;
        this.width = NodeDimensionsCalculator.getInnerNodeWidth();
        this.height = NodeDimensionsCalculator.nodeItemHeight();
        this.parent = parent;
        this.order = order;
        init(null, 0);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        super.onLayout(b, i, i1, i2, i3);
    }

    private void init(AttributeSet attrs, int defStyle) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(1);
//        setBackgroundColor(Color.BLUE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
        params.topMargin = height * order;
        params.leftMargin = -NodeDimensionsCalculator.innerNodeMargin() / 2;
        setLayoutParams(params);

        View.inflate(context, R.layout.view_node_input, this);
        imagePoint = findViewById(R.id.input_dragpoint);
        imagePoint.setLayoutParams(new LinearLayout.LayoutParams(height / 2, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
        super.onMeasure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        );
    }


    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public boolean hasOutput() {
        return false;
    }

    @Override
    public String getID() {
        return id;
    }



}
