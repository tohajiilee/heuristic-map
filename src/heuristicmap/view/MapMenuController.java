package heuristicmap.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import heuristicmap.model.Algorithm;
import heuristicmap.model.Heuristic;
import heuristicmap.model.IntegratedAStar;
import heuristicmap.model.Map;
import heuristicmap.model.SequentialAStar;
import heuristicmap.model.Vertex;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class MapMenuController {
	private Map currMap;
	private Heuristic heuristic;
	private Algorithm algorithm;
	private SequentialAStar seqalgorithm;
	private IntegratedAStar intalgorithm;

	@FXML
	private TilePane MapPane;

	@FXML
	private Button UCSButton;
	@FXML
	private Button AButton;
	@FXML
	private Button WeightedAButton;
	@FXML
	private Button SeqAButton;
	@FXML
	private Button IntAButton;

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
	private Label TimeLabel;
	@FXML
	private Label MemLabel;
	@FXML
	private Label ExpansionLabel;

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
	@FXML
	private TextField W1Field;
	@FXML
	private TextField W2Field;

	public void start(Stage primaryStage) {

		UCSButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				int totalExpansions = 0;
				double totalG = 0, totalMem = 0, totalTime = 0;
				System.out.println("Beginning UCS.");
				if(AutomateCheck.isSelected()){
					Vertex originalStart = currMap.getStart();
					Vertex originalGoal = currMap.getGoal();
					for(int i = 0; i < 9; i++){
						currMap.setStart(currMap.getMap()[currMap.getStartGoalPairs()[i][0][0]][currMap.getStartGoalPairs()[i][1][0]]);
						currMap.setGoal(currMap.getMap()[currMap.getStartGoalPairs()[i][0][1]][currMap.getStartGoalPairs()[i][1][1]]);
						Vertex curr = initiateSearch(' ');
						totalExpansions += curr.getExpansions();
						totalG += curr.getGVal(0);
						totalMem += curr.getMemUsed();
						totalTime += curr.getTimeTaken();
					}
					currMap.setStart(originalStart);
					currMap.setGoal(originalGoal);
				}
				WarningLabel.setText("");
				Vertex curr = initiateSearch(' ');
				if(!AutomateCheck.isSelected()){
					updateTiles();
					System.out.println("Tiles updated.");
					TimeLabel.setText(curr.getTimeTaken() + "ms");
					MemLabel.setText(curr.getMemUsed() + "kb");
					ExpansionLabel.setText(Integer.toString(curr.getExpansions()));
				}
				else{
					totalExpansions += curr.getExpansions();
					totalG += curr.getGVal(0);
					totalMem += curr.getMemUsed();
					totalTime += curr.getTimeTaken();
					System.out.println("Average Expansions: " + (totalExpansions / 10));
					System.out.println("Average Cost: " + (totalG / 10));
					System.out.println("Average Memory Used: " + (totalMem / 10) + "kb");
					System.out.println("Average Time Taken: " + (totalTime / 10) + "ms");
				}
				System.out.println("");
			}
		});

		AButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				int totalExpansions = 0;
				double totalG = 0, totalMem = 0, totalTime = 0;
				System.out.println("Beginning A* search with heuristic " + getHeuristic() + ".");
				if(AutomateCheck.isSelected()){
					Vertex originalStart = currMap.getStart();
					Vertex originalGoal = currMap.getGoal();
					for(int i = 0; i < 9; i++){
						currMap.setStart(currMap.getMap()[currMap.getStartGoalPairs()[i][0][0]][currMap.getStartGoalPairs()[i][1][0]]);
						currMap.setGoal(currMap.getMap()[currMap.getStartGoalPairs()[i][0][1]][currMap.getStartGoalPairs()[i][1][1]]);
						Vertex curr = initiateSearch(' ');
						totalExpansions += curr.getExpansions();
						totalG += curr.getGVal(0);
						totalMem += curr.getMemUsed();
						totalTime += curr.getTimeTaken();
					}
					currMap.setStart(originalStart);
					currMap.setGoal(originalGoal);
				}
				WarningLabel.setText("");
				Vertex curr = initiateSearch(getHeuristic());
				if(!AutomateCheck.isSelected()){
					updateTiles();
					System.out.println("Tiles updated.");
					TimeLabel.setText(curr.getTimeTaken() + "ms");
					MemLabel.setText(curr.getMemUsed() + "kb");
					ExpansionLabel.setText(Integer.toString(curr.getExpansions()));
				}
				else{
					totalExpansions += curr.getExpansions();
					totalG += curr.getGVal(0);
					totalMem += curr.getMemUsed();
					totalTime += curr.getTimeTaken();
					System.out.println("Average Expansions: " + (totalExpansions / 10));
					System.out.println("Average Cost: " + (totalG / 10));
					System.out.println("Average Memory Used: " + (totalMem / 10) + "kb");
					System.out.println("Average Time Taken: " + (totalTime / 10) + "ms");
				}
				System.out.println("");
			}
		});

		WeightedAButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				int totalExpansions = 0;
				double totalG = 0, totalMem = 0, totalTime = 0;
				if(Double.parseDouble(WeightField.getText()) < 1 || !isNumeric(WeightField.getText())){
					WarningLabel.setText("Weight: Number >= 1.");
			    	return;
				}
				WarningLabel.setText("");
				System.out.println("Beginning weighted A* search with weight " + WeightField.getText() + " with heuristic " + getHeuristic() + ".");
				algorithm.setWeight(Double.parseDouble(WeightField.getText()));
				if(AutomateCheck.isSelected()){
					Vertex originalStart = currMap.getStart();
					Vertex originalGoal = currMap.getGoal();
					for(int i = 0; i < 9; i++){
						currMap.setStart(currMap.getMap()[currMap.getStartGoalPairs()[i][0][0]][currMap.getStartGoalPairs()[i][1][0]]);
						currMap.setGoal(currMap.getMap()[currMap.getStartGoalPairs()[i][0][1]][currMap.getStartGoalPairs()[i][1][1]]);
						Vertex curr = initiateSearch(' ');
						totalExpansions += curr.getExpansions();
						totalG += curr.getGVal(0);
						totalMem += curr.getMemUsed();
						totalTime += curr.getTimeTaken();
						System.out.println("");
					}
					currMap.setStart(originalStart);
					currMap.setGoal(originalGoal);
				}
				WarningLabel.setText("");
				Vertex curr = initiateSearch(getHeuristic());
				if(!AutomateCheck.isSelected()){
					updateTiles();
					System.out.println("Tiles updated.");
					TimeLabel.setText(curr.getTimeTaken() + "ms");
					MemLabel.setText(curr.getMemUsed() + "kb");
					ExpansionLabel.setText(Integer.toString(curr.getExpansions()));
				}
				else{
					totalExpansions += curr.getExpansions();
					totalG += curr.getGVal(0);
					totalMem += curr.getMemUsed();
					totalTime += curr.getTimeTaken();
					System.out.println("Average Expansions: " + (totalExpansions / 10));
					System.out.println("Average Cost: " + (totalG / 10));
					System.out.println("Average Memory Used: " + (totalMem / 10) + "kb");
					System.out.println("Average Time Taken: " + (totalTime / 10) + "ms");
				}
				System.out.println("");
				algorithm.setWeight(1);
			}
		});

		SeqAButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				int totalExpansions = 0;
				double totalG = 0, totalMem = 0, totalTime = 0;
				System.out.println("Beginning sequential A* search.");
				if(Double.parseDouble(W1Field.getText()) < 1 || !isNumeric(W2Field.getText()) || W1Field.getText() == null)
					if(Double.parseDouble(W2Field.getText()) < 1 || !isNumeric(W2Field.getText()) || W1Field.getText() == null)
						return;
				if(AutomateCheck.isSelected()){
					Vertex originalStart = currMap.getStart();
					Vertex originalGoal = currMap.getGoal();
					for(int i = 0; i < 9; i++){
						currMap.setStart(currMap.getMap()[currMap.getStartGoalPairs()[i][0][0]][currMap.getStartGoalPairs()[i][1][0]]);
						currMap.setGoal(currMap.getMap()[currMap.getStartGoalPairs()[i][0][1]][currMap.getStartGoalPairs()[i][1][1]]);
						Vertex curr = initiateSeqSearch();
						totalExpansions += curr.getExpansions();
						totalG += curr.getGVal(0);
						totalMem += curr.getMemUsed();
						totalTime += curr.getTimeTaken();
					}
					currMap.setStart(originalStart);
					currMap.setGoal(originalGoal);
				}
				WarningLabel.setText("");
				Vertex curr = initiateSeqSearch();
				if(!AutomateCheck.isSelected()){
					updateTiles();
					System.out.println("Tiles updated.");
					TimeLabel.setText(curr.getTimeTaken() + "ms");
					MemLabel.setText(curr.getMemUsed() + "kb");
					ExpansionLabel.setText(Integer.toString(curr.getExpansions()));
				}
				else{
					totalExpansions += curr.getExpansions();
					totalG += curr.getGVal(0);
					totalMem += curr.getMemUsed();
					totalTime += curr.getTimeTaken();
					System.out.println("Average Expansions: " + (totalExpansions / 10));
					System.out.println("Average Cost: " + (totalG / 10));
					System.out.println("Average Memory Used: " + (totalMem / 10) + "kb");
					System.out.println("Average Time Taken: " + (totalTime / 10) + "ms");
				}
				System.out.println("");
			}
		});

		IntAButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				int totalExpansions = 0;
				double totalG = 0, totalMem = 0, totalTime = 0;
				System.out.println("Beginning sequential A* search.");
				if(Double.parseDouble(W1Field.getText()) < 1 || !isNumeric(W2Field.getText()) || W1Field.getText() == null)
					if(Double.parseDouble(W2Field.getText()) < 1 || !isNumeric(W2Field.getText()) || W1Field.getText() == null)
						return;
				if(AutomateCheck.isSelected()){
					Vertex originalStart = currMap.getStart();
					Vertex originalGoal = currMap.getGoal();
					for(int i = 0; i < 9; i++){
						currMap.setStart(currMap.getMap()[currMap.getStartGoalPairs()[i][0][0]][currMap.getStartGoalPairs()[i][1][0]]);
						currMap.setGoal(currMap.getMap()[currMap.getStartGoalPairs()[i][0][1]][currMap.getStartGoalPairs()[i][1][1]]);
						Vertex curr = initiateIntSearch();
						totalExpansions += curr.getExpansions();
						totalG += curr.getGVal(0);
						totalMem += curr.getMemUsed();
						totalTime += curr.getTimeTaken();
					}
					currMap.setStart(originalStart);
					currMap.setGoal(originalGoal);
				}
				WarningLabel.setText("");
				Vertex curr = initiateIntSearch();
				if(!AutomateCheck.isSelected()){
					updateTiles();
					System.out.println("Tiles updated.");
					TimeLabel.setText(curr.getTimeTaken() + "ms");
					MemLabel.setText(curr.getMemUsed() + "kb");
					ExpansionLabel.setText(Integer.toString(curr.getExpansions()));
				}
				else{
					totalExpansions += curr.getExpansions();
					totalG += curr.getGVal(0);
					totalMem += curr.getMemUsed();
					totalTime += curr.getTimeTaken();
					System.out.println("Average Expansions: " + (totalExpansions / 10));
					System.out.println("Average Cost: " + (totalG / 10));
					System.out.println("Average Memory Used: " + (totalMem / 10) + "kb");
					System.out.println("Average Time Taken: " + (totalTime / 10) + "ms");
				}
				System.out.println("");
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

	@FXML
	private void backOut(ActionEvent event)
	{
		Stage stage = new Stage();
		Parent root;
		try
		{
			((Node)(event.getSource())).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("MainMenu.fxml"));
			root = (AnchorPane) loader.load();
			MainMenuController mainController = loader.getController();

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Heuristic Map Application");
			mainController.start(stage);
			stage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public int getHeuristic(){
		if(HButtonA.isSelected())
			return 1;
		else if(HButtonB.isSelected())
			return 2;
		else if(HButtonC.isSelected())
			return 3;
		else if(HButtonD.isSelected())
			return 4;
		else if(HButtonE.isSelected())
			return 5;
		else
			return 0;
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
	                    			currMap.getMap()[xVal][yVal].getGVal(currMap.getGoal().getCurrH()),
	                    				currMap.getMap()[xVal][yVal].getHVal(currMap.getGoal().getCurrH()),
	                    					currMap.getMap()[xVal][yVal].getFVal(currMap.getGoal().getCurrH()));
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

	public Vertex initiateSearch(int h){
		currMap.refreshMap();
		long startTime; long endTime; double msEndTime;
		startTime = System.nanoTime();
		algorithm.setHeuristic(h);
		Vertex origNode = algorithm.aStarSearch(currMap);
		Vertex goalNode = origNode;
		endTime = System.nanoTime() - startTime;
		msEndTime = (double)endTime / 1000000;
		origNode.setTimeTaken(msEndTime);
		if(goalNode != null){
			while(!goalNode.getParent(0).equals(goalNode)){
				goalNode.setPath(true);
				goalNode = goalNode.getParent(0);
			}
		}
		return origNode;
	}

	public Vertex initiateSeqSearch(){
		seqalgorithm = new SequentialAStar(currMap);
		currMap.refreshMap();
		long startTime; long endTime; double msEndTime;
		startTime = System.nanoTime();
		Vertex origNode = seqalgorithm.seqASearch(Double.parseDouble(W1Field.getText()),
				Double.parseDouble(W2Field.getText()));
		Vertex goalNode = origNode;
		endTime = System.nanoTime() - startTime;
		msEndTime = (double)endTime / 1000000;
		origNode.setTimeTaken(msEndTime);
		if(goalNode != null){
			int h = goalNode.getCurrH();
			while(goalNode.getParent(h) != null){
				goalNode.setPath(true);
				goalNode = goalNode.getParent(h);
			}
		}
		return origNode;
	}

	public Vertex initiateIntSearch(){
		intalgorithm = new IntegratedAStar(currMap);
		currMap.refreshMap();
		long startTime; long endTime; double msEndTime;
		startTime = System.nanoTime();
		Vertex origNode = intalgorithm.intASearch(Double.parseDouble(W1Field.getText()),
				Double.parseDouble(W2Field.getText()));
		Vertex goalNode = origNode;
		endTime = System.nanoTime() - startTime;
		msEndTime = (double)endTime / 1000000;
		origNode.setTimeTaken(msEndTime);
		if(goalNode != null){
			while(goalNode.getParent(0) != null){
				goalNode.setPath(true);
				goalNode = goalNode.getParent(0);
			}
		}
		return origNode;
	}

	public boolean isNumeric(String s) {
	    return s.matches("[-+]?\\d*\\.?\\d+");
	}
}