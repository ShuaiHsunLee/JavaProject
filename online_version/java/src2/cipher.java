package MyPackage;

public class cipher
{
	private char[] chArray;

	public cipher() {}

	public char charShift(char ch, int shift)
	{
		shift %= 26;
		if (shift < 0)
			shift += 26;
		int asci = (int)ch;
		if (65<=asci && asci<=90)
		{
			asci += shift;
			if (asci > 90) 
				asci -= 26; // asci = 64 + (asci - 90)
		}
		else if (97<=asci && asci<=122)
		{
			asci += shift;
			if (asci >122)
				asci -= 26; // asci = 96 + (asci - 122)
		}
		return (char)asci;
	}

	public String sentenceShift(String str, int shift)
	{
		chArray = str.toCharArray();
		for (int i=0; i<chArray.length; i++)
			chArray[i] = charShift(chArray[i], shift);
		return String.valueOf(chArray);
	}
}