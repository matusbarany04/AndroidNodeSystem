package com.msvastudios.trick_builder.node_editor.node.item.connectors;

import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.ImageView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.msvastudios.trick_builder.node_editor.node.item.line.LinePoint;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.item.ConnectorCallback;
import com.msvastudios.trick_builder.node_editor.node.item.NodeItem;
import com.msvastudios.trick_builder.node_editor.node.item.Type;

public abstract class NodeConnectorItem extends NodeItem {
    NodeCallbackListener nodeCallbackListener;
    LinePoint point;
    Type type;


    NodeConnectorItem(Context context, Node parent, int order, Type type, NodeCallbackListener listener) {
        super(context, parent, order);
        point = new LinePoint(0, 0, parent);
        this.type = type;
        this.nodeCallbackListener = listener;

//        setNewLinePoint();
    }

    public void setListener(NodeCallbackListener listener) {
        this.nodeCallbackListener = listener;
    }

    public static void changeColorTint(ImageView imagePoint, Context context, @ColorRes int resource) {
        ImageViewCompat.setImageTintList(imagePoint, ColorStateList.valueOf(ContextCompat.getColor(context, resource)));
    }

    public abstract void updatePositionVars();

    public abstract NodeConnectorItem setText(String text);

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LinePoint getPoint() {
        return point;
    }


    public void connectedItem(){
        listener.onItemConnect(this);
    }

    ConnectorCallback listener;
    public void setCallback(ConnectorCallback listener){
        this.listener =  listener;
    }



}
