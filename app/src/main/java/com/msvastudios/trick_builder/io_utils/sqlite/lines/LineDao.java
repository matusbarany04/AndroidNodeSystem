package com.msvastudios.trick_builder.io_utils.sqlite.lines;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LineDao {

    @Query("SELECT * FROM LineEntity")
    List<LineEntity> getAll();

    @Query("SELECT * FROM LineEntity WHERE uid IN (:lineIds)")
    List<LineEntity> loadAllByIds(int[] lineIds);

    @Query("SELECT * FROM LineEntity")
    public LineEntity[] loadAllLine();

    @Query("SELECT * FROM LineEntity WHERE line_uuid LIKE :uuid LIMIT 1")
    LineEntity findByUUID(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLine(LineEntity line);

    @Insert
    void insertAll(LineEntity... line);

    @Delete
    void delete(LineEntity line);

    @Query("DELETE FROM LineEntity WHERE algorithm_uuid LIKE :algortihmId LIMIT 1")
    void deleteByAlgorithmId(String algortihmId);
}
