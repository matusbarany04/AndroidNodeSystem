package com.msvastudios.trick_builder.algorithm_popup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.generator_editor.items.AlgosAdapter;
import com.msvastudios.trick_builder.generator_editor.items.OnItemClickListener;
import com.msvastudios.trick_builder.generator_editor.items.AlgorithmItem;
import com.msvastudios.trick_builder.io_utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.io_utils.sqlite.algorithms.AlgorithmEntity;
import com.msvastudios.trick_builder.node_editor.line.Line;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.Arrays;

public class AlgorithmEditorActivity extends AppCompatActivity implements OnItemClickListener, DiscreteScrollView.OnItemChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_algorithm_popup);

        String name = getIntent().getStringExtra("buttonName");
        EditText text = findViewById(R.id.editor_text);
        text.setText(name);

        initAlgorithmsAdapter();

        FloatingActionButton backButton = findViewById(R.id.algo_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0,0);
            }
        });

        RelativeLayout container = findViewById(R.id.newAlgorithmFloatingButtonContainer);
        FloatingActionButton addButton = findViewById(R.id.generator_editor_chooseAlgorithmAndGoBack);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("click!");

                DatabaseHandler.getInstance(getApplicationContext()).createNewAlgorithmEntity(text.getText().toString(), new DatabaseHandler.NewAlgorithm() {
                    @Override
                    public void onFinishedNewAlgorithm(Integer result, AlgorithmEntity entity) {
                        if(result== 0) // OK
                            DatabaseHandler.getInstance(getApplicationContext()).insertAlgorithm(entity, new ArrayList<Line>(), new ArrayList<Node>());
                        else
                            System.err.println("Algorithm entity exists");

                    }
                });

            }
        };

        container.setOnClickListener(listener);
        addButton.setOnClickListener(listener);
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