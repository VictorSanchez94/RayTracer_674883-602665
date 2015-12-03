package utils;

import java.awt.Color;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import mathElements.Point;
import mathElements.Vector;
import shapes.Plane;
import shapes.Sphere;
import shapes.Triangle;

public class RayTracer {
	public static int MAX_RECURSION_LEVEL = 0;
	public static final Color BACKGROUND_COLOR = Color.GRAY;

	private Camera camera;
	private final ArrayList<Light> lights = new ArrayList<Light>();
	private final ArrayList<Aspect> aspects = new ArrayList<Aspect>();
	private final ArrayList<Obj3D> shapes = new ArrayList<Obj3D>();
	private final ArrayList<Point> vertex = new ArrayList<Point>();
	private int cols;
	private int rows;
	private boolean antialiasing = false;
	private int numRaysAA = 0;
	
	public RayTracer(){

	}
	/** Read the information in the input file */
	public void readScene(File file) throws FileNotFoundException {
		Scanner scanner = new Scanner(file).useLocale(Locale.ENGLISH);
		System.out.println("Leyendo fichero...");
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
		
		/* Reading antialiasing configuration */
		String aa = scanner.next();
		if(aa.equals("true")){
			antialiasing = true;
		}else if(aa.equals("false")){
			antialiasing = false;
		}
		numRaysAA = scanner.nextInt();
		MAX_RECURSION_LEVEL = scanner.nextInt();
		
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
						new Color((int)(scanner.nextDouble()*255), (int)(scanner.nextDouble()*255), (int)(scanner.nextDouble()*255))); //TODO: Cochinada
				shape.setAspect(aspects.get(finishNum));
				shapes.add(shape);
			} else if("plane".equals(name)) {
				Point p1 = readPoint(scanner);
				Point p2 = readPoint(scanner);
				Point p3 = readPoint(scanner);
				Point p4 = readPoint(scanner);
				shape = new Plane(p1, p2, p3, p4, 
						new Color((int)(scanner.nextDouble()*255), (int)(scanner.nextDouble()*255), (int)(scanner.nextDouble()*255)) );
				shape.setAspect(aspects.get(finishNum));
				shapes.add(shape);
			}else if("triangle".equals(name)){
				shape = new Triangle(readPoint(scanner), readPoint(scanner), readPoint(scanner), 
						new Color((int)(scanner.nextDouble()*255), (int)(scanner.nextDouble()*255), (int)(scanner.nextDouble()*255)));
				shape.setAspect(aspects.get(finishNum));
				shapes.add(shape);
			}else if("obj".equals(name)){
				//Read the first triangle
				int numVertex = scanner.nextInt();
				for(int ii=0; ii<numVertex; ii++){
					Point p = readPoint(scanner);
					vertex.add(p);
				}
				
				int numTriangles = scanner.nextInt();
				int j;
				for(int ii=0; ii<numTriangles; ii++){
					j = scanner.nextInt();
					Point p1 = vertex.get(j-1);
					j = scanner.nextInt();
					Point p2 = vertex.get(j-1);
					j = scanner.nextInt();
					Point p3 = vertex.get(j-1);
					shape = new Triangle(p1, p2, p3, 
							new Color(255, 0, 0));
					shape.setAspect(new Aspect(1, 0, 0, 0, 0, 0, 0));
					shapes.add(shape);
				}
			}else {
				System.err.println("OBJETO DESCONOCIDO");
			}

			
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
				if(c==580 && r==580){
					System.out.println("Voy por la mitad");
				}
				image.setRGB(c, r, getPixelColor(c, r).getRGB());
			}
		}
		ImageIO.write(image, "bmp", outFile);
	}
	
	public Color getPixelColor(int col, int row){
		if(antialiasing){
			Color[] colors = new Color[numRaysAA];
			for(int i=0; i<numRaysAA; i++){
				double randomX = Math.random();
				double randomY = Math.random();
				Ray ray = camera.getRay(col, rows-1-row, randomX, randomY);
				RayHit h = findHit(ray);
				Color color =  render(h, 1);
				colors[i]=color;
			}
			return ColorUtil.average(colors);
		}else{
			Ray ray = camera.getRay(col,rows-1-row);
			RayHit h = findHit(ray);
			return render(h, 1);
		}
		
		
	}
		
	public Color render(RayHit h, int depth){
		Color response = null;
		if(h != null) { // Se ha producido una colision con algun objeto
			Color color = Color.BLACK;
			// Ambient light
			Light light = lights.get(0);
			if(light != null && h.getShape().getAspect().amb > 0) {
				color = ColorUtil.blend(color, 
						ColorUtil.intensify(h.getShape().getColor(h.getPoint()), light.getColor(h.getPoint())));
			}
			
			Vector r = null; //Reflection Ray
			
			for(int i = 1;i < lights.size();i++) {
				light = lights.get(i);
				Point lightLocation = new Point(light.getX(), light.getY(), light.getZ());
				Vector l = new Vector(h.getPoint(), lightLocation); // Vector beetween hit point and light location
				
				//Ray lightRay = new Ray(h.getPoint().plusVector(l.times(1e-3)), l); //Se le suma epsilon para no chocar consigo mismo
				Ray lightRay = new Ray(h.getPoint(), l);
				l = l.normalize();
				//lightRay.setT(l.magnitude());
				RayHit obstruction = findShadow(lightRay, h.getShape());
				if(obstruction == null) {
					// Difuse light
					double scalarProduct = h.getNormal().normalize().dot(l);
					
					if(scalarProduct<0){
						if(h.getShape().getType().equals("sphere")){
							scalarProduct = 0;
						}else{
							scalarProduct = h.getNormal().negate().normalize().dot(l);		
						}
					}

					//kd*N�L
					scalarProduct = scalarProduct*(h.getShape().getAspect().diff); 
					int pepe = h.getShape().getColor().getBlue();
					Color difuseColor = new Color((int)(scalarProduct*h.getShape().getColor().getRed()), 
							(int)(scalarProduct*h.getShape().getColor().getGreen()), 
							(int)(scalarProduct*h.getShape().getColor().getBlue()));
					color = ColorUtil.blend(color, difuseColor);
	
					
					// Specular light = ks*Iincidente*cos(R*V)^n
					r = l.minusVector(h.getNormal().normalize().times(2.0*l.dot(h.getNormal().normalize())));
					double specularLight = h.getShape().getAspect().spec * 
							(float)Math.pow(Math.max(0.0, h.getRay().getDirection().dot(r)), h.getShape().getAspect().shiny);
					if(specularLight<0){
						specularLight=0;
					}
					if(specularLight>1){
						specularLight = 1;
					}
					
	
					Color SpecularColor = new Color ((int)(specularLight*light.getColor().getRed()), 
							(int)(specularLight*light.getColor().getGreen()), 
							(int)(specularLight*light.getColor().getBlue()));
					
					color = ColorUtil.blend(color, SpecularColor);
				}else{
					//System.out.println("Se produce sombra");
					//color = ColorUtil.intensify(color, obstruction.getShape().getAspect().trans);
					// Difuse light
					double scalarProduct = h.getNormal().normalize().dot(l);
					
					if(scalarProduct<0){
						if(h.getShape().getType().equals("sphere")){
							scalarProduct = 0;
						}else{
							scalarProduct = h.getNormal().negate().normalize().dot(l);		
						}
					}

					//kd*N�L
					scalarProduct = scalarProduct*(h.getShape().getAspect().diff*obstruction.getShape().getAspect().trans); 
					Color difuseColor = new Color((int)(scalarProduct*h.getShape().getColor().getRed()), 
							(int)(scalarProduct*h.getShape().getColor().getGreen()), 
							(int)(scalarProduct*h.getShape().getColor().getBlue()));
					color = ColorUtil.blend(color, difuseColor);
				}	
					
			}
			
			//Reflection and Reflexion recursion
			if(depth <= MAX_RECURSION_LEVEL) {
				if(r!=null && h.getShape().getAspect().isReflective()) {
					Ray rayRefl = new Ray(h.getPoint(), r.normalize());
					RayHit reflex = findHit(rayRefl);
					color = ColorUtil.blend(color, ColorUtil.intensify(render(reflex, depth+1), h.getShape().getAspect().refl));
				}

				if(h.getShape().getAspect().isTransmittive()) {
					Vector v = h.getRay().getDirection().negate();
					Vector n = h.getNormal();
					double cosi = v.dot(n);
					double nint;
					//if(incoming) nint = 1.0 / shape.finish.ior;
					//else nint = shape.finish.ior;
					nint = h.getShape().getAspect().ior;
					nint = 1; //TODO: Quizas haya que cambiar esto
					double cost = Math.sqrt(1.0 - nint*nint * (1 - cosi*cosi));

					Ray rayRefract = new Ray(h.getPoint(), n.times(nint * cosi - cost).minusVector(v.times(nint)));
					RayHit refract = findHit(rayRefract);
					color = ColorUtil.blend(color, ColorUtil.intensify(render(refract, depth+1), h.getShape().getAspect().trans));
				}
			}
			
			response = color;
		}else{
			response = BACKGROUND_COLOR;
		}
		return response;
	}
	
	
	private RayHit findHit(Ray ray) {
		RayHit hit = null;
		for(int i=0; i<shapes.size(); i++){
		//for(Obj3D shape: shapes) {
			
			RayHit h = shapes.get(i).intersect(ray);
			if(h != null && h.getT() < ray.getT()) {
				hit = h;
				ray.setT(h.getT());
				if(h.getT() < 0){
//					System.out.println(h);
				}
			}
		}
		
		return hit;
	}
	
	private RayHit findShadow(Ray ray, Obj3D obj) {
		RayHit hit = null;
		for(int i=0; i<shapes.size(); i++){
		//for(Obj3D shape: shapes) {
			if(ray.getOrigin().getX() > 0){
				@SuppressWarnings("unused")
				int asdasds =1;
			};
//			Obj3D aux = shapes.get(i);
			if(!obj.equals(shapes.get(i))){
				//System.out.println(obj.toString() + " != " + shapes.get(i).toString());
				RayHit h = shapes.get(i).intersect(ray);
				if(h != null && h.getT() > 0 /*&& h.getT() < ray.getT()*/) {
//					System.out.println("Choque con " + h.getShape().toString());
					hit = h;
					ray.setT(h.getT());
					if(h.getT() < 0){
						System.out.println(h.getT());
					}
					//System.out.println(i);
				}
			}else{
				//System.out.println(obj.toString() + " == " + shapes.get(i).toString());
			}
		}
		
		return hit;
	}
	
}
