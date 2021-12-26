package it.unicam.cs.pa2021.formulaUno;

import java.util.List;

public class GameBuilder_ZERO implements GameBuilder<GraphCircuit_DS, Car<Node>> {

    @Override
    public void build(int totNumOfPlayers) {
        throw new UnsupportedOperationException("Circuit is not avaible yet");
    }

    @Override
    public GraphCircuit_DS getGraphCircuit_DS() {
        return null;
    }

    @Override
    public List<Car<Node>> getCars() {
        return null;
    }

    @Override
    public int getMin_X() {
        return 0;
    }

    @Override
    public int getMax_X() {
        return 0;
    }

    @Override
    public int getMin_Y() {
        return 0;
    }

    @Override
    public int getMax_Y() {
        return 0;
    }

}
