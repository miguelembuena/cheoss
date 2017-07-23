package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.evaluation.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

public class TestEvaluator extends TestCase implements Constants, IConstantsEval {
	
	public int eval() {
		return 0;
	}
	
	public void testGamePhase() {
		Board board = new Board(FEN_START);
		//board.getMaterialBlack() = 24045
//		System.out.println("board.getMaterialBlack() = "+board.getMaterialBlack());
//		
//		int expr = (24045 + 24045) - ( QUEEN_VALUE + QUEEN_VALUE + ROOK_VALUE );
//		
//		System.out.println("limite = "+expr);
		
		
		assertFalse(board.isEndGame());
		
		String fen = "1nbb2k1/pppppppp/8/8/8/8/PPPPPPPP/R2Q1BKR w - - 2 1 ";
		board = new Board(fen);
		assertFalse(board.isEndGame());
		
		fen = "rnb1kbnr/pppppppp/8/8/8/8/PPPPPPPP/RNB1KBNR w KQkq - 0 1 ";
		board = new Board(fen);
		assertFalse(board.isEndGame());
		
		
		fen = "rn2k1nr/pppppppp/8/8/8/8/PPPPPPPP/RN2K1NR w KQkq - 0 1";
		board = new Board(fen);
		assertFalse(board.isEndGame());
		
		 
		fen = "3qk3/pppppppp/8/8/8/8/PPPPPPPP/3QK3 w - - 0 1";
		board = new Board(fen);
		assertTrue(board.isEndGame());
		
		fen = "3qk3/pppppppp/8/8/8/8/PPPPPPPP/3QKN2 w - - 0 1 ";
		board = new Board(fen);
		assertTrue(board.isEndGame());

		fen = "3qkn2/pppppppp/8/8/8/8/PPPPPPPP/3QKN2 w - - 0 1 ";
		board = new Board(fen);
		assertTrue(board.isEndGame());
		
		fen = "3qkn2/pppppppp/8/8/8/8/PPPPPPPP/2NQKN2 w - - 0 1 ";
		board = new Board(fen);
		assertTrue(board.isEndGame());
		
		fen = "2nqkn2/pppppppp/8/8/8/8/PPPPPPPP/2NQKN2 w - - 0 1 ";
		board = new Board(fen);
		assertFalse(board.isEndGame());
		
		fen = "2nqkn2/pppppppp/8/8/8/8/PPPPPPPP/2NQKNB1 w - - 0 1 ";
		board = new Board(fen);
		assertFalse(board.isEndGame());
		
		fen = "1rnqkn2/2ppp3/8/8/8/8/3PPP2/2NQKRB1 w - - 0 1 ";
		board = new Board(fen);
		assertFalse(board.isEndGame());
		
		
		
	}

}
