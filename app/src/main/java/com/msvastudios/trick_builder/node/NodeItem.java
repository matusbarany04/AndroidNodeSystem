package com.msvastudios.trick_builder.node;

import android.content.Context;
import android.widget.RelativeLayout;

import java.util.UUID;

public abstract class NodeItem {

    static String generateId() {
        return UUID.randomUUID().toString();
    }

    boolean hasInput(){return true;}

    boolean hasOutput(){return true;}

    String getID(){return "hola";}

}
