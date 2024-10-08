package cs3500.solored.model.hw02;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An implementation of Card that has a number and a color.
 */
public abstract class SoloCard implements Card {

  protected final Color color;
  protected final int num;

  /**
   * Creates a Card. Number must be between 1-7 (inclusive).
   * @param num number assigned
   * @throws IllegalArgumentException number isn't between 1-7
   */
  public SoloCard(Color color, int num) {
    if (num < 1 || num > 7) {
      throw new IllegalArgumentException("Num must be between 1 and 7");
    } if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    this.num = num;
    this.color = color;
  }

  public int number() {
    return num;
  }

  public Color color() {
    return color;
  }

  public abstract List<SoloCard> canvasRule(List<List<SoloCard>> palettes);

  @Override
  public String toString() {
    return color.toString() + num;
  }

  protected List<SoloCard> biggestOrRedCardWinner(
      List<List<SoloCard>> palettes, Map<List<SoloCard>, Integer> paletteBestNumberCount) {
    List<SoloCard> bestPalette = palettes.get(0);
    int bestPaletteNumberCount = paletteBestNumberCount.get(palettes.get(0));
    boolean biggestIsTie = false;
    for (int paletteNumber = 1; paletteNumber < palettes.size(); paletteNumber++) {
      List<SoloCard> palette = palettes.get(paletteNumber);
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

  @Override
  public boolean equals(Object o) {
    return (this.color == ((SoloCard) o).color() && (this.num == ((SoloCard) o).number()));
  }

  @Override
  public int hashCode() {
    return color.hashCode() + num;
  }
}
