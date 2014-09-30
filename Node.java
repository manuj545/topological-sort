
/*
 * Classname : Node
 */

/**
 *   This class implement a Node used to represent an element     		
 *
 * @version      
         1.0, 20 Sep 2014  
 * @author          
        Manuj Singh 
 */
public class Node {

	/**  
	 * count represents the total number of the predecessor of this node
	 * i.e total no of constraints in which this node was succeeded by other nodes
	 */
	int count;
	
	/**  
	 * head points to a list of the successors of this node
	 * A successor is a node which occurs in the constraint relation like this (predecessor,successor)
	 */
	SuccesorChain head;
	
	public Node()
	{
		this.count = 0;
		this.head = new SuccesorChain();
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public SuccesorChain getHead() {
		return head;
	}
	public void setHead(SuccesorChain head) {
		this.head = head;
	}
}
