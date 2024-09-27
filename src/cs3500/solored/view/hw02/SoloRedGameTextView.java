package cs3500.solored.view.hw02;

import java.util.Iterator;

import cs3500.solored.model.hw02.RedGameModel;

/**
 * Way to view the SoloRedGame through a string output.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<?> model;

  public SoloRedGameTextView(RedGameModel<?> model) {
    this.model = model;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Canvas: ").append(model.getCanvas().toString().charAt(0)).append("\n");
    for (int paletteNumber = 0; paletteNumber < model.numPalettes(); paletteNumber++) {
      if (paletteNumber == model.winningPaletteIndex()) {
        sb.append("> ");
      }
      sb.append("P").append(paletteNumber + 1).append(": ");
      Iterator<?> card = model.getPalette(paletteNumber).iterator();
      while (card.hasNext()) {
        sb.append(card.next().toString());
        if (card.hasNext()) {
          sb.append(" ");
        }
      }
      sb.append("\n");
    }
    sb.append("Hand: ");
    Iterator<?> card = model.getHand().iterator();
    while (card.hasNext()) {
      sb.append(card.next().toString());
      if (card.hasNext()) {
        sb.append(" ");
      }
    }
    return sb.toString();
  }
}
