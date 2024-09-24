package cs3500.solored.model.hw02;

public class VioletCard extends SoloCard{

  /**
   * Creates a VioletCard. Num must be between 1-7 (inclusive).
   * @param num number of card
   */
  public VioletCard(int num) {
    super(num);
    this.color = Color.VIOLET;
  }
}
