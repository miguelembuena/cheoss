package org.cheoss.board;

import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class Locator implements Constants {
	private int[] piecesSet = new int[16];
	private int[] wPawnSet = new int[8];
	private int[] bPawnSet = new int[8];
	int wpawnIndex = 0;
	int bpawnIndex = 0;
	int locatorIndex = 0;
	
	public void addPiece(int piece, int square) {
		if (piece == WPAWN) {
			wPawnSet[wpawnIndex] = square;
			wpawnIndex++;
		}
		else if (piece == BPAWN) {
			bPawnSet[bpawnIndex] = square;
			bpawnIndex++;
		}
		else {
			piecesSet[locatorIndex] = square;    			
			locatorIndex++;    				
		}				
	}
	
	public void undoMove(int move) {
		if (XBoard.isPawn(XMove.pieceMoved(move))) {
			undoPawn(move);
			return;
		}
		if (XBoard.isKing(XMove.pieceMoved(move))) {
			undoKing(move);
			return;
		}
		
		change(XMove.to(move), XMove.from(move));
		if (XMove.isCapture(move)) add(XMove.to(move), XMove.pieceCaptured(move));			
	}

	public void doMove(int move) {
		if (XBoard.isPawn(XMove.pieceMoved(move))) {
			actualizePawn(move);
			return;
		}
		if (XBoard.isKing(XMove.pieceMoved(move))) {
			actualizeKing(move);
			return;
		}
		
		if (XMove.isCapture(move)) remove(XMove.to(move), XMove.pieceCaptured(move));
		change(XMove.from(move), XMove.to(move));
	}

	
	private void remove(int square, int pieceToRemove) {
		if (pieceToRemove == WPAWN) {
			removeWPawn(square);			
		}
		else if (pieceToRemove == BPAWN) {
			removeBPawn(square);
		}
		else {
			for (int i=0; i < piecesSet.length; i++) {
				if (piecesSet[i] == square) {
					piecesSet[i] = 0;
					return;
				}
			}			
		}
	}

	private void removeWPawn(int square) {
		for (int i=0; i < wPawnSet.length; i++) {
			if (wPawnSet[i] == square) {
				wPawnSet[i] = 0;
				return;
			}
		}
	}
	
	private void removeBPawn(int square) {
		for (int i=0; i < bPawnSet.length; i++) {
			if (bPawnSet[i] == square) {
				bPawnSet[i] = 0;
				return;
			}
		}
	}

	
	
	private void add(int square, int pieceToAdd) {
		if (pieceToAdd == WPAWN) {
			addWPawn(square);
		}
		else if (pieceToAdd == BPAWN) {
			addBPawn(square);
		}
		else {
			for (int i=0; i < piecesSet.length; i++) {
				if (piecesSet[i] == 0) {
					piecesSet[i] = square;
					return;
				}
			}			
		}
	}
	private void addWPawn(int square) {
		for (int i=0; i < wPawnSet.length; i++) {
			if (wPawnSet[i] == 0) {
				wPawnSet[i] = square;
				break;
			}
		}
	}	

	public int[] getPiecesSet() {
		return piecesSet;
	}

	public int[] getBPawnSet() {
		return bPawnSet;
	}
	
	public int[] getWPawnSet() {
		return wPawnSet;
	}

	private void addBPawn(int square) {
		for (int i=0; i < bPawnSet.length; i++) {
			if (bPawnSet[i] == 0) {
				bPawnSet[i] = square;
				break;
			}
		}
	}
	
	private void change(int oldSquare, int newSquare) {
		for (int i=0; i < piecesSet.length; i++) {
			if (piecesSet[i] == oldSquare) {
				piecesSet[i] = newSquare;
				return;
			}
		}
	}
	
	
	private void changeBPawn(int oldSquare, int newSquare) {
		for (int i=0; i < bPawnSet.length; i++) {
			if (bPawnSet[i] == oldSquare) {
				bPawnSet[i] = newSquare;
				return;
			}
		}		
	}
	
	private void changeWPawn(int oldSquare, int newSquare) {
		for (int i=0; i < wPawnSet.length; i++) {
			if (wPawnSet[i] == oldSquare) {
				wPawnSet[i] = newSquare;
				return;
			}
		}		
	}

	
	private void actualizeWPawn(int move) {
		if (XMove.isCapture(move)) {
			if (XMove.flag(move) != FLAG_EN_PASSANT_CAPTURE ) {
				remove(XMove.to(move), XMove.pieceCaptured(move));
			}
			else {
				remove(XMove.to(move)+12, BPAWN); 				
			}			
		}
		
		if (XMove.flag(move) < FLAG_PROMO_QUEEN) {
			changeWPawn(XMove.from(move), XMove.to(move));	
		}
		else {
			int promo = XBoard.getPromoForFlag(XMove.flag(move), true);
			removeWPawn(XMove.from(move));
			add(XMove.to(move), promo);
		}
	}
	
	private void actualizeBPawn(int move) {
		if (XMove.isCapture(move)) {
			if (XMove.flag(move) != FLAG_EN_PASSANT_CAPTURE ) {
				remove(XMove.to(move), XMove.pieceCaptured(move));
			}
			else { 
				remove(XMove.to(move)-12, WPAWN);				
			}			
		}
		if (XMove.flag(move) < FLAG_PROMO_QUEEN) {
			changeBPawn(XMove.from(move), XMove.to(move));
		}
		else {
			int promo = XBoard.getPromoForFlag(XMove.flag(move), true);
			removeBPawn(XMove.from(move));
			add(XMove.to(move), promo);
		}
	}
	
	private void undoBKing(int move) {						
		if (XMove.flag(move) == FLAG_CASTLING) {
			change(XMove.to(move), XMove.from(move));
			if (XMove.equals(move, BLACK_SHORT_CASTLING)) change(31, 33);
			else change(29, 26); //BLACK_LONG_CASTLING				
		}
		else {
			change(XMove.to(move), XMove.from(move));
			if (XMove.isCapture(move)) add(XMove.to(move), XMove.pieceCaptured(move));					
		}
	}	

	private void undoWPawn(int move) {
		if (XMove.flag(move) != FLAG_EN_PASSANT_CAPTURE) {
			if (XMove.flag(move) < FLAG_PROMO_QUEEN) {
				changeWPawn(XMove.to(move), XMove.from(move));			
				if (XMove.isCapture(move)) add(XMove.to(move), XMove.pieceCaptured(move));							
			}
			else {
				int promo = XBoard.getPromoForFlag(XMove.flag(move), true);
				remove(XMove.to(move), promo);
				add(XMove.from(move), WPAWN);
				if (XMove.isCapture(move)) add(XMove.to(move), XMove.pieceCaptured(move));				
			}
		}
		else {
			//FLAG_EN_PASSANT_CAPTURE
			changeWPawn(XMove.to(move), XMove.from(move)); 			
			add(XMove.to(move)+12, XMove.pieceCaptured(move));
		}		
	}
	
	private void undoBPawn(int move) {
		if (XMove.flag(move) != FLAG_EN_PASSANT_CAPTURE) {
			if (XMove.flag(move) < FLAG_PROMO_QUEEN) {
				changeBPawn(XMove.to(move), XMove.from(move));			
				if (XMove.isCapture(move)) add(XMove.to(move), XMove.pieceCaptured(move));
			}
			else {
				int promo = XBoard.getPromoForFlag(XMove.flag(move), true);
				remove(XMove.to(move), promo);
				add(XMove.from(move), BPAWN);
				if (XMove.isCapture(move)) add(XMove.to(move), XMove.pieceCaptured(move));				
			}
		}
		else {
			//FLAG_EN_PASSANT_CAPTURE
			changeBPawn(XMove.to(move), XMove.from(move)); 			
			add(XMove.to(move)-12, XMove.pieceCaptured(move));
		}		
	}
	
	private void actualizePawn(int move) {
		if (XMove.isWhite(move)) actualizeWPawn(move);
		else actualizeBPawn(move);
	}
	
	private void actualizeKing(int move) {
		if (XMove.flag(move) != FLAG_CASTLING) {			
			if (XMove.isCapture(move)) remove(XMove.to(move), XMove.pieceCaptured(move));
			change(XMove.from(move), XMove.to(move));
		}
		else {			
			change(XMove.from(move), XMove.to(move));
			if (XMove.equals(move, WHITE_SHORT_CASTLING)) change(117, 115);
			else if (XMove.equals(move, WHITE_LONG_CASTLING)) 	change(110, 113);
			else if (XMove.equals(move, BLACK_SHORT_CASTLING)) change(33, 31);
			else change(26, 29); //BLACK_LONG_CASTLING							
		}
	}

	private void undoPawn(int move) {
		if (XMove.isWhite(move)) {
			undoWPawn(move);
		}
		else {
			undoBPawn(move);
		}
	}
	
	private void undoKing(int move) {
		if (XMove.isWhite(move)) {
			undoWKing(move);
		}
		else {
			undoBKing(move);
		}
	}
		
	private void undoWKing(int move) {						
			if (XMove.flag(move) == FLAG_CASTLING) {
				change(XMove.to(move), XMove.from(move));
				if (XMove.equals(move, WHITE_SHORT_CASTLING)) change(115, 117);
				else change(113, 110);
			}
			else {
				change(XMove.to(move), XMove.from(move));
				if (XMove.isCapture(move)) add(XMove.to(move), XMove.pieceCaptured(move));					
			}
	}
	
}