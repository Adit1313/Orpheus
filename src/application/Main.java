package application;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static Stage window;
	
	private boolean full = false;
	
	@Override
	public void start(Stage stg) {
		try {
			window = stg;
			
			Parent root = FXMLLoader.load(getClass().getResource("/application/style.fxml"));
			Scene scn = new Scene(root);
			scn.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			scn.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent evt) {
					if (evt.getCode() == KeyCode.F) {
						if (full) {
							window.setFullScreen(false);
							full = false;
						} else {
							window.setFullScreen(true);
							full = true;
						}
					}
				}
			});
			window.setResizable(false);
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