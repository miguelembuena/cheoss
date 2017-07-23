package org.cheoss.board;

import java.util.*;

import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class Interpreter implements Constants {
	
	public static int algebraicToMove(String algebraicMove, Board board) {
		algebraicMove = algebraicMove.trim();
		if (algebraicMove.startsWith("0-0") ) {
			return castling(board, algebraicMove);
		}
		
		int from = 0;
		int to = 0;
		int pieceMoved = 0;
		int pieceCaptured = 0;
		int flag = 0;		
		StringTokenizer st;
		boolean captureFound = false;
		if (algebraicMove.lastIndexOf('x') != -1) {
			captureFound = true;
			st = new StringTokenizer(algebraicMove, "x");
		}
		else {
			st = new StringTokenizer(algebraicMove, "-");
		}		
		
		String origen = st.nextToken();		
		if (origen.length() == 3) origen = origen.substring(1);
		from = XBoard.squareForCoord(origen);
		
		pieceMoved = board.pieceAt(from);
		
		String destino = st.nextToken();
		if (destino.length() > 2) {
			if (destino.lastIndexOf('/') != -1) {
				flag = FLAG_EN_PASSANT_CAPTURE;
			}
			else if (destino.lastIndexOf('+') != -1) {
				//check
			}
			else if (destino.lastIndexOf('D') != -1) {
				// Queen promotion				
				flag = FLAG_PROMO_QUEEN;
			}
			else if (destino.lastIndexOf('T') != -1) {
				// Rook promotion				
				flag = FLAG_PROMO_ROOK;
			}
			else if (destino.lastIndexOf('A') != -1) {
				// Bishop promotion				
				flag = FLAG_PROMO_BISHOP;
			}
			else if (destino.lastIndexOf('C') != -1) {
				// Knight promotion				
				flag = FLAG_PROMO_KNIGHT;
			}

		}

		to = XBoard.squareForCoord( ""+destino.charAt(0)+destino.charAt(1) );
		if (captureFound || board.pieceAt(to) != EMPTY) {
			pieceCaptured = board.pieceAt(to);
		}
 
		if (XBoard.isPawn(pieceMoved)&& Math.abs(to - from) == 24 ) {
			flag = FLAG_PAWN_JUMP;
		}
		return XMove.pack(from, to, pieceMoved, pieceCaptured, flag);
	}
	
	
	private static int castling(Board board, String algebraicMove) {
		if (algebraicMove.equals("0-0")) {
			if (board.isWhiteTurn()) return WHITE_SHORT_CASTLING;
			else return BLACK_SHORT_CASTLING;
		}
		else {
			if (board.isWhiteTurn()) return WHITE_LONG_CASTLING;
			else return BLACK_LONG_CASTLING;
		}		
	}
	
	public static String moveToUci(int move) {
		int sqFrom = XMove.from(move);
		int sqTo = XMove.to(move);
		String uciStr = 
			XBoard.coordForSquare(sqFrom) + XBoard.coordForSquare(sqTo);  
		
		int flag = XMove.flag(move);
		if (flag >= FLAG_PROMO_QUEEN) {
			switch (flag) {
			case FLAG_PROMO_QUEEN:
				return uciStr + "q";
			case FLAG_PROMO_ROOK:
				return uciStr + "r";
			case FLAG_PROMO_BISHOP:
				return uciStr + "b";
			case FLAG_PROMO_KNIGHT:
				return uciStr + "n";
			}
		}		
		return  uciStr;			
	}
	
	public static int uciStrToMove(String str, Board board) {
		String strFrom = str.substring(0, 2);
		String strTo = str.substring(2, 4);		
		int from = XBoard.squareForCoord( strFrom );
		int to = XBoard.squareForCoord( strTo );
		
		
		ArrayList<Integer> legalMovesForPiece = board.genMovesFrom(board.pieceAt(from), from);
		for (int move : legalMovesForPiece) {
			if (XMove.to(move) == to) {
				if (str.length() == 5) {
					//promo
					char ch = str.charAt(4);
					int flag = 0;
					if (ch =='q' || ch == 'Q') {
						flag = FLAG_PROMO_QUEEN;
					}
					else if (ch == 'r' || ch == 'R') {
						flag = FLAG_PROMO_ROOK;
					}
					else if (ch == 'b' || ch == 'B') {
						flag = FLAG_PROMO_BISHOP;
					}
					else if (ch == 'n' | ch == 'N') {
						flag = FLAG_PROMO_KNIGHT;
					}
					
					if (flag == 0) {
						//maybe a check? (+)
						return move;												
					}
					else {
						if (XMove.flag(move)== flag) {
							return move;
						}
					}
				}
				else {
					return move;
				}
			}
		}
		
		return 0;
	}

	public static ArrayList<String> stringToListOfStringMoves(String algebraicSequence) {
		ArrayList<String> listOfStringMoves = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(algebraicSequence, ". ");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			
			if (token.length() > 3 || token.startsWith("0-") ) {
				if ( !token.startsWith("(") ) {
					listOfStringMoves.add(token);					
				}
			}
			
		}

		return listOfStringMoves;
	}
	
	
  	private static final String[] pieceName = {
  		"", "", "", // OUT & PAWNS
  		"N", "N", "B", "B", "R", "R",
  		"Q", "Q", "K", "K"
  	};

  	private static String pieceName(int move) throws Exception {
  		int piece = XMove.pieceMoved(move);
  		if (XBoard.isPawn(piece)) {
  			return XBoard.pgnColumn(XMove.from(move));
  		}
  		else return pieceName[piece];
  	}
	private static String desambiguate(int move, Board board) throws Exception {
		int piece = XMove.pieceMoved(move);
		if (!XBoard.isKnight(piece) && !XBoard.isRook(piece)) {
			return "";
		}
		ArrayList<Integer> ms = board.genMovesTo(piece, XMove.to(move));
		if (ms.size() > 1) {
			
			if ( XBoard.shareColumn(XMove.from(ms.get(0)), XMove.from(ms.get(1))) ) {
				return String.valueOf(XBoard.pgnRow(XMove.from(move)));
			}
			else {
				return XBoard.pgnColumn(XMove.from(move));
			}
		}
		else return "";
	}

	public static String moveToPGN(int move, Board board){
		try {			
			if (XMove.equals(move, WHITE_SHORT_CASTLING) || XMove.equals(move, BLACK_SHORT_CASTLING)) return "O-O";
			if (XMove.equals(move, WHITE_LONG_CASTLING) || XMove.equals(move, BLACK_LONG_CASTLING)) return "O-O-O";

		    StringBuffer sb = new StringBuffer();
		    sb.append(pieceName(move));
	        sb.append(desambiguate(move, board));
	        if (XMove.isCapture(move)) sb.append(captureToken(move));
	        sb.append(targetToken(move));
	        return sb.toString();
		}
		catch(Exception ex) {
			//ex.printStackTrace();
			return null;
		}
	}

  	private static String captureToken(int move) throws Exception {
  		if (XBoard.isPawn(XMove.pieceMoved(move))) {
  			return "x" +XBoard.pgnColumn(XMove.to(move));
  		}
  		else return "x";
  	}
  	
  	private static String targetToken(int move) throws Exception {
  		StringBuffer sb = new StringBuffer();
  		if (XBoard.isPawn(XMove.pieceMoved(move))) {
  	        sb.append(XBoard.pgnRow(XMove.to(move)));	     
  	        if (XMove.flag(move) >= FLAG_PROMO_QUEEN) {
  	        	switch (XMove.flag(move)) {
  	        	case FLAG_PROMO_QUEEN:
  	        		sb.append("=Q");
  	        		break;
  	        	case FLAG_PROMO_ROOK:
  	        		sb.append("=R");
  	        		break;
  	        	case FLAG_PROMO_BISHOP:
  	        		sb.append("=B");
  	        		break;
  	        	case FLAG_PROMO_KNIGHT:
  	        		sb.append("=N");
  	        		break;  	        		
  	        	}
  	        }  			
  		}
  		else {
	    	sb.append(XBoard.pgnColumn(XMove.to(move)));		
		    sb.append(XBoard.pgnRow(XMove.to(move)));  			
  		}

  		return sb.toString();
  	}


	public static int pgnToMove(String pgnMove, Board board){
		try {
			int candidate = matchInMoves(pgnMove, board);
			if ( candidate == 0) {
				candidate = coordsToMove(pgnMove, board);
			}
			
			if ( candidate != 0) {
				if ( isLegal(candidate, board)) return candidate;
				else return 0;
			}
			else return 0;
		}
		catch (Exception ex) {
			//ex.printStackTrace();
			return 0;
		}
	}
	
	private static int matchInMoves(String pgnMove, Board board) throws Exception {
		int[] moves = new int[MAX_MOVES_IN_STACK];
		board.genPseudoLegalMoves(moves,0,0,0);
		if (pgnMove == null || pgnMove.length() == 0) {
			return 0;
		}
		if (pgnMove.endsWith("+")) {
			pgnMove = pgnMove.substring(0, pgnMove.length()-1);
		}
		for (int move : moves) {
			if (move == 0) continue;
			if ( Interpreter.moveToPGN(move, board).equals(pgnMove) ) {
				return move;
			}

			if ( Interpreter.moveToPGN(move, board).equals(pgnMove) ) {
				return move;
			}
		}
		return 0;
	}
	
	private static boolean isLegal(int candidate, Board board) throws Exception {
		return 
			testLegalPhysics(candidate, board) && 
			testLegalForChecks(XMove.from(candidate), XMove.to(candidate), XMove.pieceMoved(candidate), board);		
	}
	
	private static boolean testLegalPhysics(int candidate, Board board) throws Exception {
		boolean isCapture = board.pieceAt(XMove.to(candidate)) != EMPTY &&
		board.pieceAt(XMove.to(candidate))%2 != XMove.pieceMoved(candidate)%2 &&
		XMove.pieceCaptured(candidate) == board.pieceAt(XMove.to(candidate));

		return ( board.pieceAt(XMove.from(candidate)) == XMove.pieceMoved(candidate) &&
				board.pieceAt(XMove.to(candidate)) != OUT &&
				( board.pieceAt(XMove.to(candidate)) == EMPTY || isCapture )  
		);		
	}

  	private static boolean testLegalForChecks(int from, int to, int pieceMoved, Board board) throws Exception {
  		ArrayList<Integer> allMoves = board.genMovesFrom(pieceMoved, from);
  		for (int validMove : allMoves) {
  			board.doMove(validMove);
  			boolean inCheck = board.isInCheck(!board.isWhiteTurn());
  			board.undoMove(validMove);
  			if (XMove.to(validMove) == to && !inCheck) return true;
  		}
  		return false;
  	}
  	
  	private static int coordsToMove(String coordMove, Board board) {
  		//f2e1q  		
  		String lastChar = coordMove.substring(coordMove.length()-1, coordMove.length());
  		if (lastChar.equalsIgnoreCase("q") || lastChar.equalsIgnoreCase("r") 
  				|| lastChar.equalsIgnoreCase("b") || lastChar.equalsIgnoreCase("n")) {
  			coordMove = coordMove.substring(0, coordMove.length()-1);
  		}
  		String fromStr = coordMove.trim().substring(0, 2);
  		String toStr = coordMove.trim().substring(coordMove.length()-2, coordMove.length());
		int from = XBoard.squareForCoord(fromStr);  		
		int to = XBoard.squareForCoord(toStr);
		int piece = board.pieceAt(from);
		
		
		for (int move : board.genMovesFrom(piece, from)) {
			if (XMove.from(move) == from && XMove.to(move) == to) {
				return move;	
			}
		}
		return 0;
  	}
  	
}