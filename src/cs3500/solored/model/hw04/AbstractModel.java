package cs3500.solored.model.hw04;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import cs3500.solored.model.hw02.CardBuilder;
import cs3500.solored.model.hw02.Color;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloCard;

/**
 * Abstracted Model that handles most of the functionality of a SoloRed Game.
 */
public abstract class AbstractModel implements RedGameModel<SoloCard> {
  // 0 through palettes size minus one. Top to bottom when displayed.
  protected final List<List<SoloCard>> palettes;
  // 0 through handSize minus one. Left to right.
  protected final List<SoloCard> hand;
  protected final Random rand;
  //Top card is represented as 0 index, bottom card is last index.
  protected List<SoloCard> deck;
  protected SoloCard canvas;
  protected int handSize;
  protected boolean gameOver;
  protected boolean gameStart;
  protected boolean drawCanvas;
  protected boolean lost;

  /**
   * Initializes all values needed to run a game.
   */
  public AbstractModel() {
    deck = new ArrayList<>();
    palettes = new ArrayList<>();
    hand = new ArrayList<>();
    canvas = null;
    handSize = 0;
    gameOver = false;
    gameStart = false;
    drawCanvas = false;
    lost = false;
    rand = new Random();
  }

  /**
   * Initializes all values needed to run a game.
   * Also takes in a specified random seed for shuffling.
   * @param random Random object to control shuffling.
   */
  public AbstractModel(Random random) {
    deck = new ArrayList<>();
    palettes = new ArrayList<>();
    hand = new ArrayList<>();
    canvas = null;
    handSize = 0;
    gameOver = false;
    gameStart = false;
    drawCanvas = false;
    if (random == null) {
      throw new IllegalArgumentException("random cannot be null.");
    }
    rand = random;
  }

  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    if (gameOver) {
      throw new IllegalStateException("Game is over");
    }
    if (!gameStart) {
      throw new IllegalStateException("Game has not started");
    }
    if (paletteIdx < 0 || paletteIdx >= palettes.size()) {
      throw new IllegalArgumentException(
              "paletteIdx must be between 0 and " + (palettes.size() - 1));
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException(
              "cardIdxInHand must be between 0 and " + (hand.size() - 1));
    }
    if (winningPaletteIndex() == paletteIdx) {
      throw new IllegalStateException("palette played to must not be winning.");
    }
    palettes.get(paletteIdx).add(hand.get(cardIdxInHand));
    hand.remove(cardIdxInHand);
    if (paletteIdx != winningPaletteIndex()) {
      lost = true;
      gameOver = true;
    }
    if (hand.isEmpty() && deck.isEmpty()) {
      gameOver = true;
    }
  }

  /**
   * Gets the card off the top of the deck and removes it.
   *
   * @return The card on the top of the deck.
   */
  protected SoloCard dealFromDeck() {
    SoloCard dealt = deck.get(0);
    deck.remove(0);
    return dealt;
  }

  /**
   * For my implementation if the deck is null an IllegalArgumentException is thrown.
   *
   * @param deck        the cards used to set up and play the game
   * @param shuffle     whether the deck should be shuffled prior to setting up the game
   * @param numPalettes number of palettes in the game
   * @param handSize    the maximum number of cards allowed in the hand
   */
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
      throw new IllegalArgumentException(
              "deck size is not big enough to fill required card states");
    }
    if (deck.contains(null)) {
      throw new IllegalArgumentException("deck contains null element");
    }
    if (new HashSet<>(deck).size() != deck.size()) {
      throw new IllegalArgumentException("duplicate cards are not allowed");
    }
    gameStart = true;
    List<SoloCard> deckCopy = new ArrayList<>();
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
    while (hand.size() < handSize) {
      drawForHand();
    }
  }

  /**
   * Creates deep copies of all cards and randomly shuffles them into a new deck.
   *
   * @param deck previous deck
   * @return shuffled version of previous deck.
   */
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
    return gameOver;
  }

  @Override
  public boolean isGameWon() {
    if (!gameStart || !gameOver) {
      throw new IllegalStateException("Game has not started/is over.");
    }
    return !lost && (hand.isEmpty() && deck.isEmpty());
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
      throw new IllegalArgumentException(
              "paletteNum must be between 0 and " + (palettes.size() - 1));
    }
    List<SoloCard> returnList = new ArrayList<>();
    for (SoloCard card : palettes.get(paletteNum)) {
      returnList.add(CardBuilder.makeCard(card.toString()));
    }
    return returnList;
  }

  @Override
  public SoloCard getCanvas() {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started");
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
