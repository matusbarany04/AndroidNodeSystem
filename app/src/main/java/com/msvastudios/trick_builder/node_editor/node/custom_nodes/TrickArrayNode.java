package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.Type;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.params_item.ListItem;

public class TrickArrayNode extends Node {
    ListItem listRef;
    NodeOutput reference;

    public TrickArrayNode(Context context, int leftMargin, int topMargin, String jsonData, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, jsonData, linesView, listener);
        reference = (NodeOutput) addNodeOutput(Type.STRING).setText("Trick list");
//        NodeOutput reference2 = addNodeOutput(Type.STRING);
        listRef = (ListItem) addNodeParam(ListItem.class);
        listRef.setSpinnerItem(jsonData);
        listRef.setListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("clicked and chose " + adapterView.getAdapter().getItem(i));
                setJsonData(adapterView.getAdapter().getItem(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getNav().setColor(R.color.teal_200).setTitle("List picker");

        build();
    }

    @Override
    public CustomNodes getType() {
        return CustomNodes.TRICK_ARRAY_NODE;
    }

    @Override
    public void process() {
    }

    @Override
    public RunnerCallback sendData(RunnerCallback callback) {
        reference.sendData(getJsonData(), callback);
        return callback;
    }
}
