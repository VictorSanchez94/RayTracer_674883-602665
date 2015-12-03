package utils;

public class Point extends Obj3D{

	/** Class constructor */
	public Point(double x, double y, double z) {
		super(x, y, z);
	}

	/** Return the distance between two poins */
	public double distanceTo(Point p) {
		return Math.sqrt((p.getX() - super.getX())*(p.getX() - super.getX()) + (p.getY() - super.getY())*(p.getY() - super.getY()) + (p.getZ() - super.getZ())*(p.getZ() - super.getZ()));
	}

	/** Add a vector to a point */
	public Point plusVector(Vector v) {
		return new Point(super.getX() + v.getX(), super.getY() + v.getY(), super.getZ() + v.getZ());
	}
	
	/** Add 2 points*/
	public Point plusPoint(Point p){
		return new Point(super.getX() + p.getX(), super.getY() + p.getY(), super.getZ() + p.getZ());
	}
	
	/**Subtracts 2 points*/
	public Point substraction(Point p) {
		return new Point(super.getX() - p.getX(), super.getY() - p.getY(), super.getZ() - p.getZ());
	}
	
	public Vector minus(Point p) {
		return new Vector(this.getX() - p.getX(), this.getY() - p.getY(), this.getZ() - p.getZ());
	}

	/** Return the string coordinates of the point */
	public String toString() {
		return "[" + super.getX() + ", " + super.getY() + ", " + super.getZ() + "]";
	}
}