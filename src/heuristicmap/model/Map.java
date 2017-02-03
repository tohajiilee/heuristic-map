package heuristicmap.model;

import java.util.Random;

/*
 *	A model class for Songs, including a song's attributes as well as functions for formatting/comparing songs.
 *	@author Joel Carrillo (jjc372)
 */

import heuristicmap.model.Node;

public class Map {
	Random rand = new Random();
	int rows = 120, columns = 160;

	Node[][] map;

	Node[] hardmarkers;

	public Map(){

		map = new Node[columns][rows];

		// Once initialized, the map will set up 120x160 nodes in a grid. They all begin as regular nodes.

		for(int i = 0; i < columns; i++){
			for(int j = 0; j < rows; j++){
				map[i][j] = new Node(i, j);
			}
		}

		hardmarkers = new Node[8];

		// 8 different 'hard markers' are set on the grid, and recorded.

		for(int i = 0; i < 8; i++){
			int markX = rand.nextInt(columns);
			int markY = rand.nextInt(rows);
			hardmarkers[i] = map[markX][markY];

			// The following sets up the 'hard to traverse' nodes in the 31x31 square around a given 'hard marker.'
			// Each node has a 50% chance to be hard to traverse if they are within the square.
			for(int j = 0; j < 32; j++){
				for(int k = 0; k < 32; k++){
					if((markX + (j - 15) >= 0 && markX + (j - 15) < columns)
						&& (markY + (k - 15) >= 0 && markY + (k - 15) < rows)){
						if((rand.nextInt(100) + 1) > 50){
								map[markX + (j - 15)][markY + (k - 15)].setType('2');
						}
					}
				}
			}
		}

		// In creating a river, the program will navigate along the border in a clockwise direction. This will continue until four paths are verified.
		int completedRivers = 0;
		int riverLength;
		/*while(completedRivers < 4){
			riverLength = 0;
			for(int i = 0; i < columns; i++){

			}

			if(riverLength > 100){

			}
		}*/
	}

	public String toString(){
		String mapString = "";

		for(int i = 0; i < columns; i++){
			for(int j = 0; j < rows; j++){
				mapString = mapString + map[i][j].getTypeString();
			}
			mapString.concat("\n");
		}

		return mapString;
	}
}
