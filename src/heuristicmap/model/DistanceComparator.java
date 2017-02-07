package heuristicmap.model;

import java.util.Comparator;
import heuristicmap.model.Node;

public class DistanceComparator implements Comparator<Node> {
	@Override
	public int compare(Node v1, Node v2) {
		if (v1.getFVal() < v2.getFVal()) {
			return -1;
		} else if (v1.getFVal() > v2.getFVal()) {
			return 1;
		} else {
			if (v1.getDistance() < v2.getDistance()) {
				return -1;
			} else if (v1.getDistance() > v2.getDistance()) {
				return 1;
			}
		}
		return 0;
	}
}