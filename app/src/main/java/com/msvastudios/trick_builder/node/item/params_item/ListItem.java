package com.msvastudios.trick_builder.node.item.params_item;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeDimensionsCalculator;

public class ListItem extends ParameterItem {
    Spinner spinner;
    public ListItem(Context context, Node parent, int order) {
        super(context, parent, order);


        spinner = (Spinner) getView().findViewById(R.id.param_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }

    @Override
    public String getChosenData() {
        return (String) spinner.getSelectedItem();
    }

    @Override
    public void init(Context context) {
        super.init(context);

        View.inflate(context, R.layout.view_node_list, getView());


        Spinner spinner = getView().findViewById(R.id.param_spinner);
        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(
                NodeDimensionsCalculator.getInnerNodeWidth()-40,NodeDimensionsCalculator.nodeItemHeight() - 20);
        spinnerParams.setMargins(0,10,0,0);

        spinner.setLayoutParams(spinnerParams);
    }


}
