package org.cheoss.search;

import org.cheoss.util.*;

public class TTEntry implements Constants {
	private long key; //64 bits
	private int depth; // 32 bits
	private int flag; // 32 bits
	private int value; // 32 bits
	private int bestMove; // 32 bits
	
	//192 bits cada entry que son 24 bytes creo
	//ok, estimando 24 bytes por entry, una tabla
	// de 2 millones de posiciones necesitaria:
	// 48.000.000 bytes 48 millones de bytes unos 45 Mb de memoria creo
	// probemos con esos valores
	
	// 1 kb = 1.024 bytes
	// 1 mb = 1.048.576 bytes
	
  	public TTEntry(int depth, int value, int bestMove, int flag, long key) {  		
  		this.bestMove = bestMove;
  		this.depth = depth;
  		this.flag = flag;
  		this.key = key;
  		this.value = value;
  	}

	
	
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public long getKey() {
		return key;
	}
	public void setKey(long key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}



	public int getBestMove() {
		return bestMove;
	}



	public void setBestMove(int bestMove) {
		this.bestMove = bestMove;
	}

}
