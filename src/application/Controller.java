package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class Controller implements Initializable {

	@FXML
	private MediaView mv;
	private MediaPlayer mp;
	private Media m;

	@FXML
	private Button play;
	@FXML
	private Button left;
	@FXML
	private Button right;
	@FXML
	private Button stop;

	private boolean playing = true;

	@Override
	public void initialize(URL location, ResourceBundle res) {
		String path = new File("src/videos/gold.mp4").getAbsolutePath();

		m = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(m);
		mv.setMediaPlayer(mp);
		mp.setAutoPlay(true);

		setIcons();

		DoubleProperty width = mv.fitWidthProperty();
		DoubleProperty height = mv.fitHeightProperty();

		width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
	}

	private void setIcons() {
		Image playIcon = new Image(getClass().getResourceAsStream("/pause.png"));
		play.setGraphic(new ImageView(playIcon));

		playIcon = new Image(getClass().getResourceAsStream("/backward.png"));
		left.setGraphic(new ImageView(playIcon));

		playIcon = new Image(getClass().getResourceAsStream("/forward.png"));
		right.setGraphic(new ImageView(playIcon));

		playIcon = new Image(getClass().getResourceAsStream("/stop.png"));
		stop.setGraphic(new ImageView(playIcon));
	}

	public void playOrPause(ActionEvent ae) {
		if (playing) {
			mp.pause();
			playing = false;
			play.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/play.png"))));
		} else {
			mp.setRate(1);
			mp.play();
			playing = true;
			play.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pause.png"))));
		}
	}

	public void stop(ActionEvent ae) {
		mp.stop();
	}

	public void jumpLeft(ActionEvent ae) {
		mp.seek(mp.getCurrentTime().subtract(Duration.seconds(10)));
	}

	public void jumpRight(ActionEvent ae) {
		mp.seek(mp.getCurrentTime().add(Duration.seconds(10)));
	}
	
	public void loadFile() {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.setTitle("Select Video!");
		
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4", "*.mp4"));
		
		String toPlay = fc.showOpenDialog(Main.window).getAbsolutePath();
		
		mp.stop();
		
		m = new Media(new File(toPlay).toURI().toString());
		mp = new MediaPlayer(m);
		mv.setMediaPlayer(mp);
		mp.setAutoPlay(true);
	}

}