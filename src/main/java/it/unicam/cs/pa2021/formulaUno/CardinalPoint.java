package it.unicam.cs.pa2021.formulaUno;

import java.util.Optional;

/**
 * Interfaccia per individuare un punto all'interno di un CircuitGraph.
 */
public interface CardinalPoint {

    double getX();

    double getY();

    double getZ();

    static CardinalPoint aBidimentionalPoint (double x, double y) {
        return new BidimentionalCardinalPoint(x, y);
    }

    /**
     * Metodo per stabilire, se esistente,
     * una relazione di adiacenza tra due punti cardinali.
     *
     * @param cardinalPoint punto da verificare.
     * @return la posizione posizione in cui due punti sono adiacenti se effettivamente vicini,
     * Optional.empty() altrimenti.
     */
    Optional<AdjacentLocation> getAdjacencyRelationship (CardinalPoint cardinalPoint);

    /**
     * Metodo per verificare l'adicenza di due punti.
     *
     * @param cardinalPoint punto da verificare.
     * @return true se i due punti sono adiacenti,
     * false altrimenti.
     */
    boolean isNearTo (CardinalPoint cardinalPoint);

    boolean equals (Object o);

    int hashCode ();
}
