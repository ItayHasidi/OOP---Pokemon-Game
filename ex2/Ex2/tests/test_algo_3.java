import api.*;
import org.json.JSONException;

import java.io.IOException;

public class test_algo_3 {
    public static void main(String[] args) throws JSONException, IOException {
        dw_graph_algorithms al = new DWGraph_Algo();
        DWGraph_DS gr = new DWGraph_DS();

        for (int i = 0; i < 500; i++) {
            double rnd = Math.random()*500;
            gr.addNode( new NodeData(i,rnd, rnd/2,0 ));
        }

        for (int i = 0; i < 500; i++) {
//            int rnd1 = (int) (Math.random()*100);
//            int rnd2 = (int) (Math.random()*100);
//            while(rnd1 == rnd2) {
//                rnd2 = (int) (Math.random() * 100);
//            }
            for (int j = 0; j < 500; j++) {
                if(i != j) {
                    double w = Math.random() * 100;
                    gr.connect(i, j, w);
                }
            }
//            double w = Math.random()*100;
//            gr.connect(rnd1,rnd2,w);
        }

        al.init(gr);
        al.save("save3.txt");
        dw_graph_algorithms al2 = new DWGraph_Algo();
        al2.load("save3.txt");
//        System.out.println(gr.edgeSize()+" , "+gr.nodeSize());

        testConnected(al,al2);
//        testShortestDist(al,al2);
//        testShortestPath(al,al2);
    }

    public static void testConnected(dw_graph_algorithms al, dw_graph_algorithms al2) {

        boolean b1 = al.isConnected(), b2 = al2.isConnected();
        if(!b1 || !b2){
            System.err.println("al.isConnected(): "+b1+", al.isConnected(): "+b2);
        }
    }
    public static void testShortestDist(dw_graph_algorithms al, dw_graph_algorithms al2) {
        double b1 = al.shortestPathDist(0,6), b2 = al2.shortestPathDist(0,6);
        if(b1 != 8) {
            System.err.println("al hasn't the shortest path");
        }
        if(b2 != 8) {
            System.err.println("al2 hasn't the shortest path");
        }
    }
    public static void testShortestPath(dw_graph_algorithms al, dw_graph_algorithms al2) {
        int b1 = al.shortestPath(0,6).size(), b2 = al2.shortestPath(0,6).size();
        if(b1 != 4) {
            System.err.println("al hasn't the shortest path");
        }
        if(b2 != 4) {
            System.err.println("al2 hasn't the shortest path");
        }
    }
}
