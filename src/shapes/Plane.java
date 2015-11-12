package shapes;

import utils.Point;
import utils.Ray;
import utils.RayHit;
import utils.Vector;
import utils.Obj3D;

public class Plane extends Obj3D {
	private Point a, b, c, d;
	private Vector normal;

	/**
	 * Constructor of a plane.
	 * Arguments must be: a & b => adjacent vertex
	 * 					  c & d => adjacent vertex
	 */
	public Plane(Point a, Point b, Point c, Point d) {
		super((a.getX()+d.getX())/2, (a.getY()+d.getY())/2, (a.getZ()+d.getZ())/2);
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.normal = new Vector(a, d).normalize();
	}

	public RayHit intersect(Ray ray) {
		
	}
}
