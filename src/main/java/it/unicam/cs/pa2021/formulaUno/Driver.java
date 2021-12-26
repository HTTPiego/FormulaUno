package it.unicam.cs.pa2021.formulaUno;

/**
 * Interfaccia che descrive le azioni di un Giocatore.
 *
 * @param <P> tipo per la Posizione della Macchina.
 */
public interface Driver <P>{

    /**
     * Muove la Macchina di un Giocatore in una certa Posizione nel Circuito.
     *
     * @param position Posizione in cui si vuole spostare la Macchina.
     */
    void moveCar(P position);

    Car<P> getCar();

    String getDriverName ();

}
