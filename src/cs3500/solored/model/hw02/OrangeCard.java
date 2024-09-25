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
    return getSoloCards(palettes, paletteBestNumberCount);
  }
}

