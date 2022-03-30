package com.msvastudios.trick_builder.node.item;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.media.Image;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node.NodeDimensionsCalculator;

public class NodeOutput extends NodeConnectorItem implements View.OnTouchListener {
    Context context;

    ImageView imagePoint;
    NodeCallbackListener listener;
    Type type;
    public NodeOutput(Context context, NodeCallbackListener listener, Node parent, int order, Type type) {
        super(context, parent, order,type);
        this.listener = listener;
        this.context = context;
        this.type = type;
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
        params.rightMargin = -NodeDimensionsCalculator.innerNodeMargin(); // /2 ;
        getView().setLayoutParams(params);

        View.inflate(context, R.layout.view_node_output, getView());
        imagePoint = getView().findViewById(R.id.output_dragpoint);
        imagePoint.setLayoutParams(new LinearLayout.LayoutParams(height / 2, height));


        changeColorTint(imagePoint, context, type.getColor());

        getView().setOnTouchListener(this);

        getView().setClipChildren(false);

        disableClipOnParents(getView());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            listener.onOutputDragged(parent,this);
        }
        return false;
    }

}

