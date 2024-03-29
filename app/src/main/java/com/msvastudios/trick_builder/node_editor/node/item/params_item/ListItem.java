package com.msvastudios.trick_builder.node_editor.node.item.params_item;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;

import java.util.ArrayList;

public class ListItem extends ParameterItem {
    Spinner spinner;
    AdapterView.OnItemSelectedListener listener;
    ArrayAdapter<CharSequence> adapter;
    ArrayList<String> listData;
    Context context;
    private final Handler handler;

    public ListItem(Context context, Node parent, int order) {
        super(context, parent, order);
        this.context = context;
        handler = new Handler(context.getMainLooper());
        spinner = (Spinner) getView().findViewById(R.id.param_spinner);
        //Create an ArrayAdapter using the string array and a default spinner layout
        //adapter = ArrayAdapter.createFromResource(context,
        //R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item);
        //ArrayAdapter.createFromResource(context,
        //R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void setSpinnerItem(int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(i);
            }
        });
    }

    public void setSpinnerItem(String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(adapter.getPosition(data));
            }
        });
    }

    public void setListener(AdapterView.OnItemSelectedListener listener) {
        this.listener = listener;
        spinner.setOnItemSelectedListener(listener);
    }

    public void setList(ArrayList<String> data) {

        this.listData = data;
        runOnUiThread(() -> {
            adapter.clear();
            adapter.addAll(listData);
        });
    }

    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }

    @Override
    public String getChosenData() {
        return (String) spinner.getSelectedItem();
    }

    @Override
    public void init(Context context) {
        super.init(context);

        View.inflate(context, R.layout.view_node_list, getView());

        spinner = getView().findViewById(R.id.param_spinner);
        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(
                NodeDimensionsCalculator.getInnerNodeWidth() - 40, NodeDimensionsCalculator.nodeItemHeight() - 20);
        spinnerParams.setMargins(0, 10, 0, 0);

        spinner.setLayoutParams(spinnerParams);
    }
}
