package it.unicam.cs.pa2021.formulaUno;

/**
 * Interfaccia per descrivere le azioni di monitorazione della vista sul Modello
 *
 * @param <P> tipo per la Posizione delle Macchine.
 */
public interface CircuitUpdateListener<P> {

    /**
     * Avverte quando una Macchina non &grave; piu' in gioco.
     *
     * @param car Macchina non piu' in gioco.
     */
    void aCarWentOutOrCrashed(Car<P> car);

    /**
     * Avverte quando una Macchina &grave; mossa.
     *
     * @param car
     */
    void aCarMoved(Car<P> car);

    /**
     * Avverte quando una Macchina taglia il traguardo.
     */
    void aCarWon();

}
