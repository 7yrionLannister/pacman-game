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
    
    private NameRegisterController nrc;
    
    @FXML
	public void initialize() {
    	rank.setText(nrc.getPlayer().getRank());
    	score.setText(nrc.getPlayer().getScore() + "");
    	stage.setText(nrc.getPlayer().getStage() + "");
    	name.setText(nrc.getPlayer().getName());
    }
    
}

