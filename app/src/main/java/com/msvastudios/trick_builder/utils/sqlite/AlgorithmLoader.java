package com.msvastudios.trick_builder.utils.sqlite;

import android.content.Context;

import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.utils.sqlite.lines.LineEntity;
import com.msvastudios.trick_builder.utils.sqlite.nodes.NodeEntity;
import com.msvastudios.trick_builder.node_editor.node.item.line.Line;
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
    public static Node nodeEntityToNode(NodeEntity nodeEntity, Context context, LinesView linesView, NodeCallbackListener callbackListener) {
        return nodeEntity.type.createNode(
                context,
                nodeEntity.cordinateX,
                nodeEntity.cordinateY,
                nodeEntity.jsonData,
                linesView,
                callbackListener);
    }



}
