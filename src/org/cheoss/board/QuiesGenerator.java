package org.cheoss.board;

import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class QuiesGenerator implements Constants {	
	
	// Pseudo legal captures generator:
	// genMoves returns an array of capturing-moves whitout check if they
	// leave in check side-to-move king	
	
	private Board board;
	private int localIndex;
	
	public QuiesGenerator(Board board) {
		this.board = board;
	}
	
	public int genMoves(int[] moves) {
		localIndex = 0;
		if (board.isWhiteTurn()) {
			// Pawns moves
        	for (int i = 0; i < board.getWPawnSet().length; i++) {
        		int square = board.getWPawnSet()[i];
        		if (square > 0) {
					addPawnMoves(moves, square, WPAWN, -11, 
							7, -13, BPAWN, -12, 2, -24);	
        		}
        	}        	
        	for (int i = 0; i < board.getPiecesSet().length; i++) {        		
        		int square = board.getPiecesSet()[i];        		
        		int piece = board.pieceAt(square);
        		switch(piece){
                case WKNIGHT:
//                	steps(Pieces.KNIGHT_DIRECTIONS[square], square, moves);
            		for (int index = 0; index < knightOffsets.length; index++) {
            			int target = knightOffsets[index] + square;            			
            			if (board.isValidCaptureForPiece(WKNIGHT, target)) {
            	    		int move = XMove.pack(square, target, WKNIGHT, board.pieceAt(target), 0);
            	    		addMove(moves, move);
            			}            			
            		}
                    break;
                case WBISHOP:
//                	slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
            		for (int index = 0; index < bishopOffsets.length; index++) {
            			addSlides(moves, square, bishopOffsets[index]);
            		}
                    break;
                case WROOK:
//                	slides(Pieces.ROOK_DIRECTIONS[square], square, moves);
            		for (int index = 0; index < rookOffsets.length; index++) {
            			addSlides(moves, square, rookOffsets[index]);
            		}
                    break;
                case WQUEEN:
//					slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
//					slides(Pieces.ROOK_DIRECTIONS[square], square, moves);

            		for (int index = 0; index < bishopOffsets.length; index++) {
            			addSlides(moves, square, bishopOffsets[index]);
            			addSlides(moves, square, rookOffsets[index]);
            		}
                    break;

                case WKING:
                	for (int index = 0; index < kingOffsets.length; index++) {
                		int target = kingOffsets[index] + square;
                		if (board.isValidCaptureForPiece(WKING, target)) {
                			int move = XMove.pack(square, target, WKING, board.pieceAt(target), 0);
                			addMove(moves, move);
                		}
                	}
                	break;
        		}
        	}
        }//ends white moves
        else {// starts black moves
        	for (int i = 0; i < board.getBPawnSet().length; i++) {
        		int square = board.getBPawnSet()[i];
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
//                	steps(Pieces.KNIGHT_DIRECTIONS[square], square, moves);
            		for (int index = 0; index < knightOffsets.length; index++) {
            			int target = knightOffsets[index] + square;
            			if (board.isValidCaptureForPiece(BKNIGHT, target)) {
            	    		int move = XMove.pack(square, target, BKNIGHT, board.pieceAt(target), FLAG_NO_FLAG);
            	    		addMove(moves, move);
            	    	}
            		}
                    break;
                case BBISHOP:
//                	slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
            		for (int index = 0; index < bishopOffsets.length; index++) {
            			addSlides(moves, square, bishopOffsets[index]);
            		}
                    break;
                case BROOK:
//                	slides(Pieces.ROOK_DIRECTIONS[square], square, moves);
            		for (int index = 0; index < rookOffsets.length; index++) {
            			addSlides(moves, square, rookOffsets[index]);
            		}    
                    break;
                case BQUEEN:
//					slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
//					slides(Pieces.ROOK_DIRECTIONS[square], square, moves);

            		for (int index = 0; index < bishopOffsets.length; index++) {
            			addSlides(moves, square, bishopOffsets[index]);
            			addSlides(moves, square, rookOffsets[index]);
            		}
            		break;
                case BKING:
                	for (int index = 0; index < kingOffsets.length; index++) {
                		int target = kingOffsets[index] + square;
                		if (board.isValidCaptureForPiece(BKING, target)) {
                			int move = XMove.pack(square, target, BKING, board.pieceAt(target), FLAG_NO_FLAG);
                			addMove(moves, move);
                		}
                	}
                	break;
        		}
        	}
        }        
		return localIndex;
	}
	
	public int xgenMoves(int[] moves) {
		localIndex = 0;
		if (board.isWhiteTurn()) {
			// Pawns moves
        	for (int i = 0; i < board.getWPawnSet().length; i++) {
        		int square = board.getWPawnSet()[i];
        		if (square > 0) {
					addPawnMoves(moves, square, WPAWN, -11, 
							7, -13, BPAWN, -12, 2, -24);	
        		}
        	}        	
        	for (int i = 0; i < board.getPiecesSet().length; i++) {        		
        		int square = board.getPiecesSet()[i];        		
        		int piece = board.pieceAt(square);
        		switch(piece){
                case WKNIGHT:
                	steps(Pieces.KNIGHT_DIRECTIONS[square], square, moves);
//            		for (int index = 0; index < knightOffsets.length; index++) {
//            			int target = knightOffsets[index] + square;            			
//            			if (board.isValidCaptureForPiece(WKNIGHT, target)) {
//            	    		int move = XMove.pack(square, target, WKNIGHT, board.pieceAt(target), 0);
//            	    		addMove(moves, move);
//            			}            			
//            		}
                    break;
                case WBISHOP:
                	slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
//            		for (int index = 0; index < bishopOffsets.length; index++) {
//            			addSlides(moves, square, bishopOffsets[index]);
//            		}
                    break;
                case WROOK:
                	slides(Pieces.ROOK_DIRECTIONS[square], square, moves);
//            		for (int index = 0; index < rookOffsets.length; index++) {
//            			addSlides(moves, square, rookOffsets[index]);
//            		}
                    break;
                case WQUEEN:
					slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
					slides(Pieces.ROOK_DIRECTIONS[square], square, moves);

//            		for (int index = 0; index < bishopOffsets.length; index++) {
//            			addSlides(moves, square, bishopOffsets[index]);
//            			addSlides(moves, square, rookOffsets[index]);
//            		}
                    break;

                case WKING:
                	for (int index = 0; index < kingOffsets.length; index++) {
                		int target = kingOffsets[index] + square;
                		if (board.isValidCaptureForPiece(WKING, target)) {
                			int move = XMove.pack(square, target, WKING, board.pieceAt(target), 0);
                			addMove(moves, move);
                		}
                	}
                	break;
        		}
        	}
        }//ends white moves
        else {// starts black moves
        	for (int i = 0; i < board.getBPawnSet().length; i++) {
        		int square = board.getBPawnSet()[i];
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
                	steps(Pieces.KNIGHT_DIRECTIONS[square], square, moves);
//            		for (int index = 0; index < knightOffsets.length; index++) {
//            			int target = knightOffsets[index] + square;
//            			if (board.isValidCaptureForPiece(BKNIGHT, target)) {
//            	    		int move = XMove.pack(square, target, BKNIGHT, board.pieceAt(target), FLAG_NO_FLAG);
//            	    		addMove(moves, move);
//            	    	}
//            		}
                    break;
                case BBISHOP:
                	slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
//            		for (int index = 0; index < bishopOffsets.length; index++) {
//            			addSlides(moves, square, bishopOffsets[index]);
//            		}
                    break;
                case BROOK:
                	slides(Pieces.ROOK_DIRECTIONS[square], square, moves);
//            		for (int index = 0; index < rookOffsets.length; index++) {
//            			addSlides(moves, square, rookOffsets[index]);
//            		}    
                    break;
                case BQUEEN:
					slides(Pieces.BISHOP_DIRECTIONS[square], square, moves);
					slides(Pieces.ROOK_DIRECTIONS[square], square, moves);

//            		for (int index = 0; index < bishopOffsets.length; index++) {
//            			addSlides(moves, square, bishopOffsets[index]);
//            			addSlides(moves, square, rookOffsets[index]);
//            		}
            		break;
                case BKING:
                	for (int index = 0; index < kingOffsets.length; index++) {
                		int target = kingOffsets[index] + square;
                		if (board.isValidCaptureForPiece(BKING, target)) {
                			int move = XMove.pack(square, target, BKING, board.pieceAt(target), FLAG_NO_FLAG);
                			addMove(moves, move);
                		}
                	}
                	break;
        		}
        	}
        }        
		return localIndex;
	}

	
	
	///////////////// 
	// Slide-Moves: Bishops, Rooks and Queens
    private void addSlides(int[] moves, int source, int offset) {
    	int target = source + offset;    	
        if (board.pieceAt(target) == OUT) {
        	return;
        }
        
        do {
            if (board.pieceAt(target) == EMPTY) {
            	target += offset;
            }
            else if (board.isValidCaptureForPiece(board.pieceAt(source), target)) {        
                int move = XMove.pack(source, target, board.pieceAt(source), board.pieceAt(target), 0);
                addMove(moves, move);
                return;
            }
            else { // friend piece found
                return;
            }
        }while(board.pieceAt(target) != OUT);
	}

    protected int score(int move) {
    	if (XMove.flag(move) == FLAG_PROMO_QUEEN) {
    		return 115;	
    	}    		
    	
    	return Generator.victim_atacker[XMove.pieceCaptured(move)][XMove.pieceMoved(move)];    		    		
    }

	private void addMove(int[] moves, int move) {
		moves[localIndex++] = XMove.changeScore(move, score(move));
	}

    private void addPromotions(int[] moves, int from, int to, int pieceMoved, int pieceCaptured) {
		int move = XMove.pack(from, to, pieceMoved, pieceCaptured, FLAG_PROMO_QUEEN);
		addMove(moves, move);
    }

    
    ////////////////
    // Pawns
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

    }
    
    private void steps(int[] fila, int from, int[] moves) {
    	for (int sq : fila) {			
    		int piece = board.pieceAt(sq);    		
    		if ( piece != EMPTY && board.pieceAt(from)%2 != piece%2 )  {
    			int move = XMove.pack(from, sq, board.pieceAt(from), board.pieceAt(sq), 0 );
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
    				if ( piece != EMPTY) {
        				if ( board.pieceAt(from)%2 != piece%2 )  {
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

}
