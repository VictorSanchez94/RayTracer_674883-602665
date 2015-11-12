package utils;

public class Point extends Obj3D{
	/** Class attributes */
	public double x, y, z;

	/** Class constructor */
	public Point(double x, double y, double z) {
		super(x, y, z);
	}

	/** Return the distance between two poins */
	public double distanceTo(Point p) {
		return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y) + (p.z - z)*(p.z - z));
	}

	/** Add a vector to a point */
	public Point plus(Vector v) {
		return new Point(x + v.x, y + v.y, z + v.z);
	}

	/** Return the string coordinates of the point */
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
}