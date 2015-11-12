package utils;

public class Camera {
	private Point eye;
	private Vector vx;
	private Vector vy;
	private Vector vz;

	private double displayScreenDistance;
	private double displayScreenWidth;
	private double displayScreenHeight;

	private double rows, cols;

	public Camera(Point eye, Point center, Vector up, int cols, int rows, 
			int displayScreenHeight, int displayScreenWidth) {

		Vector at = new Vector(eye, center);
		vz = at.negate().normalize();
		vx = up.cross(vz).normalize();
		vy = vz.cross(vx);

		this.eye = eye;
		this.cols = cols;
		this.rows = rows;

		displayScreenDistance = 1.0;
		this.displayScreenHeight = displayScreenHeight;
		this.displayScreenWidth = displayScreenWidth;
	}

	/*public Ray getRay(int col, int row) {
		return getRay(col, row, 0.5, 0.5);
	}
	public Ray getRay(int col, int row, double pixelAdjustmentX, double pixelAdjustmentY) {
		double x = (((double)col + pixelAdjustmentX) / cols) * displayScreenWidth - (displayScreenWidth / 2.0);
		double y = (((double)row + pixelAdjustmentY) / rows) * displayScreenHeight - (displayScreenHeight / 2.0);

		Vector v = new Vector(eye, convertCoords(new Point(x, y, -displayScreenDistance)));

		Ray ray = new Ray(eye, v);

		return ray;
	}
*/
	/** NO SE SI VA A HACER FALTA */
	/*public Point convertCoords(Point p) {
		Vector v = convertCoords(new Vector(p.x, p.y, p.z));
		return new Point(v.x, v.y, v.z);
	}*/

	/** NO SE SI VA A HACER FALTA */
	/*public Vector convertCoords(Vector p) {
		Matrix rT = new Matrix(new double[][]{
				{vx.x, vy.x, vz.x, 0},
				{vx.y, vy.y, vz.y, 0},
				{vx.z, vy.z, vz.z, 0},
				{0, 0, 0, 1}
		});
		Matrix tInv = new Matrix(new double[][]{
				{1, 0, 0, eye.x},
				{0, 1, 0, eye.y},
				{0, 0, 1, eye.z},
				{0, 0, 0, 1}
		});
		Matrix matrix = tInv.times(rT);
		Vector v = matrix.times(new Vector(p.x, p.y, p.z));
		return v;
	}*/
}