package com.msvastudios.trick_builder.utils.sqlite;

import android.content.Context;

import com.msvastudios.trick_builder.utils.sqlite.lines.LineEntity;
import com.msvastudios.trick_builder.utils.sqlite.nodes.NodeEntity;
import com.msvastudios.trick_builder.node_editor.line.Line;
import com.msvastudios.trick_builder.node_editor.node.Node;

public class AlgorithmLoader {

    public static LineEntity lineToLineEntity(Line line, String id){
        return new LineEntity(
                line.getId(),
                id,
                line.getStartPoint().getId(),
                line.getEndPoint().getId(),
                line.getStartPoint().getParent().getId(),
                line.getEndPoint().getParent().getId(),
                line.getStartPoint().getParent().getNodeOutputByPointId(line.getStartPoint().getId()).getID(),
                line.getEndPoint().getParent().getNodeInputByPointId(line.getEndPoint().getId()).getID()
                );
    }

//    public Pair<ArrayList<Line>, ArrayList<Node>> LineEntityToLine(ArrayList<LineEntity> line, HashMap<String, Node> nodeList){
//        Node node = nodeList.get(line.get(0).);
//        node.updateNodeCordinates();
//        return new Line(new LinePoint( 0,0,node),new LinePoint( 0,0,node));
//    }

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
