package model;


public class Pacman extends Character {
	
	private Direction requestedDirection;
	private boolean dying;
	private int lives;
	
	public Pacman(Coordinate position, double posX, double posY) {
		super(position, posX, posY);
		lives = 3;
	}

	public Direction getRequestedDirection() {
		return requestedDirection;
	}

	public void setRequestedDirection(Direction requestedDirection) {
		this.requestedDirection = requestedDirection;
	}

	public boolean isDying() {
		return dying;
	}

	public void setDying(boolean isDying) {
		this.dying = isDying;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}
