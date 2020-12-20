import api.*;
import org.json.JSONException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class test_algo_3 {
    public static void main(String[] args) throws JSONException, IOException {
        dw_graph_algorithms al = new DWGraph_Algo();
        DWGraph_DS gr = new DWGraph_DS();

        for (int i = 0; i < 200; i++) {
            double rnd = Math.random()*100;
            gr.addNode( new NodeData(i,rnd, rnd/2,0 ));
        }

        for (int i = 0; i < 600; i++) {
            int rnd1 = (int) (Math.random()*100);
            int rnd2 = (int) (Math.random()*100);
            while(rnd1 == rnd2) {
                rnd2 = (int) (Math.random() * 100);
            }
            double w = Math.random()*100;gr.connect(rnd1,rnd2,w);
        }

        al.init(gr);
        al.save("save3.txt");
        dw_graph_algorithms al2 = new DWGraph_Algo();
        al2.load("save3.txt");

        testConnected(al,al2);
//        testShortestDist(al,al2);
//        testShortestPath(al,al2);
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
