package edu.cs2335.tsunami.stratagem.kernel;

/**
 * Shipyard class, allows for production of Naval ships
 *
 * @author Shannon Quinn
 * @version 1.0
 */
public class Shipyard extends Building {

  /** cost of this building */
  public static final int COSTMONEY = 300;

  /**
   * Constructor
   *
   */
  public Shipyard() {
      this.setName("SHIPYARD");
      setCost(COSTMONEY);
  }
  
}
