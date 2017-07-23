package org.cheoss.tests;

import java.util.*;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.util.*;

public class TestLocator extends TestCase implements Constants {
	
	// en pasant, enroques, capturas, promociones
	// initial pos r3k3/ppppp3/8/8/8/8/PPPPP3/4K2R w Kq - 0 1 
	// final pos r7/1kpp4/8/4p3/8/8/PP1PP3/5RK1 w - - 0 7 	
	public void testSetFenPositionStart() {
		Locator locator = new Locator();
		locator.addPiece(WPAWN, a2);
		locator.addPiece(WPAWN, b2);
		locator.addPiece(WPAWN, c2);
		locator.addPiece(WPAWN, d2);
		locator.addPiece(WPAWN, e2);		
		locator.addPiece(WKING, e1);		
		locator.addPiece(WROOK, h1);
		
		locator.addPiece(BPAWN, a7);
		locator.addPiece(BPAWN, b7);
		locator.addPiece(BPAWN, c7);
		locator.addPiece(BPAWN, d7);
		locator.addPiece(BPAWN, e7);		
		locator.addPiece(BKING, e8);
		locator.addPiece(BROOK, h8);
		
		assertTrue(foundInLocator(locator, BPAWN, a7));
		assertTrue(foundInLocator(locator, BPAWN, b7));
		assertTrue(foundInLocator(locator, BPAWN, c7));
		assertTrue(foundInLocator(locator, BPAWN, d7));
		assertTrue(foundInLocator(locator, BPAWN, e7));
		assertTrue(foundInLocator(locator, BKING, e8));
		assertTrue(foundInLocator(locator, BROOK, h8));
		
		assertTrue(foundInLocator(locator, WPAWN, a2));
		assertTrue(foundInLocator(locator, WPAWN, b2));
		assertTrue(foundInLocator(locator, WPAWN, c2));
		assertTrue(foundInLocator(locator, WPAWN, d2));
		assertTrue(foundInLocator(locator, WPAWN, e2));
		assertTrue(foundInLocator(locator, WKING, e1));
		assertTrue(foundInLocator(locator, WROOK, h1));
		
		int[] moves = {
				XMove.pack(c2, c4, WPAWN, 0, FLAG_PAWN_JUMP), BLACK_LONG_CASTLING,
				XMove.pack(c4, c5, WPAWN, 0, 0), XMove.pack(b7, b5, BPAWN, 0, FLAG_PAWN_JUMP),
				XMove.pack(c5, b6, WPAWN, BPAWN, FLAG_EN_PASSANT_CAPTURE), XMove.pack(e7, e5, BPAWN, 0, FLAG_PAWN_JUMP),
				WHITE_SHORT_CASTLING, XMove.pack(d8, e8, BROOK, 0, 0), 
				XMove.pack(b6, a7, WPAWN, BPAWN, 0), XMove.pack(c8, b7, BKING, 0, 0),
				XMove.pack(a7, a8, WPAWN, 0, FLAG_PROMO_QUEEN), XMove.pack(e8, h8, BROOK, WQUEEN, 0)
		};
		
		for (int move : moves) {
			locator.doMove(move);
		}		
		
		assertTrue( foundInLocator(locator, BROOK, a8) );
		assertTrue( foundInLocator(locator, BKING, b7) );
		assertTrue( foundInLocator(locator, BPAWN, c7) );
		assertTrue( foundInLocator(locator, BPAWN, d7) );
		assertTrue( foundInLocator(locator, BPAWN, e5) );		
		assertTrue( foundInLocator(locator, WPAWN, a2) );
		assertTrue( foundInLocator(locator, WPAWN, b2) );
		assertTrue( foundInLocator(locator, WPAWN, d2) );
		assertTrue( foundInLocator(locator, WPAWN, e2) );		
		assertTrue( foundInLocator(locator, WROOK, f1) );
		assertTrue( foundInLocator(locator, WKING, g1) );		
		assertEquals(3, count(locator.getBPawnSet()));
		assertEquals(4, count(locator.getWPawnSet()));
		assertEquals(4, count(locator.getPiecesSet()));
		
		for (int i = moves.length-1; i >= 0; i--) {
			locator.undoMove(moves[i]);
		}
		
		assertTrue(foundInLocator(locator, BPAWN, a7));
		assertTrue(foundInLocator(locator, BPAWN, b7));
		assertTrue(foundInLocator(locator, BPAWN, c7));
		assertTrue(foundInLocator(locator, BPAWN, d7));
		assertTrue(foundInLocator(locator, BPAWN, e7));
		assertTrue(foundInLocator(locator, BKING, e8));
		assertTrue(foundInLocator(locator, BROOK, h8));
		
		assertTrue(foundInLocator(locator, WPAWN, a2));
		assertTrue(foundInLocator(locator, WPAWN, b2));
		assertTrue(foundInLocator(locator, WPAWN, c2));
		assertTrue(foundInLocator(locator, WPAWN, d2));
		assertTrue(foundInLocator(locator, WPAWN, e2));
		assertTrue(foundInLocator(locator, WKING, e1));
		assertTrue(foundInLocator(locator, WROOK, h1));
		
		assertEquals(5, count(locator.getBPawnSet()));
		assertEquals(5, count(locator.getWPawnSet()));
		assertEquals(4, count(locator.getPiecesSet()));
	}
	
	public void testKingFirst() {
		Board board = new Board(FEN_START);
		Locator locator = board.getLocator();
		assertTrue(foundInLocator(locator, WPAWN, d2));
		assertTrue(foundInLocator(locator, WKING, e1));
		assertTrue(foundInLocator(locator, BKING, e8));
		String game = "1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6 3.Af1-b5 a7-a6 4.Ab5xc6 b7xc6 5.0-0 Cg8-f6 "+
		"6.Cb1-c3 Ac8-b7 7.b2-b3 d7-d5 8.Ac1-b2 d5xe4 9.Cf3xe5 Af8-c5 10.Tf1-e1 0-0 "+
		"11.Te1xe4 Cf6xe4 12.Ce5xc6 Ac5xf2+ 13.Rg1-f1 Ab7xc6 14.Cc3xe4 Tf8-e8 15.Rf1xf2 "+
		"Te8xe4 16.d2-d3 Ta8-b8 17.Dd1-f3 Dd8xd3 18.Df3xe4 Ac6xe4 19.c2xd3 Ae4xd3 2"+
		"0.Ta1-d1 Tb8xb3 21.Td1xd3 Tb3xb2+ 22.Rf2-e3 Tb2xa2 23.Td3-d7 h7-h6 24.Td"+
		"7xc7 Ta2xg2 25.Tc7-a7 Tg2xh2 26.Ta7xa6";
		
		ArrayList<String> strmoves = Interpreter.stringToListOfStringMoves(game);
		
		ArrayList<Integer> moves = new ArrayList<Integer>();
		
		for (String strMove : strmoves) {
			int move = Interpreter.algebraicToMove(strMove, board);
			board.doMove(move);
			moves.add(move);
		}
	}
	
	public void testStartPos() {
		Board board = new Board(FEN_START);
		int numPieces = count(board.getLocator().getPiecesSet());
		int wpawns = count(board.getLocator().getWPawnSet());
		int bpawns = count(board.getLocator().getBPawnSet());		
		assertEquals(16, numPieces);
		assertEquals(8, wpawns);
		assertEquals(8, bpawns);		
	}
	
	
	//1r3Nk1/1b3ppp/1p1b4/8/1P2P1n1/8/2QPqPPP/1R3RK1 b - - 0 18 
	public void testChangingPos() {
		Board board = new Board(FEN_START);
		
		int numPieces = count(board.getLocator().getPiecesSet());
		int wpawns = count(board.getLocator().getWPawnSet());
		int bpawns = count(board.getLocator().getBPawnSet());		
		assertEquals(16, numPieces);
		assertEquals(8, wpawns);
		assertEquals(8, bpawns);
		
		board = new Board("1r3Nk1/1b3ppp/1p1b4/8/1P2P1n1/8/2QPqPPP/1R3RK1 b - - 0 18");		
		numPieces = count(board.getLocator().getPiecesSet());
		wpawns = count(board.getLocator().getWPawnSet());
		bpawns = count(board.getLocator().getBPawnSet());		
		assertEquals(11, numPieces);
		assertEquals(6, wpawns);
		assertEquals(4, bpawns);
		assertTrue(foundInLocator(board.getLocator(), WQUEEN, c2));
		assertTrue(foundInLocator(board.getLocator(), BQUEEN, e2));
		assertTrue(foundInLocator(board.getLocator(), BKNIGHT, g4));
		assertTrue(foundInLocator(board.getLocator(), WKNIGHT, f1));
	}

	
	public static int count(int[] set) {
		int matches = 0;
		for (int sq : set) {
			if (sq != 0) matches++;
		}
		return matches;
	}
	
	public static boolean foundInLocator(Locator locator, int pieceSet, int square) {
		int[] set;
		if (pieceSet == WPAWN) {
			set = locator.getWPawnSet();
		}
		else if (pieceSet == BPAWN) {
			set = locator.getBPawnSet();
		}
		else {
			set = locator.getPiecesSet();
		}
		
		for (int sq : set) {
			if (sq == square) return true;
		}
		
		return false;
	}

	

}
