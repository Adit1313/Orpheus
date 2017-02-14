package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage window) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/style.fxml"));
			Scene scn = new Scene(root);
			scn.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			window.setScene(scn);
			window.setTitle("Orpheus");
			window.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}