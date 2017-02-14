package heuristicmap.model;

import java.util.Comparator;
import heuristicmap.model.Vertex;

public class DistanceComparator implements Comparator<Vertex> {
	// Compares f values between two vertices - the tie breaker is the distance from the vertex to the start.
	// Otherwise, return 0.
	@Override
	public int compare(Vertex v1, Vertex v2) {
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