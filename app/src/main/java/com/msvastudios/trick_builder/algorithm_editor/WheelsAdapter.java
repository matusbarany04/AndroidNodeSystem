package com.msvastudios.trick_builder.algorithm_editor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.utils.wheel.Wheel;

import java.util.ArrayList;

public class WheelsAdapter extends RecyclerView.Adapter<WheelsAdapter.ViewHolder> {

    int itemHeight;
    private ArrayList<Wheel> data;

    public WheelsAdapter(ArrayList<Wheel> data) {
        this.data = data;
    }

    public Wheel getWheel(int position) {
        return data.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
//        Activity context = (Activity) recyclerView.getContext();
//        Point windowDimensions = new Point();
//        context.getWindowManager().getDefaultDisplay().getSize(windowDimensions);
//        itemHeight = Math.round(windowDimensions.y * 0.6f);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.algos_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(data.get(position).getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_button);
        }

    }
}

