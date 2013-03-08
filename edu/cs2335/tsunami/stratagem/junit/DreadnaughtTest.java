/*
 * Created on Nov 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import java.awt.Color;

import edu.cs2335.tsunami.stratagem.kernel.Dreadnaught;
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
public class DreadnaughtTest extends TestCase {
    
    /** player 1 */
    private Player player1;
    
    /** player 2 */
    private Player player2;
    
    /** starting planet */
    private Planet start;
    
    /** dreadnaught 1 */
    private Dreadnaught d;
    
    /** dreadnaught 2 */
    private Dreadnaught d2;
    
    /** dreadnaught 3 */
    private Dreadnaught d3;
    
    /**
     * Build this test case
     * @param s jigga gulp
     */
    public DreadnaughtTest(String s) {
     super(s);   
    }

        /**
         * Set up this test case
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
            d = new Dreadnaught(start, 1);
            d.setSize(7);
            d.setMorale(50);
            d.setOilSupply(300);
            d.setFoodSupply(200);
            
            d2 = new Dreadnaught(start, 2);
            d2.setSize(5);
            d2.setMorale(70);
            d2.setOilSupply(200);
            d2.setFoodSupply(700);
            
            d3 = new Dreadnaught(start, 3);
            d3.setSize(5);
            d3.setMorale(70);
            d3.setOilSupply(200);
            d3.setFoodSupply(700);
        }
        
        /**
         * Destroy this test case
         */
        public void tearDown() {
        }
        
        /**
         * Test unit merge
         *
         */
        public void testMerge() {
            d.merge(d2);
            Assert.assertTrue(d.getExperience() == 0);
            Assert.assertTrue(d.getSize() == 12);
            Assert.assertTrue(d.getMorale() == 50);
            Assert.assertTrue(d.getOilSupply() == 500);
            Assert.assertTrue(d.getFoodSupply() == 900);
        }
        
        /**
         * Test unit merge
         *
         */
        public void testMerge2() {
            d.merge(null);
            Assert.assertTrue(d.getExperience() == 0);
            Assert.assertTrue(d.getSize() == 7);
            Assert.assertTrue(d.getMorale() == 50);
            Assert.assertTrue(d.getOilSupply() == 300);
            Assert.assertTrue(d.getFoodSupply() == 200);
            
        }
        
        /**
         * Test unit merge
         *
         */
        public void testMerge3() {
            d.merge(d3);
            Assert.assertTrue(d.getExperience() == 0);
            Assert.assertTrue(d.getSize() == 7);
            Assert.assertTrue(d.getMorale() == 50);
            Assert.assertTrue(d.getOilSupply() == 300);
            Assert.assertTrue(d.getFoodSupply() == 200);
        }
}
