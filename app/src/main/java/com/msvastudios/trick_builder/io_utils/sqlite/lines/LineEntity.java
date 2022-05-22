package com.msvastudios.trick_builder.io_utils.sqlite.lines;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.msvastudios.trick_builder.node_editor.line.Line;

@Entity
public class LineEntity {

    public LineEntity(){}

    public LineEntity(String lineUUID,String algorithmUUID,String startPointId,String endPointId){
        this.lineUUID = lineUUID;
        this.algorithmUUID = algorithmUUID;
        this.startPointId= startPointId;
        this.endPointId = endPointId;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "line_uuid")
    String lineUUID;

    @ColumnInfo(name = "algorithm_uuid")
    String algorithmUUID;

    @ColumnInfo(name = "node_start_point_id")
    String startPointId;

    @ColumnInfo(name = "node_end_point_id")
    String endPointId;
}
