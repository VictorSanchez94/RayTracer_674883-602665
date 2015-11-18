package utils;

public class Vector extends Obj3D{
	/** Class attributes */

	public double getX() {
		return super.getX();
	}

	public void setX(double x) {
		super.setX(x);
	}

	public double getY() {
		return super.getY();
	}

	public void setY(double y) {
		super.setY(y);
	}

	public double getZ() {
		return super.getZ();
	}

	public void setZ(double z) {
		super.setZ(z);
	}

	/** Class constructor giving XYZ coordinates */
	public Vector(double x, double y, double z) {
		super(x,y,z);
	}

	/** Class constructor giving a Point */
	public Vector(Point p) {
		super(p.getX(), p.getY(), p.getZ());
	}

	/** Class constructor giving origin and destination Points */
	public Vector(Point from, Point to) {
		this(to.getX() - from.getX(), to.getY() - from.getY(), to.getZ() - from.getZ());
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
		return new Vector(super.getX() + v.getX(), super.getY() + v.getY(), super.getZ() + v.getZ());
	}

	/** Vector subtraction */
	public Vector minusVector(Vector v) {
		return new Vector(super.getX() - v.getX(), super.getY() - v.getY(), super.getZ() - v.getZ());
	}
	
	public double minusPoint(Vector v) {
		return super.getX() - v.getX() + super.getY() - v.getY() + super.getZ() - v.getZ();
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

	public double dot(Vector v) {
		return (x * v.x) + (y * v.y) + (z * v.z);
	}
	
	public Vector dotVector(Vector v) {
		return new Vector((x * v.x) , (y * v.y) , (z * v.z));
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
