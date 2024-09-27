package cs3500.solored.model.hw02;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for Different Color Cards Canvas Rules.
 */
public class TestCanvasRules {

  List<SoloCard> palette1;
  List<SoloCard> palette2;
  List<SoloCard> palette3;
  List<SoloCard> palette4;
  List<List<SoloCard>> palettes;
  List<List<SoloCard>> testForTiesPalettes;

  SoloCard redCard;
  SoloCard orangeCard;
  SoloCard blueCard;
  SoloCard indigoCard;
  SoloCard violetCard;

  @Before
  public void setUp() {
    palette1 = new ArrayList<>();
    palette2 = new ArrayList<>();
    palette3 = new ArrayList<>();
    palette4 = new ArrayList<>();

    palette1.add(CardBuilder.makeCard("R3"));
    palette1.add(CardBuilder.makeCard("O3"));
    palette1.add(CardBuilder.makeCard("B6"));
    palette1.add(CardBuilder.makeCard("V5"));

    palette2.add(CardBuilder.makeCard("O1"));
    palette2.add(CardBuilder.makeCard("O2"));
    palette2.add(CardBuilder.makeCard("R3"));
    palette2.add(CardBuilder.makeCard("V4"));

    palette3.add(CardBuilder.makeCard("I3"));
    palette3.add(CardBuilder.makeCard("R4"));
    palette3.add(CardBuilder.makeCard("O5"));
    palette3.add(CardBuilder.makeCard("B7"));

    palette4.add(CardBuilder.makeCard("O3"));
    palette4.add(CardBuilder.makeCard("O3"));
    palette4.add(CardBuilder.makeCard("O6"));
    palette4.add(CardBuilder.makeCard("O6"));

    palettes = new ArrayList<>();
    palettes.add(palette1);
    palettes.add(palette2);
    palettes.add(palette3);

    testForTiesPalettes = new ArrayList<>();
    testForTiesPalettes.add(palette1);
    testForTiesPalettes.add(palette4);
    testForTiesPalettes.add(palette2);

    redCard = CardBuilder.makeCard("R1");
    orangeCard = CardBuilder.makeCard("O1");
    blueCard = CardBuilder.makeCard("B5");
    indigoCard = CardBuilder.makeCard("I2");
    violetCard = CardBuilder.makeCard("V2");

  }

  @Test
  public void testRedRuleSelectsCorrectPalette() {
    Assert.assertEquals(redCard.canvasRule(palettes), palette3);
  }

  @Test
  public void testRedRuleThrowsWithNullPalettes() {
    Assert.assertThrows(NullPointerException.class, () -> redCard.canvasRule(null));
  }

  @Test
  public void testRedRuleThrowsWithEmptyPalettes() {
    Assert.assertThrows(IllegalArgumentException.class, () -> redCard.canvasRule(new ArrayList<>()));
  }

  @Test
  public void testOrangeRuleSelectsCorrectPaletteWithNoTie() {
    Assert.assertEquals(orangeCard.canvasRule(palettes), palette1);
  }

  @Test
  public void testTiesWithOrangeRule() {
    Assert.assertEquals(orangeCard.canvasRule(testForTiesPalettes), palette4);
  }

  @Test
  public void testOrangeRuleThrowsWithEmptyPalettes() {
    Assert.assertThrows(IllegalArgumentException.class, () -> orangeCard.canvasRule(new ArrayList<>()));
  }

  @Test
  public void testOrangeRuleThrowsWithNullPalettes() {
    Assert.assertThrows(NullPointerException.class, () -> orangeCard.canvasRule(null));
  }

  @Test
  public void testBlueRuleSelectsCorrectPaletteWithNoTie() {
    Assert.assertEquals(blueCard.canvasRule(palettes), palette2);
  }

  @Test
  public void testBlueRuleThrowsWithEmptyPalettes() {
    Assert.assertThrows(IllegalArgumentException.class, () -> blueCard.canvasRule(new ArrayList<>()));
  }

  @Test
  public void testBlueRuleThrowsWithNullPalettes() {
    Assert.assertThrows(NullPointerException.class, () -> blueCard.canvasRule(null));
  }

  @Test
  public void testIndigoRuleSelectsCorrectPalette() {
    Assert.assertEquals(indigoCard.canvasRule(palettes), palette2);
  }

  @Test
  public void testIndigoRuleThrowsWithEmptyPalettes() {
    Assert.assertThrows(IllegalArgumentException.class, () -> indigoCard.canvasRule(new ArrayList<>()));
  }

  @Test
  public void testIndigoRuleThrowsWithNullPalettes() {
    Assert.assertThrows(NullPointerException.class, () -> indigoCard.canvasRule(null));
  }

  @Test
  public void testVioletRuleSelectsCorrectPalette() {
    Assert.assertEquals(violetCard.canvasRule(palettes), palette2);
  }

  @Test
  public void testVioletRuleThrowsWithEmptyPalettes() {
    Assert.assertThrows(IllegalArgumentException.class, () -> violetCard.canvasRule(new ArrayList<>()));
  }

  @Test
  public void testVioletRuleThrowsWithNullPalettes() {
    Assert.assertThrows(NullPointerException.class, () -> violetCard.canvasRule(null));
  }

}
