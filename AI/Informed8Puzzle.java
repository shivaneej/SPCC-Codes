import java.util.*;
import java.lang.*;
/*
Initial 1 2 3
        0 4 6 
        7 5 8 
        
Goal    1 2 3
        4 5 6
        7 8 0
*/

class Node
{
  int state[][] = new int[4][4];
  int x,y,f,h,g;
  Vector <String> dir = new Vector<String>();
  Node(int state[][],int x,int y,Vector<String> dir,int g,int h)
  {
    this.state = state;
    this.x = x;
    this.y = y;
    this.dir = dir;
    this.g = g;
    this.h = h;
    this.f = g + h;
    //g = depth
    //h = misplaced tiles
  }
}

class Informed8Puzzle
{
  static int init[][] = new int[4][4];
  static int goal[][] = new int[4][4];
  static PriorityQueue <Node> queue = new PriorityQueue<>(new Comparator<Node>() {
    public int compare(Node n1, Node n2)
    {
      return n1.h - n2.h;
    }
  }); //For GBFS
  /*static PriorityQueue <Node> queue = new PriorityQueue<>(new Comparator<Node>() {
    public int compare(Node n1, Node n2)
    {
      return n1.f - n2.f;
    }
  });*/ //For A* 
  static Vector <int[][]> explored = new Vector<int[][]>();
  public static void main(String args[])
  {
    int i,j,k,x=0,y=0;
    long depth;
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter initial arrangement (0 for blank)");
    for(i=1;i<=3;i++)
      for(j=1;j<=3;j++)
      {
        init[i][j] = sc.nextInt();
        if(init[i][j]==0)
        {
          x = i;
          y = j;
        }
      }
    System.out.println("Enter goal arrangement (0 for blank)");
    for(i=1;i<=3;i++)
      for(j=1;j<=3;j++)
        goal[i][j] = sc.nextInt();
    int h = getH(init);
    Node root = new Node (init,x,y,new Vector<String>(),0,h);    
    queue.add(root);
    Node soln = getSoln();
    if(soln!=null) 
    {
        System.out.println("Goal Node Found");
        System.out.println("Depth of goal node : "+soln.g);
        System.out.println("Directions : ");
        for(String s : soln.dir)
        System.out.print(s+"\t");
    }  
    else
    System.out.println("Cannot reach goal node");  
  }

  public static Node getSoln()
  {
    boolean left,right,up,down;
    while(!queue.isEmpty())
    {
      Node node = queue.remove();
      Node child = null;
      System.out.println("g(n) = "+node.g+"\nh(n) = "+node.h);
      print(node);
      explored.add(node.state);
      if(isGoal(node))
      return node;
      else
      {
        left = (node.y == 1) ? false : true;
        right = (node.y == 3) ? false : true;
        up = (node.x == 1) ? false : true;
        down = (node.x == 3) ? false : true;
        if(left)
        {
          child = slide(node,"left");
          if(!inExplored(child.state))
          {
            queue.add(child);
            System.out.println("Moved Left");
            System.out.println("g(n) = "+child.g);
            System.out.println("h(n) = "+child.h);
            print(child);
          }
        }
        if(right)
        {
          child = slide(node,"right");
          if(!inExplored(child.state))
          {
            queue.add(child);
            System.out.println("Moved Right");
            System.out.println("g(n) = "+child.g);
            System.out.println("h(n) = "+child.h);
            print(child);
          }        
        }
        if(up)
        {
          child = slide(node,"up");
          if(!inExplored(child.state))
          {
            queue.add(child);
            System.out.println("Moved Up");
            System.out.println("g(n) = "+child.g);
            System.out.println("h(n) = "+child.h);
            print(child);
          }
        }
        if(down)
        {
          child = slide(node,"down");
          if(!inExplored(child.state))
          {
            queue.add(child);
            System.out.println("Moved Down");
            System.out.println("g(n) = "+child.g);
            System.out.println("h(n) = "+child.h);
            print(child);
          }
        }
      }
    }
    return null;
  }

  public static boolean isGoal(Node node)
  {
   int i,j;
    for(i=1;i<=3;i++)
      for(j=1;j<=3;j++)
        if(node.state[i][j] != goal[i][j])
          return false;
    
    return true;
  }

  public static Node slide(Node parent,String direction)
  {
    Node child = null;
    int bx,by,h,g,childg,childh;
    bx = parent.x;
    by = parent.y;
    g = parent.g; 
    Vector <String> temp;
    int childstate[][] = new int[4][4];
    for(int x=1;x<=3;x++)
      for(int y=1;y<=3;y++)
        childstate[x][y] = parent.state[x][y];
    switch(direction)
    {
      case "left":
        childstate[bx][by] = childstate[bx][by-1];
        childstate[bx][by-1] = 0;
        temp = new Vector<>(parent.dir);
        Collections.copy(temp,parent.dir);
        temp.add("Left");
        childg = g + 1;
        childh = getH(childstate);
        child = new Node (childstate,bx,by-1,temp,childg,childh);
        break;
      case "right":
        childstate[bx][by] = childstate[bx][by+1];
        childstate[bx][by+1] = 0;
        temp = new Vector<>(parent.dir);
        Collections.copy(temp,parent.dir);
        temp.add("Right");
        childg = g + 1;
        childh = getH(childstate);
        child = new Node (childstate,bx,by+1,temp,childg,childh);
        break;  
      case "up":
        childstate[bx][by] = childstate[bx-1][by];
        childstate[bx-1][by] = 0;
        temp = new Vector<>(parent.dir);
        Collections.copy(temp,parent.dir);
        temp.add("Up");
        childg = g + 1;
        childh = getH(childstate);
        child = new Node (childstate,bx-1,by,temp,childg,childh);
        break;
      case "down":
        childstate[bx][by] = childstate[bx+1][by];
        childstate[bx+1][by] = 0;
        temp = new Vector<>(parent.dir);
        Collections.copy(temp,parent.dir);
        temp.add("Down");
        childg = g + 1;
        childh = getH(childstate);
        child = new Node (childstate,bx+1,by,temp,childg,childh);
        break;
    }
    return child;
  }

  public static void print(Node node)
  {
    for(int i = 1;i <= 3;i++)
    {
      System.out.print("\n");  
      for(int j = 1;j <= 3;j++)
      System.out.print(node.state[i][j]+"\t");
    }  
    System.out.println("\n");
  }

  public static boolean inExplored(int[][] state)
  {
    boolean found = false;
    outer:
    for(int[][] x: explored)
    {
      for(int a=1;a<=3;a++)
      {
        for(int b=1;b<=3;b++)
        {
          found = (state[a][b] == x[a][b]) ;
          if(!found)
          continue outer;
        }
      }
      return true;
    }
    return false;
  }

  public static int getH(int state[][])
  {
    int h = 0;
    for(int i=1;i<=3;i++)
      for(int j=1;j<=3;j++)
        if (state[i][j]!=goal[i][j])
          h++;
    return h;
  }
}