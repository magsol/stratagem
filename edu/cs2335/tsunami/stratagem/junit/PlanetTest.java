/*
 * Created on Nov 5, 2004
 */
package edu.cs2335.tsunami.stratagem.junit;

import junit.framework.TestCase;
import junit.framework.Assert;

import java.awt.Color;
import java.awt.Point;

import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.kernel.Player;
import edu.cs2335.tsunami.stratagem.kernel.Shipyard;
//import edu.cs2335.tsunami.stratagem.kernel.Building;

/**
 * @author <a href="mailto:jwei@cc.gatech.edu">Jeff Wei </a>
 */
public class PlanetTest extends TestCase {
 
    /** planet 1 */
    private Planet testPlanet1;

    /** planet 2 */
    private Planet testPlanet2;

    /** planet 3 */
    private Planet testPlanet3;

    /**
     * builds this test case
     */
    public void setUp() {
        testPlanet1 = new Planet();
        testPlanet2 = new Planet();
        testPlanet3 = new Planet("Bob");
    }

    /**
     * destroys this test case
     */
    public void tearDown() {
        // nothing here!
    }

    /**
     * test is they are equal
     *
     */
    public void testEquals() {
        Assert.assertTrue(!testPlanet1.equals(null));
        Assert.assertTrue(!testPlanet2.equals(null));
        Assert.assertEquals(testPlanet1, testPlanet1);
        Assert.assertTrue(!testPlanet1.equals(testPlanet2));
        testPlanet2.setPlanetID(testPlanet1.getPlanetID());
        Assert.assertEquals(testPlanet1, testPlanet2);
    }

    /**
     * test adjacent planets
     *
     */
    public void testAdjacents() {
        Assert.assertTrue(!(testPlanet1.getAdjacents() == null));
        testPlanet1.addAdjacent(testPlanet2);
        Assert.assertEquals((Planet) testPlanet1.getAdjacents().get(0),
                testPlanet2);
        testPlanet1.addAdjacent(testPlanet3);
        Assert.assertEquals((Planet) testPlanet1.getAdjacents().get(0),
                testPlanet2);
        Assert.assertEquals((Planet) testPlanet1.getAdjacents().get(1),
                testPlanet3);

        testPlanet2.setAdjacents(testPlanet1.getAdjacents());
        Assert.assertEquals((Planet) testPlanet2.getAdjacents().get(0),
                testPlanet2);
        Assert.assertEquals((Planet) testPlanet2.getAdjacents().get(1),
                testPlanet3);

        testPlanet1.removeAdjacent(testPlanet2);
        Assert.assertEquals((Planet) testPlanet1.getAdjacents().get(0),
                testPlanet3);
        testPlanet1.removeAdjacent(testPlanet3);
        Assert.assertTrue(!(testPlanet1.getAdjacents() == null));
    }

    /**
     * Test planet allegiance
     *
     */
    public void testAllegiance() {
        testPlanet1.setAllegiance(0);
        testPlanet1.setAllegiance(100);
        Assert.assertTrue (testPlanet1.getAllegiance() == 100);
        testPlanet2.setAllegiance(testPlanet1.getAllegiance());
        Assert.assertTrue(testPlanet2.getAllegiance() == 100);
        testPlanet2.setAllegiance(-50);
        Assert.assertTrue(testPlanet2.getAllegiance() == -50);
        Assert.assertTrue(testPlanet1.getAllegiance() == 100);
    }

    /**
     * Test planet buildings
     *
     */    
    public void testBuildings() {
        
        Shipyard tb1 = new Shipyard();
        Shipyard tb2 = new Shipyard();
        
        Assert.assertTrue(!(testPlanet1.getBuildings() == null));
        testPlanet1.addBuilding(tb1);
        Assert.assertEquals((Shipyard) testPlanet1.getBuildings().get(0),
                tb1);
        testPlanet1.addBuilding(tb2);
        Assert.assertEquals((Shipyard) testPlanet1.getBuildings().get(0),
                tb1);
        Assert.assertEquals((Shipyard) testPlanet1.getBuildings().get(1),
                tb2);

        testPlanet2.setBuildings(testPlanet1.getBuildings());
        Assert.assertEquals((Shipyard) testPlanet2.getBuildings().get(0),
                tb1);
        Assert.assertEquals((Shipyard) testPlanet2.getBuildings().get(1),
                tb2);

        testPlanet1.removeBuilding(tb1);
        Assert.assertEquals((Shipyard) testPlanet1.getBuildings().get(0),
                tb2);
        testPlanet1.removeBuilding(tb2);
        Assert.assertTrue(!(testPlanet1.getBuildings() == null));
    }

    /**
     * Test planet fortifications
     *
     */
    public void testFortification() {
        testPlanet1.setFortification(0);
        testPlanet1.setFortification(100);
        Assert.assertTrue(testPlanet1.getFortification() == 100);
        testPlanet2.setFortification(testPlanet1.getFortification());
        Assert.assertTrue(testPlanet2.getFortification() == 100);
        testPlanet2.setFortification(-50);
        Assert.assertTrue(testPlanet2.getFortification() == -50);
        Assert.assertTrue(testPlanet1.getFortification() == 100);
    }

    /**
     * Test isCapitol field
     *
     */
    public void testIsCapitol() {

        Assert.assertTrue(!testPlanet1.getIsCapitol());
        Assert.assertTrue(!testPlanet2.getIsCapitol());

        testPlanet1.setIsCapitol(true);
        Assert.assertTrue(testPlanet1.getIsCapitol());

        testPlanet2.setIsCapitol(false);
        Assert.assertTrue(!testPlanet2.getIsCapitol());

        testPlanet2.setIsCapitol(testPlanet1.getIsCapitol());
        Assert.assertTrue(testPlanet1.getIsCapitol());
        Assert.assertTrue(testPlanet1.getIsCapitol());

        testPlanet1.setIsCapitol(false);
        Assert.assertTrue(!testPlanet1.getIsCapitol());
        Assert.assertTrue(testPlanet2.getIsCapitol());
    }

    /**
     * Test planet's worm hole accessibility
     *
     */
    public void testIsWormHoleAccessible() {

        Assert.assertTrue(!testPlanet1.getIsWormHoleAccessible());
        Assert.assertTrue(!testPlanet2.getIsWormHoleAccessible());

        testPlanet1.setIsWormHoleAccessible(true);
        Assert.assertTrue(testPlanet1.getIsWormHoleAccessible());

        testPlanet2.setIsWormHoleAccessible(false);
        Assert.assertTrue(!testPlanet2.getIsWormHoleAccessible());

        testPlanet2.setIsWormHoleAccessible(testPlanet1
                .getIsWormHoleAccessible());
        Assert.assertTrue(testPlanet1.getIsWormHoleAccessible());
        Assert.assertTrue(testPlanet1.getIsWormHoleAccessible());

        testPlanet1.setIsWormHoleAccessible(false);
        Assert.assertTrue(!testPlanet1.getIsWormHoleAccessible());
        Assert.assertTrue(testPlanet2.getIsWormHoleAccessible());
    }
    
    /**
     * Test planet location
     *
     */
    public void testLocation() {

        Point testPoint = new Point(12, 23);
        
        testPlanet1.setLocation(testPoint);
        Assert.assertEquals(testPlanet1.getLocation(), testPoint);
        Assert.assertTrue(testPlanet1.getXPosition() == 12);
        Assert.assertTrue(testPlanet1.getYPosition() == 23);
        
        testPlanet2.setLocation(testPlanet1.getLocation());
        Assert.assertEquals(testPlanet2.getLocation(), testPoint);
        Assert.assertTrue(testPlanet2.getXPosition() == 12);
        Assert.assertTrue(testPlanet2.getYPosition() == 23);
        
        testPlanet1.setLocation(new Point(1, 2));
        Assert.assertTrue(testPlanet1.getXPosition() != 12);
        Assert.assertTrue(testPlanet1.getYPosition() != 23);

    }

    /**
     * Test planet morale
     *
     */
    public void testMorale() {
        testPlanet1.setMorale(0);
        testPlanet1.setMorale(100);
        Assert.assertTrue(testPlanet1.getMorale() == 100);
        testPlanet2.setMorale(testPlanet1.getMorale());
        Assert.assertTrue(testPlanet2.getMorale() == 100);
        testPlanet2.setMorale(-50);
        Assert.assertTrue(testPlanet2.getMorale() == -50);
        Assert.assertTrue(testPlanet1.getMorale() == 100);
    }

    /**
     * Test planet name
     *
     */
    public void testName() {
        
        Assert.assertEquals(testPlanet3.getName(), "Bob");
        
        testPlanet1.setName("Joe");
        Assert.assertEquals(testPlanet1.getName(), "Joe");
        
        Assert.assertTrue(
                !testPlanet3.getName().equals(testPlanet1.getName()));
        
        testPlanet2.setName(testPlanet1.getName());
        Assert.assertEquals(testPlanet1.getName(), testPlanet2.getName());
        Assert.assertEquals(testPlanet2.getName(), "Joe");
        
        testPlanet2.setName("Jeff");
        Assert.assertEquals(testPlanet2.getName(), "Jeff");
        Assert.assertTrue(!testPlanet1.getName().equals("Jeff"));

    }

    /**
     * Test planet owner
     *
     */
    public void testOwner() {
        
        Player tp1 = new Player("Woo", Color.yellow);
        Player tp2 = new Player("Bah", Color.blue);
        
        testPlanet1.setOwner(tp1);
        testPlanet1.setOwner(tp2);
        Assert.assertEquals(testPlanet1.getOwner(), tp2);
        testPlanet1.setOwner(tp1);
        Assert.assertEquals(testPlanet1.getOwner(), tp1);
    }
      

    /**
     * Test planet ID
     *
     */
    public void testPlanetID() {
        testPlanet1.setPlanetID(0);
        testPlanet1.setPlanetID(100);
        Assert.assertTrue(testPlanet1.getPlanetID() == 100);
        testPlanet2.setPlanetID(testPlanet1.getPlanetID());
        Assert.assertTrue(testPlanet2.getPlanetID() == 100);
        testPlanet2.setPlanetID(-50);
        Assert.assertTrue(testPlanet2.getPlanetID() == -50);
        Assert.assertTrue(testPlanet1.getPlanetID() == 100);

    }

    /**
     * Test planet population
     *
     */
    public void testPopulation() {
        testPlanet1.setPopulation(0);
        testPlanet1.setPopulation(100);
        Assert.assertTrue(testPlanet1.getPopulation() == 100);
        testPlanet2.setPopulation(testPlanet1.getPopulation());
        Assert.assertTrue(testPlanet2.getPopulation() == 100);
        testPlanet2.setPopulation(-50);
        Assert.assertTrue(testPlanet2.getPopulation() == -50);
        Assert.assertTrue(testPlanet1.getPopulation() == 100);
    }
    
    /**
     * Test planet toString
     *
     */
    public void testToString() {
        
        testPlanet1.setLocation(new Point(34, 45));
        //testPlanet1.setImageFile("asdf");
        Assert.assertEquals(testPlanet1.toString(), "asdf 34 45");
       
        //testPlanet1.setImageFile("fdas");
        Assert.assertEquals(testPlanet1.toString(), "fdas 34 45");
        
        testPlanet1.setLocation(new Point(33, 11));
        Assert.assertEquals(testPlanet1.toString(), "fdas 33 11");
        
    }
}