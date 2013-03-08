package edu.cs2335.tsunami.stratagem.kernel;

import java.awt.Color;
import java.io.Serializable;

/**
 * Player class will track everything related to the individual players in the
 * game.
 * 
 * @author Shannon Quinn
 * @version 1.1
 */
public class Player implements Serializable {

  /** something or other */
  public static final long serialVersionUID = 19;
  
  /** Food in the player's stockpile */
  private int food;

  /** Oil in the player's stockpile */
  private int oil;

  /** Steel in the player's stockpile */
  private int steel;

  /** Money in the player's stockpile */
  private int money;

  /** Name of the player */
  private String name;

  /** Name of side */
  private String side;

  /** Team's color */
  private Color color;

  /** Unique identification number for Player */
  private int playerID;

  /** Day */
  private int day;

  /**ready boolean */
  private boolean ready = false;


  /**
   * Constructor for a player
   * @param playerName name of the player
   * @param c color of the player
   */
  public Player(String playerName, Color c) {
      name = playerName;
      side = "Robbers";
      this.color = c;
      money = 100;
      steel = 75;
      oil = 75;
      food = 75;
    }

   /**
     * Modifier for the ID field
     * 
     * @param newID the Player's new ID number 
     * ---WARNING--- New ID number MAY
     * NOT BE UNIQUE
     */
    public void setPlayerID(int newID) {
        playerID = newID;
    }

    /**
     * Accessor for the ID field
     * 
     * @return the unique ID number for the Player
     */
    public int getPlayerID() {
        return playerID;
    }

    
    /**
     * Modifier for the name field
     * @param newName the new name of the Player
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Accessor for the name field
     * @return the name of the Player
     */
    public String getName() {
        return name;
    }

    /**
     * Modifier for the player's team color
     * @param newColor the new color for the Player
     */
    public void setColor(Color newColor) {
        color = newColor;
    }

    /**
     * Accessor for the color field
     * @return the color of the Player
     */
    public Color getColor() {
        return color;
    }

    /**
     * Modifier for the food field.
     * @param newFood the new food levels
     */
    public void setFood(int newFood) {
        food = newFood;
    }

    /**
     * Accessor for the food field.
     * @return the current amount of food in the player's stockpile
     */
    public int getFood() {
        return food;
    }

    /**
     * Modifier for the oil field.
     * @param newOil the new amount of oil in the stockpile.
     */
    public void setOil(int newOil) {
        oil = newOil;
    }

    /**
     * Accessor for the oil field.
     * @return amount of oil in the stockpile
     */
    public int getOil() {
        return oil;
    }

    /**
     * Modifier for the steel field.
     * @param newSteel the new amount of steel in the stockpile
     */
    public void setSteel(int newSteel) {
        steel = newSteel;
    }

    /**
     * Accessor for the steel field.
     * @return the amount of steel in the stockpile
     */
    public int getSteel() {
        return steel;
    }

    /**
     * Modifier for the money field.
     * @param newMoney the new amount of money in the stockpile
     */
    public void setMoney(int newMoney) {
        money = newMoney;
    }

    /**
     * Accessor for the money field
     * @return the amount of money in the player's stockpile
     */
    public int getMoney() {
        return money;
    }

    /**
     * Modifier for the side field
     * @param s the new side
     */
    public void setSide(String s) {
      side = s;
    }

    /**
     * Accessor for side field
     * @return Side string
     */
    public String getSide() {
        return side;
    }
    
    /**
     * Modifier for the day field
     * @param d the new day
     */
    public void setDay(int d) {
      day = d;
    }
   
    /**
     * Accessor for the day field
     * @return the current day
     */
    public int getDay() {
      return day;
    }
    
    /**
     * Modifier for the ready field
     * @param b the new ready state
     */
    public void setReady(boolean b) {
      ready = b;
    }
    
    /**
     * Accessor for the ready field
     * @return the ready state
     */
    public boolean getReady() {
      return ready;
    }

}