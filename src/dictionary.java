package MyPackage;
import java.util.LinkedList;

public class dictionary extends node
{
	private node root;

	dictionary() { root = new node(); }

	public void insertWord(String str)
	{
		node tmp = root;
		for (int i=0; i<str.length(); i++)
		{
			char ch = str.charAt(i);
			tmp.addChild(ch);
			tmp = tmp.nextChild(tmp, ch);
		}
		// avoid problem of that same word insert more than once
		if (tmp.getStatus() == false)
			tmp.modifyStatus();
	}

	public boolean isWord(String str)
	{
		node tmp = root;
		char ch;
		for (int i=0; i<str.length(); i++)
		{
			ch = str.charAt(i);
			// Turn Upper case into lower case 
			if (65<=ch && ch<=90)
				ch += 32;
			if (!tmp.existChild(ch))
				return false;
			tmp = tmp.nextChild(tmp, ch);
			if (tmp.getChildren() == null)
				return false;
		}
		return tmp.getStatus();
	}
}

// efficient usage of memory
class node
{
	private boolean[] key;
	private boolean status = false;
	private LinkedList<node> children = null;

	public node() {}
	public node(char ch)
	{
		key = new boolean[5];
		// convert alphabet to 5 bit
		for (int i=0; i<5; i++)
			key[i] = ((((int)ch - 97) >> i) & 1) == 1 ? true : false;
	}

	public boolean[] chToBool(char ch)
	{
		boolean[] bit = new boolean[5]; 
		for (int i=0; i<5; i++)
			bit[i] = ((((int)ch - 97) >> i) & 1) == 1 ? true : false;
		return bit;
	}

	protected boolean[] getKey() { return key; }

	protected boolean getStatus() { return status; }

	protected LinkedList<node> getChildren() { return children; }

	protected void modifyStatus() { status = !status; }

	private boolean isEqual(boolean[] bit1, boolean[] bit2)
	{
		for (int i=0; i<bit1.length; i++)
			if (bit1[i] != bit2[i])
				return false;
		return true;
	}

	protected boolean existChild(char ch)
	{
		boolean[] bits = chToBool(ch);
		// upper case and lower case are same
		for (node n : children)
			if (isEqual(n.key, bits))
				return true;
		return false;
	}

	protected void addChild(char ch)
	{
		if (children == null)
		{
			children = new LinkedList<node>();
			children.add(new node(ch));
		}
		else
			if (!existChild(ch))
				children.add(new node(ch));
	}

	protected node nextChild(node obj, char ch)
	{
		boolean[] bits = chToBool(ch);
		for (node n : obj.children)
			if (isEqual(n.key, bits))
				return n;
		return null;
	}
}

// class node
// {
// 	private char key;
// 	private boolean status = false;
// 	private LinkedList<node> children = null;

// 	public node() {}

// 	public node(char ch) { key = ch; }

// 	protected char getKey() { return key; }

// 	protected boolean getStatus() { return status; }

// 	protected LinkedList<node> getChildren() { return children; }

// 	protected void modifyStatus() { status = !status; }

// 	protected boolean existChild(char ch)
// 	{
// 		for (node n : children)
// 			if (n.key == ch)
// 				return true;
// 		return false;
// 	}

// 	protected void addChild(char ch)
// 	{
// 		if (children == null)
// 		{
// 			children = new LinkedList<node>();
// 			children.add(new node(ch));
// 		}
// 		else
// 			if (!existChild(ch))
// 				children.add(new node(ch));
// 	}

// 	protected node nextChild(node obj, char ch)
// 	{
// 		for (node n : obj.children)
// 			if (n.key == ch)
// 				return n;
// 		return null;
// 	}
// }