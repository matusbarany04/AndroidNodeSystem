package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.Type;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.item.params_item.ListItem;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PickRandomNode extends Node {
    ListItem listRef;
    NodeOutput itemOutput;
    NodeInput trickArrayInput;

    public PickRandomNode(Context context, int leftMargin, int topMargin, String jsonData, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, jsonData, linesView, listener);
        itemOutput = (NodeOutput) addNodeOutput(Type.STRING).setText("Trick");
        trickArrayInput = (NodeInput) addNodeInput(Type.ARRAY_LIST).setText("TrickList");

        getNav().setColor(R.color.teal_200).setTitle("GROUP picker");

        build();
    }

    @Override
    public CustomNodes getType() {
        return CustomNodes.PICK_RANDOM;
    }

    String outputTrick;
    @Override
    public void process() {
        ArrayList<String> tricks = (ArrayList<String>) trickArrayInput.getData();
        Random random = new Random();
        outputTrick = tricks.get(random.nextInt(tricks.size()));
    }

    @Override
    public RunnerCallback sendData(RunnerCallback callback) {
        itemOutput.sendData(outputTrick, callback);
        return callback;
    }
}

