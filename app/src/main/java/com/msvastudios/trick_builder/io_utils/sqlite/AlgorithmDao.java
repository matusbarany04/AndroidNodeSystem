package com.msvastudios.trick_builder.io_utils.sqlite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface AlgorithmDao {
    @Query("SELECT * FROM algorithm")
    List<Algorithm> getAll();

    @Query("SELECT * FROM algorithm WHERE uid IN (:userIds)")
    List<Algorithm> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM algorithm WHERE algorithm_name LIKE :name LIMIT 1")
    Algorithm findByName(String name);

    @
    void addAlgorithm(Algorithm algorithm);

    @Insert
    void insertAll(Algorithm... algorithms);

    @Delete
    void delete(Algorithm algorithm);

}
