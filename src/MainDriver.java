import java.io.*;
import java.nio.file.Files;

import javax.swing.JFrame;

public class MainDriver {

	public static void main(String[] args) {


		String fileName = "info.txt";
		File file;
		
		JFrame window = new JFrame();
		window.setTitle("InfoBox");
		window.setSize(600, 400);
		window.setLocation(660, 340);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Deletes file instead of running code if true for test purposes
		boolean flag = false;
		if(flag)
		{
			new File(fileName).delete();
			
		}else{
		
			FileReader fileReader = null;
			file = new File(fileName);
			
			
				try {
					if(!file.exists())
						file.createNewFile();
					fileReader = new FileReader(fileName);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			

		
			String line = null;
		
			try {
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
				
				InfoBox ib = new InfoBox(window, out);
				window.add(ib);
				
				window.setVisible(true);
			
				while((line = bufferedReader.readLine()) != null)
					System.out.println(line);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
