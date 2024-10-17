package cs3500.solored;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;

public class TestAdvancedSoloRedGameModel extends TestModel {

  @Override
  @Before
  public void testSetup() {
    modelDeck = new AdvancedSoloRedGameModel();
  }

  @Test
  public void testCanvasPlayCausesTwoCardDraw() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    int handSize = modelDeck.getHand().size();
    modelDeck.playToCanvas(0);
    modelDeck.playToPalette(0, 0);
    modelDeck.drawForHand();
    Assert.assertEquals(handSize, modelDeck.getHand().size());
  }

}
