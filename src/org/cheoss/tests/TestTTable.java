package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;


public class TestTTable extends TestCase implements Constants {
	
	public void testPutAndGet() {
		Board board = new Board(FEN_START);
		TTable ttab = new TTable();
		int bestMove = 1;
		//ttab.put(depth, value, bestMove, flag, key)
		ttab.put(5, 17, bestMove, HASH_EXACT, board.getKey());
		
		TTEntry entry = ttab.probe(4, 0, 0, board.getKey());		
		assertNotNull(entry);
		
		assertEquals(5, entry.getDepth());
		assertEquals(17, entry.getValue());
		assertEquals(1, entry.getBestMove());
		assertEquals(HASH_EXACT, entry.getFlag());
		assertEquals(board.getKey(), entry.getKey());
		
		int move = XMove.pack(e2, e4, WPAWN, 0, FLAG_PAWN_JUMP);
		board.doMove(move);
		assertNull(ttab.probe(5, 0, 0, board.getKey()));
		
		ttab.put(5, 18, 2, HASH_EXACT, board.getKey());
		
		board.undoMove(move);
		// depth < 5
		ttab.put(3, 23, bestMove, HASH_EXACT, board.getKey());
		entry = ttab.probe(4, 0, 0, board.getKey());
		assertNotNull(entry);// //		
		assertEquals(5, entry.getDepth());
		assertEquals(17, entry.getValue());

		// depth > 5
		ttab.put(8, 23, bestMove, HASH_EXACT, board.getKey());
		entry = ttab.probe(4, 0, 0, board.getKey());
		assertNotNull(entry);		
		assertEquals(8, entry.getDepth());
		assertEquals(23, entry.getValue());
		
	}
	
//	public void testAnother() {
//		Board board = new Board(FEN_START);
//		Node node = new Node(board);
//		node.getTtable();
//
//		node.alphaBetaRoot(6, -INFINITY, INFINITY);
//		board.doMove(node.getBestLine().getReverse(0));
//		board.doMove(XMove.pack(e7, e5, BPAWN, 0, FLAG_PAWN_JUMP));
//		node.alphaBetaRoot(4, -INFINITY, INFINITY);
//		
//	}
	

}
