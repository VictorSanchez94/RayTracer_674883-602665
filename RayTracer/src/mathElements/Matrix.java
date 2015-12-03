package mathElements;

public class Matrix {
	private double m[][];

	public Matrix(double m[][]) {
		if(m.length < 4 || m[0].length < 4) throw new IllegalArgumentException("Matrix must be a 4x4 array");
		this.m = m;
	}
	
	public Matrix times(Matrix matrix) {
		double m2[][] = matrix.m;
		double r[][] = new double[4][4];

		for(int row=0;row<4;row++) {
			for(int col=0;col<4;col++) {
				r[row][col] = m[row][0] * m2[0][col] + m[row][1] * m2[1][col] + m[row][2] * m2[2][col] + m[row][3] * m2[3][col];
			}
		}

		return new Matrix(r);
	}
	
	public Vector times(Vector v) {
		double x, y, z;

		x = m[0][0] * v.getX() + m[0][1] * v.getY() + m[0][2] * v.getZ() + m[0][3] * 1.0;
		y = m[1][0] * v.getX() + m[1][1] * v.getY() + m[1][2] * v.getZ() + m[1][3] * 1.0;
		z = m[2][0] * v.getX() + m[2][1] * v.getY() + m[2][2] * v.getZ() + m[2][3] * 1.0;

		// fourth coordinate
		double mag = m[3][0] * v.getX() + m[3][1] * v.getY() + m[3][2] * v.getZ() + m[3][3] * 1.0;

		x /= mag;
		y /= mag;
		z /= mag;

		return new Vector(x, y, z);
	}
	
	public Vector timesMio(Vector v) {
		double x, y, z;
		//System.out.println(m[0][0]+" "+m[0][1]+" "+m[0][3]);
		x = m[0][0] * v.getX() + m[1][0] * v.getY() + m[2][0] * v.getZ() + m[3][0] * 1.0;
		y = m[0][1] * v.getX() + m[1][1] * v.getY() + m[2][1] * v.getZ() + m[3][1] * 1.0;
		z = m[0][2] * v.getX() + m[1][2] * v.getY() + m[2][2] * v.getZ() + m[3][2] * 1.0;
		return new Vector(x, y, z);
	}

}