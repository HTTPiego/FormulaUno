package it.unicam.cs.pa2021.formulaUno;

import java.util.List;

/**
 * Interfaccia con lo scopo di creare la Struttura Dati del Circuito.
 *
 * @param <T> tipo della classe che estende la Struttura Dati del Circuito.
 * @param <C> tipo delle Macchine.
 */
public interface GameBuilder<T extends GraphCircuit_DS, C> {

    /**
     * Costruisce il Circuito e le Macchine, posizionandole sul Circuito.
     * in base al numero di Giocatori fornito.
     *
     * @param totNumOfPlayers numero totale dei Giocatori nella partita.
     * @throws RuntimeException se la struttura del Circuito non &grave; stata creata correttamente.
     */
    void build(int totNumOfPlayers) throws RuntimeException;

    /**
     * Restitusce la Struttura Dati del Circuito.
     *
     * @return  la Struttura Dati del Circuito.
     */
    T getGraphCircuit_DS();

    /**
     * Restituisce la liste con le Macchine create.
     *
     * @return la liste con le Macchine create.
     */
    List<C> getCars();

    int getMin_X();

    int getMax_X();

    int getMin_Y();

    int getMax_Y();

}
