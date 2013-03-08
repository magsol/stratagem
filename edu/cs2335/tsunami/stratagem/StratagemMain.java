/*
 * Created on Nov 4, 2004
 *
 */
package edu.cs2335.tsunami.stratagem;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import edu.cs2335.tsunami.stratagem.gui.StratagemGUI;
import edu.cs2335.tsunami.stratagem.gui.StratagemSound;
import edu.cs2335.tsunami.stratagem.kernel.Player;
import edu.cs2335.tsunami.stratagem.net.ClientState;
import edu.cs2335.tsunami.stratagem.net.ServerState;
import edu.cs2335.tsunami.stratagem.net.StratagemClient;
import edu.cs2335.tsunami.stratagem.net.StratagemServer;

/**
 * @author Chris Gray
 */
public class StratagemMain {

    /** game client */
    private StratagemClient stratagemClient;

    /** game server */
    private StratagemServer stratagemServer;

    /** game gui */
    private StratagemGUI gui;

    /** Sounds Class */
    private StratagemSound sounds;

    /** game client state */
    private ClientState clientState;

    /* unused? */
    //private ServerState serverState;
    /** flag indicating if this machine is server */
    private boolean thisIsServer;

    /** player ID generator */
    private int playerId;

    /**
     * Constructor for this class
     */
    public StratagemMain() {
        sounds = new StratagemSound();
    }

    /**
     * Main class
     * 
     * @param args
     *            Arguements
     */
    public static void main(String[] args) {
        StratagemMain stratagemMain = new StratagemMain();
        stratagemMain.setGUI(new StratagemGUI(stratagemMain));
    }

    /**
     * Hosts the current game
     */
    public void hostGame() {
        thisIsServer = true;
        stratagemServer = new StratagemServer();
        stratagemClient = new StratagemClient(stratagemServer.getAddress(),
                this);
        //Already sending name for server, just need to update get player name
        stratagemClient.sendToServer("ADDPLAYER " + gui.getPlayerName());
        stratagemClient.sendToServer("SENDSTATE");
    }

    /**
     * Joins the game
     * 
     * @param add
     *            name of the game to add
     * @param name
     *            game to join
     */
    public void joinGame(String add, String name) {
        stratagemClient = new StratagemClient(add, this);
        stratagemClient.sendToServer("ADDPLAYER " + gui.getPlayerName());
        stratagemClient.sendToServer("SENDSTATE");
        thisIsServer = false;
    }

    /**
     * loads a game
     *  @param name is a filename
     */
    public void loadGame(String name) {
        ServerState s = loadState(name);
        if (s == null) {
            return;
        }
        thisIsServer = true;
        stratagemServer = new StratagemServer(s);
        stratagemClient = new StratagemClient(stratagemServer.getAddress(),
                this);
        //Already sending name for server, just need to update get player name
        stratagemClient.sendToServer("ADDPLAYER " + gui.getPlayerName());
        stratagemClient.sendToServer("SENDSTATE");
        for (int i = 0; i < s.getPlayers().size(); i++) {
            ((Player) s.getPlayer(i)).setReady(true);
        }
    }

    /**
     * Begins the game
     */
    public void beginGame() {
        gui.beginGame();
        //stratagemClient.getState().setPlanetOwners();

        if (stratagemServer != null) {
            stratagemServer.getState().getChat().clear();
        }
        stratagemClient.getState().getChat().clear();
        stratagemClient.getState().setUnitCommand(stratagemClient.getCommand());
        stratagemClient.getState().setUnitState(clientState);

        if (thisIsServer) {
            stratagemServer.getCommand()
            .setGameState(stratagemServer.getState());
            stratagemServer.getState().setUnitState(stratagemServer.getState());
        }
    }


    /**
     * Sets the current server state
     * 
     * @param s
     *            the new server state
     */
    public void setState(ServerState s) {
        clientState = new ClientState(s);
        clientState.setPlanetsCommand(stratagemClient.getCommand());
        gui.setServerState(clientState);
        stratagemClient.setState(clientState);
        gui.startMatchmaking();
    }
    
    /**
     * this loads a state
     * @param filename is filename
     * @return returns a serverstate
     */
    public ServerState loadState(String filename) {
        ServerState gameState;
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream inStream = new ObjectInputStream(file);
            gameState = (ServerState) inStream.readObject();
            return gameState;
        } catch (Exception e) {
            System.out.println("Cannot Load");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the client state
     * 
     * @return the client state
     */
    public ClientState getState() {
        return clientState;
    }

    /**
     * Sets the GUI
     * 
     * @param g
     *            the new stratagem GUI
     */
    public void setGUI(StratagemGUI g) {
        gui = g;
    }

    /**
     * Returns the client
     * 
     * @return the client
     */
    public StratagemClient getClient() {
        return stratagemClient;
    }

    /**
     * Determines if this is the server
     * 
     * @return whether or not this is the server
     */
    public boolean isServer() {
        return thisIsServer;
    }

    /**
     * Accessor for the server
     * 
     * @return the server
     */
    public StratagemServer getServer() {
        return stratagemServer;
    }

    /**
     * Accessor for the GUI
     * 
     * @return the GUI
     */
    public StratagemGUI getGUI() {
        return gui;
    }

    /**
     * Modifier for the player ID
     * 
     * @param id
     *            the new player's ID
     */
    public void setPlayerId(int id) {
        playerId = id;
    }

    /**
     * Accessor for the player ID
     * 
     * @return the player ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Accessor for the sound
     * 
     * @return the sound
     */
    public StratagemSound getSound() {
        return sounds;
    }
}