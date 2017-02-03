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
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				map[i][j] = new Node(i, j);
			}
		}

		for(int i = 0; i < 8; i++){
			int markX = rand.nextInt(rows);
			int markY = rand.nextInt(columns);
			hardmarkers[i] = map[markX][markY];

			for(int j = 0; j < 31; j++){
				for(int k = 0; k < 31; k++){
					if((markX + (j - 15) >= 0 && markX + (j - 15) < columns)
						&& (markY + (j - 15) >= 0 && markY + (j - 15) < rows)){
						if(rand.nextInt(100) > 50){

						}
					}
				}
			}

		}
	}
}
