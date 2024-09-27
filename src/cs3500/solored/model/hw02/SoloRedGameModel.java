package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the SoloRedGame.
 */
public class SoloRedGameModel implements RedGameModel<SoloCard> {

  List<SoloCard> deck;
  List<List<SoloCard>> palettes;
  List<SoloCard> hand;
  SoloCard canvas;
  int handSize;
  Random rand;
  boolean gameOver;
  boolean gameStart;
  boolean drawCanvas;

  /**
   * initializes a SoloRedGameModel with values that allow startGame to be called.
   */
  public SoloRedGameModel() {
    deck = new ArrayList<>();
    palettes = new ArrayList<>();
    hand = new ArrayList<>();
    canvas = null;
    handSize = 0;
    rand = new Random();
    gameOver = false;
    drawCanvas = false;
    gameStart = false;
  }

  /**
   * initializes a SoloRedGameModel with a preset random seed.
   * @param random preset random object.
   * @throws IllegalArgumentException if the random object is null
   */
  public SoloRedGameModel(Random random) {
    deck = new ArrayList<>();
    palettes = new ArrayList<>();
    hand = new ArrayList<>();
    canvas = null;
    handSize = 0;
    if (random == null) {
      throw new IllegalArgumentException("random cannot be null.");
    }
    rand = random;
    gameOver = false;
    drawCanvas = false;
    gameStart = false;
  }

  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    if (gameOver || !gameStart) {
      throw new IllegalStateException("Game is over/hasn't started.");
    }
    if (paletteIdx < 0 || paletteIdx >= palettes.size()) {
      throw new IllegalArgumentException("paletteIdx must be between 0 and " + (palettes.size() - 1));
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("cardIdxInHand must be between 0 and " + (hand.size() - 1));
    }
    if (winningPaletteIndex() == paletteIdx) {
      throw new IllegalStateException("palette played to must not be winning.");
    }
    palettes.get(paletteIdx).add(hand.get(cardIdxInHand));
    hand.remove(cardIdxInHand);
    if (paletteIdx != winningPaletteIndex()) {
      gameOver = true;
    }
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    if (gameOver || !gameStart) {
      throw new IllegalStateException("Game is over/hasn't started.");
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= deck.size()) {
      throw new IllegalArgumentException("cardIdxInHand must be between 0 and " + (deck.size() - 1));
    }
    if (drawCanvas) {
      throw new IllegalStateException("method already called once in a turn");
    }
    if (hand.size() == 1) {
      throw new IllegalStateException("Only 1 card left in hand");
    }
    canvas = hand.get(cardIdxInHand);
    hand.remove(cardIdxInHand);
    drawCanvas = true;
  }

  @Override
  public void drawForHand() {
    if (gameOver || !gameStart) {
      throw new IllegalStateException("Game is over/hasn't started.");
    }
    while (hand.size() < handSize) {
      if (numOfCardsInDeck() > 0) {
        hand.add(dealFromDeck());
      } else {
        break;
      }
    }
    drawCanvas = false;
  }

  private SoloCard dealFromDeck() {
    SoloCard dealt = deck.get(0);
    deck.remove(0);
    return dealt;
  }

  @Override
  public void startGame(List<SoloCard> deck, boolean shuffle, int numPalettes, int handSize) {
    if (deck == null) {
      throw new IllegalArgumentException("deck cannot be null.");
    }
    if (gameOver || gameStart) {
      throw new IllegalStateException("Game must not have started.");
    }
    if (handSize < 1 || numPalettes < 2) {
      throw new IllegalArgumentException("Invalid hand size/number of palettes");
    }
    if (deck.size() < (numPalettes + handSize)) {
      throw new IllegalArgumentException("deck size is not big enough to fill required card states");
    }
    if (deck.contains(null)) {
      throw new IllegalArgumentException("deck contains null element");
    }
    if (new HashSet<>(deck).size() != deck.size()) {
      throw new IllegalArgumentException("duplicate cards are not allowed");
    }
    gameStart = true;
    List<SoloCard> deckCopy = new ArrayList<>(deck);
    for (int deckIndex = 0; deckIndex < deck.size(); deckIndex++) {
      deckCopy.add(CardBuilder.makeCard(deck.get(deckIndex).toString()));
    }
    if (shuffle) {
      deckCopy = shuffle(deckCopy);
    }
    this.deck = deckCopy;
    for (int i = 0; i < numPalettes; i++) {
      palettes.add(new ArrayList<>());
      palettes.get(i).add(dealFromDeck());
    }
    this.canvas = CardBuilder.makeCard("R1");
    this.handSize = handSize;
    drawForHand();
  }

  private List<SoloCard> shuffle(List<SoloCard> deck) {
    List<SoloCard> shuffled = new ArrayList<>();
    while (!deck.isEmpty()) {
      int randomIndex = rand.nextInt(deck.size());
      shuffled.add(CardBuilder.makeCard(deck.get(randomIndex).toString()));
      deck.remove(randomIndex);
    }
    return shuffled;
  }

  @Override
  public int numOfCardsInDeck() {
    if (!gameStart) {
      throw new IllegalStateException("Game hasn't started.");
    }
    return deck.size();
  }

  @Override
  public int numPalettes() {
    if (!gameStart) {
      throw new IllegalStateException("Game hasn't started.");
    }
    return palettes.size();
  }

  @Override
  public int winningPaletteIndex() {
    if (!gameStart) {
      throw new IllegalStateException("Game hasn't started.");
    }
    return palettes.indexOf(canvas.canvasRule(palettes));
  }

  @Override
  public boolean isGameOver() {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    return gameOver || isGameWon();
  }

  @Override
  public boolean isGameWon() {
    if (!gameStart || gameOver) {
      throw new IllegalStateException("Game has not started/is over.");
    }
    gameOver = true;
    return hand.isEmpty() && deck.isEmpty();
  }

  @Override
  public List<SoloCard> getHand() {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    List<SoloCard> returnList = new ArrayList<>();
    for (SoloCard card : hand) {
      returnList.add(CardBuilder.makeCard(card.toString()));
    }
    return returnList;
  }

  @Override
  public List<SoloCard> getPalette(int paletteNum) {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    if (paletteNum < 0 || paletteNum > palettes.size() - 1) {
      throw new IllegalArgumentException("paletteNum must be between 0 and " + (palettes.size() - 1));
    }
    List<SoloCard> returnList = new ArrayList<>();
    for (SoloCard card : palettes.get(paletteNum)) {
      returnList.add(CardBuilder.makeCard(card.toString()));
    }
    return returnList;
  }

  @Override
  public SoloCard getCanvas() {
    if (!gameStart || gameOver) {
      throw new IllegalStateException("Game has not started/is over.");
    }
    return CardBuilder.makeCard(canvas.toString());
  }

  @Override
  public List<SoloCard> getAllCards() {
    List<SoloCard> listOfAllCards = new ArrayList<>();
    for (Color c : Color.values()) {
      for (int number = 1; number <= 7; number++) {
        listOfAllCards.add(CardBuilder.makeCard(c.toString() + number));
      }
    }
    return listOfAllCards;
  }
}
