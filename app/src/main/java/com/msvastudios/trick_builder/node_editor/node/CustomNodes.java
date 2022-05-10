package com.msvastudios.trick_builder.node_editor.node;

import android.content.Context;

import com.msvastudios.trick_builder.node_editor.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.DummyNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.EndNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.RepeaterNode;

public enum CustomNodes {
    DUMMY_NODE(DummyNode::new, "dummy"),
    REPEATER_NODE(RepeaterNode::new, "repeater"),
    END_NODE(EndNode::new, "end");

    NodeCreator< Context, Integer, Integer , LinesView, NodeCallbackListener, Node> s;
    String type;
    CustomNodes(NodeCreator< Context, Integer, Integer , LinesView, NodeCallbackListener, Node> s, String type) {
        this.s = s;
        this.type = type;
    }

    Node createNode(Context context, Integer left, Integer top , LinesView linesView, NodeCallbackListener callbackListener) {
        return s.apply(context, left, top, linesView, callbackListener);
    }

    static CustomNodes fromString(String type) {

        for (CustomNodes nodeType : CustomNodes.values()) {
            if (nodeType.type.equals(type)) {
                return nodeType;
            }
        }

        return null;
    }


}
