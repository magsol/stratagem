package edu.cs2335.tsunami.stratagem.kernel;

/**
 * Navy class.
 *
 * @author Shannon Quinn
 * @version 1.0
 */
public abstract class Navy extends Military {

   /**
    * Constructor
    * @param buildPlanet planet on which this outfit is built
    */
    public Navy(Planet buildPlanet) {
      super(buildPlanet);
    }
  /**
   * Moves the ships by water to another Planet - default movement
   * @param dest the Planet to which the navy is moving
   */
  public void moveWormHole(Planet dest) {
    if (dest.getIsWormHoleAccessible() 
     && this.getCurrentPlanet().getIsWormHoleAccessible()) {
        this.setNextPlanet(dest);
    }
  }

  /** Accessor for supply
   * 
   * @return Oil supply
   */
  public int getSupply() {
      return getOilSupply();
  }
}
