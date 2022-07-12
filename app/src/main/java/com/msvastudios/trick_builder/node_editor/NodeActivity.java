package com.msvastudios.trick_builder.node_editor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.popups.NewNodePopup;
import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;
import com.msvastudios.trick_builder.node_editor.node.NodeManager;
import com.msvastudios.trick_builder.node_editor.activity_components.SettingsContainer;

import java.util.ArrayList;
import java.util.Arrays;

public class NodeActivity extends AppCompatActivity {
    DisplayMetrics displayMetrics;

    RelativeLayout dragArea;
    NodeManager nodeManager;
    String sessionId = "myFirstProgram";
    String algoName;
    Dialog dialog;
    NewNodePopup nodePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);


        this.algoName = getIntent().getStringExtra(getString(R.string.NodeActivityExtraId));
        if (algoName == null) algoName = "smh";
//        getSupportActionBar().hide();
        Log.d("NODEACTIVITY", algoName);

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
        nodeManager = new NodeManager(this, linesView, dragArea);
        nodeManager.loadSavedNodeNetwork(algoName);

        FloatingActionButton floatingActionButton = findViewById(R.id.ping);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nodeManager.play(null);
            }
        });


        FloatingActionButton nodeBackButton = findViewById(R.id.nodeBackButton);
        nodeBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

            }
        });
        SettingsContainer container = new SettingsContainer(getApplicationContext());

        LinearLayout nodeRoot = findViewById(R.id.node_root);
        nodeRoot.addView(container.getSettingsView());


        // retrackable menu buttons
        container.setClickListenerOnButton(0, view -> {
            //call add activity
            //with some callback
//            nodeManager.addNode(RepeaterNode.class, 200, 200);
            nodePopup.show();
        });

        container.setClickListenerOnButton(1, view -> {
            // TODO start deletion mode of nodes add deletion of nodes
            nodeManager.toggleDeletionMode();
        });

        container.setClickListenerOnButton(2, view -> {
//                nodeManager.play();
            nodeManager.saveCurrentNodes(algoName);
        });

        buildNewNodePopup();
        buildDialog();
    }

    public void buildDialog() {
        dialog = new Dialog(NodeActivity.this);
        dialog.setContentView(R.layout.dialog_yes_no);
        Button btn_yes = dialog.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nodeManager.saveCurrentNodes(algoName);
                dialog.dismiss();
                finish();
                overridePendingTransition(0, 0);
            }
        });
        Button btn_no = dialog.findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                overridePendingTransition(0, 0);
            }
        });

    }

    private void buildNewNodePopup() {
        nodePopup = new NewNodePopup(NodeActivity.this, new ArrayList<CustomNodes>(Arrays.asList(CustomNodes.values())));

        nodePopup.setOnItemClickListener(new NewNodePopup.Popup() {
            @Override
            public void onNodeItemClickListener(int position, CustomNodes node) {
                System.out.println("adding new node! called : " + node.getType());
                nodeManager.addNode(node, 200, 200); // TODO change margin to be dynamic
                nodePopup.hide();
            }
        });

    }
}