package cs3500.solored.controller;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.view.hw02.SoloRedGameTextView;

/**
 * Controller that implements the RedGameController interface.
 * Has methods that allow the game of SoloRed to be played via text with Readables and Appendables.
 */
public class SoloRedTextController implements RedGameController {

  private final Scanner scan;
  private final Appendable appendable;
  private SoloRedGameTextView textView;

  /**
   * Creates a SoloRedTextController.
   *
   * @param rd Readable that is used to get input for the controller.
   * @param ap Appendable that is used for the controller's output.
   * @throws IllegalArgumentException If readable or appendable is null.
   */
  public SoloRedTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null) {
      throw new IllegalArgumentException("Readable is null");
    }
    if (ap == null) {
      throw new IllegalArgumentException("Appendable is null");
    }
    appendable = ap;
    scan = new Scanner(rd);
  }

  @Override
  public <C extends Card> void playGame(
          RedGameModel<C> model, List<C> deck,
          boolean shuffle, int numPalettes,
          int handSize
  ) throws IllegalArgumentException, IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("RedGameModel is null");
    }
    try {
      model.startGame(deck, shuffle, numPalettes, handSize);
    } catch (IllegalStateException | IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
    textView = new SoloRedGameTextView(model, appendable);
    if (model.isGameOver()) {
      if (model.isGameWon()) {
        append(appendable, "Game won." + "\n");
      } else if (!model.isGameWon()) {
        append(appendable, "Game lost." + "\n");
      }
      printState(model, appendable);
      return;
    }
    printState(model, appendable);
    try {
      parseCommand(model, read());
    } catch (EndException e) {
      printState(model, appendable);
      if (model.isGameWon()) {
        append(appendable, "Game won." + "\n");
      } else {
        append(appendable, "Game lost." + "\n");
      }
      printState(model, appendable);
    } catch (QuitException e) {
      gameQuit(model);
    }
  }

  /**
   * Calls the render method of the textView. Made to deal with the IOException.
   *
   * @param textView the SoloRedGameTextView to call the render method of.
   * @throws IllegalStateException If the rendering fails.
   */
  private void render(SoloRedGameTextView textView) throws IllegalStateException {
    try {
      textView.render();
    } catch (IOException e) {
      throw new IllegalStateException("Cannot render game", e);
    }
  }

  /**
   * Appends the message to the appendable. Made to deal with the IOException.
   *
   * @param appendable Where to append to.
   * @param msg        What to append to the appendable.
   * @throws IllegalStateException
   */
  private void append(Appendable appendable, String msg) throws IllegalStateException {
    try {
      appendable.append(msg);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot append message", e);
    }
  }

  /**
   * Reads the next line from the scanner. Made to deal with NoSuchElementException.
   *
   * @return String that contains the next scanner line.
   * @throws IllegalStateException If there is nothing more to read.
   */
  private String read() throws IllegalStateException {
    String output = "";
    try {
      output = scan.nextLine();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException();
    }
    return output;
  }

  /**
   * Determines the command being run, then sends the args (filtered via helper methods) to either
   * play palette or play canvas.
   *
   * @param model The model that the operations are being run on.
   * @param input The first read input.
   * @param <C>   Card implementation that is being used.
   * @throws QuitException if the program detects a Q input.
   * @throws EndException  if the program detecs a game over.
   */
  private <C extends Card> void parseCommand(RedGameModel<C> model, String input) throws QuitException, EndException {
    Deque<String> commandAndArgs = new ArrayDeque<>(inputFilterInts(input));
    while (true) {
      if (commandAndArgs.isEmpty()) {
        commandAndArgs.addAll(inputFilterInts(read()));
      }
      if (model.isGameOver()) {
        throw new EndException();
      }
      switch (commandAndArgs.pop()) {
        case "palette":
          commandAndArgs = new ArrayDeque<>(
                  paletteInputFilterStrings(new ArrayList<>(commandAndArgs))
          );
          while (commandAndArgs.size() < 2) {
            if (commandAndArgs.size() == 1 && commandAndArgs.peek().equalsIgnoreCase("Q")) {
              throw new QuitException();
            }
            commandAndArgs.addAll(inputFilterInts(read()));
            commandAndArgs = new ArrayDeque<>(
                    paletteInputFilterStrings(new ArrayList<>(commandAndArgs))
            );
          }
          playPalette(model, commandAndArgs);
          break;
        case "canvas":
          commandAndArgs = new ArrayDeque<>(
                  canvasInputFilterStrings(new ArrayList<>(commandAndArgs))
          );
          while (commandAndArgs.isEmpty()) {
            commandAndArgs.addAll(inputFilterInts(read()));
          }
          commandAndArgs = new ArrayDeque<>(
                  canvasInputFilterStrings(new ArrayList<>(commandAndArgs))
          );
          playCanvas(model, commandAndArgs);
          break;
        case "q":
        case "Q":
          throw new QuitException();
        default:
          append(appendable, "Invalid command. Try again. Enter palette, canvas, or q/Q.\n");
          break;
      }
      printState(model, appendable);
    }
  }

  /**
   * Prints out 'game quit' and the final game state of model.
   *
   * @param model the model to get the game state from.
   * @param <C>   the card implementation used.
   */
  private <C extends Card> void gameQuit(RedGameModel<C> model) {
    append(appendable, "Game quit!\n");
    append(appendable, "State of game when quit:\n");
    render(textView);
    append(appendable, "\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");
  }

  /**
   * Calls the models playToCanvas method, adjusting the 1 start player index to the 0 start index.
   *
   * @param model   the model that the playToCanvas method is called on.
   * @param indices index used (plus one because 1 start vs 0 start).
   * @param <C>     card implementation used.
   * @throws QuitException if next arg is q, then quit.
   */
  private <C extends Card> void playCanvas(RedGameModel<C> model, Deque<String> indices) throws QuitException {
    String arg = indices.pop();
    if (arg.equalsIgnoreCase("q")) {
      throw new QuitException();
    }
    try {
      model.playToCanvas(Integer.parseInt(arg) - 1);
    } catch (IllegalArgumentException e) {
      append(appendable, "Invalid move. Try again. CardIndexInHand was OOB.\n");
    } catch (IllegalStateException e) {
      append(appendable, "Invalid move. Try again. Only one card left in hand.\n");
    }
    if (model.isGameOver()) {
      throw new EndException();
    }
  }

  /**
   * Calls the models playToPalette method, adjusting the 1 start player index to the 0 start index.
   *
   * @param model   the model that the playToPalette method is called on
   * @param indices index used (plus one because 1 start vs 0 start)
   * @param <C>     card implementation used.
   * @throws QuitException if next arg is q, then quit.
   */
  private <C extends Card> void playPalette(RedGameModel<C> model, Deque<String> indices) throws QuitException {
    String firstArg = indices.pop();
    String secondArg = indices.pop();
    if (firstArg.equalsIgnoreCase("q") || secondArg.equalsIgnoreCase("q")) {
      throw new QuitException();
    }
    try {
      model.playToPalette(Integer.parseInt(firstArg) - 1, Integer.parseInt(secondArg) - 1);
    } catch (IllegalArgumentException e) {
      append(appendable, "Invalid move. Try again. CardIndexInHand was OOB.\n");
      return;
    } catch (IllegalStateException e) {
      append(appendable, "Invalid move. Try again. Palette was already winning.\n");
      return;
    }
    if (model.isGameOver()) {
      throw new EndException();
    }
    model.drawForHand();
  }

  /**
   * Appends the current state of the model to the appendable.
   *
   * @param model      the model that the state is gotten from.
   * @param appendable the appendable to append to.
   * @param <C>        card implementation used.
   */
  private <C extends Card> void printState(RedGameModel<C> model, Appendable appendable) {
    render(textView);
    append(appendable, "\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");
  }

  /**
   * Filters ints from string list that are negative.
   *
   * @param input separated by spaces into a list.
   * @return list of strings without negative numbers.
   */
  private List<String> inputFilterInts(String input) {
    List<String> result = Arrays.stream(input.split(" ")).collect(Collectors.toList());
    for (int index = 0; index < result.size(); index++) {
      Integer num;
      try {
        num = Integer.parseInt(result.get(index));
      } catch (NumberFormatException e) {
        num = null;
      }
      if (num != null && num < 0) {
        result.remove(index);
        index--;
      }
    }
    return result;
  }

  /**
   * Filters out strings from the input that are in between two numbers.
   *
   * @param input list of strings.
   * @return list that starts with two numbers, or only has one number.
   */
  private List<String> paletteInputFilterStrings(List<String> input) {
    boolean twoNum = false;
    for (int index = 0; index < input.size() && !twoNum; index++) {
      Integer num;
      try {
        num = Integer.parseInt(input.get(index));
      } catch (NumberFormatException e) {
        num = null;
      }
      if (num == null && !input.get(index).equalsIgnoreCase("q")) {
        input.remove(index);
        index--;
      }
      Integer first;
      Integer second;
      if (index < input.size() - 1 && index > -1) {
        try {
          first = Integer.parseInt(input.get(index));
        } catch (NumberFormatException e) {
          first = null;
        }
        try {
          second = Integer.parseInt(input.get(index + 1));
        } catch (NumberFormatException e) {
          second = null;
        }
        if (first != null && second != null) {
          twoNum = true;
        }
      }
    }
    return input;
  }

  /**
   * Filters out strings from the input that are before the first number.
   *
   * @param input list of strings.
   * @return list that starts with one number, or is empty.
   */
  private List<String> canvasInputFilterStrings(List<String> input) {
    boolean oneNum = false;
    for (int index = 0; index < input.size() && !oneNum; index++) {
      Integer num;
      try {
        num = Integer.parseInt(input.get(index));
      } catch (NumberFormatException e) {
        num = null;
      }
      if (num == null && !input.get(index).equalsIgnoreCase("q")) {
        input.remove(index);
        index--;
      }
      Integer first;
      if (index > -1) {
        try {
          first = Integer.parseInt(input.get(index));
        } catch (NumberFormatException e) {
          first = null;
        }
        if (first != null) {
          oneNum = true;
        }
      }
    }
    return input;
  }

  /**
   * Exception that should be thrown whenever the program needs to quit.
   */
  static class QuitException extends RuntimeException {

  }

  /**
   * Exception that should be thrown whenever the model says the game is over.
   */
  static class EndException extends RuntimeException {

  }
}
