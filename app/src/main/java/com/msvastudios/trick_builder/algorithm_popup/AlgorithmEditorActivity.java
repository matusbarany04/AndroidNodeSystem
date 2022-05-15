package com.msvastudios.trick_builder.algorithm_popup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.generator_editor.items.AlgosAdapter;
import com.msvastudios.trick_builder.generator_editor.items.OnItemClickListener;
import com.msvastudios.trick_builder.generator_editor.items.SliderItem;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.Arrays;

public class AlgorithmEditorActivity extends AppCompatActivity implements OnItemClickListener, DiscreteScrollView.OnItemChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_algorithm_popup);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("buttonName");
        EditText text = findViewById(R.id.editor_text);
        text.setText(name);

        initAlgorithmsAdapter();
    }

    private void initAlgorithmsAdapter() {
        // TODO set images to choose from
        DiscreteScrollView scrollView = findViewById(R.id.picker);
        scrollView.setAdapter(new AlgosAdapter(Arrays.asList(
                new SliderItem(1, "Everyday Candle", "$12.00 USD", R.drawable.delete),
                new SliderItem(2, "Small Porcelain Bowl", "$50.00 USD", R.drawable.ic_launcher_foreground)
                ), this)
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
    public void onItemClicked(SliderItem item) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

    }
}