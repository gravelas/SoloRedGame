package cs3500.solored.model.hw02;

public class CardBuilder {

  static SoloCard makeCard(String cardString) {
    if (cardString.charAt(0) == 'R') {
      return (SoloCard) new RedCard((Integer.parseInt(cardString.substring(1))));
    } else if (cardString.charAt(0) == 'O') {
      return (SoloCard) new OrangeCard((Integer.parseInt(cardString.substring(1))));
    } else if (cardString.charAt(0) == 'B') {
      return (SoloCard) new BlueCard((Integer.parseInt(cardString.substring(1))));
    } else if (cardString.charAt(0) == 'I') {
      return (SoloCard) new IndigoCard((Integer.parseInt(cardString.substring(1))));
    } else if (cardString.charAt(0) == 'V') {
      return (SoloCard) new VioletCard((Integer.parseInt(cardString.substring(1))));
    } else {
      return null;
    }
  }
}
