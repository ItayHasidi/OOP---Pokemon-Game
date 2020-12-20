import api.*;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

public class test_algo_1 {
    public static void main(String[] args) throws JSONException, IOException {
        dw_graph_algorithms al = new DWGraph_Algo();
        DWGraph_DS gr = new DWGraph_DS();
        node_data n1 = new NodeData(1 - 1, 1, 1, 0);
        node_data n2 = new NodeData(2 - 1, 2, 2, 0);
        node_data n3 = new NodeData(3 - 1, 3, 3, 0);
        node_data n4 = new NodeData(4 - 1, 4, 4, 0);
        node_data n5 = new NodeData(5 - 1, 5, 5, 0);
        node_data n6 = new NodeData(6 - 1, 6, 6, 0);
        node_data n7 = new NodeData(7 - 1, 7, 7, 0);
        gr.addNode(n1);
        gr.addNode(n2);
        gr.addNode(n3);
        gr.addNode(n4);
        gr.addNode(n5);
        gr.addNode(n6);
        gr.addNode(n7);
        gr.connect(n1.getKey(), n6.getKey(), 1);
        gr.connect(n2.getKey(), n3.getKey(), 11);
        gr.connect(n2.getKey(), n1.getKey(), 3);
        gr.connect(n3.getKey(), n5.getKey(), 6);
        gr.connect(n4.getKey(), n7.getKey(), 7);
        gr.connect(n4.getKey(), n2.getKey(), 10);
        gr.connect(n4.getKey(), n3.getKey(), 8);
        gr.connect(n5.getKey(), n1.getKey(), 2);
        gr.connect(n5.getKey(), n7.getKey(), 3);
        gr.connect(n5.getKey(), n4.getKey(), 5);
        gr.connect(n6.getKey(), n5.getKey(), 4);
        gr.connect(n7.getKey(), n6.getKey(), 5);
        al.init(gr);
//        System.out.println(al.toString());
        al.save("save3.txt");
        dw_graph_algorithms al2 = new DWGraph_Algo();
        al2.load("save3.txt");

        testConnected(al,al2);
        testShortestDist(al,al2);
        testShortestPath(al,al2);
    }

    public static void testConnected(dw_graph_algorithms al, dw_graph_algorithms al2) {

        assertTrue(al.isConnected());
        assertTrue(al2.isConnected());
    }
    public static void testShortestDist(dw_graph_algorithms al, dw_graph_algorithms al2) {

        assertEquals(8.0+"", al.shortestPathDist(0,6)+"");
        assertEquals(8.0+"", al2.shortestPathDist(0,6)+"");
    }
    public static void testShortestPath(dw_graph_algorithms al, dw_graph_algorithms al2) {

        assertEquals(4+"", al.shortestPath(0,6).size()+"");
        assertEquals(4+"", al2.shortestPath(0,6).size()+"");
    }
}
