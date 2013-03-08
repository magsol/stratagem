/*
 * Created on Nov 8, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.net;

import java.awt.Color;
import java.util.StringTokenizer;

import edu.cs2335.tsunami.stratagem.StratagemMain;
import edu.cs2335.tsunami.stratagem.kernel.Army;
import edu.cs2335.tsunami.stratagem.kernel.Dreadnaught;
import edu.cs2335.tsunami.stratagem.kernel.Frigate;
import edu.cs2335.tsunami.stratagem.kernel.Military;
import edu.cs2335.tsunami.stratagem.kernel.Navy;
import edu.cs2335.tsunami.stratagem.kernel.Overlord;
import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.kernel.Player;
import edu.cs2335.tsunami.stratagem.kernel.Ragnarok;
import edu.cs2335.tsunami.stratagem.kernel.Raptor;
import edu.cs2335.tsunami.stratagem.kernel.Shade;
import edu.cs2335.tsunami.stratagem.kernel.Shipyard;
import edu.cs2335.tsunami.stratagem.kernel.TrainingAcademy;
import edu.cs2335.tsunami.stratagem.kernel.WarFactory;

/**
 * @author Tim Liu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ClientCommandProcessor {
    /**client*/
    private StratagemClient stratagemClient;

    /**main*/
    private StratagemMain stratagemMain;

    /**state*/
    private ClientState gameState;

    /**
     * this is the constructor
     * @param s is a client
     * @param sm is main
     */
    public ClientCommandProcessor(StratagemClient s, StratagemMain sm) {
        stratagemClient = s;
        stratagemMain = sm;
    }

    /** 
     * *********RECEIVE FROM SERVER COMMANDS**********************
     * processes commands
     * @param cmd is a command 
     */
    public void processCommand(String cmd) {
        //System.out.println("Client" + 
        //stratagemMain.getPlayerId() + " receives:"
        //+ cmd);
        StringTokenizer tokenizer = new StringTokenizer(cmd);
        String executeCommand = tokenizer.nextToken();

        String tempCmd;

        /** Unit orders */
        if (executeCommand.compareTo("MOVE") == 0) {
           move(tokenizer);
        } else if (executeCommand.compareTo("BOMBARD") == 0) {
            Military ship = gameState.getUnitFromId(Integer.parseInt(tokenizer
                    .nextToken()));
            Planet end = gameState.getPlanetFromId(Integer.parseInt(tokenizer
                    .nextToken()));
            ship.bombard(end);
        } else if (executeCommand.compareTo("RAID") == 0) {
            Military ship = gameState.getUnitFromId(Integer.parseInt(tokenizer
                    .nextToken()));
            Planet end = gameState.getPlanetFromId(Integer.parseInt(tokenizer
                    .nextToken()));
            ship.raid(end);
        } else if (executeCommand.compareTo("ISBOMBARDING") == 0) {
            Planet world = gameState.getPlanetFromId(Integer.parseInt(tokenizer
                    .nextToken()));
            Military ship = gameState.getUnitFromId(Integer.parseInt(tokenizer
                    .nextToken()));
            String bool = tokenizer.nextToken();
            if (bool.equals("TRUE")) {
                ship.setIsBombarding(true);
                ship.setNextPlanet(world);
            } else {
                ship.setIsBombarding(false);
            }
        } else if (executeCommand.compareTo("ISRAIDING") == 0) {
            Planet world = gameState.getPlanetFromId(Integer.parseInt(tokenizer
                    .nextToken()));
            Military ship = gameState.getUnitFromId(Integer.parseInt(tokenizer
                    .nextToken()));
            String bool = tokenizer.nextToken();
            int daysR = Integer.parseInt(tokenizer.nextToken());
            if (bool.equals("TRUE")) {
                ship.setNextPlanet(world);
                ship.setDaysToRaid(daysR);
                ship.setIsRaiding(true);
            } else {
                ship.setIsRaiding(false);
            }
        } else if (executeCommand.compareTo("BATTLE") == 0) {
            Military winner = gameState.getUnitFromId(Integer
                    .parseInt(tokenizer.nextToken()));
            winner.setSize(Integer.parseInt(tokenizer.nextToken()));
            winner.setMorale(Integer.parseInt(tokenizer.nextToken()));
            winner.setExperience(Integer.parseInt(tokenizer.nextToken()));

            Military loser = gameState.getUnitFromId(Integer.parseInt(tokenizer
                    .nextToken()));
            loser.setSize(Integer.parseInt(tokenizer.nextToken()));
            loser.setMorale(Integer.parseInt(tokenizer.nextToken()));
            loser.setExperience(Integer.parseInt(tokenizer.nextToken()));

            stratagemMain.getGUI().drawBattle(winner, loser);
            stratagemMain.getSound().play(0);

        } else if (executeCommand.compareTo("TRAIN") == 0) {
            tempCmd = tokenizer.nextToken();
            Military ship = gameState.getUnitFromId(Integer.parseInt(tempCmd));
            ship.train();
        } else if (executeCommand.compareTo("MERGE") == 0) {
            Military m1 = gameState.getUnitFromId
            (Integer.parseInt(tokenizer.nextToken()));
            Military m2 = gameState.getUnitFromId
            (Integer.parseInt(tokenizer.nextToken()));
                if (merge(m1, m2)) {
                    gameState.removeUnit(m2);
                }
        } else if (executeCommand.compareTo("RECRUIT") == 0) {
            recruit(tokenizer);
        } else if (executeCommand.compareTo("CURRENTPLANET") == 0) {
            Military ship = gameState.getUnitFromId(Integer.parseInt(tokenizer
                    .nextToken()));

            Planet p = gameState.getPlanetFromId(Integer.parseInt(tokenizer
                    .nextToken()));

            ship.setCurrentPlanet(p);
        } else if (executeCommand.compareTo("UPDATERESOURCES") == 0) {
            gameState.incrementDate();

        } else if (executeCommand.compareTo("BUILD") == 0) {
            build(tokenizer);
        } else if (executeCommand.compareTo("FORTIFY") == 0) {
            int cityId = Integer.parseInt(tokenizer.nextToken());
            Planet p = gameState.getPlanetFromId(cityId);
            p.fortify();

        } else if (executeCommand.compareTo("MAKECAPITAL") == 0) {
            int cityId = Integer.parseInt(tokenizer.nextToken());
            Planet p = gameState.getPlanetFromId(cityId);

            for (int i = 0; i < gameState.getNumPlanets(); i++) {
                if (gameState.getPlanet(i).getIsCapitol()) {
                    gameState.getPlanet(i).setIsCapitol(false);
                    break;
                }
            }
            p.setIsCapitol(true);
        } else if (executeCommand.compareTo("CHANGEOWNER") == 0) {
            Planet p = gameState.getPlanetFromId(Integer.parseInt(tokenizer
                    .nextToken()));

            Player player = gameState.getPlayer(Integer.parseInt(tokenizer
                    .nextToken()));
            p.setOwner(player);
        } else if (executeCommand.compareTo("SENDINGSTATE") == 0) {
            try {
                stratagemClient.sleep(50);
            } catch (Exception e) {
                //System.err.println(e);
                stratagemClient.sendToServer("STATEDIED");
            }
            stratagemClient.receiveGameState();
        } else if (executeCommand.compareTo("CHAT") == 0) {
            gameState.addChat(getRest(tokenizer));
        } else if (executeCommand.compareTo("BEGINGAME") == 0) {
            stratagemMain.beginGame();
        } else if (executeCommand.compareTo("PLAYERID") == 0) {
            int id = Integer.parseInt(tokenizer.nextToken());
            stratagemMain.setPlayerId(id);

        } else if (executeCommand.compareTo("COLOR") == 0) {
            int usrId = Integer.parseInt(tokenizer.nextToken());
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
        } else if (executeCommand.compareTo("SIDE") == 0) {
            int usrId = Integer.parseInt(tokenizer.nextToken());
            String side = tokenizer.nextToken();
            gameState.getPlayerFromID(usrId).setSide(side);
        } else if (executeCommand.compareTo("REMOVE") == 0) {
            int unitId = Integer.parseInt(tokenizer.nextToken());
            Military ship = gameState.getUnitFromId(unitId);
            gameState.removeUnit(ship);
        } else if (executeCommand.compareTo("ADDPLAYER") == 0) {
            int playerId = Integer.parseInt(tokenizer.nextToken());
            String name = tokenizer.nextToken();
            Player p = new Player(name, gameState.getNotTakenColor());
            p.setPlayerID(playerId);
            gameState.addPlayer(p);
        } else if (executeCommand.compareTo("PAUSE") == 0) {
            stratagemMain.getGUI().setPause();
        } else if (executeCommand.compareTo("STARTCITY") == 0) {
            int playerId = Integer.parseInt(tokenizer.nextToken());
            Player currentPlayer = gameState.getPlayerFromID(playerId);
            String planetId = tokenizer.nextToken();
            Planet p = gameState.getPlanetFromId(Integer.parseInt(planetId));

            for (int i = 0; i < gameState.getNumPlanets(); i++) {
                Planet currentPlanet = gameState.getPlanet(i);
                if (currentPlanet.getIsCapitol()
                        && (currentPlanet.getOwner() != null)) {
                    if (currentPlanet.getOwner().getPlayerID() == currentPlayer
                            .getPlayerID()) {
                        currentPlanet.setCapitol(false);
                        currentPlanet.setOwner(null);
                        break;
                    }
                }
            }
            p.setOwner(currentPlayer);
            p.setCapitol(true);
        } else if (executeCommand.compareTo("READY") == 0) {
            int usrId = Integer.parseInt(tokenizer.nextToken());
            Player p = gameState.getPlayerFromID(usrId);
            p.setReady(!p.getReady());
        }
    }

    /** *****************SEND TO SERVER REQUESTS************************** */
    /** UNIT ORDERS */
    /**moveflight
     * @param unitId is id
     * @param to is planet
     */
    public void moveFlight(int unitId, int to) {
        stratagemClient.sendToServer("MOVE FLIGHT " + unitId + " " + to);
    }

    /**
     * moveshuttle
     * @param unitId is unitid
     * @param to is planet
     */
    public void moveShuttle(int unitId, int to) {
        stratagemClient.sendToServer("MOVE SHUTTLE " + unitId + " " + to);
    }

    /**
     * wormhole army
     * @param unitId is id
     * @param to is planet
     */
    public void moveWormHoleTransportable(int unitId, int to) {
        stratagemClient.sendToServer("MOVE WORMTRANS " + unitId + " " + to);
    }

    /**
     * wormhole
     * @param unitId id
     * @param to planet
     */
    public void moveWormHole(int unitId, int to) {
        stratagemClient.sendToServer("MOVE WORM " + unitId + " " + to);
    }

    /**
     * bombard
     * @param unitId id
     * @param to planet
     */
    public void bombard(int unitId, int to) {
        stratagemClient.sendToServer("BOMBARD " + unitId + " " + to);
    }

    /**
     * raid
     * @param unitId id
     * @param to planet
     */
    public void raid(int unitId, int to) {
        stratagemClient.sendToServer("RAID " + unitId + " " + to);
    }

    /**
     * is raiding
     * @param unitId id
     * @param planetId planet id
     * @param bool raiding?
     * @param daysRaid days
     */
    public void isRaiding
    (int unitId, int planetId, String bool, String daysRaid) {
        stratagemClient.sendToServer("ISRAIDING " + planetId + " " + unitId
                + " " + bool + " " + daysRaid);
    }

    /**
     * is bombarding
     * @param unitId id
     * @param planetId planet
     * @param bool bombarding?
     */
    public void isBombarding(int unitId, int planetId, String bool) {
        stratagemClient.sendToServer("ISBOMBARDING " + planetId + " " + unitId
                + " " + bool);
    }

    /**
     * train
     * @param unitId id
     */
    public void train(int unitId) {
        stratagemClient.sendToServer("TRAIN " + unitId);
    }

    /**
     * recruit
     * @param cityId city id
     * @param type type of unit
     * @param size number of units
     */
    public void recruit(int cityId, String type, int size) {
        stratagemClient.sendToServer("RECRUIT " + cityId + " " + type + " "
                + size);
    }
    
    /**
     * merge
     * @param unit1Id id1
     * @param unit2Id id2
     */
    public void sendMerge(int unit1Id, int unit2Id) {
        stratagemClient.sendToServer("MERGE " + unit1Id + " " + unit2Id);
    }

    /**
     * battle two units
     * @param attackerId attacker
     * @param defenderId defender
     */
    public void battle(int attackerId, int defenderId) {
        stratagemClient.sendToServer("BATTLE " + attackerId + " " + defenderId);
    }

    /**
     * setting current planet
     * @param unitId unit id
     * @param planetId planet id
     */
    public void setCurrentPlanet(int unitId, int planetId) {
        Military ship = gameState.getUnitFromId(unitId);
        if (ship.getOwner().getPlayerID() == gameState.getPlayerId()) {
            stratagemClient.sendToServer("CURRENTPLANET " + unitId + " "
                    + planetId);
        }
    }

    /** CITY ORDERS */
    /**
     * build a building
     * @param cityId is a planet id
     * @param building is a type
     */
    public void build(int cityId, String building) {
        stratagemClient.sendToServer("BUILD " + cityId + " " + building);
    }

    /**
     * fortify
     * @param cityId id
     */
    public void fortify(int cityId) {
        stratagemClient.sendToServer("FORTIFY " + cityId);
    }

    /**
     * make capital
     * @param cityId id
     */
    public void makeCapital(int cityId) {
        stratagemClient.sendToServer("MAKECAPITAL " + cityId);
    }

    /** NETWORK COMMANDS */
    /**
     * begingame
     */
    public void beginGame() {
        stratagemClient.sendToServer("BEGINGAME");
    }

    /**
     * saves
     * @param filename is a file name
     */
    public void saveGame(String filename) {
        stratagemClient.sendToServer("SAVEGAME " + filename + ".sav");
    }

    /**
     * pause
     *
     */
    public void pause() {
        stratagemClient.sendToServer("PAUSE");
    }

    /**
     * quits
     *
     */
    public void quit() {
        stratagemClient.sendToServer("QUIT");
    }

    /**
     * start city
     * @param p is a planet
     */
    public void startCity(Planet p) {
        stratagemClient.sendToServer("STARTCITY " + p.getPlanetID());
    }

    /**
     * chats
     * @param message is a string
     */
    public void chat(String message) {
        stratagemClient.sendToServer("CHAT " + message);
    }

    /**
     * sends player name
     * @param name is a name
     */
    public void sendName(String name) {
        stratagemClient.sendToServer("NAME " + name);
    }

    /**
     * planetOwner
     * @param cityId planet id
     * @param playerId player id
     */
    public void setPlanetOwner(int cityId, int playerId) {
        stratagemClient.sendToServer("CHANGEOWNER " + cityId + " " + playerId);
    }

    /**
     * remove unit
     * @param unitId id
     */
    public void removeUnit(int unitId) {
        stratagemClient.sendToServer("REMOVE " + unitId);
    }

    /**
     * change color
     * @param color is a color
     */
    public void sendColor(String color) {
        stratagemClient.sendToServer("COLOR " + color);
    }

    /**
     * sets side
     * @param side string
     */
    public void sendSide(String side) {
        stratagemClient.sendToServer("SIDE " + side);
    }

    /**
     * addplayer
     * @param name is a name
     */
    public void addPlayer(String name) {
        stratagemClient.sendToServer("ADDPLAYER " + name);
    }

    /**
     * ready
     *
     */
    public void ready() {
        stratagemClient.sendToServer("READY");
    }

    /**
     * setting state
     * @param s state
     */
    public void setState(ClientState s) {
        gameState = s;
    }

    /**
     * getting state
     * @return clientstate
     */
    public ClientState getState() {
        return gameState;
    }

    /**
     * move
     * @param tokenizer is a tokenizer
     */
    public void move(StringTokenizer tokenizer) {
         String tempCmd;
         tempCmd = tokenizer.nextToken();
         Military ship = gameState.getUnitFromId(Integer.parseInt(tokenizer
                 .nextToken()));

         Planet world = gameState.getPlanetFromId(Integer.parseInt(tokenizer
                 .nextToken()));

         if (tempCmd.compareTo("FLIGHT") == 0) {
             ((Army) ship).moveFlight(world);
             stratagemMain.getSound().play(2);
         } else if (tempCmd.compareTo("SHUTTLE") == 0) {
             ((Army) ship).moveShuttle(world);
         } else if (tempCmd.compareTo("WORMTRANS") == 0) {
             ((Army) ship).moveWormHoleTransport(world);
             stratagemMain.getSound().play(4);
         } else if (tempCmd.compareTo("WORM") == 0) {
             ((Navy) ship).moveWormHole(world);
             stratagemMain.getSound().play(4);
         }
         if (!gameState.stillProtected(ship.getCurrentPlanet(), ship)) {
            ship.getCurrentPlanet().setFriendlyUnits(false);
        }
    }

    /**
     * recruit
     * @param tokenizer is a command
     */
    public void recruit(StringTokenizer tokenizer) {
        int cityId = Integer.parseInt(tokenizer.nextToken());
        Planet p = gameState.getPlanetFromId(cityId);

        String type = tokenizer.nextToken();
        int size = Integer.parseInt(tokenizer.nextToken());
        int id = Integer.parseInt(tokenizer.nextToken());

        if (type.compareTo("DREADNAUGHT") == 0) {
            Dreadnaught d = new Dreadnaught(p, id);
            d.setCurrentPlanet(p);
            d.setNextPlanet(p);
            d.setSize(size);
            d.setCommand(this);
            gameState.addUnit(d);
        } else if (type.compareTo("FRIGATE") == 0) {
            Frigate d = new Frigate(p, id);
            d.setCurrentPlanet(p);
            d.setNextPlanet(p);
            d.setSize(size);
            d.setCommand(this);
            gameState.addUnit(d);
        } else if (type.compareTo("OVERLORD") == 0) {
            Overlord d = new Overlord(p, id);
            d.setCurrentPlanet(p);
            d.setDestPlanet(p);
            d.setSize(size);
            d.setCommand(this);
            gameState.addUnit(d);
        } else if (type.compareTo("RAGNAROK") == 0) {
            Ragnarok d = new Ragnarok(p, id);
            d.setCurrentPlanet(p);
            d.setDestPlanet(p);
            d.setSize(size);
            d.setCommand(this);
            gameState.addUnit(d);
        } else if (type.compareTo("SHADE") == 0) {
            Shade d = new Shade(p, id);
            d.setCurrentPlanet(p);
            d.setDestPlanet(p);
            d.setSize(size);
            d.setCommand(this);
            gameState.addUnit(d);
        } else if (type.compareTo("RAPTOR") == 0) {
            Raptor d = new Raptor(p, id);
            d.setCurrentPlanet(p);
            d.setDestPlanet(p);
            d.setSize(size);
            d.setCommand(this);
            gameState.addUnit(d);
        }
    }

    /**
     * build
     * @param tokenizer is a command
     */
    public void build(StringTokenizer tokenizer) {
        int cityId = Integer.parseInt(tokenizer.nextToken());
        Planet p = gameState.getPlanetFromId(cityId);
        String building = tokenizer.nextToken();

        if (building.compareTo("SHIPYARD") == 0) {
            p.addBuilding(new Shipyard());
            p.getOwner().setMoney(
                    p.getOwner().getMoney() - Shipyard.COSTMONEY);
        } else if (building.compareTo("TRAININGACADEMY") == 0) {
            p.addBuilding(new TrainingAcademy());
            p.getOwner().setMoney(
                    p.getOwner().getMoney() - TrainingAcademy.COSTMONEY);
        } else if (building.compareTo("WARFACTORY") == 0) {
            p.addBuilding(new WarFactory());
            p.getOwner().setMoney(
                    p.getOwner().getMoney() - WarFactory.COSTMONEY);
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
     * gets the rest of tokenizer
     * @param t is string tokenizer
     * @return a string
     */
    public String getRest(StringTokenizer t) {
        String ot = "";
        while (t.hasMoreTokens()) {
            ot = ot + " " + t.nextToken();
        }
        return ot;
    }

}