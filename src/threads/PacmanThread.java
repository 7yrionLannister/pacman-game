package threads;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import model.Game;
import model.Level;
import model.Pacman;
import ui.Controller;


public class PacmanThread extends Thread {
	private Controller c;
	private ImageView pacmanImage;
	private Game g;
	private Pacman pacman;
	
	public PacmanThread(Controller c) {
		this.c = c;
		pacmanImage = c.getPacman();
		g = c.getGame();
		pacman = g.getPacman();
		setDaemon(true);
	}
	
	@Override
	public void run() {
		while(true) {
			pacman.moveForward();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					pacmanImage.relocate(pacman.getPosX(), pacman.getPosY());	
				}
			});
			try {
				long rate = (long)(g.getCurrentLevel().getPacmanSpeed()*Level.REFERENCE_SPEED);
				//TODO quitar + cuando se ternime de probar
				sleep(/*rate*/40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
