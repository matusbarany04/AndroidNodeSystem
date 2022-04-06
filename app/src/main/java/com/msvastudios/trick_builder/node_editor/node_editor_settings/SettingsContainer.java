package com.msvastudios.trick_builder.node_editor.node_editor_settings;

import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.line.Line;

import java.util.zip.Inflater;

public class SettingsContainer {
    Context context;
    LinearLayout container;

    public SettingsContainer(Context context) {
        this.context = context;
        init();
    }



    private void init() {
        container = new LinearLayout(context);
        LinearLayout.LayoutParams contParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(contParams);

        LayoutInflater.from(context).inflate(R.layout.floating_settings, container);


//        Inflater inflater;
//        inflater.inflate(R.layout.custom_button, mLinearLayout, true);


//        FloatingActionButton mainButton = new FloatingActionButton(context);
//        LinearLayout.LayoutParams mainButtonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mainButton.setLayoutParams(mainButtonParams);
//        container.addView(mainButton);

    }

    public LinearLayout getSettingsView() {
        return container;
    }


}
