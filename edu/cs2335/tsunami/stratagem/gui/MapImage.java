/*
 * Created on Nov 1, 2004
 *
 */
package edu.cs2335.tsunami.stratagem.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
// import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.LinkedList;

import edu.cs2335.tsunami.stratagem.kernel.Army;
import edu.cs2335.tsunami.stratagem.kernel.Frigate;
import edu.cs2335.tsunami.stratagem.kernel.Military;
//import edu.cs2335.tsunami.stratagem.kernel.Navy;
import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.kernel.Ragnarok;
import edu.cs2335.tsunami.stratagem.kernel.Raptor;
import edu.cs2335.tsunami.stratagem.net.ClientState;

/**
 * @author chrislg
 */
public class MapImage {

    /** Map image that planets, armies and wormholes are displayed on */
    private BufferedImage mapImage;

    /** ImageLoader used to load & create accelerated BufferedImages */
    private ImageLoader il;

    /** Main gui for callback */
    private StratagemGUI gui;

    /** Image storage class */
    private StoredImages store;

    /** Actual map drawn onto */
    private BufferedImage map;

    /** Temp map with background and lines only */
    private BufferedImage tempMap;

    /** Volatile map image */
    private VolatileImage volatileMap;

    /** Graphics for the map. Used to draw directly on the image */
    private Graphics2D g2d;

    /** Game state. A single state that is used by all classes. */
    private ClientState state;

    /** Zoom level */
    private double zoom;

    /** Zoomed X dimension */
    private int zoomX;

    /** Zoomed Y dimension */
    private int zoomY;

    /** Outline for planets & units */
    private Color outline = Color.gray;

    /** Color of the chat text */
    private Color chatColor = new Color(120, 100, 180);

    /** Small font */
    private Font smallFont = new Font("Arial", Font.BOLD, 12);
    
    /** Large font */
    private Font largeFont = new Font("Arial", Font.BOLD, 18);

    /** Draw city stats */
    private boolean planetStats = false;

    /** Draw unit stats */
    private boolean unitStats = false;

    /**
     * Constructor
     * 
     * @param state
     *            Game state
     * @param il
     *            ImageLoader
     * @param store
     *            StoredImages
     * @param gui is the gui
     */
    public MapImage(ClientState state, ImageLoader il, StoredImages store,
            StratagemGUI gui) {
        this.gui = gui;
        this.il = il;
        this.state = state;
        this.store = store;
        setMap("edu/cs2335/tsunami/stratagem/util/stars.jpg");

        tempMap = il.createImage(1280, 1024);
        g2d = tempMap.createGraphics();
        g2d.drawImage(mapImage, null, 0, 0);
        drawLines();
    }

    /**
     * Sets background image.
     * 
     * @param path
     *            Path to map background
     */
    public final void setMap(String path) {
        mapImage = il.loadImage(path);
    }

    /**
     * Sets zoom level
     * 
     * @param newZoom
     *            New zoom level
     */
    public void setZoom(double newZoom) {
        this.zoom = newZoom;

        zoomX = (int) (zoom * 1280);
        zoomY = (int) (zoom * 1024);
    }

    /**
     * Draws a raptor on an oval of the owner's color
     * 
     * @param unit
     *            Unit drawn
     * @param x
     *            X to draw at
     * @param y
     *            Y to draw at
     */
    private void drawUnit(Military unit, int x, int y) {
        if (unit instanceof Army) {
            if (unit instanceof Raptor) {
                g2d.drawImage(store.getRaptor(unit.getOwner().getColor()),
                        null, x - 25, y - 25);
            } else if (unit instanceof Frigate) {
                g2d.drawImage(store.getFrigate(unit.getOwner().getColor()),
                        null, x - 30, y - 30);
            } else if (unit instanceof Ragnarok) {
                g2d.drawImage(store.getRagnarok(unit.getOwner().getColor()),
                        null, x - 30, y - 30);
            }
        }
    }

    /**
     * Draws units on the map
     *  
     */
    public void drawUnits() {
        int i = 0;
        Military temp;
        if (unitStats) {
            g2d.setFont(smallFont);
            g2d.setColor(chatColor);
            while (i < state.getNumUnits()) {
                //Draw units with stats
                temp = state.getUnit(i);
                if (temp != null) {
                    temp.moveNext();
                drawUnit(temp, temp.getXPosition(), temp.getYPosition());
                g2d.drawString("Size: " + temp.getSize(),
                        temp.getXPosition() + 30, temp.getYPosition() - 20);
                g2d.drawString("Morale :" + temp.getMorale(), temp
                        .getXPosition() + 30, temp.getYPosition() + 0);
                g2d.drawString("Exp: " + temp.getExperience(), temp
                        .getXPosition() + 30, temp.getYPosition() + 20);
                g2d.drawString("Food: " + temp.getFoodSupply(), temp
                        .getXPosition() + 30, temp.getYPosition() + 40);
                i++;
                if (temp.getOwner().getPlayerID() 
                        != gui.getPlayer().getPlayerID()
                        && !temp.getIsBattling()) {
                    checkBattle(temp);
                }
                } else {
                     state.removeUnit(i);
                }
            }
        } else {
            while (i < state.getNumUnits()) {
                temp = state.getUnit(i);
                if (temp != null) {
                    temp.moveNext();
                drawUnit(temp, temp.getXPosition(), temp.getYPosition());
                i++;
                if (temp.getOwner().getPlayerID() 
                        != gui.getPlayer().getPlayerID()
                        && !temp.getIsBattling()) {
                    checkBattle(temp);
                }
                } else {
                    state.removeUnit(i);
                }
            }
        }
    }

    /**
     * Draws planets
     *  
     */
    private void drawPlanets() {
        int num = state.getNumPlanets();
        int i = 0;
        Planet temp;
        while (i < num) {

            temp = state.getPlanet(i);
            if (temp.getOwner() != null) {
                outline = temp.getOwner().getColor();
            }

            switch (temp.getPlanetType()) {
            case 0:
                if (temp.getOwner() != null) {
                    g2d.drawImage(store.getPlanet1(temp.getOwner().getColor()),
                            null, temp.getXPosition() - 50,
                            temp.getYPosition() - 50);
                } else {
                    g2d.drawImage(store.getPlanet1Image(), null, temp
                            .getXPosition() - 50, temp.getYPosition() - 50);
                }
                break;
            case 1:
                if (temp.getOwner() != null) {
                    g2d.drawImage(store.getPlanet2(temp.getOwner().getColor()),
                            null, temp.getXPosition() - 55,
                            temp.getYPosition() - 55);
                } else {
                    g2d.drawImage(store.getPlanet2Image(), null, temp
                            .getXPosition() - 55, temp.getYPosition() - 55);
                }
                break;
            case 2:
                if (temp.getOwner() != null) {
                    g2d.drawImage(store.getPlanet3(temp.getOwner().getColor()),
                            null, temp.getXPosition() - 60,
                            temp.getYPosition() - 60);
                } else {
                    g2d.drawImage(store.getPlanet3Image(), null, temp
                            .getXPosition() - 60, temp.getYPosition() - 60);
                }
                break;
            case 3:
                if (temp.getOwner() != null) {
                    g2d.drawImage(store.getPlanet4(temp.getOwner().getColor()),
                            null, temp.getXPosition() - 65,
                            temp.getYPosition() - 65);
                } else {
                    g2d.drawImage(store.getPlanet4Image(), null, temp
                            .getXPosition() - 65, temp.getYPosition() - 65);
                }
                break;
            default:
                if (temp.getOwner() != null) {
                    g2d.drawImage(store.getPlanet5(temp.getOwner().getColor()),
                            null, temp.getXPosition() - 75,
                            temp.getYPosition() - 75);
                } else {
                    g2d.drawImage(store.getPlanet5Image(), null, state
                            .getPlanet(i).getXPosition() - 75, temp
                            .getYPosition() - 75);
                }
            }
            g2d.setColor(chatColor);
            g2d.setFont(largeFont);
            if (temp.getIsCapitol()) {
                g2d.drawString("Capital",
                     temp.getXPosition() + 20, temp.getYPosition() - 55);
            }
            if (planetStats) {
                g2d.setFont(smallFont);
                g2d.drawString("Name: " + temp.getName(), temp.getXPosition(),
                        temp.getYPosition() - 40);
                g2d.drawString("Fortification: " + temp.getFortification(),
                     temp
                        .getXPosition(), temp.getYPosition() - 20);
                g2d.drawString("Tax Rate: " + temp.giveMoney(), temp
                        .getXPosition(), temp.getYPosition());
                g2d.drawString("Food Supply: " + temp.giveFood(), temp
                        .getXPosition(), temp.getYPosition() + 20);
                g2d.drawString("Oil Supply: " + temp.giveOil(), temp
                        .getXPosition(), temp.getYPosition() + 40);
                g2d.drawString("Steel Supply: " + temp.giveSteel(), temp
                        .getXPosition(), temp.getYPosition() + 60);

            }
            i++;
        }

    }

    /**
     * Draws the paths connecting the planets
     *  
     */
    private void drawLines() {
        int x;
        int y;
        LinkedList planets;
        Planet temp;
        Planet a;
        int num = state.getNumPlanets();
        int i = 0;
        int u = 0;

        while (i < num) {
            g2d.setColor(Color.lightGray);
            temp = state.getPlanet(i);
            x = temp.getXPosition();
            y = temp.getYPosition();
            planets = temp.getAdjacents();

            while (u < planets.size()) {
                a = (Planet) planets.get(u);
                //System.out.println(a);
                g2d.drawLine(x, y, a.getXPosition(), a.getYPosition());
                u++;
            }
            u = 0;
            i++;
        }
    }

    /**
     * draws a map
     * @return a volitile image
     */
    public VolatileImage drawMap() {
        for (int i = 0; i < 50; i++) {
            if (volatileMap != null) {
                g2d.drawImage(tempMap, null, 0, 0);
                drawPlanets();
                drawUnits();
                if (!volatileMap.contentsLost()) {
                    return volatileMap;
                }
            } else {
                // Create the volatile image
                volatileMap = il.createVolatile(1280, 1024);
                g2d = volatileMap.createGraphics();
            }
            switch (volatileMap.validate(g2d.getDeviceConfiguration())) {
            case VolatileImage.IMAGE_OK:
                break;
            case VolatileImage.IMAGE_INCOMPATIBLE:
                volatileMap.flush();
                volatileMap = il.createVolatile(1280, 1024);
            case VolatileImage.IMAGE_RESTORED:
                g2d = volatileMap.createGraphics();
                break;
            default:
                volatileMap.flush();
                volatileMap = il.createVolatile(1280, 1024);
                g2d = volatileMap.createGraphics();
                break;
            }
        }
        return volatileMap;
    }

    /**
     * Draws current state onto the map
     * 
     * @return map BufferedImage of fully constructed map
     */
    public BufferedImage drawBufferedMap() {
        if ((map == null) || (g2d == null)) {
            //System.out.println("Should only see this once");
            tempMap = il.createImage(1280, 1024);
            g2d = tempMap.createGraphics();
            g2d.drawImage(mapImage, null, 0, 0);
            drawLines();

            map = il.createImage(1280, 1024);
            g2d = map.createGraphics();
        }
        g2d.drawImage(tempMap, null, 0, 0);
        drawPlanets();
        drawUnits();

        //Left text
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("40, 100", 40, 100);
        g2d.drawString("60, 300", 60, 300);
        g2d.drawString("80, 500", 80, 500);
        g2d.drawString("100, 700", 100, 700);

        //Diagonal text
        g2d.drawString("100, 70", 100, 70);
        g2d.drawString("400, 300", 400, 300);
        g2d.drawString("800, 600", 800, 600);
        g2d.drawString("980, 720", 900, 720);
        g2d.drawString("1100, 1000", 1100, 1000);

        return map;
    }

    /**
     * Checks for a battle
     * 
     * @param a
     *            military unit
     */
    public void checkBattle(Military a) {
        for (int j = 0; j < state.getNumUnits(); j++) {
            Military d = state.getUnit(j);
                if ((a.getLocation().distance(d.getLocation()) < 20)
                        && !a.equals(d)
                        && (a.getOwner().getPlayerID() != d.getOwner()
                                .getPlayerID())
                        && !d.getIsRaiding()
                        && !a.getIsRaiding()
                        && d.getSize() != 0
                        && a.getSize() != 0) {
                    gui.getCommand().battle(a.getMilitaryID(),
                            d.getMilitaryID());
                    a.setIsBattling(true);
            }
        }
    }

    /**
     * Sets unit stats setting
     */
    public void setUnitStats() {
        if (unitStats) {
            unitStats = false;
        } else {
            unitStats = true;
        }
    }

    /**
     * Sets planet stats setting
     */
    public void setPlanetStats() {
        if (planetStats) {
            planetStats = false;
        } else {
            planetStats = true;
        }
    }

}