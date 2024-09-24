package cs3500.solored.model.hw02;

public enum Color {
  RED, ORANGE, BLUE, INDIGO, VIOLET;

  @Override
  public String toString() {
    return name().substring(0,1).toUpperCase();
  }
}
