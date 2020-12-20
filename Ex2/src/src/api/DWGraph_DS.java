package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{

    ////private variables////

    private HashMap<Integer, node_data> nodes;// = new HashMap<Integer, node_data>();
    private HashMap<Integer, HashMap<Integer, edge_data>> edges;// = new HashMap<Integer, HashMap<Integer, edge_data>>(); // key: <src,dest>
    private int countMC = 0, countEdge = 0;

    /////////////////////////

    /**
     * constructor
     */
    public DWGraph_DS(){
        nodes = new HashMap<Integer, node_data>();
        edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
        countMC = 0;
        countEdge = 0;
    }

    /**
     * constructor
     * @param graph
     */
    public DWGraph_DS(directed_weighted_graph graph){
        nodes = new HashMap<Integer, node_data>();
        edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
        countMC = 0;
        countEdge = 0;

        for(node_data temp: graph.getV()){
            addNode(temp);
        }
        for(node_data temp: graph.getV()){
            for(edge_data temp2: graph.getE(temp.getKey())){
                connect(temp.getKey(), temp2.getDest(), temp2.getWeight());
            }
        }
    }

    /**
     * if exists returns the node with the given id
     * else returns null
     * @param key - the node_id
     * @return
     */
    @Override
    public node_data getNode(int key) {
        return nodes.get(key);
    }

    /**
     * if exists returns the edge between src => dest
     * else returns null
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        return edges.get(src).get(dest);
    }

    /**
     * adds the given node to the graph, if it doesn't exist already
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if(n != null) {
            nodes.putIfAbsent(n.getKey(), n);
            HashMap<Integer, edge_data> de = new HashMap<Integer, edge_data>();
            edges.putIfAbsent(n.getKey(), de);
            countMC++;
        }
    }

    /**
     * makes a new edge from src to dest with the weight - w, if no such edge exists
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if(nodes.containsKey(src) && nodes.containsKey(dest) && src != dest) {
            EdgeData ed = new EdgeData(src, dest, w);
            edges.get(src).putIfAbsent(dest, ed);
            countEdge++;
            countMC++;
        }
    }

    /**
     * returns a collection of all nodes
     * @return
     */
    @Override
    public Collection<node_data> getV() {
        return nodes.values();
    }

    /**
     * returns a collection of all edges
     * @param node_id
     * @return
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return edges.get(node_id).values();
    }

    /**
     * removes the node - key, and returns the node
     * if such a node doesn't exists on graph the function does nothing
     * @param key
     * @return
     */
    @Override
    public node_data removeNode(int key) {
        if(nodes.containsKey(key)) {

            for (node_data temp : getV()) {
                if(edges.get(temp.getKey()).containsKey(key))
                    edges.get(temp.getKey()).remove(key);
                countMC++;
                countEdge--;
            }
            edges.remove(key);
            node_data res = nodes.get(key);
            nodes.remove(key);
            countMC++;
            return res;
        }
        return null;
    }

    /**
     * removes the edge src => dest and returns it
     * if such an edge doesn't exists on graph the function does nothing
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if(nodes.containsKey(src) && nodes.containsKey(dest) && src != dest && edges.get(src).containsKey(dest)) {
            edges.get(src).remove(dest);
            countMC++;
            countEdge--;
        }
        return null;
    }

    /**
     * returns the number of nodes on graph
     * @return
     */
    @Override
    public int nodeSize() {
        return nodes.size();
    }

    /**
     * returns the number of edges on graph
     * @return
     */
    @Override
    public int edgeSize() {
        return countEdge;
    }

    /**
     * returns the number of actions made on graph
     * @return
     */
    @Override
    public int getMC() {
        return countMC;
    }

    /**
     * returns a description of all the nodes and edges of the graph
     * @return
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb.append("\nNodes:\n");
        sb2.append("\nEdges:\n");
        for (node_data n: nodes.values()) {
            sb.append("key: "+n.getKey() + "\n");
            for (edge_data e: edges.get(n.getKey()).values()){
                sb2.append("src: "+e.getSrc()+", dest: "+e.getDest()+", weight: "+e.getWeight()+"\n");
            }
        }
        sb.append(sb2);
        return sb.toString();
    }
}
