package com.msvastudios.trick_builder.node.item;

import android.provider.ContactsContract;

import androidx.annotation.ColorRes;

import com.msvastudios.trick_builder.R;

import java.util.ArrayList;

public enum Type {
    ARRAY_LIST(new DataFlower<ArrayList>(R.color.purple_200)),
    NUMBER(new DataFlower<Integer>(R.color.light_blue_400)),
    ANY(new DataFlower<Object>(R.color.gray_400));

    DataFlower dataFlower;
    Type(DataFlower dataFlower) {
        this.dataFlower = dataFlower;
    }

    public int getColor() {
        return dataFlower.color;
    }

    public DataFlower getDataFlower() {
        return dataFlower;
    }
}
