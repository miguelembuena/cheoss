package org.cheoss.util;

import org.cheoss.board.*;

public interface Constants {
	public final static int END_GAME_LIMIT = 43800;
	public final static int MAX_DEPTH = 64;
	public static final boolean NULL_ALLOWED = true;
	public static final boolean NO_NULL = false;
	public static final int INVALID = 173127;
	public static final int INFINITY = 99999;
	public static final int MAX_MOVES_IN_STACK = 147;
  	public static final int OUT = 0;
  	public static final int WPAWN = 1;
  	public static final int BPAWN = 2;
  	public static final int WKNIGHT = 3;
  	public static final int BKNIGHT = 4;
  	public static final int WBISHOP = 5;
  	public static final int BBISHOP = 6;
  	public static final int WROOK = 7;
  	public static final int BROOK = 8;
  	public static final int WQUEEN = 9;
  	public static final int BQUEEN = 10;
  	public static final int WKING = 11;
  	public static final int BKING = 12;
  	public static final int EMPTY = 13;  	
  	
    public final static int PAWN_VALUE = 100;
    public final static int KNIGHT_VALUE = 326;
    public final static int BISHOP_VALUE = 334;   
    public final static int ROOK_VALUE = 504; //ok
    public final static int QUEEN_VALUE = 917; //ok
    public final static int KING_VALUE = 20000; //shanon ;)
    public static final int[] valueOf = {0, PAWN_VALUE, PAWN_VALUE, KNIGHT_VALUE, KNIGHT_VALUE, 
    	BISHOP_VALUE, BISHOP_VALUE, ROOK_VALUE, ROOK_VALUE, 
    	QUEEN_VALUE, QUEEN_VALUE,KING_VALUE,KING_VALUE };

  	
  	//special movements flags
  	public static int FLAG_NO_FLAG = 0;
  	public static int FLAG_PAWN_JUMP = 1;
  	public static int FLAG_EN_PASSANT_CAPTURE = 2;
  	public static int FLAG_CASTLING = 3;
  	//public static int FLAG_PROMOTION = 4; // no usado para asignar, se usa para distinguir
  	public static int FLAG_PROMO_QUEEN = 4;
  	public static int FLAG_PROMO_ROOK = 5;
  	public static int FLAG_PROMO_BISHOP = 6;
  	public static int FLAG_PROMO_KNIGHT = 7;
  	
  	public static final int WHITE_SHORT_CASTLING = XMove.pack(114, 116, WKING, 0, FLAG_CASTLING);
  	public static final int WHITE_LONG_CASTLING = XMove.pack(114, 112, WKING, 0, FLAG_CASTLING);
  	public static final int BLACK_SHORT_CASTLING = XMove.pack(30, 32, BKING, 0, FLAG_CASTLING);
  	public static final int BLACK_LONG_CASTLING = XMove.pack(30, 28, BKING, 0, FLAG_CASTLING);
  	
	public static final int[] knightOffsets = { 10, -10, 14, -14, 23, -23, 25, -25 };
	public static final int[] kingOffsets = {13, -13, 11, -11, 12, -12, 1, -1};
	public static final int rookOffsets[] = {12, -12, 1, -1};
	// el orden de los bishops offsets debe ser asi, porque
	//isAttacked asume que está así
	public static final int bishopOffsets[] = {11, -11, 13, -13};
  	public static final boolean WHITE = true;
  	public static final boolean BLACK = false;    


  	public static final int STALEMATE = 101;
	public static final int WHITE_MATED = 102;
	public static final int BLACK_MATED = 103;
	public static final int DRAW_BY_REP = 104;

 
  	public static final String FEN_START = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
  	public static final int[] NUMERIC_START = {
	        OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,
	        OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,
	        OUT    ,OUT    ,BROOK  ,BKNIGHT,BBISHOP,BQUEEN ,BKING  ,BBISHOP,BKNIGHT,BROOK  ,OUT    ,OUT    ,
	        OUT    ,OUT    ,BPAWN  ,BPAWN  ,BPAWN  ,BPAWN  ,BPAWN  ,BPAWN  ,BPAWN  ,BPAWN  ,OUT    ,OUT    ,
	        OUT    ,OUT    ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,OUT    ,OUT    ,
	        OUT    ,OUT    ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,OUT    ,OUT    ,
	        OUT    ,OUT    ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,OUT    ,OUT    ,
	        OUT    ,OUT    ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,EMPTY  ,OUT    ,OUT    ,
/*96-107*/ OUT    ,OUT    ,WPAWN  ,WPAWN  ,WPAWN  ,WPAWN  ,WPAWN  ,WPAWN  ,WPAWN  ,WPAWN  ,OUT    ,OUT    ,
/*108-119*/OUT    ,OUT    ,WROOK  ,WKNIGHT,WBISHOP,WQUEEN ,WKING  ,WBISHOP,WKNIGHT,WROOK  ,OUT    ,OUT    ,
	        OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,
	        OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    ,OUT    
	  	};
  	
  	
  	public static final int COL_a = 2;
  	public static final int COL_b = 3;
  	public static final int COL_c = 4;
  	public static final int COL_d = 5;
  	public static final int COL_e = 6;
  	public static final int COL_f = 7;
  	public static final int COL_g = 8;
  	public static final int COL_h = 9;

  	public static final int a1 = 110;
  	public static final int b1 = 111;
  	public static final int c1 = 112;
  	public static final int d1 = 113;
  	public static final int e1 = 114;
  	public static final int f1 = 115;
  	public static final int g1 = 116;
  	public static final int h1 = 117;
  	
  	public static final int a2 = 98;
  	public static final int b2 = 99;
  	public static final int c2 = 100;
  	public static final int d2 = 101;
  	public static final int e2 = 102;
  	public static final int f2 = 103;
  	public static final int g2 = 104;
  	public static final int h2 = 105;
  	
  	public static final int a3 = 86;
  	public static final int b3 = 87;
  	public static final int c3 = 88;
  	public static final int d3 = 89;
  	public static final int e3 = 90;
  	public static final int f3 = 91;
  	public static final int g3 = 92;
  	public static final int h3 = 93;

  	public static final int a4 = 74;
  	public static final int b4 = 75;
  	public static final int c4 = 76;
  	public static final int d4 = 77;
  	public static final int e4 = 78;
  	public static final int f4 = 79;
  	public static final int g4 = 80;
  	public static final int h4 = 81;
  	
  	public static final int a5 = 62;
  	public static final int b5 = 63;
  	public static final int c5 = 64;
  	public static final int d5 = 65;
  	public static final int e5 = 66;
  	public static final int f5 = 67;
  	public static final int g5 = 68;
  	public static final int h5 = 69;

  	public static final int a6 = 50;
  	public static final int b6 = 51;
  	public static final int c6 = 52;
  	public static final int d6 = 53;
  	public static final int e6 = 54;
  	public static final int f6 = 55;
  	public static final int g6 = 56;
  	public static final int h6 = 57;
  	
  	public static final int a7 = 38;
  	public static final int b7 = 39;
  	public static final int c7 = 40;
  	public static final int d7 = 41;
  	public static final int e7 = 42;
  	public static final int f7 = 43;
  	public static final int g7 = 44;
  	public static final int h7 = 45;

  	public static final int a8 = 26;
  	public static final int b8 = 27;
  	public static final int c8 = 28;
  	public static final int d8 = 29;
  	public static final int e8 = 30;
  	public static final int f8 = 31;
  	public static final int g8 = 32;
  	public static final int h8 = 33;
  	
  	// Hash flags
  	public static int HASH_EXACT = 0;
  	public static int HASH_ALPHA = 1;
  	public static int HASH_BETA = 2;

}
