import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	GridBagConstraints c;
	GridBagLayout layout;

	public InfoBox(JFrame window, SmartFile f) throws IOException 
	{
		setBounds(0, 0, window.getWidth(), window.getHeight());
		file = f;
		fileWriter = new FileWriter(file, true);
		printWriter = new PrintWriter(fileWriter);
		reader = new BufferedReader(new FileReader(file));
		layout = new GridBagLayout();
		this.setLayout(layout);
		c = new GridBagConstraints();
		createInterface();
	}

	public void createInterface() throws IOException
	{
		//title, topic, and info lists
		final JTextField titleTextField = new JTextField(20);
		final JTextField topicTextField = new JTextField(20);
		final JTextField infoTextField = new JTextField(20);
		//lists to be used in panes later
		final JList<String> infoList = new JList<>();
		final JList<String> topicList = new JList<>();
			
		//panes for info and topic lists
		JScrollPane infoPane = new JScrollPane(infoList);
		infoPane.setPreferredSize(new Dimension(300,200));
		JScrollPane topicPane;
		//labels for title, topic, and info text fields
		JLabel titleLabel = new JLabel("Title:");
		JLabel topicLabel = new JLabel("Topics:");
		JLabel infoLabel = new JLabel("Info:");
		//various buttons
		final JButton deleteButton = new JButton("Delete Saved File");
		JButton openFile = new JButton("Open Raw File");
		JButton deleteSelected = new JButton("Delete Selected Item");
		JButton printFile = new JButton("Print Whole File");
		
		//sets up the topic pane with given the topic list
		upDateTopicList(topicList);
		topicPane = new JScrollPane(topicList);
		topicPane.setPreferredSize(new Dimension(300,200));
		
		
		JPanel headingPane = new JPanel();
		headingPane.setBackground(Color.BLUE);
		//headingPane.setSize(new Dimension(600, 100));
		JPanel leftPane = new JPanel();
		leftPane.setBackground(Color.YELLOW);
		JPanel rightPane = new JPanel();
		rightPane.setBackground(Color.RED);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight =1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		this.add(headingPane, c);
		
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight =1;
		c.gridwidth = 1;
		this.add(leftPane, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight =1;
		c.gridwidth = 1;
		this.add(rightPane, c);
		
		/*
		 * Action listeners for all the buttons and scroll panes used in the UI
		 */
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
				int count = 0;
				while((topicList.getModel()).getElementAt(count) != null)
					count ++;
					
				topicList.setSelectedIndex(count-1);
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
				int topicIndex = topicList.getSelectedIndex();
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
					try {
						upDateTopicList(topicList);
						upDateInfoList(infoList, topicList.getSelectedValue());
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
				topicList.setSelectedIndex(topicIndex);
			}
		});
		
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
		
//		//adding all the given elements to the jPanel
//		add(titleLabel);
//		add(titleTextField);
//		add(infoLabel);
//		add(infoTextField);
//		add(deleteButton);
//		add(openFile);
//		add(topicLabel);
//		add(topicTextField);
//		add(topicPane);
//		add(infoPane);
//		add(deleteSelected);
//		add(printFile);
	}
	
	/**
	 * Updates the topic list to have a fresh version displayed
	 * 
	 * @param topicList
	 * @throws IOException
	 */
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
	
	/**
	 * Updates the info list to have a fresh version displayed
	 * 
	 * @param infoList
	 * @param topic
	 * @throws IOException
	 */
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