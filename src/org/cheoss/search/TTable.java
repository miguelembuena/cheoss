package org.cheoss.search;

import java.util.*;

import org.cheoss.board.*;
import org.cheoss.util.*;


public class TTable implements Constants {
	//private final static int HASH_SIZE = 2000003; // = 48,000072 Mb * 4 slots = 192 Mb total table
	//private final static int HASH_SIZE = 1500007; // = 48,000072 Mb * 4 slots = 192 Mb total table
	private final static int HASH_SIZE = 1500003; // = 48,000072 Mb * 4 slots = 192 Mb total table
	

	private TTEntry[][] slots = {
			new TTEntry[HASH_SIZE],
			new TTEntry[HASH_SIZE],
			new TTEntry[HASH_SIZE],
			new TTEntry[HASH_SIZE]
	};
	

	private int index(long key) {
		return Math.abs( (int) (key % HASH_SIZE) );
	}
	
	public int bestMoveForKey(long key) {
		int index = index(key); 
		
		for (TTEntry[] slot : slots) {
			if ( slot[index] != null && slot[index].getKey() == key ) {
				return slot[index].getBestMove();
			}					
		}
				
		return 0;
	}
	
	public Variation collectPV(Board board) {		
		ArrayList<Integer> moves = new ArrayList<Integer>();
		Variation pv = new Variation();
		int i = 0;
		for( ; i < pv.getMoves().length; i++) {
			int move = bestMoveForKey(board.getKey());
			if (move == 0) break;
			if ( moves.contains(move) ) break;
			moves.add(move);
			pv.getMoves()[i] = move;
			
			board.doMove(move);
		}
		
		for ( i = 72; i >=0; i--) {			
			int m = pv.getMoves()[i];
			if (m == 0) continue;
			board.undoMove(m);
		}
		
		return pv;	
	}
	
	private boolean isHit(TTEntry entry, long key) {
		return entry != null && key == entry.getKey(); 
	}

	public int primarys = 0;
	public int secondarys = 0;
	public int putys = 0;
	public void put(int depth, int value, int bestMove, int flag, long key) {
		int index = index(key);
		
//		Search up to 4 consecutive slots for a hit. If no hit, use the slot with the smallest draft 
		//(a never-written-to slot has draft 0). 
		

		int minDepth = -1;
		int slotFavorito = 0;
		// Busca en todos los slots buscando coincidencia
		for (int i = 0; i < slots.length; i++) {
			TTEntry entry = slots[i][index];
			if (entry == null) {
				slotFavorito = i;
				minDepth = -1;
			}
			else {
				if (entry.getDepth() < minDepth) {
					minDepth = entry.getDepth();
					slotFavorito = i;
				}
			}
			
			if (isHit(entry, key)) {
//				Then overwrite the slot unless 
//				(incoming_score_is_inexact && we_have_a_hit && draft_of_slot > depth_of_incoming_node).

				
				if (flag != HASH_EXACT && entry.getDepth() > depth) {
					return;
				}
				else {
					slots[i][index] = new TTEntry(depth, value, bestMove, flag, key);
					return;
				}
				
			}

		}
		
		slots[slotFavorito][index] = new TTEntry(depth, value, bestMove, flag, key);
		
		
		// Si no hay coincidencia en ningun slot, graba en el favorito para grabar
		// si queda entrada nula, o si no el de menor depth
	}

	
	public TTEntry probe(int depth, int alpha, int beta, long key) {
		int xindex = index(key);
		for (TTEntry[] slot : slots) {
			if ( slot[xindex] != null && slot[xindex].getKey() == key  && slot[xindex].getDepth() >= depth) {
				return slot[xindex];
			}					
		}
		
		return null;
	}

	
	private int matches = 0;

	
	

	public int getMatches() {
		return matches;
	}

}
