package threads;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Game;
import model.Ghost;
import model.Level;
import ui.Controller;


public class BlinkyThread extends Thread {
	private Controller c;
	private Game g;
	private Ghost blinky;
	private ImageView blinkyImage;

	public BlinkyThread(Controller c) {
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
			//TODO haz que funcione para blinky y si te das cuenta la forma en que van a perseguir su objetivo y a
			//TODO refrescar su sprite es igual para todos, lo que varia es el tiempo que esperan dependiendo
			//TODO del estado en que se encuentre el respectivo fantasma, entonces deja todo lo comun en una funcion en algun lado
			//TODO y en cada fantasma especifico solo llama esa funcion y luego dale el sleep que necesite ese fantasma
			g.moveBlinky();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if(Controller.MOVEMENT_COUNTER%5 == 0) {
						if(blinky.isFrightened()) {
							//TODO implementar, la linea que sigue sirve para cuando esta azulito
							//TODO ahora falta para los tres parpadeos de advertencia al final, eso solo lo podras hacer con el timer y timertask
							blinkyImage.setImage(new Image(new File(Controller.GHOSTS_SPRITES+"vulnerable"+File.separator+((Controller.MOVEMENT_SPRITE%2)==0?0:2)+".png").toURI().toString()));
						} else {
							long number = (Controller.MOVEMENT_COUNTER%2);
							String dir = "";
							switch(blinky.getDirection()) {
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
							blinkyImage.setImage(new Image(new File(Controller.GHOSTS_SPRITES+blinky.getName()+File.separator+dir+number+".png").toURI().toString()));	
						}
					}
					blinkyImage.relocate(blinky.getPosX(), blinky.getPosY());
				}
			});
			try {
				Level level = g.getCurrentLevel();
				long rate = 0;
				if(blinky.isFrightened()) {
					rate = (long)((1 - level.getFrightGhostsSpeed())*Level.REFERENCE_SPEED);
				} else if(blinky.isInTheTunnel()) {
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
