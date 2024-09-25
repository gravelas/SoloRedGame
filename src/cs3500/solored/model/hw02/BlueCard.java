package cs3500.solored.model.hw02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueCard extends SoloCard{

  /**
   * Creates a BlueCard. Num must be between 1-7 (inclusive).
   * @param num number of card
   */
  public BlueCard(int num) {
    super(num);
    this.color = Color.BLUE;
  }

  @Override
  public List<SoloCard> canvasRule(List<List<SoloCard>> palettes) {
    Map<List<SoloCard>, Integer> paletteBestColorCount = new HashMap<>();
    int bestCardColorCount = 0;
    for (List<SoloCard> palette : palettes) {
      for (Color color : Color.values()) {
        int cardColorCount = (int) palette.stream().filter((card) -> (card.color() == color)).count();
        if (cardColorCount > bestCardColorCount) {
          bestCardColorCount = cardColorCount;
        }
      }
      paletteBestColorCount.put(palette, bestCardColorCount);
    }
    return getSoloCards(palettes, paletteBestColorCount);
  }
}
