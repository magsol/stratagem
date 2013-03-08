/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import edu.cs2335.tsunami.stratagem.kernel.Building;
import edu.cs2335.tsunami.stratagem.kernel.TrainingAcademy;
import junit.framework.TestCase;
import junit.framework.Assert;

/**
 * @author squinn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TrainingAcademyTest extends TestCase {

  /** the academy */
  private Building academy;
  
  /**
   * Constructor
   * @param s some sort of string
   */
  public TrainingAcademyTest(String s) {
    super(s);
  }
  
  /**
   * Set up this test case
   */
  public void setUp() {
    academy = new TrainingAcademy();
  }

  /**
   * Tests this object
   *
   */
  public void testCost() {
    academy.setCost(50);
    academy.setCost(100);
    Assert.assertTrue(academy.getCost() == 100);
    academy.setCost(200);
    Assert.assertTrue(academy.getCost() == 200);
    Assert.assertTrue(TrainingAcademy.COSTMONEY == 200);
  }
}
