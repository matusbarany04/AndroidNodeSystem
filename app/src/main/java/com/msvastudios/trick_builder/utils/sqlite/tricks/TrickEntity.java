package com.msvastudios.trick_builder.utils.sqlite.tricks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(indices = {@Index(value = {"trick_uuid"}, unique = true)})
public class TrickEntity {

    public TrickEntity() {
    }

    public TrickEntity(String trickName, String trickUUID, ArrayList<String> trickGroupIds, boolean learned){
        this.groupIds = trickGroupIds;
        this.name = trickName;
        this.trickUUID= trickUUID;
        this.learned = learned;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;


    @ColumnInfo(name = "desription")
    public String description;

    @ColumnInfo(name = "trick_uuid")
    public String trickUUID;


    @ColumnInfo(name = "learned")
    public boolean learned;

    @ColumnInfo(name = "trick_group_ids")
    public ArrayList<String> groupIds;
}
