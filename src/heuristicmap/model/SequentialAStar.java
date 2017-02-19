package heuristicmap.model;

import heuristicmap.model.MultiFringe;
import heuristicmap.view.MainMenuController;

public class SequentialAStar{
	MultiFringe fringe;
	Heuristic heuristic;
	int expansions;
	Map currMap;

	public SequentialAStar(Map mapIn){
		fringe = new MultiFringe();
		heuristic = new Heuristic();
		expansions = 0;
		currMap = MainMenuController.currentMap;
	}

	public void expandState(Vertex v, int i, double w1){
		v.setTraveled(true, i);
		expansions++;
		for(int j = -1; j < 2; j++){
			for(int l = -1; l < 2; l++){
				if(!(j == 0 && l == 0))
				if(v.getX() + j < currMap.columns && v.getX() + j >= 0 &&
					v.getY() + l < currMap.rows && v.getY() + l >= 0)
				if(currMap.getMap()[v.getX() + j][v.getY() + l].getType() != '0'){
					Vertex vn = currMap.getMap()[v.getX() + j][v.getY() + l];
					if(vn.getX() == currMap.getGoal().getX() && vn.getY() == currMap.getGoal().getY()){
						currMap.setGoal(vn);
						currMap.getGoal().currH = i;
						vn.setParent(v, i);
					}
					if(vn.getGVal(i) > (v.getGVal(i) + currMap.findPathDistance(v,vn))){
						vn.setGVal(v.getGVal(i) + currMap.findPathDistance(v, vn), i);
						vn.setParent(v, i);
						if(!vn.getTraveled(i)){
							vn.setHVal((heuristic.selectHeuristic(vn, currMap.getGoal(), i + 1) * w1), i);
							vn.setFVal((vn.getGVal(i) + vn.getHVal(i)), i);
							fringe.add(i, vn);
						}
					}
				}
			}
		}
	}

	public Vertex seqASearch(double w1, double w2){
		for(int i = 0; i < 5; i++){
			currMap.getStart().setGVal(0, i);
			currMap.getStart().setHVal((heuristic.selectHeuristic(currMap.getStart(), currMap.getGoal(), i + 1) * w1), i);
			currMap.getStart().setFVal((currMap.getStart().getGVal(i) + currMap.getStart().getHVal(i)), i);
			fringe.add(i, currMap.getStart());
		}
		while(fringe.hmap[0].get(1).getGVal(0) < 32767){
			for(int i = 0; i < 5; i++){
				if(fringe.hmap[i].get(1).getFVal(i) <= (w2 * fringe.hmap[0].get(1).getFVal(0))){
					if(currMap.getGoal().getGVal(i) <= fringe.hmap[i].get(1).getFVal(i)){
						if (currMap.getGoal().getGVal(i) < 32767){
							giveStats(currMap);
							return currMap.getGoal();
						}
					}
					else{
						Vertex v = fringe.hmap[i].get(1);
						fringe.remove(i);
						expandState(v, i, w1);
					}
				}
				else{
					if(currMap.getGoal().getGVal(0) <= fringe.hmap[0].get(1).getFVal(0)){
						if(currMap.getGoal().getGVal(0) < 32767){
							giveStats(currMap);
							return currMap.getGoal();
						}
					}
					else{
						Vertex v = fringe.hmap[0].get(1);
						fringe.remove(0);
						expandState(v, 0, w1);
					}
				}
			}
		}
		System.out.println("Failed.\n");
		return null;
	}

	public void giveStats(Map currMap){
		System.out.println("Expansions: " + expansions);
		System.out.println("Resulting Path Cost: " + currMap.getGoal().getGVal(currMap.getGoal().getCurrH()));
		double endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println("Memory used: " + endMem / 1024 + "kb");
	}
}