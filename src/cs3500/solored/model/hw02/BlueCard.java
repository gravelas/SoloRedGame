package cs3500.solored.model.hw02;

public class BlueCard extends SoloCard{

  /**
   * Creates a BlueCard. Num must be between 1-7 (inclusive).
   * @param num number of card
   */
  public BlueCard(int num) {
    super(num);
    this.color = Color.BLUE;
  }
}
