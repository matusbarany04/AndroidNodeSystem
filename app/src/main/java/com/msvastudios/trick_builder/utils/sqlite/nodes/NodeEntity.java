package com.msvastudios.trick_builder.utils.sqlite.nodes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.msvastudios.trick_builder.node_editor.node.CustomNodes;

import java.util.ArrayList;


@Entity(indices = {@Index(value = {"node_uuid"}, unique = true)})
public class NodeEntity {

    public NodeEntity() {
    }

    public NodeEntity(String algorithmUUID, int cordinateX, int cordinateY,
                      String nodeUUID,CustomNodes type,  ArrayList<String> outputIds,  ArrayList<String> inputIds ) {
        this.algorithmUUID = algorithmUUID;
        this.cordinateX = cordinateX;
        this.cordinateY = cordinateY;
        this.nodeUUID = nodeUUID;
        this.type = type;
        this.outputIds = outputIds;
        this.inputIds = inputIds;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "algorithm_uuid")
    public String algorithmUUID;

    @ColumnInfo(name = "cordinate_x")
    public int cordinateX;

    @ColumnInfo(name = "cordinate_y")
    public int cordinateY;

    @ColumnInfo(name = "node_uuid")
    public String nodeUUID;

    @ColumnInfo(name = "type")
    public CustomNodes type;

    @ColumnInfo(name = "output_ids")
    public ArrayList<String> outputIds;

    @ColumnInfo(name = "input_ids")
    public ArrayList<String> inputIds;


}
