package com.msvastudios.trick_builder.trick_listing.tricks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.trick_listing.popup.TrickInfoPopupActivity;

import java.util.ArrayList;

public class TrickListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trick_list);


        ArrayList<Trick> tricks = new ArrayList<>();

        tricks.add(new Trick("smh"));
        tricks.add(new Trick("sqmh"));
        tricks.add(new Trick("ssmh"));
        tricks.add(new Trick("samh"));
        tricks.add(new Trick("sfmh"));
        tricks.add(new Trick("smh"));
        tricks.add(new Trick("sqmh"));
        tricks.add(new Trick("ssmh"));
        tricks.add(new Trick("samh"));
        tricks.add(new Trick("sfmh"));
        tricks.add(new Trick("smh"));
        tricks.add(new Trick("sqmh"));
        tricks.add(new Trick("ssmh"));
        tricks.add(new Trick("samh"));
        tricks.add(new Trick("sfmh"));

        ListView listView = findViewById(R.id.trickListView);

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