package threads;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;
import model.Level;
import model.Pacman;
import ui.Controller;


public class PacmanThread extends Thread {
	public final static String MOVEMENTS = "resources/sprites/pacman/movements/";
	private Controller c;
	private ImageView pacmanImage;
	private Game g;
	private Pacman pacman;

	public PacmanThread(Controller c) {
		this.c = c;
		pacmanImage = c.getPacman();
		g = c.getGame();
		pacman = g.getPacman();
		Controller.MOVEMENT_SPRITE = 0;
		setDaemon(true);
	}

	@Override
	public void run() {
		while(!c.isOnPause()) {
			pacmanImage.setImage(new Image(new File(MOVEMENTS+Controller.MOVEMENT_SPRITE+".png").toURI().toString()));
			Controller.MOVEMENT_COUNTER++;
			if(Controller.MOVEMENT_COUNTER % 3 == 0) {
				Controller.MOVEMENT_SPRITE++;
				if(Controller.MOVEMENT_SPRITE > 3) {
					Controller.MOVEMENT_SPRITE = 0;
				}
			}
			g.movePacman();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					switch(pacman.getDirection()) {
					case DOWN:
						pacmanImage.setRotate(90);
						break;
					case LEFT:
						pacmanImage.setRotate(180);
						break;
					case RIGHT:
						pacmanImage.setRotate(0);
						break;
					case UP:
						pacmanImage.setRotate(270);
						break;
					}
					pacmanImage.relocate(pacman.getPosX(), pacman.getPosY());	
				}
			});
			try {
				Level level = g.getCurrentLevel();
				long rate = 0;
				if(level.isFrightened() && g.isEatingDots()) {
					rate = (long)((1 - level.getPacmanWithEnergizerEatingDotsSpeed())*Level.REFERENCE_SPEED);
				} else if(level.isFrightened()) {
					rate = (long)((1 - level.getPacmanWithEnergizerSpeed())*Level.REFERENCE_SPEED);
				}  else if(g.isEatingDots()){
					rate = (long)((1 - level.getPacmanEatingDotsSpeed())*Level.REFERENCE_SPEED);
				} else {
					rate = (long)((1 - level.getPacmanSpeed())*Level.REFERENCE_SPEED);
				}
				sleep(rate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
