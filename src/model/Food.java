package model;

import javafx.beans.property.SimpleBooleanProperty;

public class Food {
	
	/**
	 */
	public static final byte NOTHING = 0;
	/**
	 */
	public static final byte PACDOT = 1;
	/**
	 */
	public static final byte ENERGIZER = 2;
	/**
	 */
	public static final byte BONUS = 3;
	
	/**
	 */
	private SimpleBooleanProperty notEaten;
	/**
	 */
	private byte type;
	
	/**
	 * @param type
	 * @param notEaten
	 */
	public Food(byte type, boolean notEaten) {
		this.type = type;
		this.notEaten = new SimpleBooleanProperty(notEaten);
	}
	/**
	 * @return
	 */
	public SimpleBooleanProperty getNotEaten() {
		return notEaten;
	}
	/**
	 * @param notEaten
	 */
	public void setNotEaten(boolean notEaten) {
		this.notEaten.set(notEaten);
	}
	/**
	 * @return
	 */
	public byte getType() {
		return type;
	}
	/**
	 * @param type
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
