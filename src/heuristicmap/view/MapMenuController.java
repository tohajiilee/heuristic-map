package heuristicmap.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import heuristicmap.model.Map;
import heuristicmap.model.Path;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MapMenuController {
	private Map currMap;

	@FXML
	private TilePane MapPane;

	public void start(Stage primaryStage) {
		currMap = MainMenuController.currentMap;
		MapPane.setOrientation(Orientation.HORIZONTAL);
		MapPane.setPrefColumns(160);
		MapPane.setHgap(2);
		MapPane.setVgap(2);
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
						imageType = "normal.png";
						break;
					case '2':
						imageType = "hard.png";
						break;
					case 'a':
						imageType = "normalriver.png";
						break;
					case 'b':
						imageType = "hardriver.png";
						break;
					default:
						break;
				}
				try {
					image = new Image(new FileInputStream(imageType));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("uh ohhhh\n");
					e.printStackTrace();
				}
				imageView.setImage(image);
				MapPane.getChildren().add(imageView);
			}
		}
	}

	public Path uniformCostSearch(){

		return null;
	}
}