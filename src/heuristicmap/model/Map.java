package heuristicmap.model;

import java.util.Random;

/*
 *	A model class for Songs, including a song's attributes as well as functions for formatting/comparing songs.
 *	@author Joel Carrillo (jjc372)
 */

import heuristicmap.model.Node;
import heuristicmap.model.Path;

public class Map {
	Random rand = new Random();
	int rows = 120, columns = 160;

	Node[][] map;

	Node[] hardmarkers;

	Node start;
	Node goal;

	public Map() {

		map = new Node[columns][rows];

		// Once initialized, the map will set up 120x160 nodes in a grid. They
		// all begin as regular nodes.

		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				map[i][j] = new Node(i, j);
			}
		}

		hardmarkers = new Node[8];

		// 8 different 'hard markers' are set on the grid, and recorded.

		for (int i = 0; i < 8; i++) {
			int markX = rand.nextInt(columns);
			int markY = rand.nextInt(rows);
			hardmarkers[i] = map[markX][markY];

			// The following sets up the 'hard to traverse' nodes in the 31x31
			// square around a given 'hard marker.'
			// Each node has a 50% chance to be hard to traverse if they are
			// within the square.
			for (int j = 0; j < 32; j++) {
				for (int k = 0; k < 32; k++) {
					if ((markX + (j - 15) >= 0 && markX + (j - 15) < columns)
							&& (markY + (k - 15) >= 0 && markY + (k - 15) < rows)) {
						if ((rand.nextInt(100) + 1) > 50) {
							map[markX + (j - 15)][markY + (k - 15)].setType('2');
						}
					}
				}
			}
		}

		/*
		 *  In creating a river, the program will navigate along the border in a
		 * clockwise direction. This will continue until four paths are verified.
		 *
		 * The river generating algorithm randomly selects a side of the map, and then
		 * gives it 10,000 attempts to generate a river that has a length of at least 100 vertices
		 * and does not collide with any other river including itself. Failure to do so causes the
		 * river to generate from a different starting vertex.
		 *
		 * Each river first progresses at least 20 spaces away from the border at which it starts,
		 * and then has a 60% chance to continue straight, 20% chance to go left, and 20% chance to go right.
		 */
		int completedRivers = 0;
		int progressInDir = 0;
		int riverGenAttempts;
		boolean startComplete, riverComplete;
		while (completedRivers < 4) {
			Path river = null;
			startComplete = false;
			riverComplete = false;
			riverGenAttempts = 0;
			while (!startComplete) {
				int m = rand.nextInt(100000);
				int beginX = rand.nextInt(columns);
				int beginY = rand.nextInt(rows);
				if (m < 25001) {
					if (map[beginX][0].getType() != 'a' && map[beginX][0].getType() != 'b') {
						river = new Path(beginX, 0, null, 'S');
						startComplete = true;
						break;
					}
				} else if (m < 50001) {
					if (map[159][beginY].getType() != 'a' && map[159][beginY].getType() != 'b') {
						river = new Path(159, beginY, null, 'W');
						startComplete = true;
						break;
					}
				} else if (m < 75001) {
					if (map[beginX][119].getType() != 'a' && map[beginX][119].getType() != 'b') {
						river = new Path(beginX, 119, null, 'N');
						startComplete = true;
						break;
					}
				} else {
					if (map[0][beginY].getType() != 'a' && map[0][beginY].getType() != 'b') {
						river = new Path(0, beginY, null, 'E');
						startComplete = true;
						break;
					}
				}

			}
			map[river.x][river.y].setRiver();
			char leftDir = ' ';
			char rightDir = ' ';
			int xMove = 0;
			int yMove = 0;
			int q;
			while (riverGenAttempts < 10000) {
				while (!riverComplete) {
					switch (river.getDirection()) {
					case 'N':
						leftDir = 'W';
						rightDir = 'E';
						xMove = 0;
						yMove = -1;
						break;
					case 'W':
						leftDir = 'S';
						rightDir = 'N';
						xMove = 0;
						yMove = -1;
						break;
					case 'E':
						leftDir = 'N';
						rightDir = 'S';
						xMove = 1;
						yMove = 0;
						break;
					case 'S':
						leftDir = 'E';
						rightDir = 'W';
						xMove = 0;
						yMove = 1;
						break;
					default:
						break;
					}
					if (river.y + yMove == -1 || river.y + yMove == rows || river.x + xMove == -1
							|| river.x + xMove == columns) {
						riverComplete = true;
					}
					if (!riverComplete)
						if (map[river.x + xMove][river.y + yMove].getType() == 'a'
								|| map[river.x + xMove][river.y + yMove].getType() == 'b') {
							river.setLength(-1);
							riverComplete = true;
						}
					if (!riverComplete)
						if (progressInDir < 21) {
							river = addToRiver(river.x + xMove, river.y + yMove, river, river.getDirection());
							map[river.x][river.y].setRiver();
							progressInDir++;
						} else {
							q = rand.nextInt(10000);
							if (q < 6001) {
								river = addToRiver(river.x + xMove, river.y + yMove, river, river.getDirection());
								map[river.x][river.y].setRiver();
								progressInDir++;
							} else if (q < 8001) {
								progressInDir = 0;
								river.setDirection(leftDir);
							} else {
								progressInDir = 0;
								river.setDirection(rightDir);
							}
						}
					if (river.getLength() < 100 && riverComplete) {
						while (river.getParent() != null) {
							map[river.x][river.y].revertRiver();
							river = river.getParent();
						}
						riverComplete = false;
						progressInDir = 0;
						riverGenAttempts++;
						if (riverGenAttempts >= 10000) {
							map[river.x][river.y].revertRiver();
							break;
						}
					} else if (riverComplete) {

						completedRivers++;
						riverGenAttempts = 10000;
						break;
					}
				}

			}
		}

		/*
		 * At least 20% of the spaces on the map must be made as impassable 'mountain' vertices.
		 * This loop will scroll directly along the map, marking as it goes.
		 */
		int numImpassable = 0;
		int impassableGoal = ((columns * rows) / 5);
		while (numImpassable < impassableGoal)
			for (int i = 0; i < columns; i++) {
				for (int j = 0; j < rows; j++)
					if (map[i][j].getType() != 'a' && map[i][j].getType() != 'b' && map[i][j].getType() != '0') {
						if (rand.nextInt(20000) < 50) {
							map[i][j].setType('0');
							numImpassable++;
							if (numImpassable >= impassableGoal)
								break;
						}
					}
				if (numImpassable >= impassableGoal)
					break;
			}

		/*
		 * A set of start and goal vertices are also chosen. Randomly, among 4 different variations, a start/goal is selected:
		 * 	- From the top and bottom, or vice versa
		 * 	- From the left and right, or vice versa
		 * If the overall distance in vertices is not over 100, we retry.
		 */
		int n;
		int startX, startY, goalX, goalY;
		while (true) {
			n = rand.nextInt(4000) + 1;
			if (n < 1001) {
				startX = rand.nextInt(20);
				startY = rand.nextInt(rows);
				goalX = rand.nextInt(20) + 140;
				goalY = rand.nextInt(rows);
			} else if (n < 2001) {
				startX = rand.nextInt(20) + 140;
				startY = rand.nextInt(rows);
				goalX = rand.nextInt(20);
				goalY = rand.nextInt(rows);
			} else if (n < 3001) {
				startX = rand.nextInt(columns);
				startY = rand.nextInt(20) + 100;
				goalX = rand.nextInt(columns);
				goalY = rand.nextInt(20);
			} else {
				startX = rand.nextInt(columns);
				startY = rand.nextInt(20);
				goalX = rand.nextInt(columns);
				goalY = rand.nextInt(20) + 100;
			}
			if (Math.abs(startX - goalX) + Math.abs(startY - goalY) > 100)
				break;
		}
		start = map[startX][startY];
		goal = map[goalX][goalY];
	}

	public Path addToRiver(int x, int y, Path parent, char direction) {
		Path newRiver = new Path(x, y, parent, direction);
		return newRiver;
	}

	public String toString() {
		String mapString = "";
		mapString = mapString + ("(" + start.x + "," + start.y + ")" + "\n");
		mapString = mapString + ("(" + goal.x + "," + goal.y + ")" + "\n");

		for (int i = 0; i < hardmarkers.length; i++)
			mapString = mapString + ("(" + hardmarkers[i].x + "," + hardmarkers[i].y + ")" + "\n");

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				mapString = mapString + map[j][i].getTypeString();
			}
			mapString = mapString + "\n";
		}

		return mapString;
	}
}
