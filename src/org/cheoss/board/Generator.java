package org.cheoss.board;

import java.util.*;

import org.cheoss.evaluation.*;
import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class Generator implements Constants, IConstantsEval {
	
	protected Board board;
	protected int localIndex = 0;
	
	public Generator(Board board) {
		this.board = board;		
	}
	
	public int genMoves(int[] moves, int firstKiller, int secondKiller, int ttMove) {
		this.firstKiller = firstKiller;
		this.secondKiller = secondKiller;
		this.ttMove = ttMove;
		return genMoves(moves);
	}

	private int genMoves(int[] moves) {
		this.localIndex = 0;
		
		if (board.isWhiteTurn()) {
			for (Integer square : board.getWPawnSet()) {
				if (square > 0) {
					addPawnMoves(moves, square, WPAWN, -11, 
							7, -13, BPAWN, -12, 2, -24);
				}				
			}

			for (int i = 0; i < board.getPiecesSet().length; i++) {
				int square = board.getPiecesSet()[i];
				if (square == 0) continue;
				int piece = board.pieceAt(square);
				switch(piece){
				case WKNIGHT:
//					steps(Pieces.KNIGHT_DIRECTIONS[square], square, moves);					
					for (int index = 0; index < knightOffsets.length; index++) {
						addStep( moves, square, knightOffsets[index]);
					}
					break;
				case WBISHOP:
//					slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
					for (int index = 0; index < bishopOffsets.length; index++) {
						addSlides( moves, square, bishopOffsets[index]);
					}
					break;
				case WROOK:
//					slides(Pieces.ROOK_DIRECTIONS[square], square, moves);
					for (int index = 0; index < rookOffsets.length; index++) {
						addSlides( moves, square, rookOffsets[index]);
					}
					break;
				case WQUEEN:
//					slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
//					slides(Pieces.ROOK_DIRECTIONS[square], square, moves);
					for (int index = 0; index < bishopOffsets.length; index++) {
						addSlides( moves, square, bishopOffsets[index]);
						addSlides( moves, square, rookOffsets[index]);
					}
					break;

				case WKING:
					addWhiteCastlings( moves);
					for (int index = 0; index < kingOffsets.length; index++) {
						addStep( moves, square, kingOffsets[index]);
					}
					break;
				}                
			}
		}// end white
		else {// start black
			for (Integer square : board.getBPawnSet()) {
				if (square > 0) {
					addPawnMoves(moves, square, BPAWN, 11, 
							2, 13, WPAWN, 12, 7, 24);
				}				
			}			

			for (int i = 0; i < board.getPiecesSet().length; i++) {        		
				int square = board.getPiecesSet()[i];
				if (square == 0) continue;
				int piece = board.pieceAt(square);
				switch(piece){
				case BKNIGHT:
//					steps(Pieces.KNIGHT_DIRECTIONS[square], square, moves);
					for (int index = 0; index < knightOffsets.length; index++) {
						addStep( moves, square, knightOffsets[index]);
					}
					break;
				case BBISHOP:
//					slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
					for (int index = 0; index < bishopOffsets.length; index++) {
						addSlides( moves, square, bishopOffsets[index]);
					}
					break;
				case BROOK:
//					slides(Pieces.ROOK_DIRECTIONS[square], square, moves);
					for (int index = 0; index < rookOffsets.length; index++) {
						addSlides( moves, square, rookOffsets[index]);
					}    
					break;
				case BQUEEN:
//					slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
//					slides(Pieces.ROOK_DIRECTIONS[square], square, moves);
					for (int index = 0; index < bishopOffsets.length; index++) {
						addSlides( moves, square, bishopOffsets[index]);
						addSlides( moves, square, rookOffsets[index]);
					}
					break;

				case BKING:            		   
					addBlackCastlings( moves);
					for (int index = 0; index < kingOffsets.length; index++) {
						addStep( moves, square, kingOffsets[index]);
					}               	
					break;
				}
			}
		}        

		return localIndex;
	}

	
	
	///////////////// 
	// Castlings
    private void addWhiteCastlings( int[] moves) {
    	if (board.getCsWhiteShort() == 1) {
    		if (board.isPossibleWhiteShortCastling()) {
    			addMove(moves, WHITE_SHORT_CASTLING);
    		}
    	}
    	
    	if (board.getCsWhiteLong() == 1) {
    		if (board.isPossibleWhiteLongCastling()) {
    			addMove(moves, WHITE_LONG_CASTLING);
    		}    		
    	}
    }

    private void addBlackCastlings( int[] moves) {
    	if (board.getCsBlackShort() == 1) {
    		if (board.isPossibleBlackShortCastling()) {
    			addMove(moves, BLACK_SHORT_CASTLING);
    		}
    	}
    	
    	if (board.getCsBlackLong() == 1) {
    		if (board.isPossibleBlackLongCastling()) {
    			addMove(moves, BLACK_LONG_CASTLING);
    		}    		
    	}
    }

	
	///////////////// 
	// Slide-Moves: Bishops, Rooks and Queens
    private void addSlides( int[] moves, int source, int offset) {
    	int target = source + offset;    	
    	int move = 0;
        if (board.pieceAt(target) == OUT) {
        	return;
        }
        
        do {        	
            if (board.pieceAt(target) == EMPTY) {
                    move = XMove.pack(source, target, board.pieceAt(source), 0, 0);                	
                    addMove(moves, move);
    				target += offset;
            }
            else if (board.isValidCaptureForPiece(board.pieceAt(source), target)) {            	
                move = XMove.pack(source, target, board.pieceAt(source), board.pieceAt(target), 0);
                addMove(moves, move);
                return;
            }
            else {
                return;
            }
        }while(board.pieceAt(target) != OUT);
	}

	
	///////////////// 
	// One-Step-Moves: Knights and Kings
    private void addStep( int[] moves, int source, int offset) {    	    	
    	int target = source + offset;
    	int move = 0;
		switch (board.pieceAt(target)) {
		case OUT:
			return;
		case EMPTY:
			move = XMove.pack(source, target, board.pieceAt(source), 0, 0);
			addMove(moves, move);
			return;
		default:
			// capture (valid square whith an enemy piece)	
			if (board.isValidCaptureForPiece(board.pieceAt(source), target)) {
				move = XMove.pack(source, target, board.pieceAt(source), board.pieceAt(target), 0 );
				addMove(moves, move);
				return;
			}
			// ... imposible move (a friend piece was found in target square)
			else {
				return;
			}
		}
	}
    
    final int[] promos = {FLAG_PROMO_QUEEN , FLAG_PROMO_ROOK, FLAG_PROMO_BISHOP, FLAG_PROMO_KNIGHT };
    private void addPromotions(int[] moves, int from, int to, int pieceMoved, int pieceCaptured) {
    	//int move = XMove.pack(from, to, pieceMoved, pieceCaptured, FLAG_PROMO_QUEEN);    	addMove(moves, move);

    	int move = 0;
    	for (int flag : promos) {
    		move = XMove.pack(from, to, pieceMoved, pieceCaptured, flag);
    		addMove(moves, move);
    	}    	
    }
    
	// [victima][attacker]
	// Asign a value for capture (MVV/LVA Most Valuable Victim/Least Valuable Attacker)
	// menos de 50 es captura mala
	// 51, 52, 53, 54, 55 reservado
	// de 55 a 70 neutras
	// 71 captura neutra
	// 72 a 120 capturas buenas
	// > 120 reservado
    
    // 111 es la peor de las capturas buenas
    // usaremos 101 para el primer killer y 102 para el segundo
    
	public static final int victim_atacker[][] = {
		{}, // 0
			{ // pieza comida: peon 1, 2
				0,
				71, 71, // peon come
				49, 49, // caballo come
				48, 48, // alfil come
				45, 45, //torre come
				41, 41, //dama come
				120, 120, //rey come
			},
			{ // pieza comida: peon 1, 2
				0,
				71, 71, // peon come
				49, 49, // caballo come
				48, 48, // alfil come
				45, 45, //torre come
				41, 41, //dama come
				120, 120, //rey come
			},

			{ // pieza comida: caballo 
				0,
				113, 113, // peon come
				72, 72, // caballo come
				72, 72, // alfil come
				46, 46, //torre come
				42, 42, //dama come
				120, 120, //rey come
			},
			{ // pieza comida: caballo 
				0,
				113, 113, // peon come
				72, 72, // caballo come
				72, 72, // alfil come
				46, 46, //torre come
				42, 42, //dama come
				120, 120, //rey come
			},

			{ // pieza comida: alfil
				0,
				114, 114, // peon come
				73, 73, // caballo come
				73, 73, // alfil come
				47, 47, //torre come
				43, 43, //dama come
				120, 120, //rey come
			},
			{ // pieza comida: alfil
				0,
				114, 114, // peon come
				73, 73, // caballo come
				73, 73, // alfil come
				47, 47, //torre come
				43, 43, //dama come
				120, 120, //rey come
			},

			{ // pieza comida: torre
				0,
				116,116, // peon come
				112, 112, // caballo come
				111, 111, // alfil come
				74, 74, //torre come
				44, 44, //dama come
				120, 120, //rey come
			},
			{ // pieza comida: torre
				0,
				116,116, // peon come
				112, 112, // caballo come
				111, 111, // alfil come
				74, 74, //torre come
				44, 44, //dama come
				120, 120, //rey come
			},

			{ // pieza comida: dama
				0,
				119, 119, // peon come
				118, 118, // caballo come
				117, 117, // alfil come
				115, 115, //torre come
				75, 75, //dama come
				120, 120, //rey come
			},
			{ // pieza comida: dama
				0,
				119, 119, // peon come
				118, 118, // caballo come
				117, 117, // alfil come
				115, 115, //torre come
				75, 75, //dama come
				120, 120, //rey come
			},

			{ // pieza comida: rey 
				0,
				120,120, // peon come
				120,120, // caballo come
				120,120, // alfil come
				120,120, //torre come
				120,120, //dama come
				120,120, //rey come
			},
			{ // pieza comida: rey 
				0,
				120,120, // peon come
				120,120, // caballo come
				120,120, // alfil come
				120,120, //torre come
				120,120, //dama come
				120,120, //rey come
			}

	};

	
	
    private int firstKiller = 0;
    private int secondKiller = 0;
    private int ttMove = 0;
    protected int score(int move, boolean isLegalGen) {
    	if (XMove.equals( ttMove, move)) {
    		return 125;
    	}
    	else if (XMove.isCapture(move)) {
    		if (XMove.flag(move) == FLAG_PROMO_QUEEN) {
    			return 115;	
    		}    		
    		
    		return victim_atacker[XMove.pieceCaptured(move)][XMove.pieceMoved(move)]; 
    	}   
    	else if (XMove.flag(move) == FLAG_PROMO_QUEEN) {    		
    		return 115;
    	}
    	else if (XMove.equals(firstKiller, move)) {
    		return 102;
    	}
    	else if (XMove.equals( secondKiller, move)) {
    		return 101;
    	}
    	
    	else if (XMove.flag(move) == FLAG_CASTLING) {
    		if (XMove.equals(move, WHITE_SHORT_CASTLING)|| XMove.equals(move, BLACK_SHORT_CASTLING)) {
    			return 70;
    		}
    		else {
    			return 69;
    		}    		
    	}
    	else {
    		//int score = 50;
    		//int from = XMove.pieceMoved(move);

    		if (leavePieceInPrise(move)) return 0;
    		if (XBoard.isPawn(XMove.pieceMoved(move))) return 1;
    		int score = bonusOf[XMove.pieceMoved(move)][XMove.to(move)] - 
    			bonusOf[XMove.pieceMoved(move)][XMove.from(move)];     			
    		if (score < 0) return 0;
    		return score;
    	}
    }

    ////////////////
    // Pawns    
    
    protected void addMove(int[] moves, int move) {     
    	if (move == 0) return;		
    	moves[localIndex++] = XMove.changeScore(move, score(move, false));
    }
    
    private void addPawnMoves( int[] moves, int square, int pawn,
    		int captureLeft, int sevenRow, int captureRight, int enemyPawn,
    		int step, int jumpRow, int jump){
    	int move = 0;
    	
    	// diagonal captures left
    	if (board.isValidCaptureForPiece(pawn, square + captureLeft)) {// != OUT, !=EMPTY and enemy
    		if (XBoard.pgnRow(square) == sevenRow) {
    			addPromotions(moves, square, square + captureLeft, pawn, board.pieceAt(square + captureLeft));
    		}
    		else {
    			move = XMove.pack(square, square + captureLeft, pawn, board.pieceAt(square + captureLeft), 0);
    			addMove(moves, move);        			
    		}
    	}
    	
    	// diagonal captures right
    	if (board.isValidCaptureForPiece(pawn, square + captureRight)) {// != OUT, !=EMPTY and enemy
    		if (XBoard.pgnRow(square) == sevenRow) {
    			addPromotions(moves, square, square + captureRight, pawn, board.pieceAt(square + captureRight));	
    		}
    		else {
        		move = XMove.pack(square, square + captureRight, pawn, board.pieceAt(square + captureRight), 0);
        		addMove(moves, move);
    		}
    	}
    	
    	// en passants captures
    	int enPassant = board.getEnPassant();
    	if (enPassant == square + captureLeft) {
    		move = XMove.pack(square, square + captureLeft, pawn, enemyPawn, FLAG_EN_PASSANT_CAPTURE);
    		addMove(moves, move);
    	}
    	else if (enPassant == square + captureRight) {
    		move = XMove.pack(square, square + captureRight, pawn, enemyPawn, FLAG_EN_PASSANT_CAPTURE);
    		addMove(moves, move);
    	}    	
    	
    	//quiet promotion
    	if (XBoard.pgnRow(square) == sevenRow) {
    		if (board.pieceAt(square + step) == EMPTY){
    			addPromotions(moves, square, square + step, pawn, 0);
    		}
    	}

    	// normal one step move
    	if (board.pieceAt(square + step) == EMPTY && XBoard.pgnRow(square) != sevenRow){
			move = XMove.pack(square, square + step, pawn, 0, 0);        	
			addMove(moves, move);    		    			
    		// initial jump
    		if(XBoard.pgnRow(square) == jumpRow && board.pieceAt(square + jump) == EMPTY) {
    			move = XMove.pack(square, square + jump, pawn, 0, FLAG_PAWN_JUMP );
    			addMove(moves, move);
    		}
    	}    	
    }
	
	public ArrayList<Integer> genMovesTo(int piece, int to) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int[] vmoves = new int[MAX_MOVES_IN_STACK];
		genMoves(vmoves);
		for(int i=0; i<localIndex; i++) {
			int move = vmoves[i];
			if (piece == XMove.pieceMoved(move)) {
				if ( to == XMove.to(move)) {
					list.add(move);
				}
			}
		}		
		return list;
	}
	
	private boolean leavePieceInPrise(int move) {
		return board.isProtectedByPawn(XMove.to(move), !XMove.isWhite(move));
	}
	
	// Returns the attacker piece
	// boolean pinned => se busca clavada o descubierta
	public int isPiecePinned(int piece, int moveFrom, int moveTo, boolean pinned) {
		int kingSquare = 0; 
		if (pinned) {
			kingSquare = piece % 2 == 0? board.getBlackKingSquare() : board.getWhiteKingSquare();	
		}
		else {
			kingSquare = piece % 2 == 0? board.getWhiteKingSquare() : board.getBlackKingSquare();
		}
		

		if (XBoard.shareColumn(kingSquare, moveFrom) || XBoard.shareRow(kingSquare, moveFrom)) {
			//possible check
			// determinar potenciales atacantes, depndiendo del color, dama y torre negras o blancas
			int attacker1 = 0;
			int attacker2 = 0;
			if (pinned) {
				if (piece % 2 == 0) {
					attacker1 = WQUEEN;
					attacker2 = WROOK;
				}
				else {
					attacker1 = BQUEEN;
					attacker2 = BROOK;					
				}				
			}
			else {
				if (piece % 2 == 0) {
					attacker1 = BQUEEN;
					attacker2 = BROOK;
				}
				else {
					attacker1 = WQUEEN;
					attacker2 = WROOK;					
				}
				
			}

			/**/
			//determinar dirección y sentido de avance
			int offset = 0;
			if (XBoard.shareColumn(kingSquare, moveFrom)) {
				// Si comparten col, dirección N-S, sentido según color
				offset = kingSquare > moveFrom? 12:-12; 
			}
			else {
				// Si comparten row, dirección E-O, sentido según color
				offset = kingSquare < moveFrom? -1:1;
			}
			/**/

			// 1 buscar el rey
			// primero recorrer hacia el sur desde la casilla inmediatamente inferior al origen
			// del move hasta encontrar obstáculo o el rey
			// si se encuentra obstáculo, salir, no hay jaque
			// si se encuentra al rey continuar con el siguiente recorrido
			for (int target = moveFrom + offset; target != kingSquare; target = target + offset) {
				if (board.pieceAt(target) != EMPTY){
					return 0;
				}						
			}					
			// 2 buscar el atacante
			// recorrer hacia el norte desde la casilla inmediatamente superior al origen 
			// del move hasta encontrar un obstáculo o una pieza atacante					
			for (int target = moveFrom - offset; board.pieceAt(target) != OUT; target = target - offset) {
				if (board.pieceAt(target) == attacker1) {
					return attacker1;
				}
				else if (board.pieceAt(target) == attacker2) {
					return attacker2;
				}
				else if (board.pieceAt(target) != EMPTY) { // pieza amiga
					return 0;
				}
			}
			//Hemos llegado al final sin encontrar atacantes, no hay jaque y salimos
			return 0;
		}		
		else if (XBoard.shareDiagonals(kingSquare, moveFrom)) {
			//possible check
			// determinar potenciales atacantes, depndiendo del color, dama y torre negras o blancas
			int attacker1 = 0;
			int attacker2 = 0;
			
			if (pinned) {
				if (piece % 2 == 0) {
					attacker1 = WQUEEN;
					attacker2 = WBISHOP;
				}
				else {
					attacker1 = BQUEEN;
					attacker2 = BBISHOP;					
				}				
			}
			else {
				if (piece % 2 == 0) {
					attacker1 = BQUEEN;
					attacker2 = BBISHOP;
				}
				else {
					attacker1 = WQUEEN;
					attacker2 = WBISHOP;					
				}
			}
			
			/**/
			//determinar dirección y sentido de avance
			int offset = 0;
			if (kingSquare > moveFrom) {
				if (XBoard.column(kingSquare) < XBoard.column(moveFrom)) {
					//+11
					offset = 11;
				}
				else {
					offset = 13;
				}
			}
			else {
				if (XBoard.column(kingSquare) < XBoard.column(moveFrom)) {
					offset = -11;
				}
				else {
					offset = -13;
				}				
			}
			/**/
			// 1 buscar el rey
			// primero recorrer hacia el sur desde la casilla inmediatamente inferior al origen
			// del move hasta encontrar obstáculo o el rey
			// si se encuentra obstáculo, salir, no hay jaque
			// si se encuentra al rey continuar con el siguiente recorrido
			for (int target = moveFrom + offset; target != kingSquare; target = target + offset) {
				if (board.pieceAt(target) != EMPTY){
					return 0;
				}						
			}			
			// 2 buscar el atacante
			// recorrer hacia el norte desde la casilla inmediatamente superior al origen 
			// del move hasta encontrar un obstáculo o una pieza atacante					
			for (int target = moveFrom - offset; board.pieceAt(target) != OUT; target = target - offset) {
				if (board.pieceAt(target) == attacker1 ) {
					return attacker1;
				}
				else if (board.pieceAt(target) == attacker2) {
					return attacker2;
				}
				else if (board.pieceAt(target) != EMPTY) { // pieza amiga
					return 0;
				}
			}
			//Hemos llegado al final sin encontrar atacantes, no hay jaque y salimos
			return 0;
		}
		else {
			// no check
			return 0;
		}


	}
	
	public boolean isKnightDoubleAttack(int[] fila, int from) {
		int pieceMoved = board.pieceAt(from);		
		int kingSquare = pieceMoved % 2 == 0? board.getWhiteKingSquare() : board.getBlackKingSquare();
		
		int majorVictims = 0;
		
		for (int sq : fila) {
			int victim = board.pieceAt(sq);
			if (Pieces.isKing(victim) || Pieces.isQueen(victim) || Pieces.isRook(victim)  ) {
				majorVictims++;
			}
    	}
		
		return majorVictims >= 2;
	}

	
	//kingSquare = piece % 2 == 0? board.getBlackKingSquare() : board.getWhiteKingSquare();
	
	public boolean checkSteps(int[] fila, int from) {
		int pieceMoved = board.pieceAt(from);		
		int kingSquare = pieceMoved % 2 == 0? board.getWhiteKingSquare() : board.getBlackKingSquare();
		
		for (int sq : fila) {						
			if (sq == kingSquare) return true;
    	}
		return false;
	}
	
    public boolean checkSlides(int[] fila, int from) {
		int pieceMoved = board.pieceAt(from);		
		int kingSquare = pieceMoved % 2 == 0? board.getWhiteKingSquare() : board.getBlackKingSquare();

    	boolean nullDirection = false;
    	for (int sq : fila) {
    		if (sq == -1) {
    			if (nullDirection) nullDirection = !nullDirection;  
    		}
    		else {
    			if (!nullDirection) {
    				if (sq == kingSquare) return true;
        			if (board.pieceAt(sq) != EMPTY) nullDirection = !nullDirection;
    			}
    		}
    	}    	    	
    	return false;
    }


	
	private void steps(int[] fila, int from, int[] moves) {
		int move = 0;
		for (int sq : fila) {			
			int piece = board.pieceAt(sq);
			if (piece == EMPTY) {   
				move = XMove.pack(from, sq, board.pieceAt(from), 0, 0);
				addMove(moves, move);
			}
			else if ( board.pieceAt(from)%2 != piece%2 )  {
				move = XMove.pack(from, sq, board.pieceAt(from), board.pieceAt(sq), 0 );
				addMove(moves, move);
			}
    	}    	    	
	}
	
    private void slides(int[] fila, int from, int[] moves) {
    	boolean nullDirection = false;
    	for (int sq : fila) {
    		if (sq == -1) {
    			if (nullDirection) nullDirection = !nullDirection;  
    		}
    		else {
    			if (!nullDirection) {
        			int piece = board.pieceAt(sq);
        			if (piece == EMPTY) {
        				int move = XMove.pack(from, sq, board.pieceAt(from), 0, 0);
        				addMove(moves, move);     				
        			}
        			else if ( board.pieceAt(from)%2 != piece%2 )  {
        				int move = XMove.pack(from, sq, board.pieceAt(from), board.pieceAt(sq), 0 );
        				addMove(moves, move);
        				nullDirection = !nullDirection;
        			}
        			else {
        				nullDirection = !nullDirection;
        			}    		    				
    			}
    		}
    	}    	    	
    }
	

}
