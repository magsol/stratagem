/*
 * Created on Nov 15, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.kernel;

import java.util.Random;

/**
 * @author squinn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Battle {

  /** debugging boolean */
  public static final boolean DEBUG = false;

  /** value at which an upset can occur */
  public static final int UPSET_VAL = 1;

  /** player one */
  private Military attacker;
  
  /** player two */
  private Military defender;
  
  /** random number generator */
  private Random random;

  /** damage dealt by player 1 */
  private int playerOneDamage;
  
  /** damage dealt by player 2 */
  private int playerTwoDamage;
  
  /** the losing military outfit */
  private Military loserUnit;
  
  /**
   * Constructor for the Battle class
   * @param attack the attacking military outfit
   * @param defend the defending military outfit
   */
  public Battle(Military attack, Military defend) {
    attacker = attack;
    defender = defend;
    random = new Random();
  }
  
  /**
   * Determines the outcome of the battle
   * LOSER ALWAYS DEALS LESS DAMAGE THAN WINNER
   * @return the player who wins
   */
  public Military battle() {
    // quick and painless
    if (attacker.getSize() <= 0) {
      if (DEBUG) {
        System.out.println("Battle aborted - attacker destroyed");
      }
      loserUnit = attacker;
      return (defender);
    } else if (defender.getSize() <= 0) {
      if (DEBUG) {
        System.out.println("Battle aborted - defender destroyed");
      }
      loserUnit = defender;
      return (attacker);
    }
    int fortification = 0;
 
    // account for fortification levels of planet
    if (defender.getNextPlanet() == null) {
      // have arrived at destination planet
      fortification = defender.getCurrentPlanet().getFortification();
    }
  
    playerOneDamage = 0;
    playerTwoDamage = 0;
    int playerOneHP = attacker.getSize() * attacker.getHitPoints();
    int playerTwoHP = defender.getSize() * defender.getHitPoints();
 
    if (DEBUG) {
      System.out.println("P1 Hit Points: " + playerOneHP);
      System.out.println("P2 Hit Points: " + playerTwoHP);
    }
    // loop until damage exceeds one of the player's total hit points
    while (playerOneDamage < playerTwoHP 
           && playerTwoDamage < playerOneHP) {
  
      // DAMAGE - heavy impact
      playerOneDamage += attacker.getDamage() * attacker.getSize();
      playerTwoDamage += defender.getDamage() * defender.getSize();

      // SPEED ADJUSTMENT - small impact
      playerOneDamage = decrement(playerOneDamage, 
        random.nextInt((int) (defender.getSpeed() * random.nextFloat()) + 1));
      playerTwoDamage = decrement(playerTwoDamage, 
      random.nextInt((int) (attacker.getSpeed() * random.nextFloat()) + 1));

      // ARMOR CLASS ADJUSTMENT - moderate impact
      playerOneDamage = decrement(playerOneDamage, 
        random.nextInt(defender.getArmorClass()));
      playerTwoDamage = decrement(playerTwoDamage,
        random.nextInt(defender.getArmorClass()));

      // MORALE ADJUSTMENT - moderate impact
      playerOneDamage = decrement(playerOneDamage,
        random.nextInt(defender.getMorale() + 1));
      playerTwoDamage = decrement(playerTwoDamage,
        random.nextInt(attacker.getMorale() + 1));

      // EXPERIENCE ADJUSTMENT - moderate impact
      playerOneDamage = decrement(playerOneDamage,
        random.nextInt(defender.getExperience() + 1));
      playerTwoDamage = decrement(playerTwoDamage,
        random.nextInt(attacker.getExperience() + 1));

      // FORTIFICATION ADJUSTMENT - moderate impact
      playerOneDamage = decrement(playerOneDamage, fortification);

      if (DEBUG) {
        System.out.println("Damage dealt by P1: " + playerOneDamage);
        System.out.println("Damage dealt by P2: " + playerTwoDamage);
      }
    }
    
    //int upsetVal = random.nextInt();
  
    // damage levels exceeded in same round!
    if (playerOneDamage >= playerTwoHP 
        && playerTwoDamage >= playerOneHP) {
      float determinant = random.nextFloat();
      if (determinant > 0.5) {
        playerOneDamage = playerTwoHP - 1;
        if (DEBUG) {
          System.out.println("Attacker damage reduced to " + playerOneDamage);
        }
      } else {
        playerTwoDamage = playerOneHP - 1;
        if (DEBUG) {
          System.out.println("Defender damage reduced to " + playerTwoDamage);
        }
      }
    }
  
    Military winner;
    // to win, player will deal more damage than opposing player's
    // HP as well as deal more damage total
    if (playerOneDamage > playerTwoHP) {
      winner = attacker;
      loserUnit = defender;
    } else {
      winner = defender;
      loserUnit = attacker;
    }
  
    adjust(attacker, defender);

    if (DEBUG) {
      System.out.println("The winner is: " 
        + winner.getOwner().getName() + "\n");
    }
    return winner;
  }
  
  /**
   * Adjusts individual statistics for both players
   * @param attacker1 the attacking player - also player 1
   * @param defender2 the defending player - also player 2
   */
  private void adjust(Military attacker1, Military defender2) {    
    int win;                // winner's damage
    int lose;               // loser's damage
    double determinantOne;  // percentage losses for player 1
    double determinantTwo;  // percentage losses for player 2

    // determine total damage dealt and percentages
    if (playerOneDamage > playerTwoDamage) {
      win = playerOneDamage;
      lose = playerTwoDamage;
      determinantOne = (double) win / ((double) win + (double) lose);
      determinantTwo = (double) lose / ((double) win + (double) lose);
    } else {
      win = playerTwoDamage;
      lose = playerOneDamage;
      determinantTwo = (double) win / ((double) win + (double) lose);
      determinantOne = (double) lose / ((double) win + (double) lose);  
    }
  
    // detect a slaughter
    if (!(slaughter(attacker1, defender2, determinantOne, determinantTwo))) {
    // UPSET HAS OCCURRED!!!  KUDOS TO WINNER!!!

      Military winner = (determinantOne > determinantTwo) 
          ? attacker1 : defender2;
      Military loser = (determinantOne > determinantTwo) 
          ? defender2 : attacker1;
      winner.setMorale(99999999);
      loser.setMorale(1);
      winner.setExperience(99999999);
      loser.setExperience(0);
      return;
    }
  
    if (DEBUG) {
      System.out.println("P1 determinant: " + determinantOne);
      System.out.println("P2 determinant: " + determinantTwo);
    }
    // increase experience levels for both, also dependent on percentages
    int oneAmount = ((int) ((double) attacker1.getExperience()
                    * determinantOne) + 1);
    int twoAmount = ((int) ((double) defender2.getExperience()
                    * determinantTwo) + 1);
    attacker1.setExperience(attacker.getExperience() + oneAmount);
    defender2.setExperience(defender.getExperience() + twoAmount);
  
    determinantTwo *= -1;  // make percentage NEGATIVE as to SUBTRACT
    determinantOne *= -1;
  
    // determine percentage values for new outfit sizes
    oneAmount = (int) ((double) attacker1.getSize() * determinantOne);
    twoAmount = (int) ((double) defender2.getSize() * determinantTwo);
    attacker1.setSize(attacker1.getSize() + twoAmount);
    defender2.setSize(defender2.getSize() + oneAmount);
  
    // determine percentage values for new morale levels
    // make ONE of the percentages positive
    determinantOne *= (playerOneDamage < playerTwoDamage) ? 1 : -1;
    determinantTwo *= (playerTwoDamage < playerOneDamage) ? 1 : -1;

    oneAmount = ((int) ((double) attacker1.getMorale() * determinantOne) * 2);
    twoAmount = ((int) ((double) defender2.getMorale() * determinantTwo) * 2);
    attacker1.setMorale(attacker1.getMorale() + oneAmount);
    defender2.setMorale(defender2.getMorale() + twoAmount);
  }
  
  /**
   * Detects a slaughter and wipes out the loser
   * @param attacker1 player 1
   * @param defender2 player 2
   * @param one percentage damage dealt by player 1
   * @param two percentage damage dealt by player 2
   * @return true if there is a slaughter, false if there is an upset
   */
  private boolean slaughter(Military attacker1, Military defender2, 
                            double one, double two) {
  //  if damage dealt is greater than 90% of total
    int upsetVal = random.nextInt();

    if (one * 100 > 90) {
      if (upsetVal == UPSET_VAL) {  // OSNAP!!!!!!!!!11111oneoneone
        upset(attacker1, defender2);
        return false;
      }
      defender2.setSize(0); // destroy this unit
    }

    if (two * 100 > 90) {
      if (upsetVal == UPSET_VAL) {  // OSNAP!!!!!!!!!11111oneoneone
        upset(defender2, attacker1);
        return false;
      }
      attacker1.setSize(0); // destroy this unit
    }
    return true;
  }
  
  /**
   * White box test for slaughter method
   * @param attacker1 player 1
   * @param defender2 player 2
   * @param one percentage of total damage dealt by player 1
   * @param two percentage of total damage dealt by player 2
   * @return true if there is a slaughter, false if there is an upset
   */
  public boolean whiteBoxSlaughter(Military attacker1, Military defender2,
                                   double one, double two) {
    return slaughter(attacker1, defender2, one, two);
  }
  
  /**
   * Adjusts individual statistics for both players (lopsided)
   * @param lost was supposed to win
   * @param won was supposed to lose
   */
  private void upset(Military lost, Military won) {
    if (DEBUG) {
      System.out.println("UPSET!");
    }
    lost.setSize(0);
    won.setExperience(won.getExperience() + 10);
    won.setMorale(won.getMorale() + 10);
  }

  /**
   * private helper method to decrement amounts without dropping below 0
   * @param toDecrement the number to decrement
   * @param amount the amount by which it should be decremented
   * @return the new amount, which cannot go below 0
   */
  private int decrement(int toDecrement, int amount) {
    int toReturn = toDecrement - amount;
    if (toReturn < 0) {
      toReturn = 0;
    }
    return toReturn;
  }
 
  /**
   * Accessor for the loserUnit field
   * @return the loser of this battle
   */
  public Military getLoser() {
      return loserUnit;
  }
  
  /**
   * Main method
   * @param args whatever
   */
  public static void main(String[] args) {

    Planet one = new Planet();
    one.setOwner(new Player("Shannon", java.awt.Color.black));
    Planet two = new Planet();
    two.setOwner(new Player("Person", java.awt.Color.red));
    Military rags = new Frigate(one, 1);
    Military soldier = new Dreadnaught(two, 2);

    System.out.println("--BEFORE--");
    System.out.println("Player 1");
    System.out.println(rags.getSize() + " units");
    System.out.println(rags.getMorale() + " morale");
    System.out.println(rags.getExperience() + " experience");
    System.out.println((rags.getSize() * rags.getHitPoints()) 
           + " total hit points");
    System.out.println((rags.getSize() * rags.getDamage()) 
           + " total base damage\n");
    System.out.println("Player 2");
    System.out.println(soldier.getSize() + " units");
    System.out.println(soldier.getMorale() + " morale");
    System.out.println(soldier.getExperience() + " experience");
    System.out.println((soldier.getSize() * soldier.getHitPoints()) 
           + " total hit points");
    System.out.println((soldier.getSize() * soldier.getDamage()) 
           + " total base damage\n");
/*
    for (int c = 0; c < 10; c++) {
      Battle theBattle = new Battle(rags, soldier);
      Military winner = theBattle.battle();
    }
*/    
    System.out.println("--AFTER--");
    System.out.println("Player 1: "  + rags.getOwner().getName());
    System.out.println(rags.getSize() + " units");
    System.out.println(rags.getMorale() + " morale");
    System.out.println(rags.getExperience() + " experience\n\n");

    System.out.println("Player 2: " + soldier.getOwner().getName());
    System.out.println(soldier.getSize() + " units");
    System.out.println(soldier.getMorale() + " morale");
    System.out.println(soldier.getExperience() + " experience");
  }
}
