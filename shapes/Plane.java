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
		Vector aux1 = new Vector(a,b);
		Vector aux2 = new Vector(a,c);
		this.normal = aux1.cross(aux2);
	}

	/**
	 * Pre: ---
	 * Post: This method returns a RayHit object with the point where ray intersects
	 * 		 with the plane.
	 * 
	 */
	public RayHit intersect(Ray ray) {
		if(ray.getDirection().dot(normal) != 0){	//The ray intersect with plane
			Vector vAux = new Vector(a.substraction(ray.getOrigin()));
			double lambda = vAux.dot(normal)/ray.getDirection().dot(normal);
			return new RayHit(ray,normal,ray.getPoint(lambda));
		}else{
			return null;
		}		
	}
	
	public static void main (String[] args) {
		Ray r = new Ray(new Point(2,3,0), new Vector(1.0,0.0,0.0));
		Plane p = new Plane(new Point(4,4,-2), new Point(4,2,-2), new Point(4,4,2), new Point(4,2,2));
		RayHit rh = p.intersect(r);
		if(rh == null){
			System.out.println("NULL");
		}else{
			System.out.println(rh.toString());
		}
	}
	
}