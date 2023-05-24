package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.Type;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;

import java.util.ArrayList;
import java.util.Arrays;

public class AddToListNode  extends Node {

        NodeInput arrayList;
        NodeInput text;
        NodeOutput reference;
        public AddToListNode(Context context, Integer leftMargin, Integer topMargin, String jsonData, LinesView linesView, NodeCallbackListener listener) {
            super(context, leftMargin, topMargin,jsonData, linesView, listener);
            arrayList = addNodeInput(Type.ARRAY_LIST);
            arrayList.setText("List");
            text = addNodeInput(Type.STRING);
            text.setText("New item");
            reference = addNodeOutput(Type.ARRAY_LIST);
            reference.setText("New List");
            getNav().setColor(R.color.add).setTitle("Add to list");


            build();
        }



        @Override
        public CustomNodes getType() {
            return CustomNodes.ADD_TO_LIST;
        }

        @Override
        public void process() {

        }

        @Override
        public RunnerCallback sendData(RunnerCallback callback){

            //TODO check if text is empty
            ArrayList<String> tricks = (ArrayList<String>) arrayList.getData();
            tricks.add((String) text.getData());

            reference.sendData(reference.getType().encode(tricks),callback);
            return callback;
        }
    }
