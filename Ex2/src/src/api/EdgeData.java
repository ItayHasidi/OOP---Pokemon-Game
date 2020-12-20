package api;

public class EdgeData implements edge_data{

    ////private variables////

    private int src, dest, tag;
    private double weight;
    private String info;

    /////////////////////////

    /**
     * constructor
     * @param src
     * @param dest
     * @param weight
     */
    public EdgeData(int src, int dest, double weight){
        this.tag = 0;
        this.info = "";
        this.dest = dest;
        this.src = src;
        this.weight = weight;
    }

    /**
     * constructor
     * @param edge
     */
    public EdgeData(EdgeData edge){
        this.tag = edge.getTag();
        this.info = edge.getInfo();
        this.dest = edge.getDest();
        this.src = edge.getSrc();
        this.weight = edge.getWeight();
    }

    /**
     * returns the id of the source node
     * @return
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * returns the id if the destination node
     * @return
     */
    @Override
    public int getDest() {
        return this.dest;
    }

    /**
     * returns the weight of the edge
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * returns the meta data of the edge
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * sets the given string as the meta data
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * returns the tag of the edge
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * sets a new tag for the edge
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;

    }

    /**
     * a simple to string function
     * @return
     */
    @Override
    public String toString() {
        String res = "src: "+src+", dest: "+dest+", weight: "+weight;
        return res;
    }
}
