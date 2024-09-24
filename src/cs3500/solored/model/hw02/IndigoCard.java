package cs3500.solored.model.hw02;

public class IndigoCard extends SoloCard{

  /**
   * Creates a IndigoCard. Num must be between 1-7 (inclusive).
   * @param num number of card
   */
  public IndigoCard(int num) {
    super(num);
    this.color = Color.INDIGO;
  }

}
