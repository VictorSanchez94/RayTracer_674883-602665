package utils;

import mathElements.Point;
import mathElements.Vector;

public class Ray {

	private Point origin;
	private Vector direction;
	private double t;
	
	public Ray (Point origin, Vector direction) {
		this.origin = origin;
		this.direction = direction;
		this.t = Double.POSITIVE_INFINITY; //El rayo es infinito
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
	
	public Point getEnd(double t) {
		return origin.plusVector(direction.times(t));
	}

	public Point getEnd() {
		return getEnd(this.t);
	}
	
	public String toString() {
		return "origin: " + origin.toString() + ", direction: " + direction.toString();
	}
	
	public double getT(){
		return t;
	}
	
	public void setT(double t){
		this.t = t;
	}
}
