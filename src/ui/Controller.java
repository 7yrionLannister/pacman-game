package ui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import model.Coordinate;
import model.Game;
import model.Pacman.Direction;
import threads.PacmanThread;


public class Controller {
	static int coor = 0;
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
	private ImageView info;

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
	
	@FXML
	public void initialize() {
		try {
			game = new Game();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pt = new PacmanThread(this);
		pt.start();
		//TODO delete this line as it is just for tests
		inky.relocate(44,34);
	}

	@FXML
	public void changeDirection(KeyEvent event) {
		/*double pacmanX = pacman.getLayoutX();
		double pacmanY = pacman.getLayoutY();
		String key = event.getCode().getName().toLowerCase();
		if(key.equals("up") || key.equals("i") || key.equals("w")) {
			pacmanY--;
		} else if(key.equals("down") || key.equals("k") || key.equals("s")) {
			pacmanY++;
		} else if(key.equals("right") || key.equals("l") || key.equals("d")) {
			pacmanX++;
		} else if(key.equals("left") || key.equals("j") || key.equals("a")) {
			pacmanX--;
		}
		pacman.relocate(pacmanX, pacmanY);
		if(key.equals("1")) {
			ArrayList<Coordinate> c = game.getCoordinates();
			System.out.println(c.get(coor).getX()+","+c.get(coor).getY());
			pacman.relocate(c.get(coor).getX(), c.get(coor).getY());
			coor++;
		}*/
		String key = event.getCode().getName().toLowerCase();
		if(key.equals("up") || key.equals("i") || key.equals("w")) {
			game.getPacman().setDirection(Direction.UP);
		} else if(key.equals("down") || key.equals("k") || key.equals("s")) {
			game.getPacman().setDirection(Direction.DOWN);
		} else if(key.equals("right") || key.equals("l") || key.equals("d")) {
			game.getPacman().setDirection(Direction.RIGHT);
		} else if(key.equals("left") || key.equals("j") || key.equals("a")) {
			game.getPacman().setDirection(Direction.LEFT);
		}
	}

	@FXML
	public void printMapCoordinates(MouseEvent event) {
		/*System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(inky.getLayoutX()+","+inky.getLayoutY());
		System.out.println(pinky.getLayoutX()+","+pinky.getLayoutY());
		System.out.println(blinky.getLayoutX()+","+blinky.getLayoutY());
		System.out.println(clyde.getLayoutX()+","+clyde.getLayoutY());
		System.out.println(pacman.getLayoutX()+","+pacman.getLayoutY());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");*/
		System.out.println(pacman.getLayoutX()+","+pacman.getLayoutY());
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

	public ImageView getInfo() {
		return info;
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
}
