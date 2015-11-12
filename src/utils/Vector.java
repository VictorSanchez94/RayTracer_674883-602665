package utils;

public class Vector extends Obj3D{
	/** Class attributes */
	private double x, y, z;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	/** Class constructor giving XYZ coordinates */
	public Vector(double x, double y, double z) {
		super(x,y,z);
	}

	/** Class constructor giving a Point */
	public Vector(Point p) {
		this(p.x, p.y, p.z);
	}

	/** Class constructor giving origin and destination Points */
	public Vector(Point from, Point to) {
		this(to.x - from.x, to.y - from.y, to.z - from.z);
	}

	/** Vector normalization */
	public Vector normalize() {
		double magnitude = magnitude();
		double divisor;
		if(magnitude == 0) {
			divisor = Double.POSITIVE_INFINITY;
		}
		else divisor = 1 / magnitude;

		return this.times(divisor);
	}

	/** NO SE SI VA A HACER FALTA */
	public double magnitude() {
		return Math.sqrt(this.dot(this));
	}

	/** NO SE SI VA A HACER FALTA */
	public Vector plus(Vector v) {
		return new Vector(x + v.x, y + v.y, z + v.z);
	}

	/** Vector subtraction */
	public Vector minus(Vector v) {
		return new Vector(x - v.x, y - v.y, z - v.z);
	}

	/** Vector negation */
	public Vector negate() {
		return times(-1);
	}

	/** Vector multiplication by a number */
	public Vector times(double scalar) {
		return new Vector(x * scalar, y * scalar, z * scalar);
	}

	/** NO SE SI VA A HACER FALTA */
	public Vector cross(Vector v) {
		return new Vector(((y * v.z) - (z * v.y)),
						  ((z * v.x) - (x * v.z)),
						  ((x * v.y) - (y * v.x)));
	}

	/** NO SE SI VA A HACER FALTA */
	public double dot(Vector v) {
		return (x * v.x) + (y * v.y) + (z * v.z);
	}

	/** NO SE SI VA A HACER FALTA */
	public static Vector halfway(Vector v1, Vector v2) {
		return v1.plus(v2).normalize();
	}

	/** Return the string coordinates of the point */
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
