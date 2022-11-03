package com.msvastudios.trick_builder.utils;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.msvastudios.trick_builder.R;

import java.util.ArrayList;
import java.util.function.Function;

public class ListViewAdapter<T> extends BaseAdapter {

    private ArrayList<T> data;
    private final int template;
    private final ItemInflater<T> function;
    public ListViewAdapter(ArrayList<T> data, int template, ItemInflater<T> function) {
        this.data = data;
        this.template= template;
        this.function = function;
    }

    public ArrayList<T> getData() {
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v;

        if(convertView == null) {
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            v =  li.inflate(template,null);
        }else
        {
            v = convertView;
        }

        v = function.inflateItem(v,i, getItem(i));

        return v;
    }

    public interface ItemInflater<T>{
        View inflateItem(View convertView, int position, T item);
    }

}



