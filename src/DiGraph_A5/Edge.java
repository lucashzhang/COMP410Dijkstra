package DiGraph_A5;

public class Edge {

	private long _idNum;
	private String _eLabel;
	private long _weight;

	private String _sLabel;
	private String _dLabel;

	public Edge(long idNum, String eLabel, String sLabel, String dLabel, long weight) {

		setIdNum(idNum);
		setELabel(eLabel);
		setWeight(weight);
		setSLabel(sLabel);
		setDLabel(dLabel);
	}

	public Edge(long idNum, String eLabel, String sLabel, String dLabel) {
		//If weight is not specified, default to 1
		setIdNum(idNum);
		setELabel(eLabel);
		setWeight(1);
		setSLabel(sLabel);
		setDLabel(dLabel);
	}

	public long getWeight() {
		return _weight;
	}

	public void setWeight(long weight) {
		_weight = weight;
	}

	public long getIdNum() {
		return _idNum;
	}

	public void setIdNum(long idNum) {
		_idNum = idNum;
	}

	public String getELabel() {
		return _eLabel;
	}

	public void setELabel(String eLabel) {
		_eLabel = eLabel;
	}

	public String getSLabel() {
		return _sLabel;
	}

	public void setSLabel(String sLabel) {
		_sLabel = sLabel;
	}

	public String getDLabel() {
		return _dLabel;
	}

	public void setDLabel(String dLabel) {
		_dLabel = dLabel;
	}
}
