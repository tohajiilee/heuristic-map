package heuristicmap.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import heuristicmap.model.DistanceComparator;
import heuristicmap.model.Heuristic;
import heuristicmap.model.Map;
import heuristicmap.model.Node;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MapMenuController {
	private Map currMap;
	private Heuristic heuristic;

	@FXML
	private TilePane MapPane;
	@FXML
	private Button UCSButton;
	@FXML
	private Button AButton;

	public void start(Stage primaryStage) {

		UCSButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				long startTime; long endTime; long msEndTime;
				startTime = System.nanoTime();
				System.out.println("Beginning UCS.");
				Node goalNode = uniformCostSearch();
				if(goalNode != null){
					while(!goalNode.getParent().equals(goalNode)){
						goalNode.setPath(true);
						goalNode = goalNode.getParent();
					}
					endTime = System.nanoTime() - startTime;
					msEndTime = endTime / 1000000;
					System.out.println("UCS Complete! Time: " + msEndTime + "ms");
					updateTiles();
					System.out.println("Tiles updated.");
				}
			}
		});

		AButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				long startTime; long endTime; long msEndTime;
				startTime = System.nanoTime();
				System.out.println("Beginning A* search.");
				Node goalNode = aStarSearch('b');
				if(goalNode != null){
					while(!goalNode.getParent().equals(goalNode)){
						goalNode.setPath(true);
						goalNode = goalNode.getParent();
					}
					endTime = System.nanoTime() - startTime;
					msEndTime = endTime / 1000000;
					System.out.println("A* Complete! Time: " + msEndTime + "ms");
					updateTiles();
					System.out.println("Tiles updated.");
				}
			}
		});


		currMap = MainMenuController.currentMap;
		MapPane.setOrientation(Orientation.HORIZONTAL);
		MapPane.setPrefColumns(160);
		MapPane.setHgap(2);
		MapPane.setVgap(2);
		heuristic = new Heuristic(currMap);
		updateTiles();
	}

	/*
	 * for (int i = 0; i < rows; i++) { for (int j = 0; j < columns; j++) {
	 * mapString = mapString + map[j][i].getTypeString(); } mapString =
	 * mapString + "\n"; }
	 */

	public void updateTiles() {
		MapPane.getChildren().clear();
		for (int i = 0; i < currMap.rows; i++) {
			for (int j = 0; j < currMap.columns; j++) {
				ImageView imageView = new ImageView();
				imageView.setPreserveRatio(true);
				imageView.setFitWidth(16);
				imageView.setFitHeight(16);
				Image image = null;
				String imageType = "";
				switch(currMap.getMap()[j][i].getType()){
					case '0':
						imageType = "block.png";
						break;
					case '1':
						if(currMap.getMap()[j][i].getPath())
							imageType = "normalpath.png";
						else
							imageType = "normal.png";
						break;
					case '2':
						if(currMap.getMap()[j][i].getPath())
							imageType = "hardpath.png";
						else
							imageType = "hard.png";
						break;
					case 'a':
						if(currMap.getMap()[j][i].getPath())
							imageType = "normalriverpath.png";
						else
							imageType = "normalriver.png";
						break;
					case 'b':
						if(currMap.getMap()[j][i].getPath())
							imageType = "hardriverpath.png";
						else
							imageType = "hardriver.png";
						break;
					default:
						break;
				}
				try {
					image = new Image(new FileInputStream(imageType));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageView.setImage(image);
				MapPane.getChildren().add(imageView);
			}
		}
	}

	public Node uniformCostSearch(){
		return aStarSearch(' ');
	}


	/*
	 * Warning: Currently the actual use of a heuristic does not appear to work. MUST fix this soon.
	 */
	public Node aStarSearch(char heurIn){
		Comparator<Node> comparator = new DistanceComparator();
		PriorityQueue<Node> fringe =
	            new PriorityQueue<Node>(10, comparator);
		currMap.getStart().setDistance(0);
		currMap.getStart().setParent(currMap.getStart());
		fringe.add(currMap.getStart());
		while(!fringe.isEmpty()){
			Node curr = fringe.poll();
			if(currMap.getGoal().equals(curr)){
				return curr;
			}
			curr.setTraveled(true);
			int currX = curr.getX(); int currY = curr.getY();
			for(int i = -1; i < 2; i++){
				for(int j = -1; j < 2; j++){
					if(currX + i > -1 && currX + i < 160
							&& currY + j > -1 && currY + j < 120){
						if(currMap.getMap()[currX + i][currY + j].getType() != '0')
							if(!(i == 0 && j == 0))
								if(!currMap.getMap()[currX + i][currY + j].getTraveled()){
									updateVertex(curr, currMap.getMap()[currX + i][currY + j], fringe, heurIn);
								}
					}
				}
			}
		}
		System.out.println("Failed.");
		return null;
	}

	public void updateVertex(Node v1, Node v2, PriorityQueue<Node> fringe, char heurIn){
		if(v1.getDistance() + (currMap.findPathDistance(v1, v2)) < v2.getDistance()){
			v2.setDistance(v1.getDistance() + (currMap.findPathDistance(v1, v2)) + heuristic.selectHeuristic(v1, currMap.getGoal(), heurIn));
			v2.setParent(v1);
			if(!fringe.isEmpty())
				if(fringe.peek().equals(v2))
					fringe.remove();
			fringe.add(v2);
		}
	}
}