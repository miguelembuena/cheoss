package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

public class TestPositionForHash extends TestCase implements Constants {
	
	
	
	public void testNPS() {

		Board board = new Board("kn6/2B5/8/1P6/1K6/P7/8/8 b - - 0 1");
		Tree tree = new Tree(board);
		long start = System.currentTimeMillis();
		tree.searchToDepth(19);
		System.out.println("tiempo = "+(System.currentTimeMillis()-start));

		
	}
	
}
