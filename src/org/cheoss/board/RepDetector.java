package org.cheoss.board;

import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class RepDetector {	
	private int index = 0;
	private int max_num_moves = 107;
	private long[] gameKeys = new long[max_num_moves];
	private Board board;
	
	public RepDetector(Board board) {
		this.board = board;
		clearGameKeys();
	}
	
	private void clearGameKeys() {
		for (int i = 0; i < gameKeys.length; i++) {
			gameKeys[i] = 0L;
		}
		
		index = 0;
	}
	
	private int[] movelist = new int[500];
	
	public int getMoveFromMovelist(int movenumber) {
		return movelist[movenumber];
	}
	
    public void registerRealGameKey(int move) {
    	// -1 because it's called after the move has been done
    	movelist[board.getMoveNumber() - 1] = move;
    	
    	if (isMoveIrreversible(move)) {
    		clearGameKeys();
    	}
    	
    	gameKeys[index] = board.getKey();
    	index++;
    }
    
	public boolean checkRepes() {
		if (index >= 105) {
			clearGameKeys();
		}
		
		if (index >= 102) {
			return true;
		}
		
		for (int i = 0; i < gameKeys.length; i++) {
			long key = gameKeys[i];
			if (key == board.getKey() ) {
				return true;
			}
		}
		return false;
	}

	private boolean isMoveIrreversible(int move) {
		if (XMove.isCapture(move) || XBoard.isPawn( XMove.pieceMoved(move)) ) {
			return true;
		}
		return false;
	}

	public long[] getGameKeys() {
		return gameKeys;
	}
	

}
