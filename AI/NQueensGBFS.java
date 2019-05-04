import java.util.*;
import java.lang.*;

class Node
{
	int h,pid,depth;
	int state[][];
	int pos[];
	Node(int state[][],int pos[],int h,int pid,int depth)
	{
		this.state = state;
		this.pos = pos;
		this.h = h;
		this.pid = pid;
    this.depth = depth;
	}
}
class NQueensGBFS
{
	static int board[][] = new int[5][5];
	static int pos[] = new int[5];
	static Vector <Node> ss = new Vector <>();
	static PriorityQueue <Node> queue = new PriorityQueue <>(
		new Comparator<Node>(){public int compare(Node n1, Node n2){return n1.h - n2.h;}});
	static Queue <Node> ssq = new ArrayDeque <>();

	public static void main(String[] args) 
	{
		Node root = new Node(board,pos,0,-1,0);
		ss.add(root);
		ssq.add(root);
		createStateSpace();
		System.out.println("\nState space tree generated with number of nodes = "+ss.size());
		queue.add(root);
		gbfs();	
	}
	public static void createStateSpace()
	{
		int depth,queen,y,j,k,pid,childh;
		int childstate[][];
		int childpos[];
		while(!ssq.isEmpty())
		{
			Node parent = ssq.remove();
			depth = parent.depth+1;
			queen = depth;
      if(depth>4)
        return;
			for(y=1;y<=4;y++)
			{
				childstate = new int[5][5];
				childpos = new int[5];
				for(j=1;j<=4;j++)
				{
					childpos[j] = parent.pos[j];
					for(k=1;k<=4;k++)
						childstate[j][k] = parent.state[j][k];
				}
        childstate[queen][y] = queen;  
        childpos[queen] = y;
        childh = getH(childpos,queen);
        pid = parent.hashCode();
        Node child = new Node(childstate,childpos,childh,pid,depth);
        ss.add(child);
        ssq.add(child);
			}
		}
	} 
	public static int getH(int pos[],int row)
	{
		int j,count=0,col,k;
    int a[]={0,2,4,1,3}; 
		for(j=1;j<=row;j++)
    {
      col = pos[j];
      for(k=1;k<=row;k++)
      {
        if((pos[k]==col || (Math.abs(pos[k]-col)==Math.abs(k-j))) && k!=j)
        count++;
      }
    }
		return count;
	}
	public static void gbfs()
	{
		while(!queue.isEmpty())
		{
			Node current = queue.remove();
			print(current.state);
			System.out.println("h(n) = "+current.h);
			if(isGoal(current))
			{
				System.out.println("Goal Found");
				System.exit(-1);
			}
			for(Node x:ss)
			{
				if(x.pid==current.hashCode())
				{
					queue.add(x);
				}
			}
		}
	}
	public static void print(int state[][])
	{
		System.out.print("\n");
  	for (int i=1;i<=4;i++)
  	{
    		for(int j=1;j<=4;j++)
      		System.out.print(state[i][j]+"\t");
    		System.out.print("\n");
  	}
	}
	public static boolean isGoal(Node node)
	{
		int goal1[] = {0,2,4,1,3};
  	int goal2[] = {0,3,1,4,2};
  	if (Arrays.equals(node.pos, goal1) || Arrays.equals(node.pos, goal2))
    		return true;
  	return false;
	}
}