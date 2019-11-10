package ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.collections.FXCollections;
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
    
    private String rank;
	
	private int score;
	
	private int stage;
    
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
    	ArrayList<Player> lb = new ArrayList<>();
    	try {
    		FileInputStream fis = new FileInputStream(LeaderboardController.LEADER_BOARD_PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);
			lb = (ArrayList<Player>)ois.readObject();
			fis.close();
			ois.close();
			
			lb.add(new Player(rank, score, stage, name.getText()));
			FileOutputStream fos = new FileOutputStream(LeaderboardController.LEADER_BOARD_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(lb);
			fos.close();
			oos.close();
		} catch (IOException | ClassNotFoundException e) {
		}
    }
    
    public void setThingsHere() {
    	//TODO implementar
    }
}
