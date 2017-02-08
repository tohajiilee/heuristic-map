package heuristicmap.model;

/*
 *	A model class for Heuristics - the entire purpose of this is to be used as a Heuristic calculator.
 *	@author Joel Carrillo (jjc372)
 */

import heuristicmap.model.Node;
import heuristicmap.model.Map;

public class Heuristic
{

	public double selectHeuristic(Node v1, Node v2, char heurIn){
		switch(heurIn){
			case 'a':
				return ManhattanSquares(v1, v2);
			case 'b':
				return EuclideanDistance(v1, v2);
			case 'c':
				return DiagDistance(v1, v2);
			case 'd':
				return Heur4(v1, v2);
			default:
				break;
		}
		return 0;
	}

	/*
	 * A heuristic that uses Manhattan Squares to determine the distance between the goal and a certain Node.
	 */

	public double ManhattanSquares(Node v1, Node v2){
		return (1/4) * (Math.abs(v1.getX() - v2.getX()) + Math.abs(v1.getY() - v2.getY()));
	}

	/*
	 * A heuristic that uses Euclidean distance to determine the distance between the goal and a certain Node.
	 */

	public double EuclideanDistance(Node v1, Node v2){
		double dx = Math.abs(v1.getX() - v2.getX());
		double dy = Math.abs(v1.getY() - v2.getY());
		return (1/4) * Math.sqrt(dx * dx + dy * dy);
	}

	public double DiagDistance(Node v1, Node v2){
		double dx = Math.abs(v1.getX() - v2.getX());
		double dy = Math.abs(v1.getY() - v2.getY());
		return (1/32) * (dx + dy) + ((Math.sqrt(2)/32) - 2 * (1/32)) * Math.min(dx, dy);
	}

	public double Heur4(Node v1, Node v2){
		double dx = Math.abs(v1.getX() - v2.getX());
		double dy = Math.abs(v1.getY() - v2.getY());
		return ((Math.max(dx, dy) - Math.min(dx, dy)));
	}
}