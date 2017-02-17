package heuristicmap.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import heuristicmap.model.Algorithm;
import heuristicmap.model.Heuristic;
import heuristicmap.model.Map;
import heuristicmap.model.Vertex;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class MapMenuController {
	private Map currMap;
	private Heuristic heuristic;
	private Algorithm algorithm;

	@FXML
	private TilePane MapPane;

	@FXML
	private Button UCSButton;
	@FXML
	private Button AButton;
	@FXML
	private Button WeightedAButton;

	@FXML
	private CheckBox AutomateCheck;

	@FXML
	private Label WarningLabel;
	@FXML
	private Label XLabel;
	@FXML
	private Label YLabel;
	@FXML
	private Label FLabel;
	@FXML
	private Label HLabel;
	@FXML
	private Label GLabel;

	@FXML
	private RadioButton HButtonA;
	@FXML
	private RadioButton HButtonB;
	@FXML
	private RadioButton HButtonC;
	@FXML
	private RadioButton HButtonD;
	@FXML
	private RadioButton HButtonE;

	@FXML
	private TextField WeightField;

	public void start(Stage primaryStage) {

		UCSButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				System.out.println("Beginning UCS.");
				if(AutomateCheck.isSelected()){
					Vertex originalStart = currMap.getStart();
					Vertex originalGoal = currMap.getGoal();
					for(int i = 0; i < 9; i++){
						currMap.setStart(currMap.getMap()[currMap.getStartGoalPairs()[i][0][0]][currMap.getStartGoalPairs()[i][1][0]]);
						currMap.setGoal(currMap.getMap()[currMap.getStartGoalPairs()[i][0][1]][currMap.getStartGoalPairs()[i][1][1]]);
						initiateSearch(' ');
					}
					currMap.setStart(originalStart);
					currMap.setGoal(originalGoal);
				}
				WarningLabel.setText("");
				initiateSearch(' ');
				if(!AutomateCheck.isSelected()){
					updateTiles();
					System.out.println("Tiles updated.");
				}
			}
		});

		AButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				System.out.println("Beginning A* search.");
				if(AutomateCheck.isSelected()){
					Vertex originalStart = currMap.getStart();
					Vertex originalGoal = currMap.getGoal();
					for(int i = 0; i < 9; i++){
						currMap.setStart(currMap.getMap()[currMap.getStartGoalPairs()[i][0][0]][currMap.getStartGoalPairs()[i][1][0]]);
						currMap.setGoal(currMap.getMap()[currMap.getStartGoalPairs()[i][0][1]][currMap.getStartGoalPairs()[i][1][1]]);
						initiateSearch(getHeuristic());
					}
					currMap.setStart(originalStart);
					currMap.setGoal(originalGoal);
				}
				WarningLabel.setText("");
				initiateSearch(getHeuristic());
				if(!AutomateCheck.isSelected()){
					updateTiles();
					System.out.println("Tiles updated.");
				}
			}
		});

		WeightedAButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				if(Double.parseDouble(WeightField.getText()) < 1 || !isNumeric(WeightField.getText())){
					WarningLabel.setText("Weight: Number >= 1.");
			    	return;
				}
				WarningLabel.setText("");
				System.out.println("Beginning weighted A* search with weight " + WeightField.getText());
				algorithm.setWeight(Double.parseDouble(WeightField.getText()));
				if(AutomateCheck.isSelected()){
					Vertex originalStart = currMap.getStart();
					Vertex originalGoal = currMap.getGoal();
					for(int i = 0; i < 9; i++){
						currMap.setStart(currMap.getMap()[currMap.getStartGoalPairs()[i][0][0]][currMap.getStartGoalPairs()[i][1][0]]);
						currMap.setGoal(currMap.getMap()[currMap.getStartGoalPairs()[i][0][1]][currMap.getStartGoalPairs()[i][1][1]]);
						initiateSearch(getHeuristic());
					}
					currMap.setStart(originalStart);
					currMap.setGoal(originalGoal);
				}
				WarningLabel.setText("");
				initiateSearch(getHeuristic());
				if(!AutomateCheck.isSelected()){
					updateTiles();
					System.out.println("Tiles updated.");
				}
				algorithm.setWeight(1);
			}
		});

		final ToggleGroup group = new ToggleGroup();

		HButtonA.setToggleGroup(group);
		HButtonA.setSelected(true);
		HButtonB.setToggleGroup(group);
		HButtonC.setToggleGroup(group);
		HButtonD.setToggleGroup(group);
		HButtonE.setToggleGroup(group);

		currMap = MainMenuController.currentMap;
		MapPane.setOrientation(Orientation.HORIZONTAL);
		MapPane.setPrefColumns(160);
		MapPane.setHgap(2);
		MapPane.setVgap(2);
		heuristic = new Heuristic();
		algorithm = new Algorithm(heuristic);
		updateTiles();
	}

	public char getHeuristic(){
		if(HButtonA.isSelected())
			return 'a';
		else if(HButtonB.isSelected())
			return 'b';
		else if(HButtonC.isSelected())
			return 'c';
		else if(HButtonD.isSelected())
			return 'd';
		else if(HButtonE.isSelected())
			return 'e';
		else
			return ' ';
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
				int xVal = j; int yVal = i;
				ImageView imageView = new ImageView();
				imageView.setPreserveRatio(true);
				imageView.setFitWidth(16);
				imageView.setFitHeight(16);
				Image image = null;
				String imageType = " ";
				switch(currMap.getMap()[j][i].getType()){
					case '0':
						imageType = "pictures\\block.png";
						break;
					case '1':
						if(currMap.getMap()[j][i].getPath())
							imageType = "pictures\\normalpath.png";
						else
							imageType = "pictures\\normal.png";
						if(currMap.getMap()[j][i].equals(currMap.getGoal()))
							imageType = "pictures\\normalgoal.png";
						else if(currMap.getMap()[j][i].equals(currMap.getStart()))
							imageType = "pictures\\normalstart.png";
						break;
					case '2':
						if(currMap.getMap()[j][i].getPath())
							imageType = "pictures\\hardpath.png";
						else
							imageType = "pictures\\hard.png";
						if(currMap.getMap()[j][i].equals(currMap.getGoal()))
							imageType = "pictures\\hardgoal.png";
						else if(currMap.getMap()[j][i].equals(currMap.getStart()))
							imageType = "pictures\\hardstart.png";
						break;
					case 'a':
						if(currMap.getMap()[j][i].getPath())
							imageType = "pictures\\normalriverpath.png";
						else
							imageType = "pictures\\normalriver.png";
						if(currMap.getMap()[j][i].equals(currMap.getGoal()))
							imageType = "pictures\\normalrivergoal.png";
						else if(currMap.getMap()[j][i].equals(currMap.getStart()))
							imageType = "pictures\\normalriverstart.png";
						break;
					case 'b':
						if(currMap.getMap()[j][i].getPath())
							imageType = "pictures\\hardriverpath.png";
						else
							imageType = "pictures\\hardriver.png";
						if(currMap.getMap()[j][i].equals(currMap.getGoal()))
							imageType = "pictures\\hardrivergoal.png";
						else if(currMap.getMap()[j][i].equals(currMap.getStart()))
							imageType = "pictures\\hardriverstart.png";
						break;
					default:
						break;
				}
				try {
					image = new Image(new FileInputStream(imageType));
					imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

	                    @Override
	                    public void handle(MouseEvent event) {
	                    	updateLabels(xVal, yVal,
	                    			currMap.getMap()[xVal][yVal].getDistance(), currMap.getMap()[xVal][yVal].getHVal(), currMap.getMap()[xVal][yVal].getFVal());
	                        event.consume();
	                    }
	               });
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageView.setImage(image);
				MapPane.getChildren().add(imageView);
			}
		}
	}

	public void updateLabels(int x, int y, double g, double h, double f){
		XLabel.setText(Integer.toString(x));
		YLabel.setText(Integer.toString(y));
		GLabel.setText(Double.toString(g));
		HLabel.setText(Double.toString(h));
		FLabel.setText(Double.toString(f));
	}

	public Vertex initiateSearch(char heurType){
		currMap.refreshMap();
		long startTime; long endTime; double msEndTime;
		startTime = System.nanoTime();
		algorithm.setHeuristic(heurType);
		Vertex goalNode = algorithm.aStarSearch(currMap);
		if(goalNode != null){
			while(!goalNode.getParent().equals(goalNode)){
				goalNode.setPath(true);
				goalNode = goalNode.getParent();
			}
			endTime = System.nanoTime() - startTime;
			msEndTime = (double)endTime / 1000000;
			System.out.println("Time: " + msEndTime + "ms");
		}
		return goalNode;
	}

	public boolean isNumeric(String s) {
	    return s.matches("[-+]?\\d*\\.?\\d+");
	}
}