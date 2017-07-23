package org.cheoss.evaluation;

import org.cheoss.board.*;
import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class AlterPawnEvaluator implements IEvaluator, Constants, IConstantsEval {

	private Board board;
	private int whiteScore = 0;
	private int blackScore = 0;
	
	
	public AlterPawnEvaluator(Board board) {
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

	
	
	int[] wpVector = {-13, -12, -11}; 
	public boolean isWhitePassedPawnIn(int sq) {
		if ( board.pieceAt(sq) != WPAWN ) return false;
		int xq = 0;
		while (XBoard.pgnRow(sq) <= 7) {			
			for (int offset = 0; offset < 3; offset++) {
				xq = sq + wpVector[offset];
				if (board.pieceAt(xq) == BPAWN) return false;
			}
			sq -= 12;
		}
		
		return true;
	}
	
//                                 2   3    4    5    6    7                                        	
//	private int[] wpBonus = {0, 0, 50, 60,  80,  100, 150, 200};
	private int[] wpBonus = {0, 0, 30, 50,  70,  90, 110, 350};
	private int valueWhitePassedPawn(int sq) {
		int value = wpBonus[XBoard.pgnRow(sq)];
		if (board.pieceAt(XBoard.getSW(sq)) == WPAWN || 
				board.pieceAt(XBoard.getSE(sq)) == WPAWN ||
				board.pieceAt(sq -1) == WPAWN ||
				board.pieceAt(sq +1) == WPAWN	) {
			value += 15;
		}
		else {
			value = value - (value*40)/100;
		}
		
		if (!board.isEndGame()) {
			value = value - (value*20)/100;
		}
		return value;
	}
	
//                                 2    3    4    5    6    7                                        	
//	private int[] bpBonus = {0, 0, 200, 150, 100, 80,  60,  50};
	private int[] bpBonus = {0, 0, 350, 110, 90, 70,  50,  30};
	private int valueBlackPassedPawn(int sq) {
		int value = bpBonus[XBoard.pgnRow(sq)];
		if (board.pieceAt(XBoard.getNW(sq)) == BPAWN || 
				board.pieceAt(XBoard.getNE(sq)) == BPAWN ||
				board.pieceAt(sq -1) == BPAWN ||
				board.pieceAt(sq +1) == BPAWN	) {
			value += 15;
		}
		else {
			value = value - (value*40)/100;
		}
		
		if (!board.isEndGame()) {
			value = value - (value*20)/100;
		}
		return value;
	}
	
	
	int[] bpVector = {13, 12, 11}; 
	public boolean isBlackPassedPawnIn(int sq) {
		if ( board.pieceAt(sq) != BPAWN ) return false;
		int xq = 0;
		while (XBoard.pgnRow(sq) >= 2) {			
			for (int offset = 0; offset < 3; offset++) {
				xq = sq + bpVector[offset];
				if (board.pieceAt(xq) == WPAWN) return false;
			}
			sq += 12;
		}
		
		return true;
	}


  	public static final int[] FOO_POSITION = {
  		OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,
  		OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,
  		OUT    ,OUT    ,26     ,27     ,28     ,29     ,30     ,31     ,32     ,33     ,OUT    ,OUT    ,//2
  		OUT    ,OUT    ,38     ,39     ,40     ,41     ,42     ,43     ,44     ,45     ,OUT    ,OUT    ,//3
  		OUT    ,OUT    ,50     ,51     ,52     ,53     ,54     ,55     ,56     ,57     ,OUT    ,OUT    ,//4
  		OUT    ,OUT    ,62     ,63     ,64     ,65     ,66     ,67     ,68     ,69     ,OUT    ,OUT    ,//5
  		OUT    ,OUT    ,74     ,75     ,76     ,77     ,78     ,79     ,80     ,81    ,OUT    ,OUT    ,//6
  		OUT    ,OUT    ,86     ,87     ,88     ,89     ,90     ,91     ,92     ,93    ,OUT    ,OUT    ,//7
  		OUT    ,OUT    ,98     ,99     ,100    ,101    ,102   ,103    ,104    ,105   ,OUT    ,OUT    ,//8
  		OUT    ,OUT    ,110    ,111    ,112    ,113    ,114   ,115    ,116    ,117   ,OUT    ,OUT    ,//9
  		OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,
  		OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT
        //0    //1     //a,2  	b,3     c,4    d,5     e,6     f,7     g,8     h,9	
  	};  		
  	
  	private int whiteTropism = 0;
  	private int blackTropism = 0;
  	
	public int normalEval() {
		int[] bcols = {0,0,0,0,0,0,0,0,0,0,0};
		int[] wcols = {0,0,0,0,0,0,0,0,0,0,0};

		whiteScore = 0;
		blackScore = 0;
		for (int i = 0; i < 8; i++) {
			int sq = board.getWPawnSet()[i];
			if (sq > 0) {
				// bonus p/sq
				whiteScore += bonusOf[1][sq];
				// doubles				
				int col = XBoard.column(sq);				
				whiteScore -= EVAL_DOUBLED_PAWN * wcols[col];
				++wcols[col];			
				// passed
				if (isWhitePassedPawnIn(sq)) {
					
					whiteScore += valueWhitePassedPawn(sq);					
				}
			}			
			
			sq = board.getBPawnSet()[i];
			if (sq > 0) {
				//blackTropism = tropism[XBoard.distance(sq, board.getWhiteKingSquare())]; 
				//blackScore += blackTropism;
				blackScore += bonusOf[2][sq];
				int col = XBoard.column(sq);
				blackScore -= EVAL_DOUBLED_PAWN * bcols[col];
				++bcols[col];
				
				if (isBlackPassedPawnIn(sq)) {
					//System.out.println("bpassed in "+sq);
					
					blackScore += valueBlackPassedPawn(sq);
				}

			}
			
		}

		int cadena = 0;
		int bcadena = 0;
		for (int j = 2; j < 9; j++ ) {
			int pawnsInCol = wcols[j];
			if (pawnsInCol > 0) {
				cadena++;
			}
			else {
				if (cadena == 1 && j > 3) {
					whiteScore -= EVAL_ISOLATED_PAWN; //isolated					
				}
				cadena = 0;
			}
			
			pawnsInCol = bcols[j];
			if (pawnsInCol > 0) {
				bcadena++;
			}
			else {
				if (bcadena == 1 && j > 3) {
					blackScore -= EVAL_ISOLATED_PAWN; //isolated					
				}
				bcadena = 0;
			}
		}

		int value = whiteScore - blackScore;		
		return board.isWhiteTurn()? value: -value;
	}
	
	public int endEval() {
		whiteScore = 0;
		blackScore = 0;
//		int[] wcols = {0,0,0,0,0,0,0,0,0,0,0};
//		int[] bcols = {0,0,0,0,0,0,0,0,0,0,0};
		
		for (int sq : board.getWPawnSet()) {
			if (isWhitePassedPawnIn(sq)) {
				//System.out.println("sumando valueWhitePassedPawn(sq)="+valueWhitePassedPawn(sq));
				whiteScore += valueWhitePassedPawn(sq);					
			}
		}
		
		for (int sq : board.getBPawnSet()) {
			if (isBlackPassedPawnIn(sq)) {
				//System.out.println("sumando valueBlackPassedPawn(sq)="+valueBlackPassedPawn(sq));
				blackScore += valueBlackPassedPawn(sq);
			}
		}
		
		
		int value = whiteScore - blackScore;		
		return board.isWhiteTurn()? value: -value;
	}

	
	public int oldendEval() {
		whiteScore = 0;
		blackScore = 0;
		int[] wcols = {0,0,0,0,0,0,0,0,0,0,0};
		int[] bcols = {0,0,0,0,0,0,0,0,0,0,0};
		for (int i = 0; i < 8; i++) {
			int sq = board.getWPawnSet()[i];
			if (sq > 0) {
				// bonus p/sq
				//whiteScore += END_WHITE_PAWN_BONUS[sq];
				// doubles				
				int col = XBoard.column(sq);
				
				//dobles se penalizan un poco mas en el final
				whiteScore -= END_EVAL_DOUBLED_PAWN * wcols[col];
				++wcols[col];
				if (isWhitePassedPawnIn(sq)) {
					System.out.println("sumando valueWhitePassedPawn(sq)="+valueWhitePassedPawn(sq));
					whiteScore += valueWhitePassedPawn(sq);					
				}
			}
			// isolateds
			
			sq = board.getBPawnSet()[i];
			if (sq > 0) {
				//blackScore += END_BLACK_PAWN_BONUS[sq];
				int col = XBoard.column(sq);
				blackScore -= END_EVAL_DOUBLED_PAWN * bcols[col];
				++bcols[col];			
				System.out.println("if (isBlackPassedPawnIn(sq)) sq es "+sq);
				if (isBlackPassedPawnIn(sq)) {
					System.out.println("sumando valueBlackPassedPawn(sq)="+valueBlackPassedPawn(sq));
					blackScore += valueBlackPassedPawn(sq);
				}
			}
			
		}

		int cadena = 0;
		int bcadena = 0;
		for (int j = 2; j < 9; j++ ) {
			int pawnsInCol = wcols[j];
			if (pawnsInCol > 0) {
				cadena++;
			}
			else {
				if (cadena == 1 && j > 3) {
					whiteScore -= END_EVAL_ISOLATED_PAWN; 					
				}
				cadena = 0;
			}
			
			pawnsInCol = bcols[j];
			if (pawnsInCol > 0) {
				bcadena++;
			}
			else {
				if (bcadena == 1 && j > 3) {
					blackScore -= END_EVAL_ISOLATED_PAWN; 					
				}
				bcadena = 0;
			}
		}

		
		int value = whiteScore - blackScore;		
		return value;
	}



	public int getWhiteScore() {
		return whiteScore;
	}



	public int getBlackScore() {
		return blackScore;
	}

	public int getWhiteTropism() {
		return whiteTropism;
	}

	public int getBlackTropism() {
		return blackTropism;
	}


}
