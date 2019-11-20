package model;

import javafx.beans.property.SimpleBooleanProperty;

public class Food {
	
	/**It represents nothing as a byte of value 0.
	 */
	public static final byte NOTHING = 0;
	/**It represents a pacdot as a byte of value 1.
	 */
	public static final byte PACDOT = 1;
	/**It represents the energizer as a byte of value 2.
	 */
	public static final byte ENERGIZER = 2;
	/**It represents the bonus as a byte of value 3.
	 */
	public static final byte BONUS = 3;
	/**It represents the actual state of the food when the game stars.
	 */
	private SimpleBooleanProperty notEaten;
	/**It represents the points for the score in the game for a determinate kind of food.
	 */
	private byte type;
	
	/**Creates a food specifying its value and it has been eaten or not yet.
	 * @param type is a byte that represents the points for the score in the game for a determinate kind of food.
	 * @param notEaten is a boolean that represents the actual state of the food when the game stars.
	 */
	public Food(byte type, boolean notEaten) {
		this.type = type;
		this.notEaten = new SimpleBooleanProperty(notEaten);
	}
	/**Allows to obtain a boolean that represents the actual state of the food when the game stars.
	 * @return a boolean that represents the actual state of the food when the game stars.
	 */
	public SimpleBooleanProperty getNotEaten() {
		return notEaten;
	}
	/**Allows to set a boolean that represents the actual state of the food when the game stars
	 * @param notEaten is a boolean that represents the actual state of the food when the game stars.
	 */
	public void setNotEaten(boolean notEaten) {
		this.notEaten.set(notEaten);
	}
	/**Allows to obtain a byte that represents the points for the score in the game for a determinate kind of food.
	 * @return a byte that represents the points for the score in the game for a determinate kind of food.
	 */
	public byte getType() {
		return type;
	}
	/**Allows to set a byte that represents the points for the score in the game for a determinate kind of food.
	 * @param type is a byte that represents the points for the score in the game for a determinate kind of food.
	 */
	public void setType(byte type) {
		this.type = type;
		if(type == NOTHING) {
			setNotEaten(false);
		} else {
			setNotEaten(true);
		}
	}
}
