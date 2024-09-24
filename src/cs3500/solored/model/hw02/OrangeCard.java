package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrangeCard extends SoloCard{

  /**
   * Creates a OrangeCard. Num must be between 1-7 (inclusive).
   * @param num number of card
   */
  public OrangeCard(int num) {
    super(num);
    this.color = Color.ORANGE;
  }

  @Override
  public List<SoloCard> canvasRule(List<List<SoloCard>> palettes) {
    List<List<SoloCard>> listOfTies = new ArrayList<List<SoloCard>>();
    Map<List<SoloCard>, Integer> paletteBestNumberCount = new HashMap<>();
    int bestCardNumberCount = 0;
    for (List<SoloCard> palette : palettes) {
      for (int cardNumber = 1; cardNumber <= 7; cardNumber++) {
        int finalCardNumber = cardNumber;
        int cardNumberCount = (int) palette.stream().filter((card) -> (card.number() == finalCardNumber)).count();
        if (cardNumberCount > bestCardNumberCount) {
          bestCardNumberCount = cardNumberCount;
        }
      }
      paletteBestNumberCount.put(palette, bestCardNumberCount);
    }
    List<SoloCard> bestPalette = List.of();
    int bestPaletteNumberCount = 0;
    boolean biggestIsTie = false;
    for (List<SoloCard> palette : palettes) {
      if (paletteBestNumberCount.get(palette) > paletteBestNumberCount.get(bestPalette)) {
        bestPalette = palette;
        bestPaletteNumberCount = paletteBestNumberCount.get(palette);
        biggestIsTie = false;
      } else if (paletteBestNumberCount.get(palette) == paletteBestNumberCount.get(bestPalette)) {
        biggestIsTie = true;
      }
    }
    List<List<SoloCard>> tiedPalette = new ArrayList<>();
    if (biggestIsTie) {
      for (List<SoloCard> palette : palettes) {
        if (paletteBestNumberCount.get(palette) == bestPaletteNumberCount) {
          tiedPalette.add(palette);
        }
      }
      return CardBuilder.makeCard("R1").canvasRule(tiedPalette);
    }
    return bestPalette;
  }
}

