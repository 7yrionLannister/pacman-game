package ui;


import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Player;

public class LeaderboardController {

    @FXML
    private TableView<String> lb;

    @FXML
    private TableColumn<Player, String> rank;

    @FXML
    private TableColumn<Player, Integer> stage;

    @FXML
    private TableColumn<Player, Integer> score;

    @FXML
    private TableColumn<Player, String> name;
    
    @FXML
	public void initialize() {
    	
    }
}

