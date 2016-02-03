import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Jimmy
 *
 */
@SuppressWarnings("serial")
public class SmartFile extends File {

	BufferedReader reader;
	String line;
	
	public SmartFile(String pathname) throws FileNotFoundException {
		super(pathname);
		reader = new BufferedReader(new FileReader(this));
	}
	
	public String getTitle() throws IOException
	{
		String start = "<title>";
		String end = "</title>";
		String resultStr;
		int start_index; 
		int end_index;
		line = reader.readLine();
	
		start_index = line.indexOf(start);
		end_index = line.indexOf(end);
		resultStr = line.substring(start_index + start.length(), end_index);
		if(resultStr != "")
		{
			return resultStr;
		}else{
			return "";
		}
	}
	
	public void setTitle(String t)
	{
		
	}

}
