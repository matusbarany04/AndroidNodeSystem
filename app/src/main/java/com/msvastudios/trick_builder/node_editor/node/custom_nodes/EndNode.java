package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.Type;

public class EndNode extends Node {
    NodeInput reference;
    public EndNode(Context context, int leftMargin, int topMargin, String jsonData,LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin,jsonData, linesView, listener);
        reference = (NodeInput) addNodeInput(Type.STRING).setText("final input");
        build();

        getNav().setColor(R.color.design_default_color_on_primary).setTitle("End");
    }


    @Override
    public CustomNodes getType() {
        return CustomNodes.END_NODE;
    }

    @Override
    public void process() {
        reference.setText((String) reference.getData());
//        System.out.println("fuck yeah " +  reference.getData());
    }

    @Override
    public RunnerCallback sendData(RunnerCallback callback){
        //TODO poslať dáta naspäť node managerovi
        if (callback != null){
            callback.finished((String) reference.getData());
        }
        System.out.println("sending data !!");
        return callback;
    }


}
