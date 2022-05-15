package com.msvastudios.trick_builder.node_editor.io;

import java.util.Objects;
import java.util.function.Function;

public interface NodeCreator<Con, Left,Right, Lines, Callback, R> {

    R apply(Con con, Left left ,Right right, Lines lines, Callback c);
}
