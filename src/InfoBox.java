import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

/**
 * 
 * @author Jimmy
 *
 */
@SuppressWarnings("serial")
public class InfoBox extends JPanel {

	SmartFile file;
	FileWriter fileWriter;
	PrintWriter printWriter;

	public InfoBox(JFrame window, SmartFile f) throws IOException 
	{
		if(f.createNewFile())
			System.out.println("created new file");
		setBounds(0, 0, window.getWidth(), window.getHeight());
		createInterface();
		file = f;
		fileWriter = new FileWriter(file, true);
		printWriter = new PrintWriter(fileWriter);

	}

	public void createInterface()
	{
		JLabel label = new JLabel();
		label.setText("Info:");

		final JTextField textField = new JTextField(20);
		textField.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) 
			{
				try {
					file.addInfo(e.getActionCommand());
				} catch (IOException e1) {
					System.out.println("Info was not successfully added to file.");
				}
//				printWriter.print(e.getActionCommand());
//				printWriter.flush();
				textField.setText("");
			}

		});

		final JButton deleteButton = new JButton("Delete Saved File");
		deleteButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String message = "Would you like to delete your saved file? You will lose all data!";
				if(JOptionPane.showConfirmDialog(deleteButton, message) == 0)
				{
					try {
						fileWriter = new FileWriter(file);
					} catch (IOException e1) {
						System.err.println("Couln't create file");
					}
				}	
			}
		});

		JButton openFile = new JButton("Open Raw File");
		openFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(file);
					System.out.println(file.getTitle());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});

		add(label);
		add(textField);
		add(deleteButton);
		add(openFile);
	}


}