package cs3500.solored;

import org.junit.Before;

import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;

public class TestAdvancedSoloRedGameModel extends TestModel {

  @Override
  @Before
  public void testSetup() {
    modelDeck = new AdvancedSoloRedGameModel();
  }
}
