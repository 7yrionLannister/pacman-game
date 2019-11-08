package threads;

import model.Game;
import model.Ghost;
import model.Level;
import ui.Controller;


public class GhostThread extends Thread {
	private Controller controller;
	private Game game;
	private Ghost ghost;

	public GhostThread(Controller c, String name) {
		this.controller = c;
		game = c.getGame();
		ghost = game.getGhost(name);
		setDaemon(true);
	}

	@Override
	public void run() {
		while(true) {
			if(!controller.isOnPause()) {
				game.moveGhost(ghost);
			}
			try {
				Level level = game.getCurrentLevel();
				long rate = 0;
				if(ghost.equals(game.getBlinky())) {
					if(ghost.isFrightened()) {
						rate = (long)((1 - level.getFrightGhostsSpeed())*Level.REFERENCE_SPEED);
					} else if(game.isInTheTunnel(ghost)) {
						rate = (long)((1 - level.getGhostsTunelSpeed())*Level.REFERENCE_SPEED);
					}  else if(level.getDotsLeft() <= level.getCruiseElroyDotsLeft2()){
						System.out.println("elroy 2");
						rate = (long)((1 - level.getCruiseElroySpeed2())*Level.REFERENCE_SPEED);
					} else if(level.getDotsLeft() <= level.getCruiseElroyDotsLeft1()){
						System.out.println("elroy 1");
						rate = (long)((1 - level.getCruiseElroySpeed1())*Level.REFERENCE_SPEED);
					} else {
						rate = (long)((1 - level.getGhostsSpeed())*Level.REFERENCE_SPEED);
					}
					game.setFrightenedCountdown(game.getFrightenedCountdown()-rate);
				} else {
					if(ghost.isFrightened()) {
						rate = (long)((1 - level.getFrightGhostsSpeed())*Level.REFERENCE_SPEED);
					} else if(game.isInTheTunnel(ghost)) {
						rate = (long)((1 - level.getGhostsTunelSpeed())*Level.REFERENCE_SPEED);
					} else {
						rate = (long)((1 - level.getGhostsSpeed())*Level.REFERENCE_SPEED);
					}
				}
				if(ghost.isGoingHome().get()) {
					rate = 6;
				}
				sleep(rate+1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
