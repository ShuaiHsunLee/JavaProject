package MyPackage;
import java.util.LinkedList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class project1
{
	
	public static void main(String[] args)
	{
		gpa obj = new gpa();
		obj.calculate();
	}	
}

class gpa
{
	private HashMap<String, Double> people = new HashMap<String, Double>();
	private HashMap<String, Double> standard = new HashMap<String, Double>();
	public gpa()
	{
		standard.put("A", 4.0);
		standard.put("A-", 3.7);
		standard.put("B+", 3.3);
		standard.put("B", 3.0);
		standard.put("B-", 2.7);
		standard.put("C+", 2.3);
		standard.put("C", 2.0);
		standard.put("C-", 1.7);
		standard.put("D+", 1.3);
		standard.put("D", 1.0);
		standard.put("D-", 0.7);
	}
	private String parse(String input) { return input.split(":")[1].split(";")[0]; }

	public void calculate()
	{
		try
		{
			/* READ */
			BufferedReader br = new BufferedReader(new FileReader("uploads/Grades_in.txt"));
		    String line;
		    String name = "";
		    double score = 0.0;
		    int counter = 0;
		    while ((line = br.readLine()) != null)
		    {
		    	if (line.charAt(0) == '-')
		    	{
		    		people.put(name, score/(counter-1));
		    		counter = 0;
		    		score = 0.0;
		    		continue;
		    	}
		    	if (counter == 0)
		    	{
		    		name = line;
		    		counter++;
		    		continue;
		    	}
		    	score += standard.get(parse(line));
		    	counter++;
		    }
		    people.put(name, score/(counter-1));
		    
		    DecimalFormat df = new DecimalFormat();
		    df.setMaximumFractionDigits(2);

			/* WRITE */
			PrintWriter writer1 = new PrintWriter("uploads/Grades_out.txt", "UTF-8");
			for (String key : people.keySet())
				writer1.write(key + "\'s GPA:" + df.format(people.get(key)) + '\n');
			writer1.close();
		}
		catch (IOException e) {}
	}
}