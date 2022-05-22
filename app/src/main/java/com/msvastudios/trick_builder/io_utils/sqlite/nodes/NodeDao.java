package com.msvastudios.trick_builder.io_utils.sqlite.nodes;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.msvastudios.trick_builder.io_utils.sqlite.lines.LineEntity;

import java.util.List;

public interface NodeDao {

    @Query("SELECT * FROM NodeEntity")
    List<LineEntity> getAll();

    @Query("SELECT * FROM NodeEntity WHERE uid IN (:nodeIds)")
    List<LineEntity> loadAllByIds(int[] nodeIds);

    @Query("SELECT * FROM NodeEntity")
    public NodeEntity[] loadAllNode();

    @Query("SELECT * FROM NodeEntity WHERE node_uuid LIKE :uuid LIMIT 1")
    NodeEntity findByUUID(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNode(NodeEntity Node);

    @Insert
    void insertAll(NodeEntity... node);

    @Delete
    void delete(NodeEntity node);

    @Query("DELETE FROM LineEntity WHERE algorithm_uuid LIKE :algortihmId LIMIT 1")
    void deleteByAlgorithmId(String algortihmId);
}
