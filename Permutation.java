
/*
 * Classname : topo2
 */

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
/**
 *  
         This class implement the algorithm 
         "A STRUCIURED PROGRAM TO GENERATE ALL TOPOLOGICAL SORTING ARRANGEMENTS " by Donald 2. KNUTH  and Jayme L. SZWARCFITER
         
 *
 * @ImplemntationDetails
 * 		
 * 		
 *
 * @version      
         1.0, 20 Sep 2014  
 * @author          
        Manuj Singh 
 */
@SuppressWarnings("rawtypes")
public class Permutation {
	
	/**  
	 * nodes represent an array which corresponds to the numbers of the input a1,a2,a3........an. It stores elements of type Node
	 */
	private Node[] nodes;
	
	/**  
	 *	outputQueue is an output restricted queue. this is used as an auxiliary data structure to store the number of a given permutation.
	 *  this queue holds all the nodes whose count is zero in the nodes array
	 */
	
	private Deque outputQueue;
	
	/** 
	 *	N is the total no of the numbers. e.g if N is 5 , we have number from 1,2,3..5
	 */
	private int N ;
	
	/** 
	 *	count is the total no. of permutations
	 */
	
	int count =0;
	
	int verbose;
	
	/**
	 *  output represents the array which contains the permutation to be printed on the console
	 */
	int[] output;
	
	/**
	 * Constructor used to initialize the class
	 * @param n , total no of elements
	 */
	public Permutation(int n)
	{
		// if no is n we are initializing the array to be n+1, because the array is 0 based and we want a total correspondence between
		// index and the no. being stored in that index, so a[1] will represent 1 , a[2] will represent 2 and so on
		this.nodes = new Node[n+1];
		this.output = new int[n+1];
		
		/*
		 * the array nodes store elements of type Node, so create one node for each number.
		 */
		for(int i = 1;i <= n ;i++)
		{
			nodes[i] = new Node();
		}
		
		this.outputQueue = new LinkedList();
		this.N = n;
	}
	
	/**
	 * this method ass a precedence rule. for all constraint pairs
	 * This method will increment the count field of each node who is a successor in the constraint
	 * e.g if the constraint is (2 precedes 3). count of node[3] will increase by 1
	 * Also it will add the successor node to the predecessor's list.
	 * e.g in the above case node 3 is added to node 2's successor list
	 *
	 * @param predecesor 		the element who precedes in the relation
	 * 
	 * @param succesor			the element who is a successor in the relation
	 * 
	 */
	public void addPrecedence(int predecesor , int succesor)
	{
		nodes[succesor].setCount(nodes[succesor].getCount() + 1);
		nodes[predecesor].getHead().addSuccessor(succesor);
	}
	
	/**
	 * this method initializes the outputQueue.
	 * In the main method after initializing the data structure, this call is made
	 * this will scan all the nodes in the nodes array and all the nodes whose count is zero are added to the outputQueue
	 */
	@SuppressWarnings("unchecked")
	public void initOutputQueue()
	{
		for(int i = 1;i < nodes.length;i++)
		{
			if(nodes[i].getCount() == 0)
			{
				this.outputQueue.addLast(i);
			}
		}
	}
	
	/**
	 * this method deletes all the relations in the original array for a given predecessor.
	 * e.g if there was a constraint like (1,2) then the count value node 2 is 1.
	 * this method will decrement the count by 1. meaning that this constraint is no more valid.
	 * The way to implement is it checks the successor list of the predecessor and decrements  each successor 
	 * count variable by 1.
	 * Also due to this operation if the count of ant node drops to zero then that node is moved to the outputQueue
	 */
	@SuppressWarnings("unchecked")
	public void deleteRelation(int predecesor)
	{
		for(Object i :nodes[predecesor].getHead())
		{
			Integer successor = (Integer)i;
			nodes[successor].setCount(nodes[successor].getCount() - 1);
			if(nodes[successor].getCount() == 0)
			{
				this.outputQueue.addLast(successor);
			}
		}
	}
	
	/**
	 * This method undo what was done in "deleteRelation" method
	 * this method add all the relations in the original array for a given predecessor.
	 * e.g if there was a constraint like (1,2) then the count value node 2 is incremented by 1.
	 * this method will increment the count by 1. meaning that this constraint is  more valid again.
	 * The way to implement is it checks the successor list of the predecessor and increments each successor 
	 * count variable by 1.
	 * Also due to this operation if the count of any node goes up from zero then that node is removed from the outputQueue
	 */
	public void addRelation(int predecesor)
	{
		for(Object i :nodes[predecesor].getHead())
		{
			Integer successor = (Integer)i;
			nodes[successor].setCount(nodes[successor].getCount() + 1);
			if(nodes[successor].getCount() == 1)
			{
				this.outputQueue.removeLast();
			}

		}
	}
	
	/**
	 * This method is a recursive function which generated all the topological permutations which begin with the sequence a1,a2,a3......ak
	 * i.e upon call to this method all the permutations starting with a1,a2,....ak are already being done we just has to permute the rest
	 * This method will temporary change the count value for each node and the outputQueue , but they are restored back to the original values 
	 *  
	 *  @preCondition : this methosd assumes that all the permuations statring with prefix a1,a2.. are already printed out 
	 * @param k this indicate that a1..ak has already been generated
	 */
	@SuppressWarnings("unchecked")
	public void GeneratePermutation(int k)
	{
		if(!this.outputQueue.isEmpty())
		{
			// Initialize base to the  rightmost element of the outputQueue
			int base = (int)this.outputQueue.getLast();
			do
			{
				// Initialize q to the  rightmost element of the outputQueue
				int q = (int)this.outputQueue.getLast();
				
				this.outputQueue.removeLast();
				
				// delete all the relations which were there due to this element.
				// this will decrement the count of all the successors of the element q
				deleteRelation(q);
				
				// add this node to the output array
				this.output[k+1] = q;
				if(k == N-1)
				{	
					visit(output,verbose);
				}
				// permutation using prefix a1...ak are done so call again the function with k = k+1
				GeneratePermutation(k+1);
				
				// restore back all the relation deleted 
				addRelation(q);
				
				/// add the element q back to the left of the outputQueue
				this.outputQueue.addFirst(q);
				
				
			}while(base != (int)this.outputQueue.getLast());
		}
	}
	
	void visit(int [] A, int verbose)
	{
	   if (verbose > 0) {
	      for(int i = 1; i < A.length; i++) {
	    	  System.out.print(A[i]+" ");
	      }
	      System.out.println();
	   }
	   // increment total no. of permutaions
		count++;
	}
	
	/**
	 * The first method which gets the input from the user through standard input
	 * This will initialize all the data structure
	 *     Input specification:
   			First line of input has 3 integers: n, c, and v.
			The value of n is between 3 and 1000;  c is the number of constraint pairs;
			v>0 means verbose output.
			The next c lines of the input contain two integers each: a, b,
			representing the constraint (a,b).

   		   Output specification:
   			If v=0 then the program outputs one line with two integers: the number of
			permutations of 1..n that satisfy the given precedence constraints, and
			the running time of your program in milliseconds (rounded to integer).
			If v>0, then print the permutations, one per line, and the generate the
			output for v=0.
	 *
	 */
	
	public static void main(String args[])
	{
		Scanner scanner = null;
	
		try
		{
			scanner = new Scanner(System.in);
			// getting the input from the user
			String[] firstLine= scanner.nextLine().split(" ");
			int n = Integer.parseInt(firstLine[0]);
			if(n <3 ||n > 1000)
			{
				System.out.println("n cannot be less then 3 or greate then 1000" );
				System.exit(0);
			}else
			{
				Permutation obj = new Permutation(n);
			
				int constraintCount = Integer.parseInt(firstLine[1]);
				int verboseMode = Integer.parseInt(firstLine[2]);
				if(verboseMode == 0 || verboseMode == 1 )
				{
					obj.verbose = verboseMode;
					
					// intializing all the constraints
					for(int i = 0;i < constraintCount;i++ )
					{
						String[] line= scanner.nextLine().split(" ");
						obj.addPrecedence(Integer.parseInt(line[0]),Integer.parseInt(line[1]));
					}

					long start = System.currentTimeMillis();
								
					// intializing all the output Queue
					obj.initOutputQueue();
					
					// call the GeneratePermutation with intial value of k = 0
					obj.GeneratePermutation(0);

					long last = System.currentTimeMillis();
					System.out.println(obj.count +"," + (last-start) );
				}else
				{
					System.out.println("V can be only 0 or 1" );
					System.exit(0);
				}
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			scanner.close();
		}
		
	}

}
