package com.msvastudios.trick_builder.utils.sqlite.lines;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"line_uuid"}, unique = true)})
public class LineEntity {

    public LineEntity(){}

    public LineEntity(String lineUUID,String algorithmUUID,
                      String startPointId, String endPointId,
                      String startPointNodeId,String endPointNodeId,
                      String startPointNodeConnectorId, String endPointNodeConnectorId){
        this.lineUUID = lineUUID;
        this.algorithmUUID = algorithmUUID;
        this.startPointId= startPointId;
        this.endPointId = endPointId;
        this.startPointNodeId = startPointNodeId;
        this.startPointNodeConnectorId = startPointNodeConnectorId;
        this.endPointNodeId = endPointNodeId;
        this.endPointNodeConnectorId = endPointNodeConnectorId;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "line_uuid")
    public String lineUUID;

    @ColumnInfo(name = "algorithm_uuid")
    public String algorithmUUID;

    @ColumnInfo(name ="node_start_point_id")
    public String startPointId;

    @ColumnInfo(name ="node_start_point_node_connector_id")
    public String startPointNodeConnectorId;

    @ColumnInfo(name ="node_start_point_node_id")
    public String startPointNodeId;

    @ColumnInfo(name = "node_end_point_id")
    public String endPointId;

    @ColumnInfo(name ="node_end_point_node_id")
    public String endPointNodeId;

    @ColumnInfo(name ="node_end_point_node_connector_id")
    public String endPointNodeConnectorId;

}
