package utils;

import java.awt.Color;

import mathElements.Point;
import mathElements.Vector;

public class RayHit {
	private final Ray ray;			//Incident ray
	private final Vector normal;		//Object´s normal vector
	private final Point point;		//Intersection with object point
	private final Obj3D shape;
	private double t;
	
	public RayHit(Ray ray, Vector normal, Point intersection, Obj3D shape, double t) {
		this.ray = ray;
		this.normal = normal.normalize();
		this.point = intersection;
		this.shape = shape;
		this.t = t;
	}

	public String toString() {
		return "ray: " + ray.toString() + "; normal: " + normal.toString() + "; point: " + point.toString();
	}

	public Ray getRay() {
		return ray;
	}

	public Vector getNormal() {
		return normal;
	}

	public Point getPoint() {
		return point;
	}

	public Obj3D getShape() {
		return shape;
	}
	
	public double getT() {
		return t;
	}

	public void setT(double t) {
		this.t = t;
	}
	
}
