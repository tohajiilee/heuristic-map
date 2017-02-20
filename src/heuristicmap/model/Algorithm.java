package heuristicmap.model;

import java.util.Comparator;
import java.util.PriorityQueue;

import heuristicmap.model.Map;
import heuristicmap.model.Heuristic;

public class Algorithm{

	private PriorityQueue<Vertex> fringe;
	private double weight;
	Heuristic heuristic;
	int expansions;
	int currHeuristic;

	public Algorithm(Heuristic hIn){
		this.heuristic = hIn;
		expansions = 0;
		currHeuristic = 0;
		weight = 1;
	}
	public Vertex aStarSearch(Map currMap){
		Comparator<Vertex> comparator = new DistanceComparator();
		fringe = new PriorityQueue<Vertex>(10, comparator);
		currMap.getStart().setFVal(heuristic.selectHeuristic(currMap.getStart(), currMap.getGoal(), currHeuristic), 0);
		currMap.getStart().setGVal(0, 0);
		currMap.getStart().setParent(currMap.getStart(), 0);
		fringe.add(currMap.getStart());
		while(!fringe.isEmpty()){
			Vertex curr = fringe.poll();
			if(currMap.getGoal().equals(curr)){
				curr.setExpansions(expansions);
				double endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				curr.setMemUsed(endMem / 1024);
				expansions = 0;
				return curr;
			}
			curr.setTraveled(true, 0);
			expansions++;
			int currX = curr.getX(); int currY = curr.getY();
			for(int i = -1; i < 2; i++){
				for(int j = -1; j < 2; j++){
					if(currX + i > -1 && currX + i < 160
							&& currY + j > -1 && currY + j < 120){
						if(currMap.getMap()[currX + i][currY + j].getType() != '0')
							if(!(i == 0 && j == 0))
								if(!currMap.getMap()[currX + i][currY + j].getTraveled(0)){
									updateVertex(currMap, curr, currMap.getMap()[currX + i][currY + j]);
								}
					}
				}
			}
		}
		System.out.println("Failed.");
		expansions = 0;
		return null;
	}

	public void updateVertex(Map currMap, Vertex v1, Vertex v2){
		if(v1.getGVal(0) + (currMap.findPathDistance(v1, v2)) < v2.getGVal(0)){
			v2.setGVal(v1.getGVal(0) + (currMap.findPathDistance(v1, v2)), 0);
			v2.setHVal((heuristic.selectHeuristic(v2, currMap.getGoal(), currHeuristic) * weight), 0);
			v2.setFVal(v2.getGVal(0) + v2.getHVal(0), 0);
			v2.setParent(v1, 0);
			if(fringe.contains(v2))
					fringe.remove(v2);
			fringe.add(v2);
		}
	}

	public void setWeight(double wIn){
		weight = wIn;
	}

	public double getWeight(){
		return weight;
	}

	public void setHeuristic(int hIn){
		currHeuristic = hIn;
	}

	public int getHeuristic(){
		return currHeuristic;
	}
}