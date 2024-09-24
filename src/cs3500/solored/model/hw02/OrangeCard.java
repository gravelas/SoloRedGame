package cs3500.solored.model.hw02;

public class OrangeCard extends SoloCard{

  /**
   * Creates a OrangeCard. Num must be between 1-7 (inclusive).
   * @param num number of card
   */
  public OrangeCard(int num) {
    super(num);
    this.color = Color.ORANGE;
  }
}
