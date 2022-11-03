package com.msvastudios.trick_builder.utils.sqlite.groups;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.msvastudios.trick_builder.utils.sqlite.tricks.TrickEntity;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM GroupEntity")
    List<GroupEntity> getAll();

    @Query("SELECT * FROM GroupEntity WHERE uid IN (:groupsIds)")
    List<GroupEntity> getAllByIds(int[] groupsIds);

    @Query("SELECT * FROM GroupEntity")
    public GroupEntity[] getAllGroups();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(GroupEntity... group);

    @Query("SELECT * FROM GroupEntity WHERE group_uuid== :groupUUID")
    GroupEntity getByGroupId(String groupUUID);

    @Delete
    void delete( GroupEntity group);

    @Query("DELETE FROM GroupEntity WHERE group_uuid == :groupId")
    void deleteByGroupId(String groupId);
}