package com.msvastudios.trick_builder.io_utils.sqlite.algorithms;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface AlgorithmDao {
    @Query("SELECT * FROM AlgorithmEntity")
    List<AlgorithmEntity> getAll();

    @Query("SELECT * FROM AlgorithmEntity WHERE uid IN (:userIds)")
    List<AlgorithmEntity> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM AlgorithmEntity")
    public AlgorithmEntity[] loadAllAlgorithms();

    @Query("SELECT * FROM AlgorithmEntity WHERE algorithm_id IN (:algoId)")
    public AlgorithmEntity getByAlgorithmId(String algoId);

    @Query("SELECT * FROM AlgorithmEntity WHERE algorithm_name LIKE :name LIMIT 1")
    public AlgorithmEntity[] findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAlgorithm(AlgorithmEntity algorithm);

    @Insert
    void insertAll(AlgorithmEntity... algorithms);

    @Delete
    void delete(AlgorithmEntity algorithm);

}
