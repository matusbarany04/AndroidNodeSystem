package com.msvastudios.trick_builder.node_editor.node.item.params_item;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;

import java.util.ArrayList;

import kotlin.Unit;

public class EditTextItem extends ParameterItem{
    EditText text;
    public EditTextItem(Context context, Node parent, int order) {
        super(context, parent, order);
    }

    @Override
    public String getChosenData() {
        return text.getText().toString();
    }


    @Override
    public void init(Context context) {
        super.init(context);

        View.inflate(context, R.layout.view_node_edit_text, getView());

        text = getView().findViewById(R.id.param_edit_text);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                NodeDimensionsCalculator.getInnerNodeWidth() - 40, NodeDimensionsCalculator.nodeItemHeight());
//        textParams.setMargins(0, 10, 0, 0);
        text.setLayoutParams(textParams);
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, NodeDimensionsCalculator.nodeItemHeight()/2f-(NodeDimensionsCalculator.nodeItemHeight()*0.15f));

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listener.changed(editable.toString());
            }
        });
    }
}
