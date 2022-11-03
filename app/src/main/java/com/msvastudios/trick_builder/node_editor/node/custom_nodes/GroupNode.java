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
import com.msvastudios.trick_builder.trick_listing.groups.StaticGroups;
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

        //TODO try catch for json data
        if (jsonData.length() != 0 && jsonData.contains("~")) {
            chosenGroupId = jsonData.split("~")[1];

        } else {
            chosenGroupId = "not known"; //TODO catch exception
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
                //code changed since this comment was created but maybe it was something important
                //TODO check name when creating new group for these two
                for (StaticGroups group: StaticGroups.values()) {
                    groupList.add(group.getTitle());
                }

                listRef.setList(groupList);
                listRef.setSpinnerItem(jsonData.split("~")[0]);
            }
        });

        // aug 23 2022
        //TODO do something when person is faster than sqlite database
        //TODO load trick list

        listRef.setListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("clicked and chose " + adapterView.getAdapter().getItem(i));
                StringBuilder jsonData = new StringBuilder(adapterView.getAdapter().getItem(i).toString());

                jsonData.append("~");
                boolean wasStatic = false;
                for (StaticGroups group: StaticGroups.values()) {
                    if(adapterView.getAdapter().getItem(i).toString().equals(group.getTitle())){
                      wasStatic = true;
                      jsonData.append(group.getTitle());
                      chosenGroupId = group.getTitle();
                      break;
                    }
                }
                if(!wasStatic) {
                    jsonData.append(groups.get(i).groupUUID);
                    chosenGroupId = groups.get(i).groupUUID;
                }
                setJsonData(jsonData.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getNav().setColor(R.color.teal_200).setTitle("GROUP picker");
        build();
    }


    private void updateTrickList() {
        boolean wasStatic = false;
        for (StaticGroups group: StaticGroups.values()) {
            if (chosenGroupId.equals(group.getTitle())) {
                wasStatic = true;
                DatabaseHandler.getInstance(context).getTricksByGroupId(
                        StaticGroups.getByTitle(chosenGroupId), new DatabaseHandler.Trick() {
                    @Override
                    public void onTricksFetched(ArrayList<TrickEntity> trickEntities) {
                        for (TrickEntity entity : trickEntities) {
                            tricks.add(entity.name);
                        }
                    }
                });
                break;
            }
        }
        if (!wasStatic) {
            DatabaseHandler.getInstance(context).getTricksByGroupId(chosenGroupId, new DatabaseHandler.Trick() {
                @Override
                public void onTricksFetched(ArrayList<TrickEntity> trickEntities) {
                    for (TrickEntity entity : trickEntities) {
                        tricks.add(entity.name);
                    }
                }
            });
        }
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
