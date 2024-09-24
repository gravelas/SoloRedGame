package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.List;

public class RedCard extends SoloCard{

  /**
   * Creates a RedCard. Number must be between 1-7 (inclusive).
   * @param num number of card
   */
  public RedCard(int num) {
    super(num);
    this.color = Color.RED;
  }

  @Override
  public List<Boolean> canvasRule(List<List<SoloCard>> palettes) {
    int largestPaletteIndex = 0;
    int largestPaletteNumber = -1;
    for (int paletteIndex = 1; paletteIndex < palettes.size(); paletteIndex++) {
        int largestCardNum = palettes.get(paletteIndex).get(0).number();
        for (int cardIndex = 1; cardIndex < palettes.get(paletteIndex).size(); cardIndex++) {
          if (palettes.get(paletteIndex).get(cardIndex).number() > largestCardNum) {
            largestCardNum = palettes.get(paletteIndex).get(cardIndex).number();
          }
        }
        if (largestCardNum > largestPaletteNumber) {
          largestPaletteIndex = paletteIndex;
          largestPaletteNumber = largestCardNum;
        }
      }
    List<Boolean> winningPaletteList = new ArrayList<>();
    for (int paletteIndex = 0; paletteIndex < palettes.size(); paletteIndex++) {
      winningPaletteList.add(paletteIndex == largestPaletteIndex);
    }
    return winningPaletteList;
  }

}
