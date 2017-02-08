package heuristicmap.model;

import java.util.Comparator;
import java.util.PriorityQueue;

import heuristicmap.model.Map;
import heuristicmap.model.Heuristic;

public class Algorithm{
	Heuristic heuristic;
	int expansions;
	public Algorithm(Heuristic hIn){
		this.heuristic = hIn;
		expansions = 0;
	}
	public Node aStarSearch(Map currMap, char heurIn, double weight){
		Comparator<Node> comparator = new DistanceComparator();
		PriorityQueue<Node> fringe =
	            new PriorityQueue<Node>(10, comparator);
		currMap.getStart().setDistance(0 + heuristic.selectHeuristic(currMap.getStart(), currMap.getGoal(), heurIn));
		currMap.getStart().setParent(currMap.getStart());
		fringe.add(currMap.getStart());
		while(!fringe.isEmpty()){
			Node curr = fringe.poll();
			if(currMap.getGoal().equals(curr)){
				System.out.println("Expansions: " + expansions);
				System.out.println("Resulting Path Length: " + curr.getDistance());
				double endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				System.out.println("Memory used: " + endMem / 1024 + "kb");
				expansions = 0;
				return curr;
			}
			curr.setTraveled(true);
			expansions++;
			int currX = curr.getX(); int currY = curr.getY();
			for(int i = -1; i < 2; i++){
				for(int j = -1; j < 2; j++){
					if(currX + i > -1 && currX + i < 160
							&& currY + j > -1 && currY + j < 120){
						if(currMap.getMap()[currX + i][currY + j].getType() != '0')
							if(!(i == 0 && j == 0))
								if(!currMap.getMap()[currX + i][currY + j].getTraveled()){
									updateVertex(currMap, curr, currMap.getMap()[currX + i][currY + j], fringe, heurIn, weight);
								}
					}
				}
			}
		}
		System.out.println("Failed.");
		expansions = 0;
		return null;
	}

	public void updateVertex(Map currMap, Node v1, Node v2, PriorityQueue<Node> fringe, char heurIn, double weight){
		if(v1.getDistance() + (currMap.findPathDistance(v1, v2)) < v2.getDistance()){
			v2.setDistance(v1.getDistance() + (currMap.findPathDistance(v1, v2)));
			v2.setHVal((heuristic.selectHeuristic(v2, currMap.getGoal(), heurIn) * weight));
			v2.setFVal(v2.getDistance() + v2.getHVal());
			v2.setParent(v1);
			if(!fringe.isEmpty())
				if(fringe.peek().equals(v2))
					fringe.remove();
			fringe.add(v2);
		}
	}
}