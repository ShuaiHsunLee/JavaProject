package MyPackage;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class project
{
	public static void main(String[] args)
	{
		String str = "i am victor";
		int shift = 2;
		/* cipher */
		// Scanner input = new Scanner(System.in);
		// String str = input.nextLine();
		// int shift = input.nextInt();
		cipher passwd = new cipher();
		passwd.makeCipher(str, shift);
		System.out.println("Input               : " + str);
		System.out.println("Cipher (shift by 2) : " + passwd.getValue());

		/* put words into dictionary */
		dictionary dict = new dictionary();
		try (BufferedReader br = new BufferedReader(new FileReader("../dictionary/vocabulary"))) 
		{
		    String line;
		    while ((line = br.readLine()) != null)
		    	dict.insertWord(line);
		} catch(IOException ioe){}

		/* decipher */
		String tmp = passwd.getValue();
		cipher unknown = new cipher();
		String ans = "";
		boolean flag;
		for (int i=0; i<26; i++)
		{
			flag = true;
			unknown.makeCipher(tmp, i);
			String[] words = unknown.getValue().split(" ");
			for (int j=0; j<words.length; j++)
				if (!dict.isWord(words[j]))
				{
					flag = false;
					break;
				}
			if (flag)
			{
				for (int j=0; j<words.length; j++)
					ans += words[j] + " ";
				break;
			}
		}
		System.out.println("Decipher 	    : " + ans);
	}
}