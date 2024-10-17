package cs3500.solored;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.solored.model.hw02.SoloCard;
import cs3500.solored.model.hw04.AbstractModel;
import cs3500.solored.view.hw02.RedGameView;
import cs3500.solored.view.hw02.SoloRedGameTextView;

public abstract class TestModel {
  AbstractModel modelDeck;

  @Before
  public abstract void testSetup();

  @Test
  public void testNumberOfCardsInDeck() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    Assert.assertEquals(modelDeck.numOfCardsInDeck(), 24);
  }

  @Test
  public void testRedGameTextView() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
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
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
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
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
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
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    Assert.assertEquals(modelDeck.numPalettes(), 4);
  }

  @Test
  public void testWinningPaletteIndex() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    Assert.assertEquals(modelDeck.winningPaletteIndex(), 3);
  }

  @Test
  public void testIsGameOverWhenItIsNot() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    Assert.assertFalse(modelDeck.isGameOver());
  }

  @Test
  public void testIsGameOverWhenItIs() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    modelDeck.playToPalette(2, 3);
    Assert.assertTrue(modelDeck.isGameOver());
  }

  @Test
  public void testStartGameThrowsWithPaletteLessThan2() {
    Assert.assertThrows(IllegalArgumentException.class, () -> modelDeck.startGame(
            modelDeck.getAllCards(), false, 1, 7
    ));
  }

  @Test
  public void testStartGameThrowsWithNotEnoughCards() {
    Assert.assertThrows(IllegalArgumentException.class, () -> modelDeck.startGame(
            null, false, 4, 7
    ));
  }

  @Test
  public void testStartGameThatsAlreadyStarted() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    Assert.assertThrows(IllegalStateException.class, () -> modelDeck.startGame(
            modelDeck.getAllCards(), false, 4, 7
    ));
  }

  @Test
  public void testPlayToPaletteWithInvalidHandIndex() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    Assert.assertThrows(IllegalArgumentException.class, () -> modelDeck.playToPalette(0, -2));
  }

  @Test
  public void testPlayToCanvasWithValidHandIndex() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    Assert.assertThrows(IllegalArgumentException.class, () -> modelDeck.playToCanvas(-2));
  }

  @Test
  public void testGetHandDoesNotModify() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    List<SoloCard> firstHand = modelDeck.getHand();
    List<SoloCard> secondHand = modelDeck.getHand();
    secondHand.remove(1);
    Assert.assertEquals(modelDeck.getHand(), firstHand);
  }

  @Test
  public void testGetPaletteDoesNotModify() {
    modelDeck.startGame(modelDeck.getAllCards(), false, 4, 7);
    List<SoloCard> firstPalette = modelDeck.getPalette(0);
    List<SoloCard> secondPalette = modelDeck.getPalette(0);
    secondPalette.remove(0);
    Assert.assertEquals(modelDeck.getPalette(0), firstPalette);
  }
}
