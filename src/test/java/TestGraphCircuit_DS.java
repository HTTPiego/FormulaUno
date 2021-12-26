import it.unicam.cs.pa2021.formulaUno.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

public class TestGraphCircuit_DS<C>{

    LinkedList<CardinalPoint> createPoints (){
        LinkedList<CardinalPoint> points = new LinkedList<>();
        for (int x = 1; x <= 3; x++){
            for (int y = 1; y <= 3; y++) {
                CardinalPoint cardinalPoint = new BidimentionalCardinalPoint(x, y);
                points.add(cardinalPoint);
            }
        }
        return  points;
    }


    LinkedList<Node> createNodes(LinkedList<CardinalPoint> points){
        LinkedList<Node> nodes = new LinkedList<>();
        for (int i = 1; i <= 3; i++){
            for (int j = 1; j <= 3; j++) {
                Node node = new StandardGameNode(points.pollFirst(), false, false, false);
                nodes.add(node);
            }
        }
        return nodes;
    }


    GraphCircuit_DS createSquareGraph (){
        LinkedList<CardinalPoint> points = this.createPoints();
        LinkedList<Node> nodes = this.createNodes(points);
        GraphCircuit_DS graph = new GraphCircuit_DS();
        nodes.stream().forEach((node) -> graph.addNode(node));
        return graph;
    }

    @Test
    void addNodeTest(){
        LinkedList<CardinalPoint> points = this.createPoints();
        LinkedList<Node> nodes = this.createNodes(points);
        GraphCircuit_DS graph = new GraphCircuit_DS();
        nodes.stream().forEach((node) -> graph.addNode(node));
        Node outsideNode = new StandardGameNode(new BidimentionalCardinalPoint(30, 30),false, false,true);
        Assertions.assertTrue(graph.addNode(outsideNode));
        Assertions.assertFalse(graph.addNode(nodes.get(0)));
        Assertions.assertFalse(graph.addNode(null));
        Assertions.assertEquals(10, graph.getSize());
    }

    @Test
    void getNodesAdjacentyRelationshipTest(){
        LinkedList<CardinalPoint> points = this.createPoints();
        LinkedList<Node> nodes = this.createNodes(points);
        GraphCircuit_DS graph = new GraphCircuit_DS();
        nodes.stream().forEach((node) -> graph.addNode(node));
        Node centralNode = nodes.get(4);
        Node topNode = nodes.get(5);
        Node bottomNode = nodes.get(3);
        Node rightNode = nodes.get(7);
        Node leftNode = nodes.get(1);
        Node trNode = nodes.get(8);
        Node tlNode = nodes.get(2);
        Node brNode = nodes.get(6);
        Node blNode = nodes.get(0);
        Set<Node> centralNodeNeghbors = graph.getNeighborNodes(centralNode).get();
        Assertions.assertEquals(8, centralNodeNeghbors.size());
        Assertions.assertEquals(
                graph.getNodesAdjacentyRelationship(topNode, centralNode), Optional.of(AdjacentLocation.TOP));
        Assertions.assertEquals(
                graph.getNodesAdjacentyRelationship(bottomNode, centralNode), Optional.of(AdjacentLocation.BOTTOM));
        Assertions.assertEquals(
                graph.getNodesAdjacentyRelationship(rightNode, centralNode), Optional.of(AdjacentLocation.RIGHT));
        Assertions.assertEquals(
                graph.getNodesAdjacentyRelationship(leftNode, centralNode), Optional.of(AdjacentLocation.LEFT));
        Assertions.assertEquals(
                graph.getNodesAdjacentyRelationship(trNode, centralNode), Optional.of(AdjacentLocation.TOPRIGHT));
        Assertions.assertEquals(
                graph.getNodesAdjacentyRelationship(tlNode, centralNode), Optional.of(AdjacentLocation.TOPLEFT));
        Assertions.assertEquals(
                graph.getNodesAdjacentyRelationship(brNode, centralNode), Optional.of(AdjacentLocation.BOTTOMRIGHT));
        Assertions.assertEquals(
                graph.getNodesAdjacentyRelationship(blNode, centralNode), Optional.of(AdjacentLocation.BOTTOMLEFT));
    }

    @Test
    void getNodeTest(){
        LinkedList<CardinalPoint> points = this.createPoints();
        CardinalPoint point = points.get(0);
        LinkedList<Node> nodes = this.createNodes(points);
        GraphCircuit_DS graph = new GraphCircuit_DS();
        nodes.stream().forEach((node) -> graph.addNode(node));
        Assertions.assertEquals(Optional.of(nodes.get(0)), graph.getNode(nodes.get(0)));
        Assertions.assertEquals(Optional.of(nodes.get(0)), graph.getNode(point));
    }

    @Test
    void deleteNearNodesLinkingTest (){
        CardinalPoint UnoUno = new BidimentionalCardinalPoint(1,1);
        CardinalPoint DueDue = new BidimentionalCardinalPoint(2,2);
        CardinalPoint TreTre = new BidimentionalCardinalPoint(3,3);
        CardinalPoint otto = new BidimentionalCardinalPoint(8,8);
        CardinalPoint QuattroQuattro = new BidimentionalCardinalPoint(4,4);
        Node nUnoUno = Node.aNode(UnoUno);
        Node nDueDue = Node.aNode(DueDue);
        Node nTreTre = Node.aNode(TreTre);
        Node nOtto = Node.aNode(TreTre);
        Node nQuattroQuattro = new StandardGameNode(QuattroQuattro,false, false,true);;
        GraphCircuit_DS graph = new GraphCircuit_DS();
        graph.addNode(nUnoUno);
        graph.addNode(nDueDue);
        graph.addNode(nTreTre);
        graph.addNode(nQuattroQuattro);
        Assertions.assertFalse(graph.deleteNearNodesLinking(nUnoUno, nUnoUno));
        Assertions.assertFalse(graph.deleteNearNodesLinking(nUnoUno, nTreTre));
        Assertions.assertFalse(graph.deleteNearNodesLinking(nUnoUno, nOtto));
        Assertions.assertTrue(graph.deleteNearNodesLinking(nUnoUno, nDueDue));
    }

}
