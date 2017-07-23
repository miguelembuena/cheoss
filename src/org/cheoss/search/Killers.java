package org.cheoss.search;

import org.cheoss.board.*;
import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class Killers implements Constants {	
	 
	private int[][][] matchTable = new int[MAX_DEPTH][118][118];
	private int[] firstSlot = new int[MAX_DEPTH];
	private int[] secondSlot = new int[MAX_DEPTH];
		
	
	public int getFirst(int depth) {
		return firstSlot[depth];
	}
	
	public int getSecond(int depth) {
		return secondSlot[depth];
	}

	
	private int matchesOf(int move, int depth) {
		return matchTable [depth] [XMove.from(move)] [XMove.to(move)];
	}
	
	public void addMatch(int move, int depth) {
		matchTable [depth] [XMove.from(move)] [XMove.to(move)]++;
	}
	
	public void put(int move, int depth) {
		if (XMove.isCapture(move)) return;
		
		addMatch(move, depth);
		
		if (firstSlot[depth] == 0) {
			firstSlot[depth] = move;
		}
		else if (secondSlot[depth] == 0) {
			secondSlot[depth] = move;			
		}
		else {
			if ( !XMove.equals(move, firstSlot[depth]) ) {
				if ( matchesOf(move, depth) > matchesOf(firstSlot[depth], depth)) {
					secondSlot[depth] = firstSlot[depth];
					firstSlot[depth] = move;
				}
			}
			else if ( !XMove.equals(move, secondSlot[depth]) ) {
				if ( matchesOf(move, depth) > matchesOf(secondSlot[depth], depth)) {
					secondSlot[depth] = move;
				}
			} 
		}
	}

}
