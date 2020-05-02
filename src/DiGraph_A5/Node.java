package DiGraph_A5;

import java.util.HashMap;

public class Node {
	
	private long _id;
	private String _label;
	
	//Split edges in and edges out
	private HashMap<String, Long> _edgesIn;
	private HashMap<String, Long> _edgesOut;
	
	private boolean _known;
	
	public Node(long id, String label) {
		
		setId(id);
		setLabel(label);
		_edgesIn = new HashMap<String, Long>();
		_edgesOut = new HashMap<String, Long>();
		//For Dijkstra's Algorithm
		_known = false;
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public HashMap<String, Long> getEdgesIn() {
		return _edgesIn;
	}
	
	public void putEdgesIn(String label, long id) {
		_edgesIn.put(label, id);
	}
	
	public HashMap<String, Long> getEdgesOut() {
		return _edgesOut;
	}
	
	public void putEdgesOut(String label, long id) {
		_edgesOut.put(label, id);
	}
	
	public boolean isKnown() {
		return _known;
	}
	
	public void flipKnown() {
		_known = !_known;
	}

}
