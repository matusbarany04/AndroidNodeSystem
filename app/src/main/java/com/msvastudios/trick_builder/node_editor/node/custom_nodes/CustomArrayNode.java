package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;
import android.view.View;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.Type;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.item.params_item.ArrayItem;
import com.msvastudios.trick_builder.node_editor.node.item.params_item.ButtonItem;

public class CustomArrayNode extends Node {

    NodeOutput reference;
    ArrayItem item;
    public CustomArrayNode(Context context, Integer leftMargin, Integer topMargin, String jsonData, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin,jsonData, linesView, listener);
        item = (ArrayItem) addNodeParam(ArrayItem.class);
        ButtonItem button = (ButtonItem) addNodeParam(ButtonItem.class);
        button.setText("add");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                i/tem.setHeight(item.getHeight()+1);
            }
        });
        reference = addNodeOutput(Type.ARRAY_LIST);
        reference.setText("New List");
        getNav().setColor(R.color.gray_400).setTitle("Custom List");

        build();
    }



    @Override
    public CustomNodes getType() {
        return CustomNodes.CUSTOM_ARRAY;
    }

    @Override
    public void process() {

    }

    @Override
    public RunnerCallback sendData(RunnerCallback callback){
//        reference.sendData();
        System.out.println("was in dummy");
        return callback;
    }
}
