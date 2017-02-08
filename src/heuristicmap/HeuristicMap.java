package heuristicmap;

/*
 *	The 'main' class for the Heuristic Map app.
 *	@author Joel Carrillo (jjc372)
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import heuristicmap.view.MainMenuController;

public class HeuristicMap extends Application {
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/heuristicmap/view/MainMenu.fxml"));
		AnchorPane songView = (AnchorPane) loader.load();

		MainMenuController listController = loader.getController();
		listController.start(primaryStage);
		Scene scene = new Scene(songView);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Heuristic Map Application");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}

}