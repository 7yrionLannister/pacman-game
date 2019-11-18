package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Coordinate;
import model.Direction;
import model.Game;
import model.Ghost;
import model.Level;
import model.Player;
import model.Food;
import threads.GhostThread;
import threads.PacmanThread;

public class PrimaryStageController {
	/**It represents the number of movements made by Pacman.
	 */
	public static long MOVEMENT_COUNTER;
	/**It represents number of sprite movements.
	 */
	public static long MOVEMENT_SPRITE;
	/**It represents the url of the ghosts sprites.
	 */
	public final static String GHOSTS_SPRITES = "resources/sprites/ghosts/";
	/**It represents the image of a pacdot.
	 */
	public final static Image PACDOT_IMAGE = new Image(new File("resources/sprites/food/pacdot.png").toURI().toString());
	/**It represents the image of a energizer..
	 */
	public final static Image ENERGIZER_IMAGE = new Image(new File("resources/sprites/food/energizer.png").toURI().toString());
	/**It represents the url of a ghost caught.
	 */
	public final static String CAUGHT = "resources/sprites/pacman/caught/";

	@FXML private Button startPlayPauseButton;
	@FXML private Label highScoreLabel;
	@FXML private Label scoreLabel;
	@FXML private AnchorPane map;
	@FXML private FlowPane livesContainer;
	@FXML private FlowPane bonusFruitsContainer;
	@FXML private ImageView inky;
	@FXML private ImageView pinky;
	@FXML private ImageView clyde;
	@FXML private ImageView blinky;
	@FXML private ImageView pacman;
	@FXML private ImageView readyImage;
	@FXML private ImageView gameOverImage;
	@FXML private ImageView bonusImage;
	@FXML private ImageView key;
	@FXML private ImageView bell;
	@FXML private ImageView galaxian;
	@FXML private ImageView melon;
	@FXML private ImageView apple;
	@FXML private ImageView peach;
	@FXML private ImageView strawberry;
	@FXML private ImageView cherry;
	@FXML private Rectangle blackSquare2;
	@FXML private Rectangle blackSquare1;

	/**It represents the actual game object.
	 */
	private Game game;

	/**It represents the thread for the actual Pacman.
	 */
	private PacmanThread pacmanThread;
	/**It represents the thread for the actual blinky ghost.
	 */
	private GhostThread blinkyThread;
	/**It represents the thread for the actual inky ghost.
	 */
	private GhostThread inkyThread;
	/**It represents the thread for the actual clyde ghost.
	 */
	private GhostThread clydeThread;
	/**It represents the thread for the actual pinky ghost.
	 */
	private GhostThread pinkyThread;

	/**It represents the sound of the intro.
	 */
	private AudioClip intro;
	/**It represents the sound of a eaten dot.
	 */
	private AudioClip eatDot;
	/**It represents the sound of a eaten fruit.
	 */
	private AudioClip eatFruit;
	/**It represents the sound of a eaten ghost.
	 */
	private AudioClip eatGhost;
	/**It represents the sound of a extra live.
	 */
	private AudioClip extraLive;
	/**It represents the sound of the Pacman death.
	 */
	private AudioClip death;

	/**It represents if the game is in pause or not.
	 */
	private boolean onPause;

	/**This initializes all the characters and the maze. 
	 */
	@FXML
	public void initialize() {
		try {
			game = new Game();
		} catch (IOException e) {
			e.printStackTrace();
		}

		readyImage.setVisible(false);
		gameOverImage.setVisible(false);
		bonusImage.setVisible(false);

		pacman.relocate(game.getPacman().getPosX(), game.getPacman().getPosY());
		blinky.relocate(game.getBlinky().getPosX(), game.getBlinky().getPosY());
		clyde.relocate(game.getClyde().getPosX(), game.getClyde().getPosY());
		pinky.relocate(game.getPinky().getPosX(), game.getPinky().getPosY());
		inky.relocate(game.getInky().getPosX(), game.getInky().getPosY());

		initAudios();
		setListenersAndBindings();

		onPause = true;
		refreshLivesCounter();
		for(Node node: bonusFruitsContainer.getChildren()) {
			node.setVisible(false);
		}
		cherry.setVisible(true);
		startThreads();
	}
	/**This initializes all the needed audio media.
	 */
	private void initAudios() {
		intro = new AudioClip(new File("resources/audio/intro.mp3").toURI().toString());
		eatDot = new AudioClip(new File("resources/audio/eat_dot.mp3").toURI().toString());
		eatFruit = new AudioClip(new File("resources/audio/eat_fruit.mp3").toURI().toString());
		eatGhost = new AudioClip(new File("resources/audio/eat_ghost.mp3").toURI().toString());
		extraLive = new AudioClip(new File("resources/audio/extrapac.mp3").toURI().toString());
		death = new AudioClip(new File("resources/audio/pacman_death.mp3").toURI().toString());
	}
	/**This initializes all the needed listeners and bindings.
	 */
	private void setListenersAndBindings() {
		game.getFood().forEach(new BiConsumer<Coordinate, Food>() {
			@Override
			public void accept(Coordinate t, Food u) {
				ImageView food = bonusImage;
				if(u.getType() != Food.BONUS) {
					food = new ImageView();
					map.getChildren().add(food);
				}
				food.relocate(t.getX(), t.getY());
				food.visibleProperty().bind(u.getNotEaten());
				ChangeListener<Boolean> eatDotsListener = (obs, oldval, newval) -> {if(!newval) {
					eatDot.play();
					if(game.getCurrentLevel().getDotsLeft() == 0) {
						onPause = true;
						game.restartMap();
						game.setCurrentStage(game.getCurrentStage() + 1);
						setGUItoInitialState();
						startPlayPauseButtonPressed(null);
					}
				}};
				switch(u.getType()) {
				case Food.BONUS:
					u.getNotEaten().addListener((obs, oldvalue, newvalue) -> {if(!newvalue) {
						eatFruit.play();
					}});
					break;
				case Food.ENERGIZER:
					food.setImage(ENERGIZER_IMAGE);
					u.getNotEaten().addListener(eatDotsListener);
					break;
				case Food.PACDOT:
					food.setImage(PACDOT_IMAGE);
					u.getNotEaten().addListener(eatDotsListener);
					break;
				default:
					break;
				}
			}
		});

		ChangeListener<Boolean> eatGhostListener = (obs, oldval, newval) -> {if(newval) {
			eatGhost.play();
		}
		};
		game.getBlinky().isGoingHome().addListener(eatGhostListener);
		game.getInky().isGoingHome().addListener(eatGhostListener);
		game.getPinky().isGoingHome().addListener(eatGhostListener);
		game.getClyde().isGoingHome().addListener(eatGhostListener);

		if(System.getProperty("os.name").toLowerCase().contains("windows")) {
			blackSquare1.relocate(blackSquare1.getLayoutX()+5, blackSquare1.getLayoutY()+5);
			blackSquare2.relocate(blackSquare2.getLayoutX()+5, blackSquare2.getLayoutY()+5);
			readyImage.relocate(readyImage.getLayoutX()+5, readyImage.getLayoutY()+5);
			gameOverImage.relocate(gameOverImage.getLayoutX()+5, gameOverImage.getLayoutY()+5);
		}
		game.getPacman().getLives().addListener((obs, oldval, newval) ->  {if(Integer.compare(newval.intValue(), oldval.intValue()) > 0) {
			extraLive.play();
		}
		}) ;
	}

	/**This changes the direction when a key is pressed in the system input.
	 * @param event is a event related with the pressing of a determinate key in the system input.
	 */
	@FXML
	public void changeDirection(KeyEvent event) {
		String key = event.getCode().getName().toLowerCase();
		if(key.equals("up") || key.equals("i") || key.equals("w")) {
			game.getPacman().setRequestedDirection(Direction.UP);
		} else if(key.equals("down") || key.equals("k") || key.equals("s")) {
			game.getPacman().setRequestedDirection(Direction.DOWN);
		} else if(key.equals("right") || key.equals("l") || key.equals("d")) {
			game.getPacman().setRequestedDirection(Direction.RIGHT);
		} else if(key.equals("left") || key.equals("j") || key.equals("a")) {
			game.getPacman().setRequestedDirection(Direction.LEFT);
		}
	}

	/**This shows the leaderboard.
	 * @param event is an action even related with the pressing of the high score button.
	 */
	@FXML
	public void highScoresButtonPressed(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("leaderboard.fxml"));
			Scene s = new Scene(root);
			Stage st = new Stage();
			st.setScene(s);
			st.setTitle("Leaderboard");
			if(System.getProperty("os.name").toLowerCase().contains("windows")) {
				st.setWidth(st.getWidth()-5);
			}
			st.getIcons().add(new Image(new File("resources/sprites/pacman/movements/1.png").toURI().toString()));
			Window w = pacman.getParent().getScene().getWindow();
			st.setX(w.getX()+495);
			st.setY(w.getY());
			st.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**This shows info about the game.
	 * @param event is an action even related with the pressing of the info button.
	 */
	@FXML
	public void informationButtonPressed(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("info.fxml"));
			Scene s = new Scene(root);
			Stage st = new Stage();
			st.setScene(s);
			st.setResizable(false);
			st.getIcons().add(new Image(new File("resources/sprites/pacman/movements/1.png").toURI().toString()));
			st.show();
		} catch (IOException e) {
			// c:
		}
	}

	/**This starts the game.
	 * @param event is an action even related with the pressing of the play start button.
	 */
	@FXML
	public void startPlayPauseButtonPressed(ActionEvent event) {
		setOnPause(!onPause);
		try {
			FileInputStream fis = new FileInputStream(LeaderboardController.LEADER_BOARD_PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<Player> lb = (ArrayList<Player>)ois.readObject();
			highScoreLabel.setText(lb.get(0).getScore()+"");
			fis.close();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
		}
		gameOverImage.setVisible(false);
		if(!onPause) {
			onPause = true;
			readyImage.setVisible(true);
			if(game.getCurrentLevel().getDotsLeft() == game.getInitialNumberOfDots()) { //no dots eaten in the stage
				bonusImage.setImage(new Image(new File("resources/sprites/food/bonus/"+game.getCurrentLevel().getBonus()+".png").toURI().toString()));
				if(game.getCurrentLevel().getStage() == 1) { //plays intro sound in the first stage
					intro.play();
				}
			}
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					readyImage.setVisible(false);
					MOVEMENT_COUNTER++;
					onPause = false;
					if(game.allGhostsInTheirHouse()) {
						game.startSequence();
					}
				}
			};
			Timer timer = new Timer("Timer");
			long delay = 4000;
			timer.schedule(task, delay);
		}
	}

	/**This starts the Pacman and ghosts threads.
	 */
	private void startThreads() {
		pacmanThread = new PacmanThread(this);
		blinkyThread = new GhostThread(this, game.getBlinky().getName());
		inkyThread = new GhostThread(this, game.getInky().getName());
		pinkyThread = new GhostThread(this, game.getPinky().getName());
		clydeThread = new GhostThread(this, game.getClyde().getName());

		pacmanThread.start();
		blinkyThread.start();
		inkyThread.start();
		pinkyThread.start();
		clydeThread.start();
	}

	/**This refresh the image of a determinate ghost that depends of the direction of its movement.
	 * @param ghost is a ghost that could be any of the four ghosts created.
	 */
	public void refreshGhostImage(Ghost ghost) {
		if(PrimaryStageController.MOVEMENT_COUNTER%5 == 0) {
			ImageView ghostImage = getGhostImage(ghost.getName());
			if(ghost.isFrightened()) {
				int num = ((PrimaryStageController.MOVEMENT_SPRITE%2)==0?0:2);
				if(game.getFrightenedCountdown() < 2000 && MOVEMENT_SPRITE % 4 == 0) {
					num++;
				}
				ghostImage.setImage(new Image(new File(PrimaryStageController.GHOSTS_SPRITES+"vulnerable"+File.separator+num+".png").toURI().toString()));
			} else {
				long number = (PrimaryStageController.MOVEMENT_COUNTER%2);
				String dir = "";
				switch(ghost.getDirection()) {
				case DOWN:
					dir = "d";
					break;
				case LEFT:
					dir = "l";
					break;
				case RIGHT:
					dir = "r";
					break;
				case UP:
					dir = "u";
					break;
				}
				if(ghost.isGoingHome().get()) {
					ghostImage.setImage(new Image(new File(PrimaryStageController.GHOSTS_SPRITES+"eyes"+File.separator+dir+".png").toURI().toString()));
				} else {
					ghostImage.setImage(new Image(new File(PrimaryStageController.GHOSTS_SPRITES+ghost.getName()+File.separator+dir+number+".png").toURI().toString()));
				}
			}
		}
	}

	/**This sets all the gui components in their initial state.
	 */
	public void setGUItoInitialState() {
		pacman.setImage(new Image(new File(PacmanThread.MOVEMENTS+0+".png").toURI().toString()));
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				game.setCharactersToInitialTiles();
				pacman.setVisible(true);
				blinky.setVisible(true);
				pinky.setVisible(true);
				inky.setVisible(true);
				clyde.setVisible(true);
				pacman.relocate(game.getPacman().getPosX(), game.getPacman().getPosY());
				blinky.relocate(game.getBlinky().getPosX(), game.getBlinky().getPosY());
				inky.relocate(game.getInky().getPosX(), game.getInky().getPosY());
				pinky.relocate(game.getPinky().getPosX(), game.getPinky().getPosY());
				clyde.relocate(game.getClyde().getPosX(), game.getClyde().getPosY());
			}
		};
		Timer timer = new Timer("Timer");
		timer.schedule(task, 3000);

		String bonus = game.getCurrentLevel().getBonus();
		cherry.setVisible(true);
		if(Level.STRAWBERRY.equals(bonus) ) {
			strawberry.setVisible(true);
		}
		if(Level.PEACH.equals(bonus)) {
			peach.setVisible(true);
		}
		if(Level.APPLE.equals(bonus)) {
			apple.setVisible(true);
		}
		if(Level.MELON.equals(bonus)) {
			melon.setVisible(true);
		}
		if(Level.GALAXIAN.equals(bonus)) {
			galaxian.setVisible(true);
		}
		if(Level.BELL.equals(bonus)) {
			bell.setVisible(true);
		}
		if(Level.KEYS.equals(bonus)) {
			key.setVisible(true);
		}
	}

	/**This allows to obtain the respective image of the specified ghost.
	 * @param name is a string that represents the name of one of the four ghosts.
	 * @return the respective image of the specified ghost.
	 */
	public ImageView getGhostImage(String name) {
		if(name.equalsIgnoreCase(game.getBlinky().getName())) {
			return blinky;
		} else if(name.equalsIgnoreCase(game.getInky().getName())) {
			return inky;
		} else if(name.equalsIgnoreCase(game.getPinky().getName())) {
			return pinky;
		} else {
			return clyde;
		}
	}

	/**This refresh the lives counter when Pacman gets or loss one.
	 */
	public void refreshLivesCounter() {
		for(Node node : livesContainer.getChildren()) {
			node.setVisible(false);
		}
		int lives = game.getPacman().getLives().get();
		for(int i = 0; i < lives; i++) {
			livesContainer.getChildren().get(i).setVisible(true);
		}
	}

	/**This opens the players register saved in the system.
	 */
	public void openPlayerRegister() {
		ArrayList<Player> lb = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(LeaderboardController.LEADER_BOARD_PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);
			lb = (ArrayList<Player>)ois.readObject();
			fis.close();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			//c:
		}


		if(lb.size() < 10 || game.getScore() > lb.get(lb.size()-1).getScore()) {
			try {
				onPause = true;
				FXMLLoader fxmll = new FXMLLoader(getClass().getResource("nameregister.fxml"));
				Parent root = fxmll.load();
				NameRegisterController nrc = fxmll.getController();
				nrc.setScore(game.getScore());
				nrc.setStage(game.getCurrentStage()+1);
				Scene s = new Scene(root);
				Stage st = new Stage();
				st.setScene(s);
				st.setResizable(false);
				st.initOwner(pacman.getParent().getScene().getWindow());
				st.initModality(Modality.WINDOW_MODAL);
				st.getIcons().add(new Image(new File("resources/sprites/pacman/movements/1.png").toURI().toString()));
				st.showAndWait();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		game.getPacman().setLives(3);
		game.setCurrentStage(0);
		game.restartMap();
		game.setScore(0);
		scoreLabel.setText("0");
		onPause = true;

		for(Node node : bonusFruitsContainer.getChildren()) {
			node.setVisible(false);
		}
		cherry.setVisible(true);

		setGUItoInitialState();
	}

	/**Allows to get a FlowPane that represents the lives container.
	 * @return a FlowPane that represents the lives container.
	 */
	public FlowPane getLivesContainer() {
		return livesContainer;
	}

	/**Allows to get a FlowPane that represents the fruits container.
	 * @return  FlowPane that represents the fruits container.
	 */
	public FlowPane getBonusFruitsContainer() {
		return bonusFruitsContainer;
	}

	/**Allows to get an ImageView that represents inky ghost.
	 * @return an ImageView that represents inky ghost.
	 */
	public ImageView getInky() {
		return inky;
	}

	/**Allows to get an ImageView that represents pinky ghost.
	 * @return an ImageView that represents pinky ghost.
	 */
	public ImageView getPinky() {
		return pinky;
	}

	/**Allows to get an ImageView that represents clyde ghost.
	 * @return an ImageView that represents clyde ghost.
	 */
	public ImageView getClyde() {
		return clyde;
	}

	/**Allows to get an ImageView that represents blinky ghost.
	 * @return an ImageView that represents blinky ghost.
	 */
	public ImageView getBlinky() {
		return blinky;
	}

	/**Allows to get an ImageView that represents Pacman.
	 * @return an ImageView that represents Pacman.
	 */
	public ImageView getPacman() {
		return pacman;
	}

	/**Allows to get an ImageView that represents the get ready sign.
	 * @return an ImageView that represents the get ready sign.
	 */
	public ImageView getReadyImage() {
		return readyImage;
	}

	/**Allows to get an ImageView that represents the game over sign.
	 * @return an ImageView that represents the game over sign.
	 */
	public ImageView getGameOverImage() {
		return gameOverImage;
	}

	/**Allows to get an ImageView that represents the bonus sign.
	 * @return an ImageView that represents the bonus sign.
	 */
	public ImageView getBonusImage() {
		return bonusImage;
	}

	/**Allows to get an ImageView that represents the key sign.
	 * @return an ImageView that represents the key sign.
	 */
	public ImageView getKey() {
		return key;
	}

	/**Allows to get an ImageView that represents the bell sign.
	 * @return an ImageView that represents the bell sign.
	 */
	public ImageView getBell() {
		return bell;
	}

	/**Allows to get an ImageView that represents the galaxian sign.
	 * @return an ImageView that represents the galaxian sign.
	 */
	public ImageView getGalaxian() {
		return galaxian;
	}

	/**Allows to get an ImageView that represents the melon sign.
	 * @return an ImageView that represents the melon sign.
	 */
	public ImageView getMelon() {
		return melon;
	}

	/**Allows to get an ImageView that represents the apple sign.
	 * @return an ImageView that represents the apple sign.
	 */
	public ImageView getApple() {
		return apple;
	}

	/**Allows to get an ImageView that represents the peach sign.
	 * @return an ImageView that represents the peach sign.
	 */
	public ImageView getPeach() {
		return peach;
	}

	/**Allows to get an ImageView that represents the strawberry sign.
	 * @return an ImageView that represents the strawberry sign.
	 */
	public ImageView getStrawberry() {
		return strawberry;
	}

	/**Allows to get an ImageView that represents the cherry sign.
	 * @return an ImageView that represents the cherry sign.
	 */
	public ImageView getCherry() {
		return cherry;
	}

	/**Allows to get a game that represents the actual game.
	 * @return a game that represents the actual game.
	 */
	public Game getGame() {
		return game;
	}

	/**Allows to obtain a boolean that represents if the game is in pause or not.
	 * @return a boolean that represents if the game is in pause or not.
	 */
	public boolean isOnPause() {
		return onPause;
	}

	/**Allows to set a boolean that represents if the game is in pause or not.
	 * @param onPause is a boolean that represents if the game is in pause or not.
	 */
	public void setOnPause(boolean onPause) {
		this.onPause = onPause;
		Platform.runLater(() -> {if(onPause) {
			startPlayPauseButton.setText("Start");
		} else {
			startPlayPauseButton.setText("Pause");
		}
		});
	}

	/**Allows to get a label that represents the actual score in the game.
	 * @return a label that represents the actual score in the game.
	 */
	public Label getScoreLabel() {
		return scoreLabel;
	}

	/**Allows to get an AudioClip that represents the sound of a eaten dot.
	 * @return an AudioClip that represents the sound of a eaten dot.
	 */
	public AudioClip getEatDot() {
		return eatDot;
	}

	/**Allows to get an AudioClip that represents the sound of a eaten fruit.
	 * @return an AudioClip that represents the sound of a eaten fruit.
	 */
	public AudioClip getEatFruit() {
		return eatFruit;
	}

	/**Allows to get an AudioClip that represents the sound of a eaten ghost.
	 * @return an AudioClip that represents the sound of a eaten ghost.
	 */
	public AudioClip getEatGhost() {
		return eatGhost;
	}

	/**Allows to get an AudioClip that represents the sound of a extra live.
	 * @return an AudioClip that represents the sound of a extra live.
	 */
	public AudioClip getExtraLive() {
		return extraLive;
	}

	/**Allows to get an AudioClip that represents the sound of the Pacman death.
	 * @return an AudioClip that represents the sound of the Pacman death.
	 */
	public AudioClip getDeath() {
		return death;
	}
}
