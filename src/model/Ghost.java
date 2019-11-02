package model;

import java.util.ArrayList;

public class Ghost extends Character {
	public static enum State {SCATTERED, CHASE}
	public static State state;
	private boolean frightened;
	private Coordinate target;
	private ArrayList<Coordinate> path;
	private String name;
	private boolean inTheTunnel;
	
	public Ghost(Coordinate position, String name, double posX, double posY) {
		super(position, posX, posY);
		this.name = name;
	}
	
	public boolean isFrightened() {
		return frightened;
	}
	
	public void setFrightened(boolean frightened) {
		this.frightened = frightened;
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
