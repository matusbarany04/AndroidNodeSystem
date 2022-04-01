package com.msvastudios.trick_builder.node;

import com.msvastudios.trick_builder.node.item.NodeInput;
import com.msvastudios.trick_builder.node.item.NodeOutput;

public interface NodeCallbackListener {

    int onMoved(Node node);

    int onInputDropped(Node node);

    int onOutputDragged(Node node, NodeOutput nodeOutput);
}
