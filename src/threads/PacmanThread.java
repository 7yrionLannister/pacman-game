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
	public static long movement_counter; //TODO eventualmente, pon esto donde todos los hilos lo puedan usar, sera util
	private Controller c;
	private ImageView pacmanImage;
	private Game g;
	private Pacman pacman;
	private int movementSprite;

	public PacmanThread(Controller c) {
		this.c = c;
		pacmanImage = c.getPacman();
		g = c.getGame();
		pacman = g.getPacman();
		movementSprite = 0;
		setDaemon(true);
	}

	@Override
	public void run() {
		while(!c.isOnPause()) {
			pacmanImage.setImage(new Image(new File(MOVEMENTS+movementSprite+".png").toURI().toString()));
			movement_counter++;
			if(movement_counter % 3== 0) {
				movementSprite++;
				if(movementSprite > 3) {
					movementSprite = 0;
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
