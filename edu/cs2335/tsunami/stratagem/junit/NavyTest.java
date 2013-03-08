package edu.cs2335.tsunami.stratagem.junit;

import java.awt.Color;
import java.awt.Point;

import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.kernel.Player;
import edu.cs2335.tsunami.stratagem.kernel.Dreadnaught;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author gtg835p
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NavyTest extends TestCase {
   
    /**
     * Construct this naval test
     * @param s whatev
     */
    public NavyTest(String s) {
         super(s);   
        }
    
    /**
     * Set up this test
     */
    public void setUp() {
    }
    
    /**
     * Destroy this test
     */
    public void tearDown() {
    }
    
    /**
     * Test moving by worm hole
     *
     */
    public void testMoveWormHole() {
        Player player1 = new Player("Tim", Color.white);
        player1.setPlayerID(1234);
        player1.setMoney(1000);
        player1.setSteel(1000);
        
        Planet start = new Planet();
        start.setName("start");
        start.setLocation(new Point(50, 50));
        Planet dest = new Planet();
        dest.setName("dest");
        dest.setLocation(new Point (300, 300));
        start.setIsWormHoleAccessible(true);
        dest.setIsWormHoleAccessible(true);
        
        Dreadnaught d = new Dreadnaught(start, 1);
        d.moveWormHole(dest);
        
        
        Assert.assertTrue(d.getNextPlanet().equals(dest));     // (3)
    }
}
