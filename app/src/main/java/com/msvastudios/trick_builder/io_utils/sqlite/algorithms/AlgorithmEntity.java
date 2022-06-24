package com.msvastudios.trick_builder.io_utils.sqlite.algorithms;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.stream.Stream;

@Entity(indices = {@Index(value = {"algorithm_name"}, unique = true)})
public class AlgorithmEntity {

    public AlgorithmEntity(String name, String nodeNetworkUUID, int nodeCount){
        this.name = name;
        this.nodeNetworkUUID = nodeNetworkUUID;
        this.nodeCount = nodeCount;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "algorithm_name")

    public String name;

    @ColumnInfo(name = "algorithm_id")
    public String nodeNetworkUUID;

    @ColumnInfo(name = "node_count")
    public int nodeCount;
}
