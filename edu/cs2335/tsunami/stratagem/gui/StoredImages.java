/*
 * Created on Nov 16, 2004
 *
 */
package edu.cs2335.tsunami.stratagem.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.cs2335.tsunami.stratagem.kernel.Frigate;
import edu.cs2335.tsunami.stratagem.kernel.Military;
import edu.cs2335.tsunami.stratagem.kernel.Raptor;

/**
 * TODO Make images change on click.
 * 
 * @author chrislg
 */
public class StoredImages {

    /** Loads images */
    private ImageLoader il;

    /** Highlighted selections */
    private PlayerImages highlighted;

    /** Blue player */
    private PlayerImages bluePlayer;

    /** Red player */
    private PlayerImages redPlayer;

    /** Orange player */
    private PlayerImages orangePlayer;

    /** Cyan player */
    private PlayerImages cyanPlayer;

    /** Title image */
    private BufferedImage titleImage;

    /** tard */
    private BufferedImage credits;

    /** Moon background */
    private BufferedImage moon;

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

    /** Host game image (dark) */
    private BufferedImage hostGameUnselected;

    /** Host game image (light) */
    private BufferedImage hostGameSelected;

    /** Join game image (dark) */
    private BufferedImage joinGameUnselected;

    /** Join game image (light) */
    private BufferedImage joinGameSelected;

    /** Load game image (dark) */
    private BufferedImage loadGameUnselected;

    /** Load game image (light) */
    private BufferedImage loadGameSelected;

    /** Credits image (dark) */
    private BufferedImage creditsUnselected;

    /** Credits image (light) */
    private BufferedImage creditsSelected;

    /** Quit image (dark) */
    private BufferedImage quitUnselected;

    /** Quit image (light) */
    private BufferedImage quitSelected;

    /** Last sent bright host */
    private boolean brightHost;

    /** Last sent bright join */
    private boolean brightJoin;

    /** Last sent bright load */
    private boolean brightLoad;

    /** Last sent bright credits */
    private boolean brightCredits;

    /** Last sent bright quit */
    private boolean brightQuit;

    /** Panel image */
    private BufferedImage panelImage;

    /**
     * Initiates images
     * 
     * @param il
     *            ImageLoader
     */
    public StoredImages(ImageLoader il) {
        this.il = il;

        panelImage = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/panel.jpg");

        //Set images
        //Units
        raptor = il.loadImage("edu/cs2335/tsunami/stratagem/util/Raptor.gif");
        frigate = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/Frigate.gif");
        ragnarok = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/Ragnarok.gif");

        //Planets
        planet1 = il.loadImage("edu/cs2335/tsunami/stratagem/util/planet1.gif");
        planet2 = il.loadImage("edu/cs2335/tsunami/stratagem/util/planet2.gif");
        planet3 = il.loadImage("edu/cs2335/tsunami/stratagem/util/planet3.gif");
        planet4 = il.loadImage("edu/cs2335/tsunami/stratagem/util/planet4.gif");
        planet5 = il.loadImage("edu/cs2335/tsunami/stratagem/util/planet5.gif");

        //Menu
        hostGameUnselected = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/welcomescreen/Host");
        hostGameSelected = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/welcomescreen/HostBright");
        joinGameUnselected = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/welcomescreen/Join");
        joinGameSelected = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/welcomescreen/JoinBright");
        loadGameUnselected = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/welcomescreen/Load");
        loadGameSelected = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/welcomescreen/LoadBright");
        creditsUnselected = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/welcomescreen/Credits");
        creditsSelected = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/welcomescreen/CreditsBright");
        quitUnselected = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/welcomescreen/Quit");
        quitSelected = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/welcomescreen/QuitBright");
        moon = il.loadImage("edu/cs2335/tsunami/stratagem/util/FullMoony.jpg");
        titleImage = il
                .loadImage("edu/cs2335/tsunami/stratagem/util/Stratagem.png");
        credits = il.loadImage("edu/cs2335/tsunami/stratagem/util/credits");

        redPlayer = new PlayerImages(this, il, Color.red);
        bluePlayer = new PlayerImages(this, il, Color.blue);
        orangePlayer = new PlayerImages(this, il, Color.orange);
        cyanPlayer = new PlayerImages(this, il, Color.cyan);
        highlighted = new PlayerImages(this, il, Color.yellow);
    }

    /**
     * Returns current host game image
     * 
     * @return Host Game image
     */
    public BufferedImage getHostImage() {
        if (!brightHost) {
            return hostGameUnselected;
        } else {
            return hostGameSelected;
        }
    }

    /**
     * Returns current join game image
     * 
     * @return Host Join image
     */
    public BufferedImage getJoinImage() {
        if (!brightJoin) {
            return joinGameUnselected;
        } else {
            return joinGameSelected;
        }
    }

    /**
     * Returns current load game image
     * 
     * @return load Game image
     */
    public BufferedImage getLoadImage() {
        if (!brightLoad) {
            return loadGameUnselected;
        } else {
            return loadGameSelected;
        }
    }

    /**
     * Returns current credits image
     * 
     * @return Credits image
     */
    public BufferedImage getCreditsImage() {
        if (!brightCredits) {
            return creditsUnselected;
        } else {
            return creditsSelected;
        }
    }

    /**
     * Returns current quit game image
     * 
     * @return Quit Game image
     */
    public BufferedImage getQuitImage() {
        if (!brightQuit) {
            return quitUnselected;
        } else {
            return quitSelected;
        }
    }

    /**
     * Returns moon image
     * 
     * @return moon Moon image
     */
    public BufferedImage getMoonImage() {
        return moon;
    }

    /**
     * Returns title image
     * 
     * @return title Title image
     */
    public BufferedImage getTitleImage() {
        return titleImage;
    }

    /**
     * Sets which host game image to use
     *  
     */
    public void setHost() {
        if (brightHost) {
            brightHost = false;
        } else {
            brightHost = true;
        }
    }

    /**
     * Sets which join image to use
     *  
     */
    public void setJoin() {
        if (brightJoin) {
            brightJoin = false;
        } else {
            brightJoin = true;
        }
    }

    /**
     * Sets which load image to use
     *  
     */
    public void setLoad() {
        if (brightLoad) {
            brightLoad = false;
        } else {
            brightLoad = true;
        }
    }

    /**
     * Sets which credits image to use
     * 
     * @author chrislg
     */
    public void setCredits() {
        if (brightCredits) {
            brightCredits = false;
        } else {
            brightCredits = true;
        }
    }

    /**
     * Sets which quit image to use
     *  
     */
    public void setQuit() {
        if (brightQuit) {
            brightQuit = false;
        } else {
            brightQuit = true;
        }
    }

    /**
     * Returns raptor image
     * 
     * @return raptor Raptor image
     */
    public BufferedImage getRaptorImage() {
        return raptor;
    }

    /**
     * Returns raptor image
     * @param color is a color
     * @return raptor Raptor image
     */
    public BufferedImage getRaptor(Color color) {
        if (color.equals(Color.blue)) {
            return bluePlayer.getRaptor();
        } else if (color.equals(Color.red)) {
            return redPlayer.getRaptor();
        } else if (color.equals(Color.orange)) {
            return orangePlayer.getRaptor();
        } else if (color.equals(Color.cyan)) {
            return cyanPlayer.getRaptor();
        } else {
            return highlighted.getRaptor();
        }
    }

    /**
     * Returns frigate image
     * 
     * @return frigate Frigate image
     */
    public BufferedImage getFrigateImage() {
        return frigate;
    }

    /**
     * Returns frigate image
     * @param color is a color
     * @return frigate Friage image
     */
    public BufferedImage getFrigate(Color color) {
        if (color.equals(Color.blue)) {
            return bluePlayer.getFrigate();
        } else if (color.equals(Color.red)) {
            return redPlayer.getFrigate();
        } else if (color.equals(Color.orange)) {
            return orangePlayer.getFrigate();
        } else if (color.equals(Color.CYAN)) {
            return cyanPlayer.getFrigate();
        } else {
            return highlighted.getFrigate();
        }
    }

    /**
     * Returns ragnarok image
     * 
     * @return ragnarok image
     */
    public BufferedImage getRagnarokImage() {
        return ragnarok;
    }

    /**
     * Returns ragnarok image
     * @param color is a color
     * @return ragnarok image
     */
    public BufferedImage getRagnarok(Color color) {
        if (color.equals(Color.blue)) {
            return bluePlayer.getRagnarok();
        } else if (color.equals(Color.red)) {
            return redPlayer.getRagnarok();
        } else if (color.equals(Color.orange)) {
            return orangePlayer.getRagnarok();
        } else if (color.equals(Color.cyan)) {
            return cyanPlayer.getRagnarok();
        } else {
            return highlighted.getRagnarok();
        }
    }

    /** Returns correct highlighted planet image
     * @param i is an int
     * @param color is a color
     * @return bufferedimage 
     */
    public BufferedImage getPlanet(int i, Color color) {
        switch (i) {
        case 0:
            return getPlanet1(color);
        case 1:
            return getPlanet2(color);
        case 2:
            return getPlanet3(color);
        case 3:
            return getPlanet4(color);
        default:
            return getPlanet5(color);
        }
    }

    /** Returns correct highlighted unit image
     * @param unit is a military unit
     * @param color is a color
     * @return bufferedimage 
     */
    public BufferedImage getUnit(Military unit, Color color) {
            if (unit instanceof Raptor) {
                return getRaptor(color);
            } else if (unit instanceof Frigate) {
                return getFrigate(color);
            } else {
                return getRagnarok(color);
            }
    }

    /**
     * Returns planet1 image
     * 
     * @return planet1 Planet1 image
     */
    public BufferedImage getPlanet1Image() {
        return planet1;
    }

    /**
     * Returns planet2 image
     * 
     * @return planet2 Planet2 image
     */
    public BufferedImage getPlanet2Image() {
        return planet2;
    }

    /**
     * Returns planet3 image
     * 
     * @return planet3 Planet3 image
     */
    public BufferedImage getPlanet3Image() {
        return planet3;
    }

    /**
     * Returns planet4 image
     * 
     * @return planet4 Planet4 image
     */
    public BufferedImage getPlanet4Image() {
        return planet4;
    }

    /**
     * Returns planet5 image
     * 
     * @return planet5 Planet5 image
     */
    public BufferedImage getPlanet5Image() {
        return planet5;
    }

    /**
     * Returns planet1 image
     * @param color is a color
     * @return planet1 Planet1 image
     */
    public BufferedImage getPlanet1(Color color) {
        if (color.equals(Color.blue)) {
            return bluePlayer.getPlanet1();
        } else if (color.equals(Color.red)) {
            return redPlayer.getPlanet1();
        } else if (color.equals(Color.orange)) {
            return orangePlayer.getPlanet1();
        } else {
            return cyanPlayer.getPlanet1();
        }
    }

    /**
     * Returns planet2 image
     * @param color is a color
     * @return planet2 Planet2 image
     */
    public BufferedImage getPlanet2(Color color) {
        if (color.equals(Color.blue)) {
            return bluePlayer.getPlanet2();
        } else if (color.equals(Color.red)) {
            return redPlayer.getPlanet2();
        } else if (color.equals(Color.orange)) {
            return orangePlayer.getPlanet2();
        } else {
            return cyanPlayer.getPlanet2();
        }
    }

    /**
     * Returns planet3 image
     * @param color is a color
     * @return planet3 Planet3 image
     */
    public BufferedImage getPlanet3(Color color) {
        if (color.equals(Color.blue)) {
            return bluePlayer.getPlanet3();
        } else if (color.equals(Color.red)) {
            return redPlayer.getPlanet3();
        } else if (color.equals(Color.orange)) {
            return orangePlayer.getPlanet3();
        } else {
            return cyanPlayer.getPlanet3();
        }
    }

    /**
     * Returns planet4 image
     * @param color is a color
     * @return planet4 Planet4 image
     */
    public BufferedImage getPlanet4(Color color) {
        if (color.equals(Color.blue)) {
            return bluePlayer.getPlanet4();
        } else if (color.equals(Color.red)) {
            return redPlayer.getPlanet4();
        } else if (color.equals(Color.orange)) {
            return orangePlayer.getPlanet4();
        } else {
            return cyanPlayer.getPlanet4();
        }
    }

    /**
     * Returns planet5 image
     * @param color is a color
     * @return planet5 Planet5 image
     */
    public BufferedImage getPlanet5(Color color) {
        if (color.equals(Color.blue)) {
            return bluePlayer.getPlanet5();
        } else if (color.equals(Color.red)) {
            return redPlayer.getPlanet5();
        } else if (color.equals(Color.orange)) {
            return orangePlayer.getPlanet5();
        } else {
            return cyanPlayer.getPlanet5();
        }
    }

    /** Returns credits screen
     * @return bufferedImage 
     */
    public BufferedImage getCredits() {
        return credits;
    }

    /**
     * Panel image getter
     * 
     * @return panel image
     */
    public BufferedImage getPanel() {
        return panelImage;
    }
}