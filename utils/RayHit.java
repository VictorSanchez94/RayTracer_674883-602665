package utils;

public class RayHit {
	public final Ray ray;			//Incident ray
	public final Vector normal;		//Object´s normal vector
	public final Point point;		//Intersection with object point

	public RayHit(Ray ray, Vector normal, Point intersection) {
		this.ray = ray;
		this.normal = normal.normalize();
		this.point = intersection;
	}

	public Ray getReflectionRay() {
		return new Ray(point, ray.getDirection().minusVector(normal.times(2.0*ray.getDirection().dot(normal))));
	}

	public String toString() {
		return "ray: " + ray.toString() + "; normal: " + normal.toString() + "; point: " + point.toString();
	}
	
}
