import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;


public class test_ds_1 {

    private static directed_weighted_graph g;

    public static void main(String[] args) {
        g = new DWGraph_DS();
        testAdd(g);
        testConnect(g);
        testRemove(g);
    }

    public static void testAdd(directed_weighted_graph g){
        for (int i = 0; i < 100; i++) {
            double rnd = Math.random()*100;
            g.addNode( new NodeData(i,rnd, rnd/2,0 ));
        }
//        assertEquals(100+"", g.nodeSize()+"");
        if(g.nodeSize() != 100)
            System.err.println("node size not correct");
    }

    public static void testConnect(directed_weighted_graph g){
        for (int i = 0; i < 300; i++) {
            int rnd1 = (int) (Math.random()*100);
            int rnd2 = (int) (Math.random()*100);
            while(rnd1 == rnd2) {
                rnd2 = (int) (Math.random() * 100);
            }
            double w = Math.random()*100;
            g.connect(rnd1,rnd2,w);
        }
//        assertEquals(300+"", g.edgeSize()+"");
        if(g.edgeSize() != 300)
            System.err.println("edge size not correct");
    }

    public static void testRemove(directed_weighted_graph g){
        for (int i = 0; i < 10; i++) {
            g.removeNode(i);
        }
//        assertEquals(90+"", g.nodeSize()+"");
        if(g.nodeSize() != 90)
            System.err.println("node size not correct");
    }
}
