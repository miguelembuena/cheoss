package org.cheoss.search;

import java.util.*;

import org.cheoss.board.*;
import org.cheoss.controller.*;
import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class Tree implements Constants {
	public int ttMatches = 0;
	public int misses = 0;
	
	public boolean useIID = true;
	public boolean useNullMove = true;
	public boolean useCheckExtension = true;
	public boolean probeTT = true;
	public boolean useKillers = true;
	public boolean useDeltaPruning = true; // sin delta 251
	//public int DELTA_MARGIN = 111; //255
	//public int DELTA_MARGIN = 80; //record 256
	public int DELTA_MARGIN = 125;
	//public int DELTA_MARGIN = 0; //254
	public boolean useBadCapture = true;
	public boolean useLMR = true;
	
	//int fmargin[] = {0, 200, 300, 450, 600}; //jugandose esto
//	int fmargin[] = {0, 270, 310, 470, 600};
//	int fmargin[] = {0, 400, 550, 600, 600, 750, 900, 950, 950};
//	public boolean useHorizonPrune = true;
//	public boolean useDepthLimitQuies = false;
//	public int depthLimitQuies = -3;
	/////
	private Board board;
	private int rootDepth = 0;
	private long timeForMove = 0l;
	private int nodes = 0;
	private Clock clock;	
	private boolean forced = false;
	private int finalResult = 0;
	private TTable ttable = null;
	private int value = 0;
	private int bestMoveFound = 0;
	private TreeInformer treeInformer = null;
	private Killers killers = null;
	//ojo cambiados los return 0 por board.eval, probar en torneo v 06
	private boolean stopSearch = false;
	//private SEE see;

	public Tree (Board board, Clock clock) {
		this.board = board;
		this.clock = clock;
		this.ttable = new TTable();
	}
	
	public Tree (Board board) {
		this.board = board;
		this.clock = new Clock();
		this.ttable = new TTable();
		clock.setAbsoluteTime(8000000l);
		timeForMove = 3000000l;
		clock.start();
	}
	
	public int searchForMove(long timeForSearch, ArrayList<Integer> winMove) {
 
		killers = new Killers();
		bestMoveFound = 0;
		ttMatches = 0;
		misses = 0;
		
		timeForMove = timeForSearch;
    	for (int depth = 1; depth < 64; depth++) {

    		if ( noTimeRemaining() ) {    			    			
    			break;
    		}    		    		
    		
    		for (int wmove : winMove) {
//    			System.out.println("wmove = "+wmove);
//    			System.out.println("comparando bestMoveFound = "+
//    					Interpreter.moveToUci(bestMoveFound)+" con "+Interpreter.moveToUci(wmove));
    			if (XMove.equals(bestMoveFound, wmove) ) {
    				return bestMoveFound;
    			}
    		}
    		
			alphaBetaRoot(depth, -INFINITY, INFINITY);
			
			if (Math.abs( value ) > 80000)  break; // mate found
			
			
			if ( getFinalResult() != 0) {
				break;
			}			
    	}    	
    	
		return bestMoveFound;
	}

	 
	public int searchToDepth(int toDepth) {
		//treeInformer = new TreeInformer();
		int previousNodes = 0; 
		killers = new Killers();
		bestMoveFound = 0;
		ttMatches = 0;
		misses = 0;
    	for (int depth = 1; depth <= toDepth; depth++) {    		
			alphaBetaRoot(depth, -INFINITY, INFINITY);
			int EFB = previousNodes > 0? nodes/previousNodes : 0; 
			System.out.println("   ===> depth = "+depth+" nodes = "+nodes+" EFB = "+EFB);
			previousNodes = nodes;
			
			if (Math.abs( value ) > 80000)  break; // mate found
			
			if ( isForced()) {
				break;
			}
			
			if ( getFinalResult() != 0) {
				break;
			}			
    	}    	
    	
		return bestMoveFound;
	}

	private boolean noTimeRemaining() {
		return clock.partial() >= timeForMove;
	}
	
	
	public int search(long time) { 
		killers = new Killers();
		timeForMove = time;
		bestMoveFound = 0;
		stopSearch = false;
		
    	for (int depth = 1; depth < 64; depth++) {
    		if ( noTimeRemaining() && depth > 2 && bestMoveFound != 0 ) {
    			break;
    		}    		    		
    		
			alphaBetaRoot(depth, -INFINITY, INFINITY); // sin AW
			
			if (Math.abs( value ) > 80000)  break; // mate found
			
			if ( isForced()) {
				break;
			}
			
			if ( getFinalResult() != 0) {
				break;
			}			
			
    	}    	
    	
		return bestMoveFound;
	}
	
	private boolean mustStopSearch(int depth) {
		if (noTimeRemaining()&& depth > 2 && bestMoveFound != 0 ) {
			stopSearch = true;
			return true;
		}
		return false;
	}
		

	public void alphaBetaRoot(int depth, int alpha, int beta) {	
		nodes = 0;
		rootDepth = depth;
		forced = false;
		finalResult = 0;
		int hashFlag = HASH_ALPHA;
		
		int[] moves = new int[MAX_MOVES_IN_STACK];
		ttable.put(depth, alpha, bestMoveFound, HASH_ALPHA, board.getKey());
		int ttMove = ttable.bestMoveForKey(board.getKey());
		if (ttMove == 0) ttMove = ttMoveFromIID(depth, alpha, beta);
		
		int numMoves = board.genLegalMoves(moves, killers.getFirst(depth), 
				killers.getSecond(depth), ttMove);

		if (numMoves == 0) {
			// Side to move has no legal moves, we send final result			
			if ( sideToPlayIsInCheck() ) {
				finalResult = board.isWhiteTurn()? BLACK_MATED:WHITE_MATED;
			}
			else {
				finalResult = STALEMATE;
			}
			
			return;
		}
		else if (numMoves == 1) {
			// We have only one legal move, stop thinking		
			bestMoveFound = moves[0];
			return;
		}
		
		int move = 0;
		int newValue = 0;
		

		
		for (int i=0; i< numMoves; i++) {
			if ( mustStopSearch(depth) ) break;
			
			move = next(moves, numMoves, i);
			board.doMove(move);
			newValue = -alphaBeta(depth - 1, -beta, -alpha, NULL_ALLOWED);
			board.undoMove(move);			
			
			if (newValue >= beta ) {
				if (stopSearch) return;
				if (useKillers) killers.put(move, depth);
				ttable.put(depth, beta, move, HASH_BETA, board.getKey());				
			}

			if (newValue > alpha) {
				if (stopSearch) return;
				alpha = newValue;
				hashFlag = HASH_EXACT;
				ttable.put(depth, alpha, move, hashFlag, board.getKey());
				bestMoveFound = move;
				value = alpha; 
				printInfo(depth);
			}
		}
		
		if ( !stopSearch ) {
			ttable.put(depth, alpha, 0, hashFlag, board.getKey()); //changed in X86
		}
		
	}
	
	
	public int mateIn() {
		int mate = INFINITY - Math.abs(value);
		if (board.isWhiteTurn()) mate--;
		if ( value < 0) mate = mate * -1;
		return mate;
	}
	
	
	Variation bestLine = new Variation();
	private void printInfo(int depth) {
		bestLine = ttable.collectPV(board);
		bestLine.setValue(value); 
		int score = bestLine.getValue();
		if (Math.abs(score) > 80000) {
			System.out.println("info score mate "+mateIn()+" depth "+depth+					
					//" nodes "+nodes+" pv "+bestLine.toUCIString());
					" nodes "+nodes+" pv "+bestLine.variationToPGNString(board));
		}
		else {
			long partial = clock.partial();
			//System.out.println("clock.partialXNPS="+partial);
			if (partial > 0) {
				System.out.println("info score cp "+score+" depth "+depth+" nps "+(nodes*1000/partial)+
						" nodes "+nodes+" time "+clock.partial()+" pv "+bestLine.variationToPGNString(board));				
			}
			else {
				System.out.println("info score cp "+score+" depth "+depth+
						" nodes "+nodes+" time "+clock.partial()+" pv "+bestLine.variationToPGNString(board));
			}
		}		
	}
	//info score cp 167 depth 6 nodes 106819 pv d6d5 e4d2 a5b4 d3b4 e8f8 b4d3
	boolean checkExtensionDone = false;
//	int fmargin[] = {0, 200, 500};
//	boolean futilityPruning = false;
	int[] rMargins = {0, 450, 400, 350, 200, 150, 100, 90, 80, 70, 60, 50, 40, 30, 20, 0};
	private int alphaBeta(int depth, int alpha, int beta, boolean nullAllowed) {
//		if (stopSearch) return 0;
//		else if ( mustStopSearch(depth) ) return 0;
		
		if (stopSearch) {
			return board.eval();
		}
		else if ( mustStopSearch(depth) ) {
			return board.eval();
		}


		if ( board.checkRepes()) return 0; 
		
		if ( depth <= 0 ) {			
			return quies(alpha, beta, depth); 
		}
		boolean searchPV = true;
			
		int probeTTResult = probeTT(depth, alpha, beta); 
		if (probeTTResult != INVALID) return probeTTResult;
		
		int nullResult = nullMoveResult(depth, nullAllowed, beta); 
		if (nullResult != INVALID) return nullResult;
		
		int legalMoves = 0;		
		int hashFlag = HASH_ALPHA;
		int bestMove = 0;
		int ttMove = ttable.bestMoveForKey(board.getKey());
		//IID
		if ( ttMove == 0 ) ttMove = ttMoveFromIID(depth, alpha, beta); 
		
		boolean sideToPlayIsInCheck = sideToPlayIsInCheck();
		int[] moves = new int[MAX_MOVES_IN_STACK]; 		
		int numMoves = board.genPseudoLegalMoves(moves, killers.getFirst(depth), 
				killers.getSecond(depth), ttMove);
		
		int move = 0;
		int newValue = 0;
		int initialAlpha = alpha;
		
		for (int i=0; i< numMoves; i++) {
			if (stopSearch) return 0;
			
			move = next(moves, numMoves, i);
			
			board.doMove(move);
			if (sideNotToPlayIsInCheck()) {
				board.undoMove(move);
				continue;
			}			
			// If we arrive here, the are at least one legal move, so
			legalMoves++;
									
			//boolean moveCauseCheck = sideToPlayIsInCheck();
			boolean moveCauseCheck = moveCauseCheck(move);	
			//boolean isEndgame = board.isEndGame();

			// horizon prune
//			if (useHorizonPrune &&
//					depth <= 1 &&
//					i > 3 &&
//					!board.isPawnPromoting() &&
//					!XMove.isCapture(move) &&
//					!moveCauseCheck &&
//					!isEndgame) {
//				int eval = board.eval();
//				if ( eval + fmargin[depth] <= alpha || eval - fmargin[depth] >= beta ){
//					board.undoMove(move);
//					continue;
//				}
//			}

			int newDepth = depth - 1;

			// If move cause check, extend			
			if (moveCauseCheck) {			
				newDepth = depth;
			}
			else if (isKnightDouble(move)) {
				newDepth = depth;
			}
			
//			else if (sideToPlayIsInCheck && !checkExtensionDone) {
//				checkExtensionDone = true;
//				newDepth = depth;
//			}
			else if (XMove.pieceMoved(move) == WPAWN && XBoard.row(XMove.to(move)) == 3) {
				newDepth = depth;
			}
			else if (XMove.pieceMoved(move) == BPAWN && XBoard.row(XMove.to(move)) == 8) {
				newDepth = depth;
			}
			else if ( useLMR &&										
					depth >= 3 &&
					nullAllowed && 
					numMoves > 5 &&
					i > 3 &&
					!XMove.isCapture(move) 
					//!board.isPawnPromoting() && 
					//!moveCauseCheck &&
					&& !sideToPlayIsInCheck
					//&& !isEndgame 
					) {
				newDepth = depth - 2;
			}				

			/**/
			if (searchPV) {
				newValue = -alphaBeta(newDepth, -beta, -alpha, NULL_ALLOWED);
			}
			else {
				newValue = -alphaBeta(newDepth, -alpha-1, -alpha, NULL_ALLOWED);
				if (newValue > alpha) {
					newValue = -alphaBeta(newDepth, -beta, -alpha, NULL_ALLOWED);
				}
			}
			/**/			
			//newValue = -alphaBeta(newDepth, -beta, -alpha, NULL_ALLOWED);
			
			board.undoMove(move);

			if (newValue >= beta ) {
				if ( stopSearch) return 0;
				else {
					if (useKillers) killers.put(move, depth);
					ttable.put(depth, beta, move, HASH_BETA, board.getKey()); // 288 WAC 3 secs, 291 10 secs, 275 1 sec
				}
				return beta;
				//return newValue; // fail soft??
			}
			if (newValue > alpha ) {
				if ( stopSearch) return 0;
				bestMove = move;
				hashFlag = HASH_EXACT;
				alpha = newValue;	
				searchPV = false;
			}
		}
		
		if (legalMoves == 0) {
			if (sideToPlayIsInCheck()) {
				alpha = -INFINITY + (rootDepth-depth); // I will checkmated soon
			}
			else alpha = 0; //stalemate?
			
			return alpha;
		}
	
		if ( !stopSearch) {
			if (hashFlag == HASH_EXACT) ttable.put(depth, alpha, bestMove, hashFlag, board.getKey());
			else ttable.put(depth, initialAlpha, 0, hashFlag, board.getKey());
			return alpha;
		}
		else return 0;
	}
	
	int razorMargin = 200;
	
	private int quies(int alpha, int beta, int depth) {
		//RazorMargin
		if (stopSearch) return 0;
		
		int staticValue = board.eval();
		nodes++;
		if (staticValue >= beta) return staticValue;
		
		if (board.isPawnPromoting()) {
			if (staticValue < alpha - (QUEEN_VALUE + 775 ) ) {
				return alpha;
			}
		}
		else {
			if (staticValue < alpha - QUEEN_VALUE) {
				return alpha;
			}			
		}
		
		if (staticValue > alpha) {
			alpha = staticValue;
		}
		
		int[] moves = new int[MAX_MOVES_IN_STACK];
		int numMoves = 0;
		numMoves = board.genPseudoLegalQuiesMoves(moves);
		
		boolean oneMoveDone = false;
		if (numMoves == 0) {
			return staticValue;
		}
		
		int move = 0;
		for (int i=0; i < numMoves; i++) {
			move = next(moves, numMoves, i);
			
			if (useBadCapture && isBadCapture(move)) {
				continue;
			}

			// delta pruning
			if ( useDeltaPruning && 
					staticValue + valueOf[XMove.pieceCaptured(move)] + DELTA_MARGIN < alpha 
					&& XMove.flag(move) < FLAG_PROMO_QUEEN ) {
				continue;
			}
			
			board.doMove(move);
			if (sideNotToPlayIsInCheck() ) {
				board.undoMove(move);
				continue;
			}
			
			oneMoveDone = true;			
			int newValue = -quies(-beta, -alpha, depth - 1);
			board.undoMove(move);
			
			if (newValue >= beta) {
				return beta;
				//return newValue; //Fail soft???
			}
			if (newValue > alpha) {
				alpha = newValue;
			}   
		}
		
		if (!oneMoveDone) {
			return staticValue;
		}
		
		return alpha;
	}
	
	private boolean isBadCapture(int move) {
		if (XMove.flag(move) != 0) return false;
		if (XBoard.isQueen(XMove.pieceCaptured(move))) return false;
		if (board.isPawnPromoting()) return false;
		if (XBoard.isPawn(XMove.pieceMoved(move))) return false;
		if (XBoard.isKing(XMove.pieceMoved(move))) return false;
		
		
		if ( board.isProtectedByPawn( XMove.to(move), !XMove.isWhite(move))) {
			if ( valueOf[XMove.pieceMoved(move)] > valueOf[XMove.pieceCaptured(move)] + 100 ) {
				return true;
			}										
		}
		
		return false;
	}

	private boolean isKnightDouble(int move) {
		int pieceMoved = XMove.pieceMoved(move);
		if ( !Pieces.isKnight(pieceMoved) ) return false;
		int newFrom = XMove.to(move);
		return board.getGenerator().isKnightDoubleAttack(Pieces.KNIGHT_DIRECTIONS[newFrom], newFrom);
	}

	private boolean moveCauseCheck(int move) {
		//board.getGenerator().isPiecePinned(piece, moveFrom, moveTo, pinned)
		if (board.getGenerator().isPiecePinned(XMove.pieceMoved(move), XMove.from(move), XMove.to(move), false) != 0) {
			return true;
		}
		
		int pieceMoved = XMove.pieceMoved(move);
		if (XMove.flag(move) == FLAG_PROMO_QUEEN) pieceMoved = pieceMoved%2==0? BQUEEN:WQUEEN; //tmp solo promos damas
		int from = XMove.to(move); // desde la nueva casilla
		switch(pieceMoved) {
		case WPAWN:
			return board.getBlackKingSquare() == from - 13 || board.getBlackKingSquare() == from - 11; 
		case BPAWN:			
			return board.getWhiteKingSquare() == from + 13 || board.getWhiteKingSquare()== from + 11;
		case WKNIGHT:
		case BKNIGHT:
			return board.getGenerator().checkSteps(Pieces.KNIGHT_DIRECTIONS[from], from);
		case WBISHOP:
		case BBISHOP:
			return board.getGenerator().checkSlides(Pieces.BISHOP_DIRECTIONS[from], from);
		case WROOK:
		case BROOK:
			return board.getGenerator().checkSlides(Pieces.ROOK_DIRECTIONS[from], from);
		case WQUEEN:
		case BQUEEN:
			return 
					board.getGenerator().checkSlides(Pieces.ROOK_DIRECTIONS[from], from) 
					||
					board.getGenerator().checkSlides(Pieces.BISHOP_DIRECTIONS[from], from);
		default:
			return false;
			
		}
		
	}


	private boolean sideToPlayIsInCheck() {
		return board.isInCheck(board.isWhiteTurn());
	}
	private boolean sideNotToPlayIsInCheck() {
		return board.isInCheck(!board.isWhiteTurn() ); 
	}


	public void setLimitTime(long limitTime) {
		this.timeForMove = limitTime;
	}
	
	public int getNodes() {
		return nodes;
	}
	
	public boolean isForced() {
		return forced;
	}

	public int getFinalResult() {
		return finalResult;
	}

	public Board getBoard() {
		return board;
	}

	public TTable getTtable() {
		return ttable;
	}

	public void setTtable(TTable ttable) {
		this.ttable = ttable;
	}

	public int getValue() {
		return value;
	}

//	public TreeInformer getTreeInformer() {
//		return treeInformer;
//	}
	
	private int next(int[] moves, int numMoves, int from) {
		int current = moves[from];
		int bestScore = XMove.score(current);
		
		int bestIndex = from;
		for (int i=from+1; i<numMoves; i++) {
				if ( XMove.score( moves[i] ) > bestScore ) {
					bestScore = XMove.score( moves[i] );
					bestIndex = i;
				}
		}
		if (bestIndex != from) {
			// swapp
			moves[from] = moves[bestIndex];
			moves[bestIndex] = current;
		}
		return moves[from];
	}
	
	private int nullMoveResult(int depth, boolean nullAllowed, int beta) {
		if (!stopSearch && useNullMove && depth > 1 && nullAllowed &&
				board.sideToPlayHasAtLeastOnePiece() &&
				!sideToPlayIsInCheck() ) {
			board.doNullMove();
			
			int R = 2;
			if (depth > 6) R = 3;
			
			int newValue = -alphaBeta(depth-1-R, -beta, -beta+1, NO_NULL);
			board.undoNullMove();
			if ( stopSearch){
				return board.eval(); 		
			}
			else {
				if (newValue >= beta) {
					ttable.put(depth, beta, 0, HASH_BETA, board.getKey());
					return beta;
				}	
			}
		}
		
		return INVALID;
	}
	
	private int probeTT(int depth, int alpha, int beta) {
		if (probeTT) {
			TTEntry ttEntry = ttable.probe(depth, alpha, beta, board.getKey());
			if (ttEntry != null) {
				ttMatches++;
				if (ttEntry.getFlag() == HASH_EXACT) {					
					return ttEntry.getValue();
				}

				else if (ttEntry.getFlag() == HASH_BETA) {
					if (ttEntry.getValue() >= beta) {
						return beta;
					}
				}

				else if (ttEntry.getFlag() == HASH_ALPHA) {
					if (ttEntry.getValue() <= alpha) {
						return alpha;
					}
				}
			}
			else {
				misses++;
			}
		}
		
		return INVALID;
	}
	
	

	private int ttMoveFromIID(int depth, int alpha, int beta) {
		if (useIID && depth >= 5) {
			alphaBeta(3, alpha, beta, NO_NULL);
 
			//alphaBeta(3, -beta, -beta+1, NO_NULL); //changed X86
			return ttable.bestMoveForKey(board.getKey());
		}
		return 0;
	}

	public TreeInformer getTreeInformer() {
		return treeInformer;
	}

	public int getRootDepth() {
		return rootDepth;
	}

}
