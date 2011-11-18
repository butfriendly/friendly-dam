package de.soulworks.dam;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class ApplicationTest
		extends TestCase {

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public ApplicationTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ApplicationTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}
}
