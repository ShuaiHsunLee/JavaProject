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
		for (int i=0; i<str.length(); i++)
		{
			char ch = str.charAt(i);
			if (!tmp.existChild(ch))
				return false;
			// Upper case and lower case are same
			if (65<=ch && ch<=90)
				ch += 32;
			tmp = tmp.nextChild(tmp, ch);
			if (tmp.getChildren() == null)
				return false;
		}
		return tmp.getStatus();
	}
}

class node
{
	private char key;
	private boolean status = false;
	private LinkedList<node> children = null;
	public node() {}
	public node(char ch)
	{
		key = ch;
	}
	protected char getKey()
	{
		return key;
	}
	protected boolean getStatus()
	{
		return status;
	}
	protected LinkedList<node> getChildren()
	{
		return children;
	}
	protected void modifyStatus()
	{
		status = !status;
	}
	protected char upperCase(char ch)
	{
		if (97<=ch && ch<=122)
			ch -= 32;
		return ch;
	}
	protected boolean existChild(char ch)
	{
		// upper case and lower case are same
		for (int i=0; i<children.size(); i++)
			if (children.get(i).key == ch || upperCase(children.get(i).key) == ch)
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
		for (int i=0; i<obj.children.size(); i++)
			if (obj.children.get(i).key == ch)
				return obj.children.get(i);
		return null;
	}
}