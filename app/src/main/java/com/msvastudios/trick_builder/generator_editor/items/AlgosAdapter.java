package com.msvastudios.trick_builder.generator_editor.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msvastudios.trick_builder.R;

import java.util.List;

public class AlgosAdapter extends RecyclerView.Adapter<AlgosAdapter.ViewHolder> {

    OnItemClickListener listener;
    private List<AlgorithmItem> data;

    public AlgosAdapter(List<AlgorithmItem> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
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
//            Glide.with(holder.itemView.getContext())
//                    .load(data.get(position).getImage())
//                    .into(holder.image);
        if (position == 1) {
            holder.button.setTransitionName("element");
        }

//        holder.button.setText(data.get(position).getName());
        holder.button.setOnClickListener(view -> listener.onItemClicked(data.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView button;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
//                image = itemView.findViewById(R.id.image);
            button = itemView.findViewById(R.id.item_button);

        }
    }
}

