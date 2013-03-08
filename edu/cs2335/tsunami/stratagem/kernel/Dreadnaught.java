package edu.cs2335.tsunami.stratagem.kernel;

/**
 * Destroyer division of the Navy
 *
 * @author Shannon Quinn
 * @version 1.0
 */
public class Dreadnaught extends Navy {

    /** something for Serializable */
    public static final long serialVersionUID = 11;

    /** default cost in money */
    public static final int COSTMONEY = 80;
    
    /** default cost in steel */
    public static final int COSTSTEEL = 25;
    
    /** default hit points */
    public static final int HITPOINTS = 70;
    
    /** default armor class */
    public static final int ARMORCLASS = 3;
    
    /** default amount of damage dealt */
    public static final int DAMAGE = 20;
    
    /** default outfit speed */
    public static final int SPEED = 7;
    
    /** default upkeep in food/oil */
    public static final int UPKEEP = 3;    
    
    /**
     * Constructor for the Dreadnaught division of the navy
     * @param buildPlanet the planet where it is being built
     * @param id the Player who owns this outfit
     */
    public Dreadnaught(Planet buildPlanet, int id) {
    
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
      setCanBombard(true);
      setCanRaid(true);
      setID(id);

      // finally, subtract monetary amounts from player's stocks
      getOwner().setMoney(getOwner().getMoney() - COSTMONEY);
      getOwner().setSteel(getOwner().getSteel() - COSTSTEEL);
    }

    /**
     * Merges this and the parameter into a single outfit
     * @param m the outfit to be merged with this one
     * SET PARAMETER TO NULL AFTER METHOD CALL
     * @return bool
     */
    public boolean merge(Military m) {
        Dreadnaught temp;
        if (m instanceof Dreadnaught 
        && m.getCurrentPlanet().equals(this.getCurrentPlanet())
        && m.getOwner().getColor().equals(this.getOwner().getColor())) {
            temp = (Dreadnaught) m;
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
