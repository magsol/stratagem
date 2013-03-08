/*
 * Created on Nov 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import java.awt.Color;

//import edu.cs2335.tsunami.stratagem.kernel.Dreadnaught;
import edu.cs2335.tsunami.stratagem.kernel.Shade;
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
public class ShadeTest extends TestCase {
    
    /** player 1 */ 
    private Player player1;

    /** player 2 */
    private Player player2;
    
    /** starting planet */
    private Planet start;
    
    /** starting shade */
    private Shade s;
    
    /** starting shade 2 */
    private Shade s2;

    /** starting shade 3 */
    private Shade s3;
    
    /**
     * Builds the shade test
     * @param s some string
     */
    public ShadeTest(String s) {
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
            s = new Shade(start, 1);
            s.setSize(7);
            s.setMorale(50);
            s.setOilSupply(300);
            s.setFoodSupply(200);
            
            s2 = new Shade(start, 2);
            s2.setSize(5);
            s2.setMorale(70);
            s2.setOilSupply(200);
            s2.setFoodSupply(700);
            
            s3 = new Shade(start, 3);
            s3.setSize(5);
            s3.setMorale(70);
            s3.setOilSupply(200);
            s3.setFoodSupply(700);
        }
    
        /**
         * Destroy the test case 
         */
        public void tearDown() {
        }
        
        /**
         * Test merge
         *
         */
        public void testMerge() {
            s.merge(s2);
            Assert.assertTrue(s.getExperience() == 0);
            Assert.assertTrue(s.getSize() == 12);
            Assert.assertTrue(s.getMorale() == 50);
            Assert.assertTrue(s.getOilSupply() == 500);
            Assert.assertTrue(s.getFoodSupply() == 900);
        }
        
        /**
         * Test merge
         *
         */
        public void testMerge2() {
            s.merge(null);
            Assert.assertTrue(s.getExperience() == 0);
            Assert.assertTrue(s.getSize() == 7);
            Assert.assertTrue(s.getMorale() == 50);
            Assert.assertTrue(s.getOilSupply() == 300);
            Assert.assertTrue(s.getFoodSupply() == 200);
            
        }
        
        /**
         * Test merge
         */
        public void testMerge3() {
            s.merge(s3);
            Assert.assertTrue(s.getExperience() == 0);
            Assert.assertTrue(s.getSize() == 7);
            Assert.assertTrue(s.getMorale() == 50);
            Assert.assertTrue(s.getOilSupply() == 300);
            Assert.assertTrue(s.getFoodSupply() == 200);
        }
}
