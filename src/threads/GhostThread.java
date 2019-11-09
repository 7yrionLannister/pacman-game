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
						rate = level.getFrightGhostsSpeed();
					} else if(game.isInTheTunnel(ghost)) {
						rate = level.getGhostsTunelSpeed();
					}  else if(level.getDotsLeft() <= level.getCruiseElroyDotsLeft2()){
						System.out.println("elroy 2");
						rate = level.getCruiseElroySpeed2();
					} else if(level.getDotsLeft() <= level.getCruiseElroyDotsLeft1()){
						System.out.println("elroy 1");
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
				sleep(rate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
