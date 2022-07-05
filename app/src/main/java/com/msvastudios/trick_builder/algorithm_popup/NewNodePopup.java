package com.msvastudios.trick_builder.algorithm_popup;

import android.app.Dialog;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.node_editor.NodeActivity;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.utils.ListViewAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewNodePopup {
    Dialog dialog;
    Popup callback;

    public NewNodePopup(Context context, ArrayList<CustomNodes> nodes) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_node);

        ListView nodeListView = dialog.findViewById(R.id.nodes);

        ArrayList<String> nodeNames = new ArrayList<>();
        for (CustomNodes node : nodes) {
            nodeNames.add(node.getType());
        }

        ListViewAdapter<String> adapter = new ListViewAdapter<String>(nodeNames, R.layout.add_node_item, new ListViewAdapter.ItemInflater<String>() {
            @Override
            public View inflateItem(View convertView, int position, String item) {
                TextView textView = convertView.findViewById(R.id.nodeText);
                textView.setText(nodes.get(position).getType());

                return convertView;
            }
        });

        nodeListView.setAdapter(adapter);

        nodeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                callback.onNodeItemClickListener(i, nodes.get(i));
            }
        });

    }

    public void show(){
        dialog.show();
    }

    public void hide(){
        dialog.dismiss();
    }


    public void setOnItemClickListener(Popup callback) {
        this.callback = callback;
    }

    public interface Popup {
        void onNodeItemClickListener(int position, CustomNodes node);
    }

}
