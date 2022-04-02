package com.msvastudios.trick_builder.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.line.LinesView;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node.item.Type;

public class EndNode extends Node {
    NodeInput reference;
    public EndNode(Context context, int leftMargin, int topMargin, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, linesView, listener);
        reference = (NodeInput) addNodeInput(Type.STRING).setText("final input");
        build();

        getNav().setColor(R.color.design_default_color_on_primary).setTitle("End");
    }


    @Override
    public void process() {
        reference.setText(reference.getData());
//        System.out.println("fuck yeah " +  reference.getData());
    }

    @Override
    public void sendData() {
     //TODO poslať dáta naspäť node managerovi 
    }


}
