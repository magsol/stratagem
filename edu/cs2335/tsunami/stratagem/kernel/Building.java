package edu.cs2335.tsunami.stratagem.kernel;

import java.io.Serializable;

/**
 * Building class
 *
 * @author Shannon Quinn
 * @version 1.0
 */
public abstract class Building implements Serializable {

  /** amount of money needed to build */
  private int costMoney;
  
  /** name */
  private String nameOfBuilding;

  /**
   * Modifier for the costMoney field
   * @param newCost the new cost for this building
   */
  public void setCost(int newCost) {
    costMoney = newCost;
  }
  
  /**
   * setting name of building
   * @param name building
   */
  public void setName(String name) {
      nameOfBuilding = name;
  }
  
  /**
   * Accessor for the name field
   * @return the name of this building
   */
  public String getName() {
      return nameOfBuilding;
  }

  /**
   * Accessor for the costMoney field
   * @return the cost for this building
   */
  public int getCost() {
    return costMoney;
  }
}
