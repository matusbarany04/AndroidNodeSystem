package com.msvastudios.trick_builder.node.item;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.ImageView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.line.LinePoint;
import com.msvastudios.trick_builder.node.Node;

public abstract class NodeConnectorItem extends NodeItem {

    LinePoint point;
    Type type;

    NodeConnectorItem(Context context, Node parent, int order,Type type) {
        super(context, parent, order);
        point = new LinePoint(0, 0, parent);
        this.type = type;

//        setNewLinePoint();
    }

    public abstract void updatePositionVars();

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LinePoint getPoint() {
        return point;
    }

    public static void changeColorTint(ImageView imagePoint, Context context, @ColorRes int resource){
        ImageViewCompat.setImageTintList(imagePoint,  ColorStateList.valueOf(ContextCompat.getColor(context, resource)));
    }




}
