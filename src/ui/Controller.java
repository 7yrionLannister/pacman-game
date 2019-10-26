package ui;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

public class Controller {

	@FXML
	private FlowPane livesContainer;

	@FXML
	private FlowPane bonusFruitsContainer;

	@FXML
	void printMapCoordinates(MouseEvent event) {    	
		System.out.println(event.getX() + "," + event.getY());
	}

}
