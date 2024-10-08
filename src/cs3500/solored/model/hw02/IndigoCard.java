package cs3500.solored.model.hw02;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * SoloCard with Color Indigo.
 */
public class IndigoCard extends SoloCard {

  /**
   * Creates a IndigoCard. Num must be between 1-7 (inclusive).
   * @param num number of card
   */
  public IndigoCard(int num) {
    super(Color.INDIGO, num);
  }

  /**
   * Finds the longest sequential increasing run of cards (i.e. 1-2-3).
   * @param palettes the palettes it searches through
   * @return the palette with the biggest increasing run
   */
  @Override
  public List<SoloCard> canvasRule(List<List<SoloCard>> palettes) {
    if (palettes == null) {
      throw new IllegalArgumentException("Palette must not be null");
    }
    if (palettes.isEmpty()) {
      throw new IllegalArgumentException("Palette must not be empty.");
    }
    Map<List<SoloCard>, Integer> palleteSetSizes = new HashMap<>();
    for (List<SoloCard> palette : palettes) {
      Set<SoloCard> setPalette = new TreeSet<>(Comparator.comparingInt(SoloCard::number));
      setPalette.addAll(palette);
      int biggestRun = 1;
      int currentRun = 1;
      for (SoloCard card : setPalette) {
        if (setPalette.stream().anyMatch(
            (nextCard) -> (nextCard.number() == 1 + card.number()))) {
          currentRun++;
          if (currentRun > biggestRun) {
            biggestRun = currentRun;
          }
        } else {
          currentRun = 1;
        }
      }
      palleteSetSizes.put(palette, biggestRun);
    }
    return biggestOrRedCardWinner(palettes, palleteSetSizes);
  }

}
