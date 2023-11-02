package hr.fer.oprpp1.custom.collections;

import  org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConstructorsTest {
	
	LinkedListIndexedCollection list;
	ArrayIndexedCollection array;
	
	public ConstructorsTest() {
		this.list = new LinkedListIndexedCollection();
		this.array = new ArrayIndexedCollection();
	}
     
    @Test
	public void testAICNullCollection() {
		Assertions.assertThrows(NullPointerException.class,() -> new ArrayIndexedCollection(null));
	}
    
    @Test
    public void testAICInitialCapacityInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class,() -> new ArrayIndexedCollection(0));	
    }
    
    @Test
    public void testAICCopyingElementsFromOtherCollection() {
    	list.add(Integer.valueOf(2));
    	list.add(Integer.valueOf(4));
    	
    	ArrayIndexedCollection col = new ArrayIndexedCollection(list);
    	Assertions.assertEquals(list.toArray(), col.toArray());
    }
    
    @Test 
    public void testLLICNullCollection() {
    	Assertions.assertThrows(NullPointerException.class,() -> new LinkedListIndexedCollection(null));	
    }
    
    @Test
    public void testLLICCopyingElementsFromOtherCollection() {
    	array.add(Integer.valueOf(2));
    	array.add(Integer.valueOf(4));
    	
    	LinkedListIndexedCollection col = new LinkedListIndexedCollection(array);
    	Assertions.assertEquals(array.toArray(), col.toArray());
    }
    
}
