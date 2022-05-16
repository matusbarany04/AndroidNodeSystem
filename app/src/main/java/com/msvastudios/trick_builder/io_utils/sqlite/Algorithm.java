package com.msvastudios.trick_builder.io_utils.sqlite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Algorithm {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "algorithm_name")
    public String name;

    @ColumnInfo(name = "algorithm_id")
    public String nodeNetworkId;

    @ColumnInfo(name = "node_count")
    public int nodeCount;

}
