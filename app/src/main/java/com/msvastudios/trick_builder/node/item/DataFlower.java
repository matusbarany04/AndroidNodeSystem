package com.msvastudios.trick_builder.node.item;

import androidx.annotation.ColorRes;

public class DataFlower<T> {

    int color;
    public DataFlower(@ColorRes int color){
        this.color = color;
    }

    public T getType(){
        return (T) new Object();
    }

}
