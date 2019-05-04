import java.util.*;
import java.lang.*;
class Node
{
	String city;
	Vector <String> path;
	int cost,h,f;
	Node(String city, Vector <String> path,int cost,int h)
	{
		this.city = city;
		this.path = path;
		this.cost = cost; //cost is g
		this.h = h;
		this.f = this.h + this.cost;
	}
}
class SLine
{
	String src;
	int dist;
	SLine(String src, int dist)
	{
		this.src = src;
		this.dist = dist;
	}
}
class AMapExp
{
	public static int[][] map;
	public static int num;
	public static Vector <String> explored = new Vector<String>(); 
	public static Vector <String> cities = new Vector<String>();
	public static PriorityQueue <Node> pqueue = new PriorityQueue<Node>(
		new Comparator<Node>(){public int compare(Node n1,Node n2){return n1.f - n2.f;}}); 
	public static String src,dest;
	public static Vector <SLine> sline = new Vector<SLine>();
	public static void main(String[] args) 
	{
		int i,dist,route,j,sldist=0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of cities");
		num = sc.nextInt();
		System.out.println("Enter name of cities");
		map = new int[num][num];
		for(i=0;i<num;i++)
		{
			cities.add(sc.next());
			for(j=0;j<num;j++)
			{
				map[i][j]=(i==j)?0:-1;
			}
		}
		System.out.println("Enter number of routes");
		route = sc.nextInt();
		for(i=0;i<route;i++)
		{
			System.out.println("Enter source, destination and cost");
			src = sc.next();
			dest = sc.next();
			dist = sc.nextInt();
			try
			{
				map[cities.indexOf(src)][cities.indexOf(dest)] = dist;	
				map[cities.indexOf(dest)][cities.indexOf(src)] = dist;
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("Invalid");
				i--;
			}
		}
		for(i=0;i<num;i++) //print map
		{
			for(j=0;j<num;j++)
			{
				System.out.print(map[i][j]+"\t");
			}
			System.out.println("\n");
		}
		System.out.println("Enter source node");
		src = sc.next();
		System.out.println("Enter destination node");
		dest = sc.next();
		for(i=0;i<num;i++)
		{
			if(map[cities.indexOf(dest)][i]==-1)
			{
				String x = cities.get(i);
				System.out.println("Enter straight line distance from "+x+" to the destination");
				int y = sc.nextInt();
				sline.add(new SLine(x,y));
				if(x.equals(src))
					sldist = y;
			}
		}
		Vector <String> t = new Vector<String>();
		t.add(src);
		pqueue.add(new Node(src,t,0,sldist));
		search(src,dest);
	}
	public static void search(String src, String dest)
	{
		int i,j;
		while(!pqueue.isEmpty())
		{
			Node current = pqueue.remove();
			explored.add(current.city);
			System.out.println("current node "+current.city);
			if(current.city.equals(dest))
			{
				System.out.println("Path Found");
				for(String p:current.path)
					System.out.print(p+" - ");
				System.out.println("Total Cost is "+current.cost);
				return;
			}
			for(i=0;i<num;i++)
			{
				if(map[cities.indexOf(current.city)][i]>0 && !explored.contains(cities.get(i)))
				{
					String newcity = cities.get(i) ;
					int cost = current.cost + map[cities.indexOf(current.city)][i];
					Vector <String> newpath = new Vector<String>(current.path);
					Collections.copy(newpath,current.path);
					newpath.add(newcity);
					for(String p:newpath)
						System.out.print(p+" - ");
					int h = getSLineDist(newcity);
					// int h = getSLineDist(newpath.firstElement());
					pqueue.add(new Node(newcity,newpath,cost,h));
					System.out.println("Adding "+newcity);
					System.out.println("g(n) = "+cost+" h(n) = "+h+" f(n) = "+(cost+h)+" depth = "+newpath.size());
				}
			}
		}
	}
	public static int getSLineDist(String city)
	{
		for(SLine s:sline)
		{
			if(s.src.equals(city))
				return s.dist;
		}
		return map[cities.indexOf(city)][cities.indexOf(dest)];
	}
}