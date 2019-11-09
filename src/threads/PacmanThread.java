package threads;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;
import model.Ghost;
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
		while(true) {
			if(!controller.isOnPause()) {
				Controller.MOVEMENT_COUNTER++;
				if((Controller.MOVEMENT_COUNTER*Controller.MOVEMENT_SPRITE) % 2 == 0) {
					pacmanImage.setImage(new Image(new File(MOVEMENTS+Controller.MOVEMENT_SPRITE+".png").toURI().toString()));
				}
				if(Controller.MOVEMENT_COUNTER % 3 == 0) {
					Controller.MOVEMENT_SPRITE++;
					if(Controller.MOVEMENT_SPRITE > 3) {
						Controller.MOVEMENT_SPRITE = 0;
					}
				}
				if(pacman.isDying()) {
					controller.setOnPause(true);
					controller.getDeath().play();
					controller.getBlinky().setVisible(false);
					controller.getPinky().setVisible(false);
					controller.getInky().setVisible(false);
					controller.getClyde().setVisible(false);
					int sprite = 0;
					while(sprite < 13) {
						try {
							pacmanImage.setRotate(0);
							pacmanImage.setImage(new Image(new File(Controller.CAUGHT+sprite+".png").toURI().toString()));
							sprite++;
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					game.setCharactersToInitialTiles();
					controller.setGUItoInitialState();
					game.getPacman().setDying(false);
					controller.startPlayPauseButtonPressed(null);
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
							}
							if(game.isEatingDots()) {
								rate = level.getPacmanWithEnergizerEatingDotsSpeed();
							} else {
								rate = level.getPacmanWithEnergizerSpeed();
							}
						}  else if(game.isEatingDots()){
							rate = level.getPacmanEatingDotsSpeed();
						} else {
							rate = level.getPacmanSpeed();
						}
						controller.getScoreLabel().setText(game.getScore()+"");
						pacmanImage.relocate(pacman.getPosX(), pacman.getPosY());
						Ghost ghost = game.getBlinky();
						controller.getBlinky().relocate(ghost.getPosX(), ghost.getPosY());
						controller.refreshGhostImage(ghost);
						ghost = game.getInky();
						controller.getInky().relocate(ghost.getPosX(), ghost.getPosY());
						controller.refreshGhostImage(ghost);
						ghost = game.getPinky();
						controller.getPinky().relocate(ghost.getPosX(), ghost.getPosY());
						controller.refreshGhostImage(ghost);
						ghost = game.getClyde();
						controller.getClyde().relocate(ghost.getPosX(), ghost.getPosY());
						controller.refreshGhostImage(ghost);
					}
				});
			}
			try {
				sleep(rate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
