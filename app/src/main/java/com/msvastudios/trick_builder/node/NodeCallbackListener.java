package com.msvastudios.trick_builder.node;

public interface NodeCallbackListener {

    int onMoved(Node node);

    int onInputDropped(Node node);

    int onOutputDragged(Node node,NodeOutput nodeOutput);

}
