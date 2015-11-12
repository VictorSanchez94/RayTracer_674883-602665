package shapes;

import utils.Colour;
import utils.Obj3D;
import utils.Point;
import utils.Ray;

public class Triangle extends Obj3D {
	
	private Point p1;
	private Point p2;
	private Point p3;
	private Colour colour;
	
	/**
	 * Constructor with all arguments predefined
	 */
	public Triangle (Point p1, Point p2, Point p3, Colour colour) {
		super((p1.getX()+p2.getX()+p3.getX())/3, (p1.getY()+p2.getY()+p3.getY())/3, (p1.getZ()+p2.getZ()+p3.getZ())/3);
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.colour = colour;
	}
	
	/**
	 * Constructor with custom colour
	 */
	public Triangle (Point p1, Point p2, Point p3, int red, int green, int blue, double K, double I) {
		super((p1.getX()+p2.getX()+p3.getX())/3, (p1.getY()+p2.getY()+p3.getY())/3, (p1.getZ()+p2.getZ()+p3.getZ())/3);
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.colour = new Colour(red, green, blue, K, I);
	}
	
	public Point getCollisionPoint (Ray ray) {
		
	}
	
}
