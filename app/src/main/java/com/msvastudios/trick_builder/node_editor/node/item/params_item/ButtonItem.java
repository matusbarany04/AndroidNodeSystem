package com.msvastudios.trick_builder.node_editor.node.item.params_item;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;

import java.util.ArrayList;
import java.util.Arrays;

import kotlin.Unit;

public class ButtonItem  extends ParameterItem {

        Button button;
        public ButtonItem(Context context, Node parent, int order) {
            super(context, parent, order);
        }

        @Override
        public void init(Context context) {
            super.init(context);


            View.inflate(context, R.layout.button_item, getView());

            button = getView().findViewById(R.id.buttonItem);
            LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(
                    NodeDimensionsCalculator.getInnerNodeWidth() - 180,
                    NodeDimensionsCalculator.nodeItemHeight());
            spinnerParams.setMargins(0, 10, 0, 0);
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, NodeDimensionsCalculator.nodeItemHeight()/3f);
            button.setLayoutParams(spinnerParams);


        }


        public void setOnClickListener(View.OnClickListener listener){
            button.setOnClickListener(listener);
        }
        @Override
        public String getChosenData() {
            return null;
        }

        public void setText(String text){
            button.setText(text);
        }


    }
