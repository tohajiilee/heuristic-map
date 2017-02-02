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
			hardmarkers[i] = map[rand.nextInt(rows)][rand.nextInt(columns)];
		}
	}
}
