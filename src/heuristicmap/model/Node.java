package heuristicmap.model;

/*
 *	A model class for Songs, including a song's attributes as well as functions for formatting/comparing songs.
 *	@author Joel Carrillo (jjc372) and Lukasz Gremza (lgd65)
 */

public class Node {
	int x, y;

	/*
	 * Guideline For Types
	 * Use ’0’ to indicate a blocked cell
 	 * Use ’1’ to indicate a regular unblocked cell
 	 * Use ’2’ to indicate a hard to traverse cell
	 * Use ’a’ to indicate a regular unblocked cell with a highway
 	 * Use ’b’ to indicate a hard to traverse cell with a highway
	 */

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
