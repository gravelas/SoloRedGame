package cs3500.solored;

import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.view.hw02.RedGameView;
import cs3500.solored.view.hw02.SoloRedGameTextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Class meant to test the implementation of the SoloRedGameModel.
 */
public class TestSoloRedGameModel {

  SoloRedGameModel modelDeck;

  @Before
  public void testSetup() {
    modelDeck = new SoloRedGameModel();
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
  }

  @Test
  public void testNumberOfCardsInDeck() {
    Assert.assertEquals(modelDeck.numOfCardsInDeck(), 24);
  }

  @Test
  public void testRedGameTextView() {
    RedGameView view = new SoloRedGameTextView(modelDeck);
    Assert.assertEquals(view.toString(), "Canvas: R\n"
            + "P1: V1\n"
            + "P2: V2\n"
            + "P3: V3\n"
            + "> P4: V4\n"
            + "Hand: V5 V6 V7 I1 I2 I3 I4");
  }

  @Test
  public void testPuttingCardOnPalette() {
    modelDeck.playToPalette(0, 2);
    RedGameView view = new SoloRedGameTextView(modelDeck);
    Assert.assertEquals(view.toString(), "Canvas: R\n"
            + "> P1: V1 V7\n"
            + "P2: V2\n"
            + "P3: V3\n"
            + "P4: V4\n"
            + "Hand: V5 V6 I1 I2 I3 I4");
  }

  @Test
  public void testPuttingCardOnCanvas() {
    modelDeck.playToCanvas(0);
    modelDeck.playToPalette(0, 2);
    RedGameView view = new SoloRedGameTextView(modelDeck);
    Assert.assertEquals(view.toString(), "Canvas: V\n"
            + "> P1: V1 I1\n"
            + "P2: V2\n"
            + "P3: V3\n"
            + "P4: V4\n"
            + "Hand: V6 V7 I2 I3 I4");
  }

  @Test
  public void testNumOfPalettes() {
    Assert.assertEquals(modelDeck.numPalettes(), 4);
  }

  @Test
  public void testWinningPaletteIndex() {
    Assert.assertEquals(modelDeck.winningPaletteIndex(), 3);
  }

  @Test
  public void testIsGameOverWhenItIsNot() {
    Assert.assertFalse(modelDeck.isGameOver());
  }

  @Test
  public void testIsGameOverWhenItIs() {
    modelDeck.playToPalette(2, 3);
    Assert.assertTrue(modelDeck.isGameOver());
  }
}