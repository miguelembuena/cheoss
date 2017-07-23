package org.cheoss.evaluation;

import org.cheoss.board.*;
import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class BasicEvaluator implements IEvaluator, IConstantsEval, Constants {
	
    int whiteScore = 0;
    int blackScore = 0;
	
	private Board board;

	public BasicEvaluator(Board board) {
		this.board = board;
	}
	
	public int eval() {
		if (board.isEndGame()) {
			return endEval();
		}
		else {
			return normalEval();			
		}
	}
	
		
	private int normalEval() {
		int matValue = board.getMaterialWhite() - board.getMaterialBlack();			
		int lazyVal = board.isWhiteTurn()? matValue: -matValue;
		if (Math.abs(lazyVal) > 300) {
			return lazyVal;
		}
		
		whiteScore = 0;
		blackScore = 0;
		
		int scorePcsq[] = new int[2];
		
		for (int sq : board.getPiecesSet()) {
			int piece = board.pieceAt(sq);		
			switch (piece) {
			case OUT:
				break;
			case WKNIGHT:
				scorePcsq[0] += bonusOf[piece][sq];
				break;
			case BKNIGHT:
				scorePcsq[1] += bonusOf[piece][sq];
				break;
			case WBISHOP:
				scorePcsq[0] += bonusOf[piece][sq];				
				break;
			case BBISHOP:
				scorePcsq[1] += bonusOf[piece][sq];				
				break;
			case WROOK:
				scorePcsq[0] += bonusOf[piece][sq];				
				break;
			case BROOK:
				scorePcsq[1] += bonusOf[piece][sq];				
				break;
			case WQUEEN:
				scorePcsq[0] += bonusOf[piece][sq];				
				break;
			case BQUEEN:
				scorePcsq[1] += bonusOf[piece][sq];				
				break;
			case WKING:
				scorePcsq[0] += bonusOf[piece][sq];
				break;
			case BKING:
				scorePcsq[1] += bonusOf[piece][sq];
			}			
		}
				
		whiteScore = board.getMaterialWhite() + scorePcsq[0];
		blackScore = board.getMaterialBlack() + scorePcsq[1];
		int value = whiteScore - blackScore;			
		return board.isWhiteTurn()? value: -value;
	}
		
	private int endEval() {
		int scorePcsq[] = new int[2];
		whiteScore = 0;
		blackScore = 0;
		
		for (int sq : board.getPiecesSet()) {
			int piece = board.pieceAt(sq);		
			switch (piece) {
			case OUT:
				break;
			case WKING:
				scorePcsq[0] += END_WHITE_KING_BONUS[sq];
				break;
			case BKING:
				scorePcsq[1] += END_BLACK_KING_BONUS[sq];
			default:
				break;
			}			
		}
				
		whiteScore += board.getMaterialWhite() + scorePcsq[0];
		blackScore += board.getMaterialBlack() + scorePcsq[1];
		
		int value = whiteScore - blackScore;		
		return board.isWhiteTurn()? value: -value;
	}
	
}
