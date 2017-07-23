package org.cheoss.tests;

import junit.framework.*;

import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

public class TestDebug extends TestCase implements Constants {
	
	
//	public void testDebug() {
//		//fine 70 pos testing TT
//		// always replace if depth mayor 
////		info score cp 215 depth 20 nodes 3465584 pv a1b1 a7b7 b1c1 b7c7 c1d1 c7d7 d1c2 d7e7 c2b3 e7f6 b3c4 f6g6 c4b5 g6h5 b5a5 h5g4 a5b6 g4f4 b6c7 f4e3 
////		time = 19828
//		//always replace incondicional
////		info score cp 168 depth 20 nodes 1458 pv a1b2 a7a8 b2c3 a8b7 c3d2 b7c8 d2c3 c8b7 
////		time = 297, sí, 297 milisegundos
////		String fen = "8/k7/3p4/p2P1p2/P2P1P2/8/8/K7 w - - 0 1";
//
//		// hilo depuracion TT
//		//http://talkchess.com/forum/viewtopic.php?t=37419&postdays=0&postorder=asc&highlight=transposition&topic_view=flat&start=30
//		
//		// otra posicion de test c6c7
//		//String fen = "2k5/8/1pP1K3/1P6/8/8/8/8 w - -";
//		
//		Board board = new Board(FEN_START);
//		Node node = new Node(board);
//		long start = System.currentTimeMillis();
//		node.searchToDepth(8);
//		System.out.println("time = "+(System.currentTimeMillis() - start));
//	}
	
	//tiempo = 9766
	// 89391 depth 10
	//85937 d 10
	//83703 d10
	//81125 d10 si iid en root
	//83265 sin 
	//41781 con R=3 y si >6 R=4
	//tiempo = 39406 con R=3 y si >6 R=4
	//tiempo = 46734  con R=3 y si >5 R=4
	//tiempo = 46469  con R=3 y si >7 R=4
	//tiempo = 46266  con R=3 y si >8 R=4
	//         1368236 a la 9
	//		   1277522
	public void testNPS() {
		//depth 5 => 1.004.465 nps
		//1.020.904
		//depth 6 => 1.002.478 nps
		//1.111.083
		//1.131.547
		//28.000.000
		//4.821.432
		//4.865.629
		
//		int R = 3;
//		if (depth > 6) R = 4;
//		if (depth > 8) R = 5;
//		   ===> depth = 9 nodes = 1277522 EFB = 5
//		   info score cp 15 depth 10 nodes 1454029 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 f3e5 b4c3 d2c3 f6e4 
//		      ===> depth = 10 nodes = 3987001 EFB = 3
//		   tiempo = 39172
		
		
//		int R = 3;
//		if (depth > 6) R = 4;
//		if (depth > 8) R = 5;
//		if (depth > 9) R = 6;

//		info score cp 10 depth 8 nodes 141315 pv e2e4 b8c6 g1f3 g8f6 b1c3 d7d5 e4e5 f6g4 
//		   ===> depth = 8 nodes = 213200 EFB = 2
//		info score cp 30 depth 9 nodes 580466 pv e2e4 e7e5 g1f3 g8f6 b1c3 b8c6 f1b5 f8b4 e1g1 
//		   ===> depth = 9 nodes = 1277522 EFB = 5
//		info score cp 15 depth 10 nodes 1454029 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 f3e5 b4c3 d2c3 f6e4 
//		   ===> depth = 10 nodes = 3987001 EFB = 3
//		tiempo = 38781
		

		//experimentando
//		info score cp 10 depth 8 nodes 247422 pv e2e4 b8c6 g1f3 g8f6 b1c3 d7d5 e4e5 f6g4 
//		   ===> depth = 8 nodes = 351230 EFB = 5
//		info score cp 30 depth 9 nodes 525496 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 f1b5 b4c3 d2c3 
//		   ===> depth = 9 nodes = 3126109 EFB = 8
//		info score cp 15 depth 10 nodes 2436844 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 f3e5 b4c3 d2c3 f6e4 
//		   ===> depth = 10 nodes = 5913569 EFB = 1
//		tiempo = 79344
		
		//null siempre
//		   ===> depth = 7 nodes = 58480 EFB = 9
//		   info score cp 10 depth 8 nodes 134548 pv e2e4 b8c6 g1f3 g8f6 b1c3 d7d5 e4e5 f6g4 
//		      ===> depth = 8 nodes = 176159 EFB = 3
//		   info score cp 30 depth 9 nodes 468530 pv e2e4 e7e5 g1f3 g8f6 b1c3 b8c6 f1b5 f8b4 e1g1 
//		      ===> depth = 9 nodes = 1046943 EFB = 5
//		   info score cp 15 depth 10 nodes 1157491 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 f3e5 b4c3 d2c3 f6e4 
//		      ===> depth = 10 nodes = 3256444 EFB = 3
//		   tiempo = 33843
		
		
//		info score cp 143 depth 9 nodes 472590 pv d2c3 d5c3 f5d6 b7f3 d3a6 f8d8 g1f2 f3d1 e1e3 
//		info score cp 508 depth 9 nodes 589725 pv d2h6 c3e1 d3f1 e1e3 f5e3 d5e3 h6e3 b6b5 h2h4 
//		   ===> depth = 9 nodes = 593525 EFB = 5
//		tiempo = 6922 matches = 14342
//xx		primarys = 59801 secondarys = 101 total = 59902
		
//		info score cp 143 depth 8 nodes 64008 pv d2c3 d5c3 f5d6 b7f3 d3a6 f3d1 b4b5 h7h5 
//		   ===> depth = 8 nodes = 107224 EFB = 0
//		info score cp 143 depth 9 nodes 472080 pv d2c3 d5c3 f5d6 b7f3 d3a6 f8d8 g1f2 f3d1 e1e3 
//		info score cp 508 depth 9 nodes 589574 pv d2h6 c3e1 d3f1 e1e3 f5e3 d5e3 h6e3 b6b5 h2h4 
//		   ===> depth = 9 nodes = 593373 EFB = 5
//		tiempo = 6906 matches = 14355
		
		String pos1 = "5rk1/1b3p1p/pp3p2/3n1N2/1P6/P1qB1PP1/3Q3P/4R1K1 w - -";
		String posFine70 = "8/k7/3p4/p2P1p2/P2P1P2/8/8/K7 w - - 0 1";
		
		// correct move (b4b7)
		String posx = "3Q4/5q1k/4ppp1/2Kp1N1B/RR6/3P1r2/4nP1b/3b4 w - - ";
		
//		info score cp 559 depth 10 nodes 2853467 pv h5f3 e2f4 f5d6 f7g7 b4f4 h2f4 a4f4 d1f3 f4f3 f6f5 
//		   ===> depth = 10 nodes = 3336489 EFB = 10
//		info score cp 569 depth 11 nodes 3435845 pv h5f3 e2f4 f5d6 f7g7 b4f4 h2f4 f3d1 g6g5 d6e8 g7f8 c5d4 
//		   ===> depth = 11 nodes = 4426447 EFB = 1
//		tiempo1 = 49813 matches = 229945
//		ttmatches = 229945 misses = 1270130 percentMatches = 15%
		
//		nfo depth 9 seldepth 24 time 11156 nodes 638186 pv h5f3 e2f4 f5d6 f7g7 b4f4 h2f4 a4f4 d1f3 f4f3 nps 57205 score cp 600 hashfull 108 tbhits 0 
//		info depth 10 seldepth 22 time 7063 nodes 411899 pv h5f3 e2f4 f5d6 f7g7 b4f4 h2f4 f3d1 f4d6 d8d6 g7f7 nps 58317 score cp 600 hashfull 164 tbhits 0 
//		info depth 11 seldepth 25 time 91734 nodes 13189595 pv h5f3 e2f4 f5d6 f7g7 b4f4h2f4 f3d1 g6g5 d3d4 f4d6 d8d6 nps 143780 score cp 601 hashfull 710 tbhits 0 
		
		Board board = new Board(FEN_START);
		System.out.println(""+board.getMaterialWhite());
		//String fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1";
		//board = new Board(fen);
		
		long start = System.currentTimeMillis();
		Tree tree = new Tree(board);
		tree.searchToDepth(10);
		System.out.println("tiempo1 = "+(System.currentTimeMillis()-start)+" matches = "+tree.ttMatches);
		int total = tree.ttMatches + tree.misses;
		int percentMatches = (tree.ttMatches * 100)/total;		
		System.out.println("ttmatches = "+tree.ttMatches+" misses = "+tree.misses+" percentMatches = "+percentMatches+"%");
				

//		
//		board = new Board(pos1);
//		tree = new Tree(board);
//		tree.searchToDepth(9);
//		System.out.println("tiempo = "+(System.currentTimeMillis()-start)+" matches = "+tree.ttMatches);
//		System.out.println("primarys = "+tree.getTtable().primarys+" secondarys = "+tree.getTtable().secondarys+
//				" total = "+(tree.getTtable().primarys+tree.getTtable().secondarys)+" putys = "+tree.getTtable().putys);
//		
		
		
	}
	
}
