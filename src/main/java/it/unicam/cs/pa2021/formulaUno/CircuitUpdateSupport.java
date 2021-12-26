package it.unicam.cs.pa2021.formulaUno;

/**
 * Classe di mediazione tra Modello e Vista.
 *
 * @param <P> tipo della Posizione di una Macchina.
 */
public final class CircuitUpdateSupport<P> {

    //List<>
    private CircuitUpdateListener<P> circuitUpdateListener;

    //synchronized
    public void setCircuitUpdateListener(CircuitUpdateListener<P> circuitUpdateListener) {
        this.circuitUpdateListener = circuitUpdateListener;
    }

    //synchronized
    public void aCarWentOutorCrashed(Car<P> car) {
        this.circuitUpdateListener.aCarWentOutOrCrashed(car);
    }

    //synchronized
    public void aCarMoved(Car<P> car) {
        this.circuitUpdateListener.aCarMoved(car);
    }

    //synchronized
    public void aCarWon() {
        this.circuitUpdateListener.aCarWon();
    }

}
