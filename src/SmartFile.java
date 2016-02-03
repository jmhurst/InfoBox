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
				System.out.println("printed tags");
				printWriter.print(start+end);
				printWriter.flush();
			}else{
				line = start+end+line;
				//clears current file and writes updated line with title tags
				printWriter = new PrintWriter(this);
				printWriter.print("");
				printWriter.close();
				fileWriter = new FileWriter(this, true);
				printWriter = new PrintWriter(fileWriter);
				printWriter.print(line);
				printWriter.flush();
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
		//clears current file and writes updated line
		printWriter = new PrintWriter(this);
		printWriter.print("");
		printWriter.close();
		fileWriter = new FileWriter(this, true);
		printWriter = new PrintWriter(fileWriter);
		printWriter.print(line);
		printWriter.flush();
	}
	
	public void addInfo(String info) throws IOException
	{
		if(!info.equals("")){
			line = reader.readLine();
			reader = new BufferedReader(new FileReader(this));
			String start = "<info>";
			String end = "</info>";
			if(line != null)
				line = line + start + info + end;
			else
				line = start + info + end;

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

	
	public String[] getTopicList() throws IOException
	{
		line = reader.readLine();
		reader = new BufferedReader(new FileReader(this));
		String topic;
		String[] list = new String[100];
		String start = "<topic>";
		String end = "</topic>";
		
		int count = 0;
		while(line != null && line.contains(start) && line.contains(end))
		{
			topic = line.substring(line.indexOf(start) + start.length(), line.indexOf(end));
			line = line.substring(line.indexOf(end) + end.length());
			list[count] = topic;
			count++;
		}

		return list;
	}
	
	public void addTopic(String topic) throws IOException
	{
		if(!topic.equals("")){
			line = reader.readLine();
			reader = new BufferedReader(new FileReader(this));
			String start = "<topic>";
			String end = "</topic>";
			if(line != null)
				line = line + start + topic + end;
			else
				line = start + topic + end;
			
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
}
