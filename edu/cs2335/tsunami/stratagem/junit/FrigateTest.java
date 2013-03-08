/*
 * Created on Nov 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import java.awt.Color;

//import edu.cs2335.tsunami.stratagem.kernel.Dreadnaught;
import edu.cs2335.tsunami.stratagem.kernel.Frigate;
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
public class FrigateTest extends TestCase {
    
    /** player 1 */
    private Player player1;
    
    /** player 2 */
    private Player player2;
    
    /** starting planet */
    private Planet start;
    
    /** frigate 1 */
    private Frigate f;
    
    /** frigate 2 */
    private Frigate f2;
    
    /** frigate 3 */
    private Frigate f3;
    
    /**
     * Build frigate test
     * @param s whatev
     */
    public FrigateTest(String s) {
        super(s);   
       }

        /**
         * Set up this frigate test
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
            f = new Frigate(start, 1);
            f.setSize(7);
            f.setMorale(50);
            f.setOilSupply(300);
            f.setFoodSupply(200);
            
            f2 = new Frigate(start, 2);
            f2.setSize(5);
            f2.setMorale(70);
            f2.setOilSupply(200);
            f2.setFoodSupply(700);
            
            f3 = new Frigate(start, 3);
            f3.setSize(5);
            f3.setMorale(70);
            f3.setOilSupply(200);
            f3.setFoodSupply(700);
        }
        
        /**
         * Destroy this frigate test
         */
        public void tearDown() {
        }
        
        /**
         * Test unit merge
         *
         */
        public void testMerge() {
            f.merge(f2);
            Assert.assertTrue(f.getExperience() == 0);
            Assert.assertTrue(f.getSize() == 12);
            Assert.assertTrue(f.getMorale() == 50);
            Assert.assertTrue(f.getOilSupply() == 500);
            Assert.assertTrue(f.getFoodSupply() == 900);
        }
        
        /**
         * Test unit merge
         *
         */
        public void testMerge2() {
            f.merge(null);
            Assert.assertTrue(f.getExperience() == 0);
            Assert.assertTrue(f.getSize() == 7);
            Assert.assertTrue(f.getMorale() == 50);
            Assert.assertTrue(f.getOilSupply() == 300);
            Assert.assertTrue(f.getFoodSupply() == 200);
            
        }
        
        /**
         * Test unit merge
         *
         */
        public void testMerge3() {
            f.merge(f3);
            Assert.assertTrue(f.getExperience() == 0);
            Assert.assertTrue(f.getSize() == 7);
            Assert.assertTrue(f.getMorale() == 50);
            Assert.assertTrue(f.getOilSupply() == 300);
            Assert.assertTrue(f.getFoodSupply() == 200);
        }
}
