package org.cheoss.search;

import org.cheoss.board.*;

public class Variation {	
	private int[] moves = new int[73];	
	private int value = 0;
	
	public int get(int arg) {
		return moves[arg];
	}

	public String variationToPGNString(Board board) {
		StringBuffer sb = new StringBuffer();
		for (int move : moves) {
			if (move == 0) break;
			String uci = Interpreter.moveToPGN(move, board);
			sb.append( uci );
			sb.append(" ");
		}
		return sb.toString();		
	}
	
	public String toUCIString() {
		StringBuffer sb = new StringBuffer();
		for (int move : moves) {
			if (move == 0) break;
			String uci = Interpreter.moveToUci(move);
			sb.append( uci );
			sb.append(" ");
		}
		return sb.toString();		
	}
	
	public String variationToIntString() {
		StringBuffer sb = new StringBuffer();
		for (int move : moves) {
			if (move == 0) break;
			sb.append( move );
			sb.append(" ");
		}
		return sb.toString();		
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int[] getMoves() {
		return moves;
	}
}
