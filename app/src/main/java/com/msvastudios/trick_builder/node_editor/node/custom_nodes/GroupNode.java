package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.Type;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.item.params_item.ListItem;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupEntity;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupNode extends Node {
    ListItem listRef;
    NodeOutput reference;
    ArrayList<String> tricks;
    public GroupNode(Context context, int leftMargin, int topMargin, String jsonData, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, jsonData, linesView, listener);
        reference = (NodeOutput) addNodeOutput(Type.STRING).setText("Group list");
//        NodeOutput reference2 = addNodeOutput(Type.STRING);
        Log.d("group json data", jsonData);
        listRef = (ListItem) addNodeParam(ListItem.class);
        listRef.setSpinnerItem(jsonData);

        DatabaseHandler.getInstance(context).getGroups(new DatabaseHandler.Groups() {
            @Override
            public void onGroupsFetch(ArrayList<GroupEntity> groupEntities) {
                ArrayList<String> groupList = new ArrayList<>();
                for (GroupEntity databaseEntity: groupEntities) {
                    groupList.add(databaseEntity.name);
                }
                listRef.setList(groupList);
                listRef.setSpinnerItem(jsonData);
            }
        });
        // aug 23 2022
        //TODO do something when person is faster than sqlite database
        //TODO load trick list

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
        getNav().setColor(R.color.teal_200).setTitle("GROUP pciker");

        build();
    }

    @Override
    public CustomNodes getType() {
        return CustomNodes.GROUP_NODE;
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
