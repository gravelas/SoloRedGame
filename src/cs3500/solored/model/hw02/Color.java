package cs3500.solored.model.hw02;

public enum Color {
  VIOLET, INDIGO, BLUE, ORANGE, RED;

  @Override
  public String toString() {
    return name().substring(0,1).toUpperCase();
  }
}
