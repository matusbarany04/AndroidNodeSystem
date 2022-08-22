package com.msvastudios.trick_builder.popups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Database;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupEntity;

import java.util.ArrayList;


public class TrickInfoPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trick_info_popup);
        getWindow().setStatusBarColor(Color.alpha(0));
        getWindow().setNavigationBarColor(Color.alpha(0));
        Bundle data = getIntent().getExtras();
        String trickId = data.getString("trick_id");
        ArrayList<String> groupIds = data.getStringArrayList("trick_group_id");
        String trickDescription = data.getString("trick_description");
        String trickName = data.getString("trick_name");

        TextView textViewName = findViewById(R.id.trickInfoName);
        if (trickName != null)
            textViewName.setText(trickName);

        TextView textViewDescription = findViewById(R.id.trickInfoDescription);
        if (trickDescription != null)
            textViewDescription.setText(trickDescription);

        TextView textViewGroups = findViewById(R.id.trickInfoGroups);
        textViewGroups.setText("");
        DatabaseHandler.getInstance(this).getGroups(new DatabaseHandler.Groups() {
            @Override
            public void onGroupsFetch(ArrayList<GroupEntity> groupEntities) {
                for (GroupEntity entity : groupEntities) {
                    for (String id : groupIds) {
                        if (id.equals(entity.groupUUID)){
                            textViewGroups.setText(textViewGroups.getText() + entity.name + " ");
                        }
                    }
                }
            }
        });

        ConstraintLayout background = findViewById(R.id.trickInfoBackground);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0,0);
            }
        });


    }
}