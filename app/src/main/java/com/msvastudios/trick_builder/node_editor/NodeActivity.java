package com.msvastudios.trick_builder.node_editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinePoint;
import com.msvastudios.trick_builder.popups.NewNodePopup;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;
import com.msvastudios.trick_builder.node_editor.node.NodeManager;
import com.msvastudios.trick_builder.node_editor.activity_components.SettingsContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NodeActivity extends AppCompatActivity {
    DisplayMetrics displayMetrics;
    float x = 1f, y = 1f;
    RelativeLayout dragArea;
    NodeManager nodeManager;
    String sessionId = "myFirstProgram";
    String algoName;
    Dialog dialog;
    NewNodePopup nodePopup;
    private int xDelta, yDelta;
    boolean screenMove = false;
    int dragDeltaX = 0, dragDeltaY = 0;
    LinesView linesView;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);


        this.algoName = getIntent().getStringExtra(getString(R.string.NodeActivityExtraId));
        if (algoName == null) algoName = "smh";
//        getSupportActionBar().hide();
        Log.d("NODEACTIVITY", algoName);

        dragArea = findViewById(R.id.dragArea);

        FrameLayout canvasLayout = findViewById(R.id.canvasRelativeLayout);
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) canvasLayout.getLayoutParams();

         linesView = new LinesView(this);
        canvasLayout.addView(linesView);


        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        Log.d("Screen metrics", screenHeight + " " + screenWidth);


        RelativeLayout overlay_area = findViewById(R.id.overlay_area);
        //TODO move listener elsewhere

        overlay_area.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();

                if (screenMove) {

                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            dragDeltaX = rawX;
                            dragDeltaY = rawY;
                            break;

                        case MotionEvent.ACTION_MOVE:
                            nodeManager.moveAll(-(dragDeltaX - rawX), -(dragDeltaY - rawY));
                            dragDeltaX = rawX;
                            dragDeltaY = rawY;
                            break;
                    }

                }
                return screenMove;
            }
        });
        RelativeLayout relativePoint = findViewById(R.id.overlay_area); // todo add to view

        NodeDimensionsCalculator.getStatusBarHeight(this);
        nodeManager = new NodeManager(this, linesView, dragArea, relativePoint);
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
            x += 0.1f;
            y += 0.1f;
//          nodeManager.scale(x, y);
            dragArea.setScaleX(x);
            dragArea.setScaleY(y);
            linesView.setScaleX(x);
            linesView.setScaleY(y);


//            nodePopup.show();
        });

        container.setClickListenerOnButton(1, view -> {
            // TODO start deletion mode of nodes add deletion of nodes
//          nodeManager.toggleDeletionMode();
            x -= 0.1f;
            y -= 0.1f;
//          nodeManager.scale(x, y);
            dragArea.setScaleX(x);
            dragArea.setScaleY(y);
            linesView.setScaleX(x);
            linesView.setScaleY(y);

        });

        container.setClickListenerOnButton(2, view -> {
            //TODO start move mode
            screenMove = !screenMove;
            if (screenMove) {
                dragArea.setBackgroundResource(R.drawable.nav);
            } else {
                dragArea.setBackground(getApplicationContext().getDrawable(R.drawable.back_node));

            }
//            nodeManager.saveCurrentNodes(algoName);
//            x += 0.1f;
//            y += 0.1f;
//            nodeManager.scale(x, y);
        });
        initAlgorithmArea(0.25f);
        buildNewNodePopup();
        buildDialog();
    }



    private void initAlgorithmArea(float maxZoom){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        float scalar = 4;
        FrameLayout.LayoutParams dragAreaLayoutParams = (FrameLayout.LayoutParams) dragArea.getLayoutParams();
        dragAreaLayoutParams.width = (int) (width * scalar );
        dragAreaLayoutParams.height = (int) (height * scalar );
        dragArea.setLayoutParams(dragAreaLayoutParams);


        FrameLayout.LayoutParams linesViewParams = (FrameLayout.LayoutParams) linesView.getLayoutParams();
        linesViewParams.width = (int) (width * scalar);
        linesViewParams.height = (int) (height * scalar);
        linesViewParams.gravity= Gravity.CENTER;
//        linesView.setBackgroundResource(R.drawable.nav);
        linesView.setLayoutParams(linesViewParams);

//        dragArea.setScaleX(x);
//        dragArea.setScaleY(y);
//        linesView.setScaleX(x);
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
                nodeManager.addNode(node, dragArea.getLeft(),  dragArea.getTop()); // TODO change margin to be dynamic
                nodePopup.hide();
            }
        });

    }
}