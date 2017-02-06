package heuristicmap.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import heuristicmap.model.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainMenuController {
	@FXML
	private Button genMapButton;
	@FXML
	private Button loadMapButton;

	public static Map currentMap;

	public void start(Stage mainStage) throws FileNotFoundException, UnsupportedEncodingException {
		genMapButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
					Map map = new Map();
		            FileChooser fileChooser = new FileChooser();
		            fileChooser.setTitle("Save Map");
		            File file = fileChooser.showSaveDialog(mainStage);
		            if (file != null) {
		                try {
		                	PrintWriter writer = new PrintWriter(file, "UTF-8");
		                    writer.write(map.toString());
		                    writer.close();
		                } catch (IOException ex) {
		                    System.out.println(ex.getMessage());
		                }
		            }
			}
		});

		loadMapButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Load Map");
		        File file = fileChooser.showOpenDialog(mainStage);
		        currentMap = new Map(file);
		        Stage stage = new Stage();
				Parent root;
				try
				{
					((Node)(event.getSource())).getScene().getWindow().hide();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("MapMenu.fxml"));
					root = (AnchorPane) loader.load();
					MapMenuController mapController = loader.getController();

					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("View Map");
					mapController.start(stage);
					stage.show();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}