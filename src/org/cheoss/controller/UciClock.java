package org.cheoss.controller;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class UciClock {
	
	//go wtime 120000 btime 119913 winc 0 binc 0 movestogo 38
	private long wtime;
	private long btime;
	private long winc;
	private long binc;
	private int movesToGo;
	
	
	public long getWtime() {
		return wtime;
	}
	public void setWtime(long wtime) {
		this.wtime = wtime;
	}
	public long getBtime() {
		return btime;
	}
	public void setBtime(long btime) {
		this.btime = btime;
	}
	public long getWinc() {
		return winc;
	}
	public void setWinc(long winc) {
		this.winc = winc;
	}
	public long getBinc() {
		return binc;
	}
	public void setBinc(long binc) {
		this.binc = binc;
	}
	public int getMovesToGo() {
		return movesToGo;
	}
	public void setMovesToGo(int movesToGo) {
		this.movesToGo = movesToGo;
	}

}
