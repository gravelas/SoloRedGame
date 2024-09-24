package cs3500.solored.model.hw02;


public abstract class SoloCard implements Card {

  protected Color color;
  protected int num;

  /**
   * Creates a Card. Number must be between 1-7 (inclusive).
   * @param num number assigned
   * @throws IllegalArgumentException number isn't between 1-7
   */
  public SoloCard(int num) {
    if (num < 1 || num > 7) {
      throw new IllegalArgumentException("Num must be between 1 and 7");
    }
    this.num = num;
  }

  public int number() {
    return num;
  }

  public Color color() {
    return color;
  }

  @Override
  public String toString() {
    return color.toString() + num;
  }
}
