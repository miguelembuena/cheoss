package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.util.*;

public class TestStaticCheckDetection extends TestCase implements Constants {
	
	
	public void testRook() {

		String pinnedByQueen = "k6q/8/8/8/3R4/8/8/K7 w - - 0 1";
		Board board = new Board(pinnedByQueen);
		assertTrue(board.getGenerator().isPiecePinned(WROOK, d4, d5, true) == BQUEEN);
		
		String pinnedByBishop = "k6b/8/8/8/3R4/8/8/K7 b - - 0 1 ";
		board = new Board(pinnedByBishop);
		assertTrue(board.getGenerator().isPiecePinned(WROOK, d4, d5, true) == BBISHOP);
		
		String noPinned = "k6q/8/8/8/3R4/8/8/1K6 b - - 0 1 ";
		board = new Board(noPinned);
		assertTrue(board.getGenerator().isPiecePinned(WROOK, d4, d5, true) == 0);
		
		String uncoverRook = "3k3b/8/8/8/8/8/1P1B4/K2R4 b - - 0 1 ";
		board = new Board(uncoverRook);
		assertTrue(board.getGenerator().isPiecePinned(WBISHOP, d2, e3, false) == WROOK);
		
		String knightDouble = "3k3b/8/3r1r2/8/8/8/1P1B1N2/K2R4 b - - 0 1 ";
		board = new Board(knightDouble);
		assertTrue(board.getGenerator().isKnightDoubleAttack(Pieces.KNIGHT_DIRECTIONS[e4], e4));
		
		assertFalse(board.getGenerator().isKnightDoubleAttack(Pieces.KNIGHT_DIRECTIONS[d3], d3));
		
		String knightDoubleAlt = "3k3b/8/3r1r2/8/1q3p2/8/1P1B1N2/K2R4 b - - 0 1";
		board = new Board(knightDoubleAlt);
		assertTrue(board.getGenerator().isKnightDoubleAttack(Pieces.KNIGHT_DIRECTIONS[e4], e4));		
		assertFalse(board.getGenerator().isKnightDoubleAttack(Pieces.KNIGHT_DIRECTIONS[d3], d3));
		assertFalse(board.getGenerator().isKnightDoubleAttack(Pieces.KNIGHT_DIRECTIONS[g4], g4));
		
		// 
		/*
		 	private boolean moveCauseCheck(int move) {
		//board.getGenerator().isPiecePinned(piece, moveFrom, moveTo, pinned)
		if (board.getGenerator().isPiecePinned(XMove.pieceMoved(move), XMove.from(move), XMove.to(move), false) != 0) {
			return true;
		}

		 */

	}
 }
