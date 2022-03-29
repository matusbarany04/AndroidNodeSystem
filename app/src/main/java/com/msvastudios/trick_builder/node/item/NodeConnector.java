package com.msvastudios.trick_builder.node.item;

import android.content.Context;

import com.msvastudios.trick_builder.line.LinePoint;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeDimensionsCalculator;

public abstract class NodeConnector extends NodeItem {

    LinePoint point;
    Type type = Type.NOT_SET;


    NodeConnector(Context context, Node parent, int order) {
        super(context, parent, order);
        point = new LinePoint(0, 0, parent);
//        setNewLinePoint();
    }

    // to be overridden
    public void updatePositionVars() {
        point.setPosition(parent.getLeftMargin(), parent.getTopMargin());
    }

//    private void setNewLinePoint() {
//
////        updatePositionVars();
//    }

    public NodeConnector.Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LinePoint getPoint() {
        return point;
    }

    public enum Type {
        OUTPUT,
        INPUT,
        NOT_SET
    }


}
