package org.cheoss.tests;

import junit.framework.*;
import org.cheoss.board.*;
import org.cheoss.search.*;
import org.cheoss.util.*;

public class TestInformer extends TestCase implements Constants {
	
	
//	   ===> depth = 6 nodes = 10531 EFB = 6
//	   info score cp 20 depth 7 nodes 8751 pv b1c3 b8c6 g1f3 g8f6 e2e4 d7d5 d2d3 
//	   info score cp 25 depth 7 nodes 53215 pv e2e4 b8c6 g1f3 g8f6 e4e5 f6d5 d2d4 
//	      ===> depth = 7 nodes = 58341 EFB = 5
//	   info score cp 10 depth 8 nodes 136129 pv e2e4 b8c6 g1f3 g8f6 b1c3 d7d5 e4e5 f6g4 
//	      ===> depth = 8 nodes = 189840 EFB = 3
//	   info score cp 30 depth 9 nodes 708405 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 f1b5 b4c3 d2c3 
//	      ===> depth = 9 nodes = 1405454 EFB = 7
//	   info score cp 15 depth 10 nodes 1782412 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 f3e5 b4c3 d2c3 f6e4 
//	      ===> depth = 10 nodes = 4261043 EFB = 3
//	   in PV = 0
//	   out PV = 24530
//	   fail PV = 0
//	   PV Move found = 24530
//	   nodes = 530.955
	
//	info score cp 0 depth 6 nodes 7796 pv b1c3 b8c6 g1f3 g8f6 e2e4 e7e5 
//	   ===> depth = 6 nodes = 25578 EFB = 3
//	info score cp 20 depth 7 nodes 31171 pv b1c3 b8c6 g1f3 g8f6 e2e4 
//	info score cp 25 depth 7 nodes 243031 pv e2e4 b8c6 g1f3 g8f6 e4e5 f6d5 d2d4 
//	   ===> depth = 7 nodes = 261001 EFB = 10
//	info score cp 10 depth 8 nodes 502707 pv e2e4 b8c6 g1f3 g8f6 b1c3 d7d5 
//	   ===> depth = 8 nodes = 808444 EFB = 3
//	info score cp 30 depth 9 nodes 2172568 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 
//	   ===> depth = 9 nodes = 6316388 EFB = 7
//	info score cp 15 depth 10 nodes 7602703 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 
//	   ===> depth = 10 nodes = 22187580 EFB = 3
//	in PV = 34842
//	out PV = 20710
//	fail PV = 0
//	PV Move found = 55552
//	nodes = 4.688.846
	
//	info score cp 0 depth 6 nodes 7796 pv b1c3 b8c6 g1f3 g8f6 e2e4 e7e5 
//	   ===> depth = 6 nodes = 25578 EFB = 3
//	info score cp 20 depth 7 nodes 31912 pv b1c3 b8c6 g1f3 g8f6 e2e4 d7d5 d2d3 
//	info score cp 25 depth 7 nodes 252593 pv e2e4 b8c6 g1f3 g8f6 e4e5 f6d5 d2d4 
//	   ===> depth = 7 nodes = 255433 EFB = 9
//	info score cp 10 depth 8 nodes 554743 pv e2e4 b8c6 g1f3 g8f6 b1c3 d7d5 e4e5 f6g4 
//	   ===> depth = 8 nodes = 892496 EFB = 3
//	info score cp 30 depth 9 nodes 2515025 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 f1b5 b4c3 d2c3 
//	   ===> depth = 9 nodes = 6660700 EFB = 7
//	info score cp 15 depth 10 nodes 8717031 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 f3e5 b4c3 d2c3 f6e4 
//	   ===> depth = 10 nodes = 23382880 EFB = 3
//	in PV = 31058
//	out PV = 150520
//	fail PV = 0
//	PV Move found = 181578
//	nodes = 3577780
	
//	info score cp 0 depth 6 nodes 14679 pv b1c3 b8c6 g1f3 g8f6 e2e4 e7e5 
//	   ===> depth = 6 nodes = 32572 EFB = 4
//	info score cp 20 depth 7 nodes 38637 pv b1c3 b8c6 g1f3 g8f6 e2e4 d7d5 d2d3 
//	info score cp 25 depth 7 nodes 199496 pv e2e4 b8c6 g1f3 g8f6 e4e5 f6d5 d2d4 
//	   ===> depth = 7 nodes = 258763 EFB = 7
//	info score cp 10 depth 8 nodes 726813 pv e2e4 b8c6 g1f3 g8f6 b1c3 d7d5 e4e5 f6g4 
//	   ===> depth = 8 nodes = 1068470 EFB = 4
//	info score cp 30 depth 9 nodes 3128316 pv e2e4 e7e5 g1f3 g8f6 b1c3 b8c6 
//	   ===> depth = 9 nodes = 6157015 EFB = 5
//	info score cp 15 depth 10 nodes 8784231 pv e2e4 e7e5 g1f3 g8f6 b1c3 f8b4 f3e5 
//	   ===> depth = 10 nodes = 24122078 EFB = 3
//	in PV = 27327
//	out PV = 94276
//	fail PV = 0
//	PV Move found = 121603
//	nodes = 3374016
	
	public void testNPS() {
		
		Board board = new Board(FEN_START);		
		Tree tree = new Tree(board);
		tree.searchToDepth(10);
		TreeInformer treeInformer = tree.getTreeInformer();
		
		System.out.println("in PV = "+treeInformer.inPV);
		System.out.println("out PV = "+treeInformer.outPV);
		System.out.println("fail PV = "+treeInformer.failPV);
		System.out.println("PV Move found = "+treeInformer.PVMoveFound);
		System.out.println("nodes = "+treeInformer.newNodes);
		
//		board.doUciMove("e2e4");
//		tree.searchToDepth(6);
//		treeInformer = tree.getTreeInformer();
//		System.out.println("        ***               ");
//		System.out.println("in PV = "+treeInformer.inPV);
//		System.out.println("out PV = "+treeInformer.outPV);
//		System.out.println("fail PV = "+treeInformer.failPV);
//		System.out.println("PV Move found = "+treeInformer.PVMoveFound);
//		System.out.println("nodes = "+treeInformer.newNodes);
//		
//		
//		board = new Board("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
//		tree.searchToDepth(6);
//		treeInformer = tree.getTreeInformer();
//		System.out.println("        ***               ");
//		System.out.println("in PV = "+treeInformer.inPV);
//		System.out.println("out PV = "+treeInformer.outPV);
//		System.out.println("fail PV = "+treeInformer.failPV);
//		System.out.println("PV Move found = "+treeInformer.PVMoveFound);
//		System.out.println("nodes = "+treeInformer.newNodes);



		
	}
	
}
