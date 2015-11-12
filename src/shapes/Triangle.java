package shapes;

import utils.Colour;
import utils.Point;

public class Triangle {
	
	private Point p1;
	private Point p2;
	private Point p3;
	private Colour colour;
	
	/**
	 * Constructor with all arguments predefined
	 */
	public Triangle (Point p1, Point p2, Point p3, Colour colour) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.colour = colour;
	}
	
	/**
	 * Constructor with custom colour
	 */
	public Triangle (Point p1, Point p2, Point p3, int red, int green, int blue, double K, double I) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.colour = new Colour(red, green, blue, K, I);
	}
	
	public Point getCollisionPoint (Ray ray) {
		
	}
	
}
