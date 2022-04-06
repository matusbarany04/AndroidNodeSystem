package com.msvastudios.trick_builder.node_editor.node.item.params_item;

import android.content.Context;

import com.msvastudios.trick_builder.node_editor.node.Node;

public class SliderItem extends ParameterItem {

    public SliderItem(Context context, Node parent, int order) {
        super(context, parent, order);
    }

    @Override
    public String getChosenData() {
        return null;
    }
}
