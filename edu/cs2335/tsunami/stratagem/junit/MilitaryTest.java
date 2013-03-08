/**
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
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MilitaryTest extends TestCase {

  /** player */
  private Player player1;

  /** start planet */
  private Planet start;

  /** destination planet */
  private Planet dest;

  /** raptor */
  private Raptor r;

  /**
   * constructs an object
   * @param s string param
   */
  public MilitaryTest(String s) {
    super(s);   
   }

  /**
   * sets this thang up
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
        dest.setLocation(new Point (450, 350));
        
        r = new Raptor(start, 1);
        r.setExperience(0);
    }
    
    /**
     * tears this thang down
     */
    public void tearDown() {
    }
    
    /**
     * train
     *
     */
    public void testTrain() {
        player1.setMoney(500);
        r.setExperience(2);
        r.train();
        Assert.assertTrue(r.getExperience() == 7);
        Assert.assertTrue(player1.getMoney() == (500 - 100));
   }
    
    /**
     * train deux
     *
     */
    public void testTrain2() {
        player1.setMoney(400);
        r.setExperience(3);
        r.train();
        Assert.assertTrue(r.getExperience() == 8);
        Assert.assertTrue(player1.getMoney() == (400 - 100));
   }
    
    /**
     * train volume 3
     *
     */
    public void testTrain3() {
        player1.setMoney(50);
        r.setExperience(2);
        r.train();
        Assert.assertTrue(r.getExperience() == 2);
        Assert.assertTrue(player1.getMoney() == (50));
   }
    
    /**
     * train part 4
     *
     */
    public void testTrain4() {
        player1.setMoney(100);
        r.setExperience(6);
        r.train();
        Assert.assertTrue(r.getExperience() == 11);
        Assert.assertTrue(player1.getMoney() == (100 - 100));
   }
    
    /**
     * test morale
     *
     */
    public void testMorale() {
      r.setMorale(5);
      r.setMorale(10);
      Assert.assertTrue(r.getMorale() == 10);
      r.setMorale(15);
      Assert.assertTrue(r.getMorale() == 15);
    }

    /**
     * test current planet
     *
     */
    public void testCurrentPlanet() {
      Planet p = new Planet();
      p.setName("Earth");
      r.setCurrentPlanet(p);
      r.setCurrentPlanet(start);
      String name = start.getName();
      Assert.assertTrue(r.getCurrentPlanet().getName().equals(name));
      r.setCurrentPlanet(p);
      name = p.getName();
      Assert.assertTrue(r.getCurrentPlanet().getName().equals(name)); 
    }

    /**
     * test owner
     *
     */
    public void testOwner() {
      Player p = new Player("Woo", Color.yellow);
      r.setOwner(p);
      r.setOwner(player1);
      String name = player1.getName();
      Assert.assertTrue(r.getOwner().getName().equals(name));
      r.setOwner(p);
      name = p.getName();
      Assert.assertTrue(r.getOwner().getName().equals(name));
    }

    /**
     * test next planet
     *
     */
    public void testNextPlanet() {
      Planet p = new Planet();
      p.setName("Mars");
      p.setLocation(new Point(50, 50));
      r.setNextPlanet(p);
      r.setNextPlanet(dest);
      String name = dest.getName();
      
      Assert.assertTrue(r.getNextPlanet().getName().equals(name));
      r.setNextPlanet(p);
      name = p.getName();
      Assert.assertTrue(r.getNextPlanet().getName().equals(name));
    }

    /**
     * test experience
     *
     */
    public void testExperience() {
      r.setExperience(50);
      r.setExperience(10);
      Assert.assertTrue(r.getExperience() == 10);
      r.setExperience(-10);
      Assert.assertTrue(r.getExperience() == -10);
    }

    /**
     * test speed
     *
     */
    public void testSpeed() {
      r.setSpeed(100);
      r.setSpeed(200);
      Assert.assertTrue(r.getSpeed() == 200);
      r.setSpeed(500);
      Assert.assertTrue(r.getSpeed() == 500);
    }

    /**
     * test food supply
     *
     */
    public void testFoodSupply() {
      r.setFoodSupply(-10);
      r.setFoodSupply(10);
      Assert.assertTrue(r.getFoodSupply() == 10);
      r.setFoodSupply(20);
      Assert.assertTrue(r.getFoodSupply() == 20);
    }

    /**
     * test size
     *
     */
    public void testSize() {
      r.setSize(23);
      r.setSize(24);
      Assert.assertTrue(r.getSize() == 24);
      r.setSize(25);
      Assert.assertTrue(r.getSize() == 25);
    }

    /**
     * test oil supply
     *
     */
    public void testOilSupply() {
      r.setOilSupply(45);
      r.setOilSupply(55);
      Assert.assertTrue(r.getOilSupply() == 55);
      r.setOilSupply(70);
      Assert.assertTrue(r.getOilSupply() == 70);
    }

    /**
     * test image file
     *
     */
    public void testImageFile() {
      String file = "Haha";
      String other = "Hoho";
      r.setImageFile(file);
      r.setImageFile(other);
      Assert.assertTrue(r.getImageFile().equals(other));
      r.setImageFile(file);
      Assert.assertTrue(r.getImageFile().equals(file));
    }

    /**
     * test location
     *
     */
    public void testLocation() {
      Point p1 = new Point(1, 1);
      Point p2 = new Point(2, 3);
      r.setLocation(p1);
      r.setLocation(p2);
      Assert.assertTrue(r.getLocation().getX() == 2);
      Assert.assertTrue(r.getLocation().getY() == 3);
      r.setLocation(p1);
      Assert.assertTrue(r.getLocation().getX() == 1);
      Assert.assertTrue(r.getLocation().getY() == 1);
    }

    /**
     * test x position
     *
     */
    public void testXPosition() {
      Point p = new Point(5, 10);
      r.setLocation(p);
      Assert.assertTrue(r.getXPosition() == 5);
      Assert.assertFalse(r.getXPosition() == 10);
    }

    /**
     * test y position
     *
     */
    public void testYPosition() {
      Point p = new Point(10, 5);
      r.setLocation(p);
      Assert.assertTrue(r.getYPosition() == 5);
      Assert.assertFalse(r.getYPosition() == 10);
    }

    /**
     * test military ID
     *
     */
    public void testMilitaryID() {
      r.setMilitaryID(10);
      r.setMilitaryID(20);
      Assert.assertTrue(r.getMilitaryID() == 20);
      r.setMilitaryID(30);
      Assert.assertTrue(r.getMilitaryID() == 30);
    }
    
    /**
     * test orbital speed
     *
     */
    public void testOrbitalSpeed() {
      r.setOrbitalSpeed(100);
      r.setOrbitalSpeed(200);
      Assert.assertTrue(r.getOrbitalSpeed() == 200);
      r.setOrbitalSpeed(150);
      Assert.assertTrue(r.getOrbitalSpeed() == 150);
    }
    
    /**
     * test can raid
     *
     */
    public void testCanRaid() {
      r.setCanRaid(false);
      r.setCanRaid(true);
      Assert.assertTrue(r.canRaid());
      r.setCanRaid(false);
      Assert.assertFalse(r.canRaid());
    }
    
    /**
     * test can bombard
     *
     */
    public void testCanBombard() {
      r.setCanBombard(false);
      r.setCanBombard(true);
      Assert.assertTrue(r.canBombard());
      r.setCanBombard(false);
      Assert.assertFalse(r.canBombard());      
    }
    
    /**
     * test army in wormhole
     *
     */
    public void testArmyInWormhole() {
      r.setArmyInWormHole(false);
      r.setArmyInWormHole(true);
      Assert.assertTrue(r.getArmyInWormHole());
      r.setArmyInWormHole(false);
      Assert.assertFalse(r.getArmyInWormHole());
    }
    
    /**
     * test upkeep
     *
     */
    public void testUpkeep() {
      r.setUpkeep(20);
      r.setUpkeep(30);
      Assert.assertTrue(r.getUpkeep() == 30);
      r.setUpkeep(40);
      Assert.assertTrue(r.getUpkeep() == 40);
    }
    
    /**
     * test damage
     *
     */
    public void testDamage() {
      r.setDamage(100);
      r.setDamage(0);
      Assert.assertTrue(r.getDamage() == 0);
      r.setDamage(10);
      Assert.assertTrue(r.getDamage() == 10);
    }
    
    /**
     * test hit points
     *
     */
    public void testHitPoints() {
      r.setHitPoints(500);
      r.setHitPoints(-10);
      Assert.assertTrue(r.getHitPoints() == -10);
      r.setHitPoints(100);
      Assert.assertTrue(r.getHitPoints() == 100);
    }
    
    /**
     * test armor class
     *
     */
    public void testArmorClass() {
      r.setArmorClass(5);
      r.setArmorClass(4);
      Assert.assertTrue(r.getArmorClass() == 4);
      r.setArmorClass(3);
      Assert.assertTrue(r.getArmorClass() == 3);
    }
    
    /**
     * test orbiting
     *
     */
    public void testOrbiting() {
      r.setOrbiting(false);
      r.setOrbiting(true);
      Assert.assertTrue(r.getOrbiting());
      r.setOrbiting(false);
      Assert.assertTrue(r.getOrbiting());
    }
    
    /**
     * test ID
     *
     */
    public void testID() {
      r.setID(50);
      r.setID(-100);
      Assert.assertTrue(r.getMilitaryID() == -100);
      r.setID(3);
      Assert.assertTrue(r.getMilitaryID() == 3);
    }
}
