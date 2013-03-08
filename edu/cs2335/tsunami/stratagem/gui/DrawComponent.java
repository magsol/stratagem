/*
 * Created on Nov 18, 2004
 *
 */
package edu.cs2335.tsunami.stratagem.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import edu.cs2335.tsunami.stratagem.kernel.Military;
import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.net.ClientState;

/**
 * @author chrislg
 */
public class DrawComponent {

    /** Image Loader */
    private ImageLoader il;

    /** Stored Images */
    private StoredImages store;

    /** Gamestate */
    private ClientState state;

    /** Player color */
    private Color playerColor;

    /** Panel color */
    private Color panelColor = new Color(60, 60, 80);

    /** Small font */
    private Font smallFont = new Font("Arial", Font.BOLD, 12);

    /** Large font */
    private Font largeFont = new Font("Arial", Font.BOLD, 18);

    /** Selected Planet */
    private Planet selectedPlanet;

    /** Selected Unit */
    private int selectedUnit = -1;

    /** Units to display in box */
    private LinkedList unitBox = new LinkedList();

    /** Number of units to scroll on panel */
    private int unitScroll;

    /** Counter for how often to check unit location status */
    private int unitCheck;

    /** Graphics used to draw onto the images */
    private Graphics2D g2d;

    /** Graphics used to draw battle panel */
    private Graphics2D battleGraphics;

    /** Graphics for minimap */
    private Graphics2D miniGraphics;

    /** Graphics for panel */
    private Graphics2D panelGraphics;

    /** Battle image */
    private BufferedImage battle;

    /** Temporary Battle image */
    private BufferedImage battleTemp;

    /** Minimap */
    private BufferedImage minimap;

    /** Redraw count */
    private int redraw;

    /** Main panel */
    private BufferedImage mainPanel;

    /** Matchmaking screen */
    private BufferedImage matchmaking;

    /** Stats box */
    private BufferedImage stats;

    /** Entry box */
    private BufferedImage entryBox;

    /**
     * constructor
     * @param store of pics
     * @param il imag loader
     * @param color color
     */
    public DrawComponent(StoredImages store, ImageLoader il, Color color) {
        this.il = il;
        this.store = store;
        playerColor = color;

        //Create stats image
        stats = il.createImage(130, 50);
        g2d = stats.createGraphics();
        g2d.setColor(panelColor);
        g2d.fillRect(0, 0, 130, 50);
        g2d.setColor(Color.black);
        g2d.drawRect(3, 22, 58, 25);
        g2d.drawRect(65, 22, 58, 25);
        g2d.drawString("Unit", 18, 32);
        g2d.drawString("Stats", 16, 46);
        g2d.drawString("Planet", 75, 32);
        g2d.drawString("Stats", 77, 46);

        //Create matchmaking image
        matchmaking = il.createImage(800, 600);
        g2d = matchmaking.createGraphics();
        drawMatchmaking();

        //Create battle image
        battleTemp = il.createImage(300, 200);
        g2d = battleTemp.createGraphics();
        g2d.setColor(panelColor);
        g2d.fillRect(0, 0, 300, 200);
        g2d.setColor(Color.black);
        battle = il.createImage(300, 200);
        battleGraphics = battle.createGraphics();
        battleGraphics.drawImage(battleTemp, null, 0, 0);

        //Create mainPanel
        mainPanel = il.createImage(130, 600);
        panelGraphics = mainPanel.createGraphics();

        //Create minimap
        minimap = il.createImage(128, 102);
        miniGraphics = minimap.createGraphics();

        //Draw entry box
        entryBox = il.createImage(300, 185);
        g2d = entryBox.createGraphics();
        g2d.setColor(panelColor);
        g2d.fillRect(0, 0, 300, 185);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, 300, 185);

        //Player name box
        g2d.setColor(Color.lightGray);
        g2d.fillRect(30, 60, 250, 30);
        g2d.setColor(Color.black);
        g2d.drawRect(30, 60, 250, 30);

        //Ok button
        g2d.drawRect(110, 140, 60, 30);
    }

    /** 
     * Draws battle panel
     * @param winner unit
     * @param loser unit
     * @return buffimage
     */
    public BufferedImage drawBattle(Military winner, Military loser) {
        battleGraphics.drawImage(battleTemp, null, 0, 0);
        battleGraphics.setFont(largeFont);
        battleGraphics.setColor(Color.black);
        battleGraphics.drawString("Winner!", 60, 40);
        battleGraphics.drawString("Loser", 200, 40);
        battleGraphics.drawImage(store.getUnit(winner, winner.getOwner()
                .getColor()), null, 40, 70);
        battleGraphics.drawImage(store.getUnit(loser, loser.getOwner()
                .getColor()), null, 200, 70);

        battleGraphics.setColor(Color.black);
        battleGraphics.setFont(smallFont);
        battleGraphics.drawRect(130, 150, 60, 30);
        battleGraphics.drawString("Continue", 140, 165);

        return battle;
    }

    /**
     * Draws matchmaking screen
     *  
     */
    public final void drawMatchmaking() {
        //Draw boxes
        //Other player box
        g2d.setColor(panelColor);
        g2d.fillRect(80, 60, 220, 400);
        //Player setup box
        g2d.fillRect(400, 60, 340, 400);
        //Quit button
        g2d.fillRect(640, 480, 120, 40);
        //Player color
        //Side name
        g2d.setColor(Color.lightGray);
        g2d.fillRect(540, 120, 180, 40);

        //Draw borders
        //Other player box
        g2d.setColor(Color.black);
        g2d.drawRect(80, 60, 220, 400);
        //Player setup box
        g2d.drawRect(400, 60, 340, 400);
        //Quit button
        g2d.drawRect(640, 480, 120, 40);
        //Player side name
        g2d.drawRect(540, 120, 180, 40);

        //Draw button text
        //Player setup
        g2d.setFont(smallFont);
        g2d.drawString("Your Color", 445, 110);
        g2d.drawString("Your Side Name", 575, 110);
        //Game
        g2d.setFont(largeFont);
        g2d.drawString("Quit", 680, 505);
        g2d.drawString("Other Players", 130, 85);
    }

    /**
     * Sets state
     * 
     * @param state
     *            State
     */
    public void setState(ClientState state) {
        this.state = state;
    }

    /**
     * Sets player color
     * 
     * @param color
     *            Color
     */
    public void setPlayerColor(Color color) {
        playerColor = color;
    }

    /**
     * Sets selected planet
     * 
     * @param planet
     *            Planet
     */
    public void setSelectedPlanet(Planet planet) {
        selectedPlanet = planet;
        selectedUnit = -1;
        unitScroll = 0;
    }

    /**
     * Returns selected planet
     * 
     * @return Planet selectedPlanet
     */
    public Planet getSelectedPlanet() {
        return selectedPlanet;
    }

    /**
     * get buff
     * @return buff
     */
    public BufferedImage getMainPanel() {
        return mainPanel;
    }

    /**
     * get unit
     * @param i index
     * @return unit
     */
    public Military getUnit(int i) {
        if (unitBox.size() > (unitScroll + i)) {
            selectedUnit = unitScroll + i;
            return (Military) unitBox.get(selectedUnit);
        }
        return null;
    }

    /**
     * Returns matchmaking image
     * 
     * @return matchmaking Image
     */
    public BufferedImage getMatchmaking() {
        return matchmaking;
    }

    /**
     * Returns stats image
     * 
     * @return stats Image
     */
    public BufferedImage getStats() {
        return stats;
    }

    /**
     * Returns minimap image
     * 
     * @return minimap image
     */
    public BufferedImage getMinimap() {
        if (redraw == 0) {
            int i = 0;
            while (state.getNumPlanets() > i) {
                if (state.getPlanet(i).getOwner() != null) {
                    miniGraphics.setColor(state.getPlanet(i).getOwner()
                            .getColor());
                } else {
                    miniGraphics.setColor(Color.gray);
                }
                miniGraphics.fillOval(state.getPlanet(i).getXPosition() / 10,
                        state.getPlanet(i).getYPosition() / 10, 10, 10);
                i++;
            }
            redraw = 30;
            return minimap;
        } else {
            redraw--;
            return minimap;
        }
    }

    /**
     * Returns entry box
     * 
     * @return entry box image
     */
    public BufferedImage getEntryBox() {
        return entryBox;
    }

    /**
     * Returns panel with planets and units on it
     * 
     * @return Game panel
     */
    public BufferedImage getPanel() {
        panelGraphics.drawImage(store.getPanel(), 0, 0, null);

        //Draw units
        if (selectedPlanet != null) {
            int x = selectedPlanet.getXPosition();
            int y = selectedPlanet.getYPosition();
            int i = 0;
            int num = state.getNumUnits();
            int drawY = 382;
            Military temp;
            if (unitCheck != 0) {
                unitCheck--;
            } else {
                unitBox = new LinkedList();
                unitCheck = 5;
                while (i < num) {
                    temp = state.getUnit(i);
                    if (temp.getOwner().getPlayerID()
                            == selectedPlanet.getOwner().getPlayerID()) {
                    if ((temp.getXPosition() - x < 71)
                            && (temp.getXPosition() - x > -71)) {
                        if ((temp.getYPosition() - y < 71)
                                && (temp.getYPosition() - y > -71)) {
                            unitBox.add(temp);
                        }
                    }
                }
                    i++;
                }
            }
            i = unitScroll;
            if (unitBox.size() > i) {
                temp = (Military) unitBox.get(i);
                if (i != selectedUnit) {
                    panelGraphics.drawImage(store.getUnit(temp, temp.getOwner()
                            .getColor()), null, 35, drawY);
                } else {
                    panelGraphics.drawImage(store.getUnit(temp, Color.yellow),
                            null, 35, drawY);
                }
                i++;
                drawY += 55;
            }
            if (unitBox.size() > i) {
                temp = (Military) unitBox.get(i);
                if (i != selectedUnit) {
                    panelGraphics.drawImage(store.getUnit(temp, temp.getOwner()
                            .getColor()), null, 35, drawY);
                } else {
                    panelGraphics.drawImage(store.getUnit(temp, Color.yellow),
                            null, 35, drawY);
                }
            }
        }

        return mainPanel;
    }

    /** 
     * Scroll unit box
     * @param x index
     */
    public void scrollUnits(int x) {
        if (x < 0) {
            unitScroll--;
        } else {
            unitScroll++;
        }

        if (unitScroll == unitBox.size()) {
            unitScroll = unitBox.size() - 1;
        }
        if (unitScroll < 0) {
            unitScroll = 0;
        }
    }
}