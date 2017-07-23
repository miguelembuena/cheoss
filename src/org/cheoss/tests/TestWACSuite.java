package org.cheoss.tests;

import java.io.*;
import java.util.*;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

public class TestWACSuite extends TestCase implements Constants{
	
	
	private ArrayList<Pos> fillSuite(String data) {
		ArrayList<Pos> suite = new ArrayList<Pos>();
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
	
	public void testWAC() {
		
		String positions = "";
		try {
			//tmpFijate
			positions = fileToString( "C:/Users/miguel/Documents/workspaceOctopus/Octopus/doc/WACSuite.txt" );
			//positions = fileToString( "C:/espejo/workspaces/RefactoringCheoss/CheossRhino/WACSuite.txt" );	
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		ArrayList<Pos> suite = fillSuite(positions);		
		long timeForMove = 3 * 1000; // result 288
		//long timeForMove = 1 * 1000; // result 275 
		//long timeForMove = 10 * 1000; // result 291 
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
