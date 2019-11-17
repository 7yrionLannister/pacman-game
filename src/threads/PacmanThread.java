package threads;

import java.io.File;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;
import model.Ghost;
import model.Level;
import model.Pacman;
import ui.PrimaryStageController;

public class PacmanThread extends Thread {
	/**Its the Pacman movements route.
	 */
	public final static String MOVEMENTS = "resources/sprites/pacman/movements/";
	/**It represents a controller where the game is contained.
	 */
	private PrimaryStageController controller;
	/**Its the image view of Pacman when the game is displayed. 
	 */
	private ImageView pacmanImage;
	/**It represents the actual game.
	 */
	private Game game;
	/**It represents Pacman.
	 */
	private Pacman pacman;
	/**A long variable to set sleep times when is needed.
	 */
	private long rate = 0;

	
	/**Creates a thread for Pacman as from a primary controller where the game is contained.
	 * @param c is a controller where the game is contained.
	 */
	public PacmanThread(PrimaryStageController c) {
		this.controller = c;
		pacmanImage = c.getPacman();
		game = c.getGame();
		pacman = game.getPacman();
		PrimaryStageController.MOVEMENT_SPRITE = 0;
		setDaemon(true);
	}
	
	/**This allows to move Pacman when the game starts.
	 */
	@Override
	public void run() {
		while(true) {
			if(!controller.isOnPause()) {
				move();
				refreshGUI();
				determineRate();
			}
			try {
				sleep(rate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**This moves Pacman along the maze as from the system input.
	 */
	private void move() {
		PrimaryStageController.MOVEMENT_COUNTER++;
		if((PrimaryStageController.MOVEMENT_COUNTER*PrimaryStageController.MOVEMENT_SPRITE) % 2 == 0) {
			pacmanImage.setImage(new Image(new File(MOVEMENTS+PrimaryStageController.MOVEMENT_SPRITE+".png").toURI().toString()));
		}
		if(PrimaryStageController.MOVEMENT_COUNTER % 3 == 0) {
			PrimaryStageController.MOVEMENT_SPRITE++;
			if(PrimaryStageController.MOVEMENT_SPRITE > 3) {
				PrimaryStageController.MOVEMENT_SPRITE = 0;
			}
		}
		if(pacman.isDying()) {
			die();
		}
		game.movePacman();
	}

	/**This allows to pause all the game and disappears all the ghosts when Pacman is dying. 
	 */
	private void die() {
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
				pacmanImage.setImage(new Image(new File(PrimaryStageController.CAUGHT+sprite+".png").toURI().toString()));
				sprite++;
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(pacman.getLives().get() > 0) {
			controller.setGUItoInitialState();
			controller.startPlayPauseButtonPressed(null);
		} else {
			Game.POINTS_EXTRA_LIVE = 5000;
			controller.getGameOverImage().setVisible(true);
			controller.setOnPause(true);
			Platform.runLater(() -> controller.openPlayerRegister());
		}
		game.getPacman().setDying(false);
	}
	/**This makes that the gui get refresh when the player presses a determinate key at system input.
	 */
	private void refreshGUI() {
		Platform.runLater(() -> {{
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
	
				controller.refreshLivesCounter();
			}
		});
	}
	/**This makes Pacman faster when is eating dots or has eaten an energizer.
	 */
	private void determineRate() {
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
	}
}
