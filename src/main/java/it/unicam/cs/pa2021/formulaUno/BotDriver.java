package it.unicam.cs.pa2021.formulaUno;

import java.util.*;

public class BotDriver implements Driver<Node>{

    private final String dirverName;

    private final GraphCircuit graphCircuit;

    private final Car<Node> driverCar;

    public BotDriver(String dirverName, GraphCircuit graphCircuit, Car<Node> driverCar) {
        this.dirverName = dirverName;
        this.graphCircuit = graphCircuit;
        this.driverCar = driverCar;
    }

    public static Node calculateRandomPosition(GraphCircuit graphCircuit, Car<Node> driverCar){
        List<Node> possibleMovements = new LinkedList<>(graphCircuit.possibleMovements(driverCar));
        if (possibleMovements.isEmpty())
            return null;
        Random random = new Random();
        return possibleMovements.get(random.nextInt(possibleMovements.size()));
    }

    @Override
    public void moveCar(Node position) {
        graphCircuit.updateCarPosition(this.driverCar, position);
    }

    @Override
    public Car<Node> getCar() {
        return this.driverCar;
    }

    @Override
    public String getDriverName() {
        return this.dirverName;
    }


}
