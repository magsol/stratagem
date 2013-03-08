/*
 * Created on Nov 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;
import java.awt.Color;

//import edu.cs2335.tsunami.stratagem.kernel.Dreadnaught;
import edu.cs2335.tsunami.stratagem.kernel.Ragnarok;
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
public class RagnarokTest extends TestCase {
   
    /** player 1 */
    private Player player1;

    /** player 2 */
    private Player player2;
    
    /** starting planet */
    private Planet start;
    
    /** rag 1 */
    private Ragnarok rag;
    
    /** rag 2 */
    private Ragnarok rag2;
    
    /** rag 3 */
    private Ragnarok rag3;
   
    /**
     * Builds this test case
     * @param s whatev
     */
    public RagnarokTest(String s) {
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
            rag = new Ragnarok(start, 1);
            rag.setSize(7);
            rag.setMorale(50);
            rag.setOilSupply(300);
            rag.setFoodSupply(200);
            
            rag2 = new Ragnarok(start, 2);
            rag2.setSize(5);
            rag2.setMorale(70);
            rag2.setOilSupply(200);
            rag2.setFoodSupply(700);
            
            rag3 = new Ragnarok(start, 3);
            rag3.setSize(5);
            rag3.setMorale(70);
            rag3.setOilSupply(200);
            rag3.setFoodSupply(700);
        }
        
        /**
         * Destroy this test case
         */
        public void tearDown() {
        }
        
        /**
         * Merge test 1
         *
         */
        public void testMerge() {
            rag.merge(rag2);
            Assert.assertTrue(rag.getExperience() == 0);
            Assert.assertTrue(rag.getSize() == 12);
            Assert.assertTrue(rag.getMorale() == 50);
            Assert.assertTrue(rag.getOilSupply() == 500);
            Assert.assertTrue(rag.getFoodSupply() == 900);
        }
        
        /**
         * Merge test 2
         *
         */
        public void testMerge2() {
            rag.merge(null);
            Assert.assertTrue(rag.getExperience() == 0);
            Assert.assertTrue(rag.getSize() == 7);
            Assert.assertTrue(rag.getMorale() == 50);
            Assert.assertTrue(rag.getOilSupply() == 300);
            Assert.assertTrue(rag.getFoodSupply() == 200);
        }
        
        /**
         * Merge test 3
         *
         */
        public void testMerge3() {
            rag.merge(rag3);
            Assert.assertTrue(rag.getExperience() == 0);
            Assert.assertTrue(rag.getSize() == 7);
            Assert.assertTrue(rag.getMorale() == 50);
            Assert.assertTrue(rag.getOilSupply() == 300);
            Assert.assertTrue(rag.getFoodSupply() == 200);
        }
}
