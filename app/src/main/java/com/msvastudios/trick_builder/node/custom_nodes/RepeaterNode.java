package com.msvastudios.trick_builder.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.line.LinesView;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node.item.NodeInput;
import com.msvastudios.trick_builder.node.item.NodeOutput;
import com.msvastudios.trick_builder.node.item.Type;

public class RepeaterNode extends Node {
    NodeOutput output;
    NodeInput  input;
    public RepeaterNode(Context context, int leftMargin, int topMargin, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, linesView, listener);
        output = addNodeOutput(Type.STRING);
        input = addNodeInput(Type.STRING);

        getNav().setColor(R.color.purple_200);

        build();
    }

    @Override
    public void process() {

    }

    @Override
    public void sendData() {
        output.sendData(input.getData());
    }
}