package ui;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import model.Coordinate;
import model.Direction;
import model.Game;
import model.Ghost;
import model.Food;
import threads.GhostThread;
import threads.PacmanThread;


public class Controller {
	public static long MOVEMENT_COUNTER;
	public static long MOVEMENT_SPRITE;
	public final static String GHOSTS_SPRITES = "resources/sprites/ghosts/";
	public final static Image PACDOT_IMAGE = new Image(new File("resources/sprites/food/pacdot.png").toURI().toString());
	public final static Image ENERGIZER_IMAGE = new Image(new File("resources/sprites/food/energizer.png").toURI().toString());
	public final static Image BONUS_IMAGE = new Image(new File("resources/sprites/food/bonus/cherry.png").toURI().toString());
	public final static String CAUGHT = "resources/sprites/pacman/caught/";

	@FXML
	private Label highScoreLabel;

	@FXML
	private Label scoreLabel;

	@FXML
	private AnchorPane map;

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

	@FXML
	private Rectangle blackSquare2;

	@FXML
	private Rectangle blackSquare1;

	private Game game;

	private PacmanThread pacmanThread;
	private GhostThread blinkyThread;
	private GhostThread inkyThread;
	private GhostThread clydeThread;
	private GhostThread pinkyThread;

	private AudioClip intro;
	private AudioClip eatDot;
	private AudioClip eatFruit;
	private AudioClip eatGhost;
	private AudioClip extraLive;
	private AudioClip death;

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

		intro = new AudioClip(new File("resources/audio/intro.mp3").toURI().toString());
		eatDot = new AudioClip(new File("resources/audio/eat_dot.mp3").toURI().toString());
		eatFruit = new AudioClip(new File("resources/audio/eat_fruit.mp3").toURI().toString());
		eatGhost = new AudioClip(new File("resources/audio/eat_ghost.mp3").toURI().toString());
		extraLive = new AudioClip(new File("resources/audio/extrapac.mp3").toURI().toString());
		death = new AudioClip(new File("resources/audio/pacman_death.mp3").toURI().toString());
		game.getFood().forEach(new BiConsumer<Coordinate, Food>() {
			@Override
			public void accept(Coordinate t, Food u) {
				ImageView food = bonusImage;
				if(u.getType() != Food.BONUS) {
					food = new ImageView();
					map.getChildren().add(food);
				}
				food.relocate(t.getX(), t.getY());
				food.visibleProperty().bind(game.getFood().get(t).getNotEaten());
				ChangeListener<Boolean> eatDotsListener = new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						if(!newValue) {
							eatDot.play();
							if(game.getCurrentLevel().getDotsLeft() == 0) {
								onPause = true;
								game.restartMap();
								setGUItoInitialState();
								game.setCurrentStage(game.getCurrentStage() + 1);
							}
						}
					}
				};
				switch(u.getType()) {
				case Food.BONUS:
					food.setImage(BONUS_IMAGE);
					u.getNotEaten().addListener(new ChangeListener<Boolean>() {
						@Override
						public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
								Boolean newValue) {
							if(!newValue) {
								eatFruit.play();
							}
						}
					});
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

		ChangeListener<Boolean> eatGhostListener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
					Boolean newValue) {
				if(newValue) {
					eatGhost.play();
				}
			}
		};
		game.getBlinky().isGoingHome().addListener(eatGhostListener);
		game.getInky().isGoingHome().addListener(eatGhostListener);
		game.getPinky().isGoingHome().addListener(eatGhostListener);
		game.getClyde().isGoingHome().addListener(eatGhostListener);

		if(!game.isRunningLinux()) {
			blackSquare1.relocate(blackSquare1.getLayoutX()+5, blackSquare1.getLayoutY());
			blackSquare2.relocate(blackSquare2.getLayoutX()+5, blackSquare2.getLayoutY());
		}
		onPause = true;
		startThreads();
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
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("blinky image: "+blinky.getLayoutX()+","+blinky.getLayoutY()+" ~ blinky: "+game.getBlinky().getPosX()+","+game.getBlinky().getPosY());
		System.out.println(game.getBlinky().getPosition());
		System.out.println("inky image: "+inky.getLayoutX()+","+inky.getLayoutY()+" ~ inky: "+game.getInky().getPosX()+","+game.getInky().getPosY());
		System.out.println(game.getInky().getPosition());
		System.out.println("pinky image: "+pinky.getLayoutX()+","+pinky.getLayoutY()+" ~ pinky: "+game.getPinky().getPosX()+","+game.getPinky().getPosY());
		System.out.println(game.getPinky().getPosition());
		System.out.println("clyde image: "+clyde.getLayoutX()+","+clyde.getLayoutY()+" ~ clyde: "+game.getClyde().getPosX()+","+game.getClyde().getPosY());
		System.out.println(game.getClyde().getPosition());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@FXML
	public void highScoresButtonPressed(ActionEvent event) {
		openPlayerRegister();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("nameregister.fxml"));
			Scene s = new Scene(root);
			Stage st = new Stage();
			st.setScene(s);
			st.setResizable(false);
			st.initOwner(pacman.getParent().getScene().getWindow());
			st.initModality(Modality.WINDOW_MODAL);
			st.showAndWait();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	public void newGameButtonPressed(ActionEvent event) {
		System.out.println("hola mundo");
	}

	@FXML
	public void startPlayPauseButtonPressed(ActionEvent event) {
		onPause = !onPause;
		if(!onPause) {
			onPause = true;
			if(game.getCurrentLevel().getDotsLeft() == game.getInitialNumberOfDots()) { //no dots eaten in the stage
				readyImage.setVisible(true);
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
				}
			};
			Timer timer = new Timer("Timer");
			long delay = 4000;
			timer.schedule(task, delay);
		}
	}


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

	public Label getScoreLabel() {
		return scoreLabel;
	}

	public AudioClip getEatDot() {
		return eatDot;
	}

	public AudioClip getEatFruit() {
		return eatFruit;
	}

	public AudioClip getEatGhost() {
		return eatGhost;
	}

	public AudioClip getExtraLive() {
		return extraLive;
	}

	public AudioClip getDeath() {
		return death;
	}

	public void refreshGhostImage(Ghost ghost) {
		if(Controller.MOVEMENT_COUNTER%5 == 0) {
			ImageView ghostImage = getGhostImage(ghost.getName());
			if(ghost.isFrightened()) {
				int num = ((Controller.MOVEMENT_SPRITE%2)==0?0:2);
				if(game.getFrightenedCountdown() < 2000 && MOVEMENT_SPRITE % 4 == 0) {
					num++;
				}
				ghostImage.setImage(new Image(new File(Controller.GHOSTS_SPRITES+"vulnerable"+File.separator+num+".png").toURI().toString()));
			} else {
				long number = (Controller.MOVEMENT_COUNTER%2);
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
					ghostImage.setImage(new Image(new File(Controller.GHOSTS_SPRITES+"eyes"+File.separator+dir+".png").toURI().toString()));
				} else {
					ghostImage.setImage(new Image(new File(Controller.GHOSTS_SPRITES+ghost.getName()+File.separator+dir+number+".png").toURI().toString()));
				}
			}
		}
	}

	public void setGUItoInitialState() {
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

				startPlayPauseButtonPressed(null);
			}
		};
		Timer timer = new Timer("Timer");
		timer.schedule(task, 3000);
	}
	
	public void openPlayerRegister() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("leaderboard.fxml"));
			Scene s = new Scene(root);
			Stage st = new Stage();
			st.setScene(s);
			st.setResizable(false);
			st.initOwner(pacman.getParent().getScene().getWindow());
			st.initModality(Modality.WINDOW_MODAL);
			st.showAndWait();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
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
}
