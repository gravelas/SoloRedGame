package cs3500.solored.view.hw02;

import java.io.IOException;
import java.util.Iterator;

import cs3500.solored.model.hw02.RedGameModel;

/**
 * Way to view the SoloRedGame through a string output.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<?> model;
  private Appendable append;

  public SoloRedGameTextView(RedGameModel<?> model) {
    this.model = model;
  }

  public SoloRedGameTextView(RedGameModel<?> model, Appendable append) {
    if (append == null) {
      throw new IllegalArgumentException("Appendable was null");
    }
    this.model = model;
    this.append = append;
  }

  /**
   * Returns a formatted string of the board view.
   *
   * @return a string formatted to show the board state.
   */
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

  @Override
  public void render() throws IOException {
    append.append(toString());
  }
}
