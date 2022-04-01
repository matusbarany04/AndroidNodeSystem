package com.msvastudios.trick_builder.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.line.LinesView;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node.item.NodeInput;
import com.msvastudios.trick_builder.node.item.Type;

public class EndNode extends Node {
    NodeInput reference;
    public EndNode(Context context, int leftMargin, int topMargin, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, linesView, listener);
        reference = addNodeInput(Type.STRING);
        build();

        getNav().setColor(R.color.design_default_color_on_primary);
    }


    @Override
    public void process() {

        System.out.println("fuck yeah " +  reference.getData());
    }

    @Override
    public void sendData() {

    }


}
