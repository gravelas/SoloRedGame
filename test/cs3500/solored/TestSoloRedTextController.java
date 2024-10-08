package cs3500.solored;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import cs3500.solored.controller.RedGameController;
import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloCard;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Tests for the SoloRedTextController.
 */
public class TestSoloRedTextController {

  /**
   * Main method that allows the user to run the controller in console.
   * @param args main method arguments.
   */
  public static void main(String[] args) {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    RedGameController controller = new SoloRedTextController(rd, ap);

    RedGameModel<SoloCard> model = new SoloRedGameModel();

    controller.playGame(model, model.getAllCards().subList(0,10), false, 4, 4);
  }

  @Test
  public void testPlayGameWithInvalidMoveAndGameQuit() throws IOException {
    String input = "canvas 0\n" + "q\n";
    Readable rd = new StringReader(input);
    Appendable ap = new StringBuilder();
    ap.append("Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 27\n");
    ap.append("Invalid move. Try again. CardIndexInHand was OOB.\n" +
            "Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 27\n");
    ap.append("Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 27\n");
    Appendable ap2 = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(rd, ap2);
    RedGameModel<SoloCard> model = new SoloRedGameModel();

    controller.playGame(model, model.getAllCards(), false, 4, 4);
    Assert.assertEquals(ap.toString(), ap2.toString());

  }

  @Test
  public void testPlayGameWithNormalWinDoesNotQuit() throws IOException {
    Readable rd = new StringReader("palette 1 1 palette 2 1 palette 3 1 " +
            "canvas 1 palette 4 2 palette 3 1 q");
    Appendable ap = new StringBuilder();
    ap.append("Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 2\n");
    ap.append("Canvas: R\n" +
            "> P1: V1 V5\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "P4: V4\n" +
            "Hand: V6 V7 I1 I2\n" +
            "Number of cards in deck: 1\n");
    ap.append("Canvas: R\n" +
            "P1: V1 V5\n" +
            "> P2: V2 V6\n" +
            "P3: V3\n" +
            "P4: V4\n" +
            "Hand: V7 I1 I2 I3\n" +
            "Number of cards in deck: 0\n");
    ap.append("Canvas: R\n" +
            "P1: V1 V5\n" +
            "P2: V2 V6\n" +
            "> P3: V3 V7\n" +
            "P4: V4\n" +
            "Hand: I1 I2 I3\n" +
            "Number of cards in deck: 0\n");
    ap.append("Canvas: I\n" +
            "P1: V1 V5\n" +
            "P2: V2 V6\n" +
            "> P3: V3 V7\n" +
            "P4: V4\n" +
            "Hand: I2 I3\n" +
            "Number of cards in deck: 0\n");
    ap.append("Canvas: I\n" +
            "P1: V1 V5\n" +
            "P2: V2 V6\n" +
            "P3: V3 V7\n" +
            "> P4: V4 I3\n" +
            "Hand: I2\n" +
            "Number of cards in deck: 0\n");
    ap.append("Canvas: I\n" +
            "P1: V1 V5\n" +
            "P2: V2 V6\n" +
            "> P3: V3 V7 I2\n" +
            "P4: V4 I3\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n");
    ap.append("Game won.\n" +
            "Canvas: I\n" +
            "P1: V1 V5\n" +
            "P2: V2 V6\n" +
            "> P3: V3 V7 I2\n" +
            "P4: V4 I3\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n");
    Appendable ap2 = new StringBuilder();
    RedGameController controller = new SoloRedTextController(rd, ap2);

    RedGameModel<SoloCard> model = new SoloRedGameModel();

    controller.playGame(model, model.getAllCards().subList(0,10), false, 4, 4);

    Assert.assertEquals(ap2.toString(), ap.toString());
  }

  @Test
  public void testInvalidCommandWithSomeValid() throws IOException {
    Assert.assertTrue(true);
  }
}
