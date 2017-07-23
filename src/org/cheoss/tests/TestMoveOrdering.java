package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.util.*;

public class TestMoveOrdering extends TestCase implements Constants {

	public void testOrdering() {
		Board board = new Board(FEN_START);
		int[] moves = new int[MAX_MOVES_IN_STACK];
		int numMoves = board.genLegalMoves(moves,0,0,0);
		
//		for (int i = 0; i < numMoves; i++) {
//			int move = moves[i];
//			String strMove = Interpreter.moveToPGN(move, board);
//			int score = XMove.score(move);
//			System.out.println("Move "+strMove+" score = "+score);
//			
//		}
		
		//String fen = "3k4/n5p1/8/8/r2Q2b1/8/8/4K3 w - - 0 1 ";
		String fen = "3k4/n5b1/8/8/3B4/8/1p3r2/4K3 w - - 0 1 ";
		board = new Board(fen);
		numMoves = board.genLegalMoves(moves,0,0,0);
		
		for (int i = 0; i < numMoves; i++) {
			int move = moves[i];
			String strMove = Interpreter.moveToPGN(move, board);
			int score = XMove.score(move);
			System.out.println("Move "+strMove+" score = "+score);			
		}
		System.out.println(" ************ ");
		System.out.println(" ************ ");
		System.out.println(" ************ ");
//		for (int i = 0; i < numMoves; i++) {
//			int move = MoveOrdering.next(moves, numMoves, i);
//			String strMove = Interpreter.moveToPGN(move, board);
//			int score = XMove.score(move);
//			System.out.println("Move "+strMove+" score = "+score);			
//		}		
		
		
	}
	

}
