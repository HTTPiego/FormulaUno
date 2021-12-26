package it.unicam.cs.pa2021.formulaUno;

public class StandardGameNode extends Node {

    public StandardGameNode(CardinalPoint cardinalPoint, Car<Node> car, boolean isStartNode, boolean isEndNode, boolean isOutsideNode) {
        super(cardinalPoint, car, isStartNode, isEndNode, isOutsideNode);
    }

    public StandardGameNode(CardinalPoint cardinalPoint, boolean isStartNode, boolean isEndNode, boolean isOutsideNode) {
        super(cardinalPoint, isStartNode, isEndNode, isOutsideNode);
    }

}
