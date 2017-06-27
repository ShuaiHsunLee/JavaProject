package MyPackage;
import java.util.LinkedList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class decipher extends cipher
{
	static final dictionary dict = new dictionary();;
	static private boolean alreadyExecuted = false;
	private LinkedList<String> lst = null;
	private HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	public decipher () {}
	static private void checkDictionaryInit()
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
	public void compareWords(String str, int i)
	{
		int counter = 0;
		String tmp = sentenceShift(str, i);
		String[] words = tmp.split(" ");
		for (int j=0; j<words.length; j++)
			if (dict.isWord(words[j]))
				counter++;
		hmap.put(tmp, counter);
	}
	public LinkedList<String> findBest(String str)
	{
		lst = new LinkedList<String>();
		checkDictionaryInit();
		for (int i=0; i<26; i++)
			compareWords(str, i);
		int counter = 0;
		// find the smallest value
		for ( String key : hmap.keySet() )
			if (hmap.get(key) > counter)
				counter = hmap.get(key);
		for ( String key : hmap.keySet() )
			if (hmap.get(key) == counter)
				lst.add(key);
		return lst;
	}
}