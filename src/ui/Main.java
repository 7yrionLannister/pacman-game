package ui;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("pg.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Pac-Man");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.getIcons().add(new Image(new File("resources/sprites/pacman/movements/1.png").toURI().toString()));
		primaryStage.setResizable(false);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
