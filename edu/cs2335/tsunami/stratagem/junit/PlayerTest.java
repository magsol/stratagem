/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import edu.cs2335.tsunami.stratagem.kernel.Player;
import java.awt.Color;
import junit.framework.TestCase;
import junit.framework.Assert;

/**
 * @author squinn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PlayerTest extends TestCase {

  /** player 1 */
  private Player one;
  
  /** player 2 */
  private Player two;

  /**
   * Builds the player test
   * @param s whatev
   */
  public PlayerTest(String s) {
    super(s);
  }
  
  /**
   * builds the test case
   */
  public void setUp() {
    one = new Player("Shannon", Color.black);
    two = new Player("Chris", Color.cyan);
  }
  
  /**
   * Destroy the test case
   */
  public void tearDown() {
  }
  
  /**
   * test player ID
   *
   */
  public void testPlayerID() {
    one.setPlayerID(10);
    two.setPlayerID(5);
    Assert.assertEquals(one.getPlayerID(), two.getPlayerID() + 5);
    two.setPlayerID(10);
    Assert.assertEquals(one.getPlayerID(), two.getPlayerID());
  }
  
  /**
   * Test player name
   *
   */
  public void testName() {
    Assert.assertTrue(one.getName().equals("Shannon"));
    two.setName("Other");
    Assert.assertTrue(two.getName().equals("Other"));
  }
  
  /**
   * Test player color
   *
   */
  public void testColor() {
    one.setColor(Color.white);
    Assert.assertEquals(one.getColor(), Color.white);
    two.setColor(Color.black);
    Assert.assertEquals(two.getColor(), Color.black);
  }
  
  /**
   * Test player food
   *
   */
  public void testFood() {
    one.setFood(50);
    two.setFood(50);
    Assert.assertTrue(one.getFood() == two.getFood());
    two.setFood(60);
    Assert.assertTrue(one.getFood() + 10 == two.getFood());
  }
  
  /**
   * Test player oil
   *
   */
  public void testOil() {
    one.setOil(50);
    two.setOil(50);
    Assert.assertTrue(one.getOil() == two.getOil());
    two.setOil(60);
    Assert.assertTrue(one.getOil() + 10 == two.getOil());
  }
  
  /**
   * Test player money
   *
   */
  public void testMoney() {
    one.setMoney(50);
    two.setMoney(50);
    Assert.assertTrue(one.getMoney() == two.getMoney());
    two.setFood(60);
    Assert.assertTrue(one.getMoney() + 10 == two.getMoney());
  }
  
  /**
   * Test player steel
   *
   */
  public void testSteel() {
    one.setSteel(50);
    two.setSteel(50);
    Assert.assertTrue(one.getSteel() == two.getSteel());
    two.setFood(60);
    Assert.assertTrue(one.getSteel() + 10 == two.getSteel());
  }
}
