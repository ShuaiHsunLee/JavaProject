package MyPackage;
import java.util.ArrayList;

public class cipher
{
	private ArrayList<String> stringList;
	public String getValue()
	{
		String str = "";
		for (int i=0; i<stringList.size(); i++)
			str += stringList.get(i) + " ";
		return str;
	}
	public char charShift(char ch, int shift)
	{
		int asci = (int)ch;
		if (asci>=97 && asci<=122)
		{
			asci += shift;
			if (asci > 122)
				asci -= 26; // asci = 96 + (asci - 122)
		}
		return (char)asci;
	}
	public void makeCipher(String str, int shift)
	{
		String[] words = str.split(" ");

		ArrayList<String> lst = new ArrayList<String>();
		for (int i=0; i<words.length; i++)
			lst.add(words[i]);

		for (int i=0; i<lst.size(); i++)
			for (int j=0; j<lst.get(i).length(); j++)
			{
				String tmp;
				char ch = lst.get(i).charAt(j);
				tmp = lst.get(i).substring(0,j) + charShift(ch, shift);
				if (j+1 < words[i].length())
					tmp += lst.get(i).substring(j+1);
				lst.set(i, tmp);
			}
		stringList = lst;
	}
}