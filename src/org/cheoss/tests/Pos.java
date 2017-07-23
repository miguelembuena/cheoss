package org.cheoss.tests;

import java.util.*;

public class Pos {
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
