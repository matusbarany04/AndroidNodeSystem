package com.msvastudios.trick_builder.utils.sqlite.groups;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GroupEntity {

    public GroupEntity(){}

    public GroupEntity(String name, String groupUUID){
        this.name = name;
        this.groupUUID =groupUUID;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "group_uuid")
    public String groupUUID;
}
