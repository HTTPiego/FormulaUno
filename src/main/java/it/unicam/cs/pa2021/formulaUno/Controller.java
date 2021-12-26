package it.unicam.cs.pa2021.formulaUno;

/**
 * Descrive un generico Controller.
 *
 * @param <B> tipo del GameBuilder.
 * @param <P> tipo per la Posizione del CircuitSupporter.
 */
public interface Controller <B, P> {

    /**
     * Crea una nuuova partita costruendo
     * un Circuito in base alla Struttura Dati e alle Macchine
     * fornite da un certo GameBuilder e dei Drivers.
     *
     * @param gameBuilder GameBuilder che si desidera utilizzare.
     * @param totNumOfPlayers Giocatori previsisti in partita.
     */
    void newGame(B gameBuilder, int totNumOfPlayers);

    /**
     * Fornisce la classifica della partita.
     */
    void Classfication();

    /**
     * Dice quali Giocatori sono in partita.
     */
    void getDriversStatus();

    /**
     * Avanza di un turno.
     * Se la Macchina del Giocatore di turno viene eliminata,
     * anche il Driver proprietario viene eliminato di conseguenza.
     */
    void nextTurn();

    /**
     * Verifica se il Giocatore di turno vince in quel turno.
     *
     * @return true se il Giocatore di turno vince in questo turno, false altrimenti.
     */
    boolean isGameOver();

    /**
     * Restituisce il Builder utilizzato per la costruzione del Controller.
     *
     * @return il Builder utilizzato per la costruzione del Controller.
     */
    B getBuilder();

    /**
     * Imposta un circuitUpdateSupporter.
     *
     * @param circuitUpdateListener supporter da utilizzare.
     */
    void addListener (CircuitUpdateListener<P> circuitUpdateListener);

}
