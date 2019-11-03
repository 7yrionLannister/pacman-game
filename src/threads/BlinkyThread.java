package threads;

import model.Level;
import ui.Controller;


public class BlinkyThread extends GhostThread {

	public BlinkyThread(Controller c) {
		super(c, "blinky");
	}

	@Override
	public void run() {
		while(!super.getController().isOnPause()) {
			if(getGame().getMap().containsVertex(getGhost().getPosition())) { //if ghost is out of the house
				move();
			}
			try {
				Level level = getGame().getCurrentLevel();
				long rate = 0;
				if(getGhost().isFrightened()) {
					rate = (long)((1 - level.getFrightGhostsSpeed())*Level.REFERENCE_SPEED);
				} else if(getGhost().isInTheTunnel()) {
					rate = (long)((1 - level.getGhostsTunelSpeed())*Level.REFERENCE_SPEED);
				}  else if(level.getDotsLeft() <= level.getCruiseElroyDotsLeft2()){
					rate = (long)((1 - level.getCruiseElroySpeed2())*Level.REFERENCE_SPEED);
				} else if(level.getDotsLeft() <= level.getCruiseElroyDotsLeft1()){
					rate = (long)((1 - level.getCruiseElroySpeed1())*Level.REFERENCE_SPEED);
				} else {
					rate = (long)((1 - level.getGhostsSpeed())*Level.REFERENCE_SPEED);
				}
				sleep(rate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
