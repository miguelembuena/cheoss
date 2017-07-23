package org.cheoss.controller;

/**
 * 
 * @author Miguel Embuena
 *
 */
public class Clock {
	private long signalTime = 0l;
	private long timeTranscurred = 0l;
	private long baseTime = 0l;
	private long absoluteTime = 0l;
	private int baseMoves = 0;
	
	public String toString() {
		return "timeTranscurred="+timeTranscurred +", baseTime="+baseTime+", absoluteTime="+absoluteTime +
				", baseMoves="+baseMoves;
	}
	

		
	public void start() {
		signalTime = System.currentTimeMillis();						
	}
	
	public long partial() {
		return System.currentTimeMillis() - signalTime;
	}
	
	public void stop() {
		timeTranscurred += (System.currentTimeMillis() - signalTime);
		
	}
	
	public long timeForMove(int movesToGo, long time) {
    	if (absoluteTime > 0) {
    		return absoluteTime;
    	}
    	 
    	if (baseMoves == 0) {     
    		return timeForGameFinish(movesToGo);
    	}

		return (time/ (movesToGo+1) );
	}


    
    
    private int ctrlMove = 0;
    long midTime = 0l;
    private long timeForGameFinish(int moveNumber) {
    	int openCtrl = 8;
    	int midCtrl = openCtrl + 31;

    	if (ctrlMove < openCtrl ) {
    		// OPEN ctrMove=0 baseTime=180000 opentime=36000 return 2769
    		// OPEN ctrMove=1 baseTime=180000 opentime=36000 return 3000
    		//OPEN ctrMove=2 baseTime=180000 opentime=36000 return 3272
    		//...
    		//OPEN ctrMove=11 baseTime=180000 opentime=36000 return 18000 18000/2
    		
    		
        	//apertura, los 13 primeros movimientos. un 20% del tiempo total de partida
        	long openTime = ( (baseTime * 10)/100 );
        	int movesRemaining = openCtrl - ctrlMove;
        	
        	//System.out.println("OPEN ctrMove="+ctrlMove+" baseTime="+baseTime+" opentime="+openTime+" return "+((openTime - timeTranscurred) / movesRemaining)+" timeTrancurred="+timeTranscurred+" midTime ="+midTime);
        	ctrlMove++;
        	return (openTime - timeTranscurred) / movesRemaining;    		
    	}
    	// lo que queda es basetime - timetrancurred
    	
    	else if (ctrlMove < midCtrl) {
    		
    		if (ctrlMove == openCtrl) {
    			midTime = ( ( (baseTime-timeTranscurred) * 70)/100 );	
    		}
        	//medio juego, los x movimientos siguientes, un 80% del tiempo de lo que resta
        	int movesRemaining = midCtrl - ctrlMove;
        	System.out.println("MID ctrMove="+ctrlMove+" baseTime="+baseTime+" midtime="+midTime+" return "+((midTime - timeTranscurred) / movesRemaining)+" timeTrancurred="+timeTranscurred);
        	ctrlMove++;
        	return (midTime - timeTranscurred) / movesRemaining;    		
    	}
    	else {
    		return (baseTime - timeTranscurred) / 20;
    	}
    }
    
	public void setBaseTime(long baseTime) {
		System.out.println("Clock setBaseTime a "+baseTime);
		this.baseTime = baseTime;
	}

	public void setBaseMoves(int baseMoves) {
		this.baseMoves = baseMoves;
	}

	public void setAbsoluteTime(long absoluteTime) {
		this.absoluteTime = absoluteTime;
	}

	public long getTimeTranscurred() {
		return timeTranscurred;
	}

	public long getBaseTime() {
		return baseTime;
	}

    
}
