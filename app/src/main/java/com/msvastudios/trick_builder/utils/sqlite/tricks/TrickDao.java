package com.msvastudios.trick_builder.utils.sqlite.tricks;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.msvastudios.trick_builder.utils.sqlite.nodes.NodeEntity;

import java.util.List;

@Dao
public interface TrickDao {

    @Query("SELECT * FROM TrickEntity")
    List<TrickEntity> getAll();

    @Query("SELECT * FROM TrickEntity WHERE uid IN (:trickIds)")
    List<TrickEntity> getAllByIds(int[] trickIds);

    @Query("SELECT * FROM TrickEntity")
    public TrickEntity[] getAllTricks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TrickEntity... node);

    @Query("SELECT * FROM TrickEntity WHERE trick_uuid == :trickId")
    TrickEntity[] getByTrickId(String trickId);

    @Delete
    void delete(TrickEntity node);

    @Query("DELETE FROM TrickEntity WHERE trick_uuid == :trickUUID")
    void deleteByTrickId(String trickUUID);

}

