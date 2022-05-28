package com.msvastudios.trick_builder.io_utils.sqlite.lines;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.msvastudios.trick_builder.node_editor.line.Line;

@Entity
public class LineEntity {

    public LineEntity(){}

    public LineEntity(String lineUUID,String algorithmUUID,String startPointId,String endPointId, String startPointNodeId , String endPointNodeId){
        this.lineUUID = lineUUID;
        this.algorithmUUID = algorithmUUID;
        this.startPointId= startPointId;
        this.endPointId = endPointId;
        this.startPointNodeId = startPointNodeId;
        this.endPointNodeId = endPointNodeId;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "line_uuid")
    public String lineUUID;

    @ColumnInfo(name = "algorithm_uuid")
    public String algorithmUUID;

    @ColumnInfo(name ="node_start_point_id")
    public String startPointId;

    @ColumnInfo(name ="node_start_point_node_id")
    public String startPointNodeId;

    @ColumnInfo(name = "node_end_point_id")
    public String endPointId;

    @ColumnInfo(name ="node_end_point_node_id")
    public String endPointNodeId;

}
