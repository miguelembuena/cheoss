package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.util.*;

public class TestHashPoly extends TestCase implements Constants {

	//http://alpha.uhasselt.be/Research/Algebra/Toga/book_format.html
//	square      file   row
//	======================
//	a1             0     0
//	h1             7     0
//	a8             0     7
//	h8             7     7

	public void testRowCol() {
		//file y col sinonimos
		int square = a1;
		assertEquals(0, Zobrist.col(square));
		assertEquals(0, Zobrist.row(square));
		
		square = a2;
		assertEquals(0, Zobrist.col(square));
		assertEquals(1, Zobrist.row(square));

		square = a7;
		assertEquals(0, Zobrist.col(square));
		assertEquals(6, Zobrist.row(square));

		square = b7;
		assertEquals(1, Zobrist.col(square));
		assertEquals(6, Zobrist.row(square));
		
		square = e7;
		assertEquals(4, Zobrist.col(square));
		assertEquals(6, Zobrist.row(square));

		square = e4;
		assertEquals(4, Zobrist.col(square));
		assertEquals(3, Zobrist.row(square));

		
		square = h1;
		assertEquals(7, Zobrist.col(square));
		assertEquals(0, Zobrist.row(square));
		
		square = a8;
		assertEquals(0, Zobrist.col(square));
		assertEquals(7, Zobrist.row(square));

		square = h8;
		assertEquals(7, Zobrist.col(square));
		assertEquals(7, Zobrist.row(square));
	}
	
	
//	"kind_of_piece" is encoded as follows
//
//	black pawn    0
//	white pawn    1
//	black knight  2
//	white knight  3
//	black bishop  4
//	white bishop  5
//	black rook    6
//	white rook    7
//	black queen   8
//	white queen   9
//	black king   10
//	white king   11

	public void testTypeOfPiece() {

		assertEquals(1, Zobrist.typeOfPiece(WPAWN));
		assertEquals(3, Zobrist.typeOfPiece(WKNIGHT));
		assertEquals(5, Zobrist.typeOfPiece(WBISHOP));
		assertEquals(7, Zobrist.typeOfPiece(WROOK));
		assertEquals(9, Zobrist.typeOfPiece(WQUEEN));
		assertEquals(11, Zobrist.typeOfPiece(WKING));
		
		assertEquals(0, Zobrist.typeOfPiece(BPAWN));
		assertEquals(2, Zobrist.typeOfPiece(BKNIGHT));
		assertEquals(4, Zobrist.typeOfPiece(BBISHOP));
		assertEquals(6, Zobrist.typeOfPiece(BROOK));
		assertEquals(8, Zobrist.typeOfPiece(BQUEEN));
		assertEquals(10, Zobrist.typeOfPiece(BKING));
		
		assertEquals(0, Zobrist.typeOfPiece(BPAWN));
		assertEquals(5, Zobrist.typeOfPiece(WBISHOP));
		assertEquals(4, Zobrist.typeOfPiece(BBISHOP));
	}
	
	
	public void testSomePositions() {
		//test data from http://alpha.uhasselt.be/Research/Algebra/Toga/book_format.html
		//starting position
		String FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		long expectedKey = 0x463b96181691fc9cL;
		Board board = new Board(FEN);
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));
		
//		position after e2e4
//		FEN=rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1
//		key=823c9b50fd114196
		FEN = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
		expectedKey = 0x823c9b50fd114196L;
		board = new Board(FEN);
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));
		
//		position after e2e4 d75
//		FEN=rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2
//		key=0756b94461c50fb0
		FEN = "rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2";
		expectedKey = 0x0756b94461c50fb0L;
		board = new Board(FEN);
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));

//
//		position after e2e4 d7d5 e4e5
//		FEN=rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2
//		key=662fafb965db29d4
		FEN = "rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2";
		expectedKey = 0x662fafb965db29d4L;
		board = new Board(FEN);
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));

//
//		position after e2e4 d7d5 e4e5 f7f5
//		FEN=rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3
//		key=22a48b5a8e47ff78
		FEN = "rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3";
		expectedKey = 0x22a48b5a8e47ff78L;
		board = new Board(FEN);
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));

//
//		position after e2e4 d7d5 e4e5 f7f5 e1e2
//		FEN=rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR b kq - 0 3
//		key=652a607ca3f242c1
		FEN = "rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR b kq - 0 3";
		expectedKey = 0x652a607ca3f242c1L;
		board = new Board(FEN);
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));

//
//		position after e2e4 d7d5 e4e5 f7f5 e1e2 e8f7
//		FEN=rnbq1bnr/ppp1pkpp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR w - - 0 4
//		key=00fdd303c946bdd9
		FEN = "rnbq1bnr/ppp1pkpp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR w - - 0 4";
		expectedKey = 0x00fdd303c946bdd9L;
		board = new Board(FEN);
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));

//
//		position after a2a4 b7b5 h2h4 b5b4 c2c4
//		FEN=rnbqkbnr/p1pppppp/8/8/PpP4P/8/1P1PPPP1/RNBQKBNR b KQkq c3 0 3
//		key=3c8123ea7b067637
		FEN = "rnbqkbnr/p1pppppp/8/8/PpP4P/8/1P1PPPP1/RNBQKBNR b KQkq c3 0 3";
		expectedKey = 0x3c8123ea7b067637L;
		board = new Board(FEN);
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));

//
//		position after a2a4 b7b5 h2h4 b5b4 c2c4 b4c3 a1a3
//		FEN=rnbqkbnr/p1pppppp/8/8/P6P/R1p5/1P1PPPP1/1NBQKBNR b Kkq - 0 4
//		key=5c3f9b829b279560
		FEN = "rnbqkbnr/p1pppppp/8/8/P6P/R1p5/1P1PPPP1/1NBQKBNR b Kkq - 0 4";
		expectedKey = 0x5c3f9b829b279560L;
		board = new Board(FEN);
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));

	}
	
	
	public void testSomeMoves() {
		//test data from http://alpha.uhasselt.be/Research/Algebra/Toga/book_format.html
		//starting position
		String FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		Board board = new Board(FEN);
		long expectedKey = 0x463b96181691fc9cL;
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));		
		assertEquals(expectedKey, board.getKey());
		
		
//		position after e2e4 key=823c9b50fd114196
		board.doUciMove("e2e4");		
		expectedKey = 0x823c9b50fd114196L;
		assertEquals(expectedKey, board.getKey());

//		position after e2e4 d75 key=0756b94461c50fb0		
		board.doUciMove("d7d5");
		expectedKey = 0x0756b94461c50fb0L;
		assertEquals(expectedKey, board.getKey());
		
//		position after e2e4 d7d5 e4e5
////		FEN=rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2
////		key=662fafb965db29d4
//		FEN = "rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2";
		expectedKey = 0x662fafb965db29d4L;
		board.doUciMove("e4e5");
		assertEquals(expectedKey, board.getKey());
//
////
////		position after e2e4 d7d5 e4e5 f7f5
////		FEN=rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3
////		key=22a48b5a8e47ff78
//		FEN = "rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3";
		board.doUciMove("f7f5");
		expectedKey = 0x22a48b5a8e47ff78L;
		assertEquals(expectedKey, board.getKey());
//
////
////		position after e2e4 d7d5 e4e5 f7f5 e1e2
////		FEN=rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR b kq - 0 3
////		key=652a607ca3f242c1
//		FEN = "rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR b kq - 0 3";
    // esta es la que deja zobrist, con ep f6"rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR b kq f6 0 1" 
		board.doUciMove("e1e2");
		expectedKey = 0x652a607ca3f242c1L;
		// parece que falla porque debe borrarse el en passant de antes, el f6
		assertEquals(expectedKey, board.getKey());
//
////
//		position after e2e4 d7d5 e4e5 f7f5 e1e2 e8f7
////		FEN=rnbq1bnr/ppp1pkpp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR w - - 0 4
////		key=00fdd303c946bdd9
//		FEN = "rnbq1bnr/ppp1pkpp/8/3pPp2/8/8/PPPPKPPP/RNBQ1BNR w - - 0 4";
		
		board.doUciMove("e8f7");
		expectedKey = 0x00fdd303c946bdd9L;
		assertEquals(expectedKey, board.getKey());
	
////
//		position after a2a4 b7b5 h2h4 b5b4 c2c4
		FEN =       "rnbqkbnr/p1pppppp/8/8/PpP4P/8/1P1PPPP1/RNBQKBNR b KQkq c3 0 3";		
		board = new Board(FEN);
		expectedKey = 0x3c8123ea7b067637L;
		assertEquals(expectedKey, board.getKey());
//
////
		board.doUciMove("b4c3");
		board.doUciMove("a1a3");
//		position after a2a4 b7b5 h2h4 b5b4 c2c4 b4c3 a1a3
////		FEN=rnbqkbnr/p1pppppp/8/8/P6P/R1p5/1P1PPPP1/1NBQKBNR b Kkq - 0 4
////		key=5c3f9b829b279560
//		FEN = "rnbqkbnr/p1pppppp/8/8/P6P/R1p5/1P1PPPP1/1NBQKBNR b Kkq - 0 4";
		expectedKey = 0x5c3f9b829b279560L;
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));


	}
	
	
	public void testDoMovesUndoMoves() {
		//test data from http://alpha.uhasselt.be/Research/Algebra/Toga/book_format.html
		//starting position
		String FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		Board board = new Board(FEN);
		long expectedKey = 0x463b96181691fc9cL;
		assertEquals(expectedKey, Zobrist.calculateBoardKey(board));		
		assertEquals(expectedKey, board.getKey());
		
	
//		position after e2e4 key=823c9b50fd114196
		int move_e2e4 = Interpreter.uciStrToMove("e2e4", board);
		board.doUciMove("e2e4");		
		expectedKey = 0x823c9b50fd114196L;
		assertEquals(expectedKey, board.getKey());

//		position after e2e4 d75 key=0756b94461c50fb0		
		int move_d7d5 = Interpreter.uciStrToMove("d7d5", board);
		board.doUciMove("d7d5");
		expectedKey = 0x0756b94461c50fb0L;
		assertEquals(expectedKey, board.getKey());
		
//		position after e2e4 d7d5 e4e5
		expectedKey = 0x662fafb965db29d4L;
		int move_e4e5 = Interpreter.uciStrToMove("e4e5", board);
		board.doUciMove("e4e5");
		assertEquals(expectedKey, board.getKey());
//
//		position after e2e4 d7d5 e4e5 f7f5
		int move_f7f5 = Interpreter.uciStrToMove("f7f5", board);
		board.doUciMove("f7f5");
		expectedKey = 0x22a48b5a8e47ff78L;
		assertEquals(expectedKey, board.getKey());
		
////		position after e2e4 d7d5 e4e5 f7f5 e1e2
		int move_e1e2 = Interpreter.uciStrToMove("e1e2", board);
		board.doUciMove("e1e2");
		expectedKey = 0x652a607ca3f242c1L;
		assertEquals(expectedKey, board.getKey());
		
//		position after e2e4 d7d5 e4e5 f7f5 e1e2 e8f7
		int move_e8f7 = Interpreter.uciStrToMove("e8f7", board); 
		board.doUciMove("e8f7");
		expectedKey = 0x00fdd303c946bdd9L;
		assertEquals(expectedKey, board.getKey());
		

		board.undoMove(move_e8f7);
		assertEquals(0x652a607ca3f242c1L, board.getKey());
		
		board.undoMove(move_e1e2);
		assertEquals(0x22a48b5a8e47ff78L, board.getKey());
		
		board.undoMove(move_f7f5);
		assertEquals(0x662fafb965db29d4L, board.getKey());
		
		board.undoMove(move_e4e5);
		assertEquals(0x0756b94461c50fb0L, board.getKey());
		
		board.undoMove(move_d7d5);
		assertEquals( 0x823c9b50fd114196L, board.getKey());
	
		board.undoMove(move_e2e4);
		assertEquals( 0x463b96181691fc9cL, board.getKey());

	}

	
}
