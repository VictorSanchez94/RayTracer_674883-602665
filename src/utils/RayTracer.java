package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import javax.imageio.ImageIO;

import mathElements.Point;
import mathElements.Vector;
import shapes.Plane;
import shapes.Sphere;
import shapes.Triangle;

public class RayTracer {
	public static final int MAX_RECURSION_LEVEL = 3;
	public static final Color BACKGROUND_COLOR = Color.GRAY;

	private Camera camera;
	private final ArrayList<Light> lights = new ArrayList<Light>();
	private final ArrayList<Aspect> aspects = new ArrayList<Aspect>();
	private final ArrayList<Obj3D> shapes = new ArrayList<Obj3D>();
	private int cols;
	private int rows;
	
	public RayTracer(){
		
	}
	/** Read the information in the input file */
	public void readScene(File file) throws FileNotFoundException {
		Scanner scanner = new Scanner(file).useLocale(Locale.ENGLISH);

		/* Reading General Configuration parameters */
		Point eye = readPoint(scanner);
		Point worldCenter = readPoint(scanner);
		Vector up = new Vector(0.0, 1.0, 0.0);
		int displayScreenHeight = scanner.nextInt();
		int displayScreenWidth = scanner.nextInt();
		int numPixelsX = scanner.nextInt();
		int numPixelsY = scanner.nextInt();
		cols = numPixelsX;
		rows = numPixelsY;
		camera = new Camera(eye, worldCenter, up, cols, rows, displayScreenHeight, displayScreenWidth);
		
		/* Reading Lights. The first one is the ambiental */
		int numLights = scanner.nextInt();
		for(int i=0;i<numLights;i++) {
			Point location = readPoint(scanner);
			Color color = readColor(scanner);
			lights.add(new Light(location.x, location.y, location.z, 
					color.getRed(), color.getGreen(), color.getBlue()));
		}

		/* Reading Aspects of the Objects that appear in the scene */
		int numFins = scanner.nextInt();
		for(int i=0;i<numFins;i++) {
			aspects.add(new Aspect(scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat()));
		}

		/* Reading Objects that appear in the scene */
		int numShapes = scanner.nextInt();
		for(int i=0;i<numShapes;i++) {
			int finishNum = scanner.nextInt();
			String name = scanner.next();
			Obj3D shape = null;
			if("sphere".equals(name)) {
				Point punto = readPoint(scanner);
				shape = new Sphere(punto, scanner.nextDouble(), 
						new Color(scanner.nextInt()*255, scanner.nextInt()*255, scanner.nextInt()*255)); //TODO: Cochinada
			} else if("plane".equals(name)) {
				Point p1 = readPoint(scanner);
				Point p2 = readPoint(scanner);
				Point p3 = readPoint(scanner);
				Point p4 = readPoint(scanner);
				shape = new Plane(p1, p2, p3, p4, 
						new Color((int)scanner.nextDouble()*255, (int)scanner.nextDouble()*255, (int)scanner.nextDouble()*255) );
			}else if("triangle".equals(name)){
				shape = new Triangle(readPoint(scanner), readPoint(scanner), readPoint(scanner), 
						new Color(scanner.nextInt(), scanner.nextInt(),scanner.nextInt()));
			} else {
				System.err.println("OBJETO DESCONOCIDO");
			}

			shape.setAspect(aspects.get(finishNum));
			shapes.add(shape);
		}
	}

	private static Color readColor(Scanner scanner) {
		return new Color(ColorUtil.clamp(scanner.nextFloat()), ColorUtil.clamp(scanner.nextFloat()), ColorUtil.clamp(scanner.nextFloat()));
	}
	private static Point readPoint(Scanner scanner) {
		Point response = new Point(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
		return response;
	}
	
	/** Draw the output image. Core of the Raytracer */
	public void draw(File outFile) throws IOException, InterruptedException {
		final BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		for(int r = 0;r < rows; r++) {
			for(int c = 0;c < cols; c++) {
				image.setRGB(c, r, getPixelColor(c, r).getRGB());
			}
		}
		ImageIO.write(image, "bmp", outFile);
	}
	
	public Color getPixelColor(int col, int row){
		Color response = null;
		//TODO: Este ray habra que calcularlo distinto para el antialiasing
		Ray ray = camera.getRay(col,row);
		RayHit h = findHit(ray);
		
		if(h != null) { // Se ha producido una colision con algun objeto
			Color color = Color.BLACK;
			// Ambient light
			Light light = lights.get(0);
			if(light != null && h.getShape().getAspect().amb > 0) {
				color = ColorUtil.blend(color, 
						ColorUtil.intensify(h.getShape().getColor(h.getPoint()), light.getColor(h.getPoint())));
			}
			
			// Difuse light
			for(int i = 1;i < lights.size();i++) {
				light = lights.get(i);
				Point lightLocation = new Point(light.getX(), light.getY(), light.getZ());
				Vector l = new Vector(h.getPoint(), lightLocation); // Vector beetween hit point and light location
				Ray lightRay = new Ray(h.getPoint(), l);
				lightRay.setT(l.magnitude());
				//TODO: Sombras no van todavia
				/*RayHit obstruction = findHit(lightRay);
				if(obstruction == null) {
					Color c = light.getColor(h, lightRay);
					color = ColorUtil.blend(color, c);
					//color = Color.cyan;
				}*/
				double scalarProduct = h.getNormal().dot(l.normalize());
				if(scalarProduct<0){
					scalarProduct = 0;
				}
				color = new Color((int)(scalarProduct*color.getRed()), 
						(int)(scalarProduct*color.getGreen()), (int)(scalarProduct*color.getBlue()));
				
			}
			
			// Specular light
			
			
			response = color;
		}else{
			response = BACKGROUND_COLOR;
		}
		return response;
	}
	
	
	private RayHit findHit(Ray ray) {
		RayHit hit = null;
		for(Obj3D shape: shapes) {
			RayHit h = shape.intersect(ray);
			if(h != null && h.getT() < ray.getT()) {
				hit = h;
				ray.setT(h.getT());
			}
		}
		return hit;
	}
}
