package utils;

import java.awt.Color;

import mathElements.Point;

public class LightVIEJO extends Obj3D {

	private Color colour;
	private double k; 
	private double i;
	
	/**
	 * Constructor with Colour object as argument.
	 */
	public LightVIEJO(double x, double y, double z, Color colour) { //TODO: previsiblemente no se usara
		super(x, y, z);
		this.colour = colour;
	}
	
	/**
	 * Constructor with custom colour
	 */
	public LightVIEJO(double x, double y, double z, int red, int green, int blue) {
		super(x, y, z);
		this.colour = new Color(red, green, blue);
	}

	public Color getColor() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}
	
	public Color getColor(RayHit hit, Ray lightRay) {
		double distance = lightRay.getOrigin().distanceTo(new Point(this.getX(), this.getY(), this.getZ()));
		//float attenuationFactor = getAttenuationFactor(distance);
		float attenuationFactor = 1;

		// diffuse
		float diffuseStrength;
		//if(hit.getShape().finish.diff > 0) {
			//diffuseStrength = hit.shape.finish.diff * (float)Math.max(0.0, hit.normal.dot(lightRay.direction));
		//} else {
			diffuseStrength = 0.0f;
		//}

		// specular
		float specularStrength;
		//if(hit.shape.finish.spec > 0) {
			//Vector halfway = Vector.halfway(lightRay.direction, hit.ray.direction.negate());
			//specularStrength = hit.shape.finish.spec * (float)Math.pow(Math.max(0.0, hit.normal.dot(halfway)), hit.shape.finish.shiny);

		// NOTE: the method commented below seems to look better, but the specular points are much smaller, and less spread-out
//		Vector r = lightRay.direction.minus(hit.normal.times(2.0*lightRay.direction.dot(hit.normal)));
//		float specularStrength = hit.shape.finish.spec * (float)Math.pow(Math.max(0.0, hit.ray.direction.dot(r)), hit.shape.finish.shiny);
		//} else {
			specularStrength = 0.0f;
		//}


		float[] shapeColor = hit.getShape().getColor(hit.getPoint()).getRGBColorComponents(null);
		//float[] intensity = color.getRGBColorComponents(null);
		float[] intensity = {color.getRed(), color.getGreen(), color.getBlue()};
		float red = intensity[0] * attenuationFactor * (shapeColor[0] * diffuseStrength + specularStrength);
		float green = intensity[1] * attenuationFactor * (shapeColor[1] * diffuseStrength + specularStrength);
		float blue = intensity[2] * attenuationFactor * (shapeColor[2] * diffuseStrength + specularStrength);


		return new Color(ColorUtil.clamp(red), ColorUtil.clamp(green), ColorUtil.clamp(blue));
	}
}
