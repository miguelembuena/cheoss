package org.cheoss.tests;

import junit.framework.*;

import org.cheoss.board.*;
import org.cheoss.util.*;

public class TestFen extends TestCase implements Constants {
	//http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation

	public void testSetFenPositionStart() {
		Board board = new Board(FEN_START);
		
		for (int i = 0; i < NUMERIC_START.length; i++) {
			assertEquals(NUMERIC_START[i], board.pieceAt(i));	
		}
		
		assertEquals(true, board.isWhiteTurn());
		assertEquals(true, board.getCsWhiteShort() == 1);
		assertEquals(true, board.getCsWhiteLong() == 1);
		assertEquals(true, board.getCsBlackShort() == 1);
		assertEquals(true, board.getCsBlackLong() == 1 );
	}
	
 
	public void testSetFenPositionAllCastlings() {
		String ALL_CASTLINGS = "r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1";
		Board board = new Board(ALL_CASTLINGS);
		
		assertEquals(WKING, board.pieceAt(e1));
		assertEquals(WROOK, board.pieceAt(a1));
		assertEquals(WROOK, board.pieceAt(h1));
		assertEquals(BKING, board.pieceAt(e8));
		assertEquals(BROOK, board.pieceAt(a8));
		assertEquals(BROOK, board.pieceAt(h8));
		assertEquals(true, board.isWhiteTurn());
		assertEquals(true, board.getCsWhiteShort() == 1);
		assertEquals(true, board.getCsWhiteLong() == 1);
		assertEquals(true, board.getCsBlackShort() == 1);
		assertEquals(true, board.getCsBlackLong() == 1 );
	}
	
	public void testSetFenPositionWhiteCastled() {
		
		String WHITE_CASTLED = "r3k2r/8/8/8/8/8/8/R4RK1 b kq - 1 1";
		Board board = new Board(WHITE_CASTLED);
		
		assertEquals(WKING, board.pieceAt(g1));
		assertEquals(WROOK, board.pieceAt(f1));
		assertEquals(WROOK, board.pieceAt(a1));
		assertEquals(BKING, board.pieceAt(e8));
		assertEquals(BROOK, board.pieceAt(a8));
		assertEquals(BROOK, board.pieceAt(h8));
		assertEquals(false, board.isWhiteTurn());
		assertEquals(false, board.getCsWhiteShort() == 1);
		assertEquals(false, board.getCsWhiteLong() == 1);
		assertEquals(true, board.getCsBlackShort() == 1);
		assertEquals(true, board.getCsBlackLong() == 1 );
	}

	public void testSetFenPositionBothCastledAndResetOK() {
		String BOTH_CASTLED = "2kr3r/8/8/8/8/8/8/R4RK1 b - - 2 2";
		Board board = new Board(BOTH_CASTLED);
		
		assertEquals(WKING, board.pieceAt(g1));
		assertEquals(WROOK, board.pieceAt(f1));
		assertEquals(WROOK, board.pieceAt(a1));
		assertEquals(BKING, board.pieceAt(c8));
		assertEquals(BROOK, board.pieceAt(d8));
		assertEquals(BROOK, board.pieceAt(h8));
		assertEquals(false, board.isWhiteTurn());
		assertEquals(false, board.getCsWhiteShort() == 1);
		assertEquals(false, board.getCsWhiteLong() == 1);
		assertEquals(false, board.getCsBlackShort() == 1);
		assertEquals(false, board.getCsBlackLong() == 1 );
		
		board = new Board(FEN_START);
		assertEquals(true, board.isWhiteTurn());
		assertEquals(true, board.getCsWhiteShort() == 1);
		assertEquals(true, board.getCsWhiteLong() == 1);
		assertEquals(true, board.getCsBlackShort() == 1);
		assertEquals(true, board.getCsBlackLong() == 1 );
	}
	
	public void testSetFenPositionWhiteCanOnlyShortBlackCanOnlyLong() {
		String CASTLINGS01 = "r3k3/r7/8/8/8/8/7R/4K2R w Kq - 0 1 ";
		Board board = new Board(CASTLINGS01);
		
		assertEquals(WKING, board.pieceAt(e1));
		assertEquals(WROOK, board.pieceAt(h1));
		assertEquals(WROOK, board.pieceAt(h2));
		assertEquals(BKING, board.pieceAt(e8));
		assertEquals(BROOK, board.pieceAt(a8));
		assertEquals(BROOK, board.pieceAt(a7));
		assertEquals(true, board.isWhiteTurn());
		assertEquals(true, board.getCsWhiteShort() == 1);
		assertEquals(false, board.getCsWhiteLong() == 1);
		assertEquals(false, board.getCsBlackShort() == 1);
		assertEquals(true, board.getCsBlackLong() == 1 );
	}

	public void testSetFenPositionEnPasant() {
		String _en_passant = "r2qkbnr/pbppp1pp/np6/4Pp2/8/5N2/PPPPBPPP/RNBQK2R w KQkq f6 0 5";
		Board board = new Board(_en_passant);
		
		assertEquals(WKING, board.pieceAt(e1));
		assertEquals(WKNIGHT, board.pieceAt(f3));
		assertEquals(WQUEEN, board.pieceAt(d1));
		assertEquals(BKING, board.pieceAt(e8));
		assertEquals(BBISHOP, board.pieceAt(f8));
		assertEquals(BROOK, board.pieceAt(a8));
		assertEquals(true, board.isWhiteTurn());
		assertEquals(true, board.getCsWhiteShort() == 1);
		assertEquals(true, board.getCsWhiteLong() == 1);
		assertEquals(true, board.getCsBlackShort() == 1);
		assertEquals(true, board.getCsBlackLong() == 1 );
		assertEquals(board.getEnPassant(), f6);
		
		board = new Board(FEN_START);
		assertEquals(board.getEnPassant(), 0);
		
		_en_passant = "rnbqkbnr/pp1ppppp/8/8/2pPP3/5N2/PPP2PPP/RNBQKB1R b KQkq d3 0 3";
		board = new Board(_en_passant);
		
		assertEquals(board.getEnPassant(), d3);
	}
	
	public void testSetFenSomeRandomPositions() {
		String _pos = "r1b2rk1/2qpbpp1/1p3n1p/pN6/2BQP3/P4N1P/1PP2PP1/3R1RK1 w - - 3 14";
		Board board = new Board(_pos);
		assertEquals(WQUEEN, board.pieceAt(d4));
		assertEquals(BQUEEN, board.pieceAt(c7));
		assertEquals(WKNIGHT, board.pieceAt(b5));
		
		_pos = "5rk1/prppqppp/bp6/P1b1p1P1/1nPPnP1P/RQN1BN1R/1P2P3/3K1B2 b - - 0 15 ";
		board = new Board(_pos);
		assertEquals(WPAWN, board.pieceAt(a5));
		assertEquals(BBISHOP, board.pieceAt(c5));
		assertEquals(BPAWN, board.pieceAt(e5));
		assertEquals(BPAWN, board.pieceAt(h7));
		assertEquals(false, board.isWhiteTurn());
	}
	
	
	public void testFenMovenumberAndFiftyMoves() {
		String pos1 = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1 ";
		Board board = new Board(pos1);
		assertEquals(pos1.trim(), board.getFEN());
		
		pos1 = "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2 ";
		board = new Board(pos1);
		assertEquals(pos1.trim(), board.getFEN());
		
		pos1 = "rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2 ";
		board = new Board(pos1);
		assertEquals(pos1.trim(), board.getFEN());
		
		pos1 = "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3 ";
		board = new Board(pos1);
		assertEquals(pos1.trim(), board.getFEN());
		
		pos1 = "r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3 ";
		board = new Board(pos1);
		assertEquals(pos1.trim(), board.getFEN());
		
		pos1 = "r1bqkbnr/1ppp1ppp/p1n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 0 4 ";
		board = new Board(pos1);
		assertEquals(pos1.trim(), board.getFEN());
		
		pos1 = "r1bqkbnr/1ppp1ppp/p1n5/4p3/B3P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 1 4 ";
		board = new Board(pos1);
		assertEquals(pos1.trim(), board.getFEN());
		
		pos1 = "r1bqkbnr/2pp1ppp/p1n5/1p2p3/B3P3/5N2/PPPP1PPP/RNBQK2R w KQkq b6 0 5 ";
		board = new Board(pos1);
		assertEquals(pos1.trim(), board.getFEN());

		pos1 = "r1bqkbnr/2pp1ppp/p1n5/1p2p3/4P3/1B3N2/PPPP1PPP/RNBQK2R b KQkq - 1 5 ";
		board = new Board(pos1);
		assertEquals(pos1.trim(), board.getFEN());
	}

	public void testGetFenFromStartTo4Moves() {
		Board board = new Board(FEN_START);
		assertEquals(FEN_START, board.getFEN());
		
		// pos1 = 1.e4 e5 2.Nf3 Nc6 3.Bb5 a6 
		String pos1 = "r1bqkbnr/1ppp1ppp/p1n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 0 4";
		board = new Board(pos1);
		assertEquals(pos1, board.getFEN());
	}
	
}
