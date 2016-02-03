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
		FileReader fileReader = null;
		SmartFile file = null;
		JFrame window = new JFrame();
		
		window.setTitle("InfoBox");
		window.setSize(600, 400);
		window.setLocation(660, 340);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		


		try {
			file = new SmartFile(fileName);
			file.createNewFile();
			fileReader = new FileReader(fileName);

		} catch (IOException e1) {
			e1.printStackTrace();
		}



		String line = null;

		try {
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			InfoBox ib = new InfoBox(window, file);
			window.add(ib);

			window.setVisible(true);

			while((line = bufferedReader.readLine()) != null)
				System.out.println(line);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
