package com.msvastudios.trick_builder.io_utils.sqlite.algorithms;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AlgorithmEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "algorithm_name")
    public String name;

    @ColumnInfo(name = "algorithm_id")
    public String nodeNetworkUUID;

    @ColumnInfo(name = "node_count")
    public int nodeCount;

    @ColumnInfo(name = "node_id_list")
    String nodeIdList;

}
