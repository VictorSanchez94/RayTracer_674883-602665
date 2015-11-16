package utils;

public class Ray {

	private Point origin;
	private Vector direction;
	
	public Ray (Point origin, Vector direction) {
		this.origin = origin;
		this.direction = direction;
	}
	
	public Ray (Point from, Point to) {
		this.origin = from;
		this.direction = new Vector(from, to);
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public Vector getDirection() {
		return direction;
	}

	public void setDirection(Vector direction) {
		this.direction = direction;
	}
	
	public void setDirection(Point from, Point to) {
		this.direction = new Vector(from, to);
	}
	
	public Point getPoint(double lambda) {
		Point p = new Point(origin.getX()+direction.getX()*lambda, origin.getY()+direction.getY()*lambda, origin.getZ()+direction.getZ()*lambda);
		return p;
	}
	
	public String toString() {
		return "origin: " + origin.toString() + ", direction: " + direction.toString();
	}
	
}
