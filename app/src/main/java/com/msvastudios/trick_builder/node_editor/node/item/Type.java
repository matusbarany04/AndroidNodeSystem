package com.msvastudios.trick_builder.node_editor.node.item;

import com.msvastudios.trick_builder.R;

import java.util.ArrayList;
import java.util.Arrays;

public enum Type {
    ARRAY_LIST(new DataFlower<ArrayList>(R.color.purple_200)),
    NUMBER(new DataFlower<Integer>(R.color.light_blue_400)),
    STRING(new DataFlower<String>(R.color.teal_200)),
    ANY(new DataFlower<Object>(R.color.purple_500));

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

    public Object decode(String data){
        switch (this) {
            case ARRAY_LIST:
                return (Object) new ArrayList<String>(Arrays.asList(data.split("œ")));
            case NUMBER:
                return (Object) Integer.valueOf(data);
            case STRING:
                return data;
            case ANY:
                return data;
            default: throw new IllegalStateException();
        }
    }

    public String encode(Object data){
        switch (this) {
            case ARRAY_LIST:
                return String.join("œ", (ArrayList<String>) data);
            case NUMBER:
                return String.valueOf(((Integer) data).intValue());
            case STRING:
                return (String) data;
            case ANY:
                return (String) data;
            default: throw new IllegalStateException();
        }
    }
}
