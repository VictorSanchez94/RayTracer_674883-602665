package utils;

public class Camera {
	private Point eye;
	private Vector u;
	private Vector v;
	private Vector w;

	private double displayScreenDistance;
	private double displayScreenWidth;
	private double displayScreenHeight;

	private double rows, cols;

	public Camera(Point eye, Point center, Vector up, int cols, int rows, 
			int displayScreenHeight, int displayScreenWidth) {

		Vector g = new Vector(eye, center); //Por comodidad va a mirar al centro del mundo (0,0,0)
		w = g.negate().normalize();
		u = w.cross(up).normalize();
		v = w.cross(u).normalize();

		this.eye = eye;
		this.cols = cols;
		this.rows = rows;

		displayScreenDistance = 150; //TODO: AQUI ESTA EL TRUCO PARA HACERLAS MAS GRANDES
		this.displayScreenHeight = displayScreenHeight;
		this.displayScreenWidth = displayScreenWidth;
	}

	public Ray getRay(int col, int row) {
		return getRay(col, row, 0.5, 0.5); //Se tira el rayo por el centro del pixel
	}
	public Ray getRay(int col, int row, double pixelAdjustmentX, double pixelAdjustmentY) {
		double x = (((double)col + pixelAdjustmentX) / cols) * displayScreenWidth - (displayScreenWidth / 2.0);
		double y = (((double)row + pixelAdjustmentY) / rows) * displayScreenHeight - (displayScreenHeight / 2.0);
		//double x = col;
		//double y = row;
		if(Math.abs(x-0.5)<0.3 && Math.abs(y-0.5)<0.3){
			System.out.println(x+" "+y);
		}
		Vector v = new Vector(eye, convertCoords(new Point(x, y, -displayScreenDistance)));

		Ray ray = new Ray(eye, v);

		return ray;
	}

	public Point convertCoords(Point p) {
		Vector v = convertCoords(new Vector(p.getX(), p.getY(), p.getZ()));
		return new Point(v.getX(), v.getY(), v.getZ());
	}

	public Vector convertCoords(Vector p) {
		Matrix rT = new Matrix(new double[][]{
				{u.getX(), v.getX(), w.getX(), 0},
				{u.getY(), v.getY(), w.getY(), 0},
				{u.getZ(), v.getZ(), w.getZ(), 0},
				{0, 0, 0, 1}
		});
		Matrix tInv = new Matrix(new double[][]{
				{1, 0, 0, eye.getX()},
				{0, 1, 0, eye.getY()},
				{0, 0, 1, eye.getZ()},
				{0, 0, 0, 1}
		});
		Matrix matrix = tInv.times(rT);
		Vector v = matrix.times(new Vector(p.getX(), p.getY(), p.getZ()));
		return v;
	}
}