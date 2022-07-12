package com.msvastudios.trick_builder.node_editor.node;

import android.content.Context;

import com.msvastudios.trick_builder.node_editor.io.NodeCreator;
import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.DummyNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.EndNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.RepeaterNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.TrickArrayNode;

public enum CustomNodes {
    DUMMY_NODE(DummyNode::new, "dummy"),
    REPEATER_NODE(RepeaterNode::new, "repeater"),
    TRICK_ARRAY_NODE(TrickArrayNode::new, "array"),
    END_NODE(EndNode::new, "end");


    NodeCreator< Context, Integer, Integer , LinesView, NodeCallbackListener, Node> constructor;
    public String type;
    CustomNodes(NodeCreator< Context, Integer, Integer , LinesView, NodeCallbackListener, Node> constructor, String type) {
        this.constructor = constructor;
        this.type = type;
    }

    public NodeCreator<Context, Integer, Integer, LinesView, NodeCallbackListener, Node> getConstructor() {
        return constructor;
    }

    public String getType() {
        return type;
    }

    public Node createNode(Context context, Integer left, Integer top , LinesView linesView, NodeCallbackListener callbackListener) {
        return constructor.apply(context, left, top, linesView, callbackListener);
    }

    public static CustomNodes fromString(String type) {

        for (CustomNodes nodeType : CustomNodes.values()) {
            if (nodeType.type.equals(type)) {
                return nodeType;
            }
        }
        return null;
    }



}
