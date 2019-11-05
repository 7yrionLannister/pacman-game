package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;

public class Ghost extends Character {
	public static enum State {SCATTERED, CHASE}
	public static State state;
	private boolean frightened;
	private SimpleBooleanProperty goingHome;
	private Coordinate target;
	private Coordinate house;
	private ArrayList<Coordinate> path;
	private String name;
	
	public Ghost(Coordinate position, Coordinate house, String name, double posX, double posY) {
		super(position, posX, posY);
		this.house = house;
		this.name = name;
		goingHome = new SimpleBooleanProperty(false);
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

	public SimpleBooleanProperty isGoingHome() {
		return goingHome;
	}

	public void setGoingHome(boolean goingHome) {
		this.goingHome.set(goingHome);
	}
	
	@Override
	public void setPosition(Coordinate position) {
		super.setPosition(position);
		if(position.equals(house)) {
			setGoingHome(false);
		}
	}

	public Coordinate getHouse() {
		return house;
	}
}
