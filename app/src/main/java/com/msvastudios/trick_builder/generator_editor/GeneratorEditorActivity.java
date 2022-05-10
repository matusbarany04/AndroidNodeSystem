package com.msvastudios.trick_builder.generator_editor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.generator_editor.items.AlgosAdapter;
import com.msvastudios.trick_builder.generator_editor.items.OnItemClickListener;
import com.msvastudios.trick_builder.generator_editor.items.SliderItem;
import com.msvastudios.trick_builder.node_editor.NodeActivity;
import com.msvastudios.trick_builder.trick_listing.groups.GroupListActivity;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.Arrays;
import java.util.List;

public class GeneratorEditorActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener<AlgosAdapter.ViewHolder>,View.OnClickListener, OnItemClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator_editor);

        FloatingActionButton backButton = findViewById(R.id.generator_editor_backButton);
        backButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(0,0);
        });

        FloatingActionButton groupButton = findViewById(R.id.generator_editor_groupsButton);
        groupButton.setOnClickListener(view -> {
            Intent intent = new Intent(GeneratorEditorActivity.this, GroupListActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        });

//        getWindow().getSharedElementEnterTransition().setDuration(1000);
//        getWindow().getSharedElementReturnTransition().setDuration(1000)
//                .setInterpolator(new DecelerateInterpolator());

        DiscreteScrollView scrollView = findViewById(R.id.picker);
        scrollView.setAdapter(new AlgosAdapter(Arrays.asList(
                        new SliderItem(1, "Everyday Candle", "$12.00 USD", R.drawable.delete),
                        new SliderItem(2, "Small Porcelain Bowl", "$50.00 USD", R.drawable.ic_launcher_foreground)
                ),this

            )
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

//        onItemChanged(data.get(0));
//        findViewById(R.id.item_btn_rate).setOnClickListener(this);
//        findViewById(R.id.item_btn_buy).setOnClickListener(this);
//        findViewById(R.id.item_btn_comment).setOnClickListener(this);
//
//        findViewById(R.id.home).setOnClickListener(this);
//        findViewById(R.id.btn_smooth_scroll).setOnClickListener(this);
//        findViewById(R.id.btn_transition_time).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.item_btn_rate:
//                int realPosition = infiniteAdapter.getRealPosition(itemPicker.getCurrentItem());
//                Item current = data.get(realPosition);
//                shop.setRated(current.getId(), !shop.isRated(current.getId()));
//                changeRateButtonState(current);
//                break;
//            case R.id.home:
//                finish();
//                break;
//            case R.id.btn_transition_time:
//                DiscreteScrollViewOptions.configureTransitionTime(itemPicker);
//                break;
//            case R.id.btn_smooth_scroll:
//                DiscreteScrollViewOptions.smoothScrollToUserSelectedPosition(itemPicker, v);
//                break;
//            default:
//                showUnsupportedSnackBar();
//                break;
//        }
    }

    private void onItemChanged(SliderItem  item) {
//        currentItemName.setText(item.getName());
//        currentItemPrice.setText(item.getPrice());
        changeRateButtonState(item);
    }

    private void changeRateButtonState(SliderItem item) {
//        if (shop.isRated(item.getId())) {
//            rateItemButton.setImageResource(R.drawable.ic_star_black_24dp);
//            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopRatedStar));
//        } else {
//            rateItemButton.setImageResource(R.drawable.ic_star_border_black_24dp);
//            rateItemButton.setColorFilter(ContextCompat.getColor(this, R.color.shopSecondary));
//        }
    }

    @Override
    public void onCurrentItemChanged(@Nullable AlgosAdapter.ViewHolder viewHolder, int adapterPosition) {
//        int positionInDataSet = infiniteAdapter.getRealPosition(adapterPosition);
//        onItemChanged(data.get(positionInDataSet));
    }

    private void showUnsupportedSnackBar() {
//        Snackbar.make(itemPicker, R.string.dsv_ex_msg_dont_set_lm, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClicked(SliderItem item) {
        Intent intent = new Intent(GeneratorEditorActivity.this, NodeActivity.class);
        startActivity(intent);

        overridePendingTransition(0,0);
    }
}