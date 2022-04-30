package com.msvastudios.trick_builder.generator_editor.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.slider.Slider;
import com.msvastudios.trick_builder.R;

import java.util.List;

public class AlgosAdapter extends RecyclerView.Adapter<AlgosAdapter.ViewHolder> {

        private List<SliderItem> data;

        public AlgosAdapter(List<SliderItem> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.item_shop_card, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            Glide.with(holder.itemView.getContext())
//                    .load(data.get(position).getImage())
//                    .into(holder.image);
            if (position == 1){
                holder.button.setTransitionName("element");

            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView image;
            Button button;
            public ViewHolder(View itemView) {
                super(itemView);
//                image = itemView.findViewById(R.id.image);
                button = itemView.findViewById(R.id.item_button);
            }
        }
    }

