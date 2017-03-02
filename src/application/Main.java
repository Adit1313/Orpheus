package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage window;

	@Override
	public void start(Stage stg) {
		try {
			window = stg;

			FXMLLoader fl = new FXMLLoader(getClass().getResource("/application/style.fxml"));
			Parent root = fl.load();
			Controller c = fl.getController();

			Scene scn = new Scene(root);
			scn.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			window.setResizable(false);
			window.setScene(scn);
			c.setKeyEvents();
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