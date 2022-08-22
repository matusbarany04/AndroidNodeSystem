package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.Type;

public class RepeaterNode extends Node {
    NodeOutput output;
    NodeInput  input;
    public RepeaterNode(Context context, int leftMargin, int topMargin, String jsonData, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, jsonData, linesView, listener);
        output = (NodeOutput) addNodeOutput(Type.STRING).setText("output");
        input = (NodeInput) addNodeInput(Type.STRING).setText("input");

        getNav().setColor(R.color.purple_200).setTitle("Repeater");

        build();
    }

    @Override
    public CustomNodes getType() {
        return CustomNodes.REPEATER_NODE;
    }

    @Override
    public void process() {

    }

    @Override
    public RunnerCallback sendData(RunnerCallback callback) {
        output.sendData(input.getData(), callback);
        System.out.println("was in repeater");
        return callback;
    }
}
