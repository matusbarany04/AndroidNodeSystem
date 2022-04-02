package com.msvastudios.trick_builder.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.line.LinesView;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node.item.Type;

public class DummyNode extends Node {


    public DummyNode(Context context, int leftMargin, int topMargin, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, linesView, listener);

        NodeOutput reference = addNodeOutput(Type.STRING);

        getNav().setColor(R.color.purple_200).setTitle("Dummy node");

        build();
    }

    @Override
    public void process() {

    }

    @Override
    public void sendData() {
        for (NodeOutput nodeOutput : getNodeOutput()) { // prellopujeme všetko svoje outputy // potom to tu bude pre každý jedinečné
                nodeOutput.sendData("smh");
        }
    }
}
