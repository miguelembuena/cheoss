package org.cheoss.evaluation;

/**
 * 
 * @author Miguel Embuena
 *
 */
public interface IConstantsEval {
	public final static int BONUS_PAIR_OF_BISHOPS_END = 17;
	//public final static int PENALTY_NO_PAWNS_IN_ENDGAME = 100;
	
	
	//public static final int EVAL_KING_OKUPA = 10000;
	//public static final int EVAL_KING_ATTACKER = 100;
	
	public static final int EVAL_DOUBLED_PAWN = 33;
//	public static final int EVAL_DOUBLED_PAWN = 43;
	public static final int EVAL_ISOLATED_PAWN = 25;
//	public static final int EVAL_ISOLATED_PAWN = 37;
	public static final int END_EVAL_DOUBLED_PAWN = 37;
	public static final int END_EVAL_ISOLATED_PAWN = 33;
//	public static final int END_EVAL_DOUBLED_PAWN = 53;
//	public static final int END_EVAL_ISOLATED_PAWN = 43;

	//public int eval();
	

    
///////////////////////////// Piece/Square Tables
    
    //////////////////////// Pawns
    // from http://chessprogramming.wikispaces.com/Simplified+evaluation+function
    public final static int[] WHITE_PAWN_BONUS = {
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
//              a   b   c   d   e   f   g   h     	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //8
    	0,  0, 50, 50, 50, 50, 50, 50, 50, 50,  0,  0,   //7//
    	0,  0, 10, 10, 20, 30, 30, 20, 10, 10,  0,  0,   //6//
    	0,  0,  5,  5, 10, 25, 25, 10,  5,  5,  0,  0,   //5//
    	0,  0,  0,  0,  0, 20, 20,  0,  0,  0,  0,  0,   //4//
    	0,  0,  5, -5,-10,  0,  0,-10, -5,  5,  0,  0,   //3//
    	0,  0,  5, 10, 10,-20,-20, 10, 10,  5,  0,  0,   //2//
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //1
//              a   b   c   d   e   f   g   h    	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    };
    public final static int[] BLACK_PAWN_BONUS = {
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
//              a   b   c   d   e   f   g   h
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //1
    	0,  0,  5, 10, 10,-20,-20, 10, 10,  5,  0,  0,   //2//
    	0,  0,  5, -5,-10,  0,  0,-10, -5,  5,  0,  0,   //3//
    	0,  0,  0,  0,  0, 20, 20,  0,  0,  0,  0,  0,   //4//
    	0,  0,  5,  5, 10, 25, 25, 10,  5,  5,  0,  0,   //5//
    	0,  0, 10, 10, 20, 30, 30, 20, 10, 10,  0,  0,   //6//
    	0,  0, 50, 50, 50, 50, 50, 50, 50, 50,  0,  0,   //7//
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //8
//              a   b   c   d   e   f   g   h    	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    };
    
    public final static int[] END_WHITE_PAWN_BONUS = {
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
//              a   b   c   d   e   f   g   h     	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //8
    	0,  0,200,200,200,200,200,200,200,200,  0,  0,   //7//
    	0,  0, 80, 80, 80, 80, 80, 80, 80, 80,  0,  0,   //6//
    	0,  0, 60, 60, 60, 60, 60, 60, 60, 60,  0,  0,   //5//
    	0,  0, 20, 20, 20, 20, 20, 20, 20, 20,  0,  0,   //4//
    	0,  0,  5,  5,  5,  5,  5,  5,  5,  5,  0,  0,   //3/////////
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //2
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //1
//              a   b   c   d   e   f   g   h    	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    };
    public final static int[] END_BLACK_PAWN_BONUS = {
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
//              a   b   c   d   e   f   g   h
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //1
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //2
    	0,  0,  5,  5,  5,  5,  5,  5,  5,  5,  0,  0,   //3/////////
    	0,  0, 20, 20, 20, 20, 20, 20, 20, 20,  0,  0,   //4//
    	0,  0, 60, 60, 60, 60, 60, 60, 60, 60,  0,  0,   //5//
    	0,  0, 80, 80, 80, 80, 80, 80, 80, 80,  0,  0,   //6//
    	0,  0,200,200,200,200,200,200,200,200,  0,  0,   //7//
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //8
//              a   b   c   d   e   f   g   h    	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    };

    //////////////////////// End Pawns
    
    
    
    //////////////////////// Knights    
 // knight
//    -50,-40,-30,-30,-30,-30,-40,-50,
//    -40,-20,  0,  0,  0,  0,-20,-40,
//    -30,  0, 10, 15, 15, 10,  0,-30,
    
//    -30,  5, 15, 20, 20, 15,  5,-30,
//    -30,  0, 15, 20, 20, 15,  0,-30,
//    -30,  5, 10, 15, 15, 10,  5,-30,
//    -40,-20,  0,  5,  5,  0,-20,-40,
//    -50,-40,-30,-30,-30,-30,-40,-50,
    public static final int[] WHITE_KNIGHT_BONUS = {
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
//              a   b   c   d   e   f   g   h    	
    	0,0,   -50,-40,-30,-30,-30,-30,-40,-50,   0,0,
    	0,0,   -40,-20,  0,  0,  0,  0,-20,-40,   0,0,              	        
    	0,0,   -30,  0, 10, 15, 15, 10,  0,-30,   0,0,
    	0,0,   -30,  5, 15, 20, 20, 15,  5,-30,   0,0,
    	0,0,   -30,  0, 15, 20, 20, 15,  0,-30,   0,0,
    	0,0,   -30,  5, 10, 15, 15, 10,  5,-30,   0,0,              	        
    	0,0,   -40,-20,  0,  5,  5,  0,-20,-40,   0,0,
    	0,0,   -50,-40,-30,-30,-30,-30,-40,-50,   0,0,
//      		a   b   c   d   e   f   g   h    	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0
    	
    };
    
    public static final int[] BLACK_KNIGHT_BONUS = {
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
//              a   b   c   d   e   f   g   h    	
    	
    	0,0,   -50,-40,-30,-30,-30,-30,-40,-50,   0,0,
    	0,0,   -40,-20,  0,  5,  5,  0,-20,-40,   0,0,              	        
    	0,0,   -30,  5, 10, 15, 15, 10,  5,-30,   0,0,
    	0,0,   -30,  0, 15, 20, 20, 15,  0,-30,   0,0,
    	0,0,   -30,  5, 15, 20, 20, 15,  5,-30,   0,0,
    	0,0,   -30,  0, 10, 15, 15, 10,  0,-30,   0,0,              	        
    	0,0,   -40,-20,  0,  0,  0,  0,-20,-40,   0,0,
    	0,0,   -50,-40,-30,-30,-30,-30,-40,-50,   0,0,
//				a   b   c   d   e   f   g   h    	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0
    	
    };

    
 // bishop
//    -20,-10,-10,-10,-10,-10,-10,-20,
//    -10,  0,  0,  0,  0,  0,  0,-10,
//    -10,  0,  5, 10, 10,  5,  0,-10,
//    -10,  5,  5, 10, 10,  5,  5,-10,
//    -10,  0, 10, 10, 10, 10,  0,-10,
//    -10, 10, 10, 10, 10, 10, 10,-10,
//    -10,  5,  0,  0,  0,  0,  5,-10,
//    -20,-10,-10,-10,-10,-10,-10,-20,
    public static final int[] WHITE_BISHOP_BONUS = {
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,  	        
        0,0,-20,-10,-10,-10,-10,-10,-10,-20, 0,0,
        0,0,-10,  0,  0,  0,  0,  0,  0,-10, 0,0,
        0,0,-10,  0,  5, 10, 10,  5,  0,-10, 0,0,
        0,0,-10,  5,  5, 10, 10,  5,  5,-10, 0,0,  
        0,0,-10,  0, 10, 10, 10, 10,  0,-10, 0,0,
        0,0,-10, 10, 10, 10, 10, 10, 10,-10, 0,0,
        0,0,-10,  5,  0,  0,  0,  0,  5,-10, 0,0,
        0,0,-20,-10,-10,-10,-10,-10,-10,-20, 0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0   	        
      	};

    public static final int[] BLACK_BISHOP_BONUS = {
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,  	        
        	0,0,-20,-10,-10,-10,-10,-10,-10,-20, 0,0,
        	0,0,-10,  5,  0,  0,  0,  0,  5,-10, 0,0,
        	0,0,-10, 10, 10, 10, 10, 10, 10,-10, 0,0,
        	0,0,-10,  0, 10, 10, 10, 10,  0,-10, 0,0,  
        	0,0,-10,  5,  5, 10, 10,  5,  5,-10, 0,0,  
        	0,0,-10,  0,  5, 10, 10,  5,  0,-10, 0,0,
        	0,0,-10,  0,  0,  0,  0,  0,  0,-10, 0,0,
        	0,0,-20,-10,-10,-10,-10,-10,-10,-20, 0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0   	        
      	};
    
    
//    rook
//    0,  0,  0,  0,  0,  0,  0,  0,
//    5, 10, 10, 10, 10, 10, 10,  5,
//   -5,  0,  0,  0,  0,  0,  0, -5,
//   -5,  0,  0,  0,  0,  0,  0, -5,
//   -5,  0,  0,  0,  0,  0,  0, -5,
//   -5,  0,  0,  0,  0,  0,  0, -5,
//   -5,  0,  0,  0,  0,  0,  0, -5,
//    0,  0,  0,  5,  5,  0,  0,  0
    public final static int[] WHITE_ROOK_BONUS = {
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
//              a   b   c   d   e   f   g   h
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //1
    	0,  0,  5, 10, 10, 10, 10, 10, 10,  5,  0,  0,   //2
    	0,  0, -5,  0,  0,  0,  0,  0,  0, -5,  0,  0,   //3//
    	0,  0, -5,  0,  0,  0,  0,  0,  0, -5,  0,  0,   //4//
    	0,  0, -5,  0,  0,  0,  0,  0,  0, -5,  0,  0,   //5//
    	0,  0, -5,  0,  0,  0,  0,  0,  0, -5,  0,  0,   //6//
    	0,  0, -5,  0,  0,  0,  0,  0,  0, -5,  0,  0,   //7//
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //8
//              a   b   c   d   e   f   g   h    	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    };
    
    public final static int[] BLACK_ROOK_BONUS = {
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
//              a   b   c   d   e   f   g   h
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //1
    	0,  0, -5,  0,  0,  0,  0,  0,  0, -5,  0,  0,   //2//
    	0,  0, -5,  0,  0,  0,  0,  0,  0, -5,  0,  0,   //3//
    	0,  0, -5,  0,  0,  0,  0,  0,  0, -5,  0,  0,   //4//
    	0,  0, -5,  0,  0,  0,  0,  0,  0, -5,  0,  0,   //5//
    	0,  0, -5,  0,  0,  0,  0,  0,  0, -5,  0,  0,   //6//
    	0,  0,  5, 10, 10, 10, 10, 10, 10,  5,  0,  0,   //7
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //8
//              a   b   c   d   e   f   g   h    	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    };


  //queen
//    -20,-10,-10, -5, -5,-10,-10,-20,
//    -10,  0,  0,  0,  0,  0,  0,-10,
//    -10,  0,  5,  5,  5,  5,  0,-10,
//     -5,  0,  5,  5,  5,  5,  0, -5,
//      0,  0,  5,  5,  5,  5,  0, -5,
//    -10,  5,  5,  5,  5,  5,  0,-10,
//    -10,  0,  5,  0,  0,  0,  0,-10,
//    -20,-10,-10, -5, -5,-10,-10,-20
    public final static int[] WHITE_QUEEN_BONUS = {
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
//              a   b   c   d   e   f   g   h
    	0,  0, -20,-10,-10, -5, -5,-10,-10,-20,  0,  0,   //1
    	0,  0, -10,  0,  0,  0,  0,  0,  0,-10,  0,  0,   //2//
    	0,  0, -10,  0,  5,  5,  5,  5,  0,-10,  0,  0,   //3//
    	0,  0, -5,  0,  5,  5,  5,  5,  0, -5,  0,  0,   //4//
    	0,  0,  0,  0,  5,  5,  5,  5,  0, -5,  0,  0,   //5//
    	0,  0, -10,  5,  5,  5,  5,  5,  0,-10,  0,  0,   //6//
    	0,  0, -20,-10,-10, -5, -5,-10,-10,-20,  0,  0,   //7
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //8
//              a   b   c   d   e   f   g   h    	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    };

    public final static int[] BLACK_QUEEN_BONUS = {
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
//              a   b   c   d   e   f   g   h
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,   //8//////////
    	0,  0, -20,-10,-10, -5, -5,-10,-10,-20,  0,  0,   //7//////////
    	0,  0, -10,  5,  5,  5,  5,  5,  0,-10,  0,  0,   //6/////////////
    	0,  0,  0,  0,  5,  5,  5,  5,  0, -5,  0,  0,   //5//////////////
    	0,  0, -5,  0,  5,  5,  5,  5,  0, -5,  0,  0,   //4//////////////////
    	0,  0, -10,  0,  5,  5,  5,  5,  0,-10,  0,  0,   //3////////////////
    	0,  0, -10,  0,  0,  0,  0,  0,  0,-10,  0,  0,   //2//////////////
    	0,  0, -20,-10,-10, -5, -5,-10,-10,-20,  0,  0,   //1////////////
//              a   b   c   d   e   f   g   h    	
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  
    };
    
    

	public static final int[] BLACK_KING_BONUS = {
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
        
        0,0,20, 30, 10,  0,  0, 10, 30, 20, 0,0,
        0,0,20, 20,  0,  0,  0,  0, 20, 20, 0,0,
        0,0,-10,-20,-20,-20,-20,-20,-20,-10, 0,0,
        0,0,-20,-30,-30,-40,-40,-30,-30,-20, 0,0,  	        
        0,0,-30,-40,-40,-50,-50,-40,-40,-30, 0,0,
        0,0,-30,-40,-40,-50,-50,-40,-40,-30, 0,0,
        0,0,-30,-40,-40,-50,-50,-40,-40,-30, 0,0,
        0,0,-30,-40,-40,-50,-50,-40,-40,-30, 0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0   	          	        
};
	
	public static final int[] WHITE_KING_BONUS = {
      0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
      0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,  	          	        
      0,0,-30,-40,-40,-50,-50,-40,-40,-30, 0,0,
      0,0,-30,-40,-40,-50,-50,-40,-40,-30, 0,0,
      0,0,-30,-40,-40,-50,-50,-40,-40,-30, 0,0,
      0,0,-30,-40,-40,-50,-50,-40,-40,-30, 0,0,
      0,0,-20,-30,-30,-40,-40,-30,-30,-20, 0,0,
      0,0,-10,-20,-20,-20,-20,-20,-20,-10, 0,0,
      0,0,20, 20,  0,  0,  0,  0, 20, 20, 0,0,
      0,0,20, 30, 10,  0,  0, 10, 30, 20, 0,0,
       0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
       0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0   	        	         
};
	
	public static final int[] END_WHITE_KING_BONUS = {
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,   	        		
	  0,0,0,  10,  20,  30,  30,  20,  10,   0, 0,0,//
	  0,0,10,  20,  30,  40,  40,  30,  20,  10, 0,0,
	  0,0,20,  30,  40,  50,  50,  40,  30,  20, 0,0,
	  0,0,30,  40,  50,  60,  60,  50,  40,  30, 0,0,
	  0,0,30,  40,  50,  60,  60,  50,  40,  30, 0,0,
	  0,0,20,  30,  40,  50,  50,  40,  30,  20, 0,0,
	  0,0,10,  20,  30,  40,  40,  30,  20,  10, 0,0,
	  0,0,0,  10,  20,  30,  30,  20,  10,   0, 0,0,	  
      0,0, 0,  0,  0,  0,  0,  0,  0,  0, 0,0,
      0,0, 0,  0,  0,  0,  0,  0,  0,  0, 0,0   	        
	  
};

	public static final int[] END_BLACK_KING_BONUS = {
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
        0,0,0,  0,  0,  0,  0,  0,  0,  0,0,0,
        0,0,0,  10,  20,  30,  30,  20,  10,   0, 0,0,
        0,0,10,  20,  30,  40,  40,  30,  20,  10, 0,0,
        0,0,20,  30,  40,  50,  50,  40,  30,  20, 0,0,
        0,0,30,  40,  50,  60,  60,  50,  40,  30, 0,0,//
        0,0,30,  40,  50,  60,  60,  50,  40,  30, 0,0,//
        0,0,20,  30,  40,  50,  50,  40,  30,  20, 0,0,//
        0,0,10,  20,  30,  40,  40,  30,  20,  10, 0,0,//
        0,0,0,  10,  20,  30,  30,  20,  10,   0, 0,0,//
      0,0, 0,  0,  0,  0,  0,  0,  0,  0, 0,0,
      0,0, 0,  0,  0,  0,  0,  0,  0,  0, 0,0   	        

};
	
    public static final int[][] bonusOf = {
    	null, 
    	WHITE_PAWN_BONUS, 
    	BLACK_PAWN_BONUS,
    	WHITE_KNIGHT_BONUS,
    	BLACK_KNIGHT_BONUS,
    	WHITE_BISHOP_BONUS,
    	BLACK_BISHOP_BONUS,
    	WHITE_ROOK_BONUS,  //new int[126],
    	BLACK_ROOK_BONUS,  //new int[126],
    	WHITE_QUEEN_BONUS, //new int[126],
    	BLACK_QUEEN_BONUS, //new int[126],    	    	
    	WHITE_KING_BONUS,
    	BLACK_KING_BONUS
   	};
    
    
    public static final int[][] endBonusOf = {
        	null, 
        	END_WHITE_PAWN_BONUS, 
        	END_BLACK_PAWN_BONUS,
        	new int[144],
        	new int[144],
        	new int[144],
        	new int[144],
        	new int[144],  
        	new int[144],  
        	new int[144], 
        	new int[144],     	    	
        	END_WHITE_KING_BONUS,
        	END_BLACK_KING_BONUS,
       	};


}