/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import edu.cs2335.tsunami.stratagem.kernel.Building;
import edu.cs2335.tsunami.stratagem.kernel.WarFactory;
import junit.framework.TestCase;
import junit.framework.Assert;

/**
 * @author squinn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WarFactoryTest extends TestCase {

  /** the war factory */
  private Building warFactory;
  
  /**
   * Tests the constrctor
   * @param s some string
   */
  public WarFactoryTest(String s) {
    super(s);
  }

  /**
   * Set up
   */
  public void setUp() {
    warFactory = new WarFactory();
  }
  
  /**
   * Tests this object
   *
   */
  public void testCost() {
    warFactory.setCost(23);
    warFactory.setCost(34);
    Assert.assertTrue(warFactory.getCost() == 34);
    warFactory.setCost(450);
    Assert.assertTrue(warFactory.getCost() == 450);
    Assert.assertTrue(WarFactory.COSTMONEY == 300);
  }
}
