package model;

public class Character {
	
	/**It represents the position of the actual character.
	 */
	private Coordinate position;
	/**It represents the position of the actual character in the axis x.
	 */
	private double posX;
	/**It represents the position of the actual character in the axis y.
	 */
	private double posY;
	/**It represents the direction of the actual character. 
	 */
	private Direction direction;
	
	/**Creates a new character with a determinate position but always in the left direction.
	 * @param position is a Coordinate that represents the position of the actual character.
	 * @param posX is a double that represents the position of the actual character in the axis x.
	 * @param posY is a double that represents the position of the actual character in the axis y.
	 */
	public Character(Coordinate position, double posX, double posY) {
		this.position = position;
		this.posX = posX;
		this.posY = posY;
		direction = Direction.LEFT;
	}
	/**This allows to obtain a Coordinate that represents the position of the actual character.
	 * @return a Coordinate that represents the position of the actual character.
	 */
	public Coordinate getPosition() {
		return position;
	}
	/**This allows to set a Coordinate that represents the position of the actual character.
	 * @param position is a Coordinate that represents the position of the actual character.
	 */
	public void setPosition(Coordinate position) {
		this.position = position;
	}
	/**This allows to obtain a double that represents the position of the actual character in the axis x.
	 * @return a double that represents the position of the actual character in the axis x.
	 */
	public double getPosX() {
		return posX;
	}
	/**This allows to set a double that represents the position of the actual character in the axis x.
	 * @param posX is a double that represents the position of the actual character in the axis x.
	 */
	public void setPosX(double posX) {
		this.posX = posX;
	}
	/**This allows to obtain a double that represents the position of the actual character in the axis y.
	 * @return a double that represents the position of the actual character in the axis y.
	 */
	public double getPosY() {
		return posY;
	}
	/**This allows to set a double that represents the position of the actual character in the axis y.
	 * @param posY is a double that represents the position of the actual character in the axis y.
	 */
	public void setPosY(double posY) {
		this.posY = posY;
	}
	/**It allows to obtain a Direction that represents the direction of the actual character.
	 * @return a Direction that represents the direction of the actual character.
	 */
	public Direction getDirection() {
		return direction;
	}
	/**It allows to set a Direction that represents the direction of the actual character.
	 * @param direction is a Direction that represents the direction of the actual character.
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
