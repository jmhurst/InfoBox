import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	BufferedReader reader;

	public InfoBox(JFrame window, SmartFile f) throws IOException 
	{
		setBounds(0, 0, window.getWidth(), window.getHeight());
		file = f;
		fileWriter = new FileWriter(file, true);
		printWriter = new PrintWriter(fileWriter);
		reader = new BufferedReader(new FileReader(file));
		createInterface();
	}

	public void createInterface() throws IOException
	{
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Title:");
		
		final JTextField titleTextField = new JTextField(20);
		titleTextField.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) 
			{
				try {
					file.setTitle(e.getActionCommand());
				} catch (IOException e1) {
					System.out.println("Info was not successfully added to file.");
				}
				titleTextField.setText("");
			}

		});
		
		JLabel infoLabel = new JLabel();
		infoLabel.setText("Info:");

		final JTextField infoTextField = new JTextField(20);
		infoTextField.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) 
			{
				try {
					file.addInfo(e.getActionCommand());
				} catch (IOException e1) {
					System.out.println("Info was not successfully added to file.");
				}
				infoTextField.setText("");
			}

		});
		final JList<String> topicList = new JList<>();
		
		
		
		final JTextField topicTextField = new JTextField(20);
		topicTextField.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					file.addTopic(e.getActionCommand());
					upDateList(topicList);
					
				} catch (IOException e1) {
					System.out.println("Info was not successfully added to file.");
				}
				topicTextField.setText("");
			}
		});
		
		topicList.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				if(topicList.getSelectedValue() != null)
				{
					topicTextField.setText(topicList.getSelectedValue());
				}
			}
		});
		
		JLabel topicLabel = new JLabel("Topics:");
		JScrollPane topicPane;
		if(file!=null)
		{
			DefaultListModel<String> list = new DefaultListModel<String>();
			for(String a : file.getTopicList())
				list.addElement(a);
			topicList.setModel(list);
		}
		topicPane = new JScrollPane(topicList);
		topicPane.setPreferredSize(new Dimension(300,200));
		
		
		final JButton deleteButton = new JButton("Delete Saved File");
		deleteButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String message = "Would you like to delete your saved file? You will lose all data!";
				if(JOptionPane.showConfirmDialog(deleteButton, message) == 0)
				{
					try {
						fileWriter = new FileWriter(file);
						upDateList(topicList);
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

		add(titleLabel);
		add(titleTextField);
		add(infoLabel);
		add(infoTextField);
		add(deleteButton);
		add(openFile);
		add(topicLabel);
		add(topicTextField);
		add(topicPane);
	}
	
	public void upDateList(JList<String> topicList) throws IOException
	{
		if(file != null)
		{
			DefaultListModel<String> list = new DefaultListModel<String>();
			if(file.getTopicList() != null)
				for(String a : file.getTopicList())
					list.addElement(a);
			System.out.println("this far");
				topicList.setModel(list);
		}
	}


}