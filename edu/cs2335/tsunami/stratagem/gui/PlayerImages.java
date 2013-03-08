/*
 * Created on Nov 18, 2004
 *
 */
package edu.cs2335.tsunami.stratagem.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author chrislg
 */
public class PlayerImages {

    /** Color of the player */
    private Color color;

    /** Graphics element to draw to */
    private Graphics2D g2d;

    /** Raptor (infantry) image */
    private BufferedImage raptor;

    /** Frigate (destroyer) image */
    private BufferedImage frigate;

    /** Ragnarok (artillery) image */
    private BufferedImage ragnarok;

    /** Planet 1 image */
    private BufferedImage planet1;

    /** Planet 2 image */
    private BufferedImage planet2;

    /** Planet 3 image */
    private BufferedImage planet3;

    /** Planet 4 image */
    private BufferedImage planet4;

    /** Planet 5 image */
    private BufferedImage planet5;

    /**
     * constructor for player store
     * @param store is stored image
     * @param il is an image loader
     * @param color is a color
     */
    public PlayerImages(StoredImages store, ImageLoader il, Color color) {
        this.color = color;

        //Planet units
        //Raptor
        raptor = il.createImage(50, 50);
        g2d = raptor.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(0, 0, 50, 50);
        g2d.drawImage(store.getRaptorImage(), null, -4, 6);

        //Frigate
        frigate = il.createImage(60, 60);
        g2d = frigate.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(0, 0, 60, 60);
        g2d.drawImage(store.getFrigateImage(), null, 0, 8);

        //Ragnarok
        ragnarok = il.createImage(60, 60);
        g2d = ragnarok.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(0, 0, 60, 60);
        g2d.drawImage(store.getRagnarokImage(), null, 0, 8);

        //Planets
        planet1 = il.createImage(100, 100);
        g2d = planet1.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(5, 4, 91, 91);
        g2d.drawImage(store.getPlanet1Image(), null, 0, 0);

        planet2 = il.createImage(110, 110);
        g2d = planet2.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(5, 3, 100, 100);
        g2d.drawImage(store.getPlanet2Image(), null, 0, 0);

        planet3 = il.createImage(120, 120);
        g2d = planet3.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(7, 6, 106, 106);
        g2d.drawImage(store.getPlanet3Image(), null, 0, 0);

        planet4 = il.createImage(130, 130);
        g2d = planet4.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(4, 4, 120, 120);
        g2d.drawImage(store.getPlanet4Image(), null, 0, 0);

        planet5 = il.createImage(150, 150);
        g2d = planet5.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(2, 2, 142, 142);
        g2d.drawImage(store.getPlanet5Image(), null, 0, 0);
    }

    /**
     * Returns raptor image
     * 
     * @return raptor Raptor image
     */
    public BufferedImage getRaptor() {
        return raptor;
    }

    /**
     * Returns frigate image
     * 
     * @return frigate Friage image
     */
    public BufferedImage getFrigate() {
        return frigate;
    }

    /**
     * Returns ragnarok image
     * 
     * @return ragnarok image
     */
    public BufferedImage getRagnarok() {
        return ragnarok;
    }

    /**
     * Returns planet1 image
     * 
     * @return planet1 Planet1 image
     */
    public BufferedImage getPlanet1() {
        return planet1;
    }

    /**
     * Returns planet2 image
     * 
     * @return planet2 Planet2 image
     */
    public BufferedImage getPlanet2() {
        return planet2;
    }

    /**
     * Returns planet3 image
     * 
     * @return planet3 Planet3 image
     */
    public BufferedImage getPlanet3() {
        return planet3;
    }

    /**
     * Returns planet4 image
     * 
     * @return planet4 Planet4 image
     */
    public BufferedImage getPlanet4() {
        return planet4;
    }

    /**
     * Returns planet5 image
     * 
     * @return planet5 Planet5 image
     */
    public BufferedImage getPlanet5() {
        return planet5;
    }
}