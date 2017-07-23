package org.cheoss.controller;

import java.io.*;
import java.util.*;

import org.book.controller.*;
import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class Cheoss implements Constants {

	public static void main(String[] args) {
		Board board = null;
		Tree tree = null;
		Clock clock = null;
		boolean onBook = true;
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			boolean isNewGame = false;
			Book book = null;
			for (;;) {
				StringTokenizer st = new StringTokenizer(input.readLine());
				String str = st.nextToken();
				if (str.equals("uci")) {
		    		System.out.println("id name "+"Cheoss");
		        	System.out.println("id author Miguel Embuena");
		        	System.out.println("uciok");        	
				}
				else if (str.equals("isready")) {
					System.out.println("readyok");
				}
				else if (str.equals("ucinewgame")) {
//					the next search (started with "position" and "go") 
//					will be from a different game
//					onBook = true;					
//					book = openBook();
					isNewGame = true;
				}
				else if (str.equals("position")) {
					//position startpos moves b1c3 d7d5 g1f3 d5d4 c3b5 e7e5 f3e5 a7a6 b5a3
					String fen = st.nextToken();
					if (fen.equals("fen")){
						fen = st.nextToken();
					}
					String moves = "";
					if (st.hasMoreTokens()) {			
						moves = st.nextToken(); 
					}
					
					if (isNewGame) {
						isNewGame = false;
						if (fen.equalsIgnoreCase("startpos")) {
							fen = FEN_START;
						}
						else {
							System.out.println("setting fen "+fen);
						}
						board = null;
						tree = null;
						System.gc();
						board = new Board(fen);
						clock = new Clock();
						tree = new Tree(board, clock);
						if (moves.equals("moves")) {
							while (st.hasMoreTokens()) {
								int move = Interpreter.uciStrToMove(st.nextToken(), board);
								board.doMove(move);
								//board.doUciMove(st.nextToken());
								board.registerRealGameKey(move); //bug?
							}							
						}
					}
					else { // not new game
						String strmove = null;
						while (st.hasMoreTokens()) {
							strmove = st.nextToken();
						}
						int move = Interpreter.uciStrToMove(strmove, board);
						board.doMove(move);
						board.registerRealGameKey(move); //bug?
					}
					
				}

				//go wtime 179974 btime 180000  
				else if (str.equals("go")) {					
					long timeForMove = adjustClock(st, board.isWhiteTurn(), clock);
					int move = 0;
					if (board.getMoveNumber() <= 1 ) {
						onBook = true;					
						book = openBook(board.isWhiteTurn());
					}
					if (book != null && onBook) {
				    	if (board.getMoveNumber()>= 28) {
				    		onBook = false;
				    		move = 0;
				    	}
				    	else {
				    		int lastmove = 0;
				    		if (board.getMoveNumber() != 0) {
				    			lastmove = board.getRepDetector().getMoveFromMovelist(board.getMoveNumber() - 1);
				    		}
					    	
					    	String lastPgnMove = Interpreter.moveToPGN(lastmove, board);
					    	String candidate = book.next(lastPgnMove, board.isWhiteTurn(), board.getMoveNumber());
					    	move = Interpreter.pgnToMove(candidate, board);				    		
				    	}
					}
					
										
					if (move == 0) {	
						move = tree.search(timeForMove);
					}    	
					board.doMove(move);
					board.registerRealGameKey(move);
					String bestmove = Interpreter.moveToUci(move);
					System.out.println("bestmove "+bestmove);
					clock.stop();
				}		
				else if (str.equals("quit")) {
					System.exit(0);     		      	  
				}
				else {
					//System.out.println("not implemented command: "+str);
				}
			}			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
    private static Book openBook(boolean playWithWhite){
    	Book b = new Book();
		b.setVerbose(true);
		try {
			//C:\workspaces\engin\Book
			//C:/Users/miguel/Documents/engines/mini.txt
			//String ejemplo = "C:/chessEngines/motoresCheoss/Rhino/mini.txt";
			//tmpFijate
			String ejemplo = "C:/Users/miguel/Documents/engines/mini.txt";
			b.init(ejemplo, 1500, playWithWhite);
			return b;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
    }
    
    
    private static long adjustClock(StringTokenizer st, boolean whiteTurn, Clock clock) {
    	// LittleBlitzer ==> go movestogo 40 wtime 60000 btime 60000
    	String wmilis = "";
    	String bmilis = "";
    	String movesToGo = "";
    	while (st.hasMoreTokens()) {
    		String s = st.nextToken();
    		if (s.equalsIgnoreCase("wtime")) {
    			wmilis = st.nextToken();
    		}
    		else if (s.equalsIgnoreCase("btime")) {
    			bmilis = st.nextToken();
    		}
    		else if (s.equalsIgnoreCase("winc")) {
    			
    		}
    		else if (s.equalsIgnoreCase("binc")) {
    			
    		}
    		else if (s.equalsIgnoreCase("movestogo")) {
    			movesToGo = st.nextToken();
    		}
    		
    		
    	}
    	
		String milis = whiteTurn? wmilis:bmilis;
		if (clock.getBaseTime() == 0l) {
			clock.setBaseTime(new Long(Integer.parseInt(milis)));
			if (!movesToGo.isEmpty()) {
				clock.setBaseMoves(Integer.parseInt(movesToGo));	
			}			
		}
		System.out.println("clock.toString()="+clock.toString());
		clock.start();
		if (movesToGo.isEmpty()) movesToGo = "0";
		long tfm = clock.timeForMove(Integer.parseInt(movesToGo), new Long(Integer.parseInt(milis)));
		System.out.println("clock.timeForMove="+tfm);
		return tfm;			
		
    }

}
