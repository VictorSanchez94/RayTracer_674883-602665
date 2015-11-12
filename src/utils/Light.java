package utils;

public class Light extends Obj3D {

	private Colour colour;
	
	/**
	 * Constructor with Colour object as argument.
	 */
	public Light(double x, double y, double z, Colour colour) {
		super(x, y, z);
		this.colour = colour;
	}
	
	/**
	 * Constructor with custom colour
	 */
	public Light(double x, double y, double z, int red, int green, int blue, double K, double I) {
		super(x, y, z);
		this.colour = new Colour(red, green, blue, K, I);
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}
	
}
