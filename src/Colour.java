package utils;

public class Colour {

	private int red;
	private int green;
	private int blue;
	private double K;
	private double I;
	
	/**
	 * Constructor with custome color
	 * Pre: Arguments must have value between 0-255.
	 */
	public Colour (int red, int green, int blue, double K, double I) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.K = K;
		this.I = I;
	}
	
	/**
	 * Constructor white light
	 */
	public Colour (double K, double I) {
		this.red = 255;
		this.green = 255;
		this.blue = 255;
		this.K = K;
		this.I = I;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
	
}
