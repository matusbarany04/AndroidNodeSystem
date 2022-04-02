package com.msvastudios.trick_builder.line;

import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.item.connectors.NodeConnectorItem;

public class LinePoint {
    int x,y;
    Node parent;
    public LinePoint(int x, int y, Node node){
        this.x = x;
        this.y = y;
        parent = node;
    }

    public LinePoint(LinePoint point, Node node){
        this.x = point.x;
        this.y = point.y;
        parent = node;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    NodeConnectorItem connectorItem;
    public void setItem(NodeConnectorItem connectorItem){
        this.connectorItem = connectorItem;
    }

    public NodeConnectorItem getItem() {
        return connectorItem;
    }

    public Node getParent() {
        return parent;
    }
}
