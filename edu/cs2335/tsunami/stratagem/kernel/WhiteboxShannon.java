package edu.cs2335.tsunami.stratagem.kernel;

import java.awt.Color;
import java.awt.Point;

/**
 * 
 * @author squinn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WhiteboxShannon {

  /** instance to be tested */
  private Battle battle;

  /** attacker - player 1 */
  private Military attacker;

  /** defender - player 2 */
  private Military defender;

  /** attacker's planet */
  private Planet attackHome;

  /** defender's planet */
  private Planet defendHome;
  
  /**
   * Constructor
   *
   */
  public WhiteboxShannon() {
    Planet attackHomer = new Planet();
    attackHomer.setLocation(new Point(3, 3));
    attackHomer.setOwner(new Player("huh", Color.black));
    Planet defendHomer = new Planet();
    defendHomer.setLocation(new Point(4, 4));
    defendHomer.setOwner(new Player("wha", Color.white));
    attacker = new Raptor(attackHomer, 2);
    defender = new Ragnarok(defendHomer, 3);
    battle = new Battle(attacker, defender);
  }

  /**
   * Tests the slaughter / upset detection
   *
   */
  public void testSlaughter() {
    double one;
    double two;
    one = 90.0;
    two = 10.0;
    if (!battle.whiteBoxSlaughter(attacker, defender, one, two)) {
      System.out.println("Test failed!");
      System.exit(1);
    }
    one = 50.0;
    two = 50.0;
    attacker = new Raptor(attackHome, 2);
    defender = new Ragnarok(defendHome, 3);
    if (!battle.whiteBoxSlaughter(attacker, defender, one, two)) {
      System.out.println("Test failed!");
      System.exit(1);
    }
    one = 100.0;
    two = 100.0;
    attacker = new Raptor(attackHome, 2);
    defender = new Ragnarok(defendHome, 3);
    double count = 1;
    while (!battle.whiteBoxSlaughter(
            attacker, defender, one, two)
            && count != 0) {
      count++;
    }
      // must eventually end, as there is 
      //a random number generator involved...
      if (count == 0) {
        System.out.println("Test failed!");
        System.exit(1);
      }
      System.out.println("Tests successful!");
    }

  /**
   * Main method
   * @param args somethin'
   */
  public static void main(String [] args) {
    WhiteboxShannon wbs = new WhiteboxShannon();
    wbs.testSlaughter();
  }
}