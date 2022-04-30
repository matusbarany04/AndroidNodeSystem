package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.Type;

public class RepeaterNode extends Node {
    NodeOutput output;
    NodeInput  input;
    public RepeaterNode(Context context, int leftMargin, int topMargin, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, linesView, listener);
        output = (NodeOutput) addNodeOutput(Type.STRING).setText("output");
        input = (NodeInput) addNodeInput(Type.STRING).setText("input");

        getNav().setColor(R.color.purple_200).setTitle("Repeater");

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