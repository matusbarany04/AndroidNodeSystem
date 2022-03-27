package com.msvastudios.trick_builder.node;

import android.content.Context;
import android.widget.RelativeLayout;

import java.util.UUID;

public interface NodeItem {

    static String generateId() {
        return UUID.randomUUID().toString();
    }

    boolean hasInput();

    boolean hasOutput();

    String getID();

}
