package com.msvastudios.trick_builder.trick_listing.tricks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.popups.TrickInfoPopupActivity;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;

import java.util.ArrayList;

public class TrickListActivity extends AppCompatActivity {
    ArrayList<Trick> tricks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trick_list);
        tricks = new ArrayList<>();

        getIntent().getExtras().getString("group_id");

        ListView listView = findViewById(R.id.trickListView);
        DatabaseHandler.getInstance(this);
        TricksAdapter adapter = new TricksAdapter(getApplicationContext(),tricks );
        listView.setAdapter(adapter);


        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Trick trick = adapter.getItem(i);
            Intent intent = new Intent(TrickListActivity.this, TrickInfoPopupActivity.class);

            startActivity(intent);

            overridePendingTransition(0,0);
        });
    }
}