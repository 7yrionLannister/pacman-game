package model;

import java.util.ArrayList;

public class Ghost {
	private boolean frightened;
	private Coordinate position;
	private Coordinate target;
	private double posX;
	private double posY;
	private Direction direction;
	private ArrayList<Coordinate> path;
	
	public Ghost(Coordinate position) {
		this.position = position;
		posX = position.getX()-14; //TODO -14 aniadido para compatibilidad con MacOS, hacer condicional de sistema operativo para saber si hay que sumar o no el -14
		posY = position.getY();
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
}
