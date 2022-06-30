package com.msvastudios.trick_builder.generator_editor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.algorithm_popup.AlgorithmEditorActivity;
import com.msvastudios.trick_builder.generator_editor.items.AlgosAdapter;
import com.msvastudios.trick_builder.generator_editor.items.OnItemClickListener;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.algorithms.AlgorithmEntity;
import com.msvastudios.trick_builder.node_editor.NodeActivity;
import com.msvastudios.trick_builder.trick_listing.groups.GroupListActivity;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.Objects;

public class GeneratorEditorActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener<AlgosAdapter.ViewHolder>, View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator_editor);

        initTopButtonListeners();

        initAlgorithmsAdapter();

        initBottomMenu();


    }

    private void initBottomMenu() {
        FloatingActionButton addButton = findViewById(R.id.generator_editor_addNewAlgorithm);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(GeneratorEditorActivity.this, AlgorithmEditorActivity.class);
            intent.putExtra("buttonName", "add");
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        FloatingActionButton chooseAndGoBackButton = findViewById(R.id.generator_editor_chooseAlgorithmAndGoBack);
        chooseAndGoBackButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(0, 0);
        });
        FloatingActionButton customizeButton = findViewById(R.id.generator_editor_customizeAlgorithm);
        customizeButton.setOnClickListener(view -> {

            Intent intent = new Intent(GeneratorEditorActivity.this, AlgorithmEditorActivity.class);

            intent.putExtra("buttonName", "save");
            DiscreteScrollView scrollView = findViewById(R.id.picker);
            intent.putExtra("algorithmId",((AlgosAdapter) scrollView.getAdapter()).getData().get(scrollView.getCurrentItem()).nodeNetworkUUID);
            overridePendingTransition(0,0);
            startActivity(intent);
//            Intent intent = new Intent(GeneratorEditorActivity.this, DatabaseRendererActivity.class);
//            startActivity(intent);
        });
    }

    private void initAlgorithmsAdapter() {
        DiscreteScrollView scrollView = findViewById(R.id.picker);

        OnItemClickListener listener = new OnItemClickListener() {
            @Override
            public void onItemClicked(AlgorithmEntity item) {
                Intent intent = new Intent(GeneratorEditorActivity.this, NodeActivity.class);
                intent.putExtra(getString(R.string.NodeActivityExtraId), item.nodeNetworkUUID);
                startActivity(intent);

                overridePendingTransition(0, 0);
            }
        };

        scrollView.setAdapter(new AlgosAdapter(new ArrayList<AlgorithmEntity>(), listener));

        assert DatabaseHandler.getInstance(getApplicationContext()) != null;

        DatabaseHandler.getInstance(getApplicationContext()).getAllAgorithms(new DatabaseHandler.AlgoFinish() {
            @Override
            public void onFetched(ArrayList<AlgorithmEntity> entities) {
                runOnUiThread(()->{
                    scrollView.setAdapter(new AlgosAdapter(entities, listener));
                });

            }
        });

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

    private void initTopButtonListeners() {
        FloatingActionButton backButton = findViewById(R.id.generator_editor_goBack);
        backButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(0, 0);
        });

        FloatingActionButton groupButton = findViewById(R.id.generator_editor_groupsButton);
        groupButton.setOnClickListener(view -> {
            Intent intent = new Intent(GeneratorEditorActivity.this, GroupListActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAlgorithmsAdapter();
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onCurrentItemChanged(@Nullable AlgosAdapter.ViewHolder viewHolder, int adapterPosition) {
        TextView algorithmNameTextView = findViewById(R.id.editor_text);
        DiscreteScrollView scrollView = findViewById(R.id.picker);
        algorithmNameTextView.setText(((AlgosAdapter) Objects.requireNonNull(scrollView.getAdapter())).getData().get(adapterPosition).name);

    }


}