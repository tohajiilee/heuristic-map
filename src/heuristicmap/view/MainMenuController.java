package heuristicmap.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import heuristicmap.model.Map;
import heuristicmap.model.Node;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainMenuController {
	@FXML
	private Button genMapButton;
	@FXML
	private Button loadMapButton;

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
	}
}