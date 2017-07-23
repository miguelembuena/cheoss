package org.cheoss.tests;

import junit.framework.*;

public class SuiteHash extends TestSuite {
	
 	     public static Test suite() { 
	          TestSuite suite = new TestSuite("Board Suite");

	          // Add one entry for each test class 
	          // or test suite.
	          suite.addTestSuite(TestHashPoly.class);
	          suite.addTestSuite(TestZobristNode.class);	          
	          suite.addTestSuite(TestZobrist.class);
	          suite.addTestSuite(TestZobrist2.class);

	          // For a master test suite, use this pattern. 
	          // (Note that here, it's recursive!) 
	          //suite.addTest(AnotherTestSuite.suite()); 

	          return suite; 
	     }

	

}
