// C:\Program Files\Java\jdk1.8.0_151\bin
import java.util.*;
import java.lang.*;

class Node
{
	int jug1,jug2,pid;
	Vector <String> dir;
	Node(int jug1,int jug2,Vector <String> dir,int pid)
	{
		this.jug1 = jug1;
		this.jug2 = jug2;
		this.dir = dir;
		this.pid = pid; 
	}
}
class WaterJugBFSDFS
{
	public static Vector <Node> ss = new Vector<Node>();
	public static Queue <Node> queue = new ArrayDeque<Node>();
	public static Stack <Node> stack = new Stack<Node>();
	public static int goalj1,goalj2;
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter goal configuraton separated by space");
		goalj1 = sc.nextInt();
		goalj2 = sc.nextInt();
		Vector <String> v = new Vector<String>();
		v.add("Initial");
		Node root = new Node(0,0,v,-1);
		queue.add(root);
		ss.add(root);
		createStateSpace();
		printStateSpace();
		System.out.println("\nState space tree generated with number of nodes = "+ss.size());
		queue.clear();
		queue.add(root);
		System.out.println("BFS");
    	bfs();
    	System.out.println("DFS");
    	stack.add(root);
    	dfs();
	}
	public static void createStateSpace()
	{
		boolean empty1,empty2,fill1,fill2,trans12,trans21;
		while(!queue.isEmpty())
		{
			Node parent = queue.remove();
			int parentid = parent.hashCode();
			Node child;
			System.out.println(parent.jug1+"\t"+parent.jug2);
			empty1 = (parent.jug1>0) ? true : false;
			empty2 = (parent.jug2>0) ? true : false;
			fill1 = (parent.jug1<5) ? true : false;
			fill2 = (parent.jug2<3) ? true : false;
			trans12 = (parent.jug1>0 && parent.jug2<3) ? true : false;
			trans21 = (parent.jug1<5 && parent.jug2>0) ? true : false;
			System.out.println(empty1+" "+empty2+" "+fill1+" "+fill2+" "+trans12+" "+trans21+"\n");
			if (empty1)
			{
				Vector <String> temp = new Vector<String>(parent.dir);
				Collections.copy(temp,parent.dir);
				temp.add("Empty Jug 1");
				child = new Node(0,parent.jug2,temp,parentid);
				if(!explored(child))
				{
					ss.add(child);
					queue.add(child);
				}
			}
			if (empty2)
			{
				Vector <String> temp = new Vector<String>(parent.dir);
				Collections.copy(temp,parent.dir);
				temp.add("Empty Jug 2");
				child = new Node(parent.jug1,0,temp,parentid);
				if(!explored(child))
				{
					ss.add(child);
					queue.add(child);
				}
			}
			if (fill1)
			{
				Vector <String> temp = new Vector<String>(parent.dir);
				Collections.copy(temp,parent.dir);
				temp.add("Fill Jug 1");
				child = new Node(5,parent.jug2,temp,parentid);
				if(!explored(child))
				{
					ss.add(child);
					queue.add(child);
				}
			}
			if (fill2)
			{
				Vector <String> temp = new Vector<String>(parent.dir);
				Collections.copy(temp,parent.dir);
				temp.add("Fill Jug 2");
				child = new Node(parent.jug1,3,temp,parentid);
				if(!explored(child))
				{
					ss.add(child);
					queue.add(child);
				}
			}
			if (trans12)
			{
				Vector <String> temp = new Vector<String>(parent.dir);
				Collections.copy(temp,parent.dir);
				temp.add("Transfer 1 to 2");
				int diff = Math.min(3-parent.jug2,parent.jug1);
				child = new Node(parent.jug1-diff,parent.jug2+diff,temp,parentid);
				if(!explored(child))
				{
					ss.add(child);
					queue.add(child);
				}
			}
			if (trans21)
			{
				Vector <String> temp = new Vector<String>(parent.dir);
				Collections.copy(temp,parent.dir);
				temp.add("Transfer 2 to 1");
				int diff = Math.min(5-parent.jug1,parent.jug2);
				child = new Node(parent.jug1+diff,parent.jug2-diff,temp,parentid);
				if(!explored(child))
				{
					ss.add(child);
					queue.add(child);
				}
			}
		}
	}

	public static boolean explored(Node child)
	{
		for(Node n:ss)
		{
			if(n.jug1 == child.jug1 && n.jug2==child.jug2)
				return true;
		}
		return false;
	}
	public static void printStateSpace()
	{
		for(Node n:ss)
		{
			System.out.println("Jug1: "+n.jug1+" Jug2: "+n.jug2+" Depth: "+(n.dir.size())+" Last Action: "+n.dir.lastElement()+"\n");
		}
	}
	public static void bfs()
	{
		while(!queue.isEmpty())
		{
			Node parent = queue.remove();
			System.out.println("\nCurrent Node: ");
			System.out.println("Jug 1: "+parent.jug1+" Jug 2: "+parent.jug2);
			if (parent.jug1==goalj1 && parent.jug2==goalj2)
			{
				System.out.println("Goal Found");
				System.out.println("Depth of goal node is "+parent.dir.size());
				System.out.println("Actions: ");
				for(String a:parent.dir)
				{
					System.out.print(a+" -> ");
				}
				return;
			}
			for(Node x:ss)
			{
				if (x.pid==parent.hashCode())
				{
					queue.add(x);
					System.out.println("Adding "+x.jug1+" "+x.jug2+" by action "+x.dir.lastElement());
				}
			}
		}
	}
	public static void dfs()
	{
		while(!stack.isEmpty())
		{
			Node parent = stack.pop();
			System.out.println("\nCurrent Node: ");
			System.out.println("Jug 1: "+parent.jug1+" Jug 2: "+parent.jug2);
			if (parent.jug1==goalj1 && parent.jug2==goalj2)
			{
				System.out.println("Goal Found");
				System.out.println("Depth of goal node is "+parent.dir.size());
				System.out.println("Actions: ");
				for(String a:parent.dir)
				{
					System.out.print(a+" -> ");
				}
				return;
			}
			for(Node x:ss)
			{
				if (x.pid==parent.hashCode())
				{
					stack.push(x);
					System.out.println("Adding "+x.jug1+" "+x.jug2+" by action "+x.dir.lastElement());
				}
			}
		}
	} 
}