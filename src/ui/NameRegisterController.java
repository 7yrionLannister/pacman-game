package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Player;

public class NameRegisterController {

    @FXML
    private Button register;

    @FXML
    private TextField name;
    
    private String playerName;
    
    private Player p;
    
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
    	/*playerName = name.getText();
    	p = new Player("1ST", c.getGame().getScore(), c.getGame().getLevels().size(), playerName);
        Stage stage = (Stage) register.getScene().getWindow();
        stage.close();*/
    }
    
    public Player getPlayer() {
    	return p;
    }
    
}
