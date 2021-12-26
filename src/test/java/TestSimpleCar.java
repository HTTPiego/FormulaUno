import it.unicam.cs.pa2021.formulaUno.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSimpleCar {

    @Test
    void calculateNextPositionTest(){
        Node current = new StandardGameNode(new BidimentionalCardinalPoint(5, -2),false, false, false);
        Node previous = new StandardGameNode(new BidimentionalCardinalPoint(3, 6),false, false, false);
        Car<Node> car = new SimpleCar(current, "testCar");
        car.setPreviousPosition(previous);
        Assertions.assertEquals(Node.aNode(CardinalPoint.aBidimentionalPoint(7, -10)), car.calculateNextPosition());
    }
}
