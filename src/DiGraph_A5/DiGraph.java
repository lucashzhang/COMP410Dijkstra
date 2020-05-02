package DiGraph_A5;

import java.util.HashMap; // import the HashMap class
import java.util.PriorityQueue;

public class DiGraph implements DiGraphInterface {

	// in here go all your data and methods for the graph

	// nodes holds Node objects w/ labels as keys
	// nodeDict helps translate idNum to label
	private HashMap<String, Node> _nodes;
	private HashMap<Long, String> _nodeDict;

	private HashMap<Long, Edge> _edges;

	public DiGraph() { // default constructor

		_nodes = new HashMap<String, Node>();
		_nodeDict = new HashMap<Long, String>();

		_edges = new HashMap<Long, Edge>();
	}

	@Override
	public boolean addNode(long idNum, String label) {

		if (idNum < 0 || label == null) {
			return false;
		} else if (_validateNode(idNum) || _validateNode(label)) {
			return false;
		}
		
		_nodes.put(label, new Node(idNum, label));
		_nodeDict.put(idNum, label);

		return true;
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		
		//If negative edge weights aren't accepted, I blame Piazza where a TA/LA said that they shouldn't have special treatment
		
		if (idNum < 0) {
			return false;
		} else if (!_validateNode(sLabel) || !_validateNode(dLabel)) {
			//return false if either node doesn't exist
			return false;
		} else if (_validateEdge(idNum)) {
			//return false if idNum is not unique
			return false;
		} else if(_nodes.get(sLabel).getEdgesOut().containsKey(dLabel) ||
				_nodes.get(dLabel).getEdgesIn().containsKey(sLabel)) {
			//return false if edge already exists
			return false;
		}
		
		_edges.put(idNum, new Edge(idNum, eLabel, sLabel, dLabel, weight));
		_nodes.get(sLabel).getEdgesOut().put(dLabel, idNum);
		_nodes.get(dLabel).getEdgesIn().put(sLabel, idNum);

		return true;
	}
	
	public boolean addEdge(long idNum, String sLabel, String dLabel, String eLabel) {
		//If weight is not specified, default to 1
		return addEdge(idNum, sLabel, dLabel, 1, eLabel);
	}


	@Override
	public boolean delNode(String label) {
		
		if (!_validateNode(label)) {
			return false;
		}
		
		Node toRemove = _nodes.get(label);
		//remove all edges associated with the node
		_trimEdges(toRemove);
		//removes node from all data structures associated with it
		_nodeDict.remove(toRemove.getId());
		_nodes.remove(label);
		
		
		return true;
	}

	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		
		long edgeId = _findEdgeID(sLabel, dLabel);
		
		if (edgeId < 0) {
			//If the returned id is invalid, return false
			return false;
		}
		//Removes edge from all associated nodes
		_nodes.get(sLabel).getEdgesOut().remove(dLabel);
		_nodes.get(dLabel).getEdgesIn().remove(sLabel);
		//Removes edge from associated data structures
		_edges.remove(edgeId);
		
		return true;
	}

	@Override
	public long numNodes() {
		
		return _nodes.size();
	}

	@Override
	public long numEdges() {
		
		return _edges.size();
	}
	
	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		//Catch illegal starting node
		if (!_validateNode(label)) {
			throw new IllegalArgumentException("The starting node is not in the graph");
		}
		
		ShortestPathInfo[] shortestPaths = new ShortestPathInfo[(int) numNodes()];
		
		HashMap<String, Path> known = _dijkstra(label);
		
		int i = 0;
		long weight;
		String dest;
		for (Node node : _nodes.values()) {
			dest = node.getLabel();
			if (known.containsKey(dest)) {
				weight = known.get(dest).getWeight();
			} else {
				weight = -1;
			}
			shortestPaths[i] = new ShortestPathInfo(dest, weight);
			i++;
		}
		
		
		return shortestPaths;
	}
	
	//Own Methods
	
	public void clear() {
		//Clears the graph
		_nodes.clear();
		_nodeDict.clear();
		_edges.clear();
	}
	
	//Helper Methods

	private boolean _validateNode(Long idNum) {

		return _nodeDict.containsKey(idNum);
	}

	private boolean _validateNode(String label) {

		return _nodes.containsKey(label);
	}
	
	private boolean _validateEdge(Long idNum) {

		return _edges.containsKey(idNum);
	}
	
	private long _findEdgeID(String sLabel, String dLabel) {
		//Method returns -1 if either nodes or the edge cannot be found
		if (!_validateNode(sLabel) || !_validateNode(dLabel)) {
			return -1;
		}
		//Return id
		return _nodes.get(sLabel).getEdgesOut().getOrDefault(dLabel, (long) -1);
	}
	
	private void _trimEdges(Node sNode) {
		//for each other node in the label, remove the edges related to the source node
		for(String label : sNode.getEdgesOut().keySet()) {
			//Does not use HashSet removeall() due to apparent performance issues
			_edges.remove(sNode.getEdgesOut().get(label));
			_nodes.get(label).getEdgesIn().remove(sNode.getLabel());
		}
		//for each other node in the label, remove the edges related to the source node
		for(String label : sNode.getEdgesIn().keySet()) {
			_edges.remove(sNode.getEdgesIn().get(label));
			_nodes.get(label).getEdgesOut().remove(sNode.getLabel());
		}
	}
	
	private HashMap<String, Path> _dijkstra(String startLabel) {
		//This method calculates and returns all known shortest paths
		PriorityQueue<Path> candidates = new PriorityQueue<>(new PathComparator());
		HashMap<String, Path> known = new HashMap<>();
				
		Node currNode;
		String currLabel;
		candidates.add(new Path(startLabel, 0, startLabel));
		
		//The number of candidates has to be greater than 0
		//If the number of known nodes equals (or exceeds) the total number of nodes, stop
		while (candidates.size() > 0 && known.size() < _nodes.size()) {
			
			currLabel = candidates.peek().getLabel();
			currNode = _nodes.get(currLabel);
			
			//Skip process this node has already been visited before
			if (!currNode.isKnown()) {
				for (String label : currNode.getEdgesOut().keySet()) {
					if (!_nodes.get(label).isKnown()) {
						//If negative edge weights aren't accepted, I blame Piazza where a TA/LA said that they shouldn't have special treatment
						long dist = candidates.peek().getWeight() + _edges.get(currNode.getEdgesOut().get(label)).getWeight();
						candidates.add(new Path(label, dist, currLabel));
					}
				}
				
				currNode.flipKnown();
				known.put(candidates.peek().getLabel(), candidates.peek());
			}
			//Remove the head of the priority queue
			candidates.remove();
		}
		
		return known;
		
	}

}