import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import utils.RayTracer;


public class Main {

	public static void main(String[] args) {

		File input = new File("./scene.txt");
		File output = new File("./ouput.bmp");
		RayTracer rt = new RayTracer();
		try {
			rt.readScene(input);
			rt.draw(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
