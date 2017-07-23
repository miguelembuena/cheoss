package org.cheoss.tests;

import java.util.*;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.util.*;

public class TestZobrist extends TestCase implements Constants {
	
	
	public void testBasicMoveUnMoveFromStart() {
		int move = 0;
		Board board = new Board(FEN_START);
		long startKey = board.getKey();
		//move = Interpreter.uciStrToMove("g1f3", board);
		move = XMove.pack(g1, f3, WKNIGHT, 0, 0);
		board.doMove(move);
		assertTrue(startKey != board.getKey());
		
		board.undoMove(move);
		assertTrue(startKey == board.getKey());	
		
		//from start again
		//move = Interpreter.uciStrToMove("e2e4", board);		
		move = XMove.pack(e2, e4, WPAWN, 0, FLAG_PAWN_JUMP);
		board.doMove(move);
		assertTrue(startKey != board.getKey());
		board.undoMove(move);
		assertTrue(startKey == board.getKey());
		
		// start one more time
		//move = Interpreter.uciStrToMove("e2e4", board);
		move = XMove.pack(e2, e4, WPAWN, 0, FLAG_PAWN_JUMP);
		board.doMove(move);		
		
		long k1 = board.getKey();
		//move = Interpreter.uciStrToMove("e7e5", board);
		move = XMove.pack(e7, e5, BPAWN, 0, FLAG_PAWN_JUMP);
		board.doMove(move);
		
		board.undoMove(move);
		assertEquals(k1, board.getKey());
		
		//move = Interpreter.uciStrToMove("e2e4", board);
		move = XMove.pack(e2, e4, WPAWN, 0, FLAG_PAWN_JUMP);
		board.undoMove(move);
		assertEquals(startKey, board.getKey());
	}
	
	public void testZobristPromos() {
		// undocistr molaria escribirlo y que funcionara
		/**/
		long k0, k1, k2, k3;
		String f0, f1, f2, f3;
		f0 = "1k6/5P2/8/8/8/8/8/1K6 w - - 0 1";
		Board board = new Board(f0);		
		k0 = board.getKey();
		assertEquals(f0, board.getFEN());
		
		board.doUciMove("f7f8q");
		k1 = board.getKey();
		f1 = "1k3Q2/8/8/8/8/8/8/1K6 b - - 0 1";
		assertEquals(f1, board.getFEN());
		//
		board.doUciMove("b8b7");
		k2 = board.getKey();
		f2 = "5Q2/1k6/8/8/8/8/8/1K6 w - - 1 2";
		assertEquals(f2, board.getFEN());
		//
		board.doUciMove("f8e7");
		k3 = board.getKey();
		f3 = "8/1k2Q3/8/8/8/8/8/1K6 b - - 2 2";
		assertEquals(f3, board.getFEN());
		//
		int m = XMove.pack(f8, e7, WQUEEN, 0, 0);
		board.undoMove(m);
		assertEquals(f2, board.getFEN());
		assertEquals(k2, board.getKey());
		//
		m = XMove.pack(b8, b7, BKING, 0, 0);
		board.undoMove(m);
		assertEquals(f1, board.getFEN());
		assertEquals(k1, board.getKey());		
		//		
		m = XMove.pack(f7, f8, WPAWN, 0, FLAG_PROMO_QUEEN);
		board.undoMove(m);
		assertEquals(f0, board.getFEN());
		assertEquals(k0, board.getKey());
		
		
		//// promo knight
		board = new Board(f0);		
		k0 = board.getKey();
		assertEquals(f0, board.getFEN());
		///////////////
		board.doUciMove("f7f8n");
		k1 = board.getKey();
		//
		board.doUciMove("b8b7");
		k2 = board.getKey();
		//
		board.doUciMove("f8h7");
		k3 = board.getKey();
		
		//undomoves
		m = XMove.pack(f8, h7, WKNIGHT, 0, 0);
		board.undoMove(m);
		assertEquals(k2, board.getKey());
		//
		m = XMove.pack(b8, b7, BKING, 0, 0);
		board.undoMove(m);
		assertEquals(k1, board.getKey());		
		//		
		m = XMove.pack(f7, f8, WPAWN, 0, FLAG_PROMO_KNIGHT);
		board.undoMove(m);
		assertEquals(k0, board.getKey());
		
	}
	
	public void testZobristCastlingsPromosCaptureEnpassant() {
		
		 //1. e4 c5 2. Nf3 c4 3. b4 cxb3 4. Be2 
		//bxa2 5. O-O axb1=Q 
		//6. Re1 Qxc1 7. Rxc1 *
		

		Board board = new Board(FEN_START);
		long k0 = board.getKey();

		int move1= XMove.pack(e2, e4, WPAWN, 0, FLAG_PAWN_JUMP);
		board.doMove(move1);//w
		long k1 = board.getKey();

		int move2 = XMove.pack(c7, c5, BPAWN, 0, FLAG_PAWN_JUMP);
		board.doMove(move2);
		long k2 = board.getKey();

		int move3 = XMove.pack(g1, f3, WKNIGHT, 0, 0);
		board.doMove(move3);
		long k3 = board.getKey();

		int move4 = XMove.pack(c5, c4, BPAWN, 0, 0);
		board.doMove(move4);
		long k4 = board.getKey();		

		int move5 = XMove.pack(b2, b4, WPAWN, 0, FLAG_PAWN_JUMP);
		board.doMove(move5);
		long k5 = board.getKey();

		int move6 = XMove.pack(c4, b3, BPAWN, WPAWN, FLAG_EN_PASSANT_CAPTURE);
		board.doMove(move6);
		long k6 = board.getKey();

		int move7 = XMove.pack(f1, e2, WBISHOP, 0, 0);
		board.doMove(move7);
		long k7 = board.getKey();

		int move8 = XMove.pack(b3, a2, BPAWN, WPAWN, 0);
		board.doMove(move8);
		long k8 = board.getKey();

		int move9 = XMove.pack(e1, g1, WKING, 0, FLAG_CASTLING);
		board.doMove(move9);
		long k9 = board.getKey();

		int move10 = XMove.pack(a2, b1, BPAWN, WKNIGHT, FLAG_PROMO_QUEEN);
		board.doMove(move10);
		long k10 = board.getKey();

		int move11 = XMove.pack(f1, e1, WROOK, 0, 0);
		board.doMove(move11);
		long k11 = board.getKey();
		

		int move12 = XMove.pack(b1, c1, BQUEEN, WBISHOP, 0);
		board.doMove(move12);
		long k12 = board.getKey();

		int move13 = XMove.pack(a1, c1, WROOK, BQUEEN, 0);
		board.doMove(move13);
		long k13 = board.getKey();

		
		board.undoMove(move13);
		assertEquals(k12, board.getKey());
		
		board.undoMove(move12);
		assertEquals(k11, board.getKey());
		
		board.undoMove(move11);
		assertEquals(k10, board.getKey());

//		board.undoUciMove("a2b1q");
		board.undoMove(move10);
		assertEquals(k9, board.getKey());

//		board.undoUciMove("e1g1"); //w
		board.undoMove(move9);
		assertEquals(k8, board.getKey());

//		board.undoUciMove("b3a2");
		board.undoMove(move8);
		assertEquals(k7, board.getKey());

//		board.undoUciMove("f1e2"); //w
		board.undoMove(move7);
		assertEquals(k6, board.getKey());

//		board.undoUciMove("c4b3");
		board.undoMove(move6);
		assertEquals(k5, board.getKey());

//		board.undoUciMove("b2b4"); //w
		board.undoMove(move5);
		assertEquals(k4, board.getKey());

//		board.undoUciMove("c5c4");
		board.undoMove(move4);
		assertEquals(k3, board.getKey());

//		board.undoUciMove("g1f3");//w
		board.undoMove(move3);
		assertEquals(k2, board.getKey());

//		board.undoUciMove("c7c5");
		board.undoMove(move2);
		assertEquals(k1, board.getKey());

//		board.undoUciMove("e2e4");//w
		board.undoMove(move1);
		assertEquals(k0, board.getKey());
		
		assertEquals(FEN_START, board.getFEN());
		
	}
	
	
	public void testZobristOneWholeGame() {
		String fenFinal = "7k/7Q/6P1/8/7p/3P4/5P2/5RK1 b - - 1 39";
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
		
		ArrayList<Integer> moves = new ArrayList<Integer>();
		ArrayList<Long> keys = new ArrayList<Long>();
		keys.add(new Board(FEN_START).getKey());
		ArrayList<String> fens = new ArrayList<String>();
		fens.add(new Board(FEN_START).getFEN());
		
		for (String strmove : strmoves) {
			int move = Interpreter.algebraicToMove(strmove, board);
			moves.add(move);
			board.doMove( move );		
			keys.add(board.getKey());
			fens.add(board.getFEN());
		}		
		
		assertEquals(fenFinal.trim(), board.getFEN());
		
		for (int i = 0; i < keys.size(); i++) {
			long key = keys.get(i);
			int equals = 0;
			for (int j = 0; j < keys.size(); j++ ) {
				if (keys.get(j) == key) {
					equals++;
				}
			}
			assertEquals(1, equals);
		}
		
		for (int i = moves.size()-1; i >= 0; i--) {
			int move = moves.get(i);
			board.undoMove(move);
			assertEquals((long)keys.get(i), board.getKey());
			assertEquals(fens.get(i), board.getFEN());			
		}
	}
	
	public void testTurnToPlay() {
		String f2 = "5Q2/1k6/8/8/8/8/8/1K6 w - - 1 2";
		Board board = new Board(f2);
		long key0 = board.getKey();

		f2 = "5Q2/1k6/8/8/8/8/8/1K6 b - - 1 2";
		board = new Board(f2);
		long key1 = board.getKey();
		
		System.out.println("key0 = "+key0+" key1 = "+key1);
		assertTrue(key0 != key1);
	}
	
}
