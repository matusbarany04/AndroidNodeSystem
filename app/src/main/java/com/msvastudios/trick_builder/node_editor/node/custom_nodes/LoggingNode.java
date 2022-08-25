package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.Type;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;

public class LoggingNode extends Node {

    NodeInput reference;
    public LoggingNode(Context context, Integer leftMargin, Integer topMargin, String jsonData, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin,jsonData, linesView, listener);

        reference = (NodeInput) addNodeInput(Type.ANY).setText("---");

        getNav().setColor(R.color.purple_200).setTitle("Logger");

        build();
    }

    @Override
    public CustomNodes getType() {
        return CustomNodes.LOGGING_NODE;
    }

    @Override
    public void process() {
        reference.setText(reference.getRawData());
    }

    @Override
    public RunnerCallback sendData(RunnerCallback callback){
        return callback;
    }
}
