package org.cheoss.tests;

import junit.framework.*;

import org.cheoss.board.*;
import org.cheoss.controller.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

public class TestDebugRunning extends TestCase implements Constants {
	
	
	public void testDebug() {
		
		Cheoss.main(null);
		
//		fenDebug = r2qnrk1/p4ppp/2pp3B/2p1p3/4P2b/2NP2QP/PPP2PP1/R3R1K1 w - - 7 13 key = -8029060063901238979
//		MID ctrMove=9 baseTime=119236 midtime=85838 return 1999 timeTrancurred=25860
//		info score cp -824 depth 1 nodes 12 pv h6c1 
//		info score cp -804 depth 1 nodes 92 pv h6c1 
//		info score cp 3 depth 1 nodes 120 pv h6c1 
//		info score cp 58 depth 1 nodes 120 pv h6c1 
//		info score cp 58 depth 2 nodes 0 pv h6c1 e8c7 
//		info score cp 58 depth 3 nodes 0 pv h6c1 e8c7 g3f3 
//		info score cp 58 depth 4 nodes 0 pv h6c1 e8c7 g3f3 h4f6 
//		info score cp 68 depth 5 nodes 38218 pv h6c1 e8c7 g3g4 d8f6 e1e2 
//		bestmove h6c1
		
//fenDebug = rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 key = 8826030684109628480
		
//		String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
//		Board board = new Board(fen);
//		assertEquals(8826030684109628480L, board.getKey());
//		
//		board = new Board(FEN_START);
//		assertEquals(8826030684109628480L, board.getKey());
//		
//		board.doMove(XMove.pack(e2, e4, WPAWN, 0, FLAG_PAWN_JUMP));
//		board.doMove(XMove.pack(c7, c5, BPAWN, 0, FLAG_PAWN_JUMP));
//		
//		System.out.println("fen = "+board.getFEN());
//		System.out.println("keyyy = "+board.getKey());
//		
//		Board otro = new Board("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2");
//		System.out.println("fun = "+otro.getFEN());
//		System.out.println("keuuu = "+otro.getKey());
//		
//		int moveg1f3 = XMove.pack(g1, f3, WKNIGHT, 0, 0);
//		int moved7d6 = XMove.pack(d7, d6, BPAWN, 0, 0);
//		
//		board.doMove(moveg1f3);
//		board.doMove(moved7d6);
//		
//		otro.doMove(moveg1f3);
//		otro.doMove(moved7d6);
//		
//		System.out.println("board = "+board.getKey()+" fen = "+board.getFEN());
//		System.out.println("otroo = "+otro.getKey() +" fen = "+otro.getFEN());
		

//		Board board = new Board("r2qnrk1/p3bppp/2pp3B/2p1p3/4P3/2NP2QP/PPP2PP1/R4RK1 w - - 5 12");
//		Node node = new Node(board);
//		for (int i = 1; i <= 5; i++) {
//			node.alphaBetaRoot(i, -INFINITY, INFINITY);
//		}
//		
//		board.doMove(XMove.pack(f1, e1, WROOK, 0, 0));
//		board.doMove(XMove.pack(e7, h4, BBISHOP, 0, 0));
//		for (int i = 1; i <= 5; i++) {
//			node.alphaBetaRoot(i, -INFINITY, INFINITY);
//		}
		
		
//fenDebug = rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2 key = -7991176167565121914
//		fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2";
//		board = new Board(fen);
//		assertEquals(-7991176167565121914L, board.getKey());
		
//fenDebug = rnbqkbnr/pp2pppp/3p4/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 0 3 key = 8317259367579559006		
//fenDebug = rnbqkbnr/pp3ppp/3p4/2p1p3/4P3/2N2N2/PPPP1PPP/R1BQKB1R w KQkq e6 0 4 key = 6822468385484819082
//fenDebug = r1bqkbnr/pp3ppp/2np4/1Bp1p3/4P3/2N2N2/PPPP1PPP/R1BQK2R w KQkq - 2 5 key = 6162251096992189980
//fenDebug = r1bqkbnr/p4ppp/2pp4/2p1p3/4P3/2N2N2/PPPP1PPP/R1BQK2R w KQkq - 0 6 key = 289981745109141847
//fenDebug = r2qkbnr/p4ppp/2pp4/2p1p3/4P1b1/2N2N2/PPPP1PPP/R1BQ1RK1 w kq - 2 7 key = 7621788800994519795		
//fenDebug = r2qkbnr/p4ppp/2pp4/2p1p3/4P3/2N2b1P/PPPP1PP1/R1BQ1RK1 w kq - 0 8 key = 1578370695252252858
//fenDebug = r2qkb1r/p4ppp/2pp1n2/2p1p3/4P3/2N2Q1P/PPPP1PP1/R1B2RK1 w kq - 1 9 key = 1245372717303020690		
//fenDebug = r2qk2r/p3bppp/2pp1n2/2p1p3/4P3/2NP1Q1P/PPP2PP1/R1B2RK1 w kq - 1 10 key = 3276630091839422006
//fenDebug = r2q1rk1/p3bppp/2pp1n2/2p1p3/4P3/2NP2QP/PPP2PP1/R1B2RK1 w - - 3 11 key = -3992235222500402402
//fenDebug = r2qnrk1/p3bppp/2pp3B/2p1p3/4P3/2NP2QP/PPP2PP1/R4RK1 w - - 5 12 key = -755605220662937469
//fenDebug = r2qnrk1/p4ppp/2pp3B/2p1p3/4P2b/2NP2QP/PPP2PP1/R3R1K1 w - - 7 13 key = -8029060063901238979
		
		
		
		
	}
	
	
 }
