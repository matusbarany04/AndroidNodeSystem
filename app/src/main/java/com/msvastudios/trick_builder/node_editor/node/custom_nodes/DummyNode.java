package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.Type;

import java.util.function.Supplier;

public class DummyNode extends Node {


    public DummyNode(Context context, Integer leftMargin, Integer topMargin, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, linesView, listener);

        NodeOutput reference = addNodeOutput(Type.STRING);

        getNav().setColor(R.color.purple_200).setTitle("Dummy node");

        build();
    }



    @Override
    public CustomNodes getType() {
        return CustomNodes.DUMMY_NODE;
    }

    @Override
    public void process() {

    }

    @Override
    public RunnerCallback sendData(RunnerCallback callback){
        for (NodeOutput nodeOutput : getNodeOutput()) { // preloopujeme všetko svoje outputy // potom to tu bude pre každý jedinečné
                nodeOutput.sendData("smh",callback);
        }
        System.out.println("was in dummy");
        return callback;
    }
}
