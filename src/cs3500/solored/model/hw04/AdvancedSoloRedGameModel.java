package cs3500.solored.model.hw04;

import java.util.Random;

import cs3500.solored.model.hw02.SoloCard;

public class AdvancedSoloRedGameModel extends AbstractModel {

  private boolean drawTwo;

  public AdvancedSoloRedGameModel() {
    super();
    drawTwo = false;
  }

  public AdvancedSoloRedGameModel(Random random) {
    super(random);
    drawTwo = false;
  }

  @Override
  public void drawForHand() {
    if (gameOver || !gameStart) {
      throw new IllegalStateException("Game is over/hasn't started.");
    }
    if (numOfCardsInDeck() > 0 && hand.size() < handSize) {
      hand.add(dealFromDeck());
      if (drawTwo && numOfCardsInDeck() > 0 && hand.size() < handSize) {
        hand.add(dealFromDeck());
      }
    }
    drawCanvas = false;
    drawTwo = false;
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    if (gameOver || !gameStart) {
      throw new IllegalStateException("Game is over/hasn't started.");
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException(
              "cardIdxInHand must be between 0 and " + (hand.size() - 1));
    }
    if (drawCanvas) {
      throw new IllegalStateException("method already called once in a turn");
    }
    if (hand.size() == 1) {
      throw new IllegalStateException("Only 1 card left in hand");
    }
    SoloCard newCanvas = hand.get(cardIdxInHand);
    if (newCanvas.number() > canvas.number()) {
      drawTwo = true;
    }
    canvas = newCanvas;
    hand.remove(cardIdxInHand);
    drawCanvas = true;
  }
}

