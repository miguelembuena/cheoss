package org.cheoss.board;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class XBoardState {
	
	private static final int MASK = 127; // 7 bits
	
	public static int getEnPassant(int packed) {
		// >> 11 = 7 de fiftyMoves + los 4 cs rights
		return (packed >> 11) & MASK;
	}
	
	public static int getFiftyMoves(int packed) {
		// >> los 4 cs rights
		return (packed >> 4) & MASK;
	}
	
	public static int getCsWhiteShort(int packed) {
		// >> los 3 cs rights restantes
		return ((packed >> 3) & 1);
	}
	public static int getCsWhiteLong(int packed) {
		// >> los 2 cs rights restantes
		return ((packed >> 2) & 1);
	}
	public static int getCsBlackShort(int packed) {
		// >> queda 1 cs rights restante
		return ((packed >> 1) & 1);
	}
	public static int getCsBlackLong(int packed) {
		// el ultimo, no hay desplazamiento
		return (packed & 1);
	}

	public static int pack(int enPassant, int fiftyMoves, int csWhiteShort, 
			int csWhiteLong, int csBlackShort, int csBlackLong){
		// de a6 = 50 hasta h3 = 93, 7 bits 		 		
		// fiftyMoves de 0 a 50, 6 bits				
		// castlings, 1 bit cada uno
		
		return (((((((((((0 ^ enPassant) << 7) ^ fiftyMoves) << 1) 
				^ csWhiteShort) << 1) ^ csWhiteLong) << 1) ^ csBlackShort) << 1) ^ csBlackLong);
    }	
	
}
