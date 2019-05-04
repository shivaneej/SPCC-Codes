import java.util.*;
import java.lang.*;

class Chromosome
{
	int pos[];
	int fitness;
	Chromosome(int pos[],int fitness)
	{
		this.pos = pos;
		this.fitness = fitness;
	}

}
class GeneticGraphColoring
{
	static final int populationSize = 4,generationSize = 3,numColors = 3;
	static int numNodes;
	static int graph[][];
	public static Vector <Chromosome> population = new Vector<Chromosome>(populationSize);
	public static void main(String[] args) 
	{
		int i,j,route,src,dest;
		//take input
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of nodes");
		numNodes = sc.nextInt();
		graph = new int[numNodes+1][numNodes+1];
		for(i=1;i<=numNodes;i++)
		{
			for(j=1;j<=numNodes;j++)
			{
				graph[i][j]=(i==j)?0:-1;
			}
		}
		System.out.println("Enter number of edges");
		route = sc.nextInt();
		for(i=0;i<route;i++)
		{
			System.out.println("Enter source, destination");
			src = sc.nextInt();
			dest = sc.nextInt();
			graph[src][dest] = 1;	
			graph[dest][src] = 1;
		}
		for(i=1;i<=numNodes;i++) //print map
		{
			for(j=1;j<=numNodes;j++)
			{
				System.out.print(graph[i][j]+"\t");
			}
			System.out.println("\n");
		}
		initializePopulation();
		getFitness();
		System.out.println("Initial Population:\n");
    	printPopulation();
    	Chromosome p1,p2,p3,p4;
    	for(i=0;i<generationSize;i++)
    	{
    		population.sort(
			new Comparator<Chromosome>(){public int compare(Chromosome c1,Chromosome c2){return c1.fitness - c2.fitness;}});
			p1 = population.get(0);
			p2 = population.get(1);
			p3 = population.get(2);
			p4 = population.get(3);
			System.out.println("\nGeneration: "+(i+1));
			System.out.println("\nParent p1 is");
			for(j=1;j<=numNodes;j++)
				System.out.print(p1.pos[j]+"\t");
			System.out.println("\nParent p2 is");
			for(j=1;j<=numNodes;j++)
				System.out.print(p2.pos[j]+"\t");
			System.out.println("\nParent p3 is");
			for(j=1;j<=numNodes;j++)
				System.out.print(p3.pos[j]+"\t");
			System.out.println("\nParent p4 is");
			for(j=1;j<=numNodes;j++)
				System.out.print(p4.pos[j]+"\t");
			System.out.println("\n");
			crossover(p1,p2,0);
			crossover(p3,p4,2);
			getFitness();
			printPopulation();
			Chromosome fittest = Collections.min(population,new Comparator<Chromosome>(){
				public int compare(Chromosome c1, Chromosome c2){
					return c1.fitness-c2.fitness;
				}
			});
			System.out.println("Fittest Chromosome with fitness "+fittest.fitness);

    	}
	}
	public static void initializePopulation()
	{
		int i,r;
		Random rx = new Random();
		int blah[];
		while(population.size()<populationSize)
		{
			blah = new int[numNodes+1];
			for(i=1;i<=numNodes;i++)
				blah[i] = rx.nextInt(3);
			if(!inPop(blah))
				population.add(new Chromosome(blah,-1));
		}
	}
	public static boolean inPop(int arr[])
	{
		for(Chromosome c:population)
    	{
      		if(Arrays.equals(arr,c.pos))
        		return true;
    	}
    	return false;
	}
	public static void printPopulation()
	{
		System.out.println("\n");
	    for(Chromosome c: population)
	    {
	      for(int j=1;j<=numNodes;j++)
	        System.out.print((c.pos)[j]+"\t");
	      System.out.println(" Fitness = "+c.fitness+"\n");
	    }
	}
	public static void getFitness()
	{
		int j,count=0,col,k;
		for(Chromosome c:population)
		{
			count = 0;
			for(j=1;j<=numNodes;j++)
    		{
				for(k=1;k<=numNodes;k++)
				{
					if(graph[j][k]==1 && c.pos[j]==c.pos[k])
        			count++;
      			}
    		}
    		c.fitness = count/2;
		}
	}
	public static void crossover(Chromosome p1, Chromosome p2,int n)
	{
		Random r = new Random();
    	int i,copt = r.nextInt(numNodes);
    	int temp1[]=new int[numNodes+1];
    	int temp2[]=new int[numNodes+1];
    	System.out.println("\nCrossover point for parent "+(n+1)+" and "+(n+2)+" is "+copt);
	    for(i=1;i<=copt;i++)
	    {
	      temp1[i] = p1.pos[i];
	      temp2[i] = p2.pos[i];
	    }  
	    for(i=copt+1;i<=numNodes;i++)
	    {
	      temp1[i] = p2.pos[i];
	      temp2[i] = p1.pos[i];
	    }
	    //mutate 
	    System.out.println("\nFirst child is");
	    for(int j=1;j<=numNodes;j++)
	      System.out.print(temp1[j]+"\t");
	    System.out.println("\nSecond child is");
	    for(int j=1;j<=numNodes;j++)
	      System.out.print(temp2[j]+"\t");
	  	temp1=mutate(temp1,1);
	  	temp2=mutate(temp2,2);
	    population.set(n,new Chromosome(temp1, -1));
	    population.set(n+1,new Chromosome(temp2, -1));
	}
	public static int[] mutate(int temp[],int n)
	{
		Random r = new Random();
		int mp = r.nextInt(2);
		if(mp==1)
		{
			int mbit = r.nextInt(numNodes);
			System.out.println("\nMutate bit "+mbit+" for child "+n);
			temp[mbit] = r.nextInt(3);
			System.out.println("\nAfter mutation:");
	    	System.out.println("\nChild "+n+" is");
	    	for(int j=1;j<=numNodes;j++)
	      		System.out.print(temp[j]+"\t");
		}
		else
			System.out.println("\nNo mutation for child "+n);
		return temp;
	}
}