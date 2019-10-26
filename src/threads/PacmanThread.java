package threads;

import javafx.scene.image.ImageView;
import model.Game;
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
	}
	
	@Override
	public void run() {
		
	}
}
