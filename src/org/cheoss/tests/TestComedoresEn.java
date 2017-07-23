package org.cheoss.tests;

import org.cheoss.board.*;
import org.cheoss.util.*;

import junit.framework.*;

public class TestComedoresEn extends TestCase implements Constants {
	
	
	public void testCountAttackers() {
		String fenWAC10 = "2br2k1/2q3rn/p2NppQ1/2p1P3/Pp5R/4P3/1P3PPP/3R2K1 w - -";
//		String fenWAC65 = "1r1r1qk1/p2n1p1p/bp1Pn1pQ/2pNp3/2P2P1N/1P5B/P6P/3R1RK1 w - -";
//		String fenWAC92 = "r4rk1/1p2ppbp/p2pbnp1/q7/3BPPP1/2N2B2/PPP4P/R2Q1RK1 b - -";
//		String fenWAC105 = "r2r2k1/pb3ppp/1p1bp3/7q/3n2nP/PP1B2P1/1B1N1P2/RQ2NRK1 b - -";
//		String fenWAC191 = "2r1Rn1k/1p1q2pp/p7/5p2/3P4/1B4P1/P1P1QP1P/6K1 w - -";
//		String test = "6k1/5ppp/8/5NRQ/8/8/5PPP/6K1 w - - 0 1";
//		String test2 = "6k1/5ppp/8/5N1Q/8/8/5PPP/6K1 w - - 0 1";
//		//  bm Qc4; id "WAC.191";
//		
		Board board = new Board(fenWAC10);
		int comedores = board.countAttackers(h7, WHITE);				
		System.out.println("comedores en h7 = "+comedores);
		
		comedores = board.countAttackers(g7, WHITE);
		System.out.println("comedores en g7 = "+comedores);
		
		comedores = board.countAttackers(f7, WHITE);
		System.out.println("comedores en f7 = "+comedores);
		
		comedores = board.countAttackers(d8, WHITE);
		System.out.println("comedores en d8 = "+comedores);


		
//		
//		board.getEvaluator().eval();
		
	}
	
	public void testfenWAC36() {
		String fenWAC36 = "3r4/2p1rk2/1pQq1pp1/7p/1P1P4/P4P2/6PP/R1R3K1 b - -";
		
		Board board = new Board(fenWAC36);
		
		assertEquals(1, board.countAttackers(h2, BLACK));
		assertEquals(2, board.countAttackers(d4, BLACK));
		
		
		
	}
 }
