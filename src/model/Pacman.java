package model;


public class Pacman extends Character {
	
	private Direction requestedDirection;

	public Pacman(Coordinate position, double posX, double posY) {
		super(position, posX, posY);
	}

	public Direction getRequestedDirection() {
		return requestedDirection;
	}

	public void setRequestedDirection(Direction requestedDirection) {
		this.requestedDirection = requestedDirection;
	}
}
