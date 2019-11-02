package model;

import java.util.ArrayList;

public class Ghost {
	public static enum State {SCATTERED, CHASE}
	public static State state;
	private boolean frightened;
	private Coordinate position;
	private Coordinate target;
	private double posX;
	private double posY;
	private Direction direction;
	private ArrayList<Coordinate> path;
	private String name;
	private boolean inTheTunnel;
	
	public Ghost(Coordinate position, String name, double posX, double posY) {
		this.position = position;
		this.name = name;
		this.posX = posX;
		this.posY = posY;
	}
	
	public boolean isFrightened() {
		return frightened;
	}
	
	public void setFrightened(boolean frightened) {
		this.frightened = frightened;
	}
	
	public Coordinate getPosition() {
		return position;
	}
	
	public void setPosition(Coordinate position) {
		this.position = position;
	}
	
	public Coordinate getTarget() {
		return target;
	}
	
	public void setTarget(Coordinate target) {
		this.target = target;
	}
	
	public ArrayList<Coordinate> getPath() {
		return path;
	}
	
	public void setPath(ArrayList<Coordinate> path) {
		this.path = path;
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

	public String getName() {
		return name;
	}

	public boolean isInTheTunnel() {
		return inTheTunnel;
	}

	public void setInTheTunnel(boolean inTheTunnel) {
		this.inTheTunnel = inTheTunnel;
	}
}
