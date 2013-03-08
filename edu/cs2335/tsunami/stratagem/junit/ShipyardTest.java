/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import edu.cs2335.tsunami.stratagem.kernel.Building;
import edu.cs2335.tsunami.stratagem.kernel.Shipyard;
import junit.framework.TestCase;
import junit.framework.Assert;

/**
 * @author squinn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShipyardTest extends TestCase {

  /** the building */
  private Building shipyard;
  
  /**
   * Constructs this test case
   * @param s some string
   */
  public ShipyardTest(String s) {
    super(s);
  }
  
  /**
   * Set up the test case
   */
  public void setUp() {
    shipyard = new Shipyard();
  }
  
  /**
   * Execute test case
   *
   */
  public void testCost() {
    shipyard.setCost(100);
    shipyard.setCost(200);
    Assert.assertTrue(shipyard.getCost() == 200);
    shipyard.setCost(300);
    Assert.assertTrue(shipyard.getCost() == 300);
    Assert.assertTrue(Shipyard.COSTMONEY == 300);
  }
}
