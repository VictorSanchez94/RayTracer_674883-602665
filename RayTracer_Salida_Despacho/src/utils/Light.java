package utils;

import java.awt.Color;

import mathElements.Point;
import mathElements.Vector;

public class Light extends Obj3D {

	/**
	 * Constructor with Colour object as argument.
	 */
	public Light(double x, double y, double z, Color colour) { //TODO: previsiblemente no se usara
		super(x, y, z, colour);
	}
	
	/**
	 * Constructor with custom colour
	 */
	public Light(double x, double y, double z, int red, int green, int blue) {
		super(x, y, z, new Color(red, green, blue));
	}

	public Color getColor() {
		return super.getColor();
	}

	public void setColour(Color colour) {
		super.setColour(colour);
	}
	
	public Color getColor(RayHit hit, Ray lightRay) {
		double distance = lightRay.getOrigin().distanceTo(new Point(this.getX(), this.getY(), this.getZ()));
		//float attenuationFactor = getAttenuationFactor(distance);
		float attenuationFactor = 1;

		// diffuse
		float diffuseStrength;
		if(hit.getShape().getAspect().diff > 0) {
			diffuseStrength = hit.getShape().getAspect().diff * (float)Math.max(0.0, hit.getNormal().dot(lightRay.getDirection()));
		} else {
			diffuseStrength = 0.0f;
		}

		// specular
		float specularStrength;
		if(hit.getShape().getAspect().spec > 0) {
			Vector halfway = Vector.halfway(lightRay.getDirection(), hit.getRay().getDirection().negate());
			specularStrength = hit.getShape().getAspect().spec * (float)Math.pow(Math.max(0.0, hit.getNormal().dot(halfway)), hit.getShape().getAspect().shiny);

		// NOTE: the method commented below seems to look better, but the specular points are much smaller, and less spread-out
//		Vector r = lightRay.direction.minus(hit.normal.times(2.0*lightRay.direction.dot(hit.normal)));
//		float specularStrength = hit.shape.finish.spec * (float)Math.pow(Math.max(0.0, hit.ray.direction.dot(r)), hit.shape.finish.shiny);
		} else {
			specularStrength = 0.0f;
		}


		float[] shapeColor = hit.getShape().getColor(hit.getPoint()).getRGBColorComponents(null);
		//float[] intensity = color.getRGBColorComponents(null);
		float[] intensity = {color.getRed(), color.getGreen(), color.getBlue()};
		float red = intensity[0] * attenuationFactor * (shapeColor[0] * diffuseStrength + specularStrength);
		float green = intensity[1] * attenuationFactor * (shapeColor[1] * diffuseStrength + specularStrength);
		float blue = intensity[2] * attenuationFactor * (shapeColor[2] * diffuseStrength + specularStrength);


		return new Color(ColorUtil.clamp(red), ColorUtil.clamp(green), ColorUtil.clamp(blue));
	}
}
