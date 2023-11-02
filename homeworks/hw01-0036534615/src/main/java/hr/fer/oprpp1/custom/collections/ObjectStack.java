package hr.fer.oprpp1.custom.collections;

public class ObjectStack {
     private ArrayIndexedCollection arrayCollection;
     
     public ObjectStack() {
    	 this.arrayCollection = new ArrayIndexedCollection();
     }
     
     public boolean isEmpty() {
    	 return arrayCollection.isEmpty();
     }
     
     public int size() {
    	 return arrayCollection.size();
     }
     
     public void push(Object value) {
    	arrayCollection.add(value);
     }
     
     public Object pop() {
    	 
    	 this.EmptyStackHandling();
    	 
    	 Object element = arrayCollection.get(this.size()-1);
    	 
    	 arrayCollection.remove(this.size()-1);
    	 
    	 return element;
     }
     
     public Object peek() {
    	 
    	 this.EmptyStackHandling();
    	 
    	 return arrayCollection.get(this.size()-1);
    	 
     }
     
     
     public void clear() {
    	 
    	arrayCollection.clear();
     }
     
     
     private void EmptyStackHandling() {
    	 if (this.size() == 0) throw new EmptyStackException();
     }
     
     
    
     public static void main(String ...args) {
    	 ObjectStack stack = new ObjectStack();
    	 stack.push(Integer.valueOf(12));
    	 stack.push(Integer.valueOf(14));
    	 
    	 System.out.println(stack.pop());
    	 System.out.println(stack.size());
    	 System.out.println(stack.peek());
    	 stack.clear();
    	 System.out.println(stack.size());
    	 
    	 
     }
     
}
