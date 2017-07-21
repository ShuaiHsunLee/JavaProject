package MyPackage;
import java.util.LinkedList;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class project
{
	public static void main(String[] args)
	{
		try
		{
			/* READ */
			File file = new File("./java/text/origin.txt");
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			String input = new String(data, "UTF-8");
			int shift = Integer.parseInt(args[0]);

			cipher sentence = new cipher();
			String output = sentence.sentenceShift(input, shift);
			/* WRITE */
			PrintWriter writer1 = new PrintWriter("./java/text/cipher.txt", "UTF-8");
            writer1.println(output);
            writer1.close();
		}
		catch (IOException e) {}
	}
}