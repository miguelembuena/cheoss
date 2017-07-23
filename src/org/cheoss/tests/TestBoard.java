package org.cheoss.tests;

import java.util.*;

import junit.framework.*;

import org.cheoss.board.*;
import org.cheoss.util.*;

public class TestBoard extends TestCase implements Constants {
	
	public void testMaterialCount() {
		Board board = new Board(FEN_START);
		
		int materialWhite = board.getMaterialWhite();
		int materialBlack = board.getMaterialBlack();
		
		assertEquals(materialWhite, board.getMaterialWhite());
		assertEquals(materialBlack, board.getMaterialBlack());		
		
		String game ="1.e2-e4 d7-d5 2.e4xd5 Cg8-f6 3.Af1-b5+ c7-c6 4.d5xc6 Cb8-" +
				"d7 5.c6xb7 h7-h6 6.b7xa8D h6-h5 7.g2-g4 h5xg4 8.h2-h3 g4x" +
				"h3 9.Th1-h2 Th8-h5 10.Th2-g2 h3-h2 11.Cb1-c3 h2-h1T 12.a2-a3 e7-e6";
		String fen_final = "Q1bqkb2/p2n1pp1/4pn2/1B5r/8/P1N5/1PPP1PR1/R1BQK1Nr w Q - 0 13";
		
		ArrayList<String> strMoves = Interpreter.stringToListOfStringMoves(game);
		ArrayList<Integer> moves = new ArrayList<Integer>();
		
		for (String strMove : strMoves) {
			int move = Interpreter.algebraicToMove(strMove, board);
			board.doMove(move);
			moves.add(move);
		}
		
		assertEquals(fen_final.trim(), board.getFEN());
		int[] material = countMaterial(board);
		materialWhite = material[0]; 
		materialBlack = material[1];
		assertEquals(materialWhite, board.getMaterialWhite());		
		assertEquals(materialBlack, board.getMaterialBlack());
		
		for (int index = moves.size()-1, j = 0; j < 7; j++, index--) {
			int move = moves.get(index);
			board.undoMove(move);
		}
		material = countMaterial(board);
		assertEquals(material[0], board.getMaterialWhite());		
		assertEquals(material[1], board.getMaterialBlack());				
	}
	
	private int[] countMaterial(Board board) {
		int materialBlack = 0;
		int materialWhite = 0;
		
		for (int i = 0; i < NUMERIC_START.length; i++ ) {
			int piece = board.pieceAt(i);
			if (piece != OUT && piece != EMPTY) {
				if ( (piece & 1) != 0 ) materialWhite += valueOf[piece];
				else materialBlack += valueOf[piece];				
			}			
		}
		int[] material = {materialWhite, materialBlack };
		return material;
	}


	
	public void testXBoardState() {
		int enPassant = 91;
		int fiftyMoves = 43;
		int csws = 1;
		int  cswl = 0;
		int  csbs = 1;
		int  csbl = 1;
		int packed = XBoardState.pack(enPassant, fiftyMoves, csws, cswl, csbs, csbl);
		
		assertEquals(enPassant, XBoardState.getEnPassant(packed));
		assertEquals(fiftyMoves, XBoardState.getFiftyMoves(packed));
		assertEquals(csws, XBoardState.getCsWhiteShort(packed));
		assertEquals(cswl, XBoardState.getCsWhiteLong(packed) );
		assertEquals(csbs, XBoardState.getCsBlackShort(packed));
		assertEquals(csbl, XBoardState.getCsBlackLong(packed));
		
		 enPassant = 93;
		 fiftyMoves = 7;
		 csws = 0;
		 cswl = 0;
		 csbs = 1;
		 csbl = 0;
		 packed = XBoardState.pack(enPassant, fiftyMoves, csws, cswl, csbs, csbl);
		 assertEquals(enPassant, XBoardState.getEnPassant(packed));
		 assertEquals(fiftyMoves, XBoardState.getFiftyMoves(packed));
		 assertEquals(csws, XBoardState.getCsWhiteShort(packed));
		 assertEquals(cswl, XBoardState.getCsWhiteLong(packed) );
		 assertEquals(csbs, XBoardState.getCsBlackShort(packed));
		 assertEquals(csbl, XBoardState.getCsBlackLong(packed));		
	}
	
	public void testXMoveEquals() {
		int from = 77;
		int to = 13;
		int pieceMoved = 4;
		int pieceCaptured = 0;
		int flag = 0;
		int score = 73;
		int move1 = XMove.pack(from, to, pieceMoved, pieceCaptured, flag, score);
		int move2 = XMove.changeScore(move1, 69);
		
		assertTrue(XMove.equals(move1 , move2) );
		
		move1 = XMove.pack(11, 12, 13, 0, FLAG_PAWN_JUMP, 123);
		move2 = XMove.pack(11, 12, 13, 0, FLAG_PAWN_JUMP, 0);
		assertTrue(XMove.equals(move1 , move2) );
		
		move1 = XMove.pack(11, 12, 13, 0, FLAG_PAWN_JUMP, 123);
		move2 = XMove.pack(11, 12, 13, 0, 0, 0);
		assertFalse(XMove.equals(move1 , move2) );
		
		
	}
	
	public void testXMove() {
		int from = 77;
		int to = 13;
		int pieceMoved = 4;
		int pieceCaptured = 0;
		int flag = 0;
		int score = 73;
		int packed = XMove.pack(from, to, pieceMoved, pieceCaptured, flag, score);
		
		//pack = 1001101000110101000000000
		
		//System.out.println("pack = "+Integer.toBinaryString(packed));
		
		assertEquals(from, XMove.from(packed));
		assertEquals(to, XMove.to(packed));
		assertEquals(flag, XMove.flag(packed));
		assertEquals(pieceCaptured, XMove.pieceCaptured(packed));
		assertEquals(pieceMoved, XMove.pieceMoved(packed));
		assertEquals(score, XMove.score(packed));
		
		from = 102;
		to = 111;
		pieceMoved = 3;
		pieceCaptured = 2;
		flag = FLAG_PROMO_BISHOP;
		score = 100;
		packed = XMove.pack(from, to, pieceMoved, pieceCaptured, flag, score);
		
		assertEquals(from, XMove.from(packed));
		assertEquals(to, XMove.to(packed));
		assertEquals(flag, XMove.flag(packed));
		assertEquals(pieceCaptured, XMove.pieceCaptured(packed));
		assertEquals(pieceMoved, XMove.pieceMoved(packed));
		assertEquals(score, XMove.score(packed));
	}
	
	public void testXMoveScoreIncremental() {
		int from = 17;
		int to = 98;
		int pieceMoved = WQUEEN;
		int pieceCaptured = BROOK;
		int flag = 0;
		int score = 0;
		int move = XMove.pack(from, to, pieceMoved, pieceCaptured, flag, score);
		
		assertEquals(from, XMove.from(move));
		assertEquals(to, XMove.to(move));
		assertEquals(flag, XMove.flag(move));
		assertEquals(pieceCaptured, XMove.pieceCaptured(move));
		assertEquals(pieceMoved, XMove.pieceMoved(move));
		assertEquals(score, XMove.score(move));
		
		int newScore = 73;
		move = XMove.changeScore(move, newScore);
		
		assertEquals(from, XMove.from(move));
		assertEquals(to, XMove.to(move));
		assertEquals(flag, XMove.flag(move));
		assertEquals(pieceCaptured, XMove.pieceCaptured(move));
		assertEquals(pieceMoved, XMove.pieceMoved(move));
		assertEquals(newScore, XMove.score(move));
		
		newScore = 127;
		move = XMove.changeScore(move, newScore);
		assertEquals(from, XMove.from(move));
		assertEquals(to, XMove.to(move));
		assertEquals(flag, XMove.flag(move));
		assertEquals(pieceCaptured, XMove.pieceCaptured(move));
		assertEquals(pieceMoved, XMove.pieceMoved(move));
		assertEquals(newScore, XMove.score(move));
		
		newScore = 13;
		move = XMove.changeScore(move, newScore);
		assertEquals(from, XMove.from(move));
		assertEquals(to, XMove.to(move));
		assertEquals(flag, XMove.flag(move));
		assertEquals(pieceCaptured, XMove.pieceCaptured(move));
		assertEquals(pieceMoved, XMove.pieceMoved(move));
		assertEquals(newScore, XMove.score(move));

	}


	public void testAttacks() {
		String fen = "7k/8/3N4/3Q4/p2r4/P2b4/8/7K w - - 0 1 ";
		Board board = new Board(fen);
		
		// rey en h1 no está atacado por negras
		assertFalse(board.isAttacked(h1, BLACK));
		
		// casilla e4 si está atacado por negras y blancas
		assertTrue(board.isAttacked(e4, BLACK));
		assertTrue(board.isAttacked(e4, WHITE));
		
		// a1 no la ataca nadie
		assertFalse(board.isAttacked(a1, BLACK));
		assertFalse(board.isAttacked(a1, WHITE));
		
		// d7 no atacada por negras (hay piezas blancas por el medio)
		assertFalse(board.isAttacked(d7, BLACK));
		
		// peones
		assertTrue(board.isAttacked(b3, BLACK));
		assertTrue(board.isAttacked(b4, WHITE));
		
		// caballo
		assertTrue(board.isAttacked(b7, WHITE));
		assertTrue(board.isAttacked(c8, WHITE));
		assertTrue(board.isAttacked(e8, WHITE));
		
		assertTrue(board.isAttacked(f7, WHITE));
		assertTrue(board.isAttacked(f5, WHITE));
		
		assertTrue(board.isAttacked(e4, WHITE));
		assertTrue(board.isAttacked(c4, WHITE));
		assertTrue(board.isAttacked(b5, WHITE));
		
		assertFalse(board.isAttacked(b8, WHITE));
		
		//dama
		assertTrue(board.isAttacked(a2, WHITE));
		assertTrue(board.isAttacked(g5, WHITE));
		assertTrue(board.isAttacked(d4, WHITE));
		assertFalse(board.isAttacked(d3, WHITE));
		
		//Torre
		assertTrue(board.isAttacked(g4, BLACK));
		assertTrue(board.isAttacked(d5, BLACK));
		assertFalse(board.isAttacked(d7, BLACK));
		assertFalse(board.isAttacked(c3, BLACK));
		
		//alfil
		assertTrue(board.isAttacked(h7, BLACK));
		assertFalse(board.isAttacked(h6, BLACK));
		assertTrue(board.isAttacked(b1, BLACK));
		
		//rey
		assertTrue(board.isAttacked(h2, WHITE));
		assertFalse(board.isAttacked(h3, WHITE));
		assertTrue(board.isAttacked(g1, WHITE));
	}
	
	public void testSpeedAttacks() {
		String fen = "7k/8/3N4/3Q4/p2r4/P2b4/8/7K w - - 0 1 ";
		Board board = new Board(fen);
		
		//millis = 5344 primera medicion
		// millis = 5328 segunda
		// mejoramos? linea batir:
		//  millis = 5312,  millis = 5344,  millis = 5329
		// ...
		// millis = 5437,  millis = 5422,  millis = 5422
		// dividido entre tres para agilizar el test
		long start = System.currentTimeMillis();
		int limit = 1000000/5;
		for (int i = 0; i < limit; i++) {
			assertFalse(board.isAttacked(h1, BLACK));
			assertTrue(board.isAttacked(e4, BLACK));
			assertTrue(board.isAttacked(e4, WHITE));
			assertFalse(board.isAttacked(a1, BLACK));
			assertFalse(board.isAttacked(a1, WHITE));
			assertFalse(board.isAttacked(d7, BLACK));
			assertTrue(board.isAttacked(b3, BLACK));
			assertTrue(board.isAttacked(b4, WHITE));
			assertTrue(board.isAttacked(b7, WHITE));
			assertTrue(board.isAttacked(c8, WHITE));
			assertTrue(board.isAttacked(e8, WHITE));		
			assertTrue(board.isAttacked(f7, WHITE));
			assertTrue(board.isAttacked(f5, WHITE));		
			assertTrue(board.isAttacked(e4, WHITE));
			assertTrue(board.isAttacked(c4, WHITE));
			assertTrue(board.isAttacked(b5, WHITE));		
			assertFalse(board.isAttacked(b8, WHITE));
			assertTrue(board.isAttacked(a2, WHITE));
			assertTrue(board.isAttacked(g5, WHITE));
			assertTrue(board.isAttacked(d4, WHITE));
			assertFalse(board.isAttacked(d3, WHITE));
			assertTrue(board.isAttacked(g4, BLACK));
			assertTrue(board.isAttacked(d5, BLACK));
			assertFalse(board.isAttacked(d7, BLACK));
			assertFalse(board.isAttacked(c3, BLACK));
			assertTrue(board.isAttacked(h7, BLACK));
			assertFalse(board.isAttacked(h6, BLACK));
			assertTrue(board.isAttacked(b1, BLACK));
			assertTrue(board.isAttacked(h2, WHITE));
			assertFalse(board.isAttacked(h3, WHITE));
			assertTrue(board.isAttacked(g1, WHITE));			
		}
		
		long millis = System.currentTimeMillis() - start;
		//assertTrue(millis < (6000/5) );		
		//5281
	}


	
	/////////////////// do and undo moves test
	public void testDoTwoMoves() {
		Board board = new Board(FEN_START);
		
		int move = XMove.pack(XBoard.squareForCoord("e2"), XBoard.squareForCoord("e4"), 
				WPAWN, 0, FLAG_PAWN_JUMP, 0);
		board.doMove(move);		
		assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", board.getFEN());
		
		move = XMove.pack(XBoard.squareForCoord("e7"), XBoard.squareForCoord("e5"), 
				BPAWN, 0, FLAG_PAWN_JUMP, 3);
		board.doMove(move);		
		assertEquals("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2", board.getFEN());		
	}
	
	public void testWholeGame() {
		String[] games = {
				"1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6", 
				"1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6 3.Af1-b5 a7-a6 4.Ab5xc6 b7xc6",
				"1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6 3.Af1-b5 a7-a6 4.Ab5xc6 b7xc6 5.0-0",
				"1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6 3.Af1-b5 a7-a6 4.Ab5xc6 b7xc6 5.0-0 Cg8-f6 "+
					"6.Cb1-c3 Ac8-b7 7.b2-b3 d7-d5 8.Ac1-b2 d5xe4 9.Cf3xe5 Af8-c5 10.Tf1-e1 0-0 ",
					"1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6 3.Af1-b5 a7-a6 4.Ab5xc6 b7xc6 5.0-0 Cg8-f6 "+
					"6.Cb1-c3 Ac8-b7 7.b2-b3 d7-d5 8.Ac1-b2 d5xe4 9.Cf3xe5 Af8-c5 10.Tf1-e1 0-0 "+
					"11.Te1xe4 Cf6xe4 12.Ce5xc6 Ac5xf2+ 13.Rg1-f1 Ab7xc6 ",
					
					"1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6 3.Af1-b5 a7-a6 4.Ab5xc6 b7xc6 5.0-0 Cg8-f6 "+
					"6.Cb1-c3 Ac8-b7 7.b2-b3 d7-d5 8.Ac1-b2 d5xe4 9.Cf3xe5 Af8-c5 10.Tf1-e1 0-0 "+
					"11.Te1xe4 Cf6xe4 12.Ce5xc6 Ac5xf2+ 13.Rg1-f1 Ab7xc6 14.Cc3xe4 Tf8-e8 15.Rf1xf2 "+
					"Te8xe4 16.d2-d3 Ta8-b8 17.Dd1-f3 Dd8xd3 18.Df3xe4 Ac6xe4 19.c2xd3 Ae4xd3 2"+
					"0.Ta1-d1 Tb8xb3 21.Td1xd3 Tb3xb2+ 22.Rf2-e3 Tb2xa2 23.Td3-d7 h7-h6 24.Td"+
					"7xc7 Ta2xg2 25.Tc7-a7 Tg2xh2 26.Ta7xa6"					
		};
		
		String[] fens = {
				"r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3",
				"r1bqkbnr/2pp1ppp/p1p5/4p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 0 5",
				"r1bqkbnr/2pp1ppp/p1p5/4p3/4P3/5N2/PPPP1PPP/RNBQ1RK1 b kq - 1 5",
				"r2q1rk1/1bp2ppp/p1p2n2/2b1N3/4p3/1PN5/PBPP1PPP/R2QR1K1 w - - 3 11 ",
				"r2q1rk1/2p2ppp/p1b5/8/4n3/1PN5/PBPP1bPP/R2Q1K2 w - - 0 14",
				"6k1/5pp1/R6p/8/8/4K3/7r/8 b - - 0 26 "
		};

		
		//AuxLoadMoves aux = new AuxLoadMoves();
		ArrayList<String> strmoves;
		
		for (int i = 0; i < games.length; i++) {
			Board board = new Board(FEN_START);	
			//aux.setAlgebraicSequence(games[i]);
			//strmoves = aux.getListOfStringMoves();
			strmoves = Interpreter.stringToListOfStringMoves(games[i]);
			for (String strmove : strmoves) {
				//board.setHistoryIndex(0);
				board.doArenaMove(strmove);
			}
			assertEquals(fens[i].trim(), board.getFEN());
		}
				
	}
	
	public void testGamePromotionQueen() {
		String fen = "7k/7Q/6P1/8/7p/3P4/5P2/5RK1 b - - 1 39";
		String game = "1.d2-d4 d7-d5 2.Cg1-f3 Cg8-f6 3.g2-g3 g7-g6 4.Af1-g2 c7-c5 5.0-0 A" +
				"f8-g7 6.a2-a3 0-0 7.Cb1-c3 c5xd4 8.Cf3xd4 a7-a6 9.b2-b4 Cb8-c6 10.Ac1-e3 b" +
				"7-b6 11.Ta1-b1 Ac8-g4 12.h2-h3 Ag4-f5 13.Cd4xf5 g6xf5 14.a3-a4 Dd8-c7 15.b" +
				"4-b5 a6xb5 16.a4xb5 e7-e5 17.Cc3xd5 Cf6xd5 18.Ag2xd5 Ta8-a2 19.Tb1-a1 Ta2x" +
				"a1 20.Dd1xa1 e5-e4 21.Da1-a6 Ag7-d4 22.Ae3xd4 h7-h6 23.Ad5xc6 Dc7xc6 24.b5x" +
				"c6 Tf8-c8 25.Da6xc8+ Rg8-h7 26.Dc8-e6 f7xe6 27.c6-c7 b6-b5 28.c7-c8D e6-e5 29.D" +
				"c8-a6 e5xd4 30.Da6xb5 d4-d3 31.c2xd3 e4xd3 32.e2xd3 Rh7-g6 33.g3-g4 f5xg4 34.h3" +
				"xg4 Rg6-g7 35.Db5-e5+ Rg7-h7 36.g4-g5 h6-h5 37.De5-e7+ Rh7-h8 38.g5-g6 h5-h" +
				"4 39.De7-h7# (Mate)";
		Board board = new Board(FEN_START);
		
		ArrayList<String> strmoves = Interpreter.stringToListOfStringMoves(game);
		for (String strmove : strmoves) {
			board.doArenaMove( strmove );
		}
		assertEquals(fen.trim(), board.getFEN());
	}
	
	public void testSimplePromotionQueen() {
		String fen = "r1bqkb1r/1pppp3/p7/4n3/8/6Q1/PPPP1PPP/RNBQKBNR b KQkq - 1 6 ";
		String game = "1.e2-e4 f7-f5 2.e4xf5 g7-g6 3.f5xg6 Cb8-c6 4.g6xh7 Cc6-e5 5.h7xg8D a7-a6 6.Dg8-g3";
		
		Board board = new Board(FEN_START);
		
		ArrayList<String> strmoves = Interpreter.stringToListOfStringMoves(game);
		for (String strmove : strmoves) {
			board.doArenaMove( strmove );
		}
		assertEquals(fen.trim(), board.getFEN());
	}

	
	public void testGamePromotionRook() {
		String fen = "R1bqkb1r/3nppp1/5n2/7p/8/8/PPPP1PPP/RNBQKBNR b KQk - 1 7";
		String game = "1.e2-e4 d7-d5 2.e4xd5 c7-c6 3.d5xc6 Cb8-d7 4.c6" +
				"xb7 Cg8-f6 5.b7xa8T h7-h6 6.Ta8xa7 h6-h5 7.Ta7-a8";
		
		
		Board board = new Board(FEN_START);
		
		ArrayList<String> strmoves = Interpreter.stringToListOfStringMoves(game);
		for (String strmove : strmoves) {
			board.doArenaMove( strmove );
		}
		assertEquals(fen.trim(), board.getFEN());
	}
	
	public void testGamePromotionBishop() {
		String fen = "rnbqkbBr/1pppp3/8/p7/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 5 ";
		String game = "1.e2-e4 f7-f5 2.e4xf5 g7-g6 3.f5xg6 a7-a6 4.g6xh7 a6-a5 5.h7xg8A";
		
		
		Board board = new Board(FEN_START);
		ArrayList<String> strmoves = Interpreter.stringToListOfStringMoves(game);
		for (String strmove : strmoves) {
			board.doArenaMove( strmove );
		}
		assertEquals(fen.trim(), board.getFEN());
	}

	public void testGamePromotionKight() {
		String fen = "rnbqkbnr/ppp1pppp/8/P7/8/8/1PPP3P/RNBQKBNn w Qkq - 0 6 ";
		String game = "1.e2-e4 d7-d5 2.f2-f3 d5xe4 3.a2-a3 e4xf3 4.a3-a4 f3xg2 5.a4-a5 g2xh1C";
		
		
		Board board = new Board(FEN_START);
		ArrayList<String> strmoves = Interpreter.stringToListOfStringMoves(game);
		for (String strmove : strmoves) {
			board.doArenaMove( strmove );
		}
		assertEquals(fen.trim(), board.getFEN());
	}

	
	public void testDoOneMoveUndoOneMove() {
		Board board = new Board(FEN_START);
				
		int move = Interpreter.algebraicToMove("e2-e4", board);
		board.doMove(move);		
		String fen1 = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
		assertEquals(fen1.trim(), board.getFEN());
		
		board.undoMove(move);
		assertEquals(FEN_START, board.getFEN());
	}
	
	public void testSpanishGame() {
		String fen_final = "r2qkb1r/1bpp1ppp/p1n2n2/1p2p3/4P3/1B3N2/PPPP1PPP/RNBQ1RK1 w kq - 4 7";
		String game = "1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6 3.Af1-b5 a7-a6 4.Ab5-a4 b7-" +
				"b5 5.Aa4-b3 Cg8-f6 6.0-0 Ac8-b7";
		
		String fenAfterMove_4_Black = "r1bqkbnr/2pp1ppp/p1n5/1p2p3/B3P3/5N2/PPPP1PPP/RNBQK2R w KQkq b6 0 5";
		String fenAfterMove_2_White = "rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
		
		Board board = new Board(FEN_START);
		ArrayList<String> strMoves = Interpreter.stringToListOfStringMoves(game);
		
		//1.e2-e4 e7-e5 
		int move1w = Interpreter.algebraicToMove(strMoves.get(0), board);
		board.doMove(move1w); 
		
		int move1b = Interpreter.algebraicToMove(strMoves.get(1), board);
		board.doMove(move1b);
		
		//2.Cg1-f3 Cb8-c6
		int move2w = Interpreter.algebraicToMove(strMoves.get(2), board);
		board.doMove(move2w); 
		assertEquals(fenAfterMove_2_White, board.getFEN());
		
		int move2b = Interpreter.algebraicToMove(strMoves.get(3), board);
		board.doMove(move2b);
		
		//3.Af1-b5 a7-a6		  
		int move3w = Interpreter.algebraicToMove(strMoves.get(4), board);
		board.doMove(move3w); 
		
		int move3b = Interpreter.algebraicToMove(strMoves.get(5), board);
		board.doMove(move3b);

		//4.Ab5-a4 b7-b5
		int move4w = Interpreter.algebraicToMove(strMoves.get(6), board);
		board.doMove(move4w); 
		
		int move4b = Interpreter.algebraicToMove(strMoves.get(7), board);
		board.doMove(move4b);
		assertEquals(fenAfterMove_4_Black, board.getFEN());
		
		//5.Aa4-b3 Cg8-f6 
		int move5w = Interpreter.algebraicToMove(strMoves.get(8), board);
		board.doMove(move5w); 
		
		int move5b = Interpreter.algebraicToMove(strMoves.get(9), board);
		board.doMove(move5b);
				
		//6.0-0 Ac8-b7
		int move6w = Interpreter.algebraicToMove(strMoves.get(10), board);
		board.doMove(move6w); 
		
		int move6b = Interpreter.algebraicToMove(strMoves.get(11), board);
		board.doMove(move6b);
		assertEquals(fen_final, board.getFEN() );
		
		board.undoMove(move6b);
		board.undoMove(move6w);
		
		board.undoMove(move5b);
		board.undoMove(move5w);		
		assertEquals(fenAfterMove_4_Black, board.getFEN());
		
		board.undoMove(move4b);
		board.undoMove(move4w);
		
		board.undoMove(move3b);
		board.undoMove(move3w);
		
		board.undoMove(move2b);
		assertEquals(fenAfterMove_2_White, board.getFEN());
		board.undoMove(move2w);
		
		board.undoMove(move1b);
		board.undoMove(move1w);		
		assertEquals(FEN_START, board.getFEN());
	}

///////////////////////// castlings
	
	public void testCastlingsRights() {
		Board board = new Board("rnbqkb1r/pppp1ppp/6N1/4p3/8/6n1/PPPP1PPP/RNBQKB1R w KQkq - 2 5");
		assertTrue( board.getCsBlackLong() == 1);
		assertTrue( board.getCsBlackShort() == 1);
		assertTrue( board.getCsWhiteLong() == 1);
		assertTrue( board.getCsWhiteShort() == 1);
		
		String move = "Cg6xh8";
		board.doArenaMove(move);
		assertTrue( board.getCsBlackLong() == 1 );
		assertFalse( board.getCsBlackShort() == 1);
		assertTrue( board.getCsWhiteLong() == 1);
		assertTrue( board.getCsWhiteShort() == 1);

		move = "Cg3xh1";
		board.doArenaMove(move);
		assertTrue( board.getCsBlackLong() == 1 );
		assertFalse( board.getCsBlackShort() == 1);
		assertTrue( board.getCsWhiteLong() == 1);
		assertFalse( board.getCsWhiteShort() == 1);
		
		move = "Dd1-f3";
		board.doArenaMove(move);

		move = "Dd8-g5";
		board.doArenaMove(move);
		
		move = "Df3xb7";
		board.doArenaMove(move);
		
		move = "Dg5-g3";
		board.doArenaMove(move);
		
		move = "Db7-a8";
		board.doArenaMove(move);
		assertFalse( board.getCsBlackLong() == 1 );
		assertFalse( board.getCsBlackShort() == 1);
		assertTrue( board.getCsWhiteLong() == 1);
		assertFalse( board.getCsWhiteShort() == 1);

		move = "Dg3-b3";
		board.doArenaMove(move);
		assertFalse( board.getCsBlackLong() == 1 );
		assertFalse( board.getCsBlackShort() == 1);
		assertTrue( board.getCsWhiteLong() == 1);
		assertFalse( board.getCsWhiteShort() == 1);

		move = "h2-h4";
		board.doArenaMove(move);
		assertFalse( board.getCsBlackLong() == 1 );
		assertFalse( board.getCsBlackShort() == 1);
		assertTrue( board.getCsWhiteLong() == 1);
		assertFalse( board.getCsWhiteShort() == 1);

		move = "Db3xa2";
		board.doArenaMove(move);
		assertFalse( board.getCsBlackLong() == 1 );
		assertFalse( board.getCsBlackShort() == 1);
		assertTrue( board.getCsWhiteLong() == 1);
		assertFalse( board.getCsWhiteShort() == 1);
		
		move = "h4-h5";
		board.doArenaMove(move);
		
		move = "Da2xa1";
		board.doArenaMove(move);
		assertFalse( board.getCsBlackLong() == 1 );
		assertFalse( board.getCsBlackShort() == 1);
		assertFalse( board.getCsWhiteLong() == 1);
		assertFalse( board.getCsWhiteShort() == 1);
		
		String fenFinal = "Qnb1kb1N/p1pp1ppp/8/4p2P/8/8/1PPP1PP1/qNB1KB1n w - - 0 11";
		assertEquals(fenFinal.trim(), board.getFEN());
	}
	
	
	public void testRevokingCastlings() {
		String testCastlings = "r3kb1r/8/8/8/8/8/8/R3KB1R w KQkq - 0 1 ";
		
		
		Board board = new Board(testCastlings);
		board.doArenaMove("Af1-e2");
		
		String f1 = "r3kb1r/8/8/8/8/8/4B3/R3K2R b KQkq - 1 1 ";
		assertEquals(f1.trim(), board.getFEN()); // all rights
		
		board.doArenaMove("Ta8-b8");
		f1 = "1r2kb1r/8/8/8/8/8/4B3/R3K2R w KQk - 2 2 ";
		assertEquals(f1.trim(), board.getFEN());
		
		board.doArenaMove("Ae2-f1");
		f1= "1r2kb1r/8/8/8/8/8/8/R3KB1R b KQk - 3 2 ";
		assertEquals(f1.trim(), board.getFEN());
		
		board.doArenaMove("Af8-g7");
		f1= "1r2k2r/6b1/8/8/8/8/8/R3KB1R w KQk - 4 3 ";
		assertEquals(f1.trim(), board.getFEN());
		
		board.doArenaMove("Af1-e2");
		f1= "1r2k2r/6b1/8/8/8/8/4B3/R3K2R b KQk - 5 3";
		assertEquals(f1.trim(), board.getFEN());

		board.doArenaMove("Ag7-a1");
		f1= "1r2k2r/8/8/8/8/8/4B3/b3K2R w Kk - 0 4 ";
		assertEquals(f1.trim(), board.getFEN());
		
		// King moves
		board = new Board(testCastlings);
		board.doArenaMove("Re1-d1");
		f1= "r3kb1r/8/8/8/8/8/8/R2K1B1R b kq - 1 1 ";
		assertEquals(f1.trim(), board.getFEN());

		
		board.doArenaMove("Re8-e7");
		f1= "r4b1r/4k3/8/8/8/8/8/R2K1B1R w - - 2 2 ";
		assertEquals(f1.trim(), board.getFEN());

	}


	
}
