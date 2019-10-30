package model;


public class Pacman {
	public static enum Direction {LEFT, RIGHT, UP, DOWN}

	private Coordinate position;
	private double posX;
	private double posY;
	private Direction direction;
	private Direction requestedDirection;

	public Pacman(Coordinate position, double posX, double posY) {
		this.position = position;
		this.posX = posX;
		this.posY = posY;
		direction = Direction.LEFT;
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getRequestedDirection() {
		return requestedDirection;
	}

	public void setRequestedDirection(Direction requestedDirection) {
		this.requestedDirection = requestedDirection;
	}
}
