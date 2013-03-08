package edu.cs2335.tsunami.stratagem.kernel;

/**
 * Stealth/scout division of the Navy
 *
 * @author Shannon Quinn
 * @version 1.0
 */
public class Shade extends Navy {

  /** something for Serializable */
  public static final long serialVersionUID = 12;
  
  /** default cost in money */
  public static final int COSTMONEY = 50;
  
  /** default cost in steel */
  public static final int COSTSTEEL = 25;
  
  /** default hitpoints */
  public static final int HITPOINTS = 25;
  
  /** default armor class */
  public static final int ARMORCLASS = 1;
  
  /** default damage dealt */
  public static final int DAMAGE = 10;
  
  /** default speed */
  public static final int SPEED = 9;
  
  /** default upkeep in terms of food/oil */
  public static final int UPKEEP = 1;

  /**
   * Constructor for the Shade division of the Navy 
   * @param buildPlanet the planet from which this is built
   * @param id the Player who owns this outfit
   */
  public Shade(Planet buildPlanet, int id) {
      
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
    * Merges the outfits together
    * @param m the outfit to be merged
    * CALLER MUST SET PARAMETER OUTFIT TO NULL
    * @return bool
    */
    public boolean merge(Military m) {
      Shade temp;
      if (m instanceof Shade 
       && m.getCurrentPlanet().equals(this.getCurrentPlanet())
       && m.getOwner().getColor().equals(this.getOwner().getColor())) {
        temp = (Shade) m;
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
