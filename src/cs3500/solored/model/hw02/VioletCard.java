package cs3500.solored.model.hw02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SoloCard with the color Violet.
 */
public class VioletCard extends SoloCard {

  /**
   * Creates a VioletCard. Num must be between 1-7 (inclusive).
   * @param num number of card
   */
  public VioletCard(int num) {
    super(Color.VIOLET, num);
  }

  /**
   * Finds the palette with the most amount of cards with a number less than 4.
   * @param palettes the palettes to look through.
   * @return the palette that wins.
   */
  @Override
  public List<SoloCard> canvasRule(List<List<SoloCard>> palettes) {
    if (palettes == null) {
      throw new IllegalArgumentException("Palette must not be null");
    }
    if (palettes.isEmpty()) {
      throw new IllegalArgumentException("Palette must not be empty.");
    }
    Map<List<SoloCard>, Integer> paletteToCardsBellow4 = new HashMap<>();
    for (List<SoloCard> palette : palettes) {
      paletteToCardsBellow4.put(palette, (int) palette.stream().filter((card) ->
              (card.number() < 4)).count());
    }
    return biggestOrRedCardWinner(palettes, paletteToCardsBellow4);
  }
}
