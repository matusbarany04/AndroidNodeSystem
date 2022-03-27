package com.msvastudios.trick_builder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;

import com.msvastudios.trick_builder.line.LinesView;
import com.msvastudios.trick_builder.node.NodeDimensionsCalculator;
import com.msvastudios.trick_builder.node.NodeManager;

public class MainActivity extends AppCompatActivity {

    DisplayMetrics displayMetrics;

    RelativeLayout dragArea;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
    }

}