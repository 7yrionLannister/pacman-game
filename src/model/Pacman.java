package model;

import javafx.beans.property.SimpleIntegerProperty;


public class Pacman extends Character {
	
	/**It represents the direction that Pacman wants to access in the maze.
	 */
	private Direction requestedDirection;
	/**Represents if Pacman is dying or not.
	 */
	private boolean dying;
	/**It represents the number of lives of Pacman.
	 */
	private SimpleIntegerProperty lives;
	
	/**Creates a Pacman with a determinate position and three lives.
	 * @param position is a Coordinate that represents the actual position of Pacman. 
	 * @param posX is a double that represents the position of Pacman in the axis x. 
	 * @param posY is a double that represents the position of Pacman in the axis y. 
	 */
	public Pacman(Coordinate position, double posX, double posY) {
		super(position, posX, posY);
		lives = new SimpleIntegerProperty(3);
	}
	/**Allows to obtain a Direction that represents the direction that Pacman wants to access in the maze.
	 * @return a Direction that represents the direction that Pacman wants to access in the maze.
	 */
	public Direction getRequestedDirection() {
		return requestedDirection;
	}
	/**Allows to set a Direction that represents the direction that Pacman wants to access in the maze.
	 * @param requestedDirection is a Direction that represents the direction that Pacman wants to access in the maze.
	 */
	public void setRequestedDirection(Direction requestedDirection) {
		this.requestedDirection = requestedDirection;
	}
	/**Allows to obtain a boolean that represents if Pacman is dying or not.
	 * @return a boolean that represents if Pacman is dying or not.
	 */
	public boolean isDying() {
		return dying;
	}
	/**Allows to set a boolean that represents if Pacman is dying or not.
	 * @param isDying is a boolean that represents if Pacman is dying or not
	 */
	public void setDying(boolean isDying) {
		this.dying = isDying;
	}
	/**Allows to obtain an Integer that represents the number of lives of Pacman.
	 * @return an Integer that represents the number of lives of Pacman.
	 */
	public SimpleIntegerProperty getLives() {
		return lives;
	}
	/**Allows to set an Integer that represents the number of lives of Pacman.
	 * @param lives is an Integer that represents the number of lives of Pacman.
	 */
	public void setLives(int lives) {
		this.lives.set(lives);
	}
}
