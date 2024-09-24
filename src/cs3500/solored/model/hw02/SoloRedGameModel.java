package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.List;

public class RedGame implements RedGameModel<SoloCard>{

  List<SoloCard> deck;
  List<List<SoloCard>> palettes;
  List<SoloCard> hand;
  SoloCard canvas;
  int handSize;

  public RedGame() {
    deck = new ArrayList<>();
    palettes = new ArrayList<>();
    hand = new ArrayList<>();
    canvas = null;
    handSize = 0;
  }

  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    palettes.get(paletteIdx).add(0, hand.get(cardIdxInHand));
    hand.remove(cardIdxInHand);
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    canvas = hand.get(cardIdxInHand);
    hand.remove(cardIdxInHand);
  }

  @Override
  public void drawForHand() {
    while (hand.size() < handSize) {
      if (numOfCardsInDeck() > 0) {
        hand.add(deck.get(0));
        deck.remove(0);
      } else {
        break;
      }
    }
  }

  @Override
  public void startGame(List<SoloCard> deck, boolean shuffle, int numPalettes, int handSize) {
    if (shuffle) {
      deck = shuffle(deck);
    }
    this.deck = deck;
    for (int i = 0; i < numPalettes; i++) {
      palettes.add(new ArrayList<>());
    }
    this.handSize = handSize;
  }

  private List<SoloCard> shuffle(List<SoloCard> deck) {
    List<SoloCard> shuffled = new ArrayList<>();
    while (!deck.isEmpty()) {
      int randomIndex = (int) (Math.random() * (deck.size()-1));
      shuffled.add(CardBuilder.makeCard(deck.get(randomIndex).toString()));
      deck.remove(randomIndex);
    }
    return shuffled;
  }

  @Override
  public int numOfCardsInDeck() {
    return deck.size();
  }

  @Override
  public int numPalettes() {
    return palettes.size();
  }

  @Override
  public int winningPaletteIndex() {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return isGameWon() || winningPaletteIndex() == -1;
  }

  @Override
  public boolean isGameWon() {
    return hand.isEmpty() && deck.isEmpty();
  }

  @Override
  public List<SoloCard> getHand() {
    List<SoloCard> returnList = new ArrayList<>();
    for (SoloCard card : hand) {
      returnList.add(CardBuilder.makeCard(card.toString()));
    }
    return returnList;
  }

  @Override
  public List<SoloCard> getPalette(int paletteNum) {
    List<SoloCard> returnList = new ArrayList<>();
    for (SoloCard card : palettes.get(paletteNum)) {
      returnList.add(CardBuilder.makeCard(card.toString()));
    }
    return returnList;
  }

  @Override
  public SoloCard getCanvas() {
    return CardBuilder.makeCard(canvas.toString());
  }

  @Override
  public List<SoloCard> getAllCards() {
    List<SoloCard> returnList = new ArrayList<>();
    returnList.addAll(getHand());
    for (int palIndex = 0; palIndex < palettes.size(); palIndex++) {
      returnList.addAll(getPalette(palIndex));
    }
    returnList.add(getCanvas());
    return returnList;
  }
}
