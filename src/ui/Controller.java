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
	void printMapCoordinates(MouseEvent event) {    	
		System.out.println(event.getX() + "," + event.getY());
	}

}
