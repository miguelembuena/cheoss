package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

//legal tarda 106328
// pseudo tarda 70156

//Rhino:
	//PerfTest depth 6 time: 54297
	//nodes: 119060324 captures = 2812008 eps = 5248

	//PerfTest depth 1 time: 0
	//nodes: 20 captures = 0 eps = 0
	//PerfTest depth 2 time: 15
	//nodes: 400 captures = 0 eps = 0
	//PerfTest depth 3 time: 31
	//nodes: 8902 captures = 34 eps = 0
	//PerfTest depth 4 time: 140
	//nodes: 197281 captures = 1576 eps = 0
	//PerfTest depth 5 time: 2203
	//nodes: 4865609 captures = 82719 eps = 258
	//PerfTest depth 6 time: 53375
	//nodes: 119060324 captures = 2812008 eps = 5248


public class TestPerft extends TestCase implements Constants {
	
	public void testPerft() {
		//http://chessprogramming.wikispaces.com/Perft+Results
		int[] numsNodes = {0, 20, 400, 8902, 197281, 4865609, 119060324};
		int[] numsCaptures = {0, 0, 0, 34, 1576, 82719, 2812008};
		int[] numsEps = {0, 0, 0, 0, 0, 258, 5248 };
		int[] numsChecks = {0, 0, 0, 12, 469, 27351, 809099 };
		int nodes = 0;
		int captures = 0;
		int eps = 0;
		int numChecks = 0;
		
		long t = System.currentTimeMillis();
		for (int i = 1; i <= 5; i++) {
			Board board = new Board(FEN_START);
			PerftResults perftResult = board.perft(i);
			
			long tiempo = System.currentTimeMillis() - t;
			nodes = perftResult.getPerftNodes();
			captures = perftResult.getPerftCaptures();
			eps = perftResult.getPerftEps();
			numChecks = perftResult.getPerftChecks();
			assertEquals(numsNodes[i], nodes);			
			assertEquals(numsCaptures[i], captures);
			assertEquals(numsEps[i], eps);
			assertEquals(numsChecks[i], numChecks);
			System.out.println("PerfTest depth "+i+" time: "+tiempo);
			System.out.println(perftResult.toString());
//			System.out.println("   nodes: "+nodes+" captures = "+captures+" eps = "+eps+" checks = "+numChecks);		
		}
	}
	
//	 // This position is very good because it catches many possible bugs.
//	"r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1";	
	public void testPerftPos2() {
		int[] numsNodes = {0, 48, 2039, 97862, 4085603, 193690690};
		int[] numsCaptures = {0, 8, 351, 17102, 757163, 35043416};
		int[] numsEps = {0, 0, 1, 45, 1929, 73365};
		int[] castles = {0, 2, 91, 3162, 128013, 4993637};
		int[] promotions = {0, 0, 0, 0, 15172, 8392 };
		int[] numsChecks = {0, 0, 3, 993, 25523, 3309887};
		int nodes = 0;
		int captures = 0;
		int eps = 0;
		int numChecks = 0;
		int ncastles = 0;
		int promos = 0;
		long t = System.currentTimeMillis();
		for (int i = 1; i <= 4; i++) {
			Board board = new Board("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
			
			PerftResults perftResult = board.perft(i);
			
			long tiempo = System.currentTimeMillis() - t;
			nodes = perftResult.getPerftNodes();
			captures = perftResult.getPerftCaptures();
			eps = perftResult.getPerftEps();
			numChecks = perftResult.getPerftChecks();
			ncastles = perftResult.getPerftCastlings();
			promos = perftResult.getPerftPromos();
			
			assertEquals(numsNodes[i], nodes);			
			assertEquals(numsCaptures[i], captures);
			assertEquals(numsEps[i], eps);
			assertEquals(numsChecks[i], numChecks);
			assertEquals(castles[i], ncastles );
			assertEquals(promotions[i], promos);

			System.out.println("PerfTest depth "+i+" time: "+tiempo);
			System.out.println(perftResult.toString());
//			System.out.println("   nodes: "+nodes+" captures = "+captures+" eps = "+eps+
//					" checks = "+numChecks+" castles = "+ncastles+" promos = "+promos);		
		}
	}
	
	//11030083
//	940350
//	33325
//	0
//	7552
//	452473
//	2733
	public void testPerftPos3() {
		int[] numsNodes = {11030083};
		int[] numsCaptures = {940350};
		int[] numsEps = {33325};
		int[] castles = {0};
		int[] promotions = {7552};
		int[] numsChecks = {452473};
		int nodes = 0;
		int captures = 0;
		int eps = 0;
		int numChecks = 0;
		int ncastles = 0;
		int promos = 0;
		Board board = new Board("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
		long t = System.currentTimeMillis();
		PerftResults perftResults = board.perft(6);
		long tiempo = System.currentTimeMillis() - t;
		nodes = perftResults.getPerftNodes();
		captures = perftResults.getPerftCaptures();
		eps = perftResults.getPerftEps();
		numChecks = perftResults.getPerftChecks();
		ncastles = perftResults.getPerftCastlings();
		promos = perftResults.getPerftPromos();
		
		assertEquals(numsNodes[0], nodes);			
		assertEquals(numsCaptures[0], captures);
		assertEquals(numsEps[0], eps);
		assertEquals(numsChecks[0], numChecks);
		assertEquals(castles[0], ncastles );
		assertEquals(promotions[0], promos);

		System.out.println("PerfTest depth "+6+" time: "+tiempo);
		System.out.println(perftResults.toString());
//		System.out.println("   nodes: "+nodes+" captures = "+captures+" eps = "+eps+
//				" checks = "+numChecks+" castles = "+ncastles+" promos = "+promos);		

	}
	
	public void testPerftPromos() {
		Board board = new Board("8/4P3/8/8/8/8/8/k6K w - - 0 1 ");
		PerftResults perftResults = board.perft(1);
		assertEquals(4, perftResults.getPerftPromos());		
	}
}
