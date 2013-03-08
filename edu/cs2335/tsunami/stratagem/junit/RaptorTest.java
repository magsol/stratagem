/*
 * Created on Nov 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import java.awt.Color;

//import edu.cs2335.tsunami.stratagem.kernel.Dreadnaught;
import edu.cs2335.tsunami.stratagem.kernel.Raptor;
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
public class RaptorTest extends TestCase {
  
  /** player 1 */
  private Player player1;
  
  /** player 2 */
  private Player player2;
  
  /** starting planet */
  private Planet start;
  
  /** raptor 1 */
  private Raptor rap;
  
  /** raptor 2 */
  private Raptor rap2;
  
  /** raptor 3 */
  private Raptor rap3;
    
    /**
     * Construct the raptor test
     * @param s whatev
     */
    public RaptorTest(String s) {
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
            rap = new Raptor(start, 1);
            rap.setSize(7);
            rap.setMorale(50);
            rap.setOilSupply(300);
            rap.setFoodSupply(200);
            
            rap2 = new Raptor(start, 2);
            rap2.setSize(5);
            rap2.setMorale(70);
            rap2.setOilSupply(200);
            rap2.setFoodSupply(700);
            
            rap3 = new Raptor(start, 3);
            rap3.setSize(5);
            rap3.setMorale(70);
            rap3.setOilSupply(200);
            rap3.setFoodSupply(700);
        }
        
        /**
         * Destroy this test case
         */
        public void tearDown() {
        }
        
        /**
         * Test merge
         *
         */
        public void testMerge() {
            rap.merge(rap2);
            Assert.assertTrue(rap.getExperience() == 0);
            Assert.assertTrue(rap.getSize() == 12);
            Assert.assertTrue(rap.getMorale() == 50);
            Assert.assertTrue(rap.getOilSupply() == 500);
            Assert.assertTrue(rap.getFoodSupply() == 900);
        }
        
        /**
         * test merge
         *
         */
        public void testMerge2() {
            rap.merge(null);
            Assert.assertTrue(rap.getExperience() == 0);
            Assert.assertTrue(rap.getSize() == 7);
            Assert.assertTrue(rap.getMorale() == 50);
            Assert.assertTrue(rap.getOilSupply() == 300);
            Assert.assertTrue(rap.getFoodSupply() == 200);
            
        }
        
        /**
         * Test merge
         *
         */
        public void testMerge3() {
            rap.merge(rap3);
            Assert.assertTrue(rap.getExperience() == 0);
            Assert.assertTrue(rap.getSize() == 7);
            Assert.assertTrue(rap.getMorale() == 50);
            Assert.assertTrue(rap.getOilSupply() == 300);
            Assert.assertTrue(rap.getFoodSupply() == 200);
        }
}
