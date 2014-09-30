
/*
 * Classname : SuccesorChain
 */
import java.util.Iterator;

/**
 *   This class implement a SuccesorChain used to represent a list of all the successor nodes of a given node.
 *   @Implements  Iterable    		
 *
 * @version      
         1.0, 20 Sep 2014  
 * @author          
        Manuj Singh
        Abhishek Gupta 
 */
@SuppressWarnings("rawtypes")
public class SuccesorChain implements Iterable{

		/**  
		 * first points to the first successor element of the a given node
		 */
	    private SuccessorElement first;
	    
	    /**
	     * this is private class that is used to represent a successor element
	     */
	    private static class SuccessorElement {
	    	/**  
			 * suc represents the value of this element
			 */
	    	private int suc;
	    	
	    	/**  
			 * next points to the next successor in the list
			 */
	        private SuccessorElement next;
	    }


		/**
		 * Constructor used to initialize the class
		 * @param n , total no of elements
		 */
	    public SuccesorChain() {
	        first = null;
	    }
	    

		/**
		 * This method will add successor to the list
		 * @param item , item to be added
		 */
	    public void addSuccessor(int item) {
	        
	    	SuccessorElement previous = first;
	        first = new SuccessorElement();
	        first.suc = item;
	        first.next = previous;
	    }
	    
	    
		public Iterator iterator()  {
	        return new ListIterator(first);  
	    }

	    /**
		 * This implements the list Iterator, so can we iterate over the object of this class
		 */
	    
		private class ListIterator implements Iterator {
	        private SuccessorElement current;

	        public ListIterator(SuccessorElement first) {
	            current = first;
	        }

	        public boolean hasNext()  { 
	        	return current != null;                     
	        	}

	        public Integer next() {
	            int item = current.suc;
	            current = current.next; 
	            return item;
	        }

			@Override
			public void remove() {
				// Not using but added for JDK 1.7 or below.
				
			}
	    }

}
