package threads;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;
import model.Ghost;
import model.Level;
import ui.Controller;

public class GhostThread extends Thread {
	public final static String MOVEMENTS = "resources/sprites/ghosts/blinky";
	private Controller c;
	private Game g;
	private Ghost blinky;
	private ImageView blinkyImage;

	public GhostThread(Controller c) {
		this.c = c;
		blinkyImage = c.getBlinky();
		g = c.getGame();
		blinky = g.getBlinky();
		blinkyImage = c.getBlinky();
		setDaemon(true);
	}

	@Override
	public void run() {
		while(!c.isOnPause()) {
			blinkyImage.setImage(new Image(new File(MOVEMENTS+Controller.MOVEMENT_SPRITE+".png").toURI().toString()));
			g.movePacman();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if(blinky.isFrightened()) {

					} else {
						switch(blinky.getDirection()) {
						case DOWN:
							blinkyImage.setRotate(90);
							break;
						case LEFT:
							blinkyImage.setRotate(180);
							break;
						case RIGHT:
							blinkyImage.setRotate(0);
							break;
						case UP:
							blinkyImage.setRotate(270);
							break;
						}
						blinkyImage.relocate(blinky.getPosX(), blinky.getPosY());	
					}
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
