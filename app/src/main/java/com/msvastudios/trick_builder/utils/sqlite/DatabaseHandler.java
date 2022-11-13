package com.msvastudios.trick_builder.utils.sqlite;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.msvastudios.trick_builder.trick_listing.groups.StaticGroups;
import com.msvastudios.trick_builder.utils.sqlite.algorithms.AlgorithmEntity;
import com.msvastudios.trick_builder.utils.sqlite.algorithms.AlgorithmDatabase;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupDatabase;
import com.msvastudios.trick_builder.utils.sqlite.groups.GroupEntity;
import com.msvastudios.trick_builder.utils.sqlite.lines.LineEntity;
import com.msvastudios.trick_builder.utils.sqlite.lines.LineDatabase;
import com.msvastudios.trick_builder.utils.sqlite.nodes.NodeEntity;
import com.msvastudios.trick_builder.utils.sqlite.nodes.NodeDatabase;
import com.msvastudios.trick_builder.node_editor.node.item.line.Line;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinePoint;
import com.msvastudios.trick_builder.node_editor.node.item.line.LinesView;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.NodeCallbackListener;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;
import com.msvastudios.trick_builder.utils.sqlite.tricks.TrickDatabase;
import com.msvastudios.trick_builder.utils.sqlite.tricks.TrickEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DatabaseHandler {

    public static DatabaseHandler instance;
    private static int failrate = 0;
    ExecutorService executor;
    private AlgorithmDatabase algorithmDatabase;
    private NodeDatabase nodeDatabase;
    private LineDatabase lineDatabase;
    private TrickDatabase trickDatabase;
    private GroupDatabase groupDatabase;

    private DatabaseHandler() {
    }

    public static DatabaseHandler build(Context context) {
        instance = new DatabaseHandler();
        instance.algorithmDatabase = Room.databaseBuilder(context, AlgorithmDatabase.class, AlgorithmDatabase.DATABASE_NAME).build();
        instance.nodeDatabase = Room.databaseBuilder(context, NodeDatabase.class, NodeDatabase.DATABASE_NAME).build();
        instance.lineDatabase = Room.databaseBuilder(context, LineDatabase.class, LineDatabase.DATABASE_NAME).build();
        instance.trickDatabase = Room.databaseBuilder(context, TrickDatabase.class, TrickDatabase.DATABASE_NAME).build();
        instance.groupDatabase = Room.databaseBuilder(context, GroupDatabase.class, GroupDatabase.DATABASE_NAME).build();
        instance.executor = Executors.newSingleThreadExecutor();
        return instance;
    }

    public static DatabaseHandler getInstance(Context context) {
        if (instance != null) {
            failrate++;
        } else {
            instance = DatabaseHandler.build(context);
        }
        return instance;
    }


    public static int getFailrate() {
        return failrate;
    }

    public void getLines(DatabaseHandler.Lines callback) {
        executor.execute(() -> {
                    callback.onLinesFetch(new ArrayList<>(lineDatabase.lineDao().getAll()));
                }
        );
    }

    public void deleteGroupById(String id) {
        executor.execute(() -> {
            groupDatabase.groupDao().deleteByGroupId(id);
        });
    }


    public void getGroups(DatabaseHandler.Groups callback) {
        executor.execute(() -> {
                    callback.onGroupsFetch(new ArrayList<GroupEntity>(groupDatabase.groupDao().getAll()));
                }
        );
    }

    public void insertGroup(GroupEntity entity, @Nullable DatabaseHandler.Finish state) {
        executor.execute(() -> {
            try {
                groupDatabase.groupDao().insertAll(entity);
                if (state != null) state.onActionFinished(0);
            } catch (Exception e) {
                if (state != null) state.onActionFinished(1);
            }
        });
    }

    public void insertTrick(TrickEntity entity, @Nullable DatabaseHandler.Finish state) {
        executor.execute(() -> {
            try {
                trickDatabase.trickDao().insertAll(entity);
                if (state != null) state.onActionFinished(0);
            } catch (Exception e) {
                if (state != null) state.onActionFinished(1);
            }
        });
    }

    public void getGroupByUUID(Group callback, String uuid) {
        executor.execute(() -> {
                    callback.onGroupFetch(groupDatabase.groupDao().getByGroupId(uuid));
                }
        );
    }


    public void getNodes(DatabaseHandler.Nodes callback) {
        executor.execute(() -> {
                    callback.onNodesFetch(new ArrayList<>(nodeDatabase.nodeDao().getAll()));
                }
        );
    }

    public void updateAlgorithmEntity(AlgorithmUpdate callback, AlgorithmEntity updated) {
        executor.execute(() -> {
            algorithmDatabase.algorithmDao().delete(updated);
            algorithmDatabase.algorithmDao().insertAll(updated);
            callback.algorithmUpdated(0);
        });
    }

    public void getAlgorithmEntity(String id, DatabaseHandler.Algorithm callback) {
        executor.execute(() -> {
            AlgorithmEntity entity = algorithmDatabase.algorithmDao().getByAlgorithmById(id);
            if (entity != null)
                callback.onAlgorithm(0, entity);
            else
                callback.onAlgorithm(1, null);
        });
    }

    public void createNewAlgorithmEntity(String name, String imageId, Algorithm callback) {
        executor.execute(() -> {
            AlgorithmEntity[] entities = algorithmDatabase.algorithmDao().findByName(name);
            if (entities.length == 0)
                callback.onAlgorithm(0, new AlgorithmEntity(name, UUID.randomUUID().toString(), 0, imageId));
            else
                callback.onAlgorithm(1, entities[0]);
        });
    }

    //TODO xxx make a method that will join insert and delete -> update
    public void updateAlgorithm(AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes) {
        this.removeAlgorithm(algorithm);
        this.insertAlgorithm(algorithm, lines, nodes);
    }

    public void getAllAgorithms(AlgoFinish callback) {
        executor.execute(() -> {
            callback.onFetched(
                    new ArrayList<AlgorithmEntity>(
                            Arrays.asList(algorithmDatabase.algorithmDao().loadAllAlgorithms())
                    )
            );
        });
    }
    public void algorithmExists(String id, Finish callback){
        executor.execute(() -> {
            AlgorithmEntity entity = algorithmDatabase.algorithmDao().getByAlgorithmById(id);
            if (entity != null)
                callback.onActionFinished(0);
            else
                callback.onActionFinished(1);
        });
    }
    
    
    public void deleteAlgorithm(String algoID, Finish finish){
        executor.execute(() -> {
            try{
                AlgorithmEntity entity = algorithmDatabase.algorithmDao().getByAlgorithmById(algoID);
                algorithmDatabase.algorithmDao().delete(entity);
                lineDatabase.lineDao().deleteByAlgorithmId(algoID);
                nodeDatabase.nodeDao().deleteByAlgorithmId(algoID);
                finish.onActionFinished(0);

            }catch (Exception e){
                Log.e("DatabaseHAndler", e.getMessage());
                finish.onActionFinished(1);
            }
        });
    }

    /**
     * @param algorithm
     * @deprecated
     */
    public void saveAlgorithm(AlgorithmEntity algorithm) {
        executor.execute(() -> algorithmDatabase.algorithmDao().insertAll(algorithm));
    }

    public void removeAlgorithm(AlgorithmEntity algorithm) {
        executor.execute(() -> {
            algorithmDatabase.algorithmDao().delete(algorithm);
            lineDatabase.lineDao().deleteByAlgorithmId(algorithm.nodeNetworkUUID);
            nodeDatabase.nodeDao().deleteByAlgorithmId(algorithm.nodeNetworkUUID);
        });
    }

    public void insertAlgorithm(AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes) {
        executor.execute(() -> {
                    algorithmDatabase.algorithmDao().addAlgorithm(algorithm);
                    lineDatabase.lineDao().deleteByAlgorithmId(algorithm.nodeNetworkUUID);
                    for (Line line : lines) {
                        //TODO not sure if this if is necessary, when I've added line above for loop
                        if (lineDatabase.lineDao().getByLineId(line.getId()) != null) {
                            lineDatabase.lineDao().deleteByLineId(line.getId());
                        }
                        lineDatabase.lineDao().insertAll(AlgorithmLoader.lineToLineEntity(line, algorithm.nodeNetworkUUID));
                    }
                    nodeDatabase.nodeDao().deleteByAlgorithmId(algorithm.nodeNetworkUUID);
                    for (Node node : nodes) {
                        NodeEntity nodeEntity = new NodeEntity(
                                algorithm.nodeNetworkUUID,
                                node.getLeftMargin(),
                                node.getTopMargin(),
                                node.getId(),
                                node.getType(),
                                node.getJsonData(),
                                node.getNodeOutputIds(),
                                node.getNodeInputIds());
                        if (nodeDatabase.nodeDao().getByNodeId(node.getId()) != null) {
                            nodeDatabase.nodeDao().deleteByNodeId(node.getId());
                        }
                        nodeDatabase.nodeDao().insertAll(nodeEntity);

                    }
                }
        );
    }

    public void getAlgorithmName(String algorithmUUID, DatabaseHandler.NameFetch callback) {

        executor.execute(() -> {
            callback.onNameFetched(algorithmDatabase.algorithmDao().getByAlgorithmById(algorithmUUID).name);
        });
    }

    public void getAlgorithmImageId(String algorithmUUID, DatabaseHandler.NameFetch callback) {
        executor.execute(() -> {
            callback.onNameFetched(algorithmDatabase.algorithmDao().getByAlgorithmById(algorithmUUID).imageId);
        });
    }

    public void getAlgorithm(String algoID, Context context, LinesView linesView, NodeCallbackListener callbackListener, DatabaseHandler.Data callback) {
        //return AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes
        executor.execute(() -> {
                    AlgorithmEntity algo = algorithmDatabase.algorithmDao().getByAlgorithmById(algoID);

                    ArrayList<LineEntity> lineEntities = new ArrayList<>(Arrays.asList(lineDatabase.lineDao().findByAlgorithmUUID(algoID)));

                    ArrayList<NodeEntity> nodeEntities = new ArrayList<>(Arrays.asList(nodeDatabase.nodeDao().getByAlgorithmId(algoID)));


                    ArrayList<Line> lines = new ArrayList<>();

                    HashMap<String, Node> nodeHashMap = new HashMap<>();
                    for (NodeEntity entitity : nodeEntities) {
                        Node node = AlgorithmLoader.nodeEntityToNode(entitity, context, linesView, callbackListener);
                        for (String id : entitity.outputIds) {
                            Log.d("outIds: ", id);
                        }
                        node.setNodeOutputIds(entitity.outputIds);
                        node.setNodeInputIds(entitity.inputIds);
                        node.setId(entitity.nodeUUID);
                        Log.d("ENTITY ID", entitity.nodeUUID);
                        nodeHashMap.put(entitity.nodeUUID, node);
                    }

                    for (LineEntity entity : lineEntities) {

                        Node startNode = nodeHashMap.get(entity.startPointNodeId);
                        if (startNode == null) {
                            System.err.println("error searching for startnode");
                            continue;
                        }
                        startNode.updatePositionVars();

                        LinePoint startPoint = null;
                        Log.d("searching id: ", entity.startPointId);
                        for (NodeOutput output : startNode.getNodeOutput()) {
                            Log.d("output id : ", output.getPoint().getId());
                            if (output.getID().equals(entity.startPointNodeConnectorId)) {
                                startPoint = output.getPoint();
//                                output.getPoint().setId(entity.startPointId);
                            }
                        }

                        Node endNode = nodeHashMap.get(entity.endPointNodeId);
                        if (endNode == null) {
                            System.err.println("error searching for end node");
                            continue;
                        }
                        endNode.updatePositionVars();

                        LinePoint endPoint = null;
                        for (NodeInput input : endNode.getNodeInput()) {
                            if (input.getID().equals(entity.endPointNodeConnectorId)) {
                                endPoint = input.getPoint();
                            }
                        }

                        //TODO points can be nullable add exceptions and stuff
                        assert endPoint != null;
                        assert startPoint != null;

                        Line line = new Line(startPoint, endPoint);
                        lines.add(line);
                    }

                    callback.onAlgorithmBuilt(algo, lines, new ArrayList<Node>(nodeHashMap.values()));
                }
        );
    }

    public void printAlgoData() {
        new Thread(() -> {
            for (AlgorithmEntity algo : algorithmDatabase.algorithmDao().getAll()) {
                Log.d("algo", algo.name);
            }
            System.out.println("-------------------------------");
            for (NodeEntity node : nodeDatabase.nodeDao().getAll()) {
                Log.d("algo", node.toString());
            }
            System.out.println("-------------------------------");
            for (LineEntity line : lineDatabase.lineDao().getAll()) {
                Log.d("algo", line.toString());
            }
        }).start();
    }

    public void getTricks(Trick callback) {
        executor.execute(() -> {
            callback.onTricksFetched(new ArrayList<TrickEntity>(trickDatabase.trickDao().getAll()));
        });
    }

    public void getTricksByGroupId(String groupId, Trick callback) {
        executor.execute(() -> {
            ArrayList<TrickEntity> output = new ArrayList<>();
            ArrayList<TrickEntity> all = new ArrayList<>(trickDatabase.trickDao().getAll());
            for (TrickEntity entity : all) {
//                for (String id : entity.groupIds) {
//                    System.out.println("id" + id);
//                }
                for (String entityGroupId : entity.groupIds) {
                    if (entityGroupId.equals(groupId)) output.add(entity);
                }
            }
            callback.onTricksFetched(output);
        });
    }
    public void getTricksByGroupId(StaticGroups group, Trick callback) {
        executor.execute(() -> {
            ArrayList<TrickEntity> output = new ArrayList<>();
            ArrayList<TrickEntity> all = new ArrayList<>(trickDatabase.trickDao().getAll());
            for (TrickEntity entity : all) {
                if(group.equals(StaticGroups.ALL_TRICKS)){
                    output.add(entity);
                }else if(StaticGroups.LEARNED_TRICKS.equals(group)){
                    if(entity.learned) output.add(entity);
                }
            }
            callback.onTricksFetched(output);
        });
    }

    public void insertTrick(Finish callback, TrickEntity... tricks) {
        executor.execute(() -> {
            trickDatabase.trickDao().insertAll(tricks);
            callback.onActionFinished(1);
        });
    }

    public void deleteTrick(Finish callback, TrickEntity entity) {
        executor.execute(() -> {
            trickDatabase.trickDao().delete(entity);
            callback.onActionFinished(1);
        });
    }

    public void deleteLine(Finish callback, LineEntity entity) {
        executor.execute(() -> {
            lineDatabase.lineDao().delete(entity);
            callback.onActionFinished(1);
        });
    }

    public void deleteLineById(Finish callback, String id) {
        executor.execute(() -> {
            lineDatabase.lineDao().deleteByLineId(id);
            callback.onActionFinished(1);
        });
    }

    public void deleteLineByAlgorithmUUID(Finish callback, String id) {
        executor.execute(() -> {
            lineDatabase.lineDao().deleteByAlgorithmId(id);
            callback.onActionFinished(1);
        });
    }


    public void deleteTrickByUuid(Finish callback, String trickUuid) {
        executor.execute(() -> {
            trickDatabase.trickDao().deleteByTrickId(trickUuid);
            if (callback != null) callback.onActionFinished(1);
        });
    }

    public interface Trick {
        void onTricksFetched(ArrayList<TrickEntity> tricks);
    }

    public interface Data {
        void onAlgorithmBuilt(AlgorithmEntity algorithm, ArrayList<Line> lines, ArrayList<Node> nodes);
    }

    public interface Algorithm {
        void onAlgorithm(Integer result, AlgorithmEntity entity);
    }

    public interface Lines {
        void onLinesFetch(ArrayList<LineEntity> result);
    }

    public interface Nodes {
        void onNodesFetch(ArrayList<NodeEntity> result);
    }

    public interface AlgoFinish {
        void onFetched(ArrayList<AlgorithmEntity> entities);
    }

    public interface NameFetch {
        void onNameFetched(String name);

    }

    public interface AlgorithmUpdate {
        void algorithmUpdated(int status);
    }

    public interface Groups {
        void onGroupsFetch(ArrayList<GroupEntity> groupEntities);
    }

    public interface Group {
        void onGroupFetch(GroupEntity byGroupId);
    }

    public interface Finish {
        void onActionFinished(int status);
    }
}
