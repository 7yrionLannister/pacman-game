package ui;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Player;

public class LeaderboardController implements Initializable{

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
    private TextField nameField;

    @FXML
    private Button register;

    @FXML
    private Label playerState;

    @FXML
    private ImageView inky;

    @FXML
    private ImageView pinky;

    @FXML
    private ImageView clyde;

    @FXML
    private ImageView blinky;
    
    private ObservableList<Player> pol;
    
    private Controller c;

    public LeaderboardController()  throws FileNotFoundException  {
    	
	}
    
    @FXML
    void RegisterPlayer(ActionEvent event) {
    	String nameP = nameField.getText();
    	Player p = new Player("1ST", 32,44, nameP);
    	pol.add(p);
    	refreshTable();
    }
    
    public void refreshTable() {
    	lb.refresh();
    	if(pol.size() > 0) {
    	Collections.sort(pol);
    	lb.setItems(pol);
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pol = FXCollections.observableArrayList();
    	inky = new ImageView(new Image(new File("resources/sprites/ghosts/inky/r0.png").toURI().toString()));
    	pinky = new ImageView(new Image(new File("resources/sprites/ghosts/pinky/r0.png").toURI().toString()));
    	clyde = new ImageView(new Image(new File("resources/sprites/ghosts/clyde/r0.png").toURI().toString()));
    	blinky = new ImageView(new Image(new File("resources/sprites/ghosts/blinky/r0.png").toURI().toString()));
    	rank.setCellValueFactory(new PropertyValueFactory<Player, String>("RANK"));
    	score.setCellValueFactory(new PropertyValueFactory<Player, Integer>("SCORE"));
    	stage.setCellValueFactory(new PropertyValueFactory<Player, Integer>("STAGE"));
    	name.setCellValueFactory(new PropertyValueFactory<Player, String>("NAME"));
	}
}

