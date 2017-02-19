package heuristicmap.model;

import java.util.Arrays;

/*
 *	A model class for vertices - or in this case, the individual vertices of a map.
 *	@author Joel Carrillo (jjc372)
 */

public class Vertex {
	private int x;
	private int y;

	/*
	 * Guideline For Types
	 * Use ’0’ to indicate a blocked cell
 	 * Use ’1’ to indicate a regular unblocked cell
 	 * Use ’2’ to indicate a hard to traverse cell
	 * Use ’a’ to indicate a regular unblocked cell with a highway
 	 * Use ’b’ to indicate a hard to traverse cell with a highway
	 */

	char type;

	int currH;

	// Distance represents 'g' for the purposes of the algorithm, while fVal represents 'f.' Heuristic value recorded in 'h.'.
	double[] gVal;
	double[] fVal;
	double[] hVal;

	// Parent refers to the vertex that this vertex was reached from - used primarily for pathing.
	Vertex[] parent;

	// If the vertex has been traveled to, disregard this when going through A* or any other algorithm.
	boolean[] traveledTo;

	// If isPath is true, then the optimum pathway should go through this vertex.
	boolean isPath;

	// Distance is set to 32767, which should effectively represent 'infinity' for the purposes of the algorithm.
	// No special reason for the number - it can be arbitrary so as long as it greatly exceeds the distances possible.
	public Vertex(int startx, int starty){
		parent = new Vertex[5];
		Arrays.fill(parent, null);
		this.setX(startx);
		this.setY(starty);
		type = '1';
		traveledTo = new boolean[5];
		Arrays.fill(traveledTo, false);
		isPath = false;
		gVal = new double[5];
		Arrays.fill(gVal, 32767);
		fVal = new double[5];
		Arrays.fill(fVal, 0);
		hVal = new double[5];
		Arrays.fill(hVal, 0);
		currH = 0;
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

	// In the event that a river needs to be taken away from a vertex (specifically if a river is being rejected)
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

	public void setParent(Vertex parentIn, int i){
		parent[i] = parentIn;
	}

	public Vertex getParent(int i){
		return parent[i];
	}

	public boolean getTraveled(int i){
		return traveledTo[i];
	}

	public void setTraveled(boolean travelIn, int i){
		traveledTo[i] = travelIn;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean getPath(){
		return isPath;
	}

	public void setPath(boolean pathIn){
		isPath = pathIn;
	}

	public boolean equals(Vertex vertexIn){
		return (x == vertexIn.getX() && y == vertexIn.getY());
	}

	public String toString(){
		return ("X = " + x + " Y = " + y + " Type = " + type + " Traveled In = " + traveledTo[0] + " Distance from Start = " + gVal[0]);
	}

	public String toString(int i){
		return ("X = " + x + " Y = " + y + " Type = " + type + " Travel = " + traveledTo[i] + " g = " + gVal[i] + " f = " + fVal[i] + " h = " + hVal[i]);
	}

	public void setGVal(double gIn, int i){
		gVal[i] = gIn;
	}

	public double getGVal(int i){
		return gVal[i];
	}

	public void setFVal(double fIn, int i){
		fVal[i] = fIn;
	}

	public double getFVal(int i){
		return fVal[i];
	}

	public double getHVal(int i){
		return hVal[i];
	}

	public void setHVal(double hIn, int i){
		hVal[i] = hIn;
	}

	public int getCurrH(){
		return currH;
	}

	public void setCurrH(int n){
		currH = n;
	}
}
