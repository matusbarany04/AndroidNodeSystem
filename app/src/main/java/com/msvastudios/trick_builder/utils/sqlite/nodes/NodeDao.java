package com.msvastudios.trick_builder.utils.sqlite.nodes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NodeDao {

    @Query("SELECT * FROM NodeEntity")
    List<NodeEntity> getAll();

    @Query("SELECT * FROM NodeEntity WHERE uid IN (:nodeIds)")
    List<NodeEntity> loadAllByIds(int[] nodeIds);

    @Query("SELECT * FROM NodeEntity")
    public NodeEntity[] loadAllNode();

    @Query("SELECT * FROM NodeEntity WHERE node_uuid LIKE :uuid LIMIT 1")
    NodeEntity findByUUID(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NodeEntity... node);

    @Query("SELECT * FROM NodeEntity WHERE node_uuid == :nodeUUID")
    NodeEntity[] getByNodeId(String nodeUUID);

    @Delete
    void delete(NodeEntity node);

    @Query("DELETE FROM NodeEntity WHERE algorithm_uuid == :algortihmId")
    void deleteByAlgorithmId(String algortihmId);


    @Query("DELETE FROM NodeEntity WHERE node_uuid == :nodeUUID")
    void deleteByNodeId(String nodeUUID);


    @Query("SELECT * FROM NodeEntity WHERE algorithm_uuid == :algortihmId")
    public NodeEntity[] getByAlgorithmId(String algortihmId);

}
