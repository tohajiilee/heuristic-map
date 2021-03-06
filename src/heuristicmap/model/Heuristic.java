package heuristicmap.model;

/*
 *	A model class for Heuristics - the entire purpose of this is to be used as a Heuristic calculator.
 *	@author Joel Carrillo (jjc372)
 */

import heuristicmap.model.Vertex;
import heuristicmap.model.Map;

public class Heuristic
{

	public double selectHeuristic(Vertex v1, Vertex v2, int heurIn){
		switch(heurIn){
			case 1:
				return DiagDerivative(v1, v2);
			case 2:
				return EuclideanDistance(v1, v2);
			case 3:
				return DiagDistance(v1, v2);
			case 4:
				return ManhattanSquares(v1, v2);
			case 5:
				return ChebDistance(v1, v2);
			default:
				break;
		}
		return 0;
	}

	/*
	 * A heuristic that uses Manhattan Squares to determine the distance between the goal and a certain Node.
	 */

	public double ManhattanSquares(Vertex v1, Vertex v2){
		double dx = Math.abs(v1.getX() - v2.getX());
		double dy = Math.abs(v1.getY() - v2.getY());
		return (.25) * (dx + dy);
	}

	/*
	 * A heuristic that uses Euclidean distance to determine the distance between the goal and a certain Node.
	 */

	public double EuclideanDistance(Vertex v1, Vertex v2){
		double dx = Math.abs(v1.getX() - v2.getX());
		double dy = Math.abs(v1.getY() - v2.getY());
		return (.25) * Math.sqrt(dx * dx + dy * dy);
	}

	public double DiagDistance(Vertex v1, Vertex v2){
		double dx = Math.abs(v1.getX() - v2.getX());
		double dy = Math.abs(v1.getY() - v2.getY());
		return (.25) * (dx + dy) + ((Math.sqrt(2) * .25) - 2 * (.25)) * Math.min(dx, dy);
	}

	public double DiagDerivative(Vertex v1, Vertex v2){
		double dx = Math.abs(v1.getX() - v2.getX());
		double dy = Math.abs(v1.getY() - v2.getY());
		return (.25 * Math.min(dx, dy)) + (Math.sqrt(2) * .25 * (Math.max(dx, dy) - Math.min(dx, dy)));
	}

	public double ChebDistance(Vertex v1, Vertex v2){
		double dx = Math.abs(v1.getX() - v2.getX());
		double dy = Math.abs(v1.getY() - v2.getY());
		return Math.max(dx, dy);
	}
}