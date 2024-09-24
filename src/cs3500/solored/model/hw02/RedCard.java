package cs3500.solored.model.hw02;

public class RedCard extends SoloCard{

  /**
   * Creates a RedCard. Number must be between 1-7 (inclusive).
   * @param num number of card
   */
  public RedCard(int num) {
    super(num);
    this.color = Color.RED;
  }
}
