package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * Controller interface for RedSolo.
 */
public interface RedGameController {

  /**
   * Given the following parameters, plays a game of SoloRed.
   * Will provide feedback on invalid moves and commands.
   * Additionally, the input will filter out Strings/negative numbers
   * when numbers should be next.
   *
   * @param model       RedGameModel that the game is based off of.
   * @param deck        A list of cards that will be put into the model.
   * @param shuffle     Should the list be shuffled or not.
   * @param numPalettes How many palettes in the SoloRed game.
   * @param handSize    How big is the players hand.
   * @param <C>         The card implementation that extends the card interface.
   * @throws IllegalArgumentException If something is wrong with the model,
   *                                  either its null or startGame doesn't work.
   * @throws IllegalStateException    If something is wrong with either the readable or the appendable.
   */
  public <C extends Card> void playGame(
          RedGameModel<C> model,
          List<C> deck,
          boolean shuffle,
          int numPalettes,
          int handSize
  ) throws IllegalArgumentException, IllegalStateException;
}
