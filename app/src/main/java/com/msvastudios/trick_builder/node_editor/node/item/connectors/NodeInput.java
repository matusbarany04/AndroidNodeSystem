package com.msvastudios.trick_builder.node_editor.node.item.connectors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;
import com.msvastudios.trick_builder.node_editor.node.item.Type;

public class NodeInput extends NodeConnectorItem {
    LinearLayout view;
    Context context;
    int width, height;
    ImageView imagePoint;

    Node parent;
    int order;
    Type type;
    String data;

    public NodeInput(Context context, NodeCallbackListener listener, Node parent, int order, Type type) {
        super(context, parent, order, type, listener);

        this.context = context;
        this.type = type;
        this.width = NodeDimensionsCalculator.getInnerNodeWidth();
        this.height = NodeDimensionsCalculator.nodeItemHeight();
        this.parent = parent;
        this.order = order;

        init(null, 0);
    }

    @Override
    public String getID() {
        return "NodeInput" + super.getID();
    }

    @Override
    public void updatePositionVars() {
        point.setPosition(
                parent.getLeftMargin() + NodeDimensionsCalculator.innerNodeMargin() / 2,
                parent.getTopMargin() + NodeDimensionsCalculator.nodeItemHeight() * order + NodeDimensionsCalculator.nodeItemHeight() / 2
                        + NodeDimensionsCalculator.innerNodeMargin() / 2
        );
    }

    @Override
    public NodeConnectorItem setText(String text) {
        TextView textView = getView().findViewById(R.id.input_textview);
        textView.setText(text);
        return this;
    }

    public String getData() {
        return data;
    }

    public void push(String data){
        this.data = data;
        parent.dataInInputSent(data, this);
    }


    private void init(AttributeSet attrs, int defStyle) {
//        getView() = new LinearLayout(context);
        getView().setOrientation(LinearLayout.HORIZONTAL);
        getView().setGravity(1);
//        setBackgroundColor(Color.BLUE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.topMargin = height * order;
        params.leftMargin = -NodeDimensionsCalculator.innerNodeMargin() / 2;
        getView().setLayoutParams(params);

        View.inflate(context, R.layout.view_node_input, getView());
        imagePoint = getView().findViewById(R.id.input_dragpoint);
        imagePoint.setLayoutParams(new LinearLayout.LayoutParams(height / 2, height));
        changeColorTint(imagePoint, context, type.getColor());

        updatePositionVars();
    }


}
