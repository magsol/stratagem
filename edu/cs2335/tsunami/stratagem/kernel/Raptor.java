package edu.cs2335.tsunami.stratagem.kernel;

/**
* Fighter extends Army
*
* @author Shannon Quinn
* @version 1.1
*/
public class Raptor extends Army {

  /** something for Serializable */
  public static final long serialVersionUID = 18;

  /** cost of this outfit */
  public static final int COSTMONEY = 25;

  /** cost of this outfit */
  public static final int COSTSTEEL = 0;
  
  /** damage inflicted by this outfit */
  public static final int DAMAGE = 15;
  
  /** regular upkeep needed for this outfit */
  public static final int UPKEEP = 1;
  
  /** armor class for this outfit */
  public static final int ARMORCLASS = 1;
  
  /** hit points for this outfit */
  public static final int HITPOINTS = 10; 
  
  /** movement speed of this outfit */
  public static final int SPEED = 15;
 
 
  /**
   * Builds a raptor outfit
   * @param buildPlanet the planet it was built on
   * @param id the Player who owns this outfit
   */
  public Raptor(Planet buildPlanet, int id) {
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
    setCanBombard(false);
    setID(id);
  
    // finally, subtract monetary amounts from player's stocks
    getOwner().setMoney(getOwner().getMoney() - COSTMONEY);
    getOwner().setSteel(getOwner().getSteel() - COSTSTEEL);
  }

  /**
   * Merges this and the passed in outfit together
   * @param m the outfit to be merged with this one
   * MUST SET PARAMETER TO NULL AFTER METHOD CALL
   * @return bool
   */
  public boolean merge(Military m) {
    Raptor temp;
    if (m instanceof Raptor 
     && m.getCurrentPlanet().equals(this.getCurrentPlanet())
     && m.getOwner().getColor().equals(this.getOwner().getColor())) {
      temp = (Raptor) m;
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

 /**
   * Main
   * @param args is the main input
   */
  public static void main(String [] args) {
//    Fighter one = new Fighter();
//    one.setMorale(5);
//    System.out.println(one.getMorale());
  }
}
