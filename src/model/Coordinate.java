package model;

public class Coordinate {
	
	/**It represents a value in the axis x of the coordinated plane.
	 */
	private double X;
	/**It represents a value in the axis y of the coordinated plane.
	 */
	private double Y;
	/**Represents if the actual coordinate has some object to its right side. 
	 */
	private boolean hasRight;
	/**Represents if the actual coordinate has some object to its left side. 
	 */
	private boolean hasLeft;
	/**Represents if the actual coordinate has some object upside.
	 */
	private boolean hasUp;
	/**Represents if the actual coordinate has some object downside.
	 */
	private boolean hasDown;
	
	/**Creates a coordinate with a x and y value and some side specifications. 
	 * @param x is a double that represents a value in the axis x of the coordinated plane.
	 * @param y is a double that represents a value in the axis y of the coordinated plane.
	 * @param r is a boolean that represents if the actual coordinate has some object to its right side. 
	 * @param l is a boolean that represents if the actual coordinate has some object to its left side. 
	 * @param u is a boolean that represents if the actual coordinate has some object upside.
	 * @param d is a boolean that represents if the actual coordinate has some object downside.
	 */
	public Coordinate(double x, double y, boolean r, boolean l, boolean u, boolean d) {
		X = x;
		Y = y;
		hasRight = r;
		hasLeft = l;
		hasUp = u;
		hasDown = d;
	}
	/**It allows to obtain a double that represents a value in the axis x of the coordinated plane.
	 * @return a double that represents a value in the axis x of the coordinated plane.
	 */
	public double getX() {
		return X;
	}
	/**It allows to obtain a double that represents a value in the axis y of the coordinated plane.
	 * @return a double that represents a value in the axis y of the coordinated plane.
	 */
	public double getY() {
		return Y;
	}
	/**It allows to obtain a boolean that represents if the actual coordinate has some object to its right side. 
	 * @return a boolean that represents if the actual coordinate has some object to its right side. 
	 */
	public boolean hasRightTile() {
		return hasRight;
	}
	/**It allows to obtain a boolean that represents if the actual coordinate has some object to its left side.
	 * @return a boolean that represents if the actual coordinate has some object to its left side.
	 */
	public boolean hasLeftTile() {
		return hasLeft;
	}
	/**It allows to obtain a boolean that represents if the actual coordinate has some object upside.
	 * @return a boolean that represents if the actual coordinate has some object upside.
	 */
	public boolean hasUpTile() {
		return hasUp;
	}
	/**It allows to obtain a boolean that represents if the actual coordinate has some object downside.
	 * @return a boolean that represents if the actual coordinate has some object downside.
	 */
	public boolean hasDownTile() {
		return hasDown;
	}
	/**This compares if a object that arrives as parameter is the actual coordinate.
	 * @param another is a Object that is going to be compared. 
	 * @return a boolean that represents if another is the actual coordinate or not.
	 * @throws ClassCastException if another is not a Coordinate.
	 */
	@Override
	public boolean equals(Object another) {
		Coordinate a = (Coordinate)another; 
		return a.X == X && a.Y == Y;
	}
	
	@Override
	public String toString() {
		return "("+X+","+Y+")";
	}
}
