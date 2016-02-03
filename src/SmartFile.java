import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
		int start_index;
		int end_index;
		line = reader.readLine();
		reader = new BufferedReader(new FileReader(this));
		CharSequence csq = getTitle();
	
		start_index = line.indexOf(start)+ start.length();
		end_index = line.indexOf(end);
		
		if(csq != "")
		{
			printWriter.append(csq, start_index, end_index);
		}else{
			
		}
	}
	
	public void addInfo(String info) throws IOException
	{
		line = reader.readLine();
		reader = new BufferedReader(new FileReader(this));
		String start = "<info>";
		String end = "</info>";
		if(line != null)
			line = line + start + info + end;
		else
			line = start + info + end;
		printWriter = new PrintWriter(this);
		printWriter.print("");
		printWriter.close();
		fileWriter = new FileWriter(this, true);
		printWriter = new PrintWriter(fileWriter);
		printWriter.print(line);
		printWriter.flush();
	}

}
