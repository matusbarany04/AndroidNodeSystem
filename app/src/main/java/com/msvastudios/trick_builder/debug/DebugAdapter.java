package com.msvastudios.trick_builder.debug;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.trick_listing.tricks.Trick;

import java.util.ArrayList;

public class DebugAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> text;
        LayoutInflater inflater;

        public DebugAdapter(Context applicationContext, ArrayList<String> text) {
            this.context = applicationContext;
            this.text = text;
            inflater = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return text.size();
        }

        @Override
        public String getItem(int i) {
            return text.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.trick_item, viewGroup, false);

//        view = inflter.inflate(R.layout.grid_item, null);//set layout for displaying items
//        ImageView icon = (ImageView) view.findViewById(R.id.icon);//get id for image view
//        icon.setImageResource(flags[i]);//set image of the item’s

            TextView textView = view.findViewById(R.id.grid_item_text);
            textView.setText(text.get(i));
//        viewGroup.addView(text.get(i).getView());
            return view;
        }
    }


