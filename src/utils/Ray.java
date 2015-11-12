package utils;

public class Ray {

	private Point point;
	private Vector vector;
	
	public Ray (Point point, Vector vector) {
		this.point = point;
		this.vector = vector;
	}
	
	public Ray (Point point1, Point point2) {
		this.point = point1;
		this.vector = new Vector(point1, point2);
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Vector getVector() {
		return vector;
	}

	public void setVector(Vector vector) {
		this.vector = vector;
	}
	
	public void setVector(Point from, Point to) {
		this.vector = new Vector(from, to);
	}
	
}
