import java.io.*;

import javax.swing.JFrame;
/**
 * 
 * @author Jimmy
 *
 */
public class MainDriver {

	public static void main(String[] args) {

		String fileName = "info.txt";
		SmartFile file = null;
		JFrame window = new JFrame();
		
		window.setTitle("InfoBox");
		window.setSize(600, 400);
		window.setLocation(660, 340);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			File f = new File(fileName);
			f.createNewFile();
			file = new SmartFile(fileName);
			file.createNewFile();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			InfoBox ib = new InfoBox(window, file);
			window.add(ib);

			window.setVisible(true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
