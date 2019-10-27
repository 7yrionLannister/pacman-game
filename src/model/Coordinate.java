package model;

public class Coordinate {
	private double X;
	private double Y;
	private boolean hasRight;
	private boolean hasLeft;
	private boolean hasUp;
	private boolean hasDown;
	
	public Coordinate(double x, double y, boolean r, boolean l, boolean u, boolean d) {
		X = x;
		Y = y;
		hasRight = r;
		hasLeft = l;
		hasUp = u;
		hasDown = d;
	}

	public double getX() {
		return X;
	}

	public double getY() {
		return Y;
	}
	
	public boolean hasRightTile() {
		return hasRight;
	}

	public boolean hasLeftTile() {
		return hasLeft;
	}

	public boolean hasUpTile() {
		return hasUp;
	}

	public boolean hasDownTile() {
		return hasDown;
	}

	@Override
	public boolean equals(Object another) {
		Coordinate a = (Coordinate)another; //throws exception if another is not a Coordinate
		return a.X == X && a.Y == Y;
	}
}
