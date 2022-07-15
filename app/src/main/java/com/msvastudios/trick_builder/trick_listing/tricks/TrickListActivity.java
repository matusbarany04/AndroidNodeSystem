package com.msvastudios.trick_builder.trick_listing.tricks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.popups.TrickInfoPopupActivity;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.tricks.TrickEntity;

import java.util.ArrayList;

public class TrickListActivity extends AppCompatActivity {
    ArrayList<Trick> tricks;
    TricksAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trick_list);
        listView = findViewById(R.id.trickListView);
        tricks = new ArrayList<>();
        buildListView(tricks);

        String groupId = getIntent().getExtras().getString("group_id");
        if (groupId.equals("all_tricks")){
            DatabaseHandler.getInstance(this).getTricks(new DatabaseHandler.Trick() {
                @Override
                public void onTricksFetched(ArrayList<TrickEntity> trick_entities) {
                    if (trick_entities != null){
                        ArrayList<Trick> data = new ArrayList<>();
                        for (TrickEntity entity: trick_entities) {
                            data.add(new Trick(entity));
                        }
                        tricks = data;
                    }
                }
            });
        }else{
            DatabaseHandler.getInstance(this).getTricksByGroupId(groupId, new DatabaseHandler.Trick() {
                @Override
                public void onTricksFetched(ArrayList<TrickEntity> trick_entities) {
                    if (trick_entities != null){
                        ArrayList<Trick> data = new ArrayList<>();
                        for (TrickEntity entity: trick_entities) {
                            data.add(new Trick(entity));
                        }
                        tricks = data;
                    }
                }
            });
        }

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Trick trick = adapter.getItem(i);
            Intent intent = new Intent(TrickListActivity.this, TrickInfoPopupActivity.class);

            startActivity(intent);

            overridePendingTransition(0,0);
        });

        FloatingActionButton backButton = findViewById(R.id.trickBackButton);
        backButton.setOnClickListener((view) -> {
            finish();
            overridePendingTransition(0, 0);
        });

        FloatingActionButton addNewTrickButton = findViewById(R.id.trickAddButton);
        addNewTrickButton.setOnClickListener((view) -> {
            //groupPopup.show();
        });
    }

    public void buildListView(ArrayList<Trick> data){
        adapter = new TricksAdapter(getApplicationContext(),data);
        listView.setAdapter(adapter);

    }
    public void processTrickEntitieas
}