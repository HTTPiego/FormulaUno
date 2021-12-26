package it.unicam.cs.pa2021.formulaUno;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Interfaccia che definisce un generico Circuito.
 *
 * @param <P> Tipo per la Posizione della Macchina.
 */
public interface Circuit<P> {

    /**
     * Restituisce la Macchina nella Posizione richiesta se presente.
     *
     * @param position Posizione nel Circuito.
     * @return una Macchina nella posizione richiesta o un Optional.Empty() se non presente.
     * @throws NullPointerException se la Posizione passata non &grave; valida (nulla o non presente nel circuito).
     */
    Optional<Car<P>> carAt (P position) throws NullPointerException;

    /**
     * Dice se una Macchina ha tagliato il traguardo oppure no.
     *
     * @param car Macchina nel Circuito.
     * @return true se la Macchina ha tagliato il traguardo, false altrimenti.
     * @throws NullPointerException se la Macchina non &grave; presente sul Circuito.
     */
    boolean didCarWin (Car<Node> car) throws NullPointerException;

    /**
     * Restitusce tutti i possibili movimenti di una certa Macchina.
     * Se il Set &grave; vuoto  significa che la Macchina uscirebbe fuori dal Circuito.
     *
     * @param car Macchina di cui si vogliono conoscere i possibili spostamenti.
     * @return l'insieme dei possibili spostamenti della Macchina.
     * @throws NullPointerException se la Macchina inserita non &grave; valida (nulla o non presente nel circuito).
     */
    Set<Node> possibleMovements (Car<P> car) throws NullPointerException;

    /**
     * Sposta una Macchina in una certa posizione, aggiornandone i suoi campi.
     * Se possibleMovements restituisce un insieme vuoto elimina la macchina.
     * Se viene spostata in una posizione già occupata viene eliminata.
     * *
     * @param car Macchina che si deve muovere.
     * @param position Posizione nella quale si vuole spostare la Macchina.
     * @throws NullPointerException se la Macchina o la Posizione passate
     * non sono valide (nulle o non presenti nel circuito),
     * @throws IllegalArgumentException se lo spostamento non &grave; consentito.
     */
    void updateCarPosition(Car<P> car, P position) throws NullPointerException, IllegalArgumentException;

    /**
     *Data una certa Posizione, risponde dicendo se essa &egrave; libera oppure no.
     *
     * @param position Posizione nel Circuito.
     * @return True se la Posizione &egrave; libera, False altrimenti.
     */
    default boolean positionIsFree (P position){
        if (carAt(position).equals(Optional.empty()))
            return true;
        return false;
    }

    /**
     * Ritorna una List che contiene le Macchine ordinate il base alla posizione della classifica.
     *
     * @implNote
     *          ordinamento della classifica ottenuto con il SelectionSort.
     *
     * @apiNote
     *          La posizione di una Macchina all'interno della lista classifica &grave; determinata
     *          dalla somma della distanza tra tutte le posizioni nel suo storico dei movimenti.
     *
     * @return una List di Macchine.
     */
    List<Car<Node>> getClassification();

    /**
     * Dice se una determinata Macchina &grave; ancora in gara.
     *
     * @param car Macchina di cui si vuole sapere lo stato.
     * @return true se la Macchina &grave; in gara, false altrimenti
     * @throws NullPointerException se il parametro passato &grave; null.
     */
    boolean carIsInRace (Car<Node> car) throws NullPointerException;

    /**
     * Imposta un circuitUpdateSupporter.
     *
     * @param circuitUpdateListener listener da utilizzare.
     */
    void setCircuitUpdateSupporter (CircuitUpdateListener<P> circuitUpdateListener);

}
