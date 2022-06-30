package com.msvastudios.trick_builder.algorithm_popup;

import android.app.Dialog;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.NodeActivity;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.utils.ListViewAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewNodePopup {
    Dialog dialog;


    public NewNodePopup(Context context, ArrayList<CustomNodes> nodes ) {
        dialog = new Dialog(context);
            dialog.setContentView(R.layout.add_node);


        ListViewAdapter<String> adapter = new ListViewAdapter<String>(new ArrayList<String>(), R.layout.add_node, new ListViewAdapter.ItemInflater<String>() {
            @Override
            public View inflateItem(View convertView, int position, String item) {

                return convertView;
            }
        });
    }

}
