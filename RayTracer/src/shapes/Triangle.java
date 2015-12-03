package shapes;

import java.awt.Color;

import mathElements.AffineTransform;
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
		this.a = AffineTransform.translate(a.getX(), a.getY(), a.getZ());
		this.b = AffineTransform.translate(b.getX(), b.getY(), b.getZ());
		this.c = AffineTransform.translate(c.getX(), c.getY(), c.getZ());
		this.color = color;
		Vector aux1 = new Vector(a,b);
		Vector aux2 = new Vector(a,c);
		this.normal = aux1.cross(aux2).normalize();
	}
	
	/**
	 * Pre: ---
	 * Post: This method returns a RayHit object with the point where ray intersects
	 * 		 with the triangle.
	 */
	public RayHit intersect (Ray ray) {
		double DxN = ray.getDirection().dot(normal);
		if (DxN != 0) {		//Ray and triangle not parallels
			if (DxN > 0) {	//Vectors with same direction
				DxN = ray.getDirection().dot(normal.negate());
				double lambda = a.minus(ray.getOrigin()).dot(normal.negate()) / DxN;
				double s1 = b.minus(a).cross(ray.getEnd(lambda).minus(a)).dot(normal.negate());
				double s2 = c.minus(b).cross(ray.getEnd(lambda).minus(b)).dot(normal.negate());
				double s3 = a.minus(c).cross(ray.getEnd(lambda).minus(c)).dot(normal.negate());
				if((s1 < 0 && s2 < 0 && s3 < 0) || (s1 > 0 && s2 > 0 && s3 > 0)) {			//Same sign => visible
					return new RayHit(ray,normal.negate(),ray.getEnd(lambda), this, lambda);
				}else{
					return null;
				}
			}else{
				double lambda = a.minus(ray.getOrigin()).dot(normal) / DxN;
				if (lambda >= 0) {
					double s1 = b.minus(a).cross(ray.getEnd(lambda).minus(a)).dot(normal);
					double s2 = c.minus(b).cross(ray.getEnd(lambda).minus(b)).dot(normal);
					double s3 = a.minus(c).cross(ray.getEnd(lambda).minus(c)).dot(normal);
					if((s1 < 0 && s2 < 0 && s3 < 0) || (s1 > 0 && s2 > 0 && s3 > 0)) {		//Same sign => visible
						return new RayHit(ray,normal,ray.getEnd(lambda), this, lambda);
					}else{
						return null;
					}
				}else{					//Intersection behind the screen
					return null;
				}
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
	
	
	
public String getType() {
		return "triangle";
	}
	
}
