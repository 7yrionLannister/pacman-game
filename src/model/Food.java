package model;

import javafx.beans.property.SimpleBooleanProperty;

public class Food {
	public static final byte NOTHING = 0;
	public static final byte PACDOT = 1;
	public static final byte ENERGIZER = 2;
	public static final byte BONUS = 3;
	
	private SimpleBooleanProperty notEaten;
	private byte type;
	
	public Food(byte type, boolean notEaten) {
		this.type = type;
		this.notEaten = new SimpleBooleanProperty(notEaten);
	}

	public SimpleBooleanProperty getNotEaten() {
		return notEaten;
	}

	public void setNotEaten(boolean notEaten) {
		this.notEaten.set(notEaten);
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
		if(type == NOTHING) {
			setNotEaten(false);
		} else {
			setNotEaten(true);
		}
	}
}
