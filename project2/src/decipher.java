package MyPackage;
import java.util.LinkedList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class decipher extends cipher
{
	static final dictionary dict = new dictionary();
	private static boolean alreadyExecuted = false;
	private LinkedList<String> lst = new LinkedList<String>();
	private HashMap<String, Integer> hmap = new HashMap<String, Integer>();

	public decipher () {}

	private static void checkDictionaryInit()
	{
		if (!alreadyExecuted)
		{
			dictionaryInit();
			alreadyExecuted = true;
		}
	}

	private static void dictionaryInit()
	{
		/* put words into dictionary */
		try (BufferedReader br = new BufferedReader(new FileReader("../dictionary/vocabulary"))) 
		{
		    String line;
		    while ((line = br.readLine()) != null)
		    	dict.insertWord(line);
		} catch(IOException ioe){}
	}

	public void cipherCorrection(String str, int i)
	{
		int counter = 0;
		String tmp = sentenceShift(str, i);
		String[] words = tmp.split(" ");
		for (String word : words)
			if (dict.isWord(word))
				counter++;
		hmap.put(tmp, counter);
	}

	public LinkedList<String> findBest(String str)
	{
		checkDictionaryInit();
		for (int i=0; i<26; i++)
			cipherCorrection(str, i);
		// find the smallest value
		int counter = 0;
		for ( String key : hmap.keySet() )
			if (hmap.get(key) > counter)
				counter = hmap.get(key);
		for ( String key : hmap.keySet() )
			if (hmap.get(key) == counter)
				lst.add(key);
		return lst;
	}
}