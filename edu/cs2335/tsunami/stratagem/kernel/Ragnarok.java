package edu.cs2335.tsunami.stratagem.kernel;

/**
* Bombardment section of Army.
*/
public class Ragnarok extends Army {
    
  /** something for Serializable */
  public static final long serialVersionUID = 16;
  
  /** monetary cost of this outfit */
  public static final int COSTMONEY = 100;

  /** cost of this outfit */
  public static final int COSTSTEEL = 25;

  /** damage inflicted by this outfit */
  public static final int DAMAGE = 2;

  /** regular upkeep needed for this outfit */
  public static final int UPKEEP = 1;
 
  /** armor class for this outfit */
  public static final int ARMORCLASS = 1;

  /** hit points for this outfit */
  public static final int HITPOINTS = 10; 

  /** movement speed of this outfit */
  public static final int SPEED = 2;
  
  /**
   * Constructor for the Ragnarok class
   * @param buildPlanet planet built on
   * @param id player owner
   */
  public Ragnarok(Planet buildPlanet, int id) {
    
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
    setCanRaid(false);
    setCanBombard(true);
    setID(id);

    // finally, subtract monetary amounts from player's stocks
    getOwner().setMoney(getOwner().getMoney() - COSTMONEY);
    getOwner().setSteel(getOwner().getSteel() - COSTSTEEL);
  }
  
  /**
   * Merges this and the parameter into a single outfit
   * @param m the outfit to be merged with this one
   * PARAMETER MUST BE SET TO NULL AFTER METHOD CALL
   * BY CALLER
   * @return bool
   */
  public boolean merge(Military m) {
    Ragnarok temp;
    if (m instanceof Ragnarok 
       && m.getCurrentPlanet().equals(this.getCurrentPlanet())
       && m.getOwner().getColor().equals(this.getOwner().getColor())) {
      temp = (Ragnarok) m;
      this.setSize(this.getSize() + temp.getSize());
      this.setOilSupply(this.getOilSupply() + temp.getOilSupply());
      this.setFoodSupply(this.getFoodSupply() + temp.getFoodSupply());
        
      if (temp.getMorale() < this.getMorale()) {
          this.setMorale(temp.getMorale());
      }
        
      if (temp.getExperience() < this.getExperience()) {
        this.setExperience(temp.getExperience());
      }
      return true;
    }
    return false;
  }
}
