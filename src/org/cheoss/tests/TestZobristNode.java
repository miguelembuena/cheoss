package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

public class TestZobristNode extends TestCase implements Constants {
	
	public void testDebugTT() {
		String fen = "";
		long key = 0l;
	
		Board board = new Board(FEN_START);
		Tree node = new Tree(board);
		node.searchToDepth(5);
		System.out.println("fen tras search 1 ="+board.getFEN());
		
		board.doUciMove("a2a4");
		System.out.println("fen tras move 1 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());
		
		board.doUciMove("b8c6");
		System.out.println("fen tras move 2 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());


		node.searchToDepth(5);
		System.out.println("fen tras search 2 ="+board.getFEN());
		board.doUciMove("d2d4");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("d7d5");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		System.out.println("fen tras search 3 ="+board.getFEN());
		board.doUciMove("g1f3");
		System.out.println("fen tras doUciMove g1f3 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("g8f6");
		System.out.println("fen tras doUciMove g8f6 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		System.out.println("fen tras search 4 ="+board.getFEN());
		board.doUciMove("b1c3");
		System.out.println("fen tras doUciMove b1c3 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("c8g4");
		System.out.println("fen tras doUciMove c8g4 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());
				
		node.searchToDepth(5);
		System.out.println("fen tras search 4 ="+board.getFEN());
		board.doUciMove("f3e5");		
		System.out.println("fen tras doUciMove f3e5 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("e7e6");
		System.out.println("fen tras doUciMove e7e6 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);		
		System.out.println("fen tras search ="+board.getFEN());		
		board.doUciMove("e5c6");
		System.out.println("fen tras doUciMove e5c6 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();////////// la primera captura falla
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("b7c6");
		System.out.println("fen tras doUciMove b7c6 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		System.out.println("fen tras search ="+board.getFEN());
		board.doUciMove("h2h3");
		System.out.println("fen tras doUciMove h2h3 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("g4h5");
		System.out.println("fen tras doUciMove g4h5 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		System.out.println("fen tras search ="+board.getFEN());
		board.doUciMove("g2g4");
		System.out.println("fen tras doUciMove g2g4 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("h5g6");
		System.out.println("fen tras doUciMove h5g6 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		System.out.println("fen tras search ="+board.getFEN());
		board.doUciMove("g4g5");
		System.out.println("fen tras doUciMove g4g5 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("f6d7");
		System.out.println("fen tras doUciMove f6d7 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		System.out.println("fen tras search ="+board.getFEN());
		board.doUciMove("f1g2");
		System.out.println("fen tras doUciMove f1g2 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("f8e7");
		System.out.println("fen tras doUciMove f8e7 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		System.out.println("fen tras search ="+board.getFEN());
		board.doUciMove("d1d2");
		System.out.println("fen tras doUciMove d1d2 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("e8g8");
		System.out.println("fen tras doUciMove e8g8 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();///////next fail!! jeje el enroque
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		System.out.println("fen tras search ="+board.getFEN());
		board.doUciMove("e2e4");
		System.out.println("fen tras doUciMove e2e4 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("h7h5");
		System.out.println("fen tras doUciMove h7h5 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		System.out.println("===> fen tras search ="+board.getFEN());
		board.doUciMove("g5h6");
		System.out.println("fen tras doUciMove g5h6 ="+board.getFEN());
		fen = board.getFEN();
		key = board.getKey();//next fail, que será?? al paso creo
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("g7h6");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		
		node.searchToDepth(5);
		board.doUciMove("d2h6");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("g6h5");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		board.doUciMove("h6h5");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("f7f5");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		board.doUciMove("h1g1");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("d7f6");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		node.searchToDepth(5);
		board.doUciMove("g2f3");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());

		board.doUciMove("f6g4");
		fen = board.getFEN();
		key = board.getKey();
		assertEquals(key, new Board(fen).getKey());
		
	}
	
//	public void testMateInminente() {
//		String fen = "R7/1pQ5/k5p1/5p2/4N3/1q6/1P5P/7K b - - 2 41";
//		Board board = new Board(fen);
//		runThis(new Node(board), 5);
//		
//	}
	
	public void testPromos() {
		String fen = "8/4P3/8/8/1k6/8/8/1K6 w - - 0 1 ";
		Board board = new Board(fen);
		board.doUciMove("e7e8q");
		assertEquals(new Board("4Q3/8/8/8/1k6/8/8/1K6 b - - 0 1").getKey(), board.getKey());

		board = new Board(fen);
		board.doUciMove("e7e8n");
		assertEquals(new Board("4N3/8/8/8/1k6/8/8/1K6 b - - 0 1 ").getKey(), board.getKey());
		
		board = new Board(fen);
		board.doUciMove("e7e8r");
		assertEquals(new Board("4R3/8/8/8/1k6/8/8/1K6 b - - 0 1 ").getKey(), board.getKey());		

		
		fen = "7r/6P1/8/8/1k6/8/8/1K6 w - - 0 1 "; 
		board = new Board(fen);
		board.doUciMove("g7h8r");
		assertEquals(new Board("7R/8/8/8/1k6/8/8/1K6 b - - 0 1 ").getKey(), board.getKey());
	}
	
	
	
//	gmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm nMvs=19 nummoves=20
//	fen=rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R b KQkq - 1 1
//	0=a7a6  falta, es un move que no está en la lista de xgen  
//	0=      
// cazado, era un localIndex que se ponia a cero en un metodo indebidamente. 
//Eso me pasa por tener variables globales rondando por ahí

	
	
//	gmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm nMvs=31 nummoves=32
//	fen=r1bqkbnr/ppp2ppp/2n5/3pP3/P7/8/1PP1PPPP/RNBQKBNR w KQkq d6 0 4
//	5=e5d6  23=d1d5 
//	        30=d1d5
	// cazado, estaba comentado el bloque de capturas en passants


//	gmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm nMvs=28 nummoves=31
//	fen=r2q1rk1/p1pn1pP1/2p1p1b1/3p4/P2PP3/2N4P/1PPb1PB1/R1B1K2R w KQ - 0 15
//	4=g7f8q 5=g7f8r 6=g7f8b 7=g7f8n 8=b2b3 9=b2b4 10=e4d5 11=e4e5 12=a1a2 13=a1a3 14=a1b1 15=g2f1 16=g2f3 17=c1d2 18=h1h2 19=h1g1 20=h1f1 21=c3a2 22=c3e2 23=c3b1 24=c3d5 25=c3d1 26=c3b5 27=e1d2 28=e1e2 29=e1f1 30=e1d1 ---------
//	7=b2b3 8=b2b4 9=e4e5 10=a1a2 11=a1a3 12=a1b1 13=g2f1 14=g2f3 15=h1h2 16=h1g1 17=h1f1 18=c3a2 19=c3e2 20=c3b1 21=c3d1 22=c3b5 23=e1e2 24=e1f1 25=e1d1 26=c3d5 27=e1d2     0=g7f8q 1=c1d2 2=e4d5  
// cazado, era un bug en quiet promo, que por copia pega lo hacia como una captura
	
 }
