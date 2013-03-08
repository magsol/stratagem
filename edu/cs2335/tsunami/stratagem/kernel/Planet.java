package edu.cs2335.tsunami.stratagem.kernel;

import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

import edu.cs2335.tsunami.stratagem.net.ClientCommandProcessor;

/**
 * Represents the Planets
 * 
 * @author Shannon Quinn
 * @version 1.1
 */
public class Planet implements Serializable {

    /** something for Serializable */
    public static final long serialVersionUID = 20;

    /** name of the Planet */
    private String name;

    /** population of Planet */
    private int population;

    /** maximum value of population */
    private static final int MAX_PP = 9999999;

    /** minimum value of population */
    private static final int MIN_PP = 0;

    /**
     * Player who owns the planet NULL if wavering or neutral
     */
    private Player owner;

    /**
     * indicated on a scale: 0 = Neutral 1-99 = Wavering loyalty 100 = Complete
     * player ownership
     */
    private int allegiance = 0;

    /** maximum value of allegiance */
    private static final int MAX_AL = 100;

    /** minimum value of allegiance */
    private static final int MIN_AL = 0;

    /** indicates the morale of the Planet */
    private int morale = 0;

    /** maximum value of morale */
    private static final int MAX_MO = 100;

    /** minimum value of morale */
    private static final int MIN_MO = 0;

    /** fortification level */
    private int fortification;

    /** maximum value of fortification */
    private static final int MAX_FL = 3;

    /** minimum value of fortification */
    private static final int MIN_FL = 0;

    ///** array indicating adjacent planets */
    //private Planet [] adjacents;
    /** LinkedList of adjacent planets */
    private LinkedList adjacents;

    /** absolute location (x, y) on GUI coordinate plane */
    private Point location;

    /**friendly units*/
    private boolean hasFriendlyUnits;

    /** flag indicating if this is the Capitol planet */
    private boolean isCapitol = false;

    /** flag indicating if this is a coastal city */
    private boolean isWormHoleAccessible;

    /** buildings currently on this planet */
    private LinkedList buildings;

    /** Type of planet to draw */
    private int planetType;

    /** Radius at which ships orbit */
    private int orbitalRadius;

    /** ID generator */
    private static int planetIDGen = 0;

    /** this planet's unique ID number */
    private int planetID;

    /** path to adjacent planets */
    private static LinkedList path = new LinkedList();

    /** random number generator */
    private Random random;
    
    /**Client command*/
    private ClientCommandProcessor clientCommand;

    /**
     * Constructor for the Planet object
     */
    public Planet() {
        name = "b0rk";
        this.planetID = planetIDGen;
        planetIDGen++;
        planetType = (planetID % 5);
        adjacents = new LinkedList();
        buildings = new LinkedList();
        random = new Random();
        orbitalRadius = 60;
        location = new Point();
        population = random.nextInt(MAX_PP);
        hasFriendlyUnits = false;
    }

    /**
     * Overloaded constructor, takes in a name
     * 
     * @param name the name of this planet
     */
    public Planet(String name) {
        this();
        this.name = name;
    }


    /**
     * Adds a level of fortification to this planet
     */
    public void fortify() {
        if (owner.getMoney() > fortification * 200) {
            fortification++;
            owner.setMoney(owner.getMoney() - ((fortification - 1) * 200));
        }
    }

    /**
     * Checks path ?!
     * 
     * @param dest destination planet
     * @return whether or not a path exists
     */
    public boolean checkPath(Planet dest) {

        path.add(this);
        LinkedList adj = this.getAdjacents();
        for (int i = 0; i < adj.size(); i++) {
            if (path.contains(adj.get(i))) {
                continue;
            }                
            if (dest.equals((Planet) adj.get(i))) {
                path.clear();
                return true;
            } else if (((Planet) adj.get(i)).checkPath(dest)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates amount of food to give owner per round
     * 
     * @return an amount of food to add to stash
     */
    public int giveFood() {

        int toReturn = population * morale * allegiance;
        toReturn /= MAX_MO * MAX_AL * 100;
        return toReturn;
    }

    /**
     * Calculates amount of oil to give owner per round
     * 
     * @return an amount of oil to add to stash
     */
    public int giveOil() {
        int toReturn = population * morale * allegiance;
        toReturn /= MAX_MO * MAX_AL * 100;
        return toReturn;
    }

    /**
     * Calculates amount of money to give owner per round
     * 
     * @return an amount of money to add to stash
     */
    public int giveMoney() {
        int toReturn = population * morale * allegiance;
        toReturn /= MAX_MO * MAX_AL * 100;
        return toReturn;
    }

    /**
     * Calculates amount of steel to give owner per round
     * 
     * @return an amount of steel to add to stash
     */
    public int giveSteel() {
        int toReturn = population * morale * allegiance;
        toReturn /= MAX_MO * MAX_AL * 100;
        return toReturn;
    }

    /**
     * Modifier for the name field
     * 
     * @param newName the new name for the Planet
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Accessor for the name field
     * 
     * @return the name of the Planet
     */
    public String getName() {
        return name;
    }

    /**
     * Modifier for the population field
     * 
     * @param newPopulation the new population of the Planet
     */
    public void setPopulation(int newPopulation) {
        if (newPopulation > MAX_PP && MAX_PP != 0) {
            population = MAX_PP;
        } else if (newPopulation < MIN_PP) {
            population = MIN_PP;
        } else {
            population = newPopulation;
        }
    }

    /**
     * Accessor for the population field
     * 
     * @return the population of the Planet
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Modifier for the owner field
     * 
     * @param newOwner the new owner of the Planet CAN BE NULL--indicates NO
     *            ownership
     */
    public void setOwner(Player newOwner) {
        owner = newOwner;
    }

    /**
     * Accessor for the owner field
     * 
     * @return the owner of this Planet CAN BE NULL--indicates NO ownership
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Adds a building to this planet
     * 
     * @param b the building to add
     */
    public void addBuilding(Building b) {
        buildings.add(b);
    }

    /**
     * Removes a building from this planet
     * 
     * @param b the building to remove
     */
    public void removeBuilding(Building b) {
        buildings.remove(b);
    }

    /**
     * Modifier for the buildings field
     * 
     * @param newBuildings the new array of buildings
     */
    public void setBuildings(LinkedList newBuildings) {
        buildings = newBuildings;
    }

    /**
     * Accessor for the buildings field
     * 
     * @return the buildings currently in this planet
     */
    public LinkedList getBuildings() {
        return buildings;
    }

    /**
     * Determines if a building exists on the planet
     * 
     * @param b the string name of a building
     * @return true if the building is on the planet, false otherwise
     */
    public boolean hasBuilding(String b) {
        for (int i = 0; i < buildings.size(); i++) {
            if (((Building) buildings.get(i)).getName().equals(b)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Modifier for the isWormHoleAccessible flag
     * 
     * @param newIsCoastal the new worm hole status of the Planet
     */
    public void setIsWormHoleAccessible(boolean newIsCoastal) {
        isWormHoleAccessible = newIsCoastal;
    }

    /**
     * Accessor for the isWormHoleAccessible flag
     * 
     * @return whether or not this is a worm-hole accessible planet
     */
    public boolean getIsWormHoleAccessible() {
        return isWormHoleAccessible;
    }

    /**
     * Modifier for the allegiance field
     * 
     * @param newAllegiance the new allegiance level for the Planet
     */
    public void setAllegiance(int newAllegiance) {
        if (newAllegiance > MAX_AL && MAX_AL != 0) {
            allegiance = MAX_AL;
        } else if (newAllegiance < MIN_AL) {
            allegiance = MIN_AL;
        } else {
            allegiance = newAllegiance;
        }
    }

    /**
     * Accessor for the allegiance field
     * 
     * @return the allegiance level of the Planet
     */
    public int getAllegiance() {
        return allegiance;
    }

    /**
     * Modifier for the morale field
     * 
     * @param newMorale the new morale of this planet
     */
    public void setMorale(int newMorale) {
        if (newMorale > MAX_MO && MAX_MO != 0) {
            morale = MAX_MO;
        } else if (newMorale < MIN_MO) {
            morale = MIN_MO;
        } else {
            morale = newMorale;
        }
    }

    /**
     * Accessor for the morale field
     * 
     * @return the morale of this planet
     */
    public int getMorale() {
        return morale;
    }

    /**
     * Modifier for the fortification field
     * 
     * @param newFortification the new fortification level of the Planet
     */
    public void setFortification(int newFortification) {
        if (newFortification > MAX_FL && MAX_FL != 0) {
            fortification = MAX_FL;
        } else if (newFortification < MIN_FL) {
            fortification = MIN_FL;
        } else {
            fortification = newFortification;
        }
    }

    /**
     * Accessor for the fortification field
     * 
     * @return the fortification level of the Planet
     */
    public int getFortification() {
        return fortification;
    }

    /**
     * Adds the given planet to the adjacents list
     * 
     * @param toAdd the planet to add
     */
    public void addAdjacent(Planet toAdd) {
        adjacents.add(toAdd);
    }

    /**
     * Removes the given planet from the adjacents list
     * 
     * @param toRemove the planet to remove
     */
    public void removeAdjacent(Planet toRemove) {
        adjacents.remove(toRemove);
    }

    /**
     * Modifier for the adjacents field
     * 
     * @param newAdjacents the new array of planets adjacent to this one
     */
    public void setAdjacents(LinkedList newAdjacents) {
        adjacents = newAdjacents;
    }

    /**
     * Accessor for the adjacents field
     * 
     * @return the planets adjacent to this one
     */
    public LinkedList getAdjacents() {
        return adjacents;
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
     * Modifier for the location field
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void setLocation(int x, int y) {
      location.setLocation(x, y);
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
     * Modifier for the isCapitol field
     * 
     * @param newCapitol the new status of the Planet
     */
    public void setIsCapitol(boolean newCapitol) {
        if (newCapitol) {
            fortification = 3;
            owner.setMoney(owner.getMoney() - 10000);
        }
        isCapitol = newCapitol;
    }
    
    /**
     * Modifier for the capital field
     * @param b the new status of the planet
     */
    public void setCapitol(boolean b) {
      isCapitol = b;
      fortification = 3;
    }

    /**
     * Accessor for the isCapitol field
     * 
     * @return whether or not this is the capitol planet
     */
    public boolean getIsCapitol() {
        return isCapitol;
    }

    /**
     * Accessor for the planetID field
     * 
     * @return the ID for this planet
     */
    public int getPlanetID() {
        return this.planetID;
    }

    /**
     * Modifier for the planetID field
     * 
     * @param newID the new ID for this planet
     */
    public void setPlanetID(int newID) {
        this.planetID = newID;
    }

    /**
     * Overloaded toString method
     * 
     * @return a string representation of this planet
     */
    public String toString() {
        return "" + planetID + " " + (int) this.location.getX() + " "
                + (int) this.location.getY();
    }

    /**
     * Overloaded equals method
     * 
     * @param toCompare a planet to compare this one against
     * @return true if this planet is the same, false otherwise
     */
    public boolean equals(Object toCompare) {
        if (toCompare instanceof Planet) {
            Planet pToCompare = (Planet) toCompare;
            return getPlanetID() == pToCompare.getPlanetID();
        }
        return false;
    }
  
    /**
     * Overridden Object  method
     * dirty hack to make pmd shut up
     * @return the hashcode (bahaha!) for this Planet
     */
    public int hashCode() {
      return 10;
    }

    /**
     * Accessor for planetType ?????!!!??!?!?
     * 
     * @return the planet type ?!?!?!????
     */
    public int getPlanetType() {
        return planetType;
    }

    /**
     * Gets the orbital radius of this planet (default is 60)
     * 
     * @return the orbital radius of this planet
     */
    public int getOrbitalRadius() {
        return orbitalRadius;
    }

    /**
     * Sets the orbital radius of this planet (default is 60)
     * 
     * @param r the new orbital radius
     */
    public void setOrbitalRadius(int r) {
        orbitalRadius = r;
    }
    
    /**
     * Modifier for the clientCommand field
     * @param c the client command processor
     */
    public void setCommand(ClientCommandProcessor c) {
        clientCommand = c;
    }
    
    /**
     * Accessor for the clientCommand field
     * @return the clientCommand
     */
    public ClientCommandProcessor getCommand() {
        return clientCommand;
    }
    
    /**
     * Accessor for the timeToSurrender field... ?
     * @return the time (in cycles) until the planet is won over
     * by occupant
     */
    public int getTimeToSurrender() {
      int toReturn = 0;
      // anything between 1000 and 15000
      
      if (morale > (3 * MAX_MO) / 4) {
        toReturn += 20;
      } else if (morale > MAX_MO / 2) {
        toReturn += 50;
      } else if (morale > MAX_MO / 4) {
        toReturn += 70;
      } else {
        toReturn += 100;
      }
      
      // if population is large, time will be extended
      if (population > (3 * MAX_PP) / 4) {
        toReturn += (int) ((double) toReturn * 0.25);
      } else if (population > (MAX_PP / 2)) {
        toReturn += (int) ((double) toReturn * 0.50);
      }
      
      return toReturn;
    }
    
    /**
     * Modifier for the friendlyUnits field ?
     * @param b the new friendly unit status
     */
    public void setFriendlyUnits(boolean b) {
      hasFriendlyUnits = b;
    }
    
    /**
     * Accessor for the friendlyUnits field
     * @return whether or not this planet has friendly units
     */
    public boolean getFriendlyUnits() {
      return hasFriendlyUnits;
    }

    /**
     * Main method
     * @param args whatev
     */
    public static void main(String[] args) {
        Planet p = new Planet();
        p.setPopulation(10000);
        p.setMorale(50);
        p.setAllegiance(10);
        System.out.println(p.giveFood());
    }
}