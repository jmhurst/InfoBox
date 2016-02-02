import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.*;


@SuppressWarnings("serial")
public class InfoBox extends JPanel {

	OutputStream out;

  public InfoBox(JFrame window, OutputStream os) {
	  setBounds(0, 0, window.getWidth(), window.getHeight());
	  createInterface();
	  out = os;
	  
/*	  
	  try {
		out.write("hey".getBytes());
		out.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
*/		
		
  }

  public void createInterface()
  {
	  JTextField textField = new JTextField(20);
	  textField.addActionListener(new ActionListener(){
		  
		  public void actionPerformed(ActionEvent e) 
			{
			  try {
				out.write(e.getActionCommand().getBytes());
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			}
		  
	  }
			  );
	  this.add(textField);
  }
  public void actionPerformed(ActionEvent event)
  {
	  System.out.println(event.getActionCommand());
  }
 
}