package org.cheoss.board;

import java.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class LegalGenerator extends Generator {

	public LegalGenerator(Board board) {
		super(board);
	}
	
	public int genMoves(int[] moves, int firstKiller, int secondKiller, int ttMove) {
		return super.genMoves(moves, firstKiller, secondKiller, ttMove);
	}
	
	protected void addMove(int[] moves, int move) {
		if (move == 0) return;
		
		board.doMove(move);
		if (!board.isInCheck(!board.isWhiteTurn())) {
			moves[localIndex++] = XMove.changeScore(move, super.score(move, true));
		}
		board.undoMove(move);
	}
	
	int depthOrder = 1;
	
	public ArrayList<Integer> genMovesFrom(int piece, int from) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int[] vmoves = new int[MAX_MOVES_IN_STACK];
		genMoves(vmoves,0,0,0);
		for(int i=0; i<localIndex; i++) {
			int move = vmoves[i];
			if (piece == XMove.pieceMoved(move)) {
				if ( from == XMove.from(move)) {
					list.add(move);
				}
			}
		}		
		return list;
	}

}
