package ui;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Player;

public class LeaderboardController {
	public final static String LEADER_BOARD_PATH = "resources"+File.separator+"leaderboard.pac";
	
    @FXML
    private TableView<Player> lb;
    
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
    	rank.setCellValueFactory(new PropertyValueFactory<Player, String>("RANK"));
    	score.setCellValueFactory(new PropertyValueFactory<Player, Integer>("SCORE"));
    	stage.setCellValueFactory(new PropertyValueFactory<Player, Integer>("STAGE"));
    	name.setCellValueFactory(new PropertyValueFactory<Player, String>("NAME"));
    	try {
    		FileInputStream fis = new FileInputStream(LEADER_BOARD_PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);
			lb.setItems(FXCollections.observableArrayList(((ArrayList<Player>)ois.readObject())));
			fis.close();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
		}
	}
}

