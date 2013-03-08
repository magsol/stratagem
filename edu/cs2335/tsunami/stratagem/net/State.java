/*
 * Created on Oct 30, 2004
 */
package edu.cs2335.tsunami.stratagem.net;

import java.awt.Color;
import java.io.Serializable;
import java.util.LinkedList;

import edu.cs2335.tsunami.stratagem.kernel.Player;
import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.kernel.Military;

/**
 * @author Tim Liu
 */
public abstract class State implements Serializable {

    /** Uid */
    public static final long serialVersionUID = 26;

    /** List of Players */
    private LinkedList players;

    /** List of Planets */
    private Planet[] planets = new Planet[20];

    /** Number of planets */
    private int numPlanets = 0;

    /** List of units */
    private Military[] units = new Military[30];

    /** Number of military units */
    private int numUnits = 0;

    /** map */
    private String map;

    /** Chat */
    private LinkedList chat;

    /**day*/
    private int day = 0;

    /**
     * constructor
     *
     */
    public State() {
        chat = new LinkedList();
        players = new LinkedList();
    }

    /**
     * addplayer
     * @param p player
     */
    public void addPlayer(Player p) {
        players.add(p);
    }

    /**
     * getplayer
     * @return player
     */
    public LinkedList getPlayers() {
        return players;
    }

    /**
     * set player
     * @param ll linkedlist
     */
    public void setPlayers(LinkedList ll) {
        players = ll;
    }

    /**
     * set numplanets
     * @param n number
     */
    public void setNumPlanets(int n) {
        numPlanets = n;
    }

    /**
     * set num units
     * @param n number
     */
    public void setNumUnits(int n) {
        numUnits = n;
    }

    /**
     * setting units
     * @param m military array
     */
    public void setUnits(Military[] m) {
        units = m;
    }

    /**
     * setChat
     * @param ll linkedlist
     */
    public void setChat(LinkedList ll) {
        chat = ll;
    }

    /**
     * getPlayer from id
     * @param id idenification
     * @return player
     */
    public Player getPlayerFromID(int id) {
        for (int i = 0; i < players.size(); i++) {
            if (((Player) players.get(i)).getPlayerID() == id) {
                return ((Player) players.get(i));
            }
        }
        return null;
    }

    /**
     * add planet
     * @param p planet
     */
    public void addPlanet(Planet p) {
        if (numPlanets < 19) {
            planets[numPlanets] = p;
            numPlanets++;
        }
    }

    /**
     * getPlanets
     * @return planet array
     */
    public Planet[] getPlanets() {
        return planets;
    }

    /**
     * setPlanets
     * @param p planet
     */
    public void setPlanets(Planet[] p) {
        planets = p;
    }

    /**
     * getplanet
     * @param id idenification
     * @return planet
     */
    public Planet getPlanetFromId(int id) {
        for (int i = 0; i < numPlanets; i++) {
            if (planets[i].getPlanetID() == id) {
                return planets[i];
            }
        }
        return null;
    }

    /**
     * get planet
     * @param index index for retreival
     * @return planet
     */
    public Planet getPlanet(int index) {
        return planets[index];
    }

    /**
     * get numplanets
     * @return integer
     */
    public int getNumPlanets() {
        return numPlanets;
    }

    /**
     * adding unit
     * @param m military
     */
    public void addUnit(Military m) {
        units[numUnits] = m;
        numUnits++;

    }

    /**
     * getting a unit
     * @param i index
     * @return unit
     */
    public Military getUnit(int i) {
        return units[i];
    }

    /**
     * get unit
     * @param id idenification
     * @return unit
     */
    public Military getUnitFromId(int id) {
        for (int i = 0; i < numUnits; i++) {
            if (units[i].getMilitaryID() == id) {
                return units[i];
            }
        }
        return null;
    }

    /**
     * removeing unit
     * @param m unit
     */
    public void removeUnit(Military m) {
        boolean replace = false;
        for (int i = 0; i < numUnits; i++) {
            if (units[i].equals(m)) {
                replace = true;
            }
            if (replace && (units[i + 1] != null)) {
                units[i] = units[i + 1];
            } else if (units[i + 1] == null) {
                units[i] = null;
            }
        }
        numUnits--;
    }
    
    /**
     * removeing unit
     * @param j index
     */
    public void removeUnit(int j) {
        numUnits--;
        for (int i = j; i < numUnits; i++) {
            units[i] = units[i + 1];
        }
    }

    /**
     * get num units
     * @return integer
     */
    public int getNumUnits() {
        return numUnits;
    }

    /** 
     * addchat
     * @param line message
     */
    public void addChat(String line) {
        chat.addFirst(line);
    }

    /**
     * gget chat
     * @return linkedlist of chats
     */
    public LinkedList getChat() {
        return chat;
    }

    /**
     * setting map
     * @param path for map
     */
    public void setMap(String path) {
        map = path;
    }

    /**
     * return map
     * @return String
     */
    public String getMap() {
        return map;
    }

    /**
     * get player
     * @param i index
     * @return player
     */
    public Player getPlayer(int i) {
        return ((Player) players.get(i));
    }

    /**
     * setting player command
     * @param c clientCommand
     */
    public void setPlanetsCommand(ClientCommandProcessor c) {
        for (int i = 0; i < numPlanets; i++) {
            planets[i].setCommand(c);
        }
    }

    /**
     * incrementDate
     *
     */
    public void incrementDate() {
        day++;
        for (int i = 0; i < numPlanets; i++) {
            if (planets[i].getOwner() == null) {
                continue;
            }

            Player p = planets[i].getOwner();
            p.setFood(planets[i].giveFood() + p.getFood());
            p.setSteel(planets[i].giveSteel() + p.getSteel());
            p.setMoney(planets[i].giveMoney() + p.getMoney());
            p.setOil(planets[i].giveOil() + p.getOil());
        }
        for (int j = 0; j < players.size(); j++) {
            Player p = (Player) players.get(j);
            p.setDay(day);
        }
    }

    /**
     * set unit command
     * @param c clientcommand
     */
    public void setUnitCommand(ClientCommandProcessor c) {
        for (int i = 0; i < numUnits; i++) {
            units[i].setCommand(c);
        }
    }

    /**
     * setting unit State
     * @param s state
     */
    public void setUnitState(State s) {
        for (int i = 0; i < numUnits; i++) {
            units[i].setState(s);
        }
    }

    /**
     * still protected
     * @param p planet
     * @param exclude to exclude
     * @return bool
     */
    public boolean stillProtected(Planet p, Military exclude) {
        boolean still = false;
        Military m;
        for (int i = 0; i < getNumUnits(); i++) {
            m = getUnit(i);
            if (p.getLocation().distance(m.getLocation()) 
                    <= (p.getOrbitalRadius() + 10)
                    && m.getOwner().getPlayerID() != p.getOwner().getPlayerID()
                    && m.getMilitaryID() != exclude.getMilitaryID()) {
                still = true;
                return still;
            }
        }
        return still;
    }
    
    /**
     * Accessor for day
     * 
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * get next color
     * @return color
     */
    public Color getNotTakenColor() {
        return Color.red;
    }

    /**
     * setting owner
     *
     */
    public void setPlanetOwners() {
        int eachPlayerNumPlanets = numPlanets / players.size();
        int planetPointer = 0;
        Player currentPlayer;

        for (int i = 0; i < players.size(); i++) {
            currentPlayer = (Player) players.get(i);

            if (i == (players.size() - 1)) {
                for (int j = planetPointer; j < numPlanets; j++) {
                    planets[planetPointer].setOwner(currentPlayer);
                    planetPointer++;
                }
            } else {
                for (int j = 0; j < eachPlayerNumPlanets; j++) {
                    planets[planetPointer].setOwner(currentPlayer);
                    planetPointer++;
                }
            }
        }
    }

    /**
     * set adjacents
     *
     */
    public void setAdjacents() {
        for (int i = 0; i < numPlanets; i++) {
            for (int j = 0; j < numPlanets; j++) {
                if ((planets[i].getLocation()
                        .distance(planets[j].getLocation())) < 500) {
                    planets[i].addAdjacent(planets[j]);
                }
            }
        }
    }

    /**
     * get Military units
     * @return military array
     */
    public Military[] getUnits() {
        return units;
    }

    /**
     * has unit
     * @param m units
     * @return bool
     */
    public boolean hasUnit(Military m) {
        for (int i = 0; i < numUnits; i++) {
            if (units[i].equals(m)) {
                return true;
            }
        }
        return false;
    }
    

}