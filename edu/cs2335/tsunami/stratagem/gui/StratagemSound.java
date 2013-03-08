/*
 * Created on Nov 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.gui;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;

/**
 * @author Tim Liu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class StratagemSound {

    /**audioclip*/
    private AudioClip[] clip;

    /**sounds location*/
    private URL[] soundsLocation;

    /**filenames*/
    private String[] filename = {"explosion2.wav", "firemissles.wav",
            "jetfly.wav", "laserfire3.wav", "ufofly.wav"};

    /**
     * constructor for sound
     *
     */
    public StratagemSound() {
        clip = new AudioClip[filename.length];
        soundsLocation = new URL[filename.length];
        try {
            for (int i = 0; i < filename.length; i++) {
                soundsLocation[i] = new URL("file:"
                        + new File("edu/cs2335/tsunami/stratagem/util/sounds")
                                .getCanonicalPath() + "/" + filename[i]);
                clip[i] = Applet.newAudioClip(soundsLocation[i]);
            }
        } catch (Exception f) {
            System.out.println(f.toString());
        }
    }

    /**
     * play
     * @param i int
     */
    public void play(int i) {
        if (i < filename.length) {
            clip[i].play();
        }
    }

}