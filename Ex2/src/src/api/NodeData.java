package api;

public class NodeData implements node_data {

    /**
     * private implementation of geo_location used later in this class
     */

    private class location implements geo_location{

        private double x, y, z, dis;

        /**
         * constructor
         * @param x
         * @param y
         * @param z
         */
        public location(double x, double y, double z){
            this.x = x;
            this.y = y;
            this.z = z;
            this.dis = 0;
        }

        /**
         * constructor
         * @param loc
         */
        public location(geo_location loc){
            this.x = loc.x();
            this.y = loc.y();
            this.z = loc.z();
            this.dis = distance(loc);
        }

        /**
         * returns the x value
         * @return
         */
        @Override
        public double x() {
            return x;
        }

        /**
         * returns the y value
         * @return
         */
        @Override
        public double y() {
            return y;
        }

        /**
         * returns the z value
         * @return
         */
        @Override
        public double z() {
            return z;
        }

        /**
         * calculates and returns the distance between the current point the given point
         * @param g
         * @return
         */
        @Override
        public double distance(geo_location g) {
            double pow_x = this.x - g.x();
            pow_x = Math.pow(pow_x, 2);
            double pow_y = this.y - g.y();
            pow_y = Math.pow(pow_y, 2);
            double pow_z = this.z - g.z();
            pow_z = Math.pow(pow_z, 2);
            double res = pow_x + pow_y + pow_z;
            res = Math.sqrt(res);
            return res;
        }

        /**
         * a simple to string function
         * @return
         */
        @Override
        public String toString(){
            String res = " x: "+x+", y: "+y+", z: "+z;
            return res;
        }
    }

    ////private variables////
    private static int keyCount = 0;
    private int key, tag;
    private geo_location loc;
    private double weight;
    private String info;

    /////////////////////////


    /**
     * constructor
     * @param key
     * @param x
     * @param y
     * @param z
     */
    public NodeData(int key, double x, double y, double z){
        this.key = key;
        this.tag = 0;
        this.loc = new location(x,y,z);
        this.weight = weight;
        this.info = "";
    }

    /**
     * constructor
     * @param key
     */
    public NodeData(int key){
        this.key = key;
        this.tag = 0;
//        this.loc = new location();
        this.weight = weight;
        this.info = "";
    }

    /**
     * constructor
     * @param loc
     * @param weight
     */
    public NodeData(geo_location loc, double weight){
        this.key = keyCount;
        keyCount++;
        this.tag = 0;
        this.loc = new location(loc);
        this.weight = weight;
        this.info = "";
    }

    /**
     * constructor
     * @param n
     */
    public NodeData(node_data n){
        this.info = n.getInfo();
        this.weight = n.getWeight();
        this.key = n.getKey();
        this.tag = n.getTag();
        this.loc = n.getLocation();
    }

    /**
     * returns the key of the node
     * @return
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * returns the location of the node on graph - geo_location
     * @return
     */
    @Override
    public geo_location getLocation() {
        return this.loc;
    }

    /**
     * sets the location of the node on the graph
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
//        this.loc = new location(p);
        this.loc = p;
    }

    /**
     * returns the weight of the node
     * @return
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /**
     * sets the weight of the node
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     *returns the meta data of the node
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * sets the meta data of the node
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * returns the tag of the node
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * sets the tag of the node
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
    public String toString(){
        String res = "id: "+ String.valueOf(key) + loc.toString();
        return res;
    }
}
