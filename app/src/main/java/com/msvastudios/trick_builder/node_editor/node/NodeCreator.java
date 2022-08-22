package com.msvastudios.trick_builder.node_editor.node;

public interface NodeCreator<Con, Left, Top, Data, Lines, Callback, R> {

    R apply(Con con, Left left , Top top,Data data,Lines lines, Callback c);
}
