package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.Type;

public class ConcatNode extends Node {

    NodeInput secondString, firstString;
    NodeOutput output;
    public ConcatNode(Context context, Integer leftMargin, Integer topMargin, String jsonData, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin,jsonData, linesView, listener);
        firstString = addNodeInput(Type.STRING);
        firstString.setText("first String");
        secondString = addNodeInput(Type.STRING);
        secondString.setText("first String");
        output = addNodeOutput(Type.STRING);
        output.setText("output");

        getNav().setColor(R.color.purple_200).setTitle("Concat");

        build();
    }



    @Override
    public CustomNodes getType() {
        return CustomNodes.CONCAT_NODE;
    }

    String outputString;
    @Override
    public void process() {
        outputString = firstString.getRawData() + secondString.getRawData();
    }

    @Override
    public RunnerCallback sendData(RunnerCallback callback){
        output.sendData(outputString, callback);

        return callback;
    }
}
