package com.msvastudios.trick_builder.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.line.LinesView;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node.item.Type;
import com.msvastudios.trick_builder.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node.item.params_item.ListItem;

import java.util.List;

public class TrickArrayNode extends Node {
    ListItem listRef;
    NodeOutput reference;
    public TrickArrayNode(Context context, int leftMargin, int topMargin, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, linesView, listener);
        reference = (NodeOutput) addNodeOutput(Type.STRING).setText("Trick list");
//        NodeOutput reference2 = addNodeOutput(Type.STRING);
        listRef = (ListItem) addNodeParam(ListItem.class);

        getNav().setColor(R.color.teal_200).setTitle("List picker");

        build();
    }

    @Override
    public void process() {

    }

    @Override
    public void sendData() {
        reference.sendData(listRef.getChosenData());
    }
}
