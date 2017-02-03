package heuristicmap.view;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import heuristicmap.model.Map;
import heuristicmap.model.Node;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
				System.out.println(map.toString());
			}
		});
	}
}