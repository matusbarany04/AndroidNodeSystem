package com.msvastudios.trick_builder.algorithm_editor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.generator_editor.items.OnItemClickListener;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.algorithms.AlgorithmEntity;
import com.msvastudios.trick_builder.node_editor.node.item.line.Line;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.utils.wheel.Wheel;
import com.msvastudios.trick_builder.utils.wheel.WheelParser;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;

public class AlgorithmEditorActivity extends AppCompatActivity {
    EditText text;
    String id;
    String oldName = "error";
    String imageId;
    DiscreteScrollView scrollView;
    ArrayList<Wheel> wheelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_algorithm_popup);
        wheelList = new WheelParser(AlgorithmEditorActivity.this).getWheelList();
        String buttonName = getIntent().getStringExtra("buttonName");
        scrollView = findViewById(R.id.picker);
        assert buttonName != null;

        text = findViewById(R.id.editor_text);
        //TODO add right wheen when editing
        if (buttonName.equals("save")) {
            id = getIntent().getStringExtra("algorithmId");
            //goes on when creating new algorithm
            assert id != null;
            findViewById(R.id.algorithm_editor_delete).setVisibility(View.VISIBLE);
            DatabaseHandler.getInstance(getApplicationContext()).getAlgorithmName(id, name -> {
                        runOnUiThread(() -> {
                            text.setText(name);
                        });
                    }
            );
            DatabaseHandler.getInstance(getApplicationContext()).getAlgorithmImageId(id, imageId -> {
                        this.imageId = imageId;
                        boolean success= false;
                        for (int i = 0; i < wheelList.size();i++){
                            if(wheelList.get(i).getId().equals(imageId)){
                                scrollView.scrollToPosition(i);
                                success = true;
                            }
                            break;
                        }
                        if (!success){
                            scrollView.scrollToPosition(0);
                            this.imageId = wheelList.get(0).getId();
                        }
                    }
            );
        }

        initAlgorithmsWheelsAdapter();

        FloatingActionButton backButton = findViewById(R.id.algo_backButton);
        backButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(0, 0);
        });

        RelativeLayout container = findViewById(R.id.newAlgorithmFloatingButtonContainer);
        FloatingActionButton addButton = findViewById(R.id.generator_editor_chooseAlgorithmAndGoBack);
        View.OnClickListener listener = view -> {
            if (buttonName.equals("add")) {
                addAlgorithm();
            } else {
                saveAlgorithm();
            }
        };
        container.setOnClickListener(listener);
        addButton.setOnClickListener(listener);
    }

    public void saveAlgorithm() {
        if (!oldName.equals(text.getText().toString())) {
            DatabaseHandler.getInstance(getApplicationContext()).getAlgorithmEntity(id, (result, entity) -> {
                if (result == 0) { // OK
                    if (imageId != null) //TODO Not checking if its valid but it should be fine
                        entity.imageId = imageId;

                    entity.name = text.getText().toString();
                    DatabaseHandler.getInstance(getApplicationContext()).updateAlgorithmEntity((status) -> {
                        if (status == 0) {
                            finish();
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "Error happened", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }, entity);
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(), "Couldn't find algorithm", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }
    }

    public void addAlgorithm() {
        DiscreteScrollView scrollView = findViewById(R.id.picker);
        String imageId = ((WheelsAdapter) scrollView.getAdapter()).getWheel(scrollView.getCurrentItem()).getId();

        DatabaseHandler.getInstance(getApplicationContext()).createNewAlgorithmEntity(text.getText().toString(), imageId, (Integer result, AlgorithmEntity entity) -> {
            if (result == 0) {// OK
                DatabaseHandler.getInstance(getApplicationContext()).insertAlgorithm(entity, new ArrayList<Line>(), new ArrayList<Node>());
                finish();
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Algorithm already exists", Toast.LENGTH_SHORT).show();
                });
                System.err.println("Algorithm entity exists");
            }
        });
    }

    private void initAlgorithmsWheelsAdapter() {

        scrollView.setAdapter(new WheelsAdapter(wheelList));

        scrollView.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                imageId = wheelList.get(adapterPosition).getId();
            }
        });
        scrollView.setItemTransitionTimeMillis(150);
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.7f)
                .build());


    }

}