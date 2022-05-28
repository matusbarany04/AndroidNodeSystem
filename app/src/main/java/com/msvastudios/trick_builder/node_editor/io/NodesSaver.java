package com.msvastudios.trick_builder.node_editor.io;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.msvastudios.trick_builder.io_utils.textfiles.InternalFiles;
import com.msvastudios.trick_builder.io_utils.textfiles.InternalStorageSaver;
import com.msvastudios.trick_builder.node_editor.line.Line;
import com.msvastudios.trick_builder.node_editor.line.LinePoint;
import com.msvastudios.trick_builder.node_editor.node.CustomNodes;
import com.msvastudios.trick_builder.node_editor.node.Node;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeInput;
import com.msvastudios.trick_builder.node_editor.node.item.connectors.NodeOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

//deprecated
enum SerializedDividers {
    PARAM("|"),
    ARRAY("/"),
    ARRAY_ITEM(";"),
    NODE("@"),
    NODE_NETWORK("_"),
    NODE_LINES("#");
    String divider;

    SerializedDividers(String divider) {
        this.divider = divider;
    }

    @NonNull
    @Override
    public String toString() {
        return divider;
    }

    public String getDivider() {
        return divider;
    }
}

public class NodesSaver {

    InternalStorageSaver internalStorageSaver;
    Context context;

    public NodesSaver(Context context) {
        internalStorageSaver = new InternalStorageSaver(context, InternalFiles.NODE);
        this.context = context;
    }

    public InternalStorageSaver getInternalStorageSaver() {
        return internalStorageSaver;
    }

    public boolean saveNodes(@NonNull ArrayList<Node> nodeList, String networkId, ArrayList<Line> lines) {

        //internalStorageSaver.append(networkId + SerializedDividers.NODE.getDivider());
        HashMap<String, String> networks = getAlgorithmsAsHashMap();
        System.out.println("networks in me " + networks.values().size());
        StringBuilder serializedData = new StringBuilder();
        for (int i = 0; i < nodeList.size(); i++) {
            serializedData.append(SerializedDividers.NODE.getDivider());

            Node node = nodeList.get(i);
            serializedData.append(serializeNode(node));
        }

        serializedData.append(SerializedDividers.NODE_LINES.getDivider());

        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            LinePoint start = line.getStartPoint();
            LinePoint end = line.getEndPoint();

            serializedData.append(
                    start.getId() + SerializedDividers.ARRAY_ITEM.getDivider()
                            + end.getId() + SerializedDividers.ARRAY_ITEM.getDivider()
            );

            if (i < lines.size() - 1) {
                serializedData.append(SerializedDividers.ARRAY.getDivider());
            }
        }
        serializedData.append(SerializedDividers.NODE_NETWORK.getDivider());

        internalStorageSaver.clear();

        for (String key : networks.keySet()) {
            String data = networks.get(key);
            System.out.println(" loopin " + key + " =?= " + networkId);
            if (key.equals(networkId)){
                continue;
            }
            internalStorageSaver.append(networkId +
                    SerializedDividers.NODE.getDivider()+ SerializedDividers.NODE.getDivider() + data + SerializedDividers.NODE_NETWORK.getDivider());
        }
        internalStorageSaver.append(networkId +  SerializedDividers.NODE.getDivider() + serializedData + SerializedDividers.NODE_NETWORK.getDivider());


        System.out.println("networks in me after" + getAlgorithmsAsHashMap().size() );
        return true;
    }

    /**
     *
     * @return is a hashMap where key is id of algorithm and value holds the data
     */
    private HashMap<String, String> getAlgorithmsAsHashMap(){
        //reading full file and dividing it to individual algorithms
        ArrayList<String> nodeNetworks = new ArrayList<>(Arrays.asList(internalStorageSaver.read().split(Pattern.quote(SerializedDividers.NODE_NETWORK.getDivider()))));
        HashMap<String, String> serializedNodeNetwork = new HashMap<>();

        //looping trough network to find desired network
        for (String nodeNetwork : nodeNetworks) {
            System.out.println(nodeNetwork);
            try{

                ArrayList<String> data = new ArrayList<>(Arrays.asList(
                        nodeNetwork.split(SerializedDividers.NODE.getDivider() + SerializedDividers.NODE.getDivider())));

                String id = data.get(0);
                String nodeNetworkData = data.get(1);


                serializedNodeNetwork.put(id, nodeNetworkData);

            }catch (IndexOutOfBoundsException | NullPointerException ignored){
                Log.e("getAlgo ", " error bzz bzz chrr");
            }
        }

        return serializedNodeNetwork;
    }


    public Pair<ArrayList<Node>, HashMap<String, ArrayList<String>>> readNodes(String networkId) {
        HashMap<String, String> serializedNodeNetwork = getAlgorithmsAsHashMap();

        //data of certain network
        String networkData = serializedNodeNetwork.get(networkId);
        HashMap<String, ArrayList<String>> outputHashMap = new HashMap<>();
        if (networkData != null){
            String[] dividedData = networkData.split(Pattern.quote(SerializedDividers.NODE_LINES.getDivider()));
            String nodes = dividedData[0];
            if(dividedData.length>1){
                String lines = dividedData[1];
                outputHashMap = deserializeLines(lines);
            }

            ArrayList<String> serializedNodes = new ArrayList<>(Arrays.asList(nodes.split(SerializedDividers.NODE.getDivider())));

            ArrayList<Node> deserializedNodes = new ArrayList<>();
            for (String serNode : serializedNodes) {
                deserializedNodes.add(deserializeNode(serNode, context));
            }


            return new Pair<>(deserializedNodes, outputHashMap );
        }
       return new Pair<>(new ArrayList<>(), new HashMap<>());
    }

    private HashMap<String, ArrayList<String>> deserializeLines(String lineData) {
        ArrayList<String> linesString = new ArrayList<>(Arrays.asList(lineData.split(Pattern.quote(SerializedDividers.ARRAY.getDivider()))));

        HashMap<String, ArrayList<String>> lines = new HashMap<>();
        for (String lineString : linesString) {
            String startId = lineString.split(Pattern.quote(SerializedDividers.ARRAY_ITEM.getDivider()))[0];
            String endId = lineString.split(Pattern.quote(SerializedDividers.ARRAY_ITEM.getDivider()))[1];
            ArrayList<String> ends =  lines.get(startId);
            if (ends == null){
                ends = new ArrayList<>();
            }
            ends.add(endId);
            lines.put(startId, ends);
        }

        return lines;
    }


    private Node deserializeNode(String serNode, Context context) {
        String[] data = serNode.split(Pattern.quote(SerializedDividers.PARAM.getDivider()));

        String type = data[0];
        String id = data[1];
        Integer leftMargin = Integer.parseInt(data[2]);
        Integer topMargin = Integer.parseInt(data[3]);

        ArrayList<String> outputsIds = new ArrayList<>();
        try {
            outputsIds = new ArrayList<>(Arrays.asList(data[4].split(Pattern.quote(SerializedDividers.ARRAY.getDivider()))));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        ArrayList<String> inputIds = new ArrayList<>();
        try {
            inputIds = new ArrayList<>(Arrays.asList(data[5].split(Pattern.quote(SerializedDividers.ARRAY.getDivider()))));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        Node node = CustomNodes.fromString(type).createNode(context, leftMargin, topMargin, null, null);


        ArrayList<NodeOutput> nodeOutputs = node.getNodeOutput();

        for (String array_item : outputsIds) {
            ArrayList<String> arrayItemData = new ArrayList<>(Arrays.asList(array_item.split(Pattern.quote(SerializedDividers.ARRAY_ITEM.getDivider()))));

            String itemId = arrayItemData.get(0);
            int i = Integer.parseInt(arrayItemData.get(1));

            nodeOutputs.get(i).getPoint().setId(itemId);
        }

        ArrayList<NodeInput> nodeInputs = node.getNodeInput();

        for (String array_item : inputIds) {
            ArrayList<String> arrayItemData = new ArrayList<>(Arrays.asList(array_item.split(Pattern.quote(SerializedDividers.ARRAY_ITEM.getDivider()))));

            String itemId = arrayItemData.get(0);
            int i = Integer.parseInt(arrayItemData.get(1));

            nodeInputs.get(i).getPoint().setId(itemId);
        }
        return node;
    }

    public String serializeNode(@NonNull Node node) {


        String start = node.getType().type + SerializedDividers.PARAM.getDivider()
                + node.getId() + SerializedDividers.PARAM.getDivider()
                + node.getLeftMargin() + SerializedDividers.PARAM.getDivider()
                + node.getTopMargin();

        StringBuilder outputs = new StringBuilder();
        for (int i = 0; i < node.getNodeOutput().size(); i++) {
            NodeOutput output = node.getNodeOutput().get(i);
            outputs.append(output.getPoint().getId()).append(SerializedDividers.ARRAY_ITEM.getDivider()).append(i);

            if (i < node.getNodeOutput().size() - 1) {
                outputs.append(SerializedDividers.ARRAY.getDivider());
            }
        }

        StringBuilder inputs = new StringBuilder();
        for (int i = 0; i < node.getNodeInput().size(); i++) {
            NodeInput input = node.getNodeInput().get(i);
            inputs.append(input.getPoint().getId()).append(SerializedDividers.ARRAY_ITEM.getDivider()).append(i);

            if (i < node.getNodeInput().size() - 1) {
                inputs.append(SerializedDividers.ARRAY.getDivider());
            }
        }

        return start + SerializedDividers.PARAM.getDivider() + outputs + SerializedDividers.PARAM.getDivider() + inputs;

    }


}
