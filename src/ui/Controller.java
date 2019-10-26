package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import model.Game;

public class Controller {

	@FXML
	private FlowPane livesContainer;

	@FXML
	private FlowPane bonusFruitsContainer;

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
