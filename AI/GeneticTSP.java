// C:\Program Files\Java\jdk1.8.0_151\bin
import java.util.*;
import java.lang.*;
class Chromosome
{
	int order[];
	int fitness;
	Chromosome(int order[],int fitness)
	{
		this.order = order;
		this.fitness = fitness;
	}
}
class GeneticTSP
{
	static final int populationSize = 4,generationSize = 3;
	static int numNodes;
	static int graph[][];
	public static Vector <Chromosome> population = new Vector<Chromosome>(populationSize);
	public static void main(String[] args) 
	{
		int i,j,src,dest,cost;
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
		for(i=1;i<=numNodes;i++)
		{
			for(j=i+1;j<=numNodes;j++)
			{
				System.out.println("Enter cost between cities "+i+" and "+j);
				cost = sc.nextInt();
				graph[i][j] = cost;	
				graph[j][i] = cost;
			}
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
			for(j=0;j<=numNodes;j++)
				System.out.print(p1.order[j]+"\t");
			System.out.println("\nParent p2 is");
			for(j=0;j<=numNodes;j++)
				System.out.print(p2.order[j]+"\t");
			System.out.println("\nParent p3 is");
			for(j=0;j<=numNodes;j++)
				System.out.print(p3.order[j]+"\t");
			System.out.println("\nParent p4 is");
			for(j=0;j<=numNodes;j++)
				System.out.print(p4.order[j]+"\t");
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
		ArrayList<Integer> t = new ArrayList<Integer>(numNodes);
		for(i=1;i<=numNodes;i++)
			t.add(i);
		int blah[];
		while(population.size()<populationSize)
		{
			blah = new int[numNodes+1];
			Collections.shuffle(t);
			for(i=0;i<numNodes;i++)
				blah[i] = t.get(i);
			blah[numNodes] = blah[0];
			if(!inPop(blah))
				population.add(new Chromosome(blah,-1));
		}

	}
	public static boolean inPop(int arr[])
	{
		for(Chromosome c:population)
    	{
      		if(Arrays.equals(arr,c.order))
        		return true;
    	}
    	return false;
	}
	public static void printPopulation()
	{
		System.out.println("\n");
	    for(Chromosome c: population)
	    {
	      for(int j=0;j<=numNodes;j++)
	        System.out.print((c.order)[j]+"\t");
	      System.out.println(" Fitness = "+c.fitness+"\n");
	    }
	}
	public static void getFitness()
	{
		int j,sum,col,k;
		for(Chromosome c:population)
		{
			sum = 0;
			for(j=0;j<numNodes;j++)
    		{
				int s = c.order[j];
				int d = c.order[j+1];
				sum += graph[s][d];
    		}
    		c.fitness = sum;
		}
	}
	public static void crossover(Chromosome p1, Chromosome p2,int n)
	{
		Random r = new Random();
    	int i,j,k,copt = r.nextInt(numNodes);
    	int temp1[]=new int[numNodes+1];
    	int temp2[]=new int[numNodes+1];
    	System.out.println("\nCrossover point for parent "+(n+1)+" and "+(n+2)+" is "+(copt+1));
    	for(i=0;i<=copt;i++)
    	{
    		temp1[i] = p1.order[i];
    		temp2[i] = p2.order[i];
    	}
    	k = i;
    	for(j=0;j<=numNodes;j++)
		{
			if(!present(temp1,p2.order[j]))
			{
				temp1[i] = p2.order[j];
				i++;
			}	
			if(!present(temp2,p1.order[j]))
			{
				temp2[k] = p1.order[j];
				k++;
			}
		}
	    temp1[numNodes] = temp1[0];
	    temp2[numNodes] = temp2[0];
	    //mutate 
	    System.out.println("\nFirst child is");
	    for(j=0;j<=numNodes;j++)
	      System.out.print(temp1[j]+"\t");
	    System.out.println("\nSecond child is");
	    for(j=0;j<=numNodes;j++)
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
			int mbit1 = r.nextInt(numNodes-1)+1;
			int mbit2 = r.nextInt(numNodes-1)+1;
			if(mbit1==mbit2)
			{
				System.out.println("\nNo mutation for child "+n);
				return temp;
			}
			System.out.println("\nMutate bits "+(mbit1+1)+" and "+(mbit2+1)+" for child "+n);
			temp[mbit1] = temp[mbit1] + temp[mbit2];
			temp[mbit2] = temp[mbit1] - temp[mbit2];
			temp[mbit1] = temp[mbit1] - temp[mbit2];
			System.out.println("\nAfter mutation:");
	    	System.out.println("\nChild "+n+" is");
	    	for(int j=0;j<=numNodes;j++)
	      		System.out.print(temp[j]+"\t");
		}
		else
			System.out.println("\nNo mutation for child "+n);
		return temp;
	}

	public static boolean present(int arr[],int val)
	{
		for(int i=0;i<arr.length;i++)
			if(arr[i]==val)
				return true;
		return false;
	}
}	