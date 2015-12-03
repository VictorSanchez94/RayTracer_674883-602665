package utils;

import java.awt.Color;

import mathElements.Point;

public class Obj3D {
	
	protected double x;
	protected double y;
	protected double z;
	protected Color color;
	
	public Obj3D (double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
		color = new Color(0, 0, 0);
		//new Obj3D(x, y, z, new Color(0, 0, 0)); //Si no se especifica color se crea con color negro
	}
	
	public Obj3D (double x, double y, double z, Color color){
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = color;
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return this.z;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Color getColor(Point punto) {
		Color color = new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue());
		return color;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public void setColour(Color color) {
		this.color = color;
	}
	
	public RayHit intersect(Ray ray) {
		return this.intersect(ray);
	}
	
	public void setAspect(Aspect aspect) {
		
	}
	
	public Aspect getAspect(){
		return null;
	}
	
	public String getType(){
		return "";
	}
	
}
