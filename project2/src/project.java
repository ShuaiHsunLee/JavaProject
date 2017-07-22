package MyPackage;
import java.util.LinkedList;
import java.util.Scanner;

public class project
{
	public static void main(String[] args)
	{
		/* cipher */
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		int shift = sc.nextInt();
		cipher sentence = new cipher();
		String output = sentence.sentenceShift(input, shift);
		System.out.println("Input   : " + input);
		System.out.println("Cipher  : " + output);

		/* decipher */
		decipher unknown = new decipher();
		LinkedList<String> result = unknown.findBest(output);
		System.out.println("Original sentence: " + result);
	}
}