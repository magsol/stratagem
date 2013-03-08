/*
 * Created on Oct 30, 2004
 */
package edu.cs2335.tsunami.stratagem.net;

import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

import edu.cs2335.tsunami.stratagem.kernel.Army;
import edu.cs2335.tsunami.stratagem.kernel.Dreadnaught;
import edu.cs2335.tsunami.stratagem.kernel.Frigate;
import edu.cs2335.tsunami.stratagem.kernel.Navy;
import edu.cs2335.tsunami.stratagem.kernel.Overlord;
import edu.cs2335.tsunami.stratagem.kernel.Player;
import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.kernel.Military;
import edu.cs2335.tsunami.stratagem.kernel.Ragnarok;
import edu.cs2335.tsunami.stratagem.kernel.Raptor;
import edu.cs2335.tsunami.stratagem.kernel.Shade;
import edu.cs2335.tsunami.stratagem.kernel.Shipyard;
import edu.cs2335.tsunami.stratagem.kernel.TrainingAcademy;
import edu.cs2335.tsunami.stratagem.kernel.WarFactory;

/**
 * @author <a href="mailto:jwei@cc.gatech.edu">Jeff Wei </a>
 */
public class ServerState extends State implements Serializable {

    /** for implementing serializable?... */
    public static final long serialVersionUID = 25;

    /** Id generation */
    private static int playerIDGen = 0;

    /**military idgen*/
    private static int militaryIDGen = 0;

    /**orders processed*/
    private LinkedList ordersProcessed;

    /** store of names*/
    private static String[] names = {"New Washington", "Tim's Homeworld",
            "Shannon's Homeworld", "Chris's Homeworld", "New Atlanta",
            "New LA", "Terrell", "Plato", "Locke", "Thoreau", "Descartes",
            "Socrates", "Gregan", "Wilkinson", "Thorne", "Australia",
            "America", "Iraq", "New Zealand", "South Africa", "England",
            "Germany", "France", "Ireland", "China", "Philippines"};

    /**
     * constructor
     *
     */
    public ServerState() {
        super();
        ordersProcessed = new LinkedList();
        //defaultMap();
        randomMap(10);
        this.setAdjacents();
    }

    /**
     * checking moveflight
     * @param unitId id
     * @param dest planet
     * @return true or false
     */
    public boolean checkMoveFlight(String unitId, String dest) {
        //System.out.println("Number of Units: " + this.getNumUnits());
        Military ship = getUnitFromId(Integer.parseInt(unitId));

        Planet start = ship.getCurrentPlanet();
        Planet end = getPlanetFromId(Integer.parseInt(dest));

        if (ship == null || end == null) {
            //System.out.println("ship or dest is null");
            return false;
        }
        //System.out.println("StartName: " + start.getName());
        LinkedList adj = start.getAdjacents();
        for (int i = 0; i < adj.size(); i++) {
            if (end.equals((Planet) adj.get(i))) {
                ship.setNextPlanet(end);
                if (!stillProtected(start, ship)) {
                    end.setFriendlyUnits(false);
                }
                //System.out.println("returning true");
                return true;
            }
            //System.out.println(((Planet) adj.get(i)).getName());
        }
        //System.out.println("Not Adjacent");
        return false;

    }

    /**
     * shuttle
     * @param unitId id
     * @param dest planet
     * @return bool
     */
    public boolean checkMoveShuttle(String unitId, String dest) {
        Military ship = getUnitFromId(Integer.parseInt(unitId));
        Planet start = ship.getCurrentPlanet();
        Planet end = getPlanetFromId(Integer.parseInt(dest));

        if (ship instanceof Navy) {
            return false;
        }

        if (ship == null || end == null) {
            //System.out.println("ship or dest is null");
            return false;
        }

        if (ship.getOwner().getMoney() < 200) {
            //System.out.println("Not enough money to perform move!");
            return false;
        }
        if (start.checkPath(end) && end.getOwner().equals(ship.getOwner())) {
            ship.getOwner().setMoney(ship.getOwner().getMoney() - 200);
            ship.setCurrentPlanet(end);
            if (!stillProtected(start, ship)) {
                end.setFriendlyUnits(false);
            }
            ship.setLocation(end.getLocation());
            return true;
        }

        return false;
    }

    /**
     * army wormhole
     * @param unitId id
     * @param dest planet
     * @return bool
     */
    public boolean checkMoveWormHoleTrans(String unitId, String dest) {
        Military ship = getUnitFromId(Integer.parseInt(unitId));
        Planet start = ship.getCurrentPlanet();
        Planet end = getPlanetFromId(Integer.parseInt(dest));

        if (ship == null || end == null) {
            //System.out.println("ship or dest is null");
            return false;
        }

        if (ship.getOwner().getMoney() < 500) {
            //System.out.println("Not enough money to perform move!");
            return false;
        }

        if (!(ship instanceof Army)) {
            return false;
        }

        if (end.getIsWormHoleAccessible()
                && ship.getCurrentPlanet().getIsWormHoleAccessible()) {
            ship.setArmyInWormHole(true);
            ship.setSpeed(ship.getSpeed() + 4);
            ship.getOwner().setMoney(ship.getOwner().getMoney() - 500);
            ship.setNextPlanet(end);
            if (!stillProtected(start, ship)) {
                end.setFriendlyUnits(false);
            }
            return true;
        }
        return false;
    }

    /**
     * worm
     * @param unitId id
     * @param dest planet
     * @return bool
     */
    public boolean checkMoveWormHole(String unitId, String dest) {
        Military ship = getUnitFromId(Integer.parseInt(unitId));
        Planet start = ship.getCurrentPlanet();
        Planet end = getPlanetFromId(Integer.parseInt(dest));

        if (ship == null || end == null) {
            //System.out.println("ship or dest is null");
            return false;
        }

        if (start.getIsWormHoleAccessible() && end.getIsWormHoleAccessible()
                && end.getOwner().equals(ship.getOwner())
                && (ship instanceof Navy)) {
            ship.setNextPlanet(end);
            if (!stillProtected(start, ship)) {
                end.setFriendlyUnits(false);
            }
            return true;
        }
        return false;
    }

    /**
     * bombard
     * @param unitId id
     * @param target planet
     */
    public void checkBombard(String unitId, String target) {
        Military ship = getUnitFromId(Integer.parseInt(unitId));
        Planet end = getPlanetFromId(Integer.parseInt(target));
        ship.bombard(end);
        ship.setIsBombarding(false);
        ship.setNextPlanet(ship.getCurrentPlanet());
    }

    /**
     * raid
     * @param unitId id
     * @param target planet
     * @return bool
     */
    public boolean checkRaid(String unitId, String target) {
        Military ship = getUnitFromId(Integer.parseInt(unitId));
        Planet end = getPlanetFromId(Integer.parseInt(target));

        if (ship == null || target == null) {
            //System.out.println("ship or dest is null");
            return false;
        }

        if (ship.canRaid()) {
            ship.raid(end);
            return true;
        }
        return false;
    }

    /**
     * is bombard
     * @param unitId id
     * @param planetId planet
     * @param bool boolean
     * @return bool
     */
    public boolean checkIsBombarding
    (String unitId, String planetId, String bool) {
        Military ship = getUnitFromId(Integer.parseInt(unitId));
        if (ship instanceof Army) {
            if (ship.canBombard() && bool.equals("TRUE")) {
                if (checkMoveFlight(unitId, planetId)) {
                    ship.setIsBombarding(true);
                    return true;
                } else {
                    return false;
                }
            }
        } else if (ship instanceof Navy) {
            if (ship.canBombard() && bool.equals("TRUE")) {
                if (checkMoveWormHole(unitId, planetId)) {
                    ship.setIsBombarding(true);
                    return true;
                } else {
                    return false;
                }
            }
        }
        ship.setIsBombarding(false);
        return false;
    }

    /**
     * raid
     * @param unitId id
     * @param planetId planet
     * @param bool boolean
     * @param daysToRaid days
     * @return bool
     */
    public boolean checkIsRaiding(String unitId, String planetId, String bool,
            String daysToRaid) {
        Military ship = getUnitFromId(Integer.parseInt(unitId));
        int daysR = Integer.parseInt(daysToRaid);
        if (ship instanceof Army) {
            if (ship.canRaid() && bool.equals("TRUE")) {
                if (checkMoveFlight(unitId, planetId)) {
                    ship.setIsRaiding(true);
                    ship.setDaysToRaid(daysR);
                    return true;
                } else {
                    return false;
                }
            }
        } else if (ship instanceof Navy) {
            if (ship.canRaid() && bool.equals("TRUE")) {
                if (checkMoveWormHole(unitId, planetId)) {
                    ship.setIsRaiding(true);
                    ship.setDaysToRaid(daysR);
                    return true;
                } else {
                    return false;
                }
            }
        }
        ship.setIsRaiding(false);
        //ship.setNextPlanet(ship.getCurrentPlanet());
        return false;
    }

    /**
     * train
     * @param unitId id
     * @return bool
     */
    public boolean checkTrain(String unitId) {
        Military ship = getUnitFromId(Integer.parseInt(unitId));
        if (ship.getOwner().getMoney() < 100) {
            //System.out.println("Not enough money to train!");
            return false;
        } else {
            ship.train();
            return true;
        }
    }

    /**
     * build
     * @param cityId id
     * @param building type
     * @return bool
     */
    public boolean checkBuild(String cityId, String building) {
        Planet p = getPlanetFromId(Integer.parseInt(cityId));

        if (!p.hasBuilding(building)) {
            if (building.compareTo("SHIPYARD") == 0) {
                if (Shipyard.COSTMONEY <= p.getOwner().getMoney()) {
                    p.addBuilding(new Shipyard());
                    p.getOwner().setMoney(
                            p.getOwner().getMoney() - Shipyard.COSTMONEY);
                    return true;
                }
            } else if (building.compareTo("TRAININGACADEMY") == 0) {
                if (TrainingAcademy.COSTMONEY <= p.getOwner().getMoney()) {
                    p.addBuilding(new TrainingAcademy());
                    p.getOwner()
                            .setMoney(
                                    p.getOwner().getMoney()
                                            - TrainingAcademy.COSTMONEY);
                    return true;
                }
            } else if (building.compareTo("WARFACTORY") == 0) {
                if (WarFactory.COSTMONEY <= p.getOwner().getMoney()) {
                    p.addBuilding(new WarFactory());
                    p.getOwner().setMoney(
                            p.getOwner().getMoney() - WarFactory.COSTMONEY);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * fortify
     * @param cityId id
     * @return bool
     */
    public boolean checkFortify(String cityId) {
        Planet p = getPlanetFromId(Integer.parseInt(cityId));
        if ((p.getOwner().getMoney() >= 100) && p.getFortification() < 3) {
            p.fortify();
            return true;
        }
        return false;
    }

    /**
     * makecapital
     * @param cityId id
     * @return bool
     */
    public boolean checkMakeCapital(String cityId) {
        int id = Integer.parseInt(cityId);
        Planet p = getPlanetFromId(id);

        if ((p.getOwner().getMoney() >= 10000) && !p.getIsCapitol()) {

            for (int i = 0; i < this.getNumPlanets(); i++) {
                if (this.getPlanet(i).getIsCapitol()) {
                    this.getPlanet(i).setIsCapitol(false);
                    break;
                }
            }
            p.setIsCapitol(true);
            return true;
        }
        return false;
    }

    /**
     * recruit
     * @param cityId id
     * @param type of unit
     * @param number size
     * @return bool
     */
    public boolean checkRecruit(String cityId, String type, String number) {
        Planet p = getPlanetFromId(Integer.parseInt(cityId));
        int size = Integer.parseInt(number);
        int costMoney;
        int costSteel;

            if (type.compareTo("DREADNAUGHT") == 0
                    && p.hasBuilding("SHIPYARD")) {
                costMoney = Dreadnaught.COSTMONEY * size;
                costSteel = Dreadnaught.COSTSTEEL * size;
                if (costMoney <= p.getOwner().getMoney()
                        && costSteel <= p.getOwner().getSteel()) {
                    Dreadnaught d = new Dreadnaught(p, getMilitaryID());
                    d.setCurrentPlanet(p);
                    d.setDestPlanet(p);
                    d.setSize(size);
                    addUnit(d);
                    return true;
                }
            } else if (type.compareTo("SHADE") == 0
                    && p.hasBuilding("SHIPYARD")) {
                costMoney = Shade.COSTMONEY * size;
                costSteel = Shade.COSTSTEEL * size;
                if (costMoney <= p.getOwner().getMoney()
                        && costSteel <= p.getOwner().getSteel()) {
                    Shade d = new Shade(p, getMilitaryID());
                    d.setCurrentPlanet(p);
                    d.setDestPlanet(p);
                    d.setSize(size);
                    addUnit(d);
                    return true;
                }
            } else if (type.compareTo("OVERLORD") == 0
                    && p.hasBuilding("SHIPYARD")) {
                costMoney = Overlord.COSTMONEY * size;
                costSteel = Overlord.COSTSTEEL * size;
                if (costMoney <= p.getOwner().getMoney()
                        && costSteel <= p.getOwner().getSteel()) {
                    Overlord d = new Overlord(p, getMilitaryID());
                    d.setCurrentPlanet(p);
                    d.setDestPlanet(p);
                    d.setSize(size);
                    addUnit(d);
                    return true;
                }
            } else if (type.compareTo("RAGNAROK") == 0
                    && p.hasBuilding("WARFACTORY")) {
                costMoney = Ragnarok.COSTMONEY * size;
                costSteel = Ragnarok.COSTSTEEL * size;
                if (costMoney <= p.getOwner().getMoney()
                        && costSteel <= p.getOwner().getSteel()) {
                    Ragnarok d = new Ragnarok(p, getMilitaryID());
                    d.setCurrentPlanet(p);
                    d.setDestPlanet(p);
                    d.setSize(size);
                    addUnit(d);
                    return true;
                }
            } else if (type.compareTo("FRIGATE") == 0
                    && p.hasBuilding("WARFACTORY")) {
                //System.out.println("found Frigate");
                costMoney = Frigate.COSTMONEY * size;
                costSteel = Frigate.COSTSTEEL * size;
                if (costMoney <= p.getOwner().getMoney()
                        && costSteel <= p.getOwner().getSteel()) {
                    //System.out.println("creating Frigate");
                    Frigate d = new Frigate(p, getMilitaryID());
                    d.setCurrentPlanet(p);
                    d.setDestPlanet(p);
                    d.setSize(size);
                    addUnit(d);
                    return true;
                }
            } else if (type.compareTo("RAPTOR") == 0
                    && p.hasBuilding("TRAININGACADEMY")) {
                costMoney = Raptor.COSTMONEY * size;
                costSteel = Raptor.COSTSTEEL * size;
                if (costMoney <= p.getOwner().getMoney()
                        && costSteel <= p.getOwner().getSteel()) {
                    Raptor d = new Raptor(p, getMilitaryID());
                    //System.out.println("Military ID: "
                           // + returnCurrentMilitaryID());
                    d.setCurrentPlanet(p);
                    d.setDestPlanet(p);
                    d.setSize(size);
                    addUnit(d);
                    return true;
                }
            }

        return false;
    }

    /**
     * start planet
     * @param usrId id
     * @param planetId planet
     * @return bool
     */
    public boolean checkStartCity(int usrId, String planetId) {
        Planet p = getPlanetFromId(Integer.parseInt(planetId));
        Player currentPlayer = getPlayerFromID(usrId);
        if (p.getOwner() == null) {
            for (int i = 0; i < getNumPlanets(); i++) {
                Planet currentPlanet = getPlanet(i);
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
            return true;
        }
        return false;
    }

    /**
     * defaultmap
     *
     */
    public void defaultMap() {
        for (int i = 0; i < 11; i++) {
            Planet p = new Planet("p" + i);
            p.setPopulation(5000);
            p.setMorale(50);
            p.setAllegiance(50);
            this.addPlanet(p);
        }

        this.getPlanet(0).setLocation(new Point(50, 50));
        this.getPlanet(1).setLocation(new Point(200, 250));
        this.getPlanet(2).setLocation(new Point(50, 500));
        this.getPlanet(3).setLocation(new Point(700, 300));
        this.getPlanet(4).setLocation(new Point(1000, 900));
        this.getPlanet(5).setLocation(new Point(1200, 400));
        this.getPlanet(6).setLocation(new Point(1150, 100));
        this.getPlanet(7).setLocation(new Point(1000, 600));
        this.getPlanet(8).setLocation(new Point(750, 700));
        this.getPlanet(9).setLocation(new Point(500, 500));
        this.getPlanet(10).setLocation(new Point(800, 800));

        this.getPlanet(1).setIsWormHoleAccessible(true);
        this.getPlanet(5).setIsWormHoleAccessible(true);
    }

    /**
     * random map
     * @param num number of planets
     */
    public final void randomMap(int num) {
        long seed = System.currentTimeMillis();
        Random r = new Random(seed);
        int nPlanets = num;
        int xVal, yVal;
        int j = 0;
        boolean searching = false;
        Planet p;
        for (int i = 0; i < nPlanets; i++) {

            if (i < names.length) {
                p = new Planet(names[i]);
            } else {
                p = new Planet(names[names.length - 1]);
            }
            p.setPopulation(5000);
            p.setMorale(50);
            p.setAllegiance(50);

            if (j == 2) {
                p.setIsWormHoleAccessible(true);
                j = 0;
            } else {
                j++;
            }

            p.setLocation(new Point(0, 0));
            do {
                xVal = (int) (r.nextDouble() * 1200);
                yVal = (int) (r.nextDouble() * 900);
                p.setLocation(xVal, yVal);

                for (int k = 0; getPlanet(k) != null; k++) {
                    Planet tempP = getPlanet(k);
                    if (p.getLocation().distance(tempP.getLocation()) < 250) {
                        searching = true;
                        break;
                    }
                    searching = false;
                }
            } while (searching);
            this.addPlanet(p);
            searching = true;
        }
    }

    /**
     * player id gen setting
     * @param i set to
     */
    public void setPlayerIdGen(int i) {
        playerIDGen = i;
        //System.out.println("Player Id is set to: " + i);
    }

    /**
     * get player id gen
     * @return id
     */
    public int getPlayerIdGen() {
        return playerIDGen;
    }

    /**
     * getMilitary id
     * @return integer
     */
    public int getMilitaryID() {
        int temp = militaryIDGen;
        militaryIDGen++;
        return temp;
    }

    /**
     * increment
     */
    public void incrementMilitaryID() {
        militaryIDGen++;
    }

    /**
     * return id
     * @return id gen
     */
    public int returnCurrentMilitaryID() {
        return militaryIDGen;
    }

    /**
     * get orders
     * @return orders
     */
    public LinkedList getOrdersProcessed() {
        return ordersProcessed;
    }

    /**
     * addorders
     * @param order string
     */
    public void addOrdersProcessed(String order) {
        ordersProcessed.add(order);
    }

    /**
     * setting orders
     * @param orders string
     */
    public void setOrdersProcessed(LinkedList orders) {
        ordersProcessed = orders;
    }


    
}