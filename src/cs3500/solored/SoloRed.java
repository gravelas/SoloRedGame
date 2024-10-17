package cs3500.solored;

import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import cs3500.solored.controller.RedGameController;
import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloCard;
import cs3500.solored.model.hw04.RedGameCreator;

public class SoloRed {

  private static final int defaultHandSize = 7;
  private static final int defaultPalettesSize = 4;

  public static void main(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("no arguments passed.");
    }

    Deque<String> arguments = new ArrayDeque<>(List.of(args));
    RedGameController controller = new SoloRedTextController(new InputStreamReader(System.in), System.out);
    RedGameModel<SoloCard> model;

    RedGameCreator.GameType type = RedGameCreator.GameType.parseType(arguments.pop());
    if (type == null) {
      throw new IllegalArgumentException("game type is not valid.");
    }
    model = RedGameCreator.createGame(type);
    int palettesSize = defaultPalettesSize;
    int handSize = defaultHandSize;
    if (!arguments.isEmpty()) {
      palettesSize = parsePaletteInt(arguments.pop());
      if (!arguments.isEmpty()) {
        handSize = parseHandInt(arguments.pop());
      }
    }
    try {
      controller.playGame(model, model.getAllCards(), true, palettesSize, handSize);
    } catch (IllegalStateException ignored) {
      //quit
    } catch (IllegalArgumentException e) {
      controller.playGame(model, model.getAllCards(), true, defaultPalettesSize, defaultHandSize);
    }

  }

  public static int parsePaletteInt(String input) {
    int inputPaletteSize;
    try {
      inputPaletteSize = Integer.parseInt(input);
    } catch (NumberFormatException e) {
      inputPaletteSize = defaultPalettesSize;
    }
    if (inputPaletteSize < 1) {
      inputPaletteSize = defaultPalettesSize;
    }
    return inputPaletteSize;
  }

  public static int parseHandInt(String input) {
    int inputHandSize;
    try {
      inputHandSize = Integer.parseInt(input);
    } catch (NumberFormatException e) {
      inputHandSize = defaultHandSize;
    }
    if (inputHandSize < 1) {
      inputHandSize = defaultHandSize;
    }
    return inputHandSize;
  }
}
