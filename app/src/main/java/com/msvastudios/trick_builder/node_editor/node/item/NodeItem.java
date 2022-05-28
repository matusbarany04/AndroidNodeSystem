package com.msvastudios.trick_builder.node_editor.node.item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeDimensionsCalculator;

import java.util.UUID;

public abstract class NodeItem {
    public String id;
    public  int width, height;
    public int order;
    public Node parent;
    private LinearLayout view;
    Context context;

    protected NodeItem(Context context, Node parent, int order) {
        this.parent = parent;
        this.width = NodeDimensionsCalculator.getInnerNodeWidth() + NodeDimensionsCalculator.innerNodeMargin();
        this.height = NodeDimensionsCalculator.nodeItemHeight();
        this.order = order;
        this.context = context;
        init();
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public void init() {
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
