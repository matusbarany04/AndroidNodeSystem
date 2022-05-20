package com.msvastudios.trick_builder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.io_utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.io_utils.sqlite.algorithms.Algorithm;
import com.msvastudios.trick_builder.node_editor.NodeActivity;
import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;
import com.msvastudios.trick_builder.node_editor.node.NodeManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent( getApplicationContext() , NodeActivity.class);
        startActivity(intent);




    }

}