package utils;

import mathElements.Point;
import mathElements.Vector;

public class RayHit {
	private final Ray ray;			//Incident ray
	private final Vector normal;		//Object´s normal vector
	private final Point point;		//Intersection with object point
	private final Obj3D shape;
	private double t;
	private boolean incoming;
	
	public RayHit(Ray ray, Vector normal, Point intersection, Obj3D shape, double t, boolean incoming) {
		this.ray = ray;
		this.normal = normal.normalize();
		this.point = intersection;
		this.shape = shape;
		this.t = t;
		this.incoming = incoming;
	}
	
	public Ray getTransmissionRay() {
	Vector v = ray.getDirection().negate();
	Vector n = normal;
	double cosi = v.dot(n);
	double nint;
	if(incoming) {
		nint = 1.0 / shape.getAspect().ior;
	}else{
		nint = shape.getAspect().ior;
	}
	double cost = Math.sqrt(1.0 - nint*nint * (1 - cosi*cosi));

	return new Ray(point, n.times(nint * cosi - cost).minusVector(v.times(nint)));
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
	
	public boolean isIncoming() {
		return incoming;
	}
	
}
