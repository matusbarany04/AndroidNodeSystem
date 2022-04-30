package com.msvastudios.trick_builder.trick_generator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.generator_editor.GeneratorEditorActivity;

public class GeneratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);


        Button circleButton = findViewById(R.id.generator_button);
        circleButton.setTransitionName("element");
        TextView textView = findViewById(R.id.generator_text);

        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneratorActivity.this, GeneratorEditorActivity.class);

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        GeneratorActivity.this,Pair.create( circleButton, "element"), Pair.create( textView, "text")
                );

                startActivity(intent, optionsCompat.toBundle());

//                overridePendingTransition(0, 0);
            }
        });
    }
}