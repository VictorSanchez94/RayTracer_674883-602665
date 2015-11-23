package shapes;

import java.awt.Color;

import mathElements.Point;
import mathElements.Vector;
import utils.Aspect;
import utils.Obj3D;
import utils.Ray;
import utils.RayHit;

public class Triangle extends Obj3D {
	
	private Point a, b, c;		//Vertex of the triangle
	private Color color;		//Colour of the triangle
	private Vector normal;		//Normal vector of the triangle
	private Aspect aspect;
	/**
	 * Constructor with all arguments predefined
	 */
	public Triangle (Point a, Point b, Point c, Color color) {
		super((a.getX()+b.getX()+c.getX())/3, (a.getY()+b.getY()+c.getY())/3, (a.getZ()+b.getZ()+c.getZ())/3, color);		//Center of the triangle
		this.a = a;
		this.b = b;
		this.c = c;
		this.color = color;
		Vector aux1 = new Vector(a,b);
		Vector aux2 = new Vector(a,c);
		this.normal = aux1.cross(aux2);
	}
	
	/**
	 * Constructor with custom colour
	 */
	/*public Triangle (Point a, Point b, Point c, int red, int green, int blue, float K, float I) {
		super((a.getX()+b.getX()+c.getX())/3, (a.getY()+b.getY()+c.getY())/3, (a.getZ()+b.getZ()+c.getZ())/3);
		this.a = a;
		this.b = b;
		this.c = c;
		this.colour = new Colour(red, green, blue, K, I);
		Vector aux1 = new Vector(a,b);
		Vector aux2 = new Vector(a,c);
		this.normal = aux1.cross(aux2);
	}*/
	
	/**
	 * Pre: ---
	 * Post: This method returns a RayHit object with the point where ray intersects
	 * 		 with the triangle.
	 */
	public RayHit intersect (Ray ray) {
		Vector vAux = new Vector(ray.getOrigin(), a);
		double lambda = vAux.dot(normal) / ray.getDirection().dot(normal);
		if(ray.getDirection().times(lambda).plus(ray.getOrigin().substraction(a)).dot(normal) == 0) {	//Ray intersect with plane
			Point hitPoint = ray.getPoint(lambda);
			Vector v1 = new Vector(a,b);
			Vector v2 = new Vector(b,c);
			Vector v3 = new Vector(c,a);
			double s1 = v1.cross(new Vector(a,hitPoint)).dot(normal);		//((p2-p1)x(p-p1))*n
			double s2 = v2.cross(new Vector(b,hitPoint)).dot(normal);		//((p3-p2)x(p-p2))*n
			double s3 = v3.cross(new Vector(c,hitPoint)).dot(normal);		//((p1-p3)x(p-p3))*n
			if(s1 > 0.0 && s2 > 0.0 && s3 > 0){			//Ray intersects on the triangle area
				return new RayHit(ray,normal,hitPoint, this, lambda);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public void setAspect(Aspect asp){
		this.aspect = asp;
	}
	
	public Aspect getAspect(){
		return aspect;
	}
	
}
