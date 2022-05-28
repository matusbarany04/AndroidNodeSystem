package com.msvastudios.trick_builder.io_utils.sqlite.nodes;

import androidx.annotation.ColorRes;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.msvastudios.trick_builder.io_utils.sqlite.Converters;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.item.Type;

import java.util.ArrayList;

@Entity
public class NodeEntity {

    public NodeEntity() {
    }

    public NodeEntity(String algorithmUUID, int cordinateX, int cordinateY, String nodeUUID,CustomNodes type) {
        this.algorithmUUID = algorithmUUID;
        this.cordinateX = cordinateX;
        this.cordinateY = cordinateY;
        this.nodeUUID = nodeUUID;
        this.type = type;
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
    ArrayList<String> outputIds;

    @ColumnInfo(name = "input_ids")
    ArrayList<String> inputIds;


}
