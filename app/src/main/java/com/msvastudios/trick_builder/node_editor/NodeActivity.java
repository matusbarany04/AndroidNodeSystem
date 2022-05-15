package com.msvastudios.trick_builder.node_editor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;
import com.msvastudios.trick_builder.node_editor.node.NodeManager;
import com.msvastudios.trick_builder.node_editor.activity_components.SettingsContainer;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.RepeaterNode;

public class NodeActivity extends AppCompatActivity {
    DisplayMetrics displayMetrics;

    RelativeLayout dragArea;

    String sessionId = "myFirstProgram";
    String algoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);

        this.algoName = getIntent().getStringExtra(getString(R.string.NodeActivityExtraId));
        if (algoName == null) algoName = "smh";
//        getSupportActionBar().hide();

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
        NodeManager nodeManager = new NodeManager(this, linesView, dragArea);
        nodeManager.loadSavedNodeNetwork(algoName);

        FloatingActionButton floatingActionButton = findViewById(R.id.ping);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Button clicked!");
                nodeManager.play();
            }
        });


        FloatingActionButton nodeBackButton = findViewById(R.id.nodeBackButton);
        nodeBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                overridePendingTransition(0,0);
            }
        });
        SettingsContainer container = new SettingsContainer(getApplicationContext());

         LinearLayout nodeRoot = findViewById(R.id.node_root);
         nodeRoot.addView(container.getSettingsView());


        // retrackable menu buttons
        container.setClickListenerOnButton(0, view -> {
            //call add activity
            //with some callback
            nodeManager.addNode(RepeaterNode.class, 200,200);
        });

        container.setClickListenerOnButton(1, view -> {
            // TODO start deletion mode of nodes add deletion of nodes
        });

        container.setClickListenerOnButton(2, view -> {
//                nodeManager.play();
            nodeManager.saveCurrentNodes(algoName);
        });


    }
}