package com.msvastudios.trick_builder.io_utils.sqlite.nodes;

import androidx.annotation.ColorRes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.msvastudios.trick_builder.node_editor.node.Node;

@Entity
public class NodeEntity {

    public NodeEntity() {
    }

    public NodeEntity(String algorithmUUID, int cordinateX, int cordinateY, String nodeUUID) {
        this.algorithmUUID = algorithmUUID;
        this.cordinateX = cordinateX;
        this.cordinateY = cordinateY;
        this.nodeUUID = nodeUUID;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "algorithm_uuid")
    String algorithmUUID;

    @ColumnInfo(name = "cordinate_x")
    int cordinateX;

    @ColumnInfo(name = "cordinate_y")
    int cordinateY;

    @ColumnInfo(name = "node_uuid")
    String nodeUUID;




}
