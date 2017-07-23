package org.cheoss.tests;

import org.cheoss.util.*;

import junit.framework.*;


public class TestAuxMVVLVA extends TestCase implements Constants {


	private int valor(int piece) {
		if ( !XBoard.isKing(piece) ) return valueOf[piece];
		
		return valueOf[WQUEEN] + 60; // es rey
	}
	
	public void testAux() {
		for (int victim = WPAWN; victim <= BKING; victim+=2) {
			System.out.println("********* pieza comida = "+victim);
			for (int atacker = WPAWN; atacker <= BKING; atacker+=2) {
				//value of captured piece - small percentage of capturing piece				
				//int value = valueOf[victim] - valueOf[atacker]/17;
				int value = valor(victim) - valor(atacker) / 17;
				//19998 => 127
				// valor => x
				// x = valor * 127 / 19998
				//972 peon come rey
				value = (value * 127) / 972;
				System.out.println("         Victim = "+victim+" Attacker = "+atacker+" value = "+value);
			}
		}
		
		
	}

	
 }
