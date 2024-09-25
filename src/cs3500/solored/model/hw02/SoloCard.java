package cs3500.solored.model.hw02;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class SoloCard implements Card {

  protected Color color;
  protected int num;

  /**
   * Creates a Card. Number must be between 1-7 (inclusive).
   * @param num number assigned
   * @throws IllegalArgumentException number isn't between 1-7
   */
  public SoloCard(int num) {
    if (num < 1 || num > 7) {
      throw new IllegalArgumentException("Num must be between 1 and 7");
    }
    this.num = num;
  }

  public int number() {
    return num;
  }

  public Color color() {
    return color;
  }

  abstract public List<SoloCard> canvasRule(List<List<SoloCard>> palettes);

  @Override
  public String toString() {
    return color.toString() + num;
  }

  protected List<SoloCard> biggestOrRedCardWinner(List<List<SoloCard>> palettes, Map<List<SoloCard>, Integer> paletteBestNumberCount) {
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
