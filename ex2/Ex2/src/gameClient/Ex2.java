package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class Ex2 extends JFrame implements Runnable {

    /**
     * a private class that saves pokemon values with index
     */
    private static class pokeVal implements Comparable<pokeVal>  {
        private int key = -1;
        private double value = -1;

        /**
         * constructor
         * @param key
         * @param value
         */
        public pokeVal(int key, double value) {
            this.key = key;
            this.value = value;
        }

        /**
         * returns the id
         * @return
         */
        public int getKey() {
            return key;
        }

        /**
         * sets the id
         * @param key
         */
        public void setKey(int key) {
            this.key = key;
        }

        /**
         * returns the value
         * @return
         */
        public double getValue() {
            return value;
        }

        /**
         * sets the value
         * @param value
         */
        public void setValue(double value) {
            this.value = value;
        }

        /**
         * a simple toString
         * @return
         */
        @Override
        public String toString() {
            return ""+value;
        }

        /**
         * compare function
         * @param o
         * @return
         */
        @Override
        public int compareTo(@NotNull pokeVal o) {
            return toString().compareTo(o.toString());
        }
    }

    //// private variables ////

    private static MyFrame _win;
    private static Arena _ar;
    private HashMap<Integer,String> hash = new HashMap<Integer, String>();
    private static double gradeChk = 0;
    private static HashMap<Integer, CL_Pokemon> currPok = new HashMap<Integer, CL_Pokemon>();
    private static int scenario_num;
    private static Thread client;
    private static game_service game;
    private static int dt = 100;
    private static int bugCounter = 0;

    public static Ex2 ex2;

    private JFrame frame;
    private JPanel panel1;
    private JComboBox lvlSelCombo;
    private JLabel lvlSelLabel;
    private JButton strtButton;
    private JButton stpButton;
    private JLabel timeLabel;
    private JLabel score0Label;
    private JLabel score1Label;
    private JLabel score2Label;
    private JPanel graphPanel;
    private JLabel scoreEndLabel;
    private JLabel chosenLvl;
    private JLabel endMoves;
    private int _ind;
    private gameClient.util.Range2Range _w2f;
    private int time, timeLeft=0;
    public Integer timeToEnd;
    private String gameData = new String();
    private Image im;
    private Graphics g2;

    ///////////////////////////

    /**
     * the GUI main function
     * initialises all the GUI objects
     */
    public Ex2(){
//        super(a);
//        int _ind = 0;
        frame = new JFrame();
        panel1 = new JPanel();

        frame.setSize(1000,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pokemon Game");
        frame.add(panel1);
        panel1.setLayout(null);

//        ImageIcon im = new ImageIcon("images/ashSmall.png");
////        im.
//        JLabel imLab = new JLabel(im);
//        imLab.setBounds(300,300,10,10);
//        panel1.add(imLab);

        lvlSelLabel = new JLabel("Select the level: ");
        lvlSelLabel.setBounds(5,5,100,25);

        timeLabel = new JLabel("Seconds left: ");
        timeLabel.setBounds(5,65,200,25);
        timeLabel.setVisible(false);


        Integer[] arr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        lvlSelCombo = new JComboBox(arr);
        lvlSelCombo.setEditable(true);
        lvlSelCombo.setBounds(110,5,50,25);
        lvlSelCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scenario_num = lvlSelCombo.getSelectedIndex();
                if(game == null || !game.isRunning())
                    chosenLvl.setText("Current level: "+scenario_num);
            }
        });


        graphPanel = new JPanel();
        graphPanel.setBounds(5,130,975,425);





        stpButton = new JButton("Stop Game");
        stpButton.setBounds(320,5,100,25);
        stpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.isRunning())
                    game.stopGame();
            }
        });


        score0Label = new JLabel("Agent 0 has: xxx points");
        score0Label.setBounds(5,35,160,25);
        score0Label.setVisible(false);

        score1Label = new JLabel("Agent 1 has: xxx points");
        score1Label.setBounds(180,35,160,25);
        score1Label.setVisible(false);

        score2Label = new JLabel("Agent 2 has: xxx points");
        score2Label.setBounds(360,35,160,25);
        score2Label.setVisible(false);

        scoreEndLabel = new JLabel("Total score: ");
        scoreEndLabel.setBounds(130,90,150,25);
        scoreEndLabel.setVisible(false);

        endMoves = new JLabel("Total moves: ");
        endMoves.setBounds(280,90,150,25);
        endMoves.setVisible(false);

        chosenLvl = new JLabel("Current level: 0");
        chosenLvl.setBounds(5,90,100,25);
//        chosenLvl.setVisible(false);


        strtButton = new JButton("Start Game");
        strtButton.setBounds(200,5,100,25);
        strtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {




                if(game == null || !game.isRunning()) {
//                    score0Label.setText("");
//
                    graphPanel = new JPanel();
                    graphPanel.setBounds(5,130,975,425);
                    graphPanel.setVisible(true);
                    panel1.add(graphPanel);
                    try {
                        _win = new MyFrame();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    graphPanel.add(_win);
                    client = new Thread(ex2);
                    client.start();
//                    score0Label.setVisible(false);
//                    score1Label.setVisible(false);
//                    score2Label.setVisible(false);
//                    scoreEndLabel.setVisible(false);
//                    endMoves.setVisible(false);
                }
//                else if(game.isRunning())
//                    game.stopGame();

            }
        });

        panel1.add(endMoves);
        panel1.add(chosenLvl);
        panel1.add(scoreEndLabel);
        panel1.add(graphPanel);
        panel1.add(lvlSelCombo);
        panel1.add(lvlSelLabel);
        panel1.add(strtButton);
        panel1.add(stpButton);
        panel1.add(timeLabel);
        panel1.add(score0Label);
        panel1.add(score1Label);
        panel1.add(score2Label);

//        frame.add(panel1);
//        frame.add(graphPanel);

        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * the main function that starts the program
     * @param args
     * @throws FileNotFoundException
     * @throws JSONException
     */
    public static void main(String args[]) throws FileNotFoundException, JSONException {
        ex2 = new Ex2();
    }

    /**
     * main run function the runs the game and calls for calculations
     */
    @Override
    public void run() {
        directed_weighted_graph gg = new DWGraph_DS();
        dw_graph_algorithms g_algo = new DWGraph_Algo();
        game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
//        	int id = 209048313;
//        	game.login(id);
        String g = game.getGraph();
        String pks = game.getPokemons();
        int agNum = 0;
        try {
            PrintWriter out = new PrintWriter("data/graph.txt");
            out.write(game.getGraph());
            out.close();
            g_algo.init(gg);
            g_algo.load("data/graph.txt");
            gg = g_algo.getGraph();
            init(game);
            JSONObject tempG = new JSONObject(game.toString());
            JSONObject tempG2 = tempG.getJSONObject("GameServer");
            agNum = tempG2.getInt("agents");
        }
        catch (Exception ex){ }

        List<CL_Pokemon> list = Arena.json2Pokemons(game.getPokemons());
        List<CL_Agent> agList = Arena.getAgents(game.getAgents(), gg);
        game.startGame();

        moveAgents(game, gg);

        int ind = 0;
        dt = 100;
        try {
            setNextPok(gg, game.getPokemons(), game.getAgents());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int agNum2 = _ar.getAgents().size();
        if (agNum2 == 1){
            score0Label.setVisible(true);
        }
        else if (agNum2 == 2){
            score0Label.setVisible(true);
            score1Label.setVisible(true);
        }
        else {
            score0Label.setVisible(true);
            score1Label.setVisible(true);
            score2Label.setVisible(true);
        }
        chosenLvl.setText("Current level: "+scenario_num);

        timeLabel.setVisible(true);
        while(game.isRunning()) {
            timeLabel.setText("Seconds left: "+(double) game.timeToEnd()/1000);
            List<CL_Agent> agentList = _ar.getAgents();
            if (agNum2 == 1){
                score0Label.setText("Agent 0 has: "+agentList.get(0).getValue()+" points");
            }
            else if (agNum2 == 2){
                score0Label.setText("Agent 0 has: "+agentList.get(0).getValue()+" points");
                score1Label.setText("Agent 1 has: "+agentList.get(1).getValue()+" points");
            }
            else {
                score0Label.setText("Agent 0 has: "+agentList.get(0).getValue()+" points");
                score1Label.setText("Agent 1 has: "+agentList.get(1).getValue()+" points");
                score2Label.setText("Agent 2 has: "+agentList.get(2).getValue()+" points");
            }

            try {
                JSONObject tempG = new JSONObject(game.toString());
                JSONObject tempG2 = tempG.getJSONObject("GameServer");
                JSONObject tempG1 = new JSONObject(game.toString());
                JSONObject tempG21 = tempG.getJSONObject("GameServer");
                double gradeChk1 = tempG2.getDouble("grade");
                if (gradeChk1 > gradeChk) {
                    gradeChk = gradeChk1;
                    setNextPok(gg, game.getPokemons(), game.getAgents());
                }
                agList = Arena.getAgents(game.getAgents(), gg);
            } catch (Exception ex){}

            moveAgents(game, gg);
            try {
                if(ind%1==0) {_win.repaint();}
                Thread.sleep(dt);
//                if(dtChanger != -1) {
//                    agentList.get(dtChanger).setSpeed(agentList.get(dtChanger).getSpeed()*2);
//                    dt = 100;
//                }
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();
        scoreEndLabel.setVisible(true);
        double endScore = 0;
        for (int i = 0; i < agList.size(); i++) {
            endScore += agList.get(i).getValue();
        }
        JSONObject tempG = null;
        int moves = 0;
        try {
            tempG = new JSONObject(game.toString());
            JSONObject tempG2 = tempG.getJSONObject("GameServer");
            moves = tempG2.getInt("moves");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        endMoves.setVisible(true);
        scoreEndLabel.setText("Total score: "+endScore);
        endMoves.setText("Total moves: "+moves);
        timeLabel.setText("Seconds left: 0.000");
        System.out.println(res);
        System.exit(0);
    }

    /**
     * Moves each of the agents along the edge,
     * in case a pokemon has been caught, gives all agents a new target (pokemon).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgents(game_service game, directed_weighted_graph gg) {
        int id = 0;
        try {
            int agNum = _ar.getAgents().size();
            double[] grades = new double[agNum];
            for (int i = 0; i < agNum; i++) {
                grades[i] = _ar.getAgents().get(i).getValue();
            }
            
            JSONObject tempG = new JSONObject(game.toString());
            JSONObject tempG2 = tempG.getJSONObject("GameServer");
            gradeChk = tempG2.getDouble("grade");

            String lg = game.move();
            List<CL_Agent> log = Arena.getAgents(lg, gg);
            _ar.setAgents(log);
            //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
            String fs = game.getPokemons();
            List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
            _ar.setPokemons(ffs);
            int i = 0;
            for (CL_Agent ag: log) {
                id = ag.getID();
                int dest = ag.getNextNode();
                int src = ag.getSrcNode();
                double v = ag.getValue();
                if (dest == -1) {

                    dest = nextNode(gg, ag);
                    game.chooseNextEdge(ag.getID(), dest);
                    System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
//                    System.out.println(id+","+v+","+game.timeToEnd());
//                    JSONObject tempG1 = new JSONObject(game.toString());
//                    JSONObject tempG21 = tempG.getJSONObject("GameServer");
//                    double gradeChk1 = tempG2.getDouble("grade");
                    double gradeChk1 = _ar.getAgents().get(i).getValue();
//                    if(gradeChk1 == grades[i] && currPok.get(i).get_edge().getDest() == ag.getSrcNode()){
//                        bugCounter++;
////                            System.out.println("bug!!!!!!!!!");
//                        if(bugCounter == 2){
//                            System.out.println("bug counter = 2");
//                            CL_Pokemon temp;
//                            for (int j = 0; j < ffs.size(); j++) {
//                                if(currPok.get(i) != ffs.get(j)) {
//                                    temp = ffs.get(j);
//                                    currPok.put(i, temp);
//                                    break;
//                                }
//                                nextNode(gg, ag);
//                            }
//                            bugCounter = 0;
//                        }
////                            dt = 90;
////                            dtChanger = ag.getID();
////                            ag.setSpeed(ag.getSpeed()/2);
//                    }
//                    else {
//                        bugCounter = 0;
//                    }
                }
                i++;
            }
            JSONObject tempG1 = new JSONObject(game.toString());
            JSONObject tempG21 = tempG.getJSONObject("GameServer");
            double gradeChk1 = tempG2.getDouble("grade");


            if (gradeChk1 > gradeChk) {
                gradeChk = gradeChk1;
                setNextPok(gg, game.getPokemons(), game.getAgents());
            }
        }
        catch (Exception ex){}
    }

    private static int lastIndex1 = -1, lastIndex2 = -1, lastIndex3 = -1, lastIndex4 = -1;
    /**
     * gives the agent a new target (edge) according to the path to his target (pokemon)
     * @param g
     * @param ag
     * @return
     */
    private static int nextNode(directed_weighted_graph g, CL_Agent ag) throws JSONException {

        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(g);
        int src = ag.getSrcNode();
        int dest = currPok.get(ag.getID()).get_edge().getDest(); //ag.get_curr_fruit().get_edge().getDest();
        if(ag.getSrcNode() == dest/* && currPok.get(ag.getID()).getType() == -1*/){
            dest = currPok.get(ag.getID()).get_edge().getSrc();
        }
        List<node_data> list = algo.shortestPath(src, dest);
        return list.get(1).getKey();

    }

    /**
     * calculates and gives all agents a new target (pokemon) that has the highest value to them
     * (according to a calculation of distance and value of the pokemon, also if more than one pokemon is on an edge,
     * the function will consider it as one pokemon)
     *
     * @param g
     * @param pokemon
     * @param agent
     * @throws JSONException
     */
    private static pokeVal[][] setNextPok(directed_weighted_graph g, String pokemon,String agent) throws JSONException {


        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(g);
        try {
            List<CL_Pokemon> pokeList = Arena.json2Pokemons(pokemon);//_ar.getPokemons();
            List<CL_Agent> agentList = Arena.getAgents(agent,g);    // _ar.getAgents();
            List<CL_Pokemon> usedPoke = new ArrayList<>();

            pokeVal[][] arr = new pokeVal[agentList.size()][pokeList.size()];





            for (CL_Pokemon tempPoke: pokeList){
                edge_data firstPoke = tempPoke.get_edge();

                int type1 = tempPoke.getType();
                for (CL_Pokemon tempPoke2: pokeList){
                    if(tempPoke != tempPoke2){
                        edge_data secondPoke = tempPoke2.get_edge();
                        int type2 = tempPoke2.getType();
                        if (firstPoke == secondPoke && type1 == type2)
                            tempPoke.set_value(tempPoke.getValue() + tempPoke2.getValue());
                    }
                }
            }
            _ar.setPokemons(pokeList);
            int i = 0;

            for (CL_Agent tempAgent: agentList){
                int src = tempAgent.getSrcNode();
                int j = 0;


                for (CL_Pokemon tempPoke : pokeList) {
                    Arena.updateEdge(tempPoke,g);
                    int dest = 0;
                    dest = tempPoke.get_edge().getSrc();//TODO check if this is correct
                    double dis = algo.shortestPathDist(src, dest) + tempPoke.getLocation().distance(g.getNode(dest).getLocation());
                    double val = dis / tempPoke.getValue(); // TODO CHECK IF ADDING SPEED GIVES A BETTER GRADE
                    pokeVal temp = new pokeVal(j, val);
                    arr[i][j] = temp;
                    j++;
                }

                i++;
            }
            for (int j = 0; j < arr.length; j += 2) {
                Arrays.sort(arr[j], new Comparator<pokeVal>() {
                    @Override
                    public int compare(pokeVal o1, pokeVal o2) {
                        return o1.compareTo(o2);
                    }
                });
            }
            int j =0;
            boolean flag = true;
            for(CL_Agent tempAgent: agentList){
                flag = true;

//                lastIndex4 = lastIndex3;
//                lastIndex3 = lastIndex2;
//                lastIndex2 = lastIndex1;
//                lastIndex1 = tempAgent.getSrcNode();

//                    pokeVal arr2[][] = setNextPok(g, game.getPokemons(), game.getAgents());
//                    CL_Pokemon tempPoke = currPok.get(tempAgent.getID());
//                    boolean flag2 = true;
//                    for (int i2 = 0; i2 < arr2.length && fla2g; i2++) {
//                        if(arr[tempAgent.getID()][i2] != tempPoke){
//                            flag2 = false;
//                            currPok.put(tempAgent.getID(), arr2[tempAgent.getID()][i2]);
//                        }
//                    }


                if(tempAgent.getNextNode() != -1 || tempAgent.get_curr_edge() != null){
                    for (int k = 0; k < arr[j].length && flag; k++) {
                        CL_Pokemon tempPoke = pokeList.get(arr[j][k].getKey());
//                        if(lastIndex4 == lastIndex2 && lastIndex3 == lastIndex1){
//                            usedPoke.add(tempPoke);
//                        }
                        if (!usedPoke.contains(tempPoke)) {
                            currPok.put(tempAgent.getID(), tempPoke);
                            usedPoke.add(tempPoke);
                            flag = false;
                        }
                    }
                }
                j++;
            }
            _ar.setAgents(agentList);
            return arr;
        }
        catch (Exception ex){
            return null;
        }

    }

    /**
     * initialises the game
     * @param game
     * @throws IOException
     */
    private void init(game_service game) throws IOException {
        String g = game.getGraph();
        String fs = game.getPokemons();
        String graph = game.getGraph();
        dw_graph_algorithms g_algo = new DWGraph_Algo();
        PrintWriter out = new PrintWriter("data/graph.txt");
        out.write(graph);
        out.close();
        g_algo.load("data/graph.txt");
        directed_weighted_graph gg = g_algo.getGraph();
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win.setVisible(true);
        _win.setSize(975, 425);
        _win.update(_ar);
        graphPanel.add(_win);
//        graphPanel.add(_win.ashLabel);
//        graphPanel.add(_win.pikachuLabel);
//        graphPanel.add(_win.bulbassaurLabel);
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            edge_data[] edgeArr = new EdgeData[rs];
            Collections.sort(cl_fs, new Comparator<CL_Pokemon>() {

                public int compare(CL_Pokemon o1, CL_Pokemon o2) {
                    return o2.compareTo(o1);
                }
            });
            for(int a = 0; a < rs ; a++) {
                Arena.updateEdge(cl_fs.get(a),gg);
                game.addAgent(cl_fs.get(a).get_edge().getSrc());
                edgeArr[a] = cl_fs.get(a).get_edge();
                currPok.put(a, cl_fs.get(a));
            }
            _ar.setAgents(Arena.getAgents(game.getAgents(), gg));
            List<CL_Agent> agentList = Arena.getAgents(game.getAgents(), gg);

            for (int i = 0; i < rs; i++) {
                agentList.get(i).setNextNode(edgeArr[i].getDest());
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }


}
