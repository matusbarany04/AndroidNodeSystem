package com.msvastudios.trick_builder.node_editor.node.item.params_item;

import android.content.Context;
import android.widget.LinearLayout;

import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.item.NodeItem;

import java.util.ArrayList;

public abstract class ParameterItem extends NodeItem {


    ArrayList<String> data;

    public ParameterItem(Context context, Node parent, int order) {
        super(context, parent, order);
        init(context);
    }

    public void init(Context context) {
        getView().setOrientation(LinearLayout.HORIZONTAL);
        getView().setGravity(1);
//        getView().setBackgroundColor(Color.BLUE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.topMargin = height * order;
//        params.rightMargin = -NodeDimensionsCalculator.innerNodeMargin(); // /2 ;
        getView().setLayoutParams(params);

    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public abstract String getChosenData();
}
