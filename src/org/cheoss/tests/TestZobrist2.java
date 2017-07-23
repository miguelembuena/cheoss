package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.util.*;

public class TestZobrist2 extends TestCase implements Constants {
	
	// prueba que no se actualizan erróneamente la hash de enroque si 
	// el enroque ya se había perdido
	public void testDebugZobrist() {
		String fen = "r5k1/1r3pp1/1pbp3p/2n1p3/2P1P1P1/PN6/2B2P1P/R3R1K1 w - - 1 28";
		Board board = new Board(fen);
		assertEquals(-3348606769750588493L, board.getKey());
		board.doUciMove("a1d1");		
		assertEquals(new Board(board.getFEN()).getKey(), board.getKey());
		
		board = new Board(fen);
		assertEquals(-3348606769750588493L, board.getKey());
		board.doUciMove("g1h1");
		assertEquals(new Board(board.getFEN()).getKey(), board.getKey());
	}
	
	
 }
