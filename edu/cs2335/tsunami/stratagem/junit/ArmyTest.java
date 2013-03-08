/*
 * Created on Nov 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import java.awt.Color;
import java.awt.Point;

import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.kernel.Player;
import edu.cs2335.tsunami.stratagem.kernel.Raptor;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author gtg835p
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ArmyTest extends TestCase {
    
    /** player 1 */
    private Player player1;

    /** starting planet */
    private Planet start;

    /** destination planet */
    private Planet dest;

    /** second destination planet */
    private Planet dest2;

    /** raptor object */
    private Raptor r;

    /**
     * Build this test case
     * @param s say hello to mr. worthless string
     */
    public ArmyTest(String s) {
        super(s);
    }

    /**
     * Test this outfit's flight movement
     *
     */
    public void testMoveFlight() {
        r.moveFlight(dest);
        Assert.assertTrue(r.getNextPlanet().equals(dest)); // (3)
    }

    /**
     * Test this outfit's flight movement
     *
     */
    public void testMoveFlight2() {
        r.moveFlight(dest2);
        Assert.assertTrue(r.getNextPlanet() == (null)); // (3)
    }

    /**
     * Test this outfit's shuttle movement
     *
     */
    public void testMoveShuttle() {
        r.moveShuttle(dest);
        Assert.assertTrue(r.getCurrentPlanet().equals(dest));
        Assert.assertTrue(player1.getMoney() == (1000 - 200));
    }

    /**
     * Test this outfit's shuttle movement
     *
     */
    public void testMoveShuttle2() {
        r.moveShuttle(dest2);
        Assert.assertTrue(r.getCurrentPlanet().equals(dest2));
        Assert.assertTrue(player1.getMoney() == (1000 - 200));
    }

    /**
     * Test this outfit's shuttle movement
     *
     */
    public void testMoveShuttle3() {
        r.moveShuttle(null);
        Assert.assertTrue(r.getCurrentPlanet().equals(start));
        Assert.assertTrue(player1.getMoney() == (1000));
    }

    /**
     * Test this outfit's wormhole movement
     *
     */
    public void testMoveWormHoleTransporter() {
        r.moveWormHoleTransport(dest);
        Assert.assertTrue(r.getNextPlanet().equals(dest));
        Assert.assertTrue(r.getSpeed() == (Raptor.SPEED + 4));
        Assert.assertTrue(player1.getMoney() == (1000 - 500));

    }

    /**
     * Test this outfit's wormhole movement
     *
     */
    public void testMoveWormHoleTransporter2() {
        r.moveWormHoleTransport(dest2);
        Assert.assertTrue(r.getNextPlanet() == (null));
        Assert.assertTrue(r.getSpeed() == (Raptor.SPEED));
        Assert.assertTrue(player1.getMoney() == (1000));

    }

    /**
     * Set up this test case
     */
    public void setUp() {
        player1 = new Player("Tim", Color.white);
        player1.setPlayerID(1234);
        player1.setMoney(1000);
        player1.setSteel(1000);

        start = new Planet();
        start.setName("start");
        start.setLocation(new Point(50, 50));
        dest = new Planet();
        dest.setName("dest");
        dest.setLocation(new Point(300, 300));
        dest2 = new Planet();
        dest2.setName("dest2");
        dest2.setLocation(new Point(400, 400));

        start.addAdjacent(dest);
        dest.addAdjacent(start);
        dest.addAdjacent(dest2);
        dest2.addAdjacent(dest);

        start.setIsWormHoleAccessible(true);
        dest.setIsWormHoleAccessible(true);

        r = new Raptor(start, 1);
        player1.setMoney(1000);

    }

    /**
     * Destroy this test case
     */
    public void tearDown() {
    }

}