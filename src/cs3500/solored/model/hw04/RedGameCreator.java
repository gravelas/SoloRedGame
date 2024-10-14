package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloCard;
import cs3500.solored.model.hw02.SoloRedGameModel;

public class RedGameCreator {

  public enum GameType {
    BASIC, ADVANCED;

    public static GameType parseType(String gameType) {
      switch (gameType) {
        case "basic":
          return GameType.BASIC;
        case "advanced":
          return GameType.ADVANCED;
      }
      return null;
    }
  }

  public static RedGameModel<SoloCard> createGame(GameType type) {
    switch (type) {
      case BASIC:
        return new SoloRedGameModel();
      case ADVANCED:
        return new AdvancedSoloRedGameModel();
    }
    return null;
  }
}
