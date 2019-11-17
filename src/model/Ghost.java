package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;

public class Ghost extends Character {
	
	/**It represents if the ghost state is scatter or chase.
	 */
	public static enum State {SCATTER, CHASE}
	/**If represents the actual state of the ghost.
	 */
	public static State state;
	/**It represents if the actual ghost is frightened or not.
	 */
	private boolean frightened;
	/**It represents if the actual ghost is returning home or not.
	 */
	private SimpleBooleanProperty goingHome;
	/**It represents the target coordinate in the maze.
	 */
	private Coordinate target;
	/**It represents the house coordinate in the maze.
	 */
	private Coordinate house;
	/**
	 */
	private ArrayList<Coordinate> path;
	/**
	 */
	private String name;
	
	/**
	 * @param position
	 * @param house
	 * @param name
	 * @param posX
	 * @param posY
	 */
	public Ghost(Coordinate position, Coordinate house, String name, double posX, double posY) {
		super(position, posX, posY);
		this.house = house;
		this.name = name;
		goingHome = new SimpleBooleanProperty(false);
	}
	/**
	 * @return
	 */
	public boolean isFrightened() {
		return frightened;
	}
	/**
	 * @param frightened
	 */
	public void setFrightened(boolean frightened) {
		this.frightened = frightened;
	}
	/**
	 * @return
	 */
	public Coordinate getTarget() {
		return target;
	}
	/**
	 * @param target
	 */
	public void setTarget(Coordinate target) {
		this.target = target;
	}
	/**
	 * @return
	 */
	public ArrayList<Coordinate> getPath() {
		return path;
	}
	/**
	 * @param path
	 */
	public void setPath(ArrayList<Coordinate> path) {
		this.path = path;
	}
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return
	 */
	public SimpleBooleanProperty isGoingHome() {
		return goingHome;
	}
	/**
	 * @param goingHome
	 */
	public void setGoingHome(boolean goingHome) {
		this.goingHome.set(goingHome);
	}
	/**
	 * @param position
	 */
	@Override
	public void setPosition(Coordinate position) {
		super.setPosition(position);
		if(position.equals(house)) {
			setGoingHome(false);
		}
	}
	/**
	 * @return
	 */
	public Coordinate getHouse() {
		return house;
	}
	/**
	 * @return
	 */
	public boolean isAtHome() {
		return getPosition().equals(house);
	}
}
