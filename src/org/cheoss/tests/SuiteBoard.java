package org.cheoss.tests;

import junit.framework.*;

public class SuiteBoard extends TestSuite {
	
 	     public static Test suite() { 
	          TestSuite suite = new TestSuite("Board Suite");

	          // Add one entry for each test class 
	          // or test suite.
	          suite.addTestSuite(TestFen.class);
	          suite.addTestSuite(TestGenerator.class);
	          suite.addTestSuite(TestInterpreter.class);
	          suite.addTestSuite(TestLocator.class);
	          suite.addTestSuite(TestBoard.class);
	          suite.addTestSuite(TestRepeatDoMove.class);
	          suite.addTestSuite(TestPerft.class);

	          // For a master test suite, use this pattern. 
	          // (Note that here, it's recursive!) 
	          //suite.addTest(AnotherTestSuite.suite()); 

	          return suite; 
	     }

	

}
