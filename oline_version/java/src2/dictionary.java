package MyPackage;
import java.util.LinkedList;

public class dictionary extends node
{
	private node root = new node();

	public dictionary() {}

	public void insertWord(String str)
	{
		node tmp = root;
		for (char ch : str.toCharArray())
		{
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
		for (char ch : str.toCharArray())
		{
			// turn upper case into lower case 
			if (65<=ch && ch<=90)
				ch += 32;
			// check if every char of a word in dictionary
			// if there is no child in node, no need to check
			if (tmp.getChildren() == null)
				return false;
			if (!tmp.existTheChild(ch))
				return false;
			tmp = tmp.nextChild(tmp, ch);
		}
		return tmp.getStatus();
	}
}

// efficiently saving memory usage
class node
{
	private boolean[] key = new boolean[5];
	private boolean status = false;
	private LinkedList<node> children = null;

	public node() {}
	public node(char ch)
	{
		// convert alphabet to 5 bits
		for (int i=0; i<5; i++)
			key[i] = ((((int)ch - 97) >> i) & 1) == 1 ? true : false;
	}

	public boolean[] chToBool(char ch)
	{
		boolean[] bits = new boolean[5]; 
		for (int i=0; i<5; i++)
			bits[i] = ((((int)ch - 97) >> i) & 1) == 1 ? true : false;
		return bits;
	}

	protected boolean getStatus() { return status; }

	protected LinkedList<node> getChildren() { return children; }

	protected void modifyStatus() { status = !status; }

	private boolean isEqual(boolean[] bits1, boolean[] bits2)
	{
		for (int i=0; i<bits1.length; i++)
			if (bits1[i] != bits2[i])
				return false;
		return true;
	}

	protected boolean existTheChild(char ch)
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
		// if no any child, create children Linked List
		if (children == null)
			children = new LinkedList<node>();
		// if no the child in the Linked List, add it
		if (!existTheChild(ch))
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

// 	protected boolean getStatus() { return status; }

// 	protected LinkedList<node> getChildren() { return children; }

// 	protected void modifyStatus() { status = !status; }

// 	protected boolean existTheChild(char ch)
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
// 			if (!existTheChild(ch))
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