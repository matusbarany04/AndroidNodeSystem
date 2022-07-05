package com.msvastudios.trick_builder.trick_listing.groups;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.algorithm_popup.NewNodePopup;
import com.msvastudios.trick_builder.generator_editor.items.AlgosAdapter;
import com.msvastudios.trick_builder.node_editor.NodeActivity;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.trick_listing.tricks.TrickListActivity;
import com.msvastudios.trick_builder.utils.ListViewAdapter;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

public class GroupListActivity extends AppCompatActivity {
    AddGroupPopup groupPopup;
    ArrayList<GroupEntity> groups;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        buildPopup();
        gridView = findViewById(R.id.groupGridView);
        groups = new ArrayList<>();
        createAdapter();



        Log.d("FAILRATE ", DatabaseHandler.getFailrate()+ " ");
        DatabaseHandler.getInstance(this).getGroups(new DatabaseHandler.Groups() {
            @Override
            public void onGroupsFetch(ArrayList<GroupEntity> groupEntities) {
                groups.clear();
                for (GroupEntity databaseEntity: groupEntities) {
                    groups.add(databaseEntity);
                }
                adapter.notifyDataSetChanged();
            }
        });


        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(GroupListActivity.this, TrickListActivity.class);
            startActivity(intent);

            overridePendingTransition(0, 0);
        });

        FloatingActionButton backButton = findViewById(R.id.groupBackButton);
        backButton.setOnClickListener((view) -> {
            finish();
            overridePendingTransition(0, 0);
        });


        FloatingActionButton addNewTrickButton = findViewById(R.id.groupAddButton);
        addNewTrickButton.setOnClickListener((view) -> {
            groupPopup.show();
        });
    }

    private void buildPopup() {
        groupPopup = new AddGroupPopup(GroupListActivity.this);
        groupPopup.setOnNewGroupShouldBeAdded((name) -> {
            System.out.println("in callback" + name);
            GroupEntity entity = new GroupEntity(name, UUID.randomUUID().toString());
            DatabaseHandler.getInstance(GroupListActivity.this).insertGroup(entity, new DatabaseHandler.Finish() {
                @Override
                public void onActionFinished(int status) {
                    groupPopup.hide();
                    System.out.println(groups.size());
                    groups.add(entity);
                    System.out.println(groups.size());

                    System.out.println("fuck hell imm go here");
                    for (GroupEntity item: groups) {
                        System.out.println(item.name+ "");

                    };
                }
            });

        });
    }
    ListViewAdapter<GroupEntity> adapter;
    private void createAdapter(){
        adapter = new ListViewAdapter<GroupEntity>(groups, R.layout.grid_item, new ListViewAdapter.ItemInflater<GroupEntity>() {
            @Override
            public View inflateItem(View convertView, int position, GroupEntity item) {

                TextView textView = convertView.findViewById(R.id.grid_item_text);
                textView.setText(item.name);

                return convertView;

            }
        });

        gridView.setAdapter(adapter);
    }
}

