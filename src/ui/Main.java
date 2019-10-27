package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("pg.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Pac-Man");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false); //TODO esta linea causa que las imagenes salgan unos pixeles mas lejos del lugar que les corresponde en Windows
		                                  //TODO pero comentarla no es opcion porque si el usuario hace zoom la interfaz se desacomoda
	}

	public static void main(String[] args) {
		launch(args);
	}

}
