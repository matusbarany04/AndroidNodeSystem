package com.msvastudios.trick_builder.node_editor.node.item.line;

import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeConnectorItem;

import java.util.UUID;

public class LinePoint {
    int x,y;
    Node parent;
    String id;

    public LinePoint(int x, int y, Node node){
        this.x = x;
        this.y = y;
        parent = node;
        id = "LinePoint" + UUID.randomUUID().toString();

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
