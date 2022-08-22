package com.msvastudios.trick_builder.node_editor.node;

import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;

public interface NodeCallbackListener {

    int onMoved(Node node);

    int onInputDropped(Node node);

    int onOutputDragged(Node node, NodeOutput nodeOutput);
}
