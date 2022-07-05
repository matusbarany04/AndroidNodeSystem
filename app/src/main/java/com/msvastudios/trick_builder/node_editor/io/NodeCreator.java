package com.msvastudios.trick_builder.node_editor.io;

public interface NodeCreator<Con, Left, Top, Lines, Callback, R> {

    R apply(Con con, Left left , Top top, Lines lines, Callback c);
}
