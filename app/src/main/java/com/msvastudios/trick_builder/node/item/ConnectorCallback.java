package com.msvastudios.trick_builder.node.item;

import com.msvastudios.trick_builder.node.item.connectors.NodeConnectorItem;
import com.msvastudios.trick_builder.node.item.connectors.NodeInput;

public interface ConnectorCallback{
    public void onItemConnect(NodeConnectorItem item);

    public void dataInInputSent(String data, NodeInput input);


}