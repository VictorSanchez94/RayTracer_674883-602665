package utils;

public class Colour {

	private double red;
	private double green;
	private double blue;
	private float K;
	private float I;
	
	/**
	 * Constructor with custome color
	 * Pre: Arguments must have value between 0-255.
	 */
	public Colour (double red, double green, double blue, float K, float I) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.K = K;
		this.I = I;
	}
	
	/**
	 * Constructor white light
	 */
	public Colour (float K, float I) {
		this.red = 255;
		this.green = 255;
		this.blue = 255;
		this.K = K;
		this.I = I;
	}

	public float getK() {
		return K;
	}

	public void setK(float k) {
		K = k;
	}

	public float getI() {
		return I;
	}

	public void setI(float i) {
		I = i;
	}

	public double getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public double getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public double getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
	
}
