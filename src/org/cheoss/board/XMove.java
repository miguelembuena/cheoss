package org.cheoss.board;

import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class XMove implements Constants {
	
	private static final int MASK = 127; // 7 bits
	private static final int MASK_4 = 15; // 4 bits
	private static final int MASK_3 = 7; // 3 bits
	
	
	public static int pack(int from, int to, int pieceMoved, int pieceCaptured, int flag, int score) {
//		from;  //7 bits
//		to;  // 7 bits
//		pieceMoved;  // de 1 a 12, 4 bits
//		pieceCaptured = 0; //// de 1 a 12, 4 bits
//		flag = 0; // de 0 a 7 , 3 bits
//		total => 7 + 7 + 4 + 4 + 3 = 25		
		return 
			((((((((((0 ^from) << 7) ^ to) << 4) ^ pieceMoved) 
					<< 4) ^ pieceCaptured) << 3) ^ flag) << 7) ^ score; 
	}
	
	public static int pack(int from, int to, int pieceMoved, int pieceCaptured, int flag) {
		return pack(from, to, pieceMoved, pieceCaptured, flag, 0);
	}
	
	public static int from(int move) {
		return (move >> 25) & MASK;
	}
	
	public static int to(int move) {
		return (move >> 18) & MASK;
	}
	
	public static int pieceMoved(int move) {
		return ((move >> 14) & MASK_4);
	}
	public static int pieceCaptured(int move) {
		return ((move >> 10) & MASK_4);
	}
	
	public static int flag(int move) {
		return ((move >> 7) & MASK_3);		
	}
	
	public static int score(int move) {
		// el ultimo, no hay desplazamiento
		return move & MASK;
	}
	
	public static int changeScore(int move, int score) {
		return 
		((((((((((0 ^ from(move)) << 7) ^ to(move)) << 4) ^ pieceMoved(move)) 
				<< 4) ^ pieceCaptured(move)) << 3) ^ flag(move)) << 7) ^ score; 
	}

	
	public static boolean isCapture(int move) {	
		return pieceCaptured(move) != 0;
	}
	
	public static boolean isWhite(int move) {
		return pieceMoved(move) % 2 != 0;
	}
	
	public static boolean isCastling(int move) {		
		if (XMove.equals(move, WHITE_SHORT_CASTLING) ||XMove.equals(move,  WHITE_LONG_CASTLING)
				|| XMove.equals(move, BLACK_SHORT_CASTLING) || XMove.equals(move, BLACK_LONG_CASTLING)) {
			return true;
		}
		return false; 
	}
	
	public static boolean equals(int move1, int move2) {
		if (move1 == 0 || move2 == 0) return false;
//		System.out.println("move1 = "+move1+" cambiado = "+changeScore(move1, 0));
//		System.out.println("move2 = "+move2+" cambiado = "+changeScore(move2, 0));
		return changeScore(move1, 0) == changeScore(move2, 0);
//		return move1 == move2;
	}
	
	public static String toString(int move) {
		return "piece moved: "+pieceMoved(move)+" piece captured: "+pieceCaptured(move)+
				" flags: "+flag(move)+" origen: "+from(move)+" destino: "+to(move)+" score: "+score(move);		
	}
	

}
