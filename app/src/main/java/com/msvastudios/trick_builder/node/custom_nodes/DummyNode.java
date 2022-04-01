package com.msvastudios.trick_builder.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.line.Line;
import com.msvastudios.trick_builder.line.LinesView;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node.item.NodeOutput;
import com.msvastudios.trick_builder.node.item.Type;

import java.util.ArrayList;
import java.util.Arrays;

public class DummyNode extends Node {
    LinesView linesView;

    public DummyNode(Context context, int leftMargin, int topMargin, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, linesView, listener);
        this.linesView = linesView;
        NodeOutput reference = addNodeOutput(Type.STRING);

        getNav().setColor(R.color.purple_200);

        build();
    }

    @Override
    public void process() {

    }

    @Override
    public void sendData() {
        for (NodeOutput nodeOutput : getNodeOutput()) { // prellopujeme všetko svoje outputy // potom to tu bude pre každý
                nodeOutput.sendData("smh");
        }
    }
}
