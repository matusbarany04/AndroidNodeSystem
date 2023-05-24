package com.msvastudios.trick_builder.node_editor.node.item.params_item;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayItem extends ParameterItem {

    ListView listView;
    public ArrayItem(Context context, Node parent, int order) {
        super(context, parent, order);


    }

    @Override
    public void init(Context context) {
        super.init(context);

        View.inflate(context, R.layout.array_list_item, getView());

        listView = getView().findViewById(R.id.listView);
        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(
                NodeDimensionsCalculator.getInnerNodeWidth() - 40, NodeDimensionsCalculator.nodeItemHeight() * getHeight() - 20);
        spinnerParams.setMargins(0, 10, 0, 0);

        listView.setLayoutParams(spinnerParams);

        listView.setAdapter(new ArrayAdapter<String>(context, R.layout.array, new ArrayList<String>(Arrays.asList("dssa", "das"))));

    }

    @Override
    public String getChosenData() {
        return null;
    }


    @Override
    public int getHeight() {
        return  3;
    }


}
