package utils;

import java.awt.Color;

public class Obj3D {
	
	protected double x;
	protected double y;
	protected double z;
	protected Color color;
	
	public Obj3D (double x, double y, double z){
		Obj3D(x, y, z, Color.BLACK);
	}
	
	public Obj3D (double x, double y, double z, Color color){
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = color;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public RayHit intersect(Ray ray) {
		return this.intersect(ray);
	}
}
