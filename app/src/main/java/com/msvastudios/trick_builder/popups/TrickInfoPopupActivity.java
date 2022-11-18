package com.msvastudios.trick_builder.popups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Database;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.utils.FlowLayout;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupEntity;

import org.jetbrains.annotations.Contract;

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


        //TextView textViewGroups = findViewById(R.id.trickInfoGroups);
        //textViewGroups.setText("");
        DatabaseHandler.getInstance(this).getGroups(new DatabaseHandler.Groups() {
            @Override
            public void onGroupsFetch(ArrayList<GroupEntity> groupEntities) {
                for (GroupEntity entity : groupEntities) {
                    for (String id : groupIds) {
                        if (id.equals(entity.groupUUID)){

                            addTrickListGroupItem(entity.name);
                           // textViewGroups.setText(textViewGroups.getText() + entity.name + " ");
                        }
                    }
                }
                FlowLayout belongParent = findViewById(R.id.trickInfoBelongsContainer);
                belongParent.addView(getLayoutInflater().inflate(R.layout.trick_info_group_add, null));

                findViewById(R.id.plus_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO add to another group
                        //TODO change deletion of group so that it will delete the trick only if it has only its group
                    }
                });
            }
        });


        ConstraintLayout background = findViewById(R.id.trickInfoBackground);
        background.setOnClickListener(view -> {
            finish();
            overridePendingTransition(0,0);
        });

        ConstraintLayout popup = findViewById(R.id.trickInfoPopup);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void addTrickListGroupItem(String name) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.trick_info_groups_item, null);
        ((TextView) view.findViewById(R.id.trickInfoGroups)).setText(name);
        FlowLayout belongParent = findViewById(R.id.trickInfoBelongsContainer);
        belongParent.addView(view);
    }
}