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
	/**It represents a path shaped by various coordinates.
	 */
	private ArrayList<Coordinate> path;
	/**It represents the ghost name.
	 */
	private String name;
	
	/**This creates a ghost with a determinate name, initial position and house.
	 * @param position is a Coordinate that represents the actual ghost coordinate position in the maze.
	 * @param house is a Coordinate that represents the house coordinate in the maze.
	 * @param name is a String that represents the ghost name.
	 * @param posX is a double value that the actual ghost coordinate position in axis x.
	 * @param posY is a double value that the actual ghost coordinate position in axis y.
	 */
	public Ghost(Coordinate position, Coordinate house, String name, double posX, double posY) {
		super(position, posX, posY);
		this.house = house;
		this.name = name;
		goingHome = new SimpleBooleanProperty(false);
	}
	/**Allows to obtain a boolean that represents if the actual ghost is frightened or not.
	 * @return a boolean that represents if the actual ghost is frightened or not.
	 */
	public boolean isFrightened() {
		return frightened;
	}
	/**Allows to set a boolean that represents if the actual ghost is frightened or not.
	 * @param frightened is a boolean that represents if the actual ghost is frightened or not.
	 */
	public void setFrightened(boolean frightened) {
		this.frightened = frightened;
	}
	/**Allows to obtain a Coordinate that represents the target coordinate in the maze.
	 * @return a Coordinate that represents the target coordinate in the maze.
	 */
	public Coordinate getTarget() {
		return target;
	}
	/**Allows to set a Coordinate that represents the target coordinate in the maze.
	 * @param target is a Coordinate that represents the target coordinate in the maze.
	 */
	public void setTarget(Coordinate target) {
		this.target = target;
	}
	/**Allows to obtain an ArrayList of Coordinates that represents a path shaped by various coordinates.
	 * @return an ArrayList of Coordinates that represents a path shaped by various coordinates.
	 */
	public ArrayList<Coordinate> getPath() {
		return path;
	}
	/**Allows to set an ArrayList of Coordinates that represents a path shaped by various coordinates.
	 * @param path is an ArrayList of Coordinates that represents a path shaped by various coordinates.
	 */
	public void setPath(ArrayList<Coordinate> path) {
		this.path = path;
	}
	/**Allows to obtain a string that represents the ghost name.
	 * @return a string that represents the ghost name.
	 */
	public String getName() {
		return name;
	}
	/**Allows to obtain a SimpleBooleanProperty that represents if the actual ghost is returning home or not.
	 * @return a SimpleBooleanProperty that represents if the actual ghost is returning home or not.
	 */
	public SimpleBooleanProperty isGoingHome() {
		return goingHome;
	}
	/**Allows to set a SimpleBooleanProperty that represents if the actual ghost is returning home or not.
	 * @param goingHome is a SimpleBooleanProperty that represents if the actual ghost is returning home or not.
	 */
	public void setGoingHome(boolean goingHome) {
		this.goingHome.set(goingHome);
	}
	/**Allows to set a Coordinate that represents the actual ghost coordinate position in the maze.
	 * @param position is a Coordinate that represents the actual ghost coordinate position in the maze.
	 */
	@Override
	public void setPosition(Coordinate position) {
		super.setPosition(position);
		if(position.equals(house)) {
			setGoingHome(false);
		}
	}
	/**Allows to obtain a Coordinate that represents the house coordinate in the maze.
	 * @return a Coordinate that represents the house coordinate in the maze.
	 */
	public Coordinate getHouse() {
		return house;
	}
	/**Allows to obtain a boolean that represents if the actual ghost is returning home or not.
	 * @return a boolean that represents if the actual ghost is returning home or not.
	 */
	public boolean isAtHome() {
		return getPosition().equals(house);
	}
}
