package shapes;
import java.awt.Color;

import mathElements.AffineTransform;
import mathElements.Point;
import mathElements.Vector;
import utils.Aspect;
import utils.Obj3D;
import utils.Ray;
import utils.RayHit;

public class Sphere extends Obj3D {
	private Point center;
	private double radius;
	private Aspect aspect;

	public Sphere(Point center, double radius, Color color) {
		super(center.getX(), center.getY(), center.getZ(), color);
		Point transformedCenter = AffineTransform.translate(center.getX(), center.getY(), center.getZ());
		this.center = transformedCenter;
		double transformedRadius = AffineTransform.changeSize(radius, radius, radius); //The radius is the same in the three axis
		this.radius = transformedRadius;
	}
	
	public RayHit intersect(Ray ray) {
		Point rayOrigin = ray.getOrigin();
		Vector d = ray.getDirection();
		Vector aminusc = new Vector(center, rayOrigin); // (a-c)
		double a = d.dot(d); //d*d
		double b = aminusc.dot(d); //(a-c)*d 
//		Vector cAux = aminusc.minusVector(new Vector (radius*radius, radius*radius, radius*radius));
//		double c = cAux.dot(cAux)-1;
		double c = aminusc.dot(aminusc);	//(a-c)*(a-c)-radius*radius
		c = c - radius*radius;
		double discriminant = b*b - a*c;
		if(discriminant < 0){ // There is no intersection
			return null; 
		}
		
		double intersection1 = (-2*b - Math.sqrt(4*discriminant)) / 2*a;
		double intersection2 = (-2*b + Math.sqrt(4*discriminant)) / 2*a;
		
		if(intersection1 < 0 && intersection2 < 0) { //The sphere is beetween camera and plane of visualization
			return null;
		}

		double tValue;
		Vector normal;
		Point intersection;
		boolean incoming;
		if(intersection1 < 0 && intersection2 > 0) { // intersection1 is not visible and intersection2 is visible
			tValue = intersection2;
			intersection = ray.getEnd(tValue);
			normal = new Vector(intersection, center);
			incoming = false;
		} else { // both intersections are visible, take intersection1 because is the nearest
			tValue = intersection1;
			intersection = ray.getEnd(tValue);
			normal = new Vector(center, intersection);
			incoming = true;
		}

		//return new RayHit(ray, this, normal, intersection, incoming);
		return new RayHit(ray, normal, intersection, this, tValue);
		
//		Point p = ray.getOrigin();
//		Vector u = ray.getDirection();
//		Vector v = new Vector(center, p);
//		double b = 2 * (v.dot(u));
//		double c = v.dot(v) - radius*radius;
//		double discriminant = b*b - 4*c;
//
//		if(discriminant < 0) return null;
//
//		double tMinus = (-b - Math.sqrt(discriminant)) / 2;
//		double tPlus = (-b + Math.sqrt(discriminant)) / 2;
//
//		if(tMinus < 0 && tPlus < 0) {
//			// sphere is behind the ray
//			return null;
//		}
//
//		double tValue;
//		Vector normal;
//		Point intersection;
//		boolean incoming;
//		if(tMinus < 0 && tPlus > 0) {
//			// ray origin lies inside the sphere. take tPlus
//			tValue = tPlus;
////			return null;
//			intersection = ray.getEnd(tValue);
//			normal = new Vector(intersection, center);
//			incoming = false;
//		} else {
//			// both roots positive. take tMinus
//			tValue = tMinus;
//			intersection = ray.getEnd(tValue);
//			normal = new Vector(center, intersection);
//			incoming = true;
//		}
//
//		//return new RayHit(ray, this, normal, intersection, incoming);
//		return new RayHit(ray, normal, intersection, this, tValue);
	}
	
	public void setAspect(Aspect asp){
		this.aspect = asp;
	}
	
	public Aspect getAspect(){
		return aspect;
	}
	
	public String getType() {
		return "sphere";
	}
	
}