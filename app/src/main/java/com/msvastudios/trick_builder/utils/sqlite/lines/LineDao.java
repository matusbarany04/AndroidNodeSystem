package com.msvastudios.trick_builder.utils.sqlite.lines;

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

    @Query("SELECT * FROM LineEntity WHERE line_uuid LIKE :uuid")
    public LineEntity findByUUID(String uuid);

    @Query("SELECT * FROM LineEntity WHERE algorithm_uuid LIKE :uuid")
    public LineEntity[] findByAlgorithmUUID(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLine(LineEntity line);

    @Insert
    void insertAll(LineEntity... line);

    @Query("SELECT * FROM LineEntity WHERE line_uuid LIKE :lineUUID")
    LineEntity[] getByLineId(String lineUUID);

    @Delete
    void delete(LineEntity line);

    @Query("DELETE FROM LineEntity WHERE algorithm_uuid = :algortihmId")
    void deleteByAlgorithmId(String algortihmId);

    @Query("DELETE FROM LineEntity WHERE line_uuid = :lineUUID")
    void deleteByLineId(String lineUUID);
}
