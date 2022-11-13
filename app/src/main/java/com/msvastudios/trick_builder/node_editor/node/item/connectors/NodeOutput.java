package com.msvastudios.trick_builder.node_editor.node.item.connectors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.item.line.Line;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.Type;

import java.util.ArrayList;

public class NodeOutput extends NodeConnectorItem implements View.OnTouchListener {
    Context context;

    ImageView imagePoint;

    Type type;
    public NodeOutput(Context context, NodeCallbackListener listener, Node parent, int order, Type type) {
        super(context, parent, order,type, listener);

        this.context = context;
        this.type = type;
//        this.parent = parent;
        init(0);
    }



    @Override
    public String getID() {
        return super.getID();
    }

    @Override
    public void updatePositionVars(){
        point.setPosition(
                parent.getLeftMargin() + NodeDimensionsCalculator.getInnerNodeWidth()  + NodeDimensionsCalculator.innerNodeMargin() / 2,
                parent.getTopMargin() + NodeDimensionsCalculator.nodeItemHeight() * order + NodeDimensionsCalculator.nodeItemHeight() / 2  + NodeDimensionsCalculator.innerNodeMargin() / 2
        );
    }

    private void init(int Attr) {
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

        imagePoint.setColorFilter(context.getResources().getColor(type.getColor()));


        getView().setOnTouchListener(this);

        getView().setClipChildren(false);

        disableClipOnParents(getView());

        updatePositionVars();
    }


    @Override
    public NodeConnectorItem setText(String text) {
        TextView textView = getView().findViewById(R.id.output_textview);
        textView.setText(text);
        return this;
    }

    /**
     *
     * @param data any type of data that is expecting the other side
     */
    public void sendData(String data, RunnerCallback callback){
        if (this.getPoint() != null) { // ak nodeOutput má point (point by mal mať ale stále)
            ArrayList<Line> connectedLines = parent.getLinesView().getLinesContaining(this.getPoint()); // zistime si všetky spoje
            for (Line line : connectedLines) {
                line.getEndPoint().getParent().getNodeInputBy(line.getEndPoint()).push(data, callback);
            }
        }
    }


//    public Node getNodeById(String id){ // TODO parent in point is redundant refractor code and remove
//        for (Node node: parent.getId()) {
//
//        }
//    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            nodeCallbackListener.onOutputDragged(parent,this);
        }
        return false;
    }

}

