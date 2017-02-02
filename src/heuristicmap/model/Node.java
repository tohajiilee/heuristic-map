package heuristicmap.model;

/*
 *	A model class for Songs, including a song's attributes as well as functions for formatting/comparing songs.
 *	@author Joel Carrillo (jjc372)
 */

public class Node {
	int x, y;
	char type;
	boolean hasRiver;

	public Node(int startx, int starty){
		this.x = startx;
		this.y = starty;
		type = '1';
	}

	public void setType(char typeIn){
		this.type = typeIn;
	}

	public char getType(){
		return type;
	}

	public boolean setRiver(){
		if(type != '0'){
			hasRiver = true;
			return true;
		}
		return false;
	}

	public boolean getRiver(){
		return hasRiver;
	}
}
