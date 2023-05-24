package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.Type;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.item.params_item.EditTextItem;
import com.msvastudios.trick_builder.node_editor.node.item.params_item.ParameterItem;

public class CustomStringNode extends Node {

    EditTextItem editTextItem;
        public CustomStringNode(Context context, Integer leftMargin, Integer topMargin, String jsonData, LinesView linesView, NodeCallbackListener listener) {
            super(context, leftMargin, topMargin,jsonData, linesView, listener);

            editTextItem = (EditTextItem) addNodeParam(EditTextItem.class);
            editTextItem.setOnDataChanged(new ParameterItem.Data() {
                @Override
                public void changed(String data) {
                    setJsonData(data);
                }
            });

            if (jsonData != null)
                editTextItem.setText(jsonData);

            NodeOutput reference = addNodeOutput(Type.STRING);

            getNav().setColor(R.color.gray_600).setTitle("Custom Text");

            build();
        }



        @Override
        public CustomNodes getType() {
            return CustomNodes.CUSTOM_STRING;
        }

        @Override
        public void process() {

        }

        @Override
        public RunnerCallback sendData(RunnerCallback callback){
            for (NodeOutput nodeOutput : getNodeOutput()) { // preloopujeme všetko svoje outputy // potom to tu bude pre každý jedinečné
                nodeOutput.sendData( editTextItem.getChosenData() ,callback);
            }
            System.out.println("was in dummy");
            return callback;
        }
    }

