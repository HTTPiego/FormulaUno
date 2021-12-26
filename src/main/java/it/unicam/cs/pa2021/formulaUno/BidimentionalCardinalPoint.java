package it.unicam.cs.pa2021.formulaUno;

import java.util.Objects;
import java.util.Optional;

/**
 * Classe che fornisce coordinate sul piano bidimensionale.
 */
public class BidimentionalCardinalPoint implements CardinalPoint {

    private final double x;
    private final double y;

    public BidimentionalCardinalPoint(double x, double y){
        this.x = x;
        this.y = y;
    }
    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return 0;
    }

    private boolean isTopPointOf (CardinalPoint cardinalPoint){
        if (cardinalPoint.getZ() == 0 &&
            cardinalPoint.getX() == this.x &&
            cardinalPoint.getY() == this.y-1)
            return true;
        return false;
    }

    private boolean isBottomPointOf (CardinalPoint cardinalPoint){
        if (cardinalPoint.getZ() == 0 &&
            cardinalPoint.getX() == this.x &&
            cardinalPoint.getY() == this.y+1)
            return true;
        return false;
    }

    private boolean isRightPointOf (CardinalPoint cardinalPoint){
        if (cardinalPoint.getZ() == 0 &&
            cardinalPoint.getX() == this.x-1 &&
            cardinalPoint.getY() == this.y)
            return true;
        return false;
    }

    private boolean isLeftPointOf (CardinalPoint cardinalPoint){
        if (cardinalPoint.getZ() == 0 &&
            cardinalPoint.getX() == this.x+1 &&
            cardinalPoint.getY() == this.y)
            return true;
        return false;
    }

    private boolean isTopRightPointOf (CardinalPoint cardinalPoint){
        if (cardinalPoint.getZ() == 0 &&
            cardinalPoint.getX() == this.x-1 &&
            cardinalPoint.getY() == this.y-1)
            return true;
        return false;
    }

    private boolean isTopLeftPointOf (CardinalPoint cardinalPoint){
        if (cardinalPoint.getZ() == 0 &&
            cardinalPoint.getX() == this.x+1 &&
            cardinalPoint.getY() == this.y-1)
            return true;
        return false;
    }

    private boolean isBottomRightPointOf (CardinalPoint cardinalPoint){
        if (cardinalPoint.getZ() == 0 &&
            cardinalPoint.getX() == this.x-1 &&
            cardinalPoint.getY() == this.y+1)
            return true;
        return false;
    }

    private boolean isBottomLeftPointOf (CardinalPoint cardinalPoint){
        if (cardinalPoint.getZ() == 0 &&
            cardinalPoint.getX() == this.x+1 &&
            cardinalPoint.getY() == this.y+1)
            return true;
        return false;
    }

    @Override
    public Optional<AdjacentLocation> getAdjacencyRelationship(CardinalPoint cardinalPoint) {
        if (this.isTopPointOf(cardinalPoint))
            return Optional.of(AdjacentLocation.TOP);
        if (this.isBottomPointOf(cardinalPoint))
            return Optional.of(AdjacentLocation.BOTTOM);
        if (this.isRightPointOf(cardinalPoint))
            return Optional.of(AdjacentLocation.RIGHT);
        if (this.isLeftPointOf(cardinalPoint))
            return Optional.of(AdjacentLocation.LEFT);
        if (this.isTopRightPointOf(cardinalPoint))
            return Optional.of(AdjacentLocation.TOPRIGHT);
        if (this.isTopLeftPointOf(cardinalPoint))
            return Optional.of(AdjacentLocation.TOPLEFT);
        if (this.isBottomRightPointOf(cardinalPoint))
            return Optional.of(AdjacentLocation.BOTTOMRIGHT);
        if (this.isBottomLeftPointOf(cardinalPoint))
            return Optional.of(AdjacentLocation.BOTTOMLEFT);
        return Optional.empty();
    }

    @Override
    public boolean isNearTo(CardinalPoint cardinalPoint) {
        if (this.getAdjacencyRelationship(cardinalPoint).equals(Optional.empty()))
            return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BidimentionalCardinalPoint that = (BidimentionalCardinalPoint) o;
        return x == that.x && y == that.y && that.getZ() == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, 0);
    }
}
