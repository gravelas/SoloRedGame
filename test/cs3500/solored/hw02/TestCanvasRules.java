package cs3500.solored.hw02;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.solored.model.hw02.CardBuilder;
import cs3500.solored.model.hw02.SoloCard;

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
    palette1.add(CardBuilder.makeCard("R3"));
    palette1.add(CardBuilder.makeCard("R6"));
    palette1.add(CardBuilder.makeCard("R5"));

    palette2.add(CardBuilder.makeCard("O1"));
    palette2.add(CardBuilder.makeCard("O4"));
    palette2.add(CardBuilder.makeCard("O6"));
    palette2.add(CardBuilder.makeCard("O2"));

    palette3.add(CardBuilder.makeCard("B3"));
    palette3.add(CardBuilder.makeCard("B4"));
    palette3.add(CardBuilder.makeCard("B5"));
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
    Assert.assertThrows(NullPointerException.class, () -> {redCard.canvasRule(null);});
  }

  @Test
  public void testOrangeRuleSelectsCorrectPaletteWithNoTie() {
    Assert.assertEquals(orangeCard.canvasRule(palettes), palette1);
  }

  @Test
  public void testOrangeRuleSelectsCorrectPaletteWithTie() {
    Assert.assertEquals(orangeCard.canvasRule(testForTiesPalettes), palette1);
  }
}
