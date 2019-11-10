package model;

public class Coordinate {
	
	/**
	 */
	private double X;
	/**
	 */
	private double Y;
	/**
	 */
	private boolean hasRight;
	/**
	 */
	private boolean hasLeft;
	/**
	 */
	private boolean hasUp;
	/**
	 */
	private boolean hasDown;
	
	/**
	 * @param x
	 * @param y
	 * @param r
	 * @param l
	 * @param u
	 * @param d
	 */
	public Coordinate(double x, double y, boolean r, boolean l, boolean u, boolean d) {
		X = x;
		Y = y;
		hasRight = r;
		hasLeft = l;
		hasUp = u;
		hasDown = d;
	}
	/**
	 * @return
	 */
	public double getX() {
		return X;
	}
	/**
	 * @return
	 */
	public double getY() {
		return Y;
	}
	/**
	 * @return
	 */
	public boolean hasRightTile() {
		return hasRight;
	}
	/**
	 * @return
	 */
	public boolean hasLeftTile() {
		return hasLeft;
	}
	/**
	 * @return
	 */
	public boolean hasUpTile() {
		return hasUp;
	}
	/**
	 * @return
	 */
	public boolean hasDownTile() {
		return hasDown;
	}
	/**
	 * @param another
	 * @return
	 * @throws
	 */
	@Override
	public boolean equals(Object another) {
		Coordinate a = (Coordinate)another; //throws exception if another is not a Coordinate
		return a.X == X && a.Y == Y;
	}
	
	@Override
	public String toString() {
		return "("+X+","+Y+")";
	}
}
