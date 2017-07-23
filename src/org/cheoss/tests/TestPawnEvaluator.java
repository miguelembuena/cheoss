package org.cheoss.tests;

import org.cheoss.board.*;
import org.cheoss.evaluation.*;
import org.cheoss.util.*;

import junit.framework.*;

public class TestPawnEvaluator extends TestCase implements Constants {
	
	
	public void testCountAttackers() {
		
		String pos1 = "r5k1/1pp3pp/8/8/8/8/5PPP/4R1K1 b - - 0 1 ";
		Board board = new Board(pos1);
		int eval = board.eval();
		System.out.println("eval = "+eval);
		
		AlterPawnEvaluator ape = (AlterPawnEvaluator) board.getPawnEvaluator(); 
		
		System.out.println("pasado en b7? "+ape.isWhitePassedPawnIn(b7));
		
		System.out.println("pasado en b7? "+ape.isBlackPassedPawnIn(b7));
		
		//
		
	}
	
	
	
 }
