package cs3500.solored.controller;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.view.hw02.SoloRedGameTextView;

public class SoloRedTextController implements RedGameController{

  private Readable readable;
  private Appendable appendable;
  private SoloRedGameTextView textView;

  public SoloRedTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null) { throw new IllegalArgumentException("Readable is null"); }
    if (ap == null) { throw new IllegalArgumentException("Appendable is null"); }
    readable = rd;
    appendable = ap;
  }

  @Override
  public <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes, int handSize) throws IllegalArgumentException, IllegalStateException {
    model.startGame(deck, shuffle, numPalettes, handSize);
    while (!model.isGameOver()) {
      textView = new SoloRedGameTextView(model, appendable);
      render(textView);
      append(appendable, "Number of cards in deck: " + model.numOfCardsInDeck());
      parseCommand(model, read(readable, model));
    }
  }

  private void render(SoloRedGameTextView textView) {
    try {
      textView.render();
    } catch (IOException e) {
      throw new IllegalStateException("Cannot render game", e);
    }
  }

  private void append(Appendable appendable, String msg) {
    try {
      appendable.append(msg).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Cannot append message", e);
    }
  }

  private <C extends Card> String read(Readable readable, RedGameModel<C> model) {
    String output = "";
    try {
      readable.read(CharBuffer.wrap(output));
    } catch (IOException e) {
      throw new IllegalStateException("Cannot read message", e);
    }
    if (Arrays.stream(output.split(" ")).anyMatch(s -> s.equalsIgnoreCase("q"))) {
      gameQuit(model);
    }
    return output;
  }

  private <C extends Card> void parseCommand(RedGameModel<C> model, String input) {
    List<String> commandAndArgs = new ArrayList<>(List.of(input.split(" ")));
    switch (commandAndArgs.get(0)) {
      case "palette":
        while (commandAndArgs.size() < 3) {
          commandAndArgs.addAll(List.of(read(readable, model).split(" ")));
        }
        playPalette(model, commandAndArgs.subList(1, commandAndArgs.size()));
        return;
      case "canvas":
        while (commandAndArgs.size() < 2) {
          commandAndArgs.addAll(List.of(read(readable, model).split(" ")));
        }
        playCanvas(model, commandAndArgs.subList(1, commandAndArgs.size()));
        return;
      default:
        append(appendable, "Invalid Command. Try again. Enter palette, canvas, or q/Q.");
        input = read(readable, model);
        parseCommand(model, input);
    }
  }

  private <C extends Card> void gameQuit(RedGameModel<C> model) {
    append(appendable, "Game quit!");
    append(appendable, "State of game when quit:");
    render(textView);
    append(appendable, "Number of cards in deck " + model.numOfCardsInDeck());
    System.exit(1);
  }

  private <C extends Card> void playCanvas(RedGameModel<C> model, List<String> indices) {
    try {
      model.playToCanvas(Integer.parseInt(indices.get(0)));
    } catch (IllegalArgumentException e) {
      append(appendable, "Invalid move. Try again. CardIndexInHand was invalid.");
      parseCommand(model, read(readable, model));
    } catch (IllegalStateException e) {
      append(appendable, "Invalid move. Try again. Only one card left in hand.");
      parseCommand(model, read(readable, model));
    }
  }

  private <C extends Card> void playPalette(RedGameModel<C> model, List<String> indices) {
    try {
      model.playToPalette(Integer.parseInt(indices.get(0)), Integer.parseInt(indices.get(1)));
    } catch (IllegalArgumentException e) {
      append(appendable, "Invalid move. Try again. CardIndexInHand was invalid.");
      parseCommand(model, read(readable, model));
      return;
    } catch (IllegalStateException e) {
      append(appendable, "Invalid move. Try again. Palette was already winning.");
      parseCommand(model, read(readable, model));
      return;
    }
    model.drawForHand();
  }

}
