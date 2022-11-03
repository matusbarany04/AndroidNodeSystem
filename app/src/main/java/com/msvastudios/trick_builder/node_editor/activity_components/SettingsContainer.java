package com.msvastudios.trick_builder.node_editor.activity_components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.generator_editor.items.OnItemClickListener;

import java.util.ArrayList;

public class SettingsContainer {
    Context context;
    RelativeLayout container;
    ArrayList<ImageButton> buttonList;
    ImageView background;
    boolean closed = false;
    int buttonSize = 160;
    int buttonMargin = 10;

    public SettingsContainer(Context context) {
        this.context = context;
        buttonList = new ArrayList<>();
        init();
    }

    private void init() {
        container = new RelativeLayout(context);
        LinearLayout.LayoutParams contParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        background = new ImageView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(buttonSize, buttonSize * 4 + buttonMargin * 3);
        background.setLayoutParams(params);
        background.setBackgroundResource(R.drawable.settings_background);
        container.addView(background);

        container.setLayoutParams(contParams);
        buttonList.add(addButton(1, R.drawable.plus));
        buttonList.add(addButton(2, R.drawable.delete));
        buttonList.add(addButton(3, R.drawable.move));

        addButton(0, R.drawable.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate();
            }
        });

    }

    public void animate() {
        int lastDuration = 500;
        int firstDuration = lastDuration / buttonList.size();


        int i = 1;
        int state = closed ? 0 : -1;
        if (!closed) {
            background.animate()
                    .translationY(-(buttonList.size() * (buttonSize + buttonMargin)) / 2)
                    .scaleX(0.5f)
                    .scaleY(buttonSize * 1.0f / ((buttonSize + buttonMargin) * 4))
                    .setDuration(lastDuration).start();
//            background.animate()
//                    .setDuration(200)
//                    .setStartDelay(lastDuration - firstDuration)
//                    .start();
        } else {
            background.animate()
                    .translationY(0)
                    .scaleX(1f)
                    .scaleY(1)
                    .setDuration(lastDuration)
                    .start();
        }

        for (ImageButton button : buttonList) {
            button.animate()
                    .translationY(state * i * (buttonSize + buttonMargin))
                    .setDuration(((long) firstDuration * i))
                    .setStartDelay(-1 * state * (lastDuration - ((long) firstDuration * i))).start();
            i++;
        }

        closed = !closed;

    }


    public RelativeLayout getSettingsView() {
        return container;
    }

    private ImageButton addButton(int order, @DrawableRes int image) {

        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.settings_button);
        button.setImageResource(image);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
        params.topMargin = order * (buttonSize + buttonMargin);
        button.setLayoutParams(params);

        container.addView(button);

        return button;
    }
    public void setClickListenerOnButton(int pos , View.OnClickListener listener){
        buttonList.get(pos).setOnClickListener(listener);
    }
}