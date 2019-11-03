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
	private Controller c;
	private Game game;
	private Ghost ghost;
	private ImageView ghostImage;
	
	public GhostThread(Controller c) {
		this.c = c;
		ghostImage = c.getBlinky();
		game = c.getGame();
		ghost = game.getBlinky();
	}
	
	@Override
	public void run() {
		while(!c.isOnPause()) {
			move();
			try {
				Level level = game.getCurrentLevel();
				long rate = 0;
				if(ghost.isFrightened()) {
					rate = (long)((1 - level.getFrightGhostsSpeed())*Level.REFERENCE_SPEED);
				} else if(ghost.isInTheTunnel()) {
					rate = (long)((1 - level.getGhostsTunelSpeed())*Level.REFERENCE_SPEED);
				} else {
					rate = (long)((1 - level.getGhostsSpeed())*Level.REFERENCE_SPEED);
				}
				sleep(rate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void move() {
		game.moveGhost(ghost);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(Controller.MOVEMENT_COUNTER%5 == 0) {
					if(ghost.isFrightened()) {
						//TODO implementar, la linea que sigue sirve para cuando esta azulito
						//TODO ahora falta para los tres parpadeos de advertencia al final, eso solo lo podras hacer con el timer y timertask
						ghostImage.setImage(new Image(new File(Controller.GHOSTS_SPRITES+"vulnerable"+File.separator+((Controller.MOVEMENT_SPRITE%2)==0?0:2)+".png").toURI().toString()));
					} else {
						long number = (Controller.MOVEMENT_COUNTER%2);
						String dir = "";
						switch(ghost.getDirection()) {
						case DOWN:
							dir = "d";
							break;
						case LEFT:
							dir = "l";
							break;
						case RIGHT:
							dir = "r";
							break;
						case UP:
							dir = "u";
							break;
						}
						ghostImage.setImage(new Image(new File(Controller.GHOSTS_SPRITES+ghost.getName()+File.separator+dir+number+".png").toURI().toString()));	
					}
				}
				ghostImage.relocate(ghost.getPosX(), ghost.getPosY());
			}
		});
	}

	public Controller getC() {
		return c;
	}

	public Game getGame() {
		return game;
	}

	public Ghost getGhost() {
		return ghost;
	}
}
