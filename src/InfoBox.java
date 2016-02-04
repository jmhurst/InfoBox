import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
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
		final JList<String> infoList = new JList<>();
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
	
		final JList<String> topicList = new JList<>();
		
		
		
		final JTextField topicTextField = new JTextField(20);
		topicTextField.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					file.addTopic(e.getActionCommand());
					upDateTopicList(topicList);
					
				} catch (IOException e1) {
					System.out.println("Info was not successfully added to file.");
				}
				topicTextField.setText("");
				if(file!=null)
				{
					DefaultListModel<String> list = new DefaultListModel<String>();
					try {
						for(String a : file.getInfoList(topicList.getSelectedValue()))
							list.addElement(a);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					infoList.setModel(list);
				}
			}
		});
		
		topicList.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				if(topicList.getSelectedValue() != null)
				{
					topicTextField.setText(topicList.getSelectedValue());
					if(file!=null)
					{
						DefaultListModel<String> list = new DefaultListModel<String>();
						try {
							for(String a : file.getInfoList(topicList.getSelectedValue()))
								list.addElement(a);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						infoList.setModel(list);
					}
				}
			}
		});
		infoTextField.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) 
			{
				try {
					String topicString = topicTextField.getText();
					
					if(topicString.equals(""))
						file.addInfo(e.getActionCommand());
					else
						file.addInfo(e.getActionCommand(), topicString);
				} catch (IOException e1) {
					System.out.println("Info was not successfully added to file.");
				}
				infoTextField.setText("");
				
				if(file!=null)
				{
					DefaultListModel<String> list = new DefaultListModel<String>();
					try {
						upDateTopicList(topicList);
						for(String a : file.getInfoList(topicList.getSelectedValue()))
							list.addElement(a);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					infoList.setModel(list);
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
						upDateTopicList(topicList);
						DefaultListModel<String> list = new DefaultListModel<String>();
						infoList.setModel(list);
						topicTextField.setText("");
						infoTextField.setText("");
						titleTextField.setText("");
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
					file.getTitle();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JButton deleteSelected = new JButton("Delete Selected Item");
		deleteSelected.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(infoList.getSelectedValue() != null && !infoList.isSelectionEmpty()){
					try {
						file.deleteInfo(infoList.getSelectedValue());
						upDateInfoList(infoList, topicList.getSelectedValue());
					} catch (IOException e1) {
						System.err.println("Could not delete topic.");
					}
					
				}else if(topicList.getSelectedValue() != null && !topicList.isSelectionEmpty())
				{
					try {
						file.deleteTopic(topicList.getSelectedValue());
						upDateTopicList(topicList);
						upDateInfoList(infoList, topicList.getSelectedValue());
					} catch (IOException e1) {
						System.err.println("Could not delete topic.");
					}
					topicTextField.setText("");
				}
			}
		});

		JButton printFile = new JButton("Print Whole File");
		printFile.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				File f = null;
				try {
					f = file.copyFile(f);
					Desktop.getDesktop().open(f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JScrollPane infoPane;

		infoPane = new JScrollPane(infoList);
		
		add(titleLabel);
		add(titleTextField);
		add(infoLabel);
		add(infoTextField);
		add(deleteButton);
		add(openFile);
		add(topicLabel);
		add(topicTextField);
		add(topicPane);
		add(infoPane);
		add(deleteSelected);
		add(printFile);
	}
	
	public void upDateTopicList(JList<String> topicList) throws IOException
	{
		if(file != null)
		{
			DefaultListModel<String> list = new DefaultListModel<String>();
			if(file.getTopicList() != null)
				for(String a : file.getTopicList())
					list.addElement(a);
			topicList.setModel(list);
		}
	}
	
	public void upDateInfoList(JList<String> infoList, String topic) throws IOException
	{
		if(file != null)
		{
			DefaultListModel<String> list = new DefaultListModel<String>();
			if(file.getInfoList(topic) != null)
				for(String a : file.getInfoList(topic))
					list.addElement(a);
			infoList.setModel(list);
		}
	}


}