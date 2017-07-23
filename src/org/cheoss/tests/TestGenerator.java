package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.util.*;


public class TestGenerator extends TestCase implements Constants {
	
	public void testGenMoves() {
		Board board = new Board(FEN_START);
		int[] moves = new int[MAX_MOVES_IN_STACK];
		int moveNumber = board.genPseudoLegalMoves(moves, 0, 0, 0);
		assertEquals(20, moveNumber);
	}
	public void testNumMoves() {
		// Posicion de prueba setp
		// 3x 2 reyes
		// 8 x 2 caballos
		// peones sin salto 1 +1
		// peones con salto 2 x 1 blanco
		// total movs blancos: 3+8+1+2=14
		// total movs negros: 3+8+1=12
		String pos01 = "k7/4p3/8/4N3/4n3/8/P3P3/7K w - - 0 1 ";
		Board board = new Board(pos01);
		int[] moves = new int[MAX_MOVES_IN_STACK];
		assertTrue(board.isWhiteTurn());
		int numWhite = board.genPseudoLegalMoves(moves, 0, 0, 0);
		assertEquals(14, numWhite);
		assertTrue(board.isWhiteTurn());
		int move =Interpreter.uciStrToMove("a2a4", board);
		board.doMove(move);
		assertFalse(board.isWhiteTurn());
		int numBlack = board.genPseudoLegalMoves(moves, 0, 0, 0);
		assertEquals(12, numBlack);
		assertFalse(board.isWhiteTurn());
	}
	
	public void testGenQuiesMoves() {
		
		Board board = new Board(FEN_START);
		int[] moves = new int[MAX_MOVES_IN_STACK];
		int moveNumber = 0;
		
		moveNumber = board.genPseudoLegalQuiesMoves(moves);
		assertEquals(0, moveNumber);
		
		
		// 1 captura de rey, 1 de torre, 1 de alfil, 
		//1 de caballo, 1 de peon, 1 de dama
		// total 6 capturas
		
		String fen = "8/8/8/Q7/p7/1P6/pR6/KBN4k w - - 0 1";
		board = new Board(fen);
		moveNumber = board.genPseudoLegalQuiesMoves(moves);
		assertEquals(6, moveNumber);
		
		// mismas capturas que anterior + una captura mas de dama + promociones
		//1 normal, 2 capturas+promo. Un peon bloqueado
		// en septima para probar que no lo cuenta
		// total 6 anterior + 1 + 3 promos = 10
		
		
		fen = "3r1r1n/4P2P/8/Q7/p7/1P6/pR6/KBN4k w - - 0 1 ";
		board = new Board(fen);
		moveNumber = board.genPseudoLegalQuiesMoves(moves);
		assertEquals(10, moveNumber);
		
				
//		// solo una comida al paso en f6
		
		fen = "k7/8/8/4Pp2/8/8/8/7K w - f6 0 1 ";
		board = new Board(fen);
		moveNumber = board.genPseudoLegalQuiesMoves(moves);
		assertEquals(1, moveNumber);
		
		
		// Juegan negras, 1 comida al paso + 1 promo + 1 promo+captura
		fen = "8/8/4k3/8/6pP/8/p7/1N2K3 b - h3 0 1 ";
		board = new Board(fen);
		moveNumber = board.genPseudoLegalQuiesMoves(moves);
		assertEquals(3, moveNumber);
		
	}
	
	//// legal generator
	
	
	public void testLegalMoves() {
		// 2 piezas blancas clavadas, solo es legal mover el rey a
		// f1 porque en h1 o h2 se quedaria en jaque
		String fen = "6rk/b7/8/8/7r/8/5RN1/6K1 w - - 0 1 ";
		Board board = new Board(fen);	
		int[] moves = new int[MAX_MOVES_IN_STACK];
		
		assertTrue( board.genPseudoLegalMoves(moves, 0,0,0) > 1);
		
		assertEquals(1, board.genLegalMoves(moves, 0, 0, 0));
		
		// igual que posicion anterior pero la torre ya no esta clavada
		// las blancas tienen ahora 12 moves de torre, total 13
		fen = "6rk/8/8/8/7r/8/5RN1/6K1 w - - 0 1 ";
		board = new Board(fen);
		assertEquals(13, board.genLegalMoves(moves, 0, 0, 0));
	}
	
	public void testLegalWithCheckVerticalRook() {
		String fen = "k7/8/8/8/8/6r1/8/7K w - - 0 1 ";
		Board board = new Board(fen);
		int[] moves = new int[MAX_MOVES_IN_STACK];
		assertEquals(1, board.genLegalMoves(moves, 0, 0, 0));

	}


}
