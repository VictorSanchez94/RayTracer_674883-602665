package utils;

public class RayHit {
	public final Ray ray;
	public final Vector normal;
	public final Point point;

	public RayHit(Ray ray, Vector normal, Point intersection) {
		this.ray = ray;
		this.normal = normal.normalize();
		this.point = intersection;
	}

	public Ray getReflectionRay() {
		return new Ray(point, ray.getDirection().minus(normal.times(2.0*ray.getDirection().dot(normal))));
	}

}
