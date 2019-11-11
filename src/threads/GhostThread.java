package threads;

import model.Game;
import model.Ghost;
import model.Level;
import ui.PrimaryStageController;

public class GhostThread extends Thread {

	/**It represents the controller where the game is controlled. 
	 */
	private PrimaryStageController controller;
	/**It represents the actual game.
	 */
	private Game game;
	/**It represents the ghost that will be controlled by this thread.
	 */
	private Ghost ghost;
	
	private long rate = 0;

	/**Creates a thread for moving the ghost as from a game controller and the ghost name.
	 * @param c that represents the controller where the game is controlled.
	 * @param name is a String that represents the ghost name.
	 */
	public GhostThread(PrimaryStageController c, String name) {
		this.controller = c;
		game = c.getGame();
		ghost = game.getGhost(name);
		setDaemon(true);
	}

	/**This moves the ghost with respect to its name and its state inside the game.
	 */
	@Override
	public void run() {
		while(true) {
			if(!controller.isOnPause()) {
				move();
			}
			try {
				determineRate();
				sleep(rate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void determineRate() {
		Level level = game.getCurrentLevel();
		if(ghost.equals(game.getBlinky())) {
			if(ghost.isFrightened()) {
				rate = level.getFrightGhostsSpeed();
			} else if(game.isInTheTunnel(ghost)) {
				rate = level.getGhostsTunelSpeed();
			}  else if(level.getDotsLeft() <= level.getCruiseElroyDotsLeft2()){
				rate = level.getCruiseElroySpeed2();
			} else if(level.getDotsLeft() <= level.getCruiseElroyDotsLeft1()){
				rate = level.getCruiseElroySpeed1();
			} else {
				rate = level.getGhostsSpeed();
			}
			game.setFrightenedCountdown(game.getFrightenedCountdown()-rate);
		} else {
			if(ghost.isFrightened()) {
				rate = level.getFrightGhostsSpeed();
			} else if(game.isInTheTunnel(ghost)) {
				rate = level.getGhostsTunelSpeed();
			} else {
				rate = level.getGhostsSpeed();
			}
		}
		if(ghost.isGoingHome().get()) {
			rate = 6;
		}
	}

	private void move() {
		boolean move = false;
		int ini = game.getInitialNumberOfDots();
		int left = game.getCurrentLevel().getDotsLeft();
		switch(ghost.getName()) {
		case "inky":
			move = (ini - left > 5);
			break;
		case "clyde":
			move = (ini - left > 8);
			break;
		default:
			move = true;
			break;
		}
		if(move) {
			game.moveGhost(ghost);
		}
	}
}
