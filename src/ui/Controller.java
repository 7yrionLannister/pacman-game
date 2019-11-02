package ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import model.Coordinate;
import model.Direction;
import model.Game;
import threads.BlinkyThread;
import threads.PacmanThread;


public class Controller {
	public static long MOVEMENT_COUNTER;
	public static long MOVEMENT_SPRITE;
	public final static String GHOSTS_SPRITES = "resources/sprites/ghosts/";
	
	@FXML
	private FlowPane livesContainer;

	@FXML
	private FlowPane bonusFruitsContainer;

	@FXML
	private ImageView inky;

	@FXML
	private ImageView pinky;

	@FXML
	private ImageView clyde;

	@FXML
	private ImageView blinky;

	@FXML
	private ImageView pacman;

	@FXML
	private ImageView readyImage;

	@FXML
	private ImageView gameOverImage;

	@FXML
	private ImageView bonusImage;

	@FXML
	private ImageView key;

	@FXML
	private ImageView bell;

	@FXML
	private ImageView galaxian;

	@FXML
	private ImageView melon;

	@FXML
	private ImageView apple;

	@FXML
	private ImageView peach;

	@FXML
	private ImageView strawberry;

	@FXML
	private ImageView cherry;

	private Game game;

	private PacmanThread pt;
	private BlinkyThread bt;

	private boolean onPause;

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
		//TODO hacer lo mismo con cada personaje y ademas tambien acomodar los cuadritos negros del tunel si se detecta que es MacOS 
		onPause = true;
	}

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

	@FXML
	public void printMapCoordinates(MouseEvent event) {
		System.out.println(event.getX()+","+event.getY());
//		System.out.println(pacman.getLayoutX()+","+pacman.getLayoutY());
		System.out.println("bonus: "+bonusImage.getLayoutX()+","+bonusImage.getLayoutY());
	}

	@FXML
	public void highScoresButtonPressed(ActionEvent event) {

	}

	@FXML
	public void newGameButtonPressed(ActionEvent event) {
		System.out.println("hola mundo");
	}

	@FXML
	public void startPlayPauseButtonPressed(ActionEvent event) {
		//newGameButtonPressed(event);
		onPause = !onPause;
		if(!onPause) {
			pt = new PacmanThread(this);
			bt = new BlinkyThread(this);
			pt.start();
			bt.start();
		}
	}

	public FlowPane getLivesContainer() {
		return livesContainer;
	}

	public FlowPane getBonusFruitsContainer() {
		return bonusFruitsContainer;
	}

	public ImageView getInky() {
		return inky;
	}

	public ImageView getPinky() {
		return pinky;
	}

	public ImageView getClyde() {
		return clyde;
	}

	public ImageView getBlinky() {
		return blinky;
	}

	public ImageView getPacman() {
		return pacman;
	}

	public ImageView getReadyImage() {
		return readyImage;
	}

	public ImageView getGameOverImage() {
		return gameOverImage;
	}

	public ImageView getBonusImage() {
		return bonusImage;
	}

	public ImageView getKey() {
		return key;
	}

	public ImageView getBell() {
		return bell;
	}

	public ImageView getGalaxian() {
		return galaxian;
	}

	public ImageView getMelon() {
		return melon;
	}

	public ImageView getApple() {
		return apple;
	}

	public ImageView getPeach() {
		return peach;
	}

	public ImageView getStrawberry() {
		return strawberry;
	}

	public ImageView getCherry() {
		return cherry;
	}

	public Game getGame() {
		return game;
	}

	public boolean isOnPause() {
		return onPause;
	}

	public void setOnPause(boolean onPause) {
		this.onPause = onPause;
	}
}
