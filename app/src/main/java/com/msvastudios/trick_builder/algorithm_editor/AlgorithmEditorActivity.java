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
import com.msvastudios.trick_builder.generator_editor.items.AlgosAdapter;
import com.msvastudios.trick_builder.generator_editor.items.OnItemClickListener;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.algorithms.AlgorithmEntity;
import com.msvastudios.trick_builder.node_editor.line.Line;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;

public class AlgorithmEditorActivity extends AppCompatActivity implements OnItemClickListener, DiscreteScrollView.OnItemChangedListener {
    EditText text;
    String id;
    String oldName = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_algorithm_popup);

        String buttonName = getIntent().getStringExtra("buttonName");

        assert buttonName != null;

        text = findViewById(R.id.editor_text);

        id = getIntent().getStringExtra("algorithmId");
        //goes on when creating new algorithm
        assert id != null;

        DatabaseHandler.getInstance(getApplicationContext()).getAlgorithmName(id, name -> {
                    runOnUiThread(() -> {
                        text.setText(name);
                    });
                }
        );

        initAlgorithmsAdapter();

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
        DatabaseHandler.getInstance(getApplicationContext()).createNewAlgorithmEntity(text.getText().toString(), (Integer result, AlgorithmEntity entity) -> {
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

    private void initAlgorithmsAdapter() {
        // TODO set images to choose from
        DiscreteScrollView scrollView = findViewById(R.id.picker);
        //TODO xxx load algos
        scrollView.setAdapter(new AlgosAdapter(new ArrayList<AlgorithmEntity>(), this)
        );
        scrollView.addOnItemChangedListener(this);
        scrollView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        scrollView.setItemTransitionTimeMillis(150);
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.7f)
                .build());


    }


    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

    }

    @Override
    public void onItemClicked(AlgorithmEntity item) {

    }
}