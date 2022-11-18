package com.msvastudios.trick_builder.node_editor.node;

import android.content.Context;

import com.msvastudios.trick_builder.node_editor.node.custom_nodes.ConcatNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.CustomStringNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.GroupNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.LoggingNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.PickRandomNode;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.DummyNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.EndNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.RepeaterNode;
import com.msvastudios.trick_builder.node_editor.node.custom_nodes.TrickArrayNode;

public enum CustomNodes {
    DUMMY_NODE(DummyNode::new, "dummy"),
    REPEATER_NODE(RepeaterNode::new, "repeater"),
    TRICK_ARRAY_NODE(TrickArrayNode::new, "array"),
    END_NODE(EndNode::new, "end"),
    GROUP_NODE(GroupNode::new, "group"),
    PICK_RANDOM(PickRandomNode::new, "random"),
    LOGGING_NODE(LoggingNode::new, "logger"),
    CONCAT_NODE(ConcatNode::new,"concat"),
    CUSTOM_STRING(CustomStringNode::new, "custom_string");

    NodeCreator< Context, Integer, Integer ,String, LinesView, NodeCallbackListener, Node> constructor;
    public String type;
    CustomNodes(NodeCreator< Context, Integer, Integer ,String,  LinesView, NodeCallbackListener, Node> constructor, String type) {
        this.constructor = constructor;
        this.type = type;
    }

    public NodeCreator<Context, Integer, Integer,String, LinesView, NodeCallbackListener, Node> getConstructor() {
        return constructor;
    }

    public String getType() {
        return type;
    }

    public Node createNode(Context context, Integer left, Integer top ,String data, LinesView linesView, NodeCallbackListener callbackListener) {
        return constructor.apply(context, left, top, data, linesView, callbackListener);
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
