package com.msvastudios.trick_builder.trick_listing.groups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.popups.AddGroupPopup;
import com.msvastudios.trick_builder.popups.YesNoDialog;
import com.msvastudios.trick_builder.trick_listing.tricks.TrickListActivity;
import com.msvastudios.trick_builder.utils.ListViewAdapter;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupEntity;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class GroupListActivity extends AppCompatActivity {
    AddGroupPopup groupPopup;
    ArrayList<GroupEntity> groups;
    GridView gridView;
    boolean longHoldTriggered;
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
                groups.addAll(groupEntities);
                adapter.notifyDataSetChanged();
            }
        });
         longHoldTriggered = false;
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {

            if (!longHoldTriggered){
                Intent intent = new Intent(GroupListActivity.this, TrickListActivity.class);
                intent.putExtra("group_id", groups.get(i).groupUUID);
                //getIntent().getExtras().getString("group_id");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            longHoldTriggered = false;

        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                longHoldTriggered = true;
                YesNoDialog dialog = new YesNoDialog(GroupListActivity.this, getString(R.string.deleteWarningDialogTitle), getString(R.string.deleteWarningDialogDescription));
                dialog.setOnItemClickListener(new YesNoDialog.Popup() {
                    @Override
                    public void onYesClicked() {
                        deleteGroup(position);
                        dialog.hide();
                    }
                    @Override
                    public void onNoClicked() {
                        dialog.hide();
                    }
                });
                dialog.show();
                Toast.makeText(GroupListActivity.this, "Held for too long", Toast.LENGTH_SHORT).show();
                return false;
            }
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
        //TODO outsource string ids to enum
        RelativeLayout all_tricks_button = findViewById(R.id.all_tricks_button);
        all_tricks_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupListActivity.this, TrickListActivity.class);
                intent.putExtra("group_id", "all_tricks");
                //getIntent().getExtras().getString("group_id");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        RelativeLayout learned_tricks_button = findViewById(R.id.learned_tricks_button);
        learned_tricks_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupListActivity.this, TrickListActivity.class);
                intent.putExtra("group_id", "learned_tricks");
                //getIntent().getExtras().getString("group_id");
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void deleteGroup(int position){
        DatabaseHandler.getInstance(GroupListActivity.this).deleteGroupById(groups.get(position).groupUUID);
        ArrayList<GroupEntity> clone = (ArrayList<GroupEntity>) groups.clone();
        groups.clear();
        for (int i = 0; i < clone.size();i++) {
            if (i != position) groups.add(clone.get(i));
        }
        adapter.notifyDataSetChanged();
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

