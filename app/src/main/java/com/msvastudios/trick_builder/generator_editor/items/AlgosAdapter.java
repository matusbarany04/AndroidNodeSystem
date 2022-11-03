package com.msvastudios.trick_builder.generator_editor.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.algorithm_editor.WheelsAdapter;
import com.msvastudios.trick_builder.utils.sqlite.algorithms.AlgorithmEntity;
import com.msvastudios.trick_builder.utils.wheel.WheelParser;

import java.util.ArrayList;

public class AlgosAdapter extends RecyclerView.Adapter<AlgosAdapter.ViewHolder> {

    OnItemClickListener listener;
    private ArrayList<AlgorithmEntity> data;

    public AlgosAdapter(ArrayList<AlgorithmEntity> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    public ArrayList<AlgorithmEntity> getData() {
        return data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_shop_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 1) {
            holder.image.setTransitionName("element");
        }
        Glide.with(holder.itemView.getContext())
                //WHEN BORED add wheel parser up
                .load(
                        new WheelParser(holder.itemView.getContext()).getWheelById(data.get(position).imageId).getImage()
                )
                .into(holder.image);


        holder.image.setOnClickListener(view -> listener.onItemClicked(data.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
//                image = itemView.findViewById(R.id.image);
            image = itemView.findViewById(R.id.item_button);
        }
    }

}

