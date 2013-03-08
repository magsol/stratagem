package edu.cs2335.tsunami.stratagem.kernel;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;

/**
 * 
 * @author squinn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Whitebox {

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

  /** first frigate outfit */
  private Frigate frigateOne;

  /** second frigate outfit */
  private Frigate frigateTwo;

  /** list of adjacent planets */
  private LinkedList planetList;

  /**
   * Constructor
   *
   */
  public Whitebox() {
    Planet attackHomer = new Planet();
    attackHomer.setLocation(new Point(3, 3));
    attackHomer.setOwner(new Player("huh", Color.black));
    Planet defendHomer = new Planet();
    defendHomer.setLocation(new Point(4, 4));
    defendHomer.setOwner(new Player("wha", Color.white));
    attacker = new Raptor(attackHomer, 2);
    defender = new Ragnarok(defendHomer, 3);
    battle = new Battle(attacker, defender);
    frigateOne = new Frigate(attackHomer, 4);
    frigateTwo = new Frigate(defendHomer, 5);
    planetList = new LinkedList();
  }

  /**
   * Tests merge method on different military types
   * Tim Liu
   */
  public void testMerge() {
    int sizeBefore = frigateOne.getSize();
    frigateOne.merge(attacker); // should not work
    int sizeAfter = frigateOne.getSize();
    if (sizeBefore != sizeAfter) {
      System.out.println("Merge test failed!");
      System.exit(1);
    }
  
    int moraleOne = frigateOne.getMorale();
    int experienceOne = frigateOne.getExperience();
    int foodSupplyOne = frigateOne.getFoodSupply();
    frigateOne.merge(frigateTwo);
    int totalSize = sizeBefore + frigateTwo.getSize();
    if (totalSize != frigateOne.getSize()) {
      System.out.println("Merge test failed!");
      System.exit(1);
    }
  
    int moraleTwo = frigateTwo.getMorale();
    int experienceTwo = frigateTwo.getExperience();
    int foodSupplyTwo = frigateTwo.getFoodSupply();
   
    int lowMorale = (moraleTwo < moraleOne) ? moraleTwo : moraleOne;
    int lowExperience = (experienceTwo < experienceOne) 
        ? experienceTwo : experienceOne;
    int totalSupply = foodSupplyOne + foodSupplyTwo;
  
    if (lowMorale != frigateOne.getMorale() || lowExperience 
              != frigateOne.getExperience() || totalSupply 
              != frigateOne.getFoodSupply()) {
        System.out.println("Merge test failed!");
        System.exit(1);
    }
    System.out.println("Merge tests successful!");
  }

  /**
   * Tests the slaughter / upset detection
   * Shannon Quinn
   */
  public void testSlaughter() {
    double one;
    double two;
    one = 90.0;
    two = 10.0;
    if (!battle.whiteBoxSlaughter(attacker, defender, one, two)) {
      System.out.println("Slaughter test failed!");
      System.exit(1);
    }
    one = 50.0;
    two = 50.0;
    attacker = new Raptor(attackHome, 2);
    defender = new Ragnarok(defendHome, 3);
    if (!battle.whiteBoxSlaughter(attacker, defender, one, two)) {
      System.out.println("Slaughter test failed!");
      System.exit(1);
    }
    one = 100.0;
    two = 100.0;
    attacker = new Raptor(attackHome, 2);
    defender = new Ragnarok(defendHome, 3);
    double count = 1;
    while (count != 0 
          && !battle.whiteBoxSlaughter(
          attacker, defender, one, two)) {
      count++;
    }
    // must eventually end, as there is a random number generator involved...
    if (count == 0) {
      System.out.println("Slaughter test failed!");
      System.exit(1);
    }

    System.out.println("Slaughter tests successful!");
  }

  /**
   * Tests planet adjacency
   * Chris Gray
   */
  public void testAdjacents() {
    Planet one = new Planet();
    one.setName("Earth");
    Planet two = new Planet();
    two.setName("Mars");
    Planet three = new Planet();
    three.setName("Jupiter");
    Planet four = new Planet();
    four.setName("Pluto");
  
    planetList.add(one);
    planetList.add(two);
    planetList.add(three);
    planetList.add(four);
    attackHome.setAdjacents(planetList);

    if (!attackHome.checkPath(one)) {
      System.out.println("Adjacency test failed!");
      System.exit(1);
    }
 
    if (attackHome.checkPath(defendHome)) {
      System.out.println("Adjacency test failed!");
      System.exit(1);
    }
  
    LinkedList temp = new LinkedList();
    temp.add(defendHome);
    if (!defendHome.checkPath(defendHome)) {
      System.out.println("Adjacency test failed!");
      System.exit(1);
    } 
    
    System.out.println("Adjacency tests successful!");
  }

  /**
   * Stupid main
   * @param args whatev
   */
  public static void main(String [] args) {
    Whitebox wbs = new Whitebox();
    wbs.testSlaughter();
  }
}