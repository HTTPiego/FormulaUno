package it.unicam.cs.pa2021.formulaUno;

import java.util.Objects;

/**
 * Descrive un generico "nodo" che compone una GraphCircuit_DS.
 */
public abstract class Node {

    private final CardinalPoint cardinalPoint;

    private Car<Node> car;

    private final boolean isStartNode;

    private final boolean isEndNode;

    private final boolean isOutsideNode;

    public Node (CardinalPoint cardinalPoint, Car<Node> car,
                 boolean isStartNode, boolean isEndNode , boolean isOutsideNode){
        this.car = car;
        this.cardinalPoint = cardinalPoint;
        this.isStartNode = isStartNode;
        this.isEndNode = isEndNode;
        this.isOutsideNode = isOutsideNode;
    }

    public Node (CardinalPoint cardinalPoint, boolean isStartNode, boolean isEndNode , boolean isOutsideNode){
        this.car = null;
        this.cardinalPoint = cardinalPoint;
        this.isStartNode = isStartNode;
        this.isEndNode = isEndNode;
        this.isOutsideNode = isOutsideNode;
    }

    public static Node aNode (CardinalPoint cardinalPoint) {
        return new StandardGameNode(cardinalPoint, false, false, false);
    }

    public CardinalPoint getCardinalPoint() {
        return cardinalPoint;
    }

    /**
     * Ritorna la Macchina presente nel Nodo.
     *
     * @return la Macchina nel nodo o null se non &grave; presente alcuna Macchina.
     * @throws UnsupportedOperationException se chiamata su un nodo esterno.
     */
    public Car<Node> getCar() throws UnsupportedOperationException {
        if (this.isOutsideNode)
            throw new UnsupportedOperationException("Can't get a Car from an outside Node");
        return this.car;
    }

    /**
     * Aggiorna il valore della Macchina nel Nodo.
     *
     * @throws UnsupportedOperationException se chiamata su un nodo esterno.
     */
    public void setCar(Car<Node> car) throws UnsupportedOperationException {
        if (this.isOutsideNode)
            throw new UnsupportedOperationException("Can't set a Car outside of the Circuit");
        this.car = car;
    }

    /**
     * Dice se si tratta di un Nodo di inizio.
     *
     * @return true se il nodo &grave; di inizio, false altrimenti.
     */
    public boolean isStartNode() {
        return this.isStartNode;
    }

    /**
     * Dice se si tratta di un Nodo del traguardo.
     *
     * @return true se il nodo &grave; del traguardo, false altrimenti.
     */
    public boolean isEndNode() {
        return this.isEndNode;
    }

    /**
     * Dice se si tratta di un Nodo esterno.
     *
     * @return true se il nodo &grave; esterno, false altrimenti.
     */
    public boolean isOutsideNode() {
        return this.isOutsideNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(cardinalPoint, node.cardinalPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardinalPoint);
    }
}
