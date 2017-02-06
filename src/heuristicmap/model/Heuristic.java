package heuristicmap.model;

import heuristicmap.model.Node;
import heuristicmap.model.Map;

public class Heuristic
{
	private Map map;
	private Node start;
	private Node goal;

	public Heuristic(Map m){
		this.map = m;
		this.start = m.getStart();
		this.goal = m.getGoal();
	}

	public double selectHeuristic(Node v1, Node v2, char heurIn){
		switch(heurIn){
			case 'a':
				return ManhattanSquares(v1, v2);
			case 'b':
				return PythagDistance(v1, v2);
			default:
				break;
		}
		return 0;
	}

	/*
	 * A heuristic that uses Manhattan Squares to determine the distance between the goal and a certain Node.
	 */

	public double ManhattanSquares(Node v1, Node v2){
		return(Math.abs(v2.getX() - v1.getX()) + Math.abs(v2.getY() - v1.getY()));
	}

	/*
	 * A heuristic that uses Euclidean distance to determine the distance between the goal and a certain Node.
	 */

	public double PythagDistance(Node v1, Node v2){
		return(Math.sqrt(Math.pow((v2.getX() - v1.getX()), 2) + Math.pow((v2.getY() - v1.getY()), 2)));
	}
}