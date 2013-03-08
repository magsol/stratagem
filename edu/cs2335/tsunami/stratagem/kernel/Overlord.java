package edu.cs2335.tsunami.stratagem.kernel;

/**
 * Battleship division of the navy
 *
 * @author Shannon Quinn
 * @version 1.0
 */
public class Overlord extends Navy {

    /** something for Serializable */
    public static final long serialVersionUID = 10;
    
    /** default cost in money */
    public static final int COSTMONEY = 150;
    
    /** default cost in steel */
    public static final int COSTSTEEL = 100;
    
    /** default hit points */
    public static final int HITPOINTS = 100;
    
    /** default armor class */
    public static final int ARMORCLASS = 5;
    
    /** default damage dealt */
    public static final int DAMAGE = 40;
    
    /** default speed for this unit */
    public static final int SPEED = 6;
    
    /** default upkeep in food/oil */
    public static final int UPKEEP = 6;
    
    /**
     * Constructor for the Overlord division of the navy
     * @param buildPlanet the planet which this is built from
     * @param id the Player who owns this outfit
     */
    public Overlord(Planet buildPlanet, int id) {
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
     * @return bool
     */
    public boolean merge(Military m) {
      Overlord temp;
      if (m instanceof Overlord 
         && m.getCurrentPlanet().equals(this.getCurrentPlanet())
         && m.getOwner().getColor().equals(this.getOwner().getColor())) {
        temp = (Overlord) m;
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
