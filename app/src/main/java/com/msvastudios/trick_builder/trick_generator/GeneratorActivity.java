package com.msvastudios.trick_builder.trick_generator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.generator_editor.GeneratorEditorActivity;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.run.NodeRunner;
import com.msvastudios.trick_builder.utils.shared_prefs.SharedPreferencesAPI;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;

public class GeneratorActivity extends AppCompatActivity implements RunnerCallback {
    SharedPreferencesAPI prefsInstance;
    NodeRunner nodeRunner;
    boolean canGenerate = false;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        Button circleButton = findViewById(R.id.generator_button);
        circleButton.setTransitionName("element");
        textView = findViewById(R.id.generator_text);
        init();
        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canGenerate){
                    nodeRunner.play(GeneratorActivity.this);
                }else{
                    textView.setText("error happened");
                }
            }
        });
        circleButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(GeneratorActivity.this, GeneratorEditorActivity.class);

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        GeneratorActivity.this,Pair.create( circleButton, "element"), Pair.create( textView, "text")
                );

                startActivity(intent, optionsCompat.toBundle());
//                overridePendingTransition(0, 0);
                return true;
            }
        });

    }


    @Override
    protected void onResume() {
        init();
        super.onResume();
    }

    private void init(){

        prefsInstance = SharedPreferencesAPI.getInstance(getApplicationContext());

        String chosenAlgoId = prefsInstance.get(SharedPreferencesAPI.CHOSEN_ALGORITHM);
        if (chosenAlgoId == null) { //TODO check if algorithm id still exists
            textView.setText("Long press to choose an algorithm");
            canGenerate = false;
        }else {
            textView.setText(chosenAlgoId);
            nodeRunner= new NodeRunner(this, chosenAlgoId);
            canGenerate = true;
        }

    }

    @Override
    public void finished(String data) {
        textView.setText(data);
    }
}