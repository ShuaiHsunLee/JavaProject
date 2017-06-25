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
			tmp = tmp.whichChild(tmp, ch);
		}
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
			tmp = tmp.whichChild(tmp, ch);
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
	protected boolean existChild(char ch)
	{
		for (int i=0; i<children.size(); i++)
			if (children.get(i).key == ch)
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
	protected node whichChild(node obj, char ch)
	{
		for (int i=0; i<obj.children.size(); i++)
			if (obj.children.get(i).key == ch)
				return obj.children.get(i);
		return null;
	}
}