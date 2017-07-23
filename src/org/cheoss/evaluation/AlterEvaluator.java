package org.cheoss.evaluation;

import org.cheoss.board.*;
import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class AlterEvaluator implements IEvaluator, IConstantsEval, Constants {
	
	int[] knightMobilityValues = {-35, -15, 0, 7, 33, 45, 50, 54, 59};
	int[] queenMobilityValues = {-35, -25, -15, -7, -6, -5, -4, 0, 0, 22, 25, 29, 32, 46, 57, 59, 63,
			64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64};
	int[] rookMobilityValues = {-33, -29, -7, 0, 0, 5, 10, 15, 25, 37, 41, 42, 47, 49, 53, 57, 57};


	private int[] adjustEndMaterial = {0, 25, 25, -40, -40, -30, -30, 40, 40, 205, 205, 0, 0 };
	
    int whiteScore = 0;
    int blackScore = 0;
	
	private Board board;

	public AlterEvaluator(Board board) {
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
		//int scoreTropism[] = new int[2];
		int scoreMobility[] = new int[2];
		
		for (int sq : board.getPiecesSet()) {
			int piece = board.pieceAt(sq);		
			switch (piece) {
			case OUT:
				break;
			case WKNIGHT:
				scorePcsq[0] += bonusOf[piece][sq];
				//return Pieces.KNIGHT_DIRECTIONS[sq].length;
				//scoreMobility[0] += knightMobilityValues[Pieces.KNIGHT_DIRECTIONS[sq].length];
				scoreMobility[0] += knightMobilityValues[numMovesForKnight(sq, WKNIGHT)];
				//scoreTropism[0] += tropism(piece, sq, board.getBlackKingSquare());
				//if (isAttackedByPawn(sq, BLACK)) whiteScore -= PENALTY_MINOR_ATCK_BY_PAWN;
				break;
			case BKNIGHT:
				scorePcsq[1] += bonusOf[piece][sq];
				scoreMobility[1] += knightMobilityValues[numMovesForKnight(sq, BKNIGHT)];
				//scoreTropism[1] += tropism(piece, sq, board.getWhiteKingSquare());
				//if (isAttackedByPawn(sq, WHITE)) blackScore -= PENALTY_MINOR_ATCK_BY_PAWN;
				break;
			case WBISHOP:
				scorePcsq[0] += bonusOf[piece][sq];				
				scoreMobility[0] += rookMobilityValues[numMovesForBishop(sq, WBISHOP)];	
				//if (isAttackedByPawn(sq, BLACK)) whiteScore -= PENALTY_MINOR_ATCK_BY_PAWN;
				//scoreTropism[0] += tropism(piece, sq, board.getBlackKingSquare());
				break;
			case BBISHOP:
				scorePcsq[1] += bonusOf[piece][sq];				
				scoreMobility[1] += rookMobilityValues[numMovesForBishop(sq, BBISHOP)];
				//scoreTropism[1] += tropism(piece, sq, board.getWhiteKingSquare());
				//if (isAttackedByPawn(sq, WHITE)) blackScore -= PENALTY_MINOR_ATCK_BY_PAWN;
				break;
			case WROOK:
				scorePcsq[0] += bonusOf[piece][sq];				
				scoreMobility[0] += rookMobilityValues[numMovesForRook(sq, WROOK)];
				if (XBoard.isPromoRank(sq, WHITE)) whiteScore += 37;
//				if (isAttackedByPawn(sq, BLACK)) whiteScore -= PENALTY_ROOK_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, BKNIGHT)) whiteScore -= PENALTY_ROOK_ATCK_BY_KNIGHT;
				//scoreTropism[0] += tropism(piece, sq, board.getBlackKingSquare());
				break;
			case BROOK:
				scorePcsq[1] += bonusOf[piece][sq];				
				scoreMobility[1] += rookMobilityValues[numMovesForRook(sq, BROOK)];
				if (XBoard.isPromoRank(sq, BLACK)) blackScore += 37;
//				if (isAttackedByPawn(sq, WHITE)) blackScore -= PENALTY_ROOK_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, WKNIGHT)) blackScore -= PENALTY_ROOK_ATCK_BY_KNIGHT;

				//scoreTropism[1] += tropism(piece, sq, board.getWhiteKingSquare());
				break;
			case WQUEEN:
				scorePcsq[0] += bonusOf[piece][sq];				
				scoreMobility[0] += queenMobilityValues[numMovesForQueen(sq, WQUEEN)];
//				if (isAttackedByPawn(sq, BLACK)) whiteScore -= PENALTY_QUEEN_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, BKNIGHT)) whiteScore -= PENALTY_QUEEN_ATCK_BY_KNIGHT;

				//scoreTropism[0] += tropism(piece, sq, board.getBlackKingSquare());
				break;
			case BQUEEN:
				scorePcsq[1] += bonusOf[piece][sq];				
				scoreMobility[1] += queenMobilityValues[numMovesForQueen(sq, BQUEEN)];
//				if (isAttackedByPawn(sq, WHITE)) blackScore -= PENALTY_QUEEN_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, WKNIGHT)) blackScore -= PENALTY_QUEEN_ATCK_BY_KNIGHT;
				//scoreTropism[1] += tropism(piece, sq, board.getWhiteKingSquare());
				break;
			case WKING:
//				if (isAttackedByPawn(sq, BLACK)) whiteScore -= PENALTY_QUEEN_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, BKNIGHT)) whiteScore -= PENALTY_QUEEN_ATCK_BY_KNIGHT;
				scorePcsq[0] += bonusOf[piece][sq];
				break;
			case BKING:
//				if (isAttackedByPawn(sq, WHITE)) blackScore -= PENALTY_QUEEN_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, WKNIGHT)) blackScore -= PENALTY_QUEEN_ATCK_BY_KNIGHT;
				scorePcsq[1] += bonusOf[piece][sq];
			}			
		}
				
		int watk = evalAttacksToEnemyKing(BLACK);
		int batk = evalAttacksToEnemyKing(WHITE);
				
		whiteScore = board.getMaterialWhite() + scoreMobility[0] + scorePcsq[0] /*+ scoreTropism[0]*/ + watk;
		blackScore = board.getMaterialBlack() + scoreMobility[1] + scorePcsq[1] /*+ scoreTropism[1]*/ + batk;
		
//		System.out.println("   Blancas");
//		System.out.println("Material: "+scoreMaterial[0]);
//		System.out.println("Pcsq: "+scorePcsq[0]);
//		System.out.println("Mobility: "+scoreMobility[0]);
//		System.out.println("Tropism: "+scoreTropism[0]);
//		System.out.println("    Negras");
//		System.out.println("Material: "+scoreMaterial[1]);
//		System.out.println("Pcsq: "+scorePcsq[1]);
//		System.out.println("Mobility: "+scoreMobility[1]);
//		System.out.println("Tropism: "+scoreTropism[1]);

		
		int value = whiteScore - blackScore;			
		return board.isWhiteTurn()? value: -value;
	}
		
  	public static final int[] FOO_POSITION = {//10-(square/12);
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
  	
  	
  	//int[] pinnedPieces = new int[11];
  	///////////////// Mobility  	
    private int validStepMove(int source, int offset, int pieceMoved) {    
    	int target = source + offset;   
    	if (board.pieceAt(target) == OUT) {
    		return 0;
    	}
    	else {
    		return board.getGenerator().isPiecePinned(pieceMoved, source, target, true);
//			if ( attacker == 0) {
//				return 1;	
//			}
//			else {
//				return 0;
//			}		
			
    	}    	
	}

    private int validSlides( int source, int offset, int pieceMoved) {
    	int target = source + offset;    	
    	int slides = 0;
    	int attacker = 0;
        if (board.pieceAt(target) == OUT) {
        	return 0;
        }
        
        attacker = board.getGenerator().isPiecePinned(pieceMoved, source, target, true);
        if (attacker > 0) {
        	if (Pieces.isWhite(pieceMoved)) {
        		whiteScore -= scorePinnedPiece(pieceMoved, attacker, source); 
        	}
        	else {
        		blackScore -= scorePinnedPiece(pieceMoved, attacker, source);
        	}
        	
        	return 0;
        }
        
        do {        	
            if (board.pieceAt(target) == EMPTY) {
                slides++;
				target += offset;
            }
            else {
                slides++; // cuenta tanto capturas como piezas defendidas
                return slides;            		
            }
        }while(board.pieceAt(target) != OUT);
        return slides;
	}


    private int numMovesForKnight(int sq, int pieceMoved) {
    	return Pieces.KNIGHT_DIRECTIONS[sq].length;
    }
    
    private int numMovesForBishop(int sq, int pieceMoved) {
    	int moves = 0;
    	for (int index = 0; index < bishopOffsets.length; index++) {
    		moves += validSlides(sq, bishopOffsets[index], pieceMoved);
    	}
		return moves;
    }
    private int numMovesForRook(int sq, int pieceMoved) {
    	int moves = 0;
		for (int index = 0; index < rookOffsets.length; index++) {
			moves += validSlides(sq, rookOffsets[index], pieceMoved);
		}    
		return moves;
    }
    private int numMovesForQueen(int sq, int pieceMoved) {
    	int moves = 0;
		for (int index = 0; index < bishopOffsets.length; index++) {
			moves += validSlides(sq, bishopOffsets[index], pieceMoved);
			moves += validSlides(sq, rookOffsets[index], pieceMoved);
		}
		return moves;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    
    ///////////////// Tropism
//    private int tropism(int piece, int sq, int kingSquare) {
//    	int distance = XBoard.distance(sq, kingSquare);
//    	
//    	return tropism[piece][distance];
//	}

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    private int xendEval() {
		whiteScore += board.getMaterialWhite();
		blackScore += board.getMaterialBlack();
		
		int value = whiteScore - blackScore;		
		return board.isWhiteTurn()? value: -value;
    }
    
	private int endEval() {
		int scorePcsq[] = new int[2];
		//int scoreTropism[] = new int[2];
		//int scoreMobility[] = new int[2];
		whiteScore = 0;
		blackScore = 0;
		
		for (int sq : board.getPiecesSet()) {
			int piece = board.pieceAt(sq);		
			switch (piece) {
			case OUT:
				break;
			case WKNIGHT:
				scorePcsq[0] += bonusOf[piece][sq];
				//scoreMobility[0] += knightMobilityValues[numMovesForKnight(sq, WKNIGHT)];
				//scoreTropism[0] += tropism(piece, sq, board.getBlackKingSquare());
				whiteScore += adjustEndMaterial[piece];
				//if (isAttackedByPawn(sq, BLACK)) whiteScore -= 50;
				break;
			case BKNIGHT:
				scorePcsq[1] += bonusOf[piece][sq];
				//scoreMobility[1] += knightMobilityValues[numMovesForKnight(sq, BKNIGHT)];
				//scoreTropism[1] += tropism(piece, sq, board.getWhiteKingSquare());
				blackScore += adjustEndMaterial[piece];
				//if (isAttackedByPawn(sq, WHITE)) blackScore -= 50;
				break;
			case WBISHOP:
				scorePcsq[0] += bonusOf[piece][sq];				
				//scoreMobility[0] += rookMobilityValues[numMovesForBishop(sq, WBISHOP)];
				//scoreTropism[0] += tropism(piece, sq, board.getBlackKingSquare());
				whiteScore += adjustEndMaterial[piece];
				//if (isAttackedByPawn(sq, BLACK)) whiteScore -= 65;
				break;
			case BBISHOP:
				scorePcsq[1] += bonusOf[piece][sq];				
				//scoreMobility[1] += rookMobilityValues[numMovesForBishop(sq, BBISHOP)];
				//scoreTropism[1] += tropism(piece, sq, board.getWhiteKingSquare());
				blackScore += adjustEndMaterial[piece];
				//if (isAttackedByPawn(sq, WHITE)) blackScore -= 65;
				break;
			case WROOK:
				scorePcsq[0] += bonusOf[piece][sq];				
				//scoreMobility[0] += rookMobilityValues[numMovesForRook(sq, WROOK)];
				if (XBoard.isPromoRank(sq, WHITE)) whiteScore += 37;
				//scoreTropism[0] += tropism(piece, sq, board.getBlackKingSquare());
				whiteScore += adjustEndMaterial[piece];
//				if (isAttackedByPawn(sq, BLACK)) whiteScore -= PENALTY_ROOK_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, BKNIGHT)) whiteScore -= PENALTY_ROOK_ATCK_BY_KNIGHT;

				break;
			case BROOK:
				scorePcsq[1] += bonusOf[piece][sq];				
				//scoreMobility[1] += rookMobilityValues[numMovesForRook(sq, BROOK)];
				if (XBoard.isPromoRank(sq, BLACK)) blackScore += 37;
				//scoreTropism[1] += tropism(piece, sq, board.getWhiteKingSquare());
				blackScore += adjustEndMaterial[piece];
//				if (isAttackedByPawn(sq, WHITE)) blackScore -= PENALTY_ROOK_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, WKNIGHT)) blackScore -= PENALTY_ROOK_ATCK_BY_KNIGHT;

				break;
			case WQUEEN:
				scorePcsq[0] += bonusOf[piece][sq];				
				//scoreMobility[0] += queenMobilityValues[numMovesForQueen(sq, WQUEEN)]; 
				//scoreTropism[0] += tropism(piece, sq, board.getBlackKingSquare());
				whiteScore += adjustEndMaterial[piece];
//				if (isAttackedByPawn(sq, BLACK)) whiteScore -= PENALTY_QUEEN_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, BKNIGHT)) whiteScore -= PENALTY_QUEEN_ATCK_BY_KNIGHT;

				break;
			case BQUEEN:
				scorePcsq[1] += bonusOf[piece][sq];				
				//scoreMobility[1] += queenMobilityValues[numMovesForQueen(sq, BQUEEN)];
				//scoreTropism[1] += tropism(piece, sq, board.getWhiteKingSquare());
				blackScore += adjustEndMaterial[piece];
//				if (isAttackedByPawn(sq, WHITE)) blackScore -= PENALTY_QUEEN_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, WKNIGHT)) blackScore -= PENALTY_QUEEN_ATCK_BY_KNIGHT;
				break;

			case WKING:
//				if (isAttackedByPawn(sq, BLACK)) whiteScore -= PENALTY_QUEEN_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, BKNIGHT)) whiteScore -= PENALTY_QUEEN_ATCK_BY_KNIGHT;
				scorePcsq[0] += END_WHITE_KING_BONUS[sq];
				break;
			case BKING:
//				if (isAttackedByPawn(sq, WHITE)) blackScore -= PENALTY_QUEEN_ATCK_BY_PAWN;
//				if (isAttackedByKnight(sq, WKNIGHT)) blackScore -= PENALTY_QUEEN_ATCK_BY_KNIGHT;
				scorePcsq[1] += END_BLACK_KING_BONUS[sq];
			}			
		}
				
		//System.out.println("board.getMaterialWhite()="+board.getMaterialWhite()+" board.getMaterialBlack()="+board.getMaterialBlack());
		whiteScore += board.getMaterialWhite() /*+ scoreMobility[0]*/ + scorePcsq[0] /* +scoreTropism[0]*/;
		blackScore += board.getMaterialBlack() /*+ scoreMobility[1]*/ + scorePcsq[1] /* +scoreTropism[1]*/;
		
		int value = whiteScore - blackScore;		
		return board.isWhiteTurn()? value: -value;
	}
	
	
	private int[] scoreAtks  = {0, 7, 50, 70, 120, 180, 190, 190, 190, 190, 190, 190 };
	private int[] pAtks  = {0, 0, 25, 75 };
	
	//private int[] scoreAtks  = {0, 17, 53, 120, 160, 180, 220, 240, 240, 240, 240, 240 };
	
	
	private int evalAttacksToEnemyKing(boolean enemyKingColor) {
		int attacks = 0;
		int scoreAtk = 0;
		int ptosAtK = 0;
		if (enemyKingColor == WHITE) {
			int sq1 = board.getWhiteKingSquare() - 11;
			int sq2 = board.getWhiteKingSquare() - 12;
			int sq3 = board.getWhiteKingSquare() - 13;

			if (board.pieceAt(sq1) != OUT) {
				attacks = board.countAttackers(sq1, BLACK);
				if (attacks > 0) {
					scoreAtk += scoreAtks[attacks];
					ptosAtK++;
				}									
			}

			if (board.pieceAt(sq2) != OUT) {
				attacks = board.countAttackers(sq2, BLACK);
				if (attacks > 0) {
					scoreAtk += scoreAtks[attacks];
					ptosAtK++;
				}													
			}

			if (board.pieceAt(sq3) != OUT) {
				attacks = board.countAttackers(sq3, BLACK);
				if (attacks > 0) {
					scoreAtk += scoreAtks[attacks];
					ptosAtK++;
				}				
			}

			return scoreAtk +pAtks[ptosAtK];
		}
		else {
			int sq1 = board.getBlackKingSquare() + 11;
			int sq2 = board.getBlackKingSquare() + 12;
			int sq3 = board.getBlackKingSquare() + 13;

			if (board.pieceAt(sq1) != OUT) {
				attacks = board.countAttackers(sq1, WHITE);
				if (attacks > 0) {
					scoreAtk += scoreAtks[attacks];
					ptosAtK++;
				}								
			}

			if (board.pieceAt(sq2) != OUT) {
				attacks = board.countAttackers(sq2, WHITE);
				if (attacks > 0) {
					scoreAtk += scoreAtks[attacks];
					ptosAtK++;
				}								
			}

			if (board.pieceAt(sq3) != OUT) {
				attacks = board.countAttackers(sq3, WHITE);
				if (attacks > 0) {
					scoreAtk += scoreAtks[attacks];
					ptosAtK++;
				}	
			}

			return scoreAtk +pAtks[ptosAtK];
		}

	}


	
	
	private int scorePinnedPiece(int pieceMoved, int attacker, int source) {
		switch ( pieceMoved ) {
		case WKNIGHT:
			if ( attacker > BBISHOP) {
				if ( !board.isProtectedByPawn(source, WHITE) ) return 50; 						
			}
			else {
				if ( !board.isProtectedByPawn(source, WHITE) ) return 70;
			}
			break;
		case BKNIGHT:
			if ( attacker > WBISHOP) {
				if ( !board.isProtectedByPawn(source, BLACK) ) return 50; 						
			}
			else {
				if ( !board.isProtectedByPawn(source, BLACK) ) return 70;
			}
			break;
			
		case WBISHOP:
			if ( attacker > BBISHOP) {
				if ( !board.isProtectedByPawn(source, WHITE) ) return 50; 						
			}
			else {
				if ( !board.isProtectedByPawn(source, WHITE) ) return 70;
			}
			break;
		case BBISHOP:
			if ( attacker > WBISHOP) {
				if ( !board.isProtectedByPawn(source, BLACK) ) return 50; 						
			}
			else {
				if ( !board.isProtectedByPawn(source, BLACK) ) return 70;
			}
			break;
			
		case WROOK:
			if ( attacker < BROOK) {
				if ( board.isProtectedByPawn(source, WHITE) ) return 200; 	
				else return  250;
			}
			break;
		case BROOK:
			if ( attacker < WROOK) {
				if ( board.isProtectedByPawn(source, BLACK) ) return 200; 	
				else return  250;
			}
			break;
			
		case WQUEEN:
			if ( attacker == BROOK) {
				if ( board.isProtectedByPawn(source, WHITE) ) return 500; 	
				else return  550;
			}
			else if ( attacker == BBISHOP) {
				if ( board.isProtectedByPawn(source, WHITE) ) return 700; 	
				else return  750;
			}
			break;
		case BQUEEN:
			if ( attacker == WROOK) {
				if ( board.isProtectedByPawn(source, BLACK) ) return 500; 	
				else return  550;
			}
			else if ( attacker == BBISHOP) {
				if ( board.isProtectedByPawn(source, BLACK) ) return 700; 	
				else return  750;
			}
			break;
		default:
			return 0;
		}
		
		return 0;
	}
	
	private boolean isAttackedByKnight(int from, int knightAttacker) {
		for (int sq : Pieces.KNIGHT_DIRECTIONS[from]) {			
			if (board.pieceAt(sq) == knightAttacker ) return true;
    	}
		return false;
	}


	
	private boolean isAttackedByPawn(int sq, boolean colorPawnAttacker) {
		if (colorPawnAttacker) {// ataque blanco
			//-11 y -13
			if (board.pieceAt(sq - 11) == WPAWN) return true; 
			if (board.pieceAt(sq - 13) == WPAWN) return true;
		}
		else {
			//+11 y +13
			if (board.pieceAt(sq + 11) == BPAWN) return true; 
			if (board.pieceAt(sq + 13) == BPAWN) return true;
		}
		return false;
	}

	
}
