package com.msvastudios.trick_builder.io_utils.sqlite;

import android.content.Context;
import android.util.Pair;

import com.msvastudios.trick_builder.io_utils.sqlite.lines.LineEntity;
import com.msvastudios.trick_builder.io_utils.sqlite.nodes.NodeEntity;
import com.msvastudios.trick_builder.node_editor.line.Line;
import com.msvastudios.trick_builder.node_editor.line.LinePoint;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.node_editor.node.item.params_item.ParameterItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class AlgorithmLoader {

    public static LineEntity lineToLineEntity(Line line, String id){
        return new LineEntity(
                line.getId(),
                id,
                line.getStartPoint().getId(),
                line.getEndPoint().getId(),
                line.getStartPoint().getParent().getId(),
                line.getEndPoint().getParent().getId()
        );
    }

    public Pair<ArrayList<Line>, ArrayList<Node>> LineEntityToLine(
            ArrayList<LineEntity> line, HashMap<String, Node> nodeList){
        Node node = nodeList.get(line.get(0).);
        node.updateNodeCordinates();
        return new Line(new LinePoint( 0,0,node),new LinePoint( 0,0,node));
    }

    /**
     *
     * @param nodeEntity
     * @param context
     * @return
     */
    public Node NodeEntityToNode(NodeEntity nodeEntity, Context context) {
        return nodeEntity.type.createNode(
                context,
                nodeEntity.cordinateX,
                nodeEntity.cordinateY,
                null,
                null);
    }



}
