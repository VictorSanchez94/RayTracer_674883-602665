package shapes;
import utils.Obj3D;
import utils.Point;
import utils.Ray;
import utils.RayHit;
import utils.Vector;

public class Sphere extends Obj3D {
	Point center;
	double radius;

	public Sphere(Point center, double radius) {
		super(center.getX(), center.getY(), center.getZ());
		this.center = center;
		this.radius = radius;
	}
	
	public RayHit intersect(Ray ray) {
		Point rayOrigin = ray.getOrigin();
		Vector d = ray.getDirection();
		Vector aminusc = new Vector(center, rayOrigin); // (a-c)
		double a = d.dot(d); //d*d ESTE ES EL QUE MEDIO FUNCIONA
		double b = aminusc.dot(d); //(a-c)*d ESTE ES EL QUE MEDIO FUNCIONA
		//double c = rayObjectVector.dot(rayObjectVector) - 1; // (a-c)*(a-c)-1  Para una esfera de radio 1
		//double c = rayObjectVector.dot(rayObjectVector) - radius*radius; // Si se quiere una esfera de radio distinto de 1
		//double c = (aminusc.minusVector(new Vector(1.0,1.0,1.0))).dot(aminusc.minusVector(new Vector(1.0,1.0,1.0))); //ESTE ES EL QUE MEDIO FUNCIONA
		//double c = aminusc.dot(aminusc) - radius*radius;
		Vector cAux = aminusc.minusVector(new Vector (radius*radius, radius*radius, radius*radius));
		double c = cAux.dot(cAux);
		double discriminant = b*b - a*c;
		if(discriminant < 0){ // There is no intersection
			return null; 
		}
		
		double intersection1 = (-2*b - Math.sqrt(4*discriminant)) / 2*a;
		double intersection2 = (-2*b + Math.sqrt(4*discriminant)) / 2*a;
		
		if(intersection1 < 0 && intersection2 < 0) { //The sphere is beetween camera and plane of visualization
			return null;
		}
		//System.out.println("HAY INTERSECCION CON LA ESFERA");
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
		return new RayHit(ray, normal, intersection);
	}
}