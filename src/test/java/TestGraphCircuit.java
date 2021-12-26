import it.unicam.cs.pa2021.formulaUno.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestGraphCircuit {

    GraphCircuit_DS graphCircuit_ds = new GraphCircuit_DS();

    void littleSquare1(){
        for (int x = 0; x < 10; ++x) {
            for (int y = 0; y < 10; ++y) {
                if (x == 0 || x == 9 || y == 0 || y == 9) {
                    Node out = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                            false, false, true);
                    graphCircuit_ds.addNode(out);
                } else {
                    Node in = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                            false, false, false);
                    graphCircuit_ds.addNode(in);
                }
            }
        }
    }

    void littleSquare2(){
        for (int x = 0; x < 10; ++x) {
            for (int y = 11; y < 21; ++y) {
                if (x == 0 || x == 9 || y == 11 || y ==20) {
                    Node out = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                            false, false, true);
                    graphCircuit_ds.addNode(out);
                } else {
                    Node in = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                            false, false, false);
                    graphCircuit_ds.addNode(in);
                }
            }
        }
    }

    @Test
    void possibleMovementsTest (){
        this.littleSquare1();
        this.littleSquare2();
        Node previous = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(2, 2)).get();
        Node current = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(4, 8)).get();
        Car<Node> car = new SimpleCar(current, "thisIsATest");
        car.setPreviousPosition(previous);
        List<Car<Node>> cars = new ArrayList<>();
        cars.add(car);
        GraphCircuit graphCircuit = new GraphCircuit(this.graphCircuit_ds, cars);
        Assertions.assertEquals(graphCircuit.possibleMovements(car).size(), 0);


        this.graphCircuit_ds.clear();
        this.littleSquare1();
        this.littleSquare2();
        Node previous1 = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(1, 1)).get();
        Node current1 = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(7, 1)).get();
        Car<Node> car1 = new SimpleCar(current1, "thisIsATest1");
        car1.setPreviousPosition(previous1);
        List<Car<Node>> cars1 = new ArrayList<>();
        cars1.add(car1);
        GraphCircuit graphCircuit1 = new GraphCircuit(this.graphCircuit_ds, cars1);
        Assertions.assertEquals(graphCircuit1.possibleMovements(car1).size(), 0);

        this.graphCircuit_ds.clear();
        this.littleSquare1();
        this.littleSquare2();
        Node previous2 = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(2, 2)).get();
        Node current2 = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(3, 3)).get();
        Car<Node> car2 = new SimpleCar(current2, "thisIsATest2");
        car2.setPreviousPosition(previous2);
        List<Car<Node>> cars2 = new ArrayList<>();
        cars2.add(car2);
        GraphCircuit graphCircuit2 = new GraphCircuit(this.graphCircuit_ds, cars2);
        graphCircuit2.updateCarPosition(car2, Node.aNode(CardinalPoint.aBidimentionalPoint(4,4)));
    }

    @Test
    void updateCarPosition(){
        this.graphCircuit_ds.clear();
        this.littleSquare1();
        this.littleSquare2();
        Node previous = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(2, 2)).get();
        Node current = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(4, 4)).get();
        Car<Node> carCrash = new SimpleCar(current, "thisIsATest");
        carCrash.setPreviousPosition(previous);
        Car<Node> car = new SimpleCar(this.graphCircuit_ds.getNode(new BidimentionalCardinalPoint(6,6)).get(), "this");
        List<Car<Node>> cars = new ArrayList<>();
        cars.add(car);
        cars.add(carCrash);
        GraphCircuit graphCircuit = new GraphCircuit(this.graphCircuit_ds, cars);
        Assertions.assertEquals(graphCircuit.possibleMovements(carCrash).size(), 9);
        Node impossibileNode = Node.aNode(CardinalPoint.aBidimentionalPoint(30,30));
        Car<Node> impossibleCar = new SimpleCar(impossibileNode, "impossible");
        impossibleCar.setPreviousPosition(Node.aNode(CardinalPoint.aBidimentionalPoint(29,29)));//
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->  graphCircuit.updateCarPosition(car,impossibileNode));
        Assertions.assertThrows(NullPointerException.class,
                () ->  graphCircuit.updateCarPosition(impossibleCar,Node.aNode(CardinalPoint.aBidimentionalPoint(30,30))));
        graphCircuit.updateCarPosition(carCrash, this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(6,6)).get());
        Assertions.assertTrue(graphCircuit.carIsInRace(car));
        Assertions.assertTrue(graphCircuit.carIsInRace(carCrash));
    }

    void littleSquareForWinningTest(){
        for (int x = 0; x < 10; ++x) {
            for (int y = 0; y < 10; ++y) {
                if (x == 0 || x == 9 || y == 0 || y == 9) {
                    if (y == 9) {
                        Node end = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                                false, true, false);
                        graphCircuit_ds.addNode(end);
                    } else {
                        Node out = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                                false, false, true);
                        graphCircuit_ds.addNode(out);
                    }
                } else {
                        Node in = new StandardGameNode(new BidimentionalCardinalPoint(x, y),
                                false, false, false);
                        graphCircuit_ds.addNode(in);
                }
            }
        }
    }

    @Test
    void didCarWinTest(){
        this.graphCircuit_ds.clear();
        this.littleSquareForWinningTest();
        Node previous = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(3, 2)).get();
        Node current = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(3, 7)).get();
        Car<Node> car = new SimpleCar(current, "thisIsATest");
        car.setPreviousPosition(previous);
        List<Car<Node>> cars = new ArrayList<>();
        cars.add(car);
        GraphCircuit graphCircuit = new GraphCircuit(this.graphCircuit_ds, cars);
        Assertions.assertTrue(graphCircuit.didCarWin(car));
        this.graphCircuit_ds.clear();
        this.littleSquareForWinningTest();
        Node p = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(2, 2)).get();
        Node c = this.graphCircuit_ds.getNode(CardinalPoint.aBidimentionalPoint(2, 3)).get();
        Car<Node> mac = new SimpleCar(c, "thisIsATest");
        car.setPreviousPosition(p);
        List<Car<Node>> macchine = new ArrayList<>();
        macchine.add(mac);
        GraphCircuit graphCircuit1 = new GraphCircuit(this.graphCircuit_ds, macchine);
        Assertions.assertFalse(graphCircuit1.didCarWin(mac));
    }

}




