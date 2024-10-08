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

  /**
   * Constructor for SoloRedGameTextView that just takes in a model and sets it.
   *
   * @param model that the toString uses to get information.
   */
  public SoloRedGameTextView(RedGameModel<?> model) {
    this.model = model;
  }

  /**
   * Constructor for SoloRedGameTextView that takes both a model and appendable to append to.
   *
   * @param model  that the toString uses to get information.
   * @param append where to append output from render().
   */
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
  @Override
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
