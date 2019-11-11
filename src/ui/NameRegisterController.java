package ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Player;


public class NameRegisterController {

	@FXML private TextField name;
	/**It represents the score of the player when this has died.
	 */
	private int score;
	/**It represents the stage where the player died.
	 */
	private int stage;
	
	/**This method registers a new Player when this has died in the game.
	 * @param event is an ActionEvent related with the pressing of a button. 
	 */
	@FXML
	public void register(ActionEvent event) {
		ArrayList<Player> lb = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(LeaderboardController.LEADER_BOARD_PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);
			lb = (ArrayList<Player>)ois.readObject();
			fis.close();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			//c:
		}

		lb.add(new Player("", score, stage, name.getText().toUpperCase()));
		try {
			Collections.sort(lb);
			if(lb.size() > 10) {
				lb.remove(10);
			}
			for(int i = 0; i < lb.size(); i++) {
				Player p = lb.get(i);
				String rank = ""+(i+1);
				if(i == 0) {
					rank += "ST";
				} else if(i == 1) {
					rank += "ND";
				} else if(i == 2) {
					rank += "RD";
				} else {
					rank += "TH";
				}
				p.setRank(rank);
			}
			FileOutputStream fos = new FileOutputStream(LeaderboardController.LEADER_BOARD_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(lb);
			fos.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage)name.getScene().getWindow();
		stage.close();
	}
	/**This method checks that the name of the player do not exceed the four characters. 
	 * @param event an KeyEvent related with the String introduced inside the associated text field.
	 */
	@FXML
	public void checkFourLetters(KeyEvent event) {
		char input = event.getCharacter().charAt(0);
		input = Character.toUpperCase(input);
		if(name.getText().length() == 4 || !((input >= 'A' && input <= 'Z') || input == '\b')) {
			event.consume();
		}
	}
	/**It allows to set an Integer that represents the score of the player in the game.
	 * @param score is an Integer that represents the score of the player in the game.
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**It allows to set an Integer that represents the stage where the player died.
	 * @param stage is an Integer that represents the stage where the player died.
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}
}
