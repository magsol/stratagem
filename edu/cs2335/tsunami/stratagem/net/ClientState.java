package edu.cs2335.tsunami.stratagem.net;

import java.io.Serializable;

/**
 * @author tim
 */
public class ClientState extends State implements Serializable {

    /** Current Player ID */
    private int playerID;

    /**
     * constructor
     * @param s state
     */
    public ClientState(ServerState s) {
        this.setPlayers(s.getPlayers());
        this.setPlanets(s.getPlanets());
        this.setNumPlanets(s.getNumPlanets());
        this.setUnits(s.getUnits());
        this.setNumUnits(s.getNumUnits());
        this.setMap(s.getMap());
        this.setChat(s.getChat());
    }

    /**
     * player id
     * @return id
     */
    public int getPlayerId() {
        return playerID;
    }

    /**
     * setting player id
     * @param p integer
     */
    public void setPlayerId(int p) {
        playerID = p;
    }

}