package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloCard;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Factory class that can parse GameTypes as well as
 * create different types of models based on inputs.
 */
public class RedGameCreator {

  /**
   * Enum for the two different game types.
   */
  public enum GameType {
    BASIC, ADVANCED;

    /**
     * Similar to the parseInt function. Takes a string of a gameType and returns the enum.
     *
     * @param gameType string input
     * @return game type correlated to string input
     */
    public static GameType parseType(String gameType) {
      switch (gameType) {
        case "basic":
          return GameType.BASIC;
        case "advanced":
          return GameType.ADVANCED;
        default:
          return null;
      }
    }
  }

  /**
   * Returns a version of model based off of the input type.
   *
   * @param type the type that decides which model to return
   * @return the model returned
   */
  public static RedGameModel<SoloCard> createGame(GameType type) {
    switch (type) {
      case BASIC:
        return new SoloRedGameModel();
      case ADVANCED:
        return new AdvancedSoloRedGameModel();
      default:
        return null;
    }
  }
}
