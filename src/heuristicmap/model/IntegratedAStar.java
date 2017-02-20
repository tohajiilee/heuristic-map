package heuristicmap.model;

import heuristicmap.model.MultiFringe;
import heuristicmap.view.MainMenuController;

public class IntegratedAStar{
	MultiFringe fringe;
	Heuristic heuristic;
	int expansions;
	Map currMap;

	public IntegratedAStar(Map mapIn){
		fringe = new MultiFringe();
		heuristic = new Heuristic();
		expansions = 0;
		currMap = MainMenuController.currentMap;
	}

	public void expandState(Vertex v, int i, double w1, double w2){
		expansions++;
		for(int j = -1; j < 2; j++){
			for(int l = -1; l < 2; l++){
				if(!(j == 0 && l == 0))
				if(v.getX() + j < currMap.columns && v.getX() + j >= 0 &&
					v.getY() + l < currMap.rows && v.getY() + l >= 0)
				if(currMap.getMap()[v.getX() + j][v.getY() + l].getType() != '0'){
					Vertex vn = currMap.getMap()[v.getX() + j][v.getY() + l];
					if(vn.getGVal(0) > (v.getGVal(0) + currMap.findPathDistance(v,vn))){
						vn.setGVal(v.getGVal(0) + currMap.findPathDistance(v, vn), 0);
						vn.setParent(v, 0);
						if(vn.equals(currMap.getGoal()))
							currMap.setGoal(vn);
						if(!vn.getTraveled(0)){
							if(fringe.hmap[0].containsValue(vn))
								fringe.remove(vn, 0);
							vn.setHVal(heuristic.selectHeuristic(vn, currMap.getGoal(), i + 1), 0);
							vn.setFVal(getKey(vn, i, w1), 0);
							fringe.add(0, vn);
							if(!vn.getTraveled(1)){
								for(int x = 1; x < 5; x++){
									if(fringe.hmap[x].containsValue(vn))
										fringe.remove(vn, x);
									vn.setHVal(heuristic.selectHeuristic(vn, currMap.getGoal(), x + 1), x);
									vn.setFVal(getKey(vn, x, w1), x);
									fringe.add(x, vn);
								}
							}
						}
					}
				}
			}
		}
	}

	public Vertex intASearch(double w1, double w2){
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
						for(int n = 0; n < 5; n++)
							fringe.remove(n);
						expandState(v, i, w1, w2);
						v.setTraveled(true, 1);
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
						for(int n = 0; n < 5; n++)
							fringe.remove(n);
						expandState(v, 0, w1, w2);
						v.setTraveled(true, 0);
					}
				}
			}
		}
		System.out.println("Failed.\n");
		return null;
	}

	public double getKey(Vertex v, int i, double w1){
		return v.getGVal(i) + v.getHVal(i) * w1;
	}

	public void giveStats(Map currMap){
		currMap.getGoal().setExpansions(expansions);
		double endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		currMap.getGoal().setMemUsed(endMem / 1024);
	}
}