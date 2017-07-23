package org.cheoss.util;

public class PerftResults {
	private int perftNodes = 0;
	private int perftEps = 0;
	private int perftCaptures = 0;
	private int perftChecks = 0;
	private int perftCastlings = 0;
	private int perftPromos = 0;
	
	
	public PerftResults() {
		
	}
	
	public PerftResults(int perftNodes, int perftEps, int perftCaptures, int perftChecks,
			int perftCastlings, int perftPromos) {
		this.perftNodes = perftNodes;
		this.perftCaptures = perftCaptures;
		this.perftCastlings = perftCastlings;
		this.perftChecks = perftChecks;
		this.perftEps = perftEps;
		this.perftPromos = perftPromos;
	}
	
	public String toString() {
		return "          nodes: "+perftNodes+" captures = "+perftCaptures+
		" eps = "+perftEps+" checks = "+perftChecks+" promos = "+perftPromos;
	}

	public int getPerftNodes() {
		return perftNodes;
	}

	public int getPerftEps() {
		return perftEps;
	}

	public int getPerftCaptures() {
		return perftCaptures;
	}

	public int getPerftChecks() {
		return perftChecks;
	}

	public int getPerftCastlings() {
		return perftCastlings;
	}

	public int getPerftPromos() {
		return perftPromos;
	}

}
