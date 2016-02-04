import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @author Jimmy
 *
 */
@SuppressWarnings("serial")
public class SmartFile extends File {

	BufferedReader reader;
	String line;
	FileWriter fileWriter;
	PrintWriter printWriter;
	
	public SmartFile(String pathname) throws IOException {
		super(pathname);
		reader = new BufferedReader(new FileReader(this));
		fileWriter = new FileWriter(this, true);
		printWriter = new PrintWriter(fileWriter);
	}
	
	/**
	 * Returns title of given text document while placing empty title tags in new files
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getTitle() throws IOException
	{
		String start = "<title>";
		String end = "</title>";
		String resultStr = "";
		int start_index; 
		int end_index;
		line = reader.readLine();
		reader = new BufferedReader(new FileReader(this));
		
		if(line!= null && line.contains(start) && line.contains(end))
		{
		start_index = line.indexOf(start)+ start.length();
		end_index = line.indexOf(end);
		resultStr = line.substring(start_index , end_index);
		}else{
			if(line == null)
			{
				printWriter.print(start+end);
				printWriter.flush();
			}else{
				line = start+end+line;

				printToFile();
			}
		}
		return resultStr;
		
	}
	/**
	 * Sets the title value in the text document to given string
	 * 
	 * @param t
	 * @throws IOException
	 */
	public void setTitle(String t) throws IOException
	{
		String start = "<title>";
		String end = "</title>";
		int end_index;
		//Writes tags to text file if needed
		getTitle();
		
		line = reader.readLine();
		reader = new BufferedReader(new FileReader(this));
		end_index = line.indexOf(end);
		
		line = start + t + line.substring(end_index);
		printToFile();
	}
	
	public void addInfo(String info) throws IOException
	{
		if(!info.equals("")){
			line = reader.readLine();
			reader = new BufferedReader(new FileReader(this));
			String start = "<info>";
			String end = "</info>";
			String topicStart = "<topic>Misc.";
			
			if(line != null)
				line = line + start + info + end;
			else
				line = start + info + end;

			if(line.contains(topicStart))
			{
				addInfo(info, "Misc.");
			}else{
				addTopic("Misc.");
				addInfo(info, "Misc.");
			}
		}
	}
	
	public void addInfo(String info, String topic) throws IOException
	{
		if(!info.equals("") && !topic.equals("")){
			String topicStart = "<topic>" + topic;
			String infoStart = "<info>";
			String infoEnd = "</info>";
			String result;
			line = reader.readLine();
			reader = new BufferedReader(new FileReader(this));
			result = line.substring(line.indexOf(topicStart) + topicStart.length());
			line = line.substring(0, line.indexOf(topicStart)) + topicStart + infoStart + info + infoEnd + result;
			
			printToFile();
		}
	}

	
	public String[] getTopicList() throws IOException
	{
		line = reader.readLine();
		reader = new BufferedReader(new FileReader(this));
		String topic = "";
		String[] list = new String[100];
		String start = "<topic>";
		String end = "</topic>";
		String altEnd = "<info>";
		
		int count = 0;
		while(line != null && line.contains(start) && line.contains(end))
		{
			if((line.contains(altEnd) && line.indexOf(end)<line.indexOf(altEnd))){
				topic = line.substring(line.indexOf(start) + start.length(), line.indexOf(end));
				line = line.substring(line.indexOf(end) + end.length());
			}else if((line.contains(altEnd) && line.indexOf(end)>line.indexOf(altEnd))){
				topic = line.substring(line.indexOf(start) + start.length(), line.indexOf(altEnd));
				line = line.substring(line.indexOf(end) + end.length());
			}else{
				topic = line.substring(line.indexOf(start) + start.length(), line.indexOf(end));
				line = line.substring(line.indexOf(end) + end.length());
			}
				
			list[count] = topic;
			count++;
		}

		return list;
	}
	
	public void addTopic(String topic) throws IOException
	{
		line = reader.readLine();
		reader = new BufferedReader(new FileReader(this));
		
		if(line==null || (!topic.equals("") && !line.contains(topic))){
			String start = "<topic>";
			String end = "</topic>";
			if(line != null)
				line = line + start + topic + end;
			else
				line = start + topic + end;
			
			printToFile();
		}
	}

	public String[] getInfoList(String topic) throws IOException
	{
		line = reader.readLine();
		reader = new BufferedReader(new FileReader(this));
		String topicStart = "<topic>" + topic;
		String topicEnd = "</topic>";
		String infoStart = "<info>";
		String infoEnd = "</info";
		String info;
		String[] list = new String[100];
		if(topic != null)
		{
			if(line != null)
			{
				line = line.substring(line.indexOf(topicStart)) + topicStart.length();
				line = line.substring(0, line.indexOf(topicEnd));
			}
			int count = 0;
			while(line != null && line.contains(infoStart) && line.contains(infoEnd))
			{
				info = line.substring(line.indexOf(infoStart) + infoStart.length(), line.indexOf(infoEnd));
				line = line.substring(line.indexOf(infoEnd) + infoEnd.length());
				
				System.out.println("Added: " + info);
				list[count] = info;
				count++;
			}
			System.out.println();
			return list;
		}else{
			list[0] = "";
			return list;
		}
	}
	
	public void deleteTopic(String topic) throws IOException
	{
		line = reader.readLine();
		reader = new BufferedReader(new FileReader(this));
		String topicStart = "<topic>" + topic;
		String topicEnd = "</topic>";
		String holdEnd;
		
		holdEnd = line.substring(line.indexOf(topicStart) + topicStart.length());
		holdEnd = line.substring(line.indexOf(topicEnd) + topicEnd.length());
		line = line.substring(0, line.indexOf(topicStart)) + holdEnd; 
		
		printToFile();
		
	}
	
	public void deleteInfo(String info) throws IOException
	{
		line = reader.readLine();
		reader = new BufferedReader(new FileReader(this));
		info = "<info>"+info+"</info>";
		String holdEnd;

		holdEnd = line.substring(line.indexOf(info)+info.length());
		line = line.substring(0, line.indexOf(info)) + holdEnd; 
		
		printToFile();
	}
	
	public void printToFile() throws IOException
	{
		//clears current file and writes updated line
		printWriter = new PrintWriter(this);
		printWriter.print("");
		printWriter.close();
		fileWriter = new FileWriter(this, true);
		printWriter = new PrintWriter(fileWriter);
		printWriter.print(line);
		printWriter.flush();
	}
}
