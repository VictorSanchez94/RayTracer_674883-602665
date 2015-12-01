package mathElements;

public class AffineTransform {
	private static Point worldCenter = new Point(0,0,0);

	public static Point translate(double x, double y, double z){
		Matrix translateMatrix = new Matrix(new double [][]{
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{x, y, z, 1}
		});
		Vector worldC = new Vector(worldCenter); 
		Vector responseV = translateMatrix.timesMio(worldC);
		Point responsePoint = new Point(responseV.getX(), responseV.getY(), responseV.getZ());
		return responsePoint;
	}
	
	public static double changeSize(double sizeX, double sizeY, double sizeZ) {
		Matrix changeSizeMatrix = new Matrix(new double [][]{
				{sizeX, 0, 0, 0},
				{0, sizeY, 0, 0},
				{0, 0, sizeZ, 0},
				{0, 0, 0, 1}
		});
		Vector worldC = new Vector(1,1,1); //The original shape has size 1 in the three axis 
		Vector responseV = changeSizeMatrix.timesMio(worldC);
		Point responsePoint = new Point(responseV.getX(), responseV.getY(), responseV.getZ());
		return responsePoint.getX(); //Takes the first component
	}
	
	/** axis x = 1
	 * axis y = 2
	 * axis z = 3
	 * angle is the angle that the shape rotates
	 */
	public static Point rotate(int axis, double angle, Point p) {
		Point responsePoint = null;
		if(axis == 1){ //x axis
			Matrix rotateXMatrix = new Matrix(new double [][]{
					{1, 0, 0, 0},
					{0, Math.cos(angle), Math.sin(angle), 0},
					{0, -Math.sin(angle), Math.cos(angle), 0},
					{0, 0, 0, 1}
			});
			Vector pVector = new Vector (p.getX(), p.getY(), p.getZ());
			Vector responseV = rotateXMatrix.timesMio(pVector);
			responsePoint = new Point(responseV.getX(), responseV.getY(), responseV.getZ());
		}else if(axis == 2){ //y axis
			Matrix rotateYMatrix = new Matrix(new double [][]{
					{Math.cos(angle), 0, -Math.sin(angle), 0},
					{0, 1, 0, 0},
					{Math.sin(angle), 0, Math.cos(angle), 0},
					{0, 0, 0, 1}
			});
			Vector pVector = new Vector (p.getX(), p.getY(), p.getZ());
			Vector responseV = rotateYMatrix.timesMio(pVector);
			responsePoint = new Point(responseV.getX(), responseV.getY(), responseV.getZ());
		}else if(axis == 3){ //z axis
			Matrix rotateZMatrix = new Matrix(new double [][]{
					{Math.cos(angle), Math.sin(angle), 0, 0},
					{-Math.sin(angle), Math.cos(angle), 0, 0},
					{0, 0, 1, 0},
					{0, 0, 0, 1}
			});
			Vector pVector = new Vector (p.getX(), p.getY(), p.getZ());
			Vector responseV = rotateZMatrix.timesMio(pVector);
			responsePoint = new Point(responseV.getX(), responseV.getY(), responseV.getZ());
		}
		return responsePoint;
	}
}
