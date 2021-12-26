package it.unicam.cs.pa2021.formulaUno;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.logging.*;

public class DefaultController implements Controller<GameBuilder<GraphCircuit_DS, Car<Node>>, Node>{

    private GameBuilder<GraphCircuit_DS, Car<Node>> gameBuilder;

    private GraphCircuit graphCircuit;

    private Queue<Driver<Node>> turn;

    private static final Logger logger = Logger.getLogger("it.unicam.cs.pa2021.formulaUno.DefaultController");

    private static final Formatter formatter = new SimpleFormatter();

    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

    public DefaultController() {
        this.graphCircuit = null;
        this.turn = new ArrayDeque<>();
        logger.setLevel(Level.INFO);
        consoleHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler);
    }

    private List<Driver<Node>> buildDrivers(List<Car<Node>> cars, GraphCircuit graphCircuit){
        List<Driver<Node>> drivers = new ArrayList<>();
        int numberName = 1;
        for (Car<Node> car: cars){
            Driver<Node> driver = new BotDriver("bot"+Integer.valueOf(numberName).toString(),
                                                graphCircuit, car);
            drivers.add(driver);
            numberName++;
        }
        return drivers;
    }

    @Override
    public void newGame(GameBuilder<GraphCircuit_DS, Car<Node>> gameBuilder,
                        int totNumOfPlayers) {
        this.gameBuilder = gameBuilder;
        this.gameBuilder.build(totNumOfPlayers);
        this.graphCircuit = new GraphCircuit(this.gameBuilder.getGraphCircuit_DS(), this.gameBuilder.getCars());
        this.turn.addAll(this.buildDrivers(this.gameBuilder.getCars(), this.graphCircuit));
    }

    @Override
    public void Classfication() {
        List<Car<Node>> classificationList = this.graphCircuit.getClassification();
        this.printClassification(classificationList);
    }

    private void printClassification(List<Car<Node>> classification){
        if (classification == null)
            return;
        int position = 1;
        for (Car<Node> car: classification) {
            Driver<Node> driver = turn.
                                    stream().
                                    parallel().
                                    filter((drivers)->(car.equals(drivers.getCar()))).
                                    findAny().
                                    get();
            logger.info(Integer.valueOf(position).toString() + "° position for " + driver.getDriverName());
            position++;
        }
    }

    @Override
    public void getDriversStatus() {
        turn.stream().sequential().forEach(this::printStatus);
    }

    private void printStatus(Driver<Node> driver){
        logger.info(driver.getDriverName() + " is in Game");
    }

    @Override
    public void nextTurn() {
        Driver<Node> driver = this.turn.remove();
        driver.moveCar(BotDriver.calculateRandomPosition(this.graphCircuit, driver.getCar()));
        if (this.graphCircuit.carIsInRace(driver.getCar()))
            this.turn.add(driver);
        else
            logger.warning(driver.getDriverName() + " is out.");
    }

    @Override
    public boolean isGameOver() {
        Driver<Node> driver = this.turn.peek();
        boolean isGameOver = this.graphCircuit.didCarWin(driver.getCar());
        if (isGameOver)
            logger.info(driver.getDriverName() + " is the Winner");
        return isGameOver;
    }

    @Override
    public GameBuilder<GraphCircuit_DS, Car<Node>> getBuilder() {
        return this.gameBuilder;
    }

    @Override
    public void addListener(CircuitUpdateListener<Node> circuitUpdateListener) {
        this.graphCircuit.setCircuitUpdateSupporter(circuitUpdateListener);
    }

}
