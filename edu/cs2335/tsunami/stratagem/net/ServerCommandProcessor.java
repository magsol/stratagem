/*
 * Created on Nov 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.net;

import java.awt.Color;
import java.util.StringTokenizer;

import edu.cs2335.tsunami.stratagem.kernel.Battle;
import edu.cs2335.tsunami.stratagem.kernel.Dreadnaught;
import edu.cs2335.tsunami.stratagem.kernel.Frigate;
import edu.cs2335.tsunami.stratagem.kernel.Military;
import edu.cs2335.tsunami.stratagem.kernel.Overlord;
import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.kernel.Player;
import edu.cs2335.tsunami.stratagem.kernel.Ragnarok;
import edu.cs2335.tsunami.stratagem.kernel.Raptor;
import edu.cs2335.tsunami.stratagem.kernel.Shade;

/**
 * @author Tim Liu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ServerCommandProcessor {

    /** Instance of StratagemServer */
    private StratagemServer stratagemServer;

    /** Instance of current Game State */
    private ServerState gameState;

    /**
     * Server command constructor
     * 
     * @param s
     *            is a StratagemServer
     */
    public ServerCommandProcessor(StratagemServer s) {
        stratagemServer = s;
        gameState = stratagemServer.getState();
    }

    /**
     * Processes commands
     * 
     * @param cmd
     *            is a command on the state
     * @param usrId
     *            is the userId
     */
    public void processCommand(String cmd, int usrId) {
        //System.out.println("Server receives: " + cmd + " from: " + usrId);
        StringTokenizer tokenizer = new StringTokenizer(cmd);

        if (stratagemServer.started()) {
            long time = System.currentTimeMillis()
                    - stratagemServer.getInitTime();
            gameState.addOrdersProcessed(time + " " + usrId + " " + cmd);
        }

        String executeCommand = tokenizer.nextToken();

        String tempCmd;

        /** Unit orders */
        if (executeCommand.compareTo("MOVE") == 0) {
            move(tokenizer);
        } else if (executeCommand.compareTo("BOMBARD") == 0) {
            String unitId = tokenizer.nextToken();
            String planetId = tokenizer.nextToken();
            gameState.checkBombard(unitId, planetId);
        } else if (executeCommand.compareTo("RAID") == 0) {
            String unitId = tokenizer.nextToken();
            String planetId = tokenizer.nextToken();
            if (gameState.checkRaid(unitId, planetId)) {
                stratagemServer.sendToAll("RAID " + " " + unitId + " "
                        + planetId);
            }
        } else if (executeCommand.compareTo("ISBOMBARDING") == 0) {
            String planetId = tokenizer.nextToken();
            String unitId = tokenizer.nextToken();
            String bool = tokenizer.nextToken();
            if (gameState.checkIsBombarding(unitId, planetId,
                    bool)) {
                stratagemServer.sendToAll("ISBOMBARDING " + planetId + " "
                        + unitId + " " + bool);
            }
        } else if (executeCommand.compareTo("ISRAIDING") == 0) {
            String planetId = tokenizer.nextToken();
            String unitId = tokenizer.nextToken();
            String bool = tokenizer.nextToken();
            String daysToRaid = tokenizer.nextToken();
            if (gameState.checkIsRaiding(unitId, planetId,
                    bool, daysToRaid)) {
                stratagemServer.sendToAll("ISRAIDING " + planetId + " "
                        + unitId + " " + bool + " " + daysToRaid);
            }
        } else if (executeCommand.compareTo("TRAIN") == 0) {
            tempCmd = tokenizer.nextToken();
            if (gameState.checkTrain(tempCmd)) {
                stratagemServer.sendToAll("TRAIN " + tempCmd);
            }
        } else if (executeCommand.compareTo("RECRUIT") == 0) {
            String cityId = tokenizer.nextToken();
            String unitType = tokenizer.nextToken();
            String size = tokenizer.nextToken();
            int currentID = gameState.returnCurrentMilitaryID();
                if (gameState.checkRecruit(cityId, unitType, size)) {
                    stratagemServer.sendToAll("RECRUIT " + cityId + " " 
                             + unitType + " " + size + " " + currentID);
                }
        } else if (executeCommand.compareTo("MERGE") == 0) {
            int unit1Id = Integer.parseInt(tokenizer.nextToken());
            int unit2Id = Integer.parseInt(tokenizer.nextToken());
            Military m1 = gameState.getUnitFromId(unit1Id);
            Military m2 = gameState.getUnitFromId(unit2Id);
            
                if (merge(m1, m2)) {
                    gameState.removeUnit(m2);
                    stratagemServer.sendToAll
                    ("MERGE " + unit1Id + " " + unit2Id);
                }
        } else if (executeCommand.compareTo("REMOVE") == 0) {
            String unitId = tokenizer.nextToken();
            Military m = gameState.getUnitFromId(Integer.parseInt(unitId));
            gameState.removeUnit(m);
            stratagemServer.sendToAll("REMOVE " + unitId);
        } else if (executeCommand.compareTo("BATTLE") == 0) {
            battle(tokenizer);
        } else if (executeCommand.compareTo("CURRENTPLANET") == 0) {
            int unitId = Integer.parseInt(tokenizer.nextToken());
            int cityId = Integer.parseInt(tokenizer.nextToken());
            Military ship = gameState.getUnitFromId(unitId);
            Planet p = gameState.getPlanetFromId(cityId);
            if (ship != null) {
                ship.setCurrentPlanet(p);
                ship.setOrbiting(true);
                stratagemServer.sendToAll
                ("CURRENTPLANET " + unitId + " " + cityId);
            }
        } else if (executeCommand.compareTo("BUILD") == 0) {
            String cityId = tokenizer.nextToken();
            String buildingType = tokenizer.nextToken();
            if (gameState.checkBuild(cityId, buildingType)) {
                stratagemServer.sendToAll("BUILD " + cityId + " "
                        + buildingType);
            }
        } else if (executeCommand.compareTo("FORTIFY") == 0) {
            String cityId = tokenizer.nextToken();
            if (gameState.checkFortify(cityId)) {
                stratagemServer.sendToAll("FORTIFY " + cityId);
            }

        } else if (executeCommand.compareTo("MAKECAPITAL") == 0) {
            String id = tokenizer.nextToken();
            if (gameState.checkMakeCapital(id)) {
             //   long currTime = stratagemServer.getInitTime()
             //           - System.currentTimeMillis();
                stratagemServer.sendToAll("MAKECAPITAL " + id);
            }

        } else if (executeCommand.compareTo("CHANGEOWNER") == 0) {
            int planetId = Integer.parseInt(tokenizer.nextToken());
            int playerId = Integer.parseInt(tokenizer.nextToken());
            Planet p = gameState.getPlanetFromId(planetId);
            Player player = gameState.getPlayer(playerId);
            p.setOwner(player);
            p.setFriendlyUnits(true);
            stratagemServer.sendToAll("CHANGEOWNER " + planetId + " "
                    + playerId);

        } else if (executeCommand.compareTo("BEGINGAME") == 0) {
            if (stratagemServer.getState() != null) {
                try {
                    stratagemServer.getServerSocket().close();
                } catch (Exception e) {
                    stratagemServer.sendToAll("QUIT");
                }
                //gameState.setPlanetOwners();
                stratagemServer.sendToAll("BEGINGAME");
            }

        } else if (executeCommand.compareTo("PAUSE") == 0) {
            stratagemServer.pause();
            stratagemServer.sendToAll("PAUSE");
        } else if (executeCommand.compareTo("QUIT") == 0) {
            UserThread u = stratagemServer.getUser(usrId);
            String name = u.getUserAddressName();
            if (u != null) {
                u.setConnected(false);
                stratagemServer.getUserThreads().remove(u);
                stratagemServer.sendToAll("USERLEFTGAME " + name);
            }
        } else if (executeCommand.compareTo("CHAT") == 0) {
            chat(tokenizer, usrId);
        } else if (executeCommand.compareTo("NAME") == 0) {
            tempCmd = getRest(tokenizer);
            gameState.getPlayer(gameState.getPlayerIdGen()).setName(tempCmd);
      //      UserThread u = stratagemServer.getUser(usrId);
        } else if (executeCommand.compareTo("SENDSTATE") == 0) {
            UserThread u = stratagemServer.getUser(usrId);
            u.printline("SENDINGSTATE");
            stratagemServer.sendGameState(u);
            try {
                u.sleep(50);
            } catch (Exception e) {
                u.printline("ERROR");
            }
        } else if (executeCommand.compareTo("STATERECEIVED") == 0) {
            gameState.setPlayerIdGen(gameState.getPlayerIdGen() + 1);
        /*
        } else if (executeCommand.compareTo("STATEDIED") == 0) {
            UserThread u = stratagemServer.getUser(usrId);
        */
        } else if (executeCommand.compareTo("COLOR") == 0) {
            changeColor(tokenizer, usrId);
        } else if (executeCommand.compareTo("SIDE") == 0) {
            String side = tokenizer.nextToken();
            gameState.getPlayerFromID(usrId).setSide(side);

            stratagemServer.sendToAll("SIDE " + usrId + " " + side);
        } else if (executeCommand.compareTo("ADDPLAYER") == 0) {
            String name = tokenizer.nextToken();
            if (gameState.getOrdersProcessed().size() == 0) {
                Player p = new Player(name, gameState.getNotTakenColor());
                p.setPlayerID(usrId);
                gameState.addPlayer(p);
                stratagemServer.sendToAllExcept(usrId, "ADDPLAYER " + usrId
                        + " " + name);
            }

        } else if (executeCommand.compareTo("STARTCITY") == 0) {
            String planetId = tokenizer.nextToken();
            if (stratagemServer.getState().checkStartCity(usrId, planetId)) {
                stratagemServer
                        .sendToAll("STARTCITY " + usrId + " " + planetId);
            }
        } else if (executeCommand.compareTo("READY") == 0) {
            Player p = gameState.getPlayerFromID(usrId);
            p.setReady(!p.getReady());
            stratagemServer.sendToAll("READY " + usrId);
        } else if (executeCommand.compareTo("SAVEGAME") == 0) {
            String filename = tokenizer.nextToken();
            stratagemServer.saveState(filename);
        }
    }
    
    /**
     * move
     * @param tokenizer command
     */
    public void move(StringTokenizer tokenizer) {
        String tempCmd = tokenizer.nextToken();
        String unitId = tokenizer.nextToken();
        String planetId = tokenizer.nextToken();

        if (tempCmd.compareTo("FLIGHT") == 0) {
            if (gameState.checkMoveFlight(unitId, planetId)) {
                stratagemServer.sendToAll("MOVE FLIGHT " + unitId + " "
                        + planetId);
            }
        } else if (tempCmd.compareTo("SHUTTLE") == 0) {
            if (gameState.checkMoveShuttle(unitId, planetId)) {
                stratagemServer.sendToAll("MOVE SHUTTLE " + unitId + " "
                        + planetId);
            }
        } else if (tempCmd.compareTo("WORMTRANS") == 0) {
            if (gameState.checkMoveWormHoleTrans(unitId, planetId)) {
                stratagemServer.sendToAll("MOVE WORMTRANS " + unitId + " "
                        + planetId);
            }
        } else if (tempCmd.compareTo("WORM") == 0) {
            if (gameState.checkMoveWormHole(unitId, planetId)) {
                stratagemServer.sendToAll("MOVE WORM " + unitId + " "
                        + planetId);
            }
        }
    }
    
    /**
     * chat
     * @param tokenizer command
     * @param usrId id
     */
    public void chat(StringTokenizer tokenizer, int usrId) {
        String message = getRest(tokenizer);
        gameState.addChat(message);
        stratagemServer.sendToAll("CHAT " + usrId + message);
    }
    
    /**
     * battle
     * @param tokenizer command
     */
    public void battle(StringTokenizer tokenizer) {
        int attackerId = Integer.parseInt(tokenizer.nextToken());
        int defenderId = Integer.parseInt(tokenizer.nextToken());
        Military attacker = gameState.getUnitFromId(attackerId);
        Military defender = gameState.getUnitFromId(defenderId);

        if (gameState.hasUnit(defender) && gameState.hasUnit(attacker)) {
            Battle b = new Battle(attacker, defender);
            Military winner = b.battle();
            Military loser = b.getLoser();
            
            if (!gameState.stillProtected(loser.getCurrentPlanet(), loser)
                    && loser.getSize() == 0) {
                loser.getCurrentPlanet().setFriendlyUnits(false);
            }
            if (!gameState.stillProtected(winner.getCurrentPlanet(), winner)
                    && winner.getSize() == 0) {
                winner.getCurrentPlanet().setFriendlyUnits(false);
            }

            stratagemServer.sendToAll("BATTLE " + winner.getMilitaryID()
                    + " " + winner.getSize() + " " + winner.getMorale()
                    + " " + winner.getExperience() + " "
                    + loser.getMilitaryID() + " " + loser.getSize() + " "
                    + loser.getMorale() + " " + loser.getExperience());

        }
    }
    
    /**
     * merge
     * @param m1 military1
     * @param m2 military2
     * @return bool
     */
    public boolean merge(Military m1, Military m2) {
        if (m1 instanceof Raptor) {
            if (((Raptor) m1).merge(m2)) {
                return true;
            }
        } else if (m1 instanceof Frigate) {
            if (((Frigate) m1).merge(m2)) {
                return true;
            }
        } else if (m1 instanceof Ragnarok) {
            if (((Ragnarok) m1).merge(m2)) {
                return true;
            }
        } else if (m1 instanceof Overlord) {
            if (((Overlord) m1).merge(m2)) {
                return true;
            }
        } else if (m1 instanceof Shade) {
            if (((Shade) m1).merge(m2)) {
                return true;
            }
        } else if (m1 instanceof Dreadnaught) {
            if (((Dreadnaught) m1).merge(m2)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * change color
     * @param tokenizer command
     * @param usrId id
     */
    public void changeColor(StringTokenizer tokenizer, int usrId) {
        String color = tokenizer.nextToken();
        if (color.equals("BLUE")) {
            gameState.getPlayerFromID(usrId).setColor(Color.blue);
        } else if (color.equals("RED")) {
            gameState.getPlayerFromID(usrId).setColor(Color.red);
        } else if (color.equals("ORANGE")) {
            gameState.getPlayerFromID(usrId).setColor(Color.orange);
        } else if (color.equals("CYAN")) {
            gameState.getPlayerFromID(usrId).setColor(Color.cyan);
        }
        stratagemServer.sendToAll("COLOR " + usrId + " " + color);
    }

    /**
     * Get the rest of a string from a tokenizer
     * 
     * @param t
     *            is a StringTokenizer
     * @return a string of the rest of the tokenizer
     */
    public String getRest(StringTokenizer t) {
        String ot = "";
        while (t.hasMoreTokens()) {
            ot = ot + " " + t.nextToken();
        }
        return ot;
    }
    
    /**
     * setting state
     * @param s state
     */
    public void setGameState(ServerState s) {
        gameState = s;
    }
    


}