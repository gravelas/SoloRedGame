package cs3500.solored;

import cs3500.solored.model.hw02.SoloRedGameModel;

import org.junit.Before;
import org.junit.Test;

/**
 * Class meant to test the implementation of the SoloRedGameModel.
 */
public class TestSoloRedGameModel extends TestModel {

  @Override
  @Before
  public void testSetup() {
    modelDeck = new SoloRedGameModel();
  }

}