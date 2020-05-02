package DiGraph_A5;

public class Path {
	
	private String _label;
	private long _weight;
	private String _from;
	private boolean _known;
	
	public Path(String label, long weight, String through) {
		
		setLabel(label);
		setWeight(weight);
		setThrough(through);
		_known = false;
	}

	public String getLabel() {
		return _label;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public long getWeight() {
		return _weight;
	}

	public void setWeight(long weight) {
		_weight = weight;
	}

	public String getThrough() {
		return _from;
	}

	public void setThrough(String through) {
		_from = through;
	}

}
