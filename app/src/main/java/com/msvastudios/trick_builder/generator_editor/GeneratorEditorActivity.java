package com.msvastudios.trick_builder.generator_editor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.generator_editor.items.AlgosAdapter;
import com.msvastudios.trick_builder.generator_editor.items.SliderItem;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.Arrays;
import java.util.List;

public class GeneratorEditorActivity extends AppCompatActivity  implements DiscreteScrollView.OnItemChangedListener<AlgosAdapter.ViewHolder>,View.OnClickListener{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator_editor);

        getWindow().getSharedElementEnterTransition().setDuration(1000);
        getWindow().getSharedElementReturnTransition().setDuration(1000)
                .setInterpolator(new DecelerateInterpolator());

//        currentItemName = findViewById(R.id.item_name);
//        currentItemPrice = findViewById(R.id.item_price);
//        rateItemButton = findViewById(R.id.item_btn_rate);
//        shop = Shop.get();
//        data = shop.getData();

        DiscreteScrollView scrollView = findViewById(R.id.picker);
        scrollView.setAdapter( InfiniteScrollAdapter.wrap(new AlgosAdapter(Arrays.asList(
                new SliderItem(1, "Everyday Candle", "$12.00 USD", R.drawable.delete),
                new SliderItem(2, "Small Porcelain Bowl", "$50.00 USD", R.drawable.ic_launcher_foreground),
                new SliderItem(3, "Favourite Board", "$265.00 USD", R.drawable.delete),
                new SliderItem(4, "Earthenware Bowl", "$18.00 USD", R.drawable.play),
                new SliderItem(5, "Porcelain Dessert Plate", "$36.00 USD", R.drawable.plus),
                new SliderItem(6, "Detailed Rolling Pin", "$145.00 USD", R.drawable.settings)))));

        scrollView.addOnItemChangedListener(this);


        scrollView.setItemTransitionTimeMillis(150);
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
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



}