package com.msvastudios.trick_builder.node_editor.node.custom_nodes;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bumptech.glide.load.model.DataUrlLoader;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.node.item.DataFlower;
import com.msvastudios.trick_builder.node_editor.node.item.Type;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.item.params_item.ListItem;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupEntity;
import com.msvastudios.trick_builder.utils.sqlite.tricks.TrickEntity;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupNode extends Node {
    ListItem listRef;
    NodeOutput reference;
    ArrayList<String> tricks;
    ArrayList<GroupEntity> groups;
    String chosenGroupId;
    Context context;
    public GroupNode(Context context, int leftMargin, int topMargin, String jsonData, LinesView linesView, NodeCallbackListener listener) {
        super(context, leftMargin, topMargin, jsonData, linesView, listener);
        this.context = context;
        reference = (NodeOutput) addNodeOutput(Type.ARRAY_LIST).setText("Group list");
//        NodeOutput reference2 = addNodeOutput(Type.STRING);
        Log.d("group json data", jsonData);
        listRef = (ListItem) addNodeParam(ListItem.class);

        //try catch for json data

        if (jsonData.length() != 0 && jsonData.contains("~")) {
            chosenGroupId = jsonData.split("~")[1];

        }else {
            chosenGroupId = "not known";
        }
        tricks = new ArrayList<>();

        updateTrickList();
        DatabaseHandler.getInstance(context).getGroups(new DatabaseHandler.Groups() {
            @Override
            public void onGroupsFetch(ArrayList<GroupEntity> groupEntities) {
                ArrayList<String> groupList = new ArrayList<>();
                groups = (ArrayList<GroupEntity>) groupEntities.clone();
                for (GroupEntity databaseEntity : groupEntities) {
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
                setJsonData(adapterView.getAdapter().getItem(i).toString() + "~" + groups.get(i).groupUUID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getNav().setColor(R.color.teal_200).setTitle("GROUP pciker");

        build();
    }


    private void updateTrickList()
    {
        DatabaseHandler.getInstance(context).getTricksByGroupId(chosenGroupId, new DatabaseHandler.Trick() {
            @Override
            public void onTricksFetched(ArrayList<TrickEntity> trickEntities) {
                for (TrickEntity entity : trickEntities) {
                    tricks.add(entity.name);
                }
            }
        });
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
        reference.sendData(reference.getType().encode(tricks), callback);
        return callback;
    }
}
