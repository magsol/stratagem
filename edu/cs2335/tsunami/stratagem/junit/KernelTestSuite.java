/*
 * Created on Nov 5, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.junit;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author gtg835p
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class KernelTestSuite extends TestSuite {

    /**
     * Create this test suite
     * @return a new Test suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(ArmyTest.class);
        suite.addTestSuite(DreadnaughtTest.class);
        suite.addTestSuite(FrigateTest.class);
        suite.addTestSuite(MilitaryTest.class);
        suite.addTestSuite(NavyTest.class);
        suite.addTestSuite(OverlordTest.class);
        suite.addTestSuite(PlanetTest.class);
        suite.addTestSuite(RagnarokTest.class);
        suite.addTestSuite(RaptorTest.class);
        suite.addTestSuite(ShadeTest.class);

        return suite;
    }

    /**
     * Main
     * @param args jigga wha?
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}

