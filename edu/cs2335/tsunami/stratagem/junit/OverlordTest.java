/*
 * Created on Nov 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import java.awt.Color;

//import edu.cs2335.tsunami.stratagem.kernel.Dreadnaught;
import edu.cs2335.tsunami.stratagem.kernel.Overlord;
import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.kernel.Player;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author gtg835p
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OverlordTest extends TestCase {
  
    /** player 1 */
    private Player player1;
    
    /** player 2 */
    private Player player2;
    
    /** starting planet */
    private Planet start;
    
    /** overlord 1 */
    private Overlord o;
    
    /** overlord 2 */
    private Overlord o2;
    
    /** overlord 3 */
    private Overlord o3;
    
    /**
     * Construct overlord test
     * @param s whatev
     */
    public OverlordTest(String s) {
        super(s);   
       }

        /**
         * Set up the test object
         */
        public void setUp() {
            player1 = new Player("Tim", Color.white);
            player1.setPlayerID(1234);
            player1.setMoney(1000);
            player1.setSteel(1000);
            
            player2 = new Player("Tim2", Color.blue);
            player2.setPlayerID(123);
            player2.setMoney(1000);
            player2.setSteel(1000);
                    
            start = new Planet();
            start.setName("start");
            o = new Overlord(start, 1);
            o.setSize(7);
            o.setMorale(50);
            o.setOilSupply(300);
            o.setFoodSupply(200);
            
            o2 = new Overlord(start, 2);
            o2.setSize(5);
            o2.setMorale(70);
            o2.setOilSupply(200);
            o2.setFoodSupply(700);
            
            o3 = new Overlord(start, 3);
            o3.setSize(5);
            o3.setMorale(70);
            o3.setOilSupply(200);
            o3.setFoodSupply(700);
        }
        
        /**
         * Destroy this test
         */
        public void tearDown() {
        }
        
        /**
         * Test overlord merge
         *
         */
        public void testMerge() {
            o.merge(o2);
            Assert.assertTrue(o.getExperience() == 0);
            Assert.assertTrue(o.getSize() == 12);
            Assert.assertTrue(o.getMorale() == 50);
            Assert.assertTrue(o.getOilSupply() == 500);
            Assert.assertTrue(o.getFoodSupply() == 900);
        }
        
        /**
         * Test overlord merge
         *
         */
        public void testMerge2() {
            o.merge(null);
            Assert.assertTrue(o.getExperience() == 0);
            Assert.assertTrue(o.getSize() == 7);
            Assert.assertTrue(o.getMorale() == 50);
            Assert.assertTrue(o.getOilSupply() == 300);
            Assert.assertTrue(o.getFoodSupply() == 200);
            
        }
        
        /**
         * Test overlord merge
         *
         */
        public void testMerge3() {
            o.merge(o3);
            Assert.assertTrue(o.getExperience() == 0);
            Assert.assertTrue(o.getSize() == 7);
            Assert.assertTrue(o.getMorale() == 50);
            Assert.assertTrue(o.getOilSupply() == 300);
            Assert.assertTrue(o.getFoodSupply() == 200);
        }
}
