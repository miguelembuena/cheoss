package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.util.*;

public class TestInterpreter extends TestCase implements Constants {
	
	public void testPromoBug() {
		String f1 = "5Q2/1k6/8/8/8/8/8/1K6 w - - 0 1";
		Board board = new Board(f1);
		String fenIni = board.getFEN();
		assertEquals(f1, fenIni);
		//System.out.println("inicio  "+board.getFEN());
		
		board.doUciMove("f8e7");
		String fenDos = "8/1k2Q3/8/8/8/8/8/1K6 b - - 1 1";
		assertEquals(fenDos, board.getFEN());
		
		board.doUciMove("b7a8");
		String fenTres = "k7/4Q3/8/8/8/8/8/1K6 w - - 2 2";
		assertEquals(fenTres, board.getFEN());
			
		
//		System.out.println("1       "+board.getFEN());
//		board.undoUciMove("f8e7");
//		System.out.println("2       "+board.getFEN());
	}
	
	
	public void testSetAlgebraicSequence() {
		String sequence = "1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6 3.Af1-b5 a7-a6 4.Ab5-a4 b7-b5 5.Aa4-b3 Cg8-f6 6.0-0";
		//aux.setAlgebraicSequence(sequence);
		//Interpreter.stringToListOfStringMoves(sequence);
		assertEquals(11, Interpreter.stringToListOfStringMoves(sequence).size());
	}
	
	public void testMoveToUci() {
		int move = XMove.pack(e2, e4, WPAWN, 0, 0);
		String uci = Interpreter.moveToUci(move);
		assertEquals("e2e4", uci);
		move = XMove.pack(g1, f3, WKNIGHT, 0, 0);
		uci = Interpreter.moveToUci(move);
		assertEquals("g1f3", uci);
		
		// Promotions
		move = XMove.pack(e7, e8, WPAWN, 0, FLAG_PROMO_QUEEN);
		assertEquals("e7e8q", Interpreter.moveToUci(move));
		
		move = XMove.pack(e7, e8, WPAWN, 0, FLAG_PROMO_ROOK);
		assertEquals("e7e8r", Interpreter.moveToUci(move));
		
		move = XMove.pack(e7, e8, WPAWN, 0, FLAG_PROMO_BISHOP);
		assertEquals("e7e8b", Interpreter.moveToUci(move));
		
		move = XMove.pack(e7, e8, WPAWN, 0, FLAG_PROMO_KNIGHT);
		assertEquals("e7e8n", Interpreter.moveToUci(move));
		
		// Castlings ¿es 0-0 o e1g1??


		
		
//		//peon blanco y negro en séptima
//		String pos1 = "8/4P3/8/8/8/8/7p/K1k5 w - - 0 1";
	}
	
	
	public void testStrUciToMove() {
		// testing enpasant in int conversion
		String fen = "rnb1k2r/1pp2ppp/p4n2/3qN3/2Pp1P2/P7/P2PP1PP/R1BQKB1R b KQkq c3 0 8";
		Board board = new Board(fen);
		String strmove = "d4c3";
		
		int moveWitEnPasant = XMove.pack(d4, c3, BPAWN, WPAWN, FLAG_EN_PASSANT_CAPTURE);
		

		boolean areEquals = XMove.equals(moveWitEnPasant, Interpreter.uciStrToMove(strmove, board)); 
		assertTrue( areEquals );

		
		// promos
		fen = "8/4P3/8/8/8/8/8/4k2K w - c3 0 8 ";
		board = new Board(fen);
		
		String strMovePromoQueen = "e7e8q";
		int movePromoQueen = XMove.pack(e7, e8, WPAWN, 0, FLAG_PROMO_QUEEN);
		String strMovePromoRook = "e7e8r";
		int movePromoRook = XMove.pack(e7, e8, WPAWN, 0, FLAG_PROMO_ROOK);
		
		areEquals = XMove.equals(movePromoQueen, Interpreter.uciStrToMove(strMovePromoQueen, board));		
		assertTrue( areEquals );
		
		areEquals = XMove.equals(movePromoRook, Interpreter.uciStrToMove(strMovePromoRook, board));
		assertTrue( areEquals );
		
	}
	
 }
