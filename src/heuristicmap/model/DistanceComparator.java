package heuristicmap.model;

import java.util.Comparator;
import heuristicmap.model.Node;

public class DistanceComparator implements Comparator<Node> {
	// Compares f values between two vertices - the tie breaker is the distance from the vertex to the start.
	// Otherwise, return 0.
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