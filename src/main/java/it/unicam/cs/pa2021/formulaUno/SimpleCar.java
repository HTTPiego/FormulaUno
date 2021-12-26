package it.unicam.cs.pa2021.formulaUno;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimpleCar implements Car<Node>{

    private final String numberName;

    private Node currentNode;

    private Node previousNode;

    private List<Node> route;


    public SimpleCar(Node currentNode, String numberName) {
        this.numberName = numberName;
        this.currentNode = currentNode;
        this.previousNode = null;
        this.route = new ArrayList<>();
        this.route.add(this.currentNode);
    }

    @Override
    public Node getPreviousPosition() {
        return this.previousNode;
    }

    @Override
    public void setPreviousPosition(Node node) {
        this.previousNode = node;
    }

    @Override
    public Node getPosition() {
        return this.currentNode;
    }

    @Override
    public void setPosition(Node node) {
        this.currentNode = node;
        this.route.add(this.currentNode);
    }

    @Override
    public List<Node> getRoute() {
        return this.route;
    }

    @Override
    public Node calculateNextPosition() {
        if (this.previousNode == null)
            return Node.aNode(CardinalPoint.aBidimentionalPoint(this.getXof(this.currentNode),this.getYof(this.currentNode)+1));
        double xDifference;
        xDifference = this.getXof(this.currentNode) - this.getXof(this.previousNode);
        double yDifference;
        yDifference = this.getYof(this.currentNode) - this.getYof(this.previousNode);
        double newX = this.getXof(this.currentNode) + xDifference;
        double newY = this.getYof(this.currentNode) + yDifference;
        return Node.aNode(CardinalPoint.aBidimentionalPoint(newX,newY));
    }

    private double getXof (Node node) {
        return node.getCardinalPoint().getX();
    }

    private double getYof (Node node) {
        return node.getCardinalPoint().getY();
    }

    @Override
    public String getName() {
        return this.numberName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleCar simpleCar = (SimpleCar) o;
        return currentNode.equals(simpleCar.currentNode) && Objects.equals(previousNode, simpleCar.previousNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentNode, previousNode);
    }
}
