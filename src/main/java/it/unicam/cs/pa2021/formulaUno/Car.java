package it.unicam.cs.pa2021.formulaUno;

import java.util.List;

/**
 * Interfaccia che definisce una generica Macchina.
 *
 * @param <P> Posizione della Macchina all'interno del circuito.
 */
public interface Car <P>{

    /**
     * Restituisce la precedente Posizione della Macchina.
     *
     * @return la precedente Posizione della Macchina.
     */
    P getPreviousPosition();

    /**
     * Aggiorna la precedente Posizione di una Macchina.
     *
     * @param position Posizione precedente in cui si trovava la Macchina.
     */
    void setPreviousPosition(P position);

    /**
     * Restituisce l'attuale Posizione della Macchina.
     *
     * @return l'attuale Posizione della Macchina.
     */
    P getPosition();

    /**
     * Aggiorna la posizione di una Macchina e
     * incrementa il numero degli spostamenti della Macchina.
     *
     * @param position Posizione in cui si sposta la Macchina.
     */
    void setPosition(P position);

    /**
     * Calcola la "posizione principale" sulla base della posizione attuale e precedente
     * della Macchina.
     *
     * @return la posione principale.
     * @apiNote la posione principale tornata al primo turno sarà quella soprastante alla posizione di start.
     */
    P calculateNextPosition();

    /**
     * Restituisce una Lista contenente tutto lo storico degli spostamenti della Macchina.
     *
     * @return la lista con tutte le Posizioni in cui &grave; stata la Macchina.
     */
    List<P> getRoute();

    /**
     * Restituisce il nome della Macchina.
     *
     * @return il nome della Macchina.
     */
    String getName();

}
