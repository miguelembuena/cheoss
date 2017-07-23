package org.cheoss.tests;

import java.io.*;
import java.util.*;

import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

import junit.framework.*;


public class TestBKSuite extends TestCase implements Constants {
	
	private class Pos {
		public String fen = null;
		public ArrayList<String> moves = null;
		public String idPos = null;
		
		public String toString() {
			String movStr = "";
			if (moves == null) {
				System.out.println("moves nulos");
			}
			else {
				for (String m : moves) {
					movStr = movStr + m + ", ";
				}				
			}
			
			return "fen = "+fen+" moves = "+movStr+" "+idPos;
		}
	}
	
	private ArrayList<Pos> fillSuite(String data) {
		ArrayList<Pos> suite = new ArrayList<TestBKSuite.Pos>();
		StringTokenizer st = new StringTokenizer(data, ";");
		
		Pos pos = null;
		
		while (st.hasMoreTokens()) {	
			String token = st.nextToken();
					
			if (token.contains("bm")) {
				String par[] = token.split("bm");
				pos = new Pos();
				pos.fen = par[0].trim();
				
				ArrayList<String> movesToAdd = new ArrayList<String>();
				for (String s : par[1].split(" ") ) {
					if (s.trim().length() > 0) {
						movesToAdd.add(s);
					}
				}				
				pos.moves = movesToAdd;
			}
			else if (token.contains("id")) {
					pos.idPos = token;
					suite.add(pos);
			}
									
		}		
		return suite;
	}
	
	public void testBK() {
		
		String positions = "";
		try {			
			//tmpfijate
			positions = fileToString( "C:/Users/miguel/Documents/workspaceOctopus/Octopus/doc/BKSuite.txt" );	
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		ArrayList<Pos> suite = fillSuite(positions);		
		long timeForMove = 10 * 1000;
		int score = 0;
		
		for (Pos p : suite) {
			long antes = System.currentTimeMillis();						
			System.out.println("Test position => "+p.toString());
			Board board = new Board(p.fen.trim());
			Tree tree = new Tree(board);
			ArrayList<Integer> moves = new ArrayList<Integer>();
			for (String m : p.moves) {
				if (m.trim().length() > 0) {
					int movex = Interpreter.pgnToMove(m, board);
					moves.add(movex);
					//System.out.println("pgn "+m+" convertido en "+movex);
				}
			}

			int moveFound = tree.searchForMove(timeForMove, moves);
			for (String m : p.moves) {
				if (m.trim().length() > 0) {
					int movex = Interpreter.pgnToMove(m, board);
					if ( XMove.equals(moveFound, movex)) {
						System.out.println("Solved!");
						score++;
					}
				}
			}

			System.out.println("Time = "+(System.currentTimeMillis()-antes));
			System.out.println("Score = "+score);
			System.out.println("");
		}


	}

	private String fileToString(String filePath) throws Exception {
		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		Reader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer();
		int i = 0; 			
		while ((i = reader.read()) > -1) {
			sb.append((char)i);
		}
		reader.close();
		return sb.toString();  			
	}


}
