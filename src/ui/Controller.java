package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import model.Game;

public class Controller {

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
	
	@FXML
	public void initialize() {
		try {
			game = new Game();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void printMapCoordinates(MouseEvent event) {    	
		System.out.println(event.getX() + "," + event.getY());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(inky.getLayoutX()+","+inky.getLayoutY());
		System.out.println(pinky.getLayoutX()+","+pinky.getLayoutY());
		System.out.println(blinky.getLayoutX()+","+blinky.getLayoutY());
		System.out.println(clyde.getLayoutX()+","+clyde.getLayoutY());
		System.out.println(pacman.getLayoutX()+","+pacman.getLayoutY());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		inky.setLayoutX(54);inky.setLayoutY(37);
		//inky.relocate(301,318);
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
