package edu.cs2335.tsunami.stratagem.kernel;

import java.util.LinkedList;

/**
 * Creates an Army
 * 
 * @author Shannon Quinn
 */
public abstract class Army extends Military {

    /**
     * Constructor
     * @param current the planet on which this outfit is built
     */
    public Army(Planet current) {
        super(current);
    }

    /**
     * Moving a ship - standard method for movement
     * 
     * @param dest
     *            planet
     */
    public void moveFlight(Planet dest) {
        Planet here = this.getCurrentPlanet();
        LinkedList adj = here.getAdjacents();
        for (int i = 0; i < adj.size(); i++) {
            if (dest.equals((Planet) adj.get(i))) {
                setNextPlanet(dest);
                break;
            }

        }

    }

    /**
     * Movement by shuttle - costs money
     * 
     * @param dest
     *            planet
     */
    public void moveShuttle(Planet dest) {
        if (this.getOwner().getMoney() < 200) {
            System.out.println("Not enough money to perform move!");
        } else if (dest != null) {
            if (this.getCurrentPlanet().checkPath(dest)) {
                this.getOwner().setMoney(this.getOwner().getMoney() - 200);
                this.getCurrentPlanet().setFriendlyUnits(false);
                this.setDestPlanet(dest);
                this.setCurrentPlanet(dest);
                this.getCurrentPlanet().setFriendlyUnits(true);
                this.setOrbiting(true);
                this.getLocation().setLocation((int) 
                                (dest.getLocation().getX() + 60), 
                                (int) (dest.getLocation().getY()));
            }

        }
    }

    /**
     * Move through a wormhole - costs enormous money
     * 
     * @param dest
     *            planet
     */
    public void moveWormHoleTransport(Planet dest) {
        if (this.getOwner().getMoney() < 500) {
            System.out.println("Not enough money to perform move!");
        } else {
            if (dest.getIsWormHoleAccessible()
                    && this.getCurrentPlanet().getIsWormHoleAccessible()) {
                this.setArmyInWormHole(true);
                this.setSpeed(getSpeed() + 4);
                this.getOwner().setMoney(this.getOwner().getMoney() - 500);
                this.setNextPlanet(dest);
            }
        }
    }
    
    /**
     * Accessor for supply
     * @return Food supply
     */
    public int getSupply() {
        return getFoodSupply();
    }
}