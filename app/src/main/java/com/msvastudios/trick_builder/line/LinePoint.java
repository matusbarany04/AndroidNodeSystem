package com.msvastudios.trick_builder.line;

import com.msvastudios.trick_builder.node.Node;

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
}
