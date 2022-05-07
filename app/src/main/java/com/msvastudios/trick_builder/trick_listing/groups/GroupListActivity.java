package com.msvastudios.trick_builder.trick_listing.groups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.generator_editor.items.AlgosAdapter;
import com.msvastudios.trick_builder.trick_listing.tricks.TrickListActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        ArrayList<String> groups = new ArrayList<>();

        groups.add("smh");
        groups.add("smh");
        groups.add("smh");
        groups.add("smh");
        groups.add("smh");

        GroupsGridAdapter groupsGridAdapter = new GroupsGridAdapter(getApplicationContext(), groups);
        GridView gridView = findViewById(R.id.groupGridView);
        gridView.setAdapter(groupsGridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GroupListActivity.this, TrickListActivity.class);
                startActivity(intent);

                overridePendingTransition(0, 0);
            }


        });
//
//        GroupsGridAdapter basicActionsAdapter = new GroupsGridAdapter(getApplicationContext(),);
//
//        LinearLayout basicActionsView = findViewById(R.id.customGridView);
//        gridView.setAdapter(basicActionsAdapter);
//

        FloatingActionButton backButton = findViewById(R.id.groupBackButton);
        backButton.setOnClickListener((view) -> {
            finish();
            overridePendingTransition(0, 0);
        });

        FloatingActionButton addNewTrickButton = findViewById(R.id.groupAddButton);
        addNewTrickButton .setOnClickListener((view) -> {
        });

    }
}