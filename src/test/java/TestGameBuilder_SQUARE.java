import it.unicam.cs.pa2021.formulaUno.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestGameBuilder_SQUARE {

   @Test
   void buildTest(){
       GameBuilder<GraphCircuit_DS, Car<Node>> gameBuilder = new GameBuilder_SQUARE();
       gameBuilder.build(2);
       Assertions.assertTrue(gameBuilder.getGraphCircuit_DS().getSize() == 280);
       Assertions.assertTrue(gameBuilder.getCars().size() == 2);
       gameBuilder.getCars().get(0);
       Assertions.assertEquals(gameBuilder.getCars().get(0).getPosition(),
                                Node.aNode(CardinalPoint.aBidimentionalPoint(1.0, 6.0)));
       Assertions.assertEquals(gameBuilder.getCars().get(1).getPosition(),
                                Node.aNode(CardinalPoint.aBidimentionalPoint(3.0, 6.0)));
       Assertions.assertTrue(gameBuilder.getMin_X() == 0);
       Assertions.assertTrue(gameBuilder.getMin_Y() == 0);
       Assertions.assertTrue(gameBuilder.getMax_X() == 14);
       Assertions.assertTrue(gameBuilder.getMax_Y() == 14);
   }


}
