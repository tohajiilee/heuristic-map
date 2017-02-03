package heuristicmap.model;

/*
 *	A model class for Nodes - or in this case, the individual vertices of a map.
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
	Node parent;

	public Node(int startx, int starty){
		parent = null;
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

	public String getTypeString(){
		return Character.toString(type);
	}

	public void setRiver(){
		switch(type){
			case '1':
				this.setType('a');
				break;
			case '2':
				this.setType('b');
				break;
			default:
				break;
		}
	}

	// In the event that a river needs to be taken away from a node (specifically if a river is being rejected)
	public void revertRiver(){
		switch(type){
			case 'a':
				this.setType('1');
				break;
			case 'b':
				this.setType('2');
				break;
			default:
				break;
		}
	}

	public void setParent(Node parentIn){
		parent = parentIn;
	}

	public Node getParent(){
		return parent;
	}
}
