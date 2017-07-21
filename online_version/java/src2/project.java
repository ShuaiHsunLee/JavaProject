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
			File file = new File("./java/text/cipher.txt");
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			String input = new String(data, "UTF-8");

			/* decipher */
			decipher unknown = new decipher();
			LinkedList<String> result = unknown.findBest(input);
			PrintWriter writer2 = new PrintWriter("./java/text/decipher.txt", "UTF-8");
            writer2.println(result);
            writer2.close();
		}
		catch (IOException e) {}
	}
}