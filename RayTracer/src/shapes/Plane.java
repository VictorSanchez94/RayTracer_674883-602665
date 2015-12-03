package shapes;

import java.awt.Color;

import mathElements.AffineTransform;
import mathElements.Point;
import mathElements.Vector;
import utils.Aspect;
import utils.Obj3D;
import utils.Ray;
import utils.RayHit;

public class Plane extends Obj3D {
	private Point a, b, c, d;		//Vertex of the plane
	private Vector normal;
	private Aspect aspect;

	/**
	 * Constructor of a plane.
	 * Arguments must be: a & b => adjacent vertex
	 * 					  c & d => adjacent vertex
	 */
	public Plane(Point a, Point b, Point c, Point d, Color color) {
		super((a.getX()+d.getX())/2, (a.getY()+d.getY())/2, (a.getZ()+d.getZ())/2, color);
		this.a = AffineTransform.translate(a.getX(), a.getY(), a.getZ());
		this.b = AffineTransform.translate(b.getX(), b.getY(), b.getZ());;
		this.c = AffineTransform.translate(c.getX(), c.getY(), c.getZ());;
		this.d = AffineTransform.translate(d.getX(), d.getY(), d.getZ());;
		Vector aux1 = new Vector(a,b);
		Vector aux2 = new Vector(a,c);
		this.normal = aux2.cross(aux1).normalize();
	}

	public Color getColor() {
		return super.getColor();
	}

	public void setColour(Color colour) {
		super.setColour(colour);
	}

//	/**
//	 * Pre: ---
//	 * Post: This method returns a RayHit object with the point where ray intersects
//	 * 		 with the plane.
//	 */
//	public RayHit intersect(Ray ray) {
//		if(ray.getDirection().dot(normal) != 0){	//Ray and plane no parallel
//			if(ray.getDirection().dot(normal) > 0){
//				Point pAux = new Point(super.getX(), super.getY(), super.getZ());
//				Vector vAux = new Vector(pAux.substraction(ray.getOrigin()));
//				double lambda = vAux.dot(normal) / ray.getDirection().dot(normal);
//				Vector v1 = new Vector(a,b);
//				Vector v2 = new Vector(b,d);
//				Vector v3 = new Vector(d,c);
//				Vector v4 = new Vector(c,a);
//				Point hitPoint = ray.getPoint(lambda);
//				double s1 = v1.cross(new Vector(a,hitPoint)).dot(normal);		//((p2-p1)x(p-p1))*n
//				double s2 = v2.cross(new Vector(b,hitPoint)).dot(normal);		//((p3-p2)x(p-p2))*n
//				double s3 = v3.cross(new Vector(d,hitPoint)).dot(normal);		//((p1-p3)x(p-p3))*n
//				double s4 = v4.cross(new Vector(c,hitPoint)).dot(normal);
//				if(s1 > 0.0 && s2 > 0.0 && s3 > 0 && s4 > 0.0){
//					return new RayHit(ray,normal,ray.getPoint(lambda), this, lambda); 
//				}else{
//					return null;
//				}
//			}else{
//				this.normal = normal.negate();
//				Point pAux = new Point(super.getX(), super.getY(), super.getZ());
//				Vector vAux = new Vector(pAux.substraction(ray.getOrigin()));
//				double lambda = vAux.dot(normal) / ray.getDirection().dot(normal);
//				Vector v1 = new Vector(a,b);
//				Vector v2 = new Vector(b,d);
//				Vector v3 = new Vector(d,c);
//				Vector v4 = new Vector(c,a);
//				Point hitPoint = ray.getPoint(lambda);
//				double s1 = v1.cross(new Vector(a,hitPoint)).dot(normal);		//((p2-p1)x(p-p1))*n
//				double s2 = v2.cross(new Vector(b,hitPoint)).dot(normal);		//((p3-p2)x(p-p2))*n
//				double s3 = v3.cross(new Vector(d,hitPoint)).dot(normal);		//((p1-p3)x(p-p3))*n
//				double s4 = v4.cross(new Vector(c,hitPoint)).dot(normal);
//				if(s1 > 0.0 && s2 > 0.0 && s3 > 0 && s4 > 0.0){
//					return new RayHit(ray,normal,ray.getPoint(lambda), this, lambda); 
//				}else{
//					return null;
//				}
//			}
//		}else{
//			return null;
//		}		
//	}
	
	/**
	 * Pre: ---
	 * Post: This method returns a RayHit object with the point where ray intersects
	 * 		 with the plane.
	 */
	public RayHit intersect(Ray ray) {
		double DxN = ray.getDirection().dot(normal);
		if (DxN != 0) {		//Ray and plane nor parallels
			if (DxN > 0) {	//Vectors with same direction
				DxN = ray.getDirection().dot(normal.negate());
				double lambda = a.minus(ray.getOrigin()).dot(normal.negate()) / DxN;
				Vector v1 = new Vector(a,b);
				Vector v2 = new Vector(b,d);
				Vector v3 = new Vector(d,c);
				Vector v4 = new Vector(c,a);
				double s1 = v1.cross(new Vector(a,ray.getEnd(lambda))).dot(normal.negate());		//((p2-p1)x(p-p1))*n
				double s2 = v2.cross(new Vector(b,ray.getEnd(lambda))).dot(normal.negate());		//((p3-p2)x(p-p2))*n
				double s3 = v3.cross(new Vector(d,ray.getEnd(lambda))).dot(normal.negate());		//((p1-p3)x(p-p3))*n
				double s4 = v4.cross(new Vector(c,ray.getEnd(lambda))).dot(normal.negate());
				if(s1 > 0.0 && s2 > 0.0 && s3 > 0 && s4 > 0.0){
					return new RayHit(ray,normal.negate(),ray.getPoint(lambda), this, lambda); 
				}else{
					return null;
				}
			}else{
				double lambda = a.minus(ray.getOrigin()).dot(normal) / DxN;
				if (lambda >= 0) {
					Vector v1 = new Vector(a,b);
					Vector v2 = new Vector(b,d);
					Vector v3 = new Vector(d,c);
					Vector v4 = new Vector(c,a);
					double s1 = v1.cross(new Vector(a,ray.getEnd(lambda))).dot(normal.negate());		//((p2-p1)x(p-p1))*n
					double s2 = v2.cross(new Vector(b,ray.getEnd(lambda))).dot(normal.negate());		//((p3-p2)x(p-p2))*n
					double s3 = v3.cross(new Vector(d,ray.getEnd(lambda))).dot(normal.negate());		//((p1-p3)x(p-p3))*n
					double s4 = v4.cross(new Vector(c,ray.getEnd(lambda))).dot(normal.negate());
					if(s1 > 0.0 && s2 > 0.0 && s3 > 0 && s4 > 0.0){
						return new RayHit(ray,normal.negate(),ray.getPoint(lambda), this, lambda); 
					}else{
						return null;
					}
				}else{
					return null;
				}
			}
		}else{				//Ray and plane parallels
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
		return "plane";
	}
	
}