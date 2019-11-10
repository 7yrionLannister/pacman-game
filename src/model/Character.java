package model;

public class Character {
	/**
	 */
	private Coordinate position;
	/**
	 */
	private double posX;
	/**
	 */
	private double posY;
	/**
	 */
	private Direction direction;
	
	/**
	 * @param position
	 * @param posX
	 * @param posY
	 */
	public Character(Coordinate position, double posX, double posY) {
		this.position = position;
		this.posX = posX;
		this.posY = posY;
		direction = Direction.LEFT;
	}
	 
	/**
	 * @return
	 */
	public Coordinate getPosition() {
		return position;
	}
	/**
	 * @param position
	 */
	public void setPosition(Coordinate position) {
		this.position = position;
	}
	/**
	 * @return
	 */
	public double getPosX() {
		return posX;
	}
	/**
	 * @param posX
	 */
	public void setPosX(double posX) {
		this.posX = posX;
	}
	/**
	 * @return
	 */
	public double getPosY() {
		return posY;
	}
	/**
	 * @param posY
	 */
	public void setPosY(double posY) {
		this.posY = posY;
	}
	/**
	 * @return
	 */
	public Direction getDirection() {
		return direction;
	}
	/**
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
