/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import edu.cs2335.tsunami.stratagem.kernel.Building;
import edu.cs2335.tsunami.stratagem.kernel.TrainingAcademy;
import edu.cs2335.tsunami.stratagem.kernel.WarFactory;
import edu.cs2335.tsunami.stratagem.kernel.Shipyard;
import junit.framework.TestCase;
import junit.framework.Assert;

/**
 * @author squinn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BuildingTest extends TestCase {

  /** training academy building */
  private Building trainingAcademy;
  
  /** shipyard building */
  private Building shipyard;
  
  /** war factory building */
  private Building warFactory;
  
  /**
   * Construct this test case
   * @param s ayyyy caramba
   */
  public BuildingTest(String s) {
    super(s);
  }
  
  /**
   * Build this test case
   */
  public void setUp() {
    trainingAcademy = new TrainingAcademy();
    warFactory = new WarFactory();
    shipyard = new Shipyard();
  }
  
  /**
   * Destroy this test case
   */
  public void tearDown() {
  }
  
  /**
   * Test this building's cost
   *
   */
  public void testCost() {
    trainingAcademy.setCost(50);
    trainingAcademy.setCost(100);
    Assert.assertTrue(trainingAcademy.getCost() == 100);
    Assert.assertTrue(TrainingAcademy.COSTMONEY == 200);
  }
}
