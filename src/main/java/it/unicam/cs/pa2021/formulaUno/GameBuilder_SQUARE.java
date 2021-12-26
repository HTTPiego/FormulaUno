package it.unicam.cs.pa2021.formulaUno;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class GameBuilder_SQUARE implements GameBuilder<GraphCircuit_DS, Car<Node>> {

    private int min_X;

    private int max_X;

    private int min_Y;

    private int max_Y;

    private GraphCircuit_DS graphCircuit_ds;

    private List<Car<Node>> cars;

    public GameBuilder_SQUARE(){
        this.graphCircuit_ds = new GraphCircuit_DS();
        this.cars = new ArrayList<>();
        this.min_X = Integer.MAX_VALUE;
        this.max_X = Integer.MIN_VALUE;
        this.min_Y = Integer.MAX_VALUE;
        this.max_Y = Integer.MIN_VALUE;
    }

    @Override
    public int getMin_X() {
        return this.min_X;
    }

    @Override
    public int getMax_X() {
        return this.max_X;
    }

    @Override
    public int getMin_Y() {
        return this.min_Y;
    }

    @Override
    public int getMax_Y() {
        return this.max_Y;
    }

    @Override
    public List<Car<Node>> getCars() {
        return this.cars;
    }

    @Override
    public void build(int totNumOfPlayers) throws RuntimeException{
        this.buildGraphAndCars(totNumOfPlayers);
    }

    @Override
    public GraphCircuit_DS getGraphCircuit_DS() {
        return this.graphCircuit_ds;
    }


    /**
     * Costruisce il Circuito per pezzi.
     * Le Macchine sono costruite in "startingLine" e posizionate
     * nei suoi nodi di start.
     */
    private void buildGraphAndCars(int totNumOfPlayers){
        /*Runnable startingLine = () -> this.startingLine(totNumOfPlayers);
        Runnable endingLine = () -> this.endingLine(totNumOfPlayers);
        Runnable squareL = () -> this.squareL(totNumOfPlayers);
        Runnable squareTL = () -> this.squareTL(totNumOfPlayers);
        Runnable squareT = () -> this.squareT(totNumOfPlayers);
        Runnable squareTR = () -> this.squareTR(totNumOfPlayers);
        Runnable squareR = () -> this.squareR(totNumOfPlayers);
        Runnable squareBR = () -> this.squareBR(totNumOfPlayers);
        Runnable squareB = () -> this.squreB(totNumOfPlayers);
        Runnable square = () -> this.squreBL(totNumOfPlayers);
        Runnable outsideNode = () -> this.outsideNodes(totNumOfPlayers);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(startingLine);
        executorService.execute(endingLine);
        executorService.execute(squareL);
        executorService.execute(squareTL);
        executorService.execute(squareT);
        executorService.execute(squareTR);
        executorService.execute(squareR);
        executorService.execute(squareBR);
        executorService.execute(squareB);
        executorService.execute(square);
        executorService.execute(outsideNode);*/
        this.startingLine(totNumOfPlayers);
        this.endingLine(totNumOfPlayers);
        this.squareL(totNumOfPlayers);
        this.squareTL(totNumOfPlayers);
        this.squareT(totNumOfPlayers);
        this.squareTR(totNumOfPlayers);
        this.squareR(totNumOfPlayers);
        this.squareBR(totNumOfPlayers);
        this.squreB(totNumOfPlayers);
        this.squreBL(totNumOfPlayers);
        this.outsideNodes(totNumOfPlayers);
    }

    synchronized private void outsideNodes(int totNumOfPlayers){
        this.exernalSide_L_R(totNumOfPlayers, -1);
        this.exernalSide_L_R(totNumOfPlayers, (((totNumOfPlayers*2)+1)*3));//-1
        this.exernalSide_T_B(totNumOfPlayers, -1);//
        this.exernalSide_T_B(totNumOfPlayers, ((totNumOfPlayers*2)+1)*3);
        this.innerSide_L_R(totNumOfPlayers, ((totNumOfPlayers*2)+1));
        this.innerSide_L_R(totNumOfPlayers, (((totNumOfPlayers*2)+1)*2)-1);
        this.innerSide_T_B(totNumOfPlayers, ((totNumOfPlayers*2)+1));
        this.innerSide_T_B(totNumOfPlayers, (((totNumOfPlayers*2)+1)*2)-1);
    }

    private void updateMin_X(int x){
        if (x < this.min_X)
            this.min_X = x;
    }

    private void updateMax_X(int x){
        if (x > this.max_X)
            this.max_X = x;
    }

    private void updateMin_Y(int y){
        if (y < this.min_Y)
            this.min_Y = y;
    }

    private void updateMax_Y(int y){
        if (y > this.max_Y)
            this.max_Y = y;
    }

    private void normalNodesForLoop(int x_start, int x_bound, int y_start, int y_bound){
        for (int x = x_start; x < x_bound; ++x){
            for (int y = y_start; y < y_bound; ++y){
                Node node = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                        false, false, false);
                if (!this.graphCircuit_ds.addNode(node))
                    throw new RuntimeException("Circuit structure is not valid, overlapped Nodes: " +
                            "tridimentional Circuits are coming soon");
                this.updateMax_X(x);
                this.updateMin_X(x);
                this.updateMax_Y(y);
                this.updateMin_Y(y);
            }
        }
    }

    private void exernalSide_L_R(int totNumOfPlayers, int x){
        for (int y = -1; y <= ((totNumOfPlayers*2)+1)*3; ++y){
            Node out = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                    false, false, true);
            if (!this.graphCircuit_ds.addNode(out))
                throw new RuntimeException("Circuit structure is not valid, overlapped Nodes: " +
                                            "tridimentional Circuits are coming soon");
        }
    }

    private void exernalSide_T_B (int totNumOfPlayers, int y){
        for (int x = 0; x < ((totNumOfPlayers*2)+1)*3; ++x){
            Node out = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                    false, false, true);
            if (!this.graphCircuit_ds.addNode(out))
                throw new RuntimeException("Circuit structure is not valid, overlapped Nodes: " +
                                            "tridimentional Circuits are coming soon");
        }
    }

    private void innerSide_L_R (int totNumOfPlayers, int x){
        for (int y = ((totNumOfPlayers*2)+1); y < ((totNumOfPlayers*2)+1)*2; ++y){
            Node out = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                    false, false, true);
            if (!this.graphCircuit_ds.addNode(out))
                throw new RuntimeException("Circuit structure is not valid, overlapped Nodes: " +
                                            "tridimentional Circuits are coming soon");
        }
    }

    private void innerSide_T_B (int totNumOfPlayers, int y){
        for (int x = ((totNumOfPlayers*2)+1)+1; x < (((totNumOfPlayers*2)+1)*2)-1; ++x){
            Node out = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                    false, false, true);
            if (!this.graphCircuit_ds.addNode(out))
                throw new RuntimeException("Circuit structure is not valid, overlapped Nodes: " +
                                            "tridimentional Circuits are coming soon");
        }
    }

    private void endingLine(int totNumOfPlayers){
        int y = ((totNumOfPlayers*2)+1);
        for (int x = 0; x<((totNumOfPlayers*2)+1); ++x) {
            Node endingNode = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                    false, true, false);
            if (!this.graphCircuit_ds.addNode(endingNode))
                throw new RuntimeException("Circuit structure is not valid, overlapped Nodes: " +
                                            "tridimentional Circuits are coming soon");
        }
    }

    synchronized private void startingLine(int totNumOfPlayers) throws RuntimeException{
        int nameNumber = 1;
        int y = ((totNumOfPlayers*2)+1)+1;
        for (int x = 0; x<((totNumOfPlayers*2)+1); ++x) {
            if (x % 2 != 0) {
                Node startingNode = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                        true, false, false);
                if (!this.graphCircuit_ds.addNode(startingNode))
                    throw new RuntimeException("Circuit structure is not valid, overlapped Nodes: " +
                                                "tridimentional Circuits are coming soon");
                Car<Node> car = new SimpleCar(startingNode, "car" + Integer.valueOf(nameNumber).toString());
                cars.add(car);
                startingNode.setCar(car);
                nameNumber++;
            } else {
                Node startingNode = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                        true, false, false);
                if (!this.graphCircuit_ds.addNode(startingNode))
                    throw new RuntimeException("Circuit structure is not valid, overlapped Nodes: " +
                                                "tridimentional Circuits are coming soon");
            }
        }
    }


    synchronized private void squreBL(int totNumOfPlayers) {
        this.normalNodesForLoop(0, ((totNumOfPlayers*2)+1), 0, ((totNumOfPlayers*2)+1));
    }

    synchronized private void squreB(int totNumOfPlayers) {
        this.normalNodesForLoop(((totNumOfPlayers*2)+1), ((totNumOfPlayers*2)+1)*2, 0, ((totNumOfPlayers*2)+1));
    }

    synchronized private void squareBR(int totNumOfPlayers) {
        this.normalNodesForLoop(((totNumOfPlayers*2)+1)*2, ((totNumOfPlayers*2)+1)*3, 0, ((totNumOfPlayers*2)+1));
    }

    synchronized private void squareR(int totNumOfPlayers) {
        this.normalNodesForLoop(((totNumOfPlayers*2)+1)*2, ((totNumOfPlayers*2)+1)*3,
                ((totNumOfPlayers*2)+1), ((totNumOfPlayers*2)+1)*2);
    }

    synchronized private void squareTR(int totNumOfPlayers) {
        this.normalNodesForLoop(((totNumOfPlayers*2)+1)*2, ((totNumOfPlayers*2)+1)*3,
                ((totNumOfPlayers*2)+1)*2, ((totNumOfPlayers*2)+1)*3);
    }

    synchronized private void squareT(int totNumOfPlayers) {
        this.normalNodesForLoop(((totNumOfPlayers*2)+1), ((totNumOfPlayers*2)+1)*2,
                ((totNumOfPlayers*2)+1)*2, ((totNumOfPlayers*2)+1)*3);
    }

    synchronized private void squareTL(int totNumOfPlayers) {
        this.normalNodesForLoop(0, ((totNumOfPlayers*2)+1), ((totNumOfPlayers*2)+1)*2, ((totNumOfPlayers*2)+1)*3);
    }

    synchronized private void squareL(int totNumOfPlayers) {
        this.normalNodesForLoop(0, ((totNumOfPlayers*2)+1), ((totNumOfPlayers*2)+1)+2, ((totNumOfPlayers*2)+1)*2);
    }





}
