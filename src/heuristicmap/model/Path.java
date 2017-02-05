package heuristicmap.model;

/*
 *	A model class for Paths, a series of coordinates that direct back to their parent Path objects.
 *	@author Joel Carrillo (jjc372) and Lukasz Gremza (lgd65)
 */

public class Path {
	int x, y;
	int length = 0;

	/*
	 * Directions depicted as chars:
	 * 		'N', 'W', 'E', and 'S'
	 * for their respective cardinal directions.
	 *
	 * This is specifically for rivers, and not chiefly useful for the solution path - if the solution path algorithm uses this object at all, that is.
	 */

	char direction = ' ';
	Path parent;


	public Path(int startx, int starty, Path parentIn, char dirIn){
		parent = null;
		this.x = startx;
		this.y = starty;
		parent = parentIn;
		direction = dirIn;
		if(parent != null){
			setLength(parent.getLength() + 1);

		}
	}

	public void setParent(Path parentIn){
		parent = parentIn;
	}

	public Path getParent(){
		return parent;
	}

	public void setLength(int lengthIn){
		length = lengthIn;
	}

	public int getLength(){
		return length;
	}

	public void setDirection(char dirIn){
		direction = dirIn;
	}

	public char getDirection(){
		return direction;
	}
}
