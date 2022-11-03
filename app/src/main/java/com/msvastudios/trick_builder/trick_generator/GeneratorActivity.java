package com.msvastudios.trick_builder.trick_generator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.generator_editor.GeneratorEditorActivity;
import com.msvastudios.trick_builder.node_editor.node.RunnerCallback;
import com.msvastudios.trick_builder.node_editor.run.NodeRunner;
import com.msvastudios.trick_builder.utils.shared_prefs.SharedPreferencesAPI;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.algorithms.AlgorithmEntity;
import com.msvastudios.trick_builder.utils.wheel.WheelParser;

public class GeneratorActivity extends AppCompatActivity implements RunnerCallback {
    SharedPreferencesAPI prefsInstance;
    NodeRunner nodeRunner;
    boolean canGenerate = false;
    TextView textView;
    ImageView circleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        circleButton = findViewById(R.id.generator_button);


        circleButton.setTransitionName("element");
        textView = findViewById(R.id.generator_text);
        init();
        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canGenerate) {
                    nodeRunner.play(GeneratorActivity.this);
                } else {
                    textView.setText("error happened");
                }
            }
        });
        circleButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(GeneratorActivity.this, GeneratorEditorActivity.class);

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        GeneratorActivity.this, Pair.create(circleButton, "element"), Pair.create(textView, "text")
                );

                startActivity(intent, optionsCompat.toBundle());
                overridePendingTransition(0, 0);
                return true;
            }
        });

    }


    @Override
    protected void onResume() {
        init();
        super.onResume();
    }

    private void init() {

        prefsInstance = SharedPreferencesAPI.getInstance(getApplicationContext());

        String chosenAlgoId = prefsInstance.get(SharedPreferencesAPI.CHOSEN_ALGORITHM);
        if (chosenAlgoId == null) { //TODO check if algorithm id still exists
            textView.setText("Long press to choose an algorithm");
            canGenerate = false;
        } else {
            textView.setText(chosenAlgoId);
            nodeRunner = new NodeRunner(this, chosenAlgoId);
            //TODO make wheel image search faster without lag
            DatabaseHandler.build(GeneratorActivity.this).getAlgorithmEntity(chosenAlgoId, new DatabaseHandler.Algorithm() {
                @Override
                public void onAlgorithm(Integer result, AlgorithmEntity entity) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide
                                    .with(GeneratorActivity.this)
                                    .load(
                                            new WheelParser(GeneratorActivity.this)
                                                    .getWheelById(entity.imageId)
                                                    .getImage())
                                    .into(circleButton);
                        }
                    });


                }
            });
            canGenerate = true;
        }

    }

    @Override
    public void finished(String data) {
        textView.setText(data);
    }
}