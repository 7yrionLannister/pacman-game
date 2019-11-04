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
	private Controller controller;
	private ImageView pacmanImage;
	private Game game;
	private Pacman pacman;
	private long rate = 0;
	public PacmanThread(Controller c) {
		this.controller = c;
		pacmanImage = c.getPacman();
		game = c.getGame();
		pacman = game.getPacman();
		Controller.MOVEMENT_SPRITE = 0;
		setDaemon(true);
	}

	@Override
	public void run() {
		while(!controller.isOnPause()) {
			pacmanImage.setImage(new Image(new File(MOVEMENTS+Controller.MOVEMENT_SPRITE+".png").toURI().toString()));
			Controller.MOVEMENT_COUNTER++;
			if(Controller.MOVEMENT_COUNTER % 3 == 0) {
				Controller.MOVEMENT_SPRITE++;
				if(Controller.MOVEMENT_SPRITE > 3) {
					Controller.MOVEMENT_SPRITE = 0;
				}
			}
			game.movePacman();
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
					Level level = game.getCurrentLevel();
					if(level.isFrightened()) {
						if(!game.atLeastAFrightenedGhost()) {
							//TODO sonido de alarma fantasmas asustados
							if(true) {
								//TODO sonido cuando se come un fantasma
							}
						}
						if(game.isEatingDots()) {
							rate = (long)((1 - level.getPacmanWithEnergizerEatingDotsSpeed())*Level.REFERENCE_SPEED);
						} else {
							rate = (long)((1 - level.getPacmanWithEnergizerSpeed())*Level.REFERENCE_SPEED);
						}
					}  else if(game.isEatingDots()){
						if(!controller.getEatDot().isPlaying()) {
							controller.getEatDot().play();
						}
						rate = (long)((1 - level.getPacmanEatingDotsSpeed())*Level.REFERENCE_SPEED);
					} else {
					rate = (long)((1 - level.getPacmanSpeed())*Level.REFERENCE_SPEED);
					}
					if(!game.getFood().get(game.getBonusTile()).getNotEaten().get() && pacman.getPosX() == game.getBonusTile().getX() && pacman.getPosY() == game.getBonusTile().getY()) {
						controller.getEatFruit().play();
						game.getFood().get(game.getBonusTile()).setNotEaten(false);
					}
					controller.getScoreLabel().setText(game.getScore()+"");
					pacmanImage.relocate(pacman.getPosX(), pacman.getPosY());	
				}
			});
			try {
				sleep(rate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
