package edu.cs2335.tsunami.stratagem.kernel;

/**
 * Armored division class
 *
 * @author Shannon Quinn
 * @version 1.1
 */
public class Frigate extends Army {

  /** some stupid piece of crap for Serializable */
  public static final long serialVersionUID = 17;

  /** cost of this outfit */
  public static final int COSTMONEY = 125;
  
  /** cost of this outfit */
  public static final int COSTSTEEL = 100;
  
  /** damage inflicted by this outfit */
  public static final int DAMAGE = 30;
  
  /** regular upkeep needed for this outfit */
  public static final int UPKEEP = 3;
  
  /** armor class for this outfit */
  public static final int ARMORCLASS = 4;
  
  /** hit points for this outfit */
  public static final int HITPOINTS = 50; 
  
  /** movement speed of this outfit */
  public static final int SPEED = 4;


  /**
   * Builds a frigate outfit
   * @param buildPlanet the planet it was built on
   * @param id the Player who owns this outfit
   */
  public Frigate(Planet buildPlanet, int id) {
    // first, call super constructor
    super(buildPlanet);

    // next, set all attributes
    setOwner(buildPlanet.getOwner());
    setMorale(Military.DEF_MORALE);
    setFoodSupply(Military.DEF_FOODSUPPLY);
    setSize(50);
    setExperience(Military.DEF_EXPERIENCE);
    setOilSupply(Military.DEF_OILSUPPLY);
    setDamage(DAMAGE);
    setUpkeep(UPKEEP);
    setArmorClass(ARMORCLASS);
    setHitPoints(HITPOINTS);
    setSpeed(SPEED);
    setCanRaid(true);
    setCanBombard(false);
    setID(id);

    // finally, subtract monetary amounts from player's stocks
    getOwner().setMoney(getOwner().getMoney() - COSTMONEY);
    getOwner().setSteel(getOwner().getSteel() - COSTSTEEL);
  }

/**
 * Merges the two frigate outfits together
 * @param force the military force to merge
 * @return bool
 */
  public boolean merge(Military force) {
    if (!(force instanceof Frigate)) {
      return false;
    } else if (force.getCurrentPlanet().equals(this.getCurrentPlanet()) 
      && force.getOwner().getColor().equals(this.getOwner().getColor())) {
    
      // take lower experience
      if (getExperience() > force.getExperience()) {
        setExperience(force.getExperience());
      }

      // take lower morale
      if (getMorale() > force.getMorale()) {
        setMorale(force.getMorale());
      }

      // combine supplies and size
      this.setFoodSupply(this.getFoodSupply() + force.getFoodSupply());
      this.setSize(this.getSize() + force.getSize());  
      this.setOilSupply(this.getOilSupply() + force.getOilSupply());
      return true;
    }
    return false;
  }
}
