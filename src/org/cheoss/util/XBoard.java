package org.cheoss.util;


public class XBoard implements Constants {
	
	public static int distance(int sq1, int sq2) {
		return Math.max(Math.abs((row(sq1) - row(sq2))), Math.abs(column(sq1)-column(sq2)));
	}
	
	public static boolean isInCentre(int sq) {
		if (sq == e4 || sq == e5 || sq == d4 || sq == d5 ||
		sq == f4 || sq == f5 || sq == c4 || sq == c5 ) return true; 
		
		return false;
	}
	
	public static int getPromoForFlag(int flag, boolean whitePlay) {
		switch (flag) {
		case FLAG_PROMO_QUEEN:
			if (whitePlay) return WQUEEN;
			else return BQUEEN;
		case FLAG_PROMO_ROOK:
			if (whitePlay) return WROOK;
			else return BROOK;
		case FLAG_PROMO_BISHOP:
			if (whitePlay) return WBISHOP;
			else return BBISHOP;
		case FLAG_PROMO_KNIGHT:
			if (whitePlay) return WKNIGHT;
			else return BKNIGHT;			
		}

		return 0;
	}
	
	public static boolean isPromoRank(int sq, boolean color) {
		if (color) return sq/12 == 3;
		return sq/12 == 8;
	}
	
  	public static int pgnRow(int square) {
  	    return 10-(square/12);	    
  	}
  	
  	public static int column(int sq) {
  		return sq%12;
  	}
  	
  	
  	public static int row(int sq) {
  		return sq/12;
  	}
  	
  	public static boolean shareColumn(int sq1, int sq2) {
  		return (sq1%12) == (sq2%12);
  	}
  	
  	public static boolean shareRow(int sq1, int sq2) {
  		return (sq1 / 12) == (sq2 / 12); 
  	}
  	
  	
  	public static boolean shareDiagonals(int sq1, int sq2) {
  		int modulo1 = sq1%12;
  		int row1 = sq1/12;
  		int modulo2 = sq2%12;
  		int row2 = sq2/12;
  		
  		if (modulo1 - row1 == modulo2 - row2) return true;
  		if (modulo1 + row1 == modulo2 + row2) return true;
  		
  		return false;
  	}


	public static String pgnColumn(int square) throws Exception {
	    String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h"};
	    int column = (square%12)-2;
	    return columns[column];
	}

	public static final String coordForSquare(int square) {
		try {
			return pgnColumn(square)+pgnRow(square);	
		}
		catch(Exception ex) {
			return null;
		}
	    
	}
	
  	public static final int squareForCoord(String coord) {
  		int row = Integer.parseInt( coord.substring(1) );
  		switch(coord.charAt(0)) {
  		case 'a': 
  			return 110 - ((row-1)*12);
  		case 'b': 
  			return 111 - ((row-1)*12);
  		case 'c': 
  			return 112 - ((row-1)*12);
  		case 'd': 
  			return 113 - ((row-1)*12);
  		case 'e': 
  			return 114 - ((row-1)*12);
  		case 'f': 
  			return 115 - ((row-1)*12);
  		case 'g': 
  			return 116 - ((row-1)*12);
  		case 'h': 
  			return 117 - ((row-1)*12);
  		default:
  			return 0;
  		}
  	}


    public final static boolean isPawn(int piece) {
    	return piece == WPAWN || piece == BPAWN;
    }
    
    public final static boolean isKing(int piece) {
    	return piece == WKING || piece == BKING;
    }
    public final static boolean isRook(int piece) {
    	return piece == WROOK || piece == BROOK;
    }
    public final static boolean isKnight(int piece) {
    	return piece == WKNIGHT || piece == BKNIGHT;
    }
    public final static boolean isBishop(int piece) {
    	return piece == WBISHOP || piece == BBISHOP;
    }
    public final static boolean isQueen(int piece) {
    	return piece == WQUEEN || piece == BQUEEN;
    }
    

    public static int getNW(int sq) {
    	return sq - 13;
    }
    public static int getN(int sq) {
    	return sq - 12;
    }
    public static int getNE(int sq) {
    	return sq - 11;
    }
    
    public static int getSW(int sq) {
    	return sq + 11;
    }
    public static int getS(int sq) {
    	return sq + 12;
    }
    public static int getSE(int sq) {
    	return sq + 13;
    }




}
