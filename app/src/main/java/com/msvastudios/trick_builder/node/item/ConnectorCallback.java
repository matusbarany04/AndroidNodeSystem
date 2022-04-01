package com.msvastudios.trick_builder.node.item;

public interface ConnectorCallback{
    public void onItemConnect(NodeConnectorItem item);

    public void dataInInputSent(String data, NodeInput input);


}