package org.cheoss.tests;

import java.math.*;
import java.util.*;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

public class TestRepeatDoMove extends TestCase implements Constants {
	

	public void testSpanishGame() {
		String fen_final = "r2qkb1r/1bpp1ppp/p1n2n2/1p2p3/4P3/1B3N2/PPPP1PPP/RNBQ1RK1 w kq - 4 7";
		String game = "1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6 3.Af1-b5 a7-a6 4.Ab5-a4 b7-" +
				"b5 5.Aa4-b3 Cg8-f6 6.0-0 Ac8-b7";
				
		Board board = new Board(FEN_START);
////		AuxLoadMoves aux = new AuxLoadMoves();
////		aux.setAlgebraicSequence(game);
//		ArrayList<String> strMoves = aux.getListOfStringMoves();
//		AuxLoadMoves aux = new AuxLoadMoves();
//		aux.setAlgebraicSequence(game);
		ArrayList<String> strMoves = Interpreter.stringToListOfStringMoves(game);
		
		ArrayList<Integer> moves = new ArrayList<Integer>();
		int move1w = Interpreter.algebraicToMove(strMoves.get(0), board);
		board.doMove(move1w);
		moves.add(move1w);
		
		int move1b = Interpreter.algebraicToMove(strMoves.get(1), board);
		board.doMove(move1b);
		moves.add(move1b);
				
		int move2w = Interpreter.algebraicToMove(strMoves.get(2), board);
		board.doMove(move2w);
		moves.add(move2w);
		
		int move2b = Interpreter.algebraicToMove(strMoves.get(3), board);
		board.doMove(move2b);
		moves.add(move2b);
			  
		int move3w = Interpreter.algebraicToMove(strMoves.get(4), board);
		board.doMove(move3w);
		moves.add(move3w);
		
		int move3b = Interpreter.algebraicToMove(strMoves.get(5), board);
		board.doMove(move3b);
		moves.add(move3b);

		int move4w = Interpreter.algebraicToMove(strMoves.get(6), board);
		board.doMove(move4w);
		moves.add(move4w);
		
		int move4b = Interpreter.algebraicToMove(strMoves.get(7), board);
		board.doMove(move4b);
		moves.add(move4b);
		
		int move5w = Interpreter.algebraicToMove(strMoves.get(8), board);
		board.doMove(move5w);
		moves.add(move5w);
		
		int move5b = Interpreter.algebraicToMove(strMoves.get(9), board);
		board.doMove(move5b);
		moves.add(move5b);
				
		int move6w = Interpreter.algebraicToMove(strMoves.get(10), board);
		board.doMove(move6w);
		moves.add(move6w);
		
		int move6b = Interpreter.algebraicToMove(strMoves.get(11), board);
		board.doMove(move6b);
		moves.add(move6b);
		
		int movesInList = moves.size() - 1;
		int numMoves = 0;
		long start = System.currentTimeMillis();
		for (int j = 0; j < 3000; j++) {
			for (int i = movesInList; i >= 0; i--) {
				numMoves++;
				board.undoMove(moves.get(i));						
			}
			for (int i = 0; i <= movesInList; i++) {
				numMoves++;
				board.doMove(moves.get(i));
			}			
		}
		long millis = System.currentTimeMillis() - start;
		
		//System.out.println("numMoves = "+numMoves+" millis = "+millis);
		BigDecimal movesPerSec = movesPerSec(numMoves, millis);
		//System.out.println("Moves per sec = "+movesPerSec);
		//assertTrue( movesPerSec.compareTo(new BigDecimal("7000000")) > 0 );
		
//		numMoves = 72000000 millis = 9797
//		Moves per sec = 7349188.53
//		numMoves = 72000000 millis = 9813
//		Moves per sec = 7337205.75
		
		// Con material count
//		numMoves = 72000000 millis = 10250
//		Moves per sec = 7024390.24
//		numMoves = 72000000 millis = 10281
//		Moves per sec = 7003209.80
		
		// Resultado de la prueba de guardar material en BoardState: 
		// es mucho más lento

		// Con BitPack, algo más rápido
//		numMoves = 72000000 millis = 9938
//		Moves per sec = 7244918.49		
//		numMoves = 72000000 millis = 9859
//		Moves per sec = 7302971.90
		
		// con BitPack, int para csrights en lugar de booleans
//		numMoves = 72000000 millis = 9578
//		Moves per sec = 7517226.98
//		numMoves = 72000000 millis = 9562
//		Moves per sec = 7529805.48		
		
		// Clase Move ya no existe, move empaquetado en un entero, lo trata clase XMove
//		NUMMOVES = 72000000 MILLIS = 9390
//		MOVES PER SEC = 7667731.63
//		numMoves = 72000000 millis = 9375
//		Moves per sec = 7680000.00
		

		//04 junio
//		numMoves = 72000000 millis = 9594
//		Moves per sec = 7504690.43
		
		assertEquals(fen_final.trim(), board.getFEN());
	}
	
	public void testSpeedLegal() {
		String fen_final = "r2qkb1r/1bpp1ppp/p1n2n2/1p2p3/4P3/1B3N2/PPPP1PPP/RNBQ1RK1 w kq - 4 7";
		String game = "1.e2-e4 e7-e5 2.Cg1-f3 Cb8-c6 3.Af1-b5 a7-a6 4.Ab5-a4 b7-" +
				"b5 5.Aa4-b3 Cg8-f6 6.0-0 Ac8-b7";
				
		Board board = new Board(FEN_START);
		ArrayList<String> strMoves = Interpreter.stringToListOfStringMoves(game);
		
		ArrayList<Integer> moves = new ArrayList<Integer>();
		int move1w = Interpreter.algebraicToMove(strMoves.get(0), board);
		board.doMove(move1w);
		moves.add(move1w);
		
		int move1b = Interpreter.algebraicToMove(strMoves.get(1), board);
		board.doMove(move1b);
		moves.add(move1b);
				
		int move2w = Interpreter.algebraicToMove(strMoves.get(2), board);
		board.doMove(move2w);
		moves.add(move2w);
		
		int move2b = Interpreter.algebraicToMove(strMoves.get(3), board);
		board.doMove(move2b);
		moves.add(move2b);
			  
		int move3w = Interpreter.algebraicToMove(strMoves.get(4), board);
		board.doMove(move3w);
		moves.add(move3w);
		
		int move3b = Interpreter.algebraicToMove(strMoves.get(5), board);
		board.doMove(move3b);
		moves.add(move3b);

		int move4w = Interpreter.algebraicToMove(strMoves.get(6), board);
		board.doMove(move4w);
		moves.add(move4w);
		
		int move4b = Interpreter.algebraicToMove(strMoves.get(7), board);
		board.doMove(move4b);
		moves.add(move4b);
		
		int move5w = Interpreter.algebraicToMove(strMoves.get(8), board);
		board.doMove(move5w);
		moves.add(move5w);
		
		int move5b = Interpreter.algebraicToMove(strMoves.get(9), board);
		board.doMove(move5b);
		moves.add(move5b);
				
		int move6w = Interpreter.algebraicToMove(strMoves.get(10), board);
		board.doMove(move6w);
		moves.add(move6w);
		
		int move6b = Interpreter.algebraicToMove(strMoves.get(11), board);
		board.doMove(move6b);
		moves.add(move6b);
		
		int movesInList = moves.size() - 1;
		int numMoves = 0;
		long start = System.currentTimeMillis();
		for (int j = 0; j < 3000; j++) {
			for (int i = movesInList; i >= 0; i--) {
				numMoves++;
				board.undoMove(moves.get(i));						
			}
			for (int i = 0; i <= movesInList; i++) {
				numMoves++;
				
				board.doMove(moves.get(i));
			}			
		}
		long millis = System.currentTimeMillis() - start;
		
		BigDecimal movesPerSec = movesPerSec(numMoves, millis);
		//assertTrue( movesPerSec.compareTo(new BigDecimal("7000000")) > 0 );

		
//		numMoves = 72000000 millis = 9797
//		Moves per sec = 7349188.53
//		numMoves = 72000000 millis = 9813
//		Moves per sec = 7337205.75
		
		// Con material count
//		numMoves = 72000000 millis = 10250
//		Moves per sec = 7024390.24
//		numMoves = 72000000 millis = 10281
//		Moves per sec = 7003209.80
		
		// Resultado de la prueba de guardar material en BoardState: 
		// es mucho más lento

		// Con BitPack, algo más rápido
//		numMoves = 72000000 millis = 9938
//		Moves per sec = 7244918.49		
//		numMoves = 72000000 millis = 9859
//		Moves per sec = 7302971.90
		
		// con BitPack, int para csrights en lugar de booleans
//		numMoves = 72000000 millis = 9578
//		Moves per sec = 7517226.98
//		numMoves = 72000000 millis = 9562
//		Moves per sec = 7529805.48		
		
		// Clase Move ya no existe, move empaquetado en un entero, lo trata clase XMove
//		NUMMOVES = 72000000 MILLIS = 9390
//		MOVES PER SEC = 7667731.63
//		numMoves = 72000000 millis = 9375
//		Moves per sec = 7680000.00
		

		//04 junio
//		numMoves = 72000000 millis = 9594
//		Moves per sec = 7504690.43
		
		assertEquals(fen_final.trim(), board.getFEN());
	}

	
	private BigDecimal movesPerSec(int numOfMoves, long millis) {
		return ( new BigDecimal( numOfMoves ).
		multiply( new BigDecimal("1000")) ).
			divide(new BigDecimal(millis), 2, BigDecimal.ROUND_HALF_UP);
	}

	
//	public void testSpeed() {
//		Board board = new Board();
//		board.setFEN(FEN_START);
//		
//		// 1 millón por 8 = 8 millones de movimientos
//		// tardan aprox 6000 millis,
//		// lo que significa 1.333.333,33 movs per sec aprox
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 1000000; i++) {
//			board.doMove("Cg1-f3");
//			board.doMove("Cg8-f6");
//			board.doMove("Th1-g1");
//			board.doMove("Th8-g8");
//			
//			board.doMove("Tg1-h1");
//			board.doMove("Tg8-h8");
//			board.doMove("Cf3-g1");
//			board.doMove("Cf6-g8");
//		}
//		long end = System.currentTimeMillis();		
//		long millis = end-start;
//		
//		
//		BigDecimal movesPerSec = ( new BigDecimal( board.getMoveNumber() ).
//				multiply( new BigDecimal("1000")) ).
//					divide(new BigDecimal(millis), 2, BigDecimal.ROUND_HALF_UP);
//		
//		System.out.println(+board.getMoveNumber()+ " moves realizados en "+millis+" ms ");
//		System.out.println("Moves por segundo = "+movesPerSec);
//
//		
//	}
	 }
