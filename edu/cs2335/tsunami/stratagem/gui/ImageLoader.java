/*
 * Created on Oct 21, 2004
 *
 */
package edu.cs2335.tsunami.stratagem.gui;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;

/**
 * @author Chris Gray Code provided in lab 5
 */
public class ImageLoader {

    /** Graphics Configuration */
    private GraphicsConfiguration gc;

    /**
     * Constructor
     * 
     * @param gc
     *            GraphicsConfiguration
     */
    public ImageLoader(GraphicsConfiguration gc) {
        if (gc == null) {
            gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().getDefaultConfiguration();
        }
        this.gc = gc;
    }

    /**
     * Loads in an image
     * 
     * @param resource
     *            Path to image
     * @return An accelerated image
     */
    public BufferedImage loadImage(String resource) {
        try {
            File f = new File(resource);
            BufferedImage src = javax.imageio.ImageIO.read(f);
            BufferedImage dst = gc.createCompatibleImage(src.getWidth(), src
                    .getHeight(), src.getColorModel().getTransparency());
            Graphics2D g2d = dst.createGraphics();
            g2d.setComposite(AlphaComposite.Src);
            g2d.drawImage(src, 0, 0, null);
            g2d.dispose();
            return dst;
        } catch (java.io.IOException e) {
            return null;
        }
    }

    /**
     * Loads in an image
     * 
     * @param f is a file
     * @return An accelerated image
     */
    public BufferedImage loadImage(File f) {
        try {
            BufferedImage src = javax.imageio.ImageIO.read(f);
            BufferedImage dst = gc.createCompatibleImage(src.getWidth(), src
                    .getHeight(), src.getColorModel().getTransparency());
            Graphics2D g2d = dst.createGraphics();
            g2d.setComposite(AlphaComposite.Src);
            g2d.drawImage(src, 0, 0, null);
            g2d.dispose();
            return dst;
        } catch (java.io.IOException e) {
            return null;
        }
    }

    /**
     * Creates an accelerated image
     * 
     * @param width
     *            Width of created image.
     * @param height
     *            Height of created image.
     * @return image Created image
     */
    public BufferedImage createImage(int width, int height) {
        return gc
                .createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    }

    /**
     * Creates a volatile image
     * 
     * @param width
     *            Width of created image.
     * @param height
     *            Height of created image.
     * @return image Created image
     */
    public VolatileImage createVolatile(int width, int height) {
        return gc.createCompatibleVolatileImage(width, height);
    }
}