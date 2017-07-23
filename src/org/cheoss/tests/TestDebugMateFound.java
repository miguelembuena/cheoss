package org.cheoss.tests;

import junit.framework.*;

import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

public class TestDebugMateFound extends TestCase implements Constants {
	
	
	public void testMateIn() {
		

		
//		String fen = "r1bqkb1r/pp1nnppp/4p3/2P5/1P2N2P/4B3/P1P1NPP1/1R1QKB1R b Kkq - 0 11";
//		Board board = new Board(fen);
//		Node node = new Node(board);
//		for (int i = 1; i <= 3; i++) {
//			node.alphaBetaRoot(i, -INFINITY, INFINITY);
//			if (node.getBestLine().getValue() < -50000) break;
//		}
//		assertTrue(node.getBestLine().getValue() > -50000);
//
//
		System.out.println("");
		System.out.println("********* negras en 3 ");
		String fenMateIn3 = "3B1rk1/1B2bNpp/1pP2p2/pp5n/5n1r/RQP3Pq/R4P1P/6K1 w - -";
		assertEquals(3, jugadasToMate(fenMateIn3));
		//99995 negras mate en 3
		//depth 5 cuando se detecta en root
		
		System.out.println("");
		System.out.println("********* negras en 4 ");
		String fenNegrasEn4 = "8/5pp1/1B2p1k1/R5p1/4N1K1/P2nN1P1/5P1P/2r3n1 b - - 0 1";
		assertEquals(4, jugadasToMate(fenNegrasEn4));
		//99995 negras mate en 4
		//depth 5 cuando se detecta en root

		System.out.println("");
		System.out.println("********* negras en 3 falla!!!!!");
		String fenNegrasEn3 = "r1N2k1r/1p3p2/p2p4/2pP2p1/P2bq1p1/2P2PP1/1P2B1K1/R2Q1R2 b - - 0 1";
		assertTrue(jugadasToMate(fenNegrasEn3) > 2);//debe ser 3!!
		assertTrue(jugadasToMate(fenNegrasEn3) < 5);//debe ser 3!!
		//depth 5 cuando se detcta en root


		System.out.println("");
		System.out.println("********* blancas en 5 ");
		String fenBlancasEn5 = "4b2r/r1q3pk/3p1p1p/2p1pP1P/2P1P3/1R1P2Q1/1R2B2K/8 w - - 0 1";
		assertEquals(5, jugadasToMate(fenBlancasEn5));// debe ser 5!!
		//detcted rootdepth=3 depth=1
		// rootdepth 7 cuando se detecta en root
		//99993  blancas mate en 5

		
		
//		board = new Board(fenMateIn3);
//		node = new Node(board);
//		for (int i = 1; i <= 8; i++) {
//			node.alphaBetaRoot(i, -INFINITY, INFINITY);
//			if (node.getBestLine().getValue() > 80000) break;
//		}
//		//System.out.println("value = "+node.getBestLine().getValue());
//		assertTrue(node.getBestLine().getValue() > 80000);
		
		
//		String fenMateIn6 = "Bq1B1K2/3PpN2/P3Pp2/P1p2P2/2Pk1b1R/1p6/pN1P1P2/QR6 w - - ";
//		board = new Board(fenMateIn6);
//		node = new Node(board);
//		for (int i = 1; i <= 16; i++) {
//			node.alphaBetaRoot(i, -INFINITY, INFINITY);
//			if (node.getBestLine().getValue() > 80000) break;
//		}
//		assertTrue(node.getBestLine().getValue() > 80000);


	}
	
	private int jugadasToMate(String fen) {
		Board board = new Board(fen);
		Tree node = new Tree(board);
		node.searchToDepth(23); // si encuentra mate sale
		return Math.abs(node.mateIn());
	}
	
//	private int jugadasToMate(String fen) {
//		Board board = new Board(fen);
//		Node node = new Node(board);
//		int depth = 0;
//		for (depth = 1; depth <= 73; depth++) {
//			node.alphaBetaRoot(depth, -INFINITY, INFINITY);
//			//System.out.println("node.getBestLine().getValue = "+node.getBestLine().getValue());
//			if (node.getBestLine().getValue() > 80000) break;			
//		}
//		
//		
//		//return INFINITY - node.getBestLine().getValue();
//		return node.mateIn(depth);
//	}
	
	

	
 }
