package DiGraph_A5;

import java.util.Comparator;

public class PathComparator implements Comparator<Path> {

	@Override
	public int compare(Path arg0, Path arg1) {
		
		return (int) (arg0.getWeight() - arg1.getWeight());
	}

}
