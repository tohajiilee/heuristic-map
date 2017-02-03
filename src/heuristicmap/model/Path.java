package heuristicmap.model;

/*
 *	A model class for Paths, a series of coordinates that direct back to their parent Path objects.
 *	@author Joel Carrillo (jjc372) and Lukasz Gremza (lgd65)
 */

public class Path {
	int x, y;
	int length = 0;
	Path parent;


	public Path(int startx, int starty, Path parentIn){
		parent = null;
		this.x = startx;
		this.y = starty;
		parent = parentIn;
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
}
