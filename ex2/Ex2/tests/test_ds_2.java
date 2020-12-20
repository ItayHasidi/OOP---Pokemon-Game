import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;

import static org.junit.Assert.assertEquals;

public class test_ds_2 {

    private static directed_weighted_graph g;

    public static void main(String[] args) {
        g = new DWGraph_DS();
        testAdd(g);
        testConnect(g);
        testRemove(g);
    }

    public static void testAdd(directed_weighted_graph g){
        for (int i = 0; i < 10000; i++) {
            double rnd = Math.random()*10000;
            g.addNode( new NodeData(i,rnd, rnd/2,0 ));
        }
        assertEquals(10000+"", g.nodeSize()+"");
    }

    public static void testConnect(directed_weighted_graph g){
        for (int i = 0; i < 30000; i++) {
            int rnd1 = (int) (Math.random()*10000);
            int rnd2 = (int) (Math.random()*10000);
            while(rnd1 == rnd2) {
                rnd2 = (int) (Math.random() * 100);
            }
            double w = Math.random()*10000;
            g.connect(rnd1,rnd2,w);
        }
        assertEquals(30000+"", g.edgeSize()+"");
    }

    public static void testRemove(directed_weighted_graph g){
        for (int i = 0; i < 1000; i++) {
            g.removeNode(i);
        }
        assertEquals(9000+"", g.nodeSize()+"");
    }
}
