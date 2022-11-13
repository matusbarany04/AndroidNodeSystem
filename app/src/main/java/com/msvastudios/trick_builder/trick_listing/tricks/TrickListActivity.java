package com.msvastudios.trick_builder.trick_listing.tricks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.popups.AddTrickPopup;
import com.msvastudios.trick_builder.popups.TrickInfoPopupActivity;
import com.msvastudios.trick_builder.popups.YesNoDialog;
import com.msvastudios.trick_builder.trick_listing.groups.GroupListActivity;
import com.msvastudios.trick_builder.utils.FlowLayout;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupEntity;
import com.msvastudios.trick_builder.utils.sqlite.tricks.TrickEntity;

import java.util.ArrayList;
import java.util.UUID;

import kotlinx.coroutines.flow.Flow;

public class TrickListActivity extends AppCompatActivity {
    ArrayList<Trick> tricks;
    TricksAdapter adapter;
    ListView listView;
    AddTrickPopup trickPopup;
    String groupId;
    boolean longHoldTriggered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO add search
        //TODO add name of list on top and i want to be able to edit it
        setContentView(R.layout.activity_trick_list);
        listView = findViewById(R.id.trickListView);
        tricks = new ArrayList<>();
        buildListView(tricks);
        longHoldTriggered = false;
        groupId = getIntent().getExtras().getString("group_id");
        if (groupId.equals("all_tricks")) { //TODO add learned tricks
            DatabaseHandler.getInstance(this).getTricks(new DatabaseHandler.Trick() {
                @Override
                public void onTricksFetched(ArrayList<TrickEntity> trick_entities) {
                    if (trick_entities != null) {
                        ArrayList<Trick> data = new ArrayList<>();
                        for (TrickEntity entity : trick_entities) {
                            data.add(new Trick(entity));
                        }
                        tricks = data;
                        buildListView(tricks);
                    }
                }
            });
        } else {
            DatabaseHandler.getInstance(this).getTricksByGroupId(groupId, new DatabaseHandler.Trick() {
                @Override
                public void onTricksFetched(ArrayList<TrickEntity> trick_entities) {
                    if (trick_entities != null) {
                        ArrayList<Trick> data = new ArrayList<>();
                        for (TrickEntity entity : trick_entities) {
                            data.add(new Trick(entity));
                        }
                        tricks = data;
                        buildListView(tricks);
                    }
                }
            });
        }
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            if (!longHoldTriggered) {
                Trick trick = adapter.getItem(i);
                Intent intent = new Intent(TrickListActivity.this, TrickInfoPopupActivity.class);
                intent.putExtra("trick_id", trick.uuid);
                intent.putExtra("trick_group_id", trick.groupUuid);
                intent.putExtra("trick_description", trick.description);
                intent.putExtra("trick_learned", trick.learned);
                intent.putExtra("trick_name", trick.name);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            longHoldTriggered = false;
            Toast.makeText(TrickListActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                longHoldTriggered = true;
                YesNoDialog dialog = new YesNoDialog(TrickListActivity.this,
                        "Deleting " + ((Trick) listView.getAdapter().getItem(position)).name,
                        "You can't undo this action.");
                dialog.setOnItemClickListener(new YesNoDialog.Popup() {
                    @Override
                    public void onYesClicked() {
                        deleteTrick(position);
                        dialog.hide();
                    }

                    @Override
                    public void onNoClicked() {
                        dialog.hide();
                    }
                });
                dialog.show();
                Toast.makeText(TrickListActivity.this, "Held for too long", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        FloatingActionButton backButton = findViewById(R.id.trickBackButton);
        backButton.setOnClickListener((view) -> {
            finish();
            overridePendingTransition(0, 0);
        });

        FloatingActionButton addNewTrickButton = findViewById(R.id.trickAddButton);
        addNewTrickButton.setOnClickListener((view) -> {
            trickPopup.show();
        });

        buildPopup();
    }

    private void deleteTrick(int position) {

        DatabaseHandler.getInstance(TrickListActivity.this).deleteTrickByUuid(null, tricks.get(position).uuid);
        ArrayList<Trick> clone = (ArrayList<Trick>) tricks.clone();
        tricks.clear();
        for (int i = 0; i < clone.size(); i++) {
            if (i != position) tricks.add(clone.get(i));
        }
        adapter.notifyDataSetChanged();

    }

    public void buildListView(ArrayList<Trick> data) {
        runOnUiThread(() -> {
            adapter = new TricksAdapter(getApplicationContext(), data);
            listView.setAdapter(adapter);

        });
    }

    private void buildPopup() {
        trickPopup = new AddTrickPopup(this);
        trickPopup.setOnNewGroupShouldBeAdded((name) -> {
            // TODO check for duplicates
            ArrayList<String> groupIds = new ArrayList<>();
            Log.d("tricklist activity", groupId + " ");
            if (groupId == null) System.out.println("yelling!");
            groupIds.add(groupId);
            TrickEntity entity = new TrickEntity(name, UUID.randomUUID().toString(), groupIds, false);
            DatabaseHandler.getInstance(this).insertTrick(entity, (int status) -> {
                System.out.println("status " + status);
                trickPopup.hide();
                tricks.add(new Trick(entity));
                runOnUiThread(() -> {

                    buildListView(tricks);
                });
            });

        });
    }


}