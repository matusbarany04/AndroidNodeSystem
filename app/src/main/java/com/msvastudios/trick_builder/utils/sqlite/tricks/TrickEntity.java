package com.msvastudios.trick_builder.utils.sqlite.tricks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.msvastudios.trick_builder.node_editor.node.CustomNodes;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Entity(indices = {@Index(value = {"trick_uuid"}, unique = true)})
public class TrickEntity {

    public TrickEntity() {
    }

    public TrickEntity(String trickName, String trickUUID, ArrayList<String> trickGroupIds){
        this.trickGroupIds = trickGroupIds;
        this.trickName = trickName;
        this.trickUUID= trickUUID;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    String trickName;

    @ColumnInfo(name = "trick_uuid")
    String trickUUID;

    @ColumnInfo(name = "trick_group_ids")
    ArrayList<String> trickGroupIds;
}
