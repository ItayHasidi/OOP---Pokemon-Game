package api;

import jdk.swing.interop.SwingInterOpUtils;
import org.json.*;



import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {

    ////private variables////

    private directed_weighted_graph graph;
    private int mc;
    private static List<node_data> list = new ArrayList<node_data>();
    private HashMap<Integer, Double> newTag = new HashMap<Integer, Double>();

    /////////////////////////

    /**
     * initialises the dw_graph_algorithms with a directed_weighted_graph
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        if(g != null)
            this.graph = g;
    }

    /**
     * returns the initialized graph
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return graph;
    }

    /**
     * clones the graph and returns it
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(graph);
    }

    /**
     * checks if the graph is fully connected
     * @return
     */
    @Override
    public boolean isConnected() {
        if (this.graph == null || this.graph.nodeSize() == 0 || this.graph.nodeSize() == 1) {
            return true;
        }
        for (node_data n: graph.getV()) {
            resetTag(0);
            Queue<Integer> q = new LinkedList<Integer>();
            int count = 0, res = 0;
            if (graph.nodeSize() == 0 || graph.nodeSize() == 1) return true;
            Collection<edge_data> col_node = graph.getE(n.getKey());
            Iterator<edge_data> itr_node = col_node.iterator();
            if (itr_node.hasNext()) {
                edge_data temp_node = itr_node.next();
                graph.getNode(temp_node.getDest()).setTag(1);
                count++;
                q.add(temp_node.getDest());
            }
            while (!q.isEmpty()) {
                int temp_Ni = q.poll();
                if (temp_Ni >= 0) {
                    Collection<edge_data> col = graph.getE(temp_Ni);
                    Iterator<edge_data> itr = col.iterator();
                    while (itr.hasNext()) {
                        edge_data Ni = itr.next();
                        if (graph.getNode(Ni.getDest()).getTag() == 0) {
                            count++;
                            graph.getNode(Ni.getDest()).setTag(1);
                            q.add(Ni.getDest());
                        }
                    }
                }
            }
            if (count != graph.nodeSize()) return false;
        }
        return true;
    }

    /**
     * sets the tag of all nodes in the graph to the given value: val.
     * @param val
     */
    private void resetTag(int val) {
        for (node_data index : graph.getV()) {
            index.setTag(val);
        }
    }

    /**
     * calculates and returns the shortest path from - src, to - dest
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (this.graph.getNode(src) != null && this.graph.getNode(dest) != null) {
            if (src == dest || graph.nodeSize() == 0 || graph.nodeSize() == 1) {
                return 0;
            }
            for (node_data n: graph.getV()) {
                newTag.putIfAbsent(n.getKey(),-1.0);
            }
            this.mc = this.graph.getMC();
            newTag.replace(src,0.0);
            Queue<node_data> q = new LinkedList<node_data>();
            q.add(this.graph.getNode(src));
            while (!q.isEmpty()) {
                node_data temp = q.poll();
                double length = newTag.get(temp.getKey()); // tag of the src
                for (edge_data i : this.graph.getE(temp.getKey())) {
                    double length_i = i.getWeight(); // weight between src to dest
                    if (newTag.get(i.getDest()) < 0 || newTag.get(i.getDest()) > length + length_i) {
                        newTag.replace(i.getDest(), length + length_i);
                        q.add(graph.getNode(i.getDest()));
                    }
                }
            }
        }
        if (src == dest || graph.nodeSize() == 0 || graph.nodeSize() == 1) {
            return 0;
        }
        return newTag.get(dest);
    }

    /**
     * returns an organized list of all nodes of the shortest path
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if (src == dest || this.graph.nodeSize() == 0) {
            return list;
        }
        int chkMC = this.graph.getMC();
        if (list.isEmpty() || chkMC != mc) {
            shortestPathDist(src, dest);
        }
        directed_weighted_graph revG = reverseGraph();
        list.add(this.graph.getNode(dest));
        Stack<node_data> stack = new Stack<node_data>();
        node_data last =graph.getNode(dest);
        stack.push(last);
        for (int i = 0; i < revG.nodeSize(); i++) {
            for(edge_data edge: revG.getE(last.getKey())) {
                double tempNode = newTag.get(edge.getDest());
                double w = edge.getWeight();
                double destNode = newTag.get(last.getKey());
                if (tempNode + w  == destNode) {
                    stack.push(revG.getNode(edge.getDest()));
                    last = revG.getNode(edge.getDest());
                    break;
                }
            }
        }
        List<node_data> tor1 = new LinkedList<node_data>();
        while (!stack.isEmpty()){
            tor1.add(stack.pop());
        }
        return tor1;
    }


    /**
     * makes a new graph but switches the direction of all edges
     * @return
     */
    private directed_weighted_graph reverseGraph(){
        directed_weighted_graph g = new DWGraph_DS();
        for (node_data n: this.graph.getV()){
            g.addNode(new NodeData(n));
        }
        for(node_data n: this.graph.getV()) {
            for(edge_data e : this.graph.getE(n.getKey())){
               int src = e.getSrc(), dest = e.getDest();
               double w = e.getWeight();
               g.connect(dest,src,w);
            }
        }
        return g;
    }


    /**
     * saves the graph on a JASON file
     * @param file - the file name (may include a relative path).
     * @return
     * @throws JSONException
     */
    @Override
    public boolean save(String file) throws JSONException {
        JSONObject j = new JSONObject();
        JSONArray ja = new JSONArray();
        JSONArray jb = new JSONArray();
        for(node_data node: this.graph.getV()){
            JSONObject jNode = new JSONObject();
            double x = node.getLocation().x();
            double y = node.getLocation().y();
            double z = node.getLocation().z();
            String loc = x+","+y+","+z;
            jNode.put("pos", loc);
            jNode.put("id", node.getKey());
            jb.put(jNode);
            for(edge_data edge: this.graph.getE(node.getKey())){
                JSONObject jTemp = new JSONObject();
                jTemp.put("src", edge.getSrc());
                jTemp.put("w", edge.getWeight());
                jTemp.put("dest", edge.getDest());
                ja.put(jTemp);
            }
        }
        j.put("Edges",ja);
        j.put("Nodes", jb);
        try{
            PrintWriter pw = new PrintWriter(file);
            pw.write(j.toString());
            pw.close();
            return true;
        }
        catch (IOException ex){
            return false;
        }
    }

    /**
     * loads a new graph from a JASON file
     * @param file - file name of JSON file
     * @return
     */
    @Override
    public boolean load(String file) throws IOException {
        String content = Files.readString(Path.of(file), StandardCharsets.US_ASCII);
        try/*(FileReader f = new FileReader(file))*/ {
//            System.out.println(f.read());
//            Object obj = parser.parse(f);
            JSONObject parser = new JSONObject(content);
//            JSONObject jo = parser.getJSONObject(f.toString());
            JSONArray node = parser.getJSONArray("Nodes");
//            System.out.println(node);//TODO DELETE
            directed_weighted_graph gr = new DWGraph_DS();
            for (int i = 0; i < node.length(); i++) {
                JSONObject jNode = node.getJSONObject(i);
                String loc = jNode.getString("pos");
                long id = jNode.getLong("id");
                String[] xyz = loc.split(",");
                double x = Double.valueOf(xyz[0]);
                double y = Double.valueOf(xyz[1]);
                double z = Double.valueOf(xyz[2]);
                node_data newNode = new NodeData((int)id,x,y,z);
                gr.addNode(newNode);
            }
            JSONArray edgeJ =  parser.getJSONArray("Edges");
            for (int i = 0; i < edgeJ.length(); i++) {
                JSONObject jEdge = edgeJ.getJSONObject(i);
                long src =  jEdge.getLong("src");
                long dest =  jEdge.getLong("dest");
                Double w = jEdge.getDouble("w");
                gr.connect((int)src,(int)dest,w);
            }
            this.graph = gr;
            return true;
        }
        catch (JSONException ex){
            return false;
        }
    }

    /**
     * references to the to string function of DWGraph_DS
     * @return
     */
    @Override
    public String toString(){
        return graph.toString();
    }
}
