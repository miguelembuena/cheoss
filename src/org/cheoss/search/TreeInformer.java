package org.cheoss.search;

public class TreeInformer {
	public int inPV = 0;
	public int outPV = 0;
	public int failPV = 0;
	public int newNodes = 0;
	public int PVMoveFound = 0;
	
	
	public void notifyInPV() {
		inPV++;
	}
	
	public void notifyOutOfPV() {
		outPV++;
	}
	
	public void notifyFailPV() {
		failPV++;
	}
	
	
	public void notifyNewNode() {
		newNodes++;
	}

}
