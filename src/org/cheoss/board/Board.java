package org.cheoss.board;

import java.util.*;
import org.cheoss.evaluation.*;
import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class Board implements Constants, IConstantsEval {
	
	private int enPassant = 0;
	private int fiftyMoves = 0;	
	private int csWhiteShort = 0;
	private int csBlackShort = 0;
	private int csWhiteLong = 0;
	private int csBlackLong = 0;
	
	private boolean whiteTurn = false;
	private int moveNumber = 0;	
	private Locator locator = new Locator();
	private int[] kings = {0, 0};
	private int materialWhite = 0;
	private int materialBlack = 0;
	private int[] board = new int[144];
	
	private IEvaluator evaluator = new AlterEvaluator(this);
	private IEvaluator pawnEvaluator = new AlterPawnEvaluator(this);
	
	private int[] history = new int[1024];
	
	private long key = 0L;	
	private long[] keyHistory = new long[1024];
	private int historyIndex = 0;	
	private Generator generator  = new Generator(this);
	private QuiesGenerator quiesGenerator = new QuiesGenerator(this);
	private LegalGenerator legalGenerator  = new LegalGenerator(this);
	
	private RepDetector repDetector = null;
	
	public long getKey() {
		return key;
	}
	
	
  	public Board(String FEN_position) {
  		setFEN(FEN_position);  		
  		this.key = Zobrist.calculateBoardKey(this);
  		repDetector = new RepDetector(this);
  		
  	}

  	public void doMove(int move) {
  		storeBoardState();
  		int offset =  Zobrist.enPassantCol(this);
  		if (offset != -1) {
  			key = key ^ Zobrist.random64[772 + offset];
  		}

  		// first, enPassant = 0; later in the code check the ep 
  		enPassant = 0; 
  		// it's increased at start, then if is an irreversible move it's setted to zero
  		fiftyMoves++; 

  		int piece = board[XMove.from(move)];		
  		movePiece(XMove.from(move), XMove.to(move), piece);

  		if (XMove.isCapture(move)) {
  			fiftyMoves = 0;
  			if (XBoard.isRook(XMove.pieceCaptured(move))) {
  				updateCastlingRightsAfterCapture(move);//
  			}
  		}

  		switch (piece) {
  		case WPAWN:
  			whitePawnMoved(move);
  			break;
  		case BPAWN:
  			blackPawnMoved(move);
  			break;
  		case WROOK: 
  			if (XMove.from(move) == h1) {
  				if (csWhiteShort == 1) {
  					key = key ^ Zobrist.random64[768 + 0];
  					csWhiteShort = 0;  					
  				}
  			}
  			else if (XMove.from(move) == a1) {
  				if (csWhiteLong == 1) {
  	  				key = key ^ Zobrist.random64[768 + 1];
  	  				csWhiteLong = 0;  					
  				}
  			}
  			break;
  		case BROOK:
  			if (XMove.from(move) == h8) {
  				if (csBlackShort == 1) {
  	  				key = key ^ Zobrist.random64[768 + 2];
  	  				csBlackShort = 0;  					
  				}
  			}
  			else if (XMove.from(move) == a8) {
  				if (csBlackLong == 1) {
  	  				key = key ^ Zobrist.random64[768 + 3];
  	  				csBlackLong = 0;  					
  				}
  			}
  			break;
  		case WKING:
  			whiteKingMoved(move);
  			break;
  		case BKING:
  			blackKingMoved(move);
  			break;
  		}
  		
  		locator.doMove(move);
  		updateMaterial(move);

		historyIndex++;
		moveNumber++;
		whiteTurn = !whiteTurn;
  		key = Zobrist.newKeyAfterMove(this, move);  		
  	}
  	
  	public void doNullMove() {
  		storeBoardState();
		int offset =  Zobrist.enPassantCol(this);
		if (offset != -1) {
			key = key ^ Zobrist.random64[772 + offset];
		}
		
  		enPassant = 0; 

  		
  		whiteTurn = !whiteTurn;
  		key = key ^ Zobrist.random64[780];
		historyIndex++;
		moveNumber++;
		if (enPassant != 0) {
			offset =  Zobrist.enPassantCol(this);
			if (offset != -1) {
				key = key ^ Zobrist.random64[772 + offset];
			}
		}
  	}
  	
  	public void undoNullMove() {
  		moveNumber--;
		historyIndex--; 
		restoreBoardState();
		whiteTurn = !whiteTurn;
  	}
  	
  	
  	private void updateMaterial(int move) {
  		if (XMove.isCapture(move)) {
  			if (XMove.isWhite(move)) {
					materialBlack -= valueOf[XMove.pieceCaptured(move)];
  			}
  			else {
  				materialWhite -= valueOf[XMove.pieceCaptured(move)];
  			}
  		}
  		if (XMove.flag(move) >= FLAG_PROMO_QUEEN) {
  			if (XMove.isWhite(move)) {
  				materialWhite -= valueOf[WPAWN];
  				switch (XMove.flag(move)) {
  				case FLAG_PROMO_QUEEN:
  	  				materialWhite += valueOf[WQUEEN];
  					break;
  				case FLAG_PROMO_ROOK:
  					materialWhite += valueOf[WROOK];
  					break;
  				case FLAG_PROMO_BISHOP:
  					materialWhite += valueOf[WBISHOP];
  					break;
  				case FLAG_PROMO_KNIGHT:
  					materialWhite += valueOf[WKNIGHT];
  					break;
  				}
  			}
  			else {  				
  				materialBlack -= valueOf[BPAWN];
  				switch (XMove.flag(move)) {
  				case FLAG_PROMO_QUEEN:
  	  				materialBlack += valueOf[BQUEEN];
  					break;
  				case FLAG_PROMO_ROOK:
  	  				materialBlack += valueOf[BROOK];
  					break;
  				case FLAG_PROMO_BISHOP:
  	  				materialBlack += valueOf[BBISHOP];
  					break;
  				case FLAG_PROMO_KNIGHT:
  	  				materialBlack += valueOf[BKNIGHT];
  					break;
  				}
  			}
  		}
  	}
  	
  	private void restoreMaterial(int move) {
  		if (XMove.isCapture(move)) {
  			if (XMove.isWhite(move)) {
  				materialBlack += valueOf[XMove.pieceCaptured(move)];
  			}
  			else {
  				materialWhite += valueOf[XMove.pieceCaptured(move)];
  			}
  		}
  		if (XMove.flag(move) >= FLAG_PROMO_QUEEN) {
  			if (XMove.isWhite(move)) {
  				materialWhite += valueOf[WPAWN];
  				switch (XMove.flag(move)) {
  				case FLAG_PROMO_QUEEN:
  	  				materialWhite -= valueOf[WQUEEN];
  					break;
  				case FLAG_PROMO_ROOK:
  					materialWhite -= valueOf[WROOK];
  					break;
  				case FLAG_PROMO_BISHOP:
  					materialWhite -= valueOf[WBISHOP];
  					break;
  				case FLAG_PROMO_KNIGHT:
  					materialWhite -= valueOf[WKNIGHT];
  					break;
  				}
  			}
  			else {  				
  				materialBlack += valueOf[BPAWN];
  				switch (XMove.flag(move)) {
  				case FLAG_PROMO_QUEEN:
  	  				materialBlack -= valueOf[BQUEEN];
  					break;
  				case FLAG_PROMO_ROOK:
  	  				materialBlack -= valueOf[BROOK];
  					break;
  				case FLAG_PROMO_BISHOP:
  	  				materialBlack -= valueOf[BBISHOP];
  					break;
  				case FLAG_PROMO_KNIGHT:
  	  				materialBlack -= valueOf[BKNIGHT];
  					break;
  				}
  			}
  		}
  	}

  	
  	private void storeBoardState() {
  		int boardState = XBoardState.pack(enPassant, fiftyMoves,
  				csWhiteShort, csWhiteLong, csBlackShort, csBlackLong);
		history[historyIndex] = boardState;
  	
		keyHistory[historyIndex] = key;
  	}
  	
  	public void doArenaMove(String strMove) {
  		doMove(Interpreter.algebraicToMove(strMove, this));
  	}
  	
  	public void doUciMove(String strMove) {
  		doMove(Interpreter.uciStrToMove(strMove, this));
  	}
  	

  	public void undoMove(int move) {
		moveNumber--;
		historyIndex--; 
		restoreBoardState();
		whiteTurn = !whiteTurn;
		restoreMaterial(move);
		int piece = XMove.pieceMoved(move);
		
		board[XMove.from(move)] = piece; // First part of undo move
		locator.undoMove(move);
  		switch (piece) {
  		case WPAWN:  		  			
  			undoMoveWhitePawn(move);
  			return;
  		case BPAWN:
  			undoMoveBlackPawn(move);
  			return;
  		case WKING:
  			undoMoveWhiteKing(move);
  			break;
  		case BKING:
  			undoMoveBlackKing(move);
  			break;
  		}

		if (XMove.isCapture(move)) {
				board[XMove.to(move)] = XMove.pieceCaptured(move);	 				
		}
		else board[XMove.to(move)] = EMPTY;		 
		
 	}
  	
  	private void restoreBoardState() {
  		this.key = keyHistory[historyIndex];
  		int packed = history[historyIndex];
		csBlackLong = XBoardState.getCsBlackLong(packed);		
		csBlackShort = XBoardState.getCsBlackShort(packed);
		csWhiteLong = XBoardState.getCsWhiteLong(packed);
		csWhiteShort = XBoardState.getCsWhiteShort(packed);			
		enPassant = XBoardState.getEnPassant(packed);
		fiftyMoves = XBoardState.getFiftyMoves(packed);		
  	}

  	
  	private void undoMoveWhitePawn(int move) {
  		if (XMove.flag(move) == FLAG_EN_PASSANT_CAPTURE) {
  			board[XMove.to(move)] = EMPTY;  			
 	    	board[XMove.to(move)+ 12] = BPAWN;	 	
  		}
  		else {
 			if (XMove.isCapture(move)) {
 				board[XMove.to(move)] = XMove.pieceCaptured(move);	 				
 			}
 			else {
 				board[XMove.to(move)] = EMPTY;	
 			} 			      			  			
  		}
  		restoreBoardState();
  	}
  	
  	private void undoMoveBlackPawn(int move) {
  		if (XMove.flag(move) == FLAG_EN_PASSANT_CAPTURE) {
  			board[XMove.to(move)] = EMPTY;  			
 	    	board[XMove.to(move)- 12] = WPAWN;
  		}
  		else {
 			if (XMove.isCapture(move)) {
 				board[XMove.to(move)] = XMove.pieceCaptured(move);	 				
 			}
 			else {
 				board[XMove.to(move)] = EMPTY;	
 			} 			      			  			
  		}
  		restoreBoardState();
  	}

  	private void undoMoveWhiteKing(int move) {
  		kings[0] = XMove.from(move);  		
  		
  		if (XMove.flag(move) == FLAG_CASTLING) {  
  			if (XMove.equals(move, WHITE_SHORT_CASTLING) ) movePiece(115, 117, WROOK);
  			else movePiece(113, 110, WROOK); 
  		}  		
  	}
  	  	
  	private void undoMoveBlackKing(int move) {
  		kings[1] = XMove.from(move);
  		
  		if (XMove.flag(move) == FLAG_CASTLING) {
  			if (XMove.equals(move, BLACK_SHORT_CASTLING)) movePiece(31, 33, BROOK);
  			else movePiece(29, 26, BROOK); 
  		}
  	}
  	
  	
  	private void blackKingMoved(int move) {
		kings[1] = XMove.to(move);

		if (csBlackShort == 1) {
			csBlackShort = 0;
			key = key ^ Zobrist.random64[768 + 2];			
		}
		
		if (csBlackLong == 1) {
			csBlackLong = 0;
			key = key ^ Zobrist.random64[768 + 3];			
		}


		if (XMove.flag(move) == FLAG_CASTLING) {
			if (XMove.equals(move, BLACK_SHORT_CASTLING)) {
				movePiece(33, 31, BROOK);
			}
			else {
				movePiece(26, 29, BROOK);
			}
		}
	}

  	private void whiteKingMoved(int move) {
  		kings[0] = XMove.to(move); 

  		if (csWhiteShort == 1) {
  			csWhiteShort = 0;
  			key = key ^ Zobrist.random64[768 + 0];  			
  		}
  		if (csWhiteLong == 1) {
  			csWhiteLong = 0;
  			key = key ^ Zobrist.random64[768 + 1];  			
  		}

		if (XMove.flag(move) == FLAG_CASTLING) {
			//fiftyMoves = 0; según arena el 0-0 no es mov irreversible!
			if (XMove.equals(move, WHITE_SHORT_CASTLING)) {
				movePiece(117, 115, WROOK);
			}
			else {
				movePiece(110, 113, WROOK);
			}
		}
	}

	private void movePiece(int oldSquare, int newSquare, int piece) {
		board[oldSquare] = EMPTY;
		board[newSquare] = piece;
	}
	
  	private void whitePawnMoved(int move) {
  		fiftyMoves = 0;
  		switch (XMove.flag(move)) {
  		case FLAG_PAWN_JUMP:
  			enPassant = XMove.from(move)-12;
  			break;
  		case FLAG_EN_PASSANT_CAPTURE:
  			board[XMove.to(move)+12] = EMPTY;      				
  			break;
  		case FLAG_PROMO_QUEEN:  			
  			board[XMove.to(move)] = WQUEEN;
  			break;
  		case FLAG_PROMO_ROOK:  			
  			board[XMove.to(move)] = WROOK;
  			break;      			
  		case FLAG_PROMO_BISHOP:  			
  			board[XMove.to(move)] = WBISHOP;
  			break;      			
  		case FLAG_PROMO_KNIGHT:  			
  			board[XMove.to(move)] = WKNIGHT;
  			break;      			
  			
  		}  	  		  		
  	}
  	
  	private void blackPawnMoved(int move) {
  		fiftyMoves = 0;
  		switch (XMove.flag(move)) {
  		case FLAG_PAWN_JUMP:  	
  			enPassant = XMove.from(move)+12;
  			break;
  		case FLAG_EN_PASSANT_CAPTURE:
  			board[XMove.to(move)-12] = EMPTY;      				
  			break;
  		case FLAG_PROMO_QUEEN:  			
  			board[XMove.to(move)] = BQUEEN;
  			break;
  		case FLAG_PROMO_ROOK:  			
  			board[XMove.to(move)] = BROOK;
  			break;      			
  		case FLAG_PROMO_BISHOP:  			
  			board[XMove.to(move)] = BBISHOP;
  			break;      			
  		case FLAG_PROMO_KNIGHT:  			
  			board[XMove.to(move)] = BKNIGHT;
  			break;      			
  		}  	  		  		
  	}

    public int pieceAt(int square) {
    	return board[square];
    }
    
	public boolean isWhiteTurn() {
		return whiteTurn;
	}
	
	public int getEnPassant() {
		return enPassant;
	}

	public Locator getLocator() {
		return locator;
	}
	
	private boolean noCastlingsRights() {
		return  csWhiteShort == 0 && 
			csWhiteLong == 0 && csBlackShort == 0 &&
			csBlackLong == 0;
	}
	
	private void updateCastlingRightsAfterCapture(int move) {
		if (noCastlingsRights()) return;
		
		if ( (csBlackShort == 1 || csBlackLong == 1) && XMove.isWhite(move)) {
  			if (XMove.to(move) == h8) {
  				key = key ^ Zobrist.random64[768 + 2];
  				csBlackShort = 0;
  			}
  			else if (XMove.to(move) == a8) {
  				key = key ^ Zobrist.random64[768 + 3];
  				csBlackLong = 0;
  			}					
		}
		else if ( (csWhiteShort == 1 || csWhiteLong == 1) && !XMove.isWhite(move)) {
  			if (XMove.to(move) == h1) {
  				key = key ^ Zobrist.random64[768 + 0];
  				csWhiteShort = 0;
  			}
  			else if (XMove.to(move) == a1) {
  				key = key ^ Zobrist.random64[768 + 1];
  				csWhiteLong = 0;
  			}										
		}
	}

	public int getMaterialWhite() {
		return materialWhite;
	}

	public int getMaterialBlack() {
		return materialBlack;
	}

	public int getCsWhiteShort() {
		return csWhiteShort;
	}


	public int getCsBlackShort() {
		return csBlackShort;
	}


	public int getCsWhiteLong() {
		return csWhiteLong;
	}


	public int getCsBlackLong() {
		return csBlackLong;
	}
	
	public int genPseudoLegalMoves(int[] moves, int firstKiller, int secondKiller, int ttMove) {
		return generator.genMoves( moves, firstKiller, secondKiller, ttMove);
	}

	public int genPseudoLegalQuiesMoves(int[] moves) {
		return quiesGenerator.genMoves(moves);
	}
	
	
	public int genLegalMoves(int[] moves, int firstKiller, int secondKiller, int ttMove) {
		return legalGenerator.genMoves(moves, firstKiller, secondKiller, ttMove);
	}


	public int eval() {
		int value = evaluator.eval();
		int pawnEval = pawnEvaluator.eval();
		return value + pawnEval;
	}
	
	public int materialEval() {
		int value = materialWhite - materialBlack;
		return isWhiteTurn()? value: -value;
	}


	/**
	 * 
	 * @param piece
	 * @param targetSquare
	 * @return true if not OUT and NOT empty and is an enemy
	 */
	public boolean isValidCaptureForPiece(int piece, int targetSquare) {
		int contentOfTarget = board[targetSquare]; 
		if ( contentOfTarget != OUT && contentOfTarget != EMPTY) {
			if ( (piece & 1) != (contentOfTarget & 1) ) { 
				return true;
			}
		}
		
		return false;
	}
	
	public int getBlackKingSquare() {
		return kings[1];
	}

	public int getWhiteKingSquare() {
		return kings[0];
	}

	public ArrayList<Integer> genMovesTo(int piece, int to) {
		return generator.genMovesTo(piece, to);
	}
	
	public ArrayList<Integer> genMovesFrom(int piece, int from) {
		return legalGenerator.genMovesFrom(piece, from);
	}

	
	/// FEN processing /////////////////////////////////////////////////////////////////	
	private void setFEN(String fen) {
		fen = fen.trim();		
		int[] firsts = {a8, a7, a6, a5, a4, a3, a2, a1};
		StringTokenizer st = new StringTokenizer(fen, "/");
		int row = 0;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			int firstSquare = firsts[row];
			if (row < 7) setFenRow(token, firstSquare);
			else processLastFenRow(token, firstSquare);
			row++;
		}
		countMaterial();
	}
	
	private void countMaterial() {
		for (int piece : board) {
			if (piece != OUT && piece != EMPTY) {
				if ( (piece & 1) != 0 ) materialWhite += valueOf[piece];
				else materialBlack += valueOf[piece];				
			}			
		}
	}
		
	private void setFenRow(String token, int firstSquare) {
		int emptys = 0;
		for (int col = 0; col  < token.length(); col++) {
			char ch = token.charAt(col);
			int square = firstSquare + col+ emptys;
			
			if (isNumber(ch)) {
				int emptySquares = Integer.parseInt(String.valueOf(ch));
				emptys = emptys + (emptySquares-1);
				for (int esq = 0; esq < emptySquares; esq++) {
					board[square+esq] = EMPTY;
				}	
			}
			else {
				int piece = fenSymbolToInternal(ch);
				board[square] = piece;
				
				if (piece == WKING) kings[0] = square;
				else if (piece == BKING) kings[1] = square;
				locator.addPiece(piece, square);
			}
		}
	}
	
	private void processLastFenRow(String token, int firstSquare) {
		StringTokenizer st = new StringTokenizer(token, " ");
		int field = 0;
		while (st.hasMoreTokens()) {
			String subtoken = st.nextToken();
			switch(field) {
			case 0: 
				setFenRow(subtoken, firstSquare);
				break;
			case 1:
				if (subtoken.charAt(0) == 'w') whiteTurn = true;
				break;
			case 2:
				for (int j = 0; j < subtoken.length(); j++) {
					char ch = subtoken.charAt(j);
					if (ch == 'K')csWhiteShort = 1;
					else if (ch == 'k') csBlackShort = 1;
					else if (ch == 'Q') csWhiteLong = 1;
					else if (ch == 'q') csBlackLong	= 1;				
				}
				break;
			case 3:
				if (subtoken.charAt(0) != '-') {
					enPassant = XBoard.squareForCoord( subtoken );
				}
				break;
			case 4: 
				fiftyMoves = Integer.parseInt(subtoken);
				break;
			case 5:
				int mn = Integer.parseInt(subtoken);
				int playTurn = whiteTurn?2:1;
				moveNumber = (mn * 2)- playTurn;
				break;
			default:
				break;
			}			
			field++;
		}
	}
	

	public String getFEN() {
		StringBuffer sb = new StringBuffer();
		for (int pgnRow = 8; pgnRow > 1; pgnRow--) {
			sb.append( getFenRow(pgnRow));
			sb.append('/');
		}
		
		//last row
		sb.append( getFenRow(1));
		
		// Turn
		String turn = whiteTurn?"w":"b";
		sb.append(" "+turn);
		
		// Castlings rights
		String cswk = csWhiteShort==1?"K":"";
		String csbk = csBlackShort==1?"k":"";
		//
		String cswq = csWhiteLong==1?"Q":"";
		String csbq = csBlackLong==1?"q":"";
		String cs = cswk+cswq+csbk+csbq;
		if (cs.length() == 0) cs = "-";
		sb.append(" "+cs);
		
		// en pasant
		String ep = "-";
		if (enPassant > 0) {
			ep = XBoard.coordForSquare(enPassant);
		}
		sb.append(" "+ep);
		
		// field 5:
		//Halfmove clock: This is the number of halfmoves since 
		//the last pawn advance or capture. This is used to determine 
		//if a draw can be claimed under the fifty-move rule.
		sb.append(" ");
		sb.append(fiftyMoves);
		
		// field 6:
		//Fullmove number: The number of the full move. It starts at 1, 
		//and is incremented after Black's move.
		int playTurn = whiteTurn? 2:1;
		int mn = (moveNumber + playTurn)/2;
		sb.append(" "+mn);
		return sb.toString();
	}

	private boolean isNumber(char ch) {
		if (ch >= '1' && ch <= '8') return true;
		return false; 
	}
	
	private final int fenSymbolToInternal(char ch) {
		switch (ch) {
		case 'p': return BPAWN;
		case 'r': return BROOK;			
		case 'n': return BKNIGHT;
		case 'b': return BBISHOP;
		case 'q': return BQUEEN;
		case 'k': return BKING;
		case 'P': return WPAWN;
		case 'R': return WROOK;			
		case 'N': return WKNIGHT;
		case 'B': return WBISHOP;
		case 'Q': return WQUEEN;
		case 'K': return WKING;
		}
		return 0;
	}
	private final char[] letras = {
		'0', 'P', 'p', 'N', 'n', 'B', 'b', 
		'R', 'r', 'Q', 'q', 'K', 'k'
	};
	
	private String getFenRow(int pgnRow) {
		int firstSquare = ( (10 - pgnRow)*12 )+2;
		int emptys = 0;
		StringBuffer sb = new StringBuffer();
		for ( int i = 0, sq = firstSquare; i < 8; i++, sq ++) {
			int piece = board[sq];
			if (piece == EMPTY) {
				emptys++;
				if (i==7)sb.append( String.valueOf(emptys) );
				continue;
			}
			if (emptys > 0) {				
				sb.append( String.valueOf(emptys) );
				emptys = 0;
			}
			sb.append( letras[piece] );
		}
		
		return sb.toString();
	}
	/// end FEN processing /////////////////////////////////////////////////////////////////

	
	
	
	/// Perft /////////////////////////////////////////////////////////////////
	private int perftNodes = 0;
	private int perftEps = 0;
	private int perftCaptures = 0;
	private int perftChecks = 0;
	private int perftCastlings = 0;
	private int perftPromos = 0;
	private PerftResults perftResults;

	public PerftResults perft(int depth) {
		if (depth <= 0) {
			return perftResults;
		}
		
		int moves[] = new int[MAX_MOVES_IN_STACK];		
		int numMoves = genPseudoLegalMoves(moves,0,0,0);
		for (int i=0; i<numMoves; i++) {						
			int move = moves[i];
			doMove(move);			
			// If move is illegal, returns board to previous state and break
			if (isInCheck(!isWhiteTurn())){
				undoMove(move);
				continue;
			}
			
			if (depth == 1) {
				if (isInCheck(isWhiteTurn())) {
					perftChecks++;
				}
				if ( XMove.isCastling(move)) {					
					perftCastlings++;
				}
				if (XMove.flag(move) >= FLAG_PROMO_QUEEN) {
					perftPromos++;
				}
				
				if ( XMove.isCapture(move) ) {
					perftCaptures++;
					if ( XMove.flag(move) == FLAG_EN_PASSANT_CAPTURE) {
						perftEps++;
					}
				}
				perftNodes++;
				
				perftResults = new PerftResults(perftNodes, perftEps, perftCaptures, 
						perftChecks, perftCastlings, perftPromos);

			}
			perft(depth - 1);
			undoMove(move);
		}
		return perftResults;
	}
	
	public long xperft(int depth) {
		if (depth == 0) return 1;
		
		int moves[] = new int[MAX_MOVES_IN_STACK];
		long xnodes = 0;
		int numMoves = genPseudoLegalMoves(moves, 0, 0, 0);
		for (int i=0; i<numMoves; i++) {
			int move = moves[i];
			doMove(move);			
			if (isInCheck(!isWhiteTurn())){
				undoMove(move);
				continue;
			}
			xnodes += xperft(depth - 1);
			undoMove(move);
		}

		return xnodes;
	}
	
	public void speedPerft(int depth) {
		if (depth <= 0) {
			return;
		}
		
		int moves[] = new int[MAX_MOVES_IN_STACK];		
		int numMoves = genPseudoLegalMoves(moves,0,0,0);
		for (int i=0; i<numMoves; i++) {						
			int move = moves[i];
			doMove(move);			
			if (isInCheck(!isWhiteTurn())){
				undoMove(move);
				continue;
			}
			
			if (depth == 1) {
				perftNodes++;
			}
			perft(depth - 1);
			undoMove(move);
		}
		return;
	}

	/// End Perft /////////////////////////////////////////////////////////////////	
	
  	public int[] getPiecesSet() {
  		return locator.getPiecesSet();
  	}
	
  	public int[] getWPawnSet() {
  		return locator.getWPawnSet();
  	}
  	public int[] getBPawnSet() {
  		return locator.getBPawnSet();
  	}
  	
  	
  	public boolean isAttacked(int square, boolean whiteAttack) {
  		int bishopTarget;
  		int rookTarget;
  		int knightTarget;	  
  		if (whiteAttack) {
  			for (int i = 0; i < 4; i++) {
  				bishopTarget = square + bishopOffsets[i];
  				rookTarget = square + rookOffsets[i];
  				knightTarget = square + knightOffsets[i];			  
  				if (board[bishopTarget] == WPAWN && i%2 == 0) return true; //ojo aqui
  				if (board[knightTarget] == WKNIGHT) return true;
  				if (board[bishopTarget] == WKING) return true;
  				if (board[rookTarget] == WKING) return true;
  				while (board[bishopTarget] != OUT) {
  					if (board[bishopTarget] == WBISHOP || board[bishopTarget] == WQUEEN) return true;
  					if (board[bishopTarget] != EMPTY) break;
  					bishopTarget += bishopOffsets[i];
  				}
  				while (board[rookTarget] != OUT) {
  					if (board[rookTarget] == WROOK || board[rookTarget] == WQUEEN) return true;
  					if (board[rookTarget] != EMPTY) break;
  					rookTarget += rookOffsets [i];
  				}
  			}
  			//knight-style moves resto 
  			for (int i = 4; i < 8; i++) {
  				knightTarget = square + knightOffsets[i];
  				if (board[knightTarget] == WKNIGHT) return true;
  			}
  			return false;
  		} // fin ataque blanco
  		else {
  			for (int i = 0; i < 4; i++) {
  				bishopTarget = square + bishopOffsets[i];
  				rookTarget = square + rookOffsets[i];
  				knightTarget = square + knightOffsets[i];
  				if (board[bishopTarget] == BPAWN && i%2 != 0) return true; //ojo aqui
  				if (board[knightTarget] == BKNIGHT) return true;
  				if (board[bishopTarget] == BKING) return true;
  				if (board[rookTarget] == BKING) return true; 
  				while (board[bishopTarget] != OUT) {
  					if (board[bishopTarget] == BBISHOP || board[bishopTarget] == BQUEEN) return true;
  					if (board[bishopTarget] != EMPTY) break;
  					bishopTarget += bishopOffsets[i];
  				}
  				while (board[rookTarget] != OUT) {
  					if (board[rookTarget] == BROOK || board[rookTarget] == BQUEEN) return true;
  					if (board[rookTarget] != EMPTY) break;
  					rookTarget += rookOffsets[i];
  				}
  			}
  			//knight-style moves resto:
  			for (int i = 4; i < 8; i++) {
  				knightTarget = square + knightOffsets[i];
  				if (board[knightTarget] == BKNIGHT) return true;
  			}
  			return false;
  		}// fin ataque negro
  	}


  	public boolean isPossibleWhiteShortCastling(){    	
  		if (pieceAt(115) == EMPTY && pieceAt(116) == EMPTY && pieceAt(117) == WROOK) {
  			if (!isInCheck(WHITE)){
  				if (!isAttacked(115, BLACK) && !isAttacked(116, BLACK)) {
  					return true;	
  				}   			
  			}            
  		}
  		return false;
  	}

  	public boolean isPossibleWhiteLongCastling(){
  		if (pieceAt(113) == EMPTY && pieceAt(112) == EMPTY 
  				&& pieceAt(111) == EMPTY && pieceAt(110) == WROOK) {
  			if (!isInCheck(WHITE)){
  				if (!isAttacked(113, BLACK) && !isAttacked(112, BLACK) ) {
  					return true;	
  				}   			
  			}            

  		}                	
  		return false;
  	}

  	public boolean isPossibleBlackShortCastling(){
  		if (pieceAt(31) == EMPTY && pieceAt(32) == EMPTY && pieceAt(33) == BROOK) {            
  			if (!isInCheck(BLACK)){
  				if (!isAttacked(31, WHITE) && !isAttacked(32, WHITE)) {
  					return true;	
  				}   			
  			}            
  		}                	
  		return false;
  	}

  	public boolean isPossibleBlackLongCastling(){
  		if (pieceAt(29) == EMPTY && pieceAt(28) == EMPTY 
  				&& pieceAt(27) == EMPTY && pieceAt(26) == BROOK) {
  			if (!isInCheck(BLACK)){
  				if (!isAttacked(29, WHITE) && !isAttacked(28, WHITE) ) {
  					return true;	
  				}   			
  			}            
  		}                        	
  		return false;
  	}    



  	public boolean isInCheck(boolean whiteKing) {
  		if (whiteKing) {    
  			return isAttacked(getWhiteKingSquare(), BLACK);
  		}
  		else {
  			return isAttacked(getBlackKingSquare(), WHITE);
  		}

  	}

	public int getPerftNodes() {
		return perftNodes;
	}
	
	
  	public int countAttackers(int square, boolean whiteAttack) {
  		int bishopTarget;
  		int rookTarget;
  		int knightTarget;
  		int attackers = 0;
  		if (whiteAttack) {
  			for (int i = 0; i < 4; i++) {
  				bishopTarget = square + bishopOffsets[i];
  				rookTarget = square + rookOffsets[i];
  				knightTarget = square + knightOffsets[i];			  
  				if (board[bishopTarget] == WPAWN && i%2 == 0) {
  					attackers++;
  				}
  				if (board[knightTarget] == WKNIGHT) {
  					attackers++;
  				}
  				if (board[bishopTarget] == WKING) {
  					attackers++;
  				}
  				if (board[rookTarget] == WKING) {
  					attackers++;
  				}
  				while (board[bishopTarget] != OUT) {
  					if (board[bishopTarget] == WBISHOP || board[bishopTarget] == WQUEEN){
  						attackers++;
  					}
  					if (XBoard.isPawn(board[bishopTarget])) {
  					//if (board[bishopTarget] != EMPTY) {
  						break;
  					}
  					bishopTarget += bishopOffsets[i];
  				}
  				while (board[rookTarget] != OUT) {
  					if (board[rookTarget] == WROOK || board[rookTarget] == WQUEEN) {
  						attackers++;
  					}
  					if (XBoard.isPawn(board[rookTarget])) {
  					//if (board[rookTarget] != EMPTY) {
  						break;
  					}
  					rookTarget += rookOffsets [i];
  				}
  			}
  			//knight-style moves resto 
  			for (int i = 4; i < 8; i++) {
  				knightTarget = square + knightOffsets[i];
  				if (board[knightTarget] == WKNIGHT) {
  					attackers++;
  				}
  			}
  			return attackers;
  		} // fin ataque blanco
  		else {
  			for (int i = 0; i < 4; i++) {
  				bishopTarget = square + bishopOffsets[i];
  				rookTarget = square + rookOffsets[i];
  				knightTarget = square + knightOffsets[i];
  				if (board[bishopTarget] == BPAWN && i%2 != 0) {
  					attackers++;
  				}
  				if (board[knightTarget] == BKNIGHT) {
  					attackers++;
  				}
  				if (board[bishopTarget] == BKING) {
  					attackers++;
  				}
  				if (board[rookTarget] == BKING) {
  					attackers++; 
  				}
  				while (board[bishopTarget] != OUT) {
  					if (board[bishopTarget] == BBISHOP || board[bishopTarget] == BQUEEN) {
  						attackers++;
  					}
  					if (XBoard.isPawn(board[bishopTarget])) {
  					//if (board[bishopTarget] != EMPTY) {
  						break;
  					}
  					bishopTarget += bishopOffsets[i];
  				}
  				while (board[rookTarget] != OUT) {
  					if (board[rookTarget] == BROOK || board[rookTarget] == BQUEEN) {
  						attackers++;
  					}
  					if (XBoard.isPawn(board[rookTarget])) {
  					//if (board[rookTarget] != EMPTY) {
  						break;
  					}
  					rookTarget += rookOffsets[i];
  				}
  			}
  			//knight-style moves resto:
  			for (int i = 4; i < 8; i++) {
  				knightTarget = square + knightOffsets[i];
  				if (board[knightTarget] == BKNIGHT) {
  					attackers++;
  				}
  			}
  			return attackers;
  		}// fin ataque negro
  	}
  	
  	public ArrayList<Integer> collectAttackers(int square, boolean whiteAttack) {
  		ArrayList<Integer> attackers = new ArrayList<Integer>();
  		int bishopTarget;
  		int rookTarget;
  		int knightTarget;
  		//int attackers = 0;
  		if (whiteAttack) {
  			for (int i = 0; i < 4; i++) {
  				bishopTarget = square + bishopOffsets[i];
  				rookTarget = square + rookOffsets[i];
  				knightTarget = square + knightOffsets[i];			  
  				if (board[bishopTarget] == WPAWN && i%2 == 0) {
  					//attackers++;
  					attackers.add(WPAWN);
  				}
  				if (board[knightTarget] == WKNIGHT) {
  					//attackers++;
  					attackers.add(WKNIGHT);
  				}
  				if (board[bishopTarget] == WKING) {
  					//attackers++;
  					attackers.add(WKING);
  				}
  				if (board[rookTarget] == WKING) {
  					attackers.add(WKING);
  					//attackers++;
  				}
  				while (board[bishopTarget] != OUT) {
  					if (board[bishopTarget] == WBISHOP){
  						//attackers++;
  						attackers.add(WBISHOP);
  					}
  					if (board[bishopTarget] == WQUEEN){
  						//attackers++;
  						attackers.add(WQUEEN);
  					}

  					if (XBoard.isPawn(board[bishopTarget])) {
  					//if (board[bishopTarget] != EMPTY) {
  						break;
  					}
  					bishopTarget += bishopOffsets[i];
  				}
  				while (board[rookTarget] != OUT) {
  					if (board[rookTarget] == WROOK) {
  						attackers.add(WROOK);
  					}
  					if (board[rookTarget] == WQUEEN) {
  						attackers.add(WQUEEN);
  					}
  					if (XBoard.isPawn(board[rookTarget])) {
  					//if (board[rookTarget] != EMPTY) {
  						break;
  					}
  					rookTarget += rookOffsets [i];
  				}
  			}
  			//knight-style moves resto 
  			for (int i = 4; i < 8; i++) {
  				knightTarget = square + knightOffsets[i];
  				if (board[knightTarget] == WKNIGHT) {
  					//attackers++;
  					attackers.add(WKNIGHT);
  				}
  			}
  			
  			return attackers;  			
  		} // fin ataque blanco
  		else {
  			for (int i = 0; i < 4; i++) {
  				bishopTarget = square + bishopOffsets[i];
  				rookTarget = square + rookOffsets[i];
  				knightTarget = square + knightOffsets[i];
  				if (board[bishopTarget] == BPAWN && i%2 != 0) {
  					//attackers++;
  				}
  				if (board[knightTarget] == BKNIGHT) {
  					//attackers++;
  				}
  				if (board[bishopTarget] == BKING) {
  					//attackers++;
  				}
  				if (board[rookTarget] == BKING) {
  					//attackers++; 
  				}
  				while (board[bishopTarget] != OUT) {
  					if (board[bishopTarget] == BBISHOP || board[bishopTarget] == BQUEEN) {
  						//attackers++;
  					}
  					if (XBoard.isPawn(board[bishopTarget])) {
  					//if (board[bishopTarget] != EMPTY) {
  						break;
  					}
  					bishopTarget += bishopOffsets[i];
  				}
  				while (board[rookTarget] != OUT) {
  					if (board[rookTarget] == BROOK || board[rookTarget] == BQUEEN) {
  						//attackers++;
  					}
  					if (XBoard.isPawn(board[rookTarget])) {
  					//if (board[rookTarget] != EMPTY) {
  						break;
  					}
  					rookTarget += rookOffsets[i];
  				}
  			}
  			//knight-style moves resto:
  			for (int i = 4; i < 8; i++) {
  				knightTarget = square + knightOffsets[i];
  				if (board[knightTarget] == BKNIGHT) {
  					//attackers++;
  				}
  			}
  			//return attackers;
  			return null;
  		}// fin ataque negro
  	}

  	
  	public boolean isLegal(int candidate) {
  		if (candidate == 0) return false;
  		if (XMove.isWhite(candidate) != whiteTurn) return false;
  		
  		ArrayList<Integer> tomoves = generator.genMovesTo(XMove.pieceMoved(candidate), XMove.to(candidate));
  		for (Integer mov : tomoves) {
  			if (XMove.equals(mov, candidate)) return true; 
  		}
  		return false;
  	}
	
	
	public boolean isPawnPromoting() {
		if (whiteTurn) {
			return ( 
					pieceAt(a7) == WPAWN || pieceAt(b7) == WPAWN ||
					pieceAt(c7) == WPAWN || pieceAt(d7) == WPAWN ||
					pieceAt(e7) == WPAWN || pieceAt(f7) == WPAWN ||
					pieceAt(g7) == WPAWN || pieceAt(h7) == WPAWN 
					);
		}
		else {
			return ( 
					pieceAt(a2) == BPAWN || pieceAt(b2) == BPAWN ||
					pieceAt(c2) == BPAWN || pieceAt(d2) == BPAWN ||
					pieceAt(e2) == BPAWN || pieceAt(f2) == BPAWN ||
					pieceAt(g2) == BPAWN || pieceAt(h2) == BPAWN 
					);
		}
	}
	
	public boolean isProtectedByPawn(int sq, boolean whiteProtector) {
		if (whiteProtector) {
			//+11 y +13
			if (pieceAt(sq + 11) == WPAWN) return true; 
			if (pieceAt(sq + 13) == WPAWN) return true;
		}
		else {
			//-11 y -13
			if (pieceAt(sq - 11) == BPAWN) return true; 
			if (pieceAt(sq - 13) == BPAWN) return true;
		}
		return false;
	}
	
	public boolean sideToPlayHasAtLeastOnePiece() {
		if (whiteTurn) {
			for (int sq : locator.getPiecesSet()) {
				if ( 
						board[sq] == WROOK ||
						board[sq] == WKNIGHT || 
						board[sq] == WBISHOP ||
						board[sq] == WQUEEN ) {
					return true;
				}
			}
			return false;
		}
		else {

			for (int sq : locator.getPiecesSet()) {
				if ( 
						board[sq] == BROOK ||
						board[sq] == BKNIGHT ||						
						board[sq] == BBISHOP ||
						board[sq] == BQUEEN ) {
					return true;
				}
			}
			return false;
		}
	}




	public int getMoveNumber() {
		return moveNumber;
	}

	public void registerRealGameKey(int move) {
		repDetector.registerRealGameKey(move);
	}
	
	public boolean checkRepes() {
		return repDetector.checkRepes();
	}
	
	public int getMoveFromMovelist(int movenumber) {
		return repDetector.getMoveFromMovelist(movenumber);
	}


	public RepDetector getRepDetector() {
		return repDetector;
	}
	
	
	
	public boolean isEndGame() {
		int material = getMaterialBlack() + getMaterialWhite();
		if (material < END_GAME_LIMIT) {
			return true;
		}
		else return false;

	}


	public Generator getGenerator() {
		return generator;
	}
	
	// returns square of pieces attacking square
  	public ArrayList<Integer> comedoresEn(int square, boolean whiteAttack) {
  		ArrayList<Integer> attackers = new ArrayList<Integer>();
  		int bishopTarget;
  		int rookTarget;
  		int knightTarget;	 
  		boolean lookBishops = true;
  		boolean lookRooks = true;
  		if (whiteAttack) {
  			for (int i = 0; i < 4; i++) {
  					//System.out.println("bucle for i = "+i);
  				bishopTarget = square + bishopOffsets[i];
  				rookTarget = square + rookOffsets[i];
  				//knightTarget = square + knightOffsets[i];			  
  				if (board[bishopTarget] == WPAWN && i%2 == 0) {
  					//System.out.println("pawntarget = "+bishopTarget);
  					attackers.add(bishopTarget);
  					lookBishops = false;
  				}
  				if (board[bishopTarget] == WKING) {
  					attackers.add(bishopTarget);
  					lookBishops = false;
  				}
  				if (board[rookTarget] == WKING) {
  					attackers.add(rookTarget);
  					lookRooks = false;
  				}
  				if (lookBishops) {
  	  				while (board[bishopTarget] != OUT) {
  	  					//System.out.println("bishoptarget = "+bishopTarget+" i = "+i);
  	  					if (board[bishopTarget] == WBISHOP || board[bishopTarget] == WQUEEN) {
  	  						//System.out.println("bishop detected!"+" i = "+i);
  	  						attackers.add(bishopTarget);
  	  	  					break;
  	  					}
  	  					if (board[bishopTarget] != EMPTY) break;
  	  					bishopTarget += bishopOffsets[i];
  	  				}  					
  				}
  				
  				if (lookRooks) {
  	  				while (board[rookTarget] != OUT) {
  	  					//System.out.println("rooktarget = "+rookTarget+" i = "+i);
  	  					if (board[rookTarget] == WROOK || board[rookTarget] == WQUEEN) {
  	  						//System.out.println("rook detected!");
  	  						attackers.add(rookTarget);
  	  	  					break;
  	  					}
  	  					if (board[rookTarget] != EMPTY) break;
  	  					rookTarget += rookOffsets [i];
  	  				}  					
  				}
  				
  				lookBishops = true;
  				lookRooks = true;
  			}
  			//knight-style moves resto 
  			for (int i = 0; i < 8; i++) {
  				knightTarget = square + knightOffsets[i];
  				//System.out.println("knightTarget = "+knightTarget);
  				if (board[knightTarget] == WKNIGHT) {
  					//System.out.println("knight detected!!!!!!!!!!!!!!!!! puesto valor "+knightTarget);
  					attackers.add(knightTarget);
  				}
  			}
  			
  			//Collections.sort(attackers); ordena las casillas, no los atacantes 
  			return attackers;
  		} // fin ataque blanco
  		else {
  			for (int i = 0; i < 4; i++) {
  				bishopTarget = square + bishopOffsets[i];
  				rookTarget = square + rookOffsets[i];
  				//knightTarget = square + knightOffsets[i];
  				if (board[bishopTarget] == BPAWN && i%2 != 0) {
  					attackers.add(bishopTarget);
  					lookBishops = false;
  				}
  				if (board[bishopTarget] == BKING) {
  					attackers.add(bishopTarget);
  					lookBishops = false;
  				}
  				if (board[rookTarget] == BKING) {
  					attackers.add(rookTarget);
  					lookRooks = false;
  				}
  				if (lookBishops) {
  	  				while (board[bishopTarget] != OUT) {
  	  					if (board[bishopTarget] == BBISHOP || board[bishopTarget] == BQUEEN) {
  	  						attackers.add(bishopTarget);
  	  	  					break;
  	  					}
  	  					if (board[bishopTarget] != EMPTY) break;
  	  					bishopTarget += bishopOffsets[i];
  	  				}  					
  				}
  				
  				if (lookRooks) {
  	  				while (board[rookTarget] != OUT) {
  	  					if (board[rookTarget] == BROOK || board[rookTarget] == BQUEEN) {
  	  						attackers.add(rookTarget);
  	  	  					break;
  	  					}
  	  					if (board[rookTarget] != EMPTY) break;
  	  					rookTarget += rookOffsets[i];
  	  				}  					
  				}
  				
  				lookBishops = true;
  				lookRooks = true;
  			}
  			//knight-style moves resto:
  			for (int i = 0; i < 8; i++) {
  				knightTarget = square + knightOffsets[i];
  				if (board[knightTarget] == BKNIGHT) {
  					attackers.add(knightTarget);
  				}
  			}
  			return attackers;
  		}// fin ataque negro
  	}


	public LegalGenerator getLegalGenerator() {
		return legalGenerator;
	}


	public QuiesGenerator getQuiesGenerator() {
		return quiesGenerator;
	}


	public IEvaluator getEvaluator() {
		return evaluator;
	}


	public IEvaluator getPawnEvaluator() {
		return pawnEvaluator;
	}

	
}
