package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.util.*;

public class TestXBoard extends TestCase implements Constants{
	
	public void testCol() {
		assertEquals(2, XBoard.column(110));
		assertEquals(3, XBoard.column(99));
		assertEquals(4, XBoard.column(52));
		assertEquals(7, XBoard.column(31));
		assertEquals(9, XBoard.column(69));
	}

	public void testRank() {
		assertEquals(9, XBoard.row(110));
		assertEquals(8, XBoard.row(99));
		assertEquals(4, XBoard.row(52));
		assertEquals(2, XBoard.row(31));
		assertEquals(5, XBoard.row(69));
	}
	
	public void testDistance() {
		int a = 69;
		int b = 81;
		int c = 38;
		int d = 74;
		int e = 28;
		int f = 54;
		int g = 110;
		int h = 33;
		
		assertEquals(1, XBoard.distance(a, b));
		assertEquals(7, XBoard.distance(a, c));
		assertEquals(7, XBoard.distance(a, d));
		assertEquals(5, XBoard.distance(a, e));
		assertEquals(3, XBoard.distance(a, f));
		assertEquals(7, XBoard.distance(g, h));		
		assertEquals(3, XBoard.distance(d, g));
		
		
	}

}
