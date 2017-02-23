package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	@FXML
	private Slider volume;

	private ImageView playIcon = new ImageView(new Image(getClass().getResourceAsStream("/play.png")));
	private ImageView pauseIcon = new ImageView(new Image(getClass().getResourceAsStream("/pause.png")));
	private ImageView stopIcon = new ImageView(new Image(getClass().getResourceAsStream("/stop.png")));
	private ImageView forwardIcon = new ImageView(new Image(getClass().getResourceAsStream("/forward.png")));
	private ImageView backIcon = new ImageView(new Image(getClass().getResourceAsStream("/backward.png")));

	private boolean playing = true;
	private boolean full = false;

	// Playlist variables and objects
	boolean playlistPlaying = false;
	int totalMedia = 0;
	int currentMedia = 0;

	List<File> playlist = new ArrayList<File>();
	List<String> paths = new ArrayList<String>();
	List<Media> medias = new ArrayList<Media>();

	@Override
	public void initialize(URL location, ResourceBundle res) {
		String path = new File("src/videos/random.mp4").getAbsolutePath();

		m = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(m);
		mv.setMediaPlayer(mp);

		mp.setAutoPlay(true);
		

		mp.setVolume(0.3);
		volume.setValue(mp.getVolume() * 100);
		volume.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> prevValue, Number newVal, Number arg2) {
				mp.setVolume(newVal.doubleValue() / 100);
			}

		});

		setIcons();

		DoubleProperty width = mv.fitWidthProperty();
		DoubleProperty height = mv.fitHeightProperty();

		width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
	}

	public void setKeyEvents() {
		Main.window.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent evt) {

				if (evt.getCode() == KeyCode.F) {
					if (full) {
						Main.window.setFullScreen(false);
						full = false;
					} else {
						Main.window.setFullScreen(true);
						full = true;
					}
				} else if (evt.getCode() == KeyCode.D) {
					jumpRight();
				} else if (evt.getCode() == KeyCode.A) {
					jumpLeft();
				}
			}
		});
	}

	private void setIcons() {
		play.setGraphic(pauseIcon);
		stop.setGraphic(stopIcon);
		left.setGraphic(backIcon);
		right.setGraphic(forwardIcon);
	}

	public void playOrPause() {
		if (playing) {
			mp.pause();
			playing = false;
			play.setGraphic(playIcon);
		} else {
			mp.setRate(1);
			mp.play();
			playing = true;
			play.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/pause.png"))));
		}
	}

	public void stop() {
		mp.stop();
		play.setGraphic(playIcon);
		playlistPlaying = false;
	}

	public void jumpLeft() {
		mp.seek(mp.getCurrentTime().subtract(Duration.seconds(5)));
	}

	public void jumpRight() {
		mp.seek(mp.getCurrentTime().add(Duration.seconds(5)));
	}

	public void loadFile() {
		playlistPlaying = false;
		
		mp.pause();
		play.setGraphic(playIcon);

		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.setTitle("Select Video!");

		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4", "*.mp4"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3", "*.mp3"));

		String toPlay = fc.showOpenDialog(Main.window).getAbsolutePath();

		mp.stop();

		m = new Media(new File(toPlay).toURI().toString());
		mp = new MediaPlayer(m);
		mv.setMediaPlayer(mp);
		mp.setAutoPlay(true);
	}

	public void loadFiles() {
		playlistPlaying = true;
		
		mp.pause();
		play.setGraphic(playIcon);

		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.setTitle("Select Videos!");

		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4", "*.mp4"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3", "*.mp3"));

		playlist = fc.showOpenMultipleDialog(Main.window);

		for (int i = 0; i < playlist.size(); i++) {
			paths.add(playlist.get(i).getAbsolutePath());
		}

		for (int i = 0; i < paths.size(); i++) {
			medias.add(new Media(new File(paths.get(i)).toURI().toString()));
		}
		
		totalMedia = medias.size();
		currentMedia = 0;
		
		mp = new MediaPlayer(medias.get(currentMedia));
		
		mv.setMediaPlayer(mp);
		mp.setAutoPlay(true);
	}

}