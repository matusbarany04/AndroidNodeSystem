package com.msvastudios.trick_builder.node_editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;
import com.msvastudios.trick_builder.node_editor.node.NodeManager;
import com.msvastudios.trick_builder.node_editor.node_editor_settings.SettingsContainer;

public class NodeActivity extends AppCompatActivity {
    DisplayMetrics displayMetrics;

    RelativeLayout dragArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);


        getSupportActionBar().hide();

        dragArea = findViewById(R.id.dragArea);

        RelativeLayout canvasLayout = findViewById(R.id.canvasRelativeLayout);
        LinesView linesView = new LinesView(this);
        canvasLayout.addView(linesView);


        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        Log.d("Screen metrics", screenHeight + " " + screenWidth);


        NodeDimensionsCalculator.getStatusBarHeight(this);
        NodeManager nodeManager = new NodeManager(this, linesView, dragArea );

        FloatingActionButton floatingActionButton = findViewById(R.id.ping);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Button clicked!");
                nodeManager.play();
            }
        });

        SettingsContainer container = new SettingsContainer(getApplicationContext());
//        ConstraintLayout nodeRoot = findViewById(R.id.node_root);
    //   nodeRoot.addView(container.getSettingsView());


    }
}