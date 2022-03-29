package com.msvastudios.trick_builder.node.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.msvastudios.trick_builder.line.LinePoint;
import com.msvastudios.trick_builder.node.Node;
import com.msvastudios.trick_builder.node.NodeDimensionsCalculator;

import java.util.UUID;

public abstract class NodeItem {
    String id;
    int width, height;
    int order;
    Node parent;
    private LinearLayout view;

    protected NodeItem(Context context, Node parent, int order) {
        this.parent = parent;
        this.width = NodeDimensionsCalculator.getInnerNodeWidth() + NodeDimensionsCalculator.innerNodeMargin();
        this.height = NodeDimensionsCalculator.nodeItemHeight();
        this.order = order;
        init(context);
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    private void init(Context context) {
        id = generateId();
        view = new LinearLayout(context);
        disableClipOnParents(view);
    }

    public String getID() {
        return id;
    }

    public void disableClipOnParents(View v) {
        if (v == null) {
            return;
        }
        if (v instanceof ViewGroup) {
            ((ViewGroup) v).setClipChildren(false);
            ((ViewGroup) v).setClipToOutline(false);
            ((ViewGroup) v).setClipToPadding(false);
        }
        disableClipOnParents((View) v.getParent());
    }

    public LinearLayout getView() {
        return view;
    }

    public int getOrder() {
        return order;
    }


}
