package DiGraph_A5;

public class DiGraphPlayground {

  public static void main (String[] args) {
  
      // thorough testing is your responsibility
      //
      // you may wish to create methods like 
      //    -- print
      //    -- sort
      //    -- random fill
      //    -- etc.
      // in order to convince yourself your code is producing
      // the correct behavior
	  
      //exTest();
      //efficiencyTest();
	  dijkstraTest();
    }
  
    public static void exTest(){
      DiGraph d = new DiGraph();
      d.addNode(1, "f");
      d.addNode(3, "s");
      d.addNode(7, "t");
      d.addNode(0, "fo");
      d.addNode(4, "fi");
      d.addNode(6, "si");
      d.addEdge(0, "f", "s", 0, null);
      d.addEdge(1, "f", "si", 0, null);
      d.addEdge(2, "s", "t", 0, null);
      d.addEdge(3, "fo", "fi", 0, null);
      d.addEdge(4, "fi", "si", 0, null);
      System.out.println(d.delEdge("s", "t"));
      System.out.println("numEdges: "+d.numEdges());
      System.out.println("numNodes: "+d.numNodes());
    }
    
    public static void efficiencyTest() {
    	
    	DiGraph d = new DiGraph();
    	String label = "";
    	
    	//Create 1000000 nodes and 999999 edges
    	for (int i = 0; i < 1000000; i++) {
    		label += i;
    		d.addNode(i, label);
    		//Connect all nodes to first node
    		if (i > 0) {
    			d.addEdge(i, "0", label, null);
    		}
    		label = "";
    	}
    	System.out.println("numEdges: "+d.numEdges());
        System.out.println("numNodes: "+d.numNodes());
        //Delete the node the edges are connected to
        d.delNode("0");
        System.out.println("numEdges: "+d.numEdges());
        System.out.println("numNodes: "+d.numNodes());
    }
    
    public static DiGraph createGraph(int numNodes) {
    	
    	//Creates a semi-realistic graph where one can definitely travel between adjacent nodes.
    	//Also adds "Fast tracks" between random nodes to serve as "highways" between nodes.
    	
    	//All nodes should be visitable and therefore have a known weight returned by the algorithm
    	
    	//Can be scaled up to test efficiency
    	
    	DiGraph d = new DiGraph();
    	String label = "";
    	String sLabel = "";
    	String dLabel = "";
    	long weight = 1;
    	
    	//Create 1 million nodes
    	for (int i = 0; i < numNodes; i++) {
    		label += i;
    		d.addNode(i, label);
    		label = "";
    	}
    	
    	//Create <(2 * numNode) edges (Overlapping edges won't actually go in)
    	for (int i = 0; i < numNodes; i++) {
    		//Connect each node to it's adjacent w/ random weight
    		sLabel += i;
    		dLabel += (i + 1) % numNodes;
    		//Weight is in the range 1-10
    		weight= ((long) (Math.random() * 9) + 1);
    		d.addEdge(i, sLabel, dLabel, weight, null);
    		sLabel = "";
    		dLabel = "";
    		
    		//Create a random edge between a random source and destination w/ random weight
    		sLabel += ((int) (Math.random() * (numNodes - 1)));
    		dLabel += ((int) (Math.random() * (numNodes - 1)));
    		//Weight is in the range 1-20
    		weight= ((long) (Math.random() * 19) + 1);
    		d.addEdge(i + numNodes, sLabel, dLabel, weight, null);
    		sLabel = "";
    		dLabel = "";
    	}
    	
    	return d;
    }
    
    public static void dijkstraTest() {
    	
    	DiGraph d = createGraph(10); //Change to 1000000 for efficiency testing
    	ShortestPathInfo[] shortestPaths = d.shortestPath("0");
    	long count = 0;
    	
    	for (ShortestPathInfo path : shortestPaths) {
    		if (path.getTotalWeight() >= 0) {
    			count++;
    		}
    		System.out.println("Dest: " + path.getDest() + "    Weight: " + path.getTotalWeight());
    	}
    	
    	System.out.println("Total Destinations: " + shortestPaths.length);
    	System.out.println("Known Destinations: " + count);
    }
}