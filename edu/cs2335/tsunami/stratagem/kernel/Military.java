package edu.cs2335.tsunami.stratagem.kernel;

import java.awt.Point;
import java.io.Serializable;

import edu.cs2335.tsunami.stratagem.net.ClientCommandProcessor;
import edu.cs2335.tsunami.stratagem.net.State;


/**
 * Defines the main methods that will be inherited by all specific subclasses.
 * 
 * @author Shannon Quinn
 * @version 1.1 Last Modified - 10/28/04
 */
public abstract class Military implements Serializable {

    /** DEBUG flag */
    public static final boolean DEBUG = true;

    /** default value for morale */
    public static final int DEF_MORALE = 100;

    /** default value for oil supply */
    public static final int DEF_OILSUPPLY = 0;

    /** default value for experience */
    public static final int DEF_EXPERIENCE = 0;

    /** default value for food supply */
    public static final int DEF_FOODSUPPLY = 0;

    /**
     * Morale of the military outfit scaled: MIN_MO - MAX_MO
     */
    private int morale;

    /** maximum allowed morale */
    private static final int MAX_MO = 100;

    /** minimum allowed morale */
    private static final int MIN_MO = 0;

    /**
     * Experience level of the military outfit scaled: MIN_EX - MAX_EX
     */
    private int experience;

    /** maximum allowed experience */
    private static final int MAX_EX = 100;

    /** minimum allowed experience */
    private static final int MIN_EX = 0;

    /** Movement speed of this outfit */
    private int speed;

    /** Orbital movement speed of this ship */
    private double orbitalSpeed;

    /** maximum allowed speed */
    private static final int MAX_SP = 100;

    /** minimum allowed speed */
    private static final int MIN_SP = 0;

    /**
     * Current food supply levels scaled: MIN_FS - MAX_FS
     */
    private int foodSupply;

    /** maximum allowed food supply */
    private static final int MAX_FS = 50;

    /** minimum allowed food supply */
    private static final int MIN_FS = 0;

    /** Size of outfit */
    private int size;

    /** maximum allowed size */
    private static final int MAX_SI = 0;

    /** minimum allowed size */
    private static final int MIN_SI = 0;

    /**
     * Amount of oil supplies in stock--IGNORED in types for which oil is not
     * used scaled: MIN_OS - MAX_OS
     */
    private int oilSupply;

    /** maximum allowed oil supply */
    private static final int MAX_OS = 50;

    /** minimum allowed oil supply */
    private static final int MIN_OS = 0;

    /** the current planet on which the unit is located */
    private Planet currentPlanet;

    /** the destination of the unit's movement */
    private Planet nextPlanet;

    /** The filename of the image associated with this unit */
    private String imageFile;

    /** absolute location (x, y) on GUI coordinate plane */
    private Point location;

    /** ID for this outfit */
    private int militaryID;

    /** player who owns this outfit */
    private Player owner;

    /** is currently in wormhole */
    private boolean armyInWormHole;

    /** hit points for this outfit */
    private int hitPoints;

    /** damage inflicted by this outfit */
    private int damage;

    /** armor class for this outfit */
    private int armorClass;

    /** armor class for this outfit */
    private int upkeep;

    /** The distance from this unit to the next Planet */
    private double distanceToPlanet;

    /** The angle between this unit and the next planet 
     * from the planet's perspective
     * NOTE: When the unit is in orbit, currentPlanet = nextPlanet
     */
    private double angle;

    /** determines whether this planet is in orbit around a planet */
    private boolean orbiting;

    /** flag for raiding capability */
    private boolean canRaid;
    
    /**flag for raiding*/
    private boolean isRaiding;

    /** flag for bombarding capability */
    private boolean isBombarding;
    
    /** is able to Bombard*/
    private boolean canBombard;

    /**Cycles Stationed*/
    private int cyclesInvading = 0;
    
    /**client command processor*/
    private ClientCommandProcessor stratCommand;
    
    /**State*/
    private State gameState;
    
    /**days to raid*/
    private int daysToRaid = 5;
    
    /**day*/
    private int day;
    
    /**battling*/
    private boolean isBattling;
    


    /**
     * Constructor for military units
     * Sets necessary default values
     * @param current The planet on which this unit will be created.
     */
    public Military(Planet current) {

        // starting values for ALL outfits
        currentPlanet = current;
        nextPlanet = currentPlanet;
        isBattling = false;
        location = new Point(currentPlanet.getLocation());

        morale = 100;
        experience = 0;
        foodSupply = 0;
        oilSupply = 0;
        armyInWormHole = false;
        isRaiding = false;
        isBombarding = false;
      
        orbiting = true;
        angle = Math.PI / 2;
        orbitalSpeed = Math.PI / 180;
        currentPlanet.setFriendlyUnits(true);
        day = 0;

        

    }

    /**
     * TODO If you're looking for this function,
     * I changed its name to moveNext().
     * @return null
     */
    public Point getNextPosition() {
        return null;
    }

    /**
     * Moves the ship to its next location.
     */
    public void moveNext() {
        // Using angle to calculate the new coordinates
        if (orbiting) {
            // The Y is subtracted because of java's coordinate system
            location.setLocation(
                    currentPlanet.getXPosition()
                    + currentPlanet.getOrbitalRadius() * Math.cos(angle),
                    currentPlanet.getYPosition()
                    - currentPlanet.getOrbitalRadius() * Math.sin(angle));

            angle += orbitalSpeed;
            if (day < owner.getDay()) {
                growResources();
            }
            
        } else if (isRaiding && day < owner.getDay() 
                && distanceToPlanet <= 100) {
            if (owner.getPlayerID() 
                    == currentPlanet.getOwner().getPlayerID()) {
                stratCommand.raid(militaryID, nextPlanet.getPlanetID());
            } 
        } else if (distanceToPlanet <= nextPlanet.getOrbitalRadius()) {
            if (nextPlanet.getOwner() == null && !isRaiding) {
                cyclesInvading++;
                    if (cyclesInvading >= nextPlanet.getTimeToSurrender()) {
                        stratCommand.setPlanetOwner(nextPlanet.getPlanetID(), 
                                                owner.getPlayerID());
                    }
            } else if ((nextPlanet.getOwner().getPlayerID() 
                    == owner.getPlayerID())) {
                orbiting = true;
                stratCommand
                .setCurrentPlanet(militaryID, nextPlanet.getPlanetID());
                currentPlanet = nextPlanet;
                currentPlanet.setFriendlyUnits(true);
                cyclesInvading = 0;
            } else if (!nextPlanet.getFriendlyUnits() && !isRaiding) {
                cyclesInvading++;
                    if (cyclesInvading >= nextPlanet.getTimeToSurrender()) {
                        stratCommand.setPlanetOwner(nextPlanet.getPlanetID(), 
                                                owner.getPlayerID());
                    }
            }
            if (day < owner.getDay()) {
                burnResources();
            }
        } else if (distanceToPlanet <= 150 && isBombarding) {
                bombard(nextPlanet);
                isBombarding = false;
                setNextPlanet(currentPlanet);
                if (owner.getPlayerID() 
                        == currentPlanet.getOwner().getPlayerID()) {
                    stratCommand.bombard(militaryID, nextPlanet.getPlanetID());
                }
        } else {
            // Not in orbit and not close enough to orbit
            // Therefore just move towards the next planet

            // The Y is subtracted because of java's coordinate system
            location.setLocation(
                    nextPlanet.getXPosition()
                    + distanceToPlanet * Math.cos(angle),
                    nextPlanet.getYPosition()
                    - distanceToPlanet * Math.sin(angle));
            
            if (day < owner.getDay()) {
                burnResources();
            }
            distanceToPlanet -= speed;
        }
        
        if (day < owner.getDay()) {
            day = owner.getDay();
        }
        
        if ((foodSupply <= 0 || oilSupply <= 0) && (nextPlanet.getPlanetID()
                                    != currentPlanet.getPlanetID())) {
            setNextPlanet(currentPlanet);
        }
    }



    /**
     * Conducts raid on specified planet
     * 
     * @param p planet which will be raided
     */
    public void raid(Planet p) {
        if (!(canRaid)) {
            return;
        } else if (daysToRaid <= 0) {
          daysToRaid = 0;
          isRaiding = false;
          setNextPlanet(currentPlanet);
        } else {
          owner.setMoney(owner.getMoney() + p.giveMoney());
          owner.setOil(owner.getOil() + p.giveOil());
          owner.setSteel(owner.getSteel() + p.giveSteel());
          owner.setFood(owner.getFood() + p.giveFood());
          p.getOwner().setMoney(p.getOwner().getMoney() - p.giveMoney());
          p.getOwner().setOil(p.getOwner().getOil() - p.giveOil());
          p.getOwner().setSteel(p.getOwner().getSteel() - p.giveSteel());
          p.getOwner().setFood(p.getOwner().getFood() - p.giveFood());
          daysToRaid--;
        }
   }
    

    /**
     * Conducts bombardment on specified planet
     * 
     * @param p planet which will be bombarded
     */
    public void bombard(Planet p) {
        if (!(canBombard)) {
            return;
        }
        if (p.getFortification() > 0) {
            p.setFortification(p.getFortification() - 1);
        }
        


    }

    /**
     * Depending on the type of unit, the player's money stocks will decrease
     */
    public void train() {
        double multiplier = ((double) experience / (double) MAX_EX);
        if (multiplier >= 1) {
            return;
        }

            experience += 5;
            owner.setMoney(owner.getMoney() - (int) (multiplier * 100));

    }

    /**
     * Modifier for morale field
     * 
     * @param newMorale the new morale level
     */
    public void setMorale(int newMorale) {
        morale = newMorale;
        if (morale > MAX_MO && MAX_MO != 0) {
            morale = MAX_MO;
        } else if (morale < MIN_MO) {
            morale = MIN_MO;
        }
    }

    /**
     * Accessor for the morale field
     * 
     * @return the morale level
     */
    public int getMorale() {
        return morale;
    }

    /**
     * Modifier for the currentPlanet field.
     * 
     * @param newCurrentPlanet the new current planet where the outfit is
     */
    public void setCurrentPlanet(Planet newCurrentPlanet) {
        currentPlanet = newCurrentPlanet;
    }

    /**
     * Accessor for the currentPlanet field.
     * 
     * @return the current planet where the outfit is
     */
    public Planet getCurrentPlanet() {
        return currentPlanet;
    }

    /**
     * Modifier for the owner field
     * 
     * @param newOwner the owning player
     */
    public void setOwner(Player newOwner) {
        owner = newOwner;
    }

    /**
     * Accessor for the owner field
     * 
     * @return the owning player
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the destination of the ship (must be a planet) Removes a ship from a
     * planet's orbit Calculates the entry angle to the next planet, from the
     * planet's perspective
     * 
     * @param destination the destination of the ship
     */
    public void setNextPlanet(Planet destination) {

        double changeX, changeY;
        orbiting = false;
        nextPlanet = destination;

        distanceToPlanet = location.distance(nextPlanet.getLocation());
        // ****Calculate angle of entry****
        // which is actually the angle from planet to the ship

        // find change in x and y
        // change in Y is switched because of java's coordinate system
        changeX = nextPlanet.getXPosition() - location.getX();
        changeY = location.getY() - nextPlanet.getYPosition();

        // check for division by zero
        if (changeX == 0) {
            // Place ship at correct pole of planet
            if (changeY < 0) {
                angle = Math.PI / -2;
            } else {
                angle = Math.PI / 2;
            }
        } else if (changeX > 0) {
            // Add pi to angle because X is positive
            angle = Math.atan(changeY / changeX) + Math.PI;
        } else {
            angle = Math.atan(changeY / changeX);
        }

    }

    /**
     * Accessor for the nextPlanet field
     * 
     * @return the city which the outfit is en route to
     */
    public Planet getNextPlanet() {
        return nextPlanet;
    }

    /**
     * Modifier for the experience field
     * 
     * @param newExperience the new experience level
     */
    public void setExperience(int newExperience) {
        experience = newExperience;
        if (experience > MAX_EX && MAX_EX != 0) {
            experience = MAX_EX;
        } else if (experience < MIN_EX) {
            experience = MIN_EX;
        }
    }

    /**
     * Accessor for the experience field
     * 
     * @return the experience level
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Modifier for the speed field
     * 
     * @param newSpeed the new movement speed
     */
    public void setSpeed(int newSpeed) {
        speed = newSpeed;
        if (speed > MAX_SP && MAX_SP != 0) {
            speed = MAX_SP;
        } else if (speed < MIN_SP) {
            speed = MIN_SP;
        }
    }

    /**
     * Sets the orbital speed (default is 1 degree, or pi/180)
     * 
     * @param newSpeed the new orbital movement speed
     */
    public void setOrbitalSpeed(double newSpeed) {
        orbitalSpeed = newSpeed;
    }

    /**
     * Accessor for the speed field
     * 
     * @return the movement speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Accessor for the orbital speed (default is 1 degree, or pi/180)
     * 
     * @return the orbital movement speed
     */
    public double getOrbitalSpeed() {
        return orbitalSpeed;
    }

    /**
     * Modifier for the foodSupply field
     * 
     * @param newFoodSupply the new food supply amount
     */
    public void setFoodSupply(int newFoodSupply) {
        foodSupply = newFoodSupply;
        if (foodSupply > MAX_FS && MAX_FS != 0) {
            foodSupply = MAX_FS;
        } else if (foodSupply < MIN_FS) {
            foodSupply = MIN_FS;
        }
    }

    /**
     * Accessor for the foodSupply field
     * 
     * @return the food supply amount
     */
    public int getFoodSupply() {
        return foodSupply;
    }

    /**
     * Modifier for the size field
     * 
     * @param newSize the new size of the outfit
     */
    public void setSize(int newSize) {
        size = newSize;
        if (size > MAX_SI && MAX_SI != 0) {
            size = MAX_SI;
        } else if (size < MIN_SI) {
            size = MIN_SI;
        }
    }

    /**
     * Accessor for the size field
     * 
     * @return the size of the outfit
     */
    public int getSize() {
        return size;
    }

    /**
     * Modifier for the oilSupply field
     * 
     * @param newOilSupply the new amount of oil stocked
     */
    public void setOilSupply(int newOilSupply) {
        oilSupply = newOilSupply;
        if (oilSupply > MAX_OS && MAX_OS != 0) {
            oilSupply = MAX_OS;
        } else if (oilSupply < MIN_OS) {
            oilSupply = MIN_OS;
        }
    }

    /**
     * Accessor for the oilSupply field
     * 
     * @return the amount of oil stocked
     */
    public int getOilSupply() {
        return oilSupply;
    }

    /**
     * Modifier for the imageFile field
     * @param newImageFile the new image for this outfit
     */
    public void setImageFile(String newImageFile) {
        imageFile = newImageFile;
    }

    /**
     * Accessor for the myImage field
     * 
     * @return the buffered image representing this outfit
     */
    public String getImageFile() {
        return imageFile;
    }

    /**
     * Modifier for the location field
     * 
     * @param newLocation the new absolute location of this Planet
     */
    public void setLocation(Point newLocation) {
        location.setLocation(newLocation);
    }

    /**
     * Accessor for the location field
     * 
     * @return the absolute location of this Planet
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Accessor for the location field
     * 
     * @return the absolute X location of this Planet
     */
    public int getXPosition() {
        return (int) location.getX();
    }

    /**
     * Accessor for the location field
     * 
     * @return the absolute Y location of this Planet
     */
    public int getYPosition() {
        return (int) location.getY();
    }

    /**
     * Accessor for the militaryID field
     * 
     * @return the unique ID of this outfit
     */
    public int getMilitaryID() {
        return this.militaryID;
    }

    /**
     * Modifier for the canRaid field
     * 
     * @param newCanRaid whether or not this outfit can raid
     */
    public void setCanRaid(boolean newCanRaid) {
        canRaid = newCanRaid;
    }

    /**
     * Accessor for the canRaid field
     * 
     * @return whether or not this outfit can raid
     */
    public boolean canRaid() {
        return canRaid;
    }

    /**
     * Modifier for the canBombard field
     * 
     * @param newCanBombard whether or not this outfit can bombard
     */
    public void setCanBombard(boolean newCanBombard) {
        canBombard = newCanBombard;
    }

    /**
     * Accessor for the canBombard field
     * 
     * @return whether or not this outfit can bombard
     */
    public boolean canBombard() {
        return canBombard;
    }

    /**
     * Modifier for the militaryID field
     * 
     * @param newID the new ID for this outfit
     */

    public void setMilitaryID(int newID) {
        this.militaryID = newID;
    }

    /**
     * Modifier for the armyInWormHole field
     * 
     * @param b whether or not the outfit travels by wormhole
     */
    public void setArmyInWormHole(boolean b) {
        armyInWormHole = b;
    }

    /**
     * Accessor for the armyInWormHole field
     * 
     * @return whether or not the outfit travels by wormhole
     */
    public boolean getArmyInWormHole() {
        return armyInWormHole;
    }

    /**
     * Modifier for the upkeep field
     * 
     * @param newUpkeep the new upkeep for this outfit
     */
    public void setUpkeep(int newUpkeep) {
        upkeep = newUpkeep;
    }

    /**
     * Accessor for the upkeep field
     * 
     * @return the upkeep for this outfit
     */
    public int getUpkeep() {
        return upkeep;
    }

    /**
     * Modifier for the damage field
     * 
     * @param newDamage the new damage for this outfit
     */
    public void setDamage(int newDamage) {
        damage = newDamage;
    }

    /**
     * Accessor for the damage field
     * 
     * @return the damage for this outfit
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Modifier for the hitPoints field
     * 
     * @param newHitPoints the new hit points for this field
     */
    public void setHitPoints(int newHitPoints) {
        hitPoints = newHitPoints;
    }

    /**
     * Accessor for the hitPoints field
     * 
     * @return the hit points for this outfit
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Modifier for the armorClass field
     * 
     * @param newArmorClass the new armor class for this outfit
     */
    public void setArmorClass(int newArmorClass) {
      armorClass = newArmorClass;
    }

    /**
     * Accessor for the armorClass field
     * 
     * @return the armor class for this outfit
     */
    public int getArmorClass() {
      return armorClass;
    }

    /**
     * Modifier for the stratCommand field
     * @param c the new client command processor
     */    
    public void setCommand(ClientCommandProcessor c) {
      stratCommand = c;
    }
    

    /**
     * Accessor for the orbiting field
     * @return whether or not this outfit is orbiting
     */
    public boolean getOrbiting() {
      return orbiting;
    }
    
    /**
     * Modifier for the orbiting field
     * @param b the new status of oribiting
     */
    public void setOrbiting(boolean b) {
      orbiting = b;
    }
    
    /**
     * Modifier for the nextPlanet field
     * @param p the next planet
     */
    public void setDestPlanet(Planet p) {
      nextPlanet = p;
    }
    
    /**
     * Modifier for the ID field
     * @param id the new ID for this outfit
     */
    public void setID(int id) {
      militaryID = id;
    }
    
    /**
     * Modifier for the isRaiding field
     * @param r the new raiding status of the outfit
     */
    public void setIsRaiding(boolean r) {
      isRaiding = r;
    }
    
    /**
     * Accessor for the isRaiding field
     * @return if this outfit is raiding
     */
    public boolean getIsRaiding() {
      return isRaiding;
    }
    
    /**
     * Modifier for the isBombarding field
     * @param b the new status of bombardment
     */
    public void setIsBombarding(boolean b) {
      isBombarding = b;
    }
    
    /**
     * Accessor for the isBombarding field
     * @return if this outfit is bombarding
     */
    public boolean getIsBombarding() {
      return isBombarding;
    }
    
    /**
     * Modifier for the daysToRaid field
     * @param daysRaiding the new amount of raid time
     */
    public void setDaysToRaid(int daysRaiding) {
      daysToRaid = daysRaiding;
    }
    
    /**
     * Accessor for the daysToRaid field
     * @return the days which this outfit will raid
     */
    public int getDaysToRaid() {
      return daysToRaid;
    }
    
    /**
     * Modifier for the state field
     * @param s the new state for this outfit
     */
    public void setState(State s) {
      gameState = s;
    }
    
    /**
     * Accessor for the State field
     * @return the state for this outfit
     */
    public State getState() {
      return gameState;
    }
    
    /**
     * Burns resources
     *
     */
    public void burnResources() {
        if (oilSupply < 0 || (oilSupply - upkeep) < 0) {
          oilSupply = 0;
        } else {
          oilSupply = oilSupply - upkeep;
        }
        if (foodSupply < 0 || (foodSupply - upkeep) < 0) {
          foodSupply = 0;
        } else {
          foodSupply = foodSupply - upkeep;
        }
    }
    
    /**
     * Grows resources
     *
     */
    public void growResources() {
        if (oilSupply < MAX_OS && foodSupply < MAX_FS) {
          oilSupply = oilSupply + 3;
          owner.setOil(owner.getOil() - 3);
          foodSupply = foodSupply + 3;
          owner.setFood(owner.getFood() - 3);
        } else if (oilSupply > MAX_OS && foodSupply > MAX_FS) {
          oilSupply = MAX_OS;
          foodSupply = MAX_FS;
        }
    }
    
    /**
     * Overloaded toString method
     * 
     * @return this object, as a String
     */
    public String toString() {
        return this.imageFile + " " + this.location.getX() + " "
                + this.location.getY();
    }
    
    /**
     * is battling
     * @param b battling
     * @author gtg835p
     */
    public void setIsBattling(boolean b) {
       isBattling = b;
    }
    
    /**
     * is battling
     * @return is battling
     */
    public boolean getIsBattling() {
       return isBattling;
    }
    
} // end Military class