package utils;

import javax.imageio.ImageIO;

import mathElements.Point;
import mathElements.Vector;
import shapes.Plane;
import shapes.Sphere;
import shapes.Triangle;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RayTracerVIEJO {
	/*public static final int MAX_RECURSION_LEVEL = 3;
	public static final Color BACKGROUND_COLOR = Color.GRAY;

	private Camera camera;
	private final ArrayList<LightVIEJO> lights = new ArrayList<LightVIEJO>();
	private final ArrayList<Aspect> aspects = new ArrayList<Aspect>();
	private final ArrayList<Obj3D> shapes = new ArrayList<Obj3D>();
	private int cols;
	private int rows;

	public RayTracerVIEJO(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
	}*/
	
	public RayTracerVIEJO(){ 
		cols = -1;
		rows = -1;
	}


	private Color shade(RayHit hit, int depth) {
		Color color = Color.BLACK;

		// ambient light source
		LightVIEJO light = lights.get(0);
		if(light != null && hit.getShape().getAspect().amb > 0) {
			color = ColorUtil.blend(color, ColorUtil.intensify(hit.getShape().getColor(hit.getPoint()), light.getColor(hit.getPoint())));
		}

		for(int i = 1;i < lights.size();i++) {
			light = lights.get(i);
			Point lightLocation = new Point(light.getX(), light.getY(), light.getZ());
			Vector lightRayVec = new Vector(hit.getPoint(), lightLocation);
			//Vector lightRayVec = new Vector(lightLocation.getX()*-hit.getPoint().getX(), lightLocation.getY()*-hit.getPoint().getY(), lightLocation.getZ()*-hit.getPoint().getZ());
			Ray lightRay = new Ray(hit.getPoint(), lightRayVec);
			lightRay.setT(lightRayVec.magnitude());

			RayHit obstruction = findHit(lightRay);
			if(obstruction == null) {
				// not in the shadow
				//              add the basic Phong shading for this light
				//                (diffuse, specular components)

				Color c = light.getColor(hit, lightRay);
				color = ColorUtil.blend(color, c);
			}//else{
				//System.out.println("Se produce sombra "+hit.getShape().getClass().getCanonicalName());
				//color = Color.BLACK;
			//}
		}

		if(depth <= MAX_RECURSION_LEVEL) {
			if(hit.getShape().getAspect().isReflective()) {
				color = ColorUtil.blend(color, ColorUtil.intensify(trace(hit.getReflectionRay(), depth+1), hit.getShape().getAspect().refl));
			}

			if(hit.getShape().getAspect().isTransmittive()) {
				color = ColorUtil.blend(color, ColorUtil.intensify(trace(hit.getTransmissionRay(), depth+1), hit.getShape().getAspect().trans));
			}
		}

		return color;

		// Possible Outline:
		//  Get the normal vector from hit
		//  Get the contact point as R's endpoint
		//  Get the pigment and surface finish from hitObj
		//  Initialize accumulated color to Black (0,0,0)
		//  for each (light source i)
		//      if (i is the ambient source) add ambient shading to accumulated color
		//      else
		//          Ray LightRay = ray from contact point to light
		//          call Hit(LightRay) to determine whether in shadow
		//          if (not in shadow) skip the next step
		//              add the basic Phong shading for this light
		//                (diffuse, specular components)
		//  if (reflective)
		//      increment recursion level: lev++
		//      shoot reflection ray and add its contribution
		//  if (transmittive)
		//      increment recursion level: lev++
		//      shoot refraction ray and add its contribution
		//  return the final accumulated color
	}

//	private RayHit findHit(Ray ray) {
//		RayHit hit = null;
//		for(Obj3D shape: shapes) {
//			RayHit h = shape.intersect(ray);
//			if(h != null && h.getT() < ray.getT()) {
//				hit = h;
//				ray.setT(h.getT());
//			}
//		}
//		return hit;
//	}

//	private Color trace(Ray ray, int depth) {
//
//		RayHit hit = findHit(ray);
//
//		if(hit != null) {
//			return shade(hit, depth);
//		}
//
//		// missed everything. return background color
//		return BACKGROUND_COLOR;
//	}


//	public void draw(File outFile) throws IOException, InterruptedException {
//		final BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
//
//		long start = System.currentTimeMillis();
//			for(int r = 0;r < rows; r++) {
//				for(int c = 0;c < cols; c++) {
//					image.setRGB(c, r, getPixelColor(c, r).getRGB());
//				}
//			}
//		System.out.println("Finished in: " + (System.currentTimeMillis()-start) + "ms");
//
//		ImageIO.write(image, "bmp", outFile);
//	}


	public Color getPixelColor(int col, int row) {
		//int bmpRow = rows-1 - row; NO TENGO MUY CLARO LO QUE HACIA ESTO

		/*if(Main.ANTI_ALIAS) {
			Ray ray = camera.getRay(col, bmpRow, 0, 0);
			Color c1 = trace(ray, 0);
			ray = camera.getRay(col, bmpRow, .5, 0);
			Color c2 = trace(ray, 0);
			ray = camera.getRay(col, bmpRow, 0, .5);
			Color c3 = trace(ray, 0);
			ray = camera.getRay(col, bmpRow, .5, .5);
			Color c4 = trace(ray, 0);

			return ColorUtil.average(c1, c2, c3, c4);
		} else {*/
			Ray ray = camera.getRay(col,row);
			//System.out.println(ray.getDirection()+" "+ray.getOrigin());
			return trace(ray, 1);
		//}
	}


//	public void readScene(File file) throws FileNotFoundException {
//		Scanner scanner = new Scanner(file).useLocale(Locale.ENGLISH);
//
//		/* Reading general configuration parameters */
//		Point eye = readPoint(scanner);
//		Point worldCenter = readPoint(scanner);
//		Vector up = new Vector(0.0, 1.0, 0.0);
//		int displayScreenHeight = scanner.nextInt();
//		int displayScreenWidth = scanner.nextInt();
//		int numPixelsX = scanner.nextInt();
//		int numPixelsY = scanner.nextInt();
//		cols = numPixelsX;
//		rows = numPixelsY;
//		camera = new Camera(eye, worldCenter, up, cols, rows, displayScreenHeight, displayScreenWidth);
//		
//		/* Reading lights */
//		int numLights = scanner.nextInt();
//		for(int i=0;i<numLights;i++) {
//			Point location = readPoint(scanner);
//			Color color = readColor(scanner);
//			lights.add(new LightVIEJO(location.getX(), location.getY(), location.getZ(), 
//					color.getRed(), color.getGreen(), color.getBlue()));
//		}
//
//		/* Reading Aspects of the Objects that appear in the scene */
//		int numFins = scanner.nextInt();
//		for(int i=0;i<numFins;i++) {
//			aspects.add(new Aspect(scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat()));
//		}
//
//		// read shapes
//		int numShapes = scanner.nextInt();
//		for(int i=0;i<numShapes;i++) {
//			int finishNum = scanner.nextInt();
//			String name = scanner.next();
//			Obj3D shape = null;
//			if("sphere".equals(name)) {
//				shape = new Sphere(readPoint(scanner), scanner.nextDouble(), 
//						new Colour(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), 0, 0));
//			} else if("plane".equals(name)) {
//				Point p1 = readPoint(scanner);
//				Point p2 = readPoint(scanner);
//				Point p3 = readPoint(scanner);
//				Point p4 = readPoint(scanner);
//				shape = new Plane(p1, p2, p3, p4, 
//						new Colour(scanner.nextInt(), scanner.nextInt(),scanner.nextInt(), 0, 0) );
//			}else if("triangle".equals(name)){
//				shape = new Triangle(readPoint(scanner), readPoint(scanner), readPoint(scanner), 
//						new Colour(scanner.nextInt(), scanner.nextInt(),scanner.nextInt(),0,0));
//			} else {
//				System.err.println("OBJETO DESCONOCIDO");
//			}
//
//			shape.setAspect(aspects.get(finishNum));
//			shapes.add(shape);
//		}
//	}
//
//	private static Color readColor(Scanner scanner) {
//		return new Color(ColorUtil.clamp(scanner.nextFloat()), ColorUtil.clamp(scanner.nextFloat()), ColorUtil.clamp(scanner.nextFloat()));
//	}
//	private static Vector readVector(Scanner scanner) {
//		return new Vector(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
//	}
//	private static Point readPoint(Scanner scanner) {
//		return new Point(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
//	}
//	private static int readNumber(Scanner scanner) {
//		return scanner.nextInt();
//	}
}