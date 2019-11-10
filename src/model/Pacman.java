package model;


public class Pacman extends Character {
	
	/**
	 */
	private Direction requestedDirection;
	/**
	 */
	private boolean dying;
	/**
	 */
	private int lives;
	
	/**
	 * @param position
	 * @param posX
	 * @param posY
	 */
	public Pacman(Coordinate position, double posX, double posY) {
		super(position, posX, posY);
		lives = 3;
	}
	/**
	 * @return
	 */
	public Direction getRequestedDirection() {
		return requestedDirection;
	}
	/**
	 * @param requestedDirection
	 */
	public void setRequestedDirection(Direction requestedDirection) {
		this.requestedDirection = requestedDirection;
	}
	/**
	 * @return
	 */
	public boolean isDying() {
		return dying;
	}
	/**
	 * @param isDying
	 */
	public void setDying(boolean isDying) {
		this.dying = isDying;
	}
	/**
	 * @return
	 */
	public int getLives() {
		return lives;
	}
	/**
	 * @param lives
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}
}
