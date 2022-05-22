package com.msvastudios.trick_builder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.msvastudios.trick_builder.node_editor.NodeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent( getApplicationContext() , NodeActivity.class);
        startActivity(intent);




    }

}