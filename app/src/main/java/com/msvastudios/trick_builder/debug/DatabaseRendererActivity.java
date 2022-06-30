package com.msvastudios.trick_builder.debug;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.msvastudios.trick_builder.R;
import com.msvastudios.trick_builder.utils.sqlite.DatabaseHandler;
import com.msvastudios.trick_builder.utils.sqlite.algorithms.AlgorithmEntity;
import com.msvastudios.trick_builder.utils.sqlite.lines.LineEntity;
import com.msvastudios.trick_builder.utils.sqlite.nodes.NodeEntity;

import java.util.ArrayList;

public class DatabaseRendererActivity extends AppCompatActivity {
    private final String TAG = "DatabaseRendererActivity ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_renderer);


        ListView databaseAlgorithm = findViewById(R.id.databaseAlgorithms);



        DatabaseHandler.getInstance(getApplicationContext()).getAllAgorithms(new DatabaseHandler.AlgoFinish() {
            @Override
            public void onFetched(ArrayList<AlgorithmEntity> entities) {
                Log.d(TAG,  " algos " + entities.size() );
                ArrayAdapter<String> algoAdapter = new ArrayAdapter<String>(DatabaseRendererActivity.this, R.layout.array);
                for (AlgorithmEntity entity : entities) {

                    algoAdapter.add(entity.name + " " + entity.nodeNetworkUUID);
                }

                databaseAlgorithm.setAdapter(algoAdapter);
            }
        });

        ListView databaseLine = findViewById(R.id.databaseLines);

        DatabaseHandler.getInstance(getApplicationContext()).getLines(new DatabaseHandler.Lines() {
            public void onLinesFetch(ArrayList<LineEntity>entities) {
                Log.d(TAG,  " lines " + entities.size() );
                ArrayAdapter<String> lineAdapter = new ArrayAdapter<String>(DatabaseRendererActivity.this, R.layout.array);
                for (LineEntity entity : entities) {

                    lineAdapter.add("S " + entity.startPointNodeId + ":" + entity.startPointNodeConnectorId + " \n E " + entity.endPointNodeId + ":" + entity.endPointNodeConnectorId);
                    databaseLine.setAdapter(lineAdapter);
                }
            }
        });

        ListView databaseNodes = findViewById(R.id.databaseNodes);

        DatabaseHandler.getInstance(getApplicationContext()).getNodes(new DatabaseHandler.Nodes() {
            public void onNodesFetch(ArrayList<NodeEntity> entities) {
                Log.d(TAG,  " nodes " + entities.size() );

                ArrayAdapter<String> nodeAdapter = new ArrayAdapter<String>(DatabaseRendererActivity.this, R.layout.array);
                for (NodeEntity entity : entities) {

                    nodeAdapter.add("N: " + entity.nodeUUID + " A: " + entity.algorithmUUID + "P: " + entity.outputIds);
                    databaseNodes.setAdapter(nodeAdapter);
                }
            }
        });








    }
}