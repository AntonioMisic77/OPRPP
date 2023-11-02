package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

public class ArrayIndexedCollection extends Collection {
	
	  // Variables
      private int size;
      private Object[] elements;
      
      
      // Constructors
      
      public ArrayIndexedCollection(){
    	  this.size = 0;
    	  this.elements = new Object[16];
      }
      
      // TODO: Promjeni ovaj konstruktor
      
      public ArrayIndexedCollection(Collection other) {
    	  if (other == null) throw new NullPointerException();
    	  this.elements = new Object[other.size()];
    	  this.addAll(other);
    	  
      }
      
      public ArrayIndexedCollection(int initialCapacity) {
    	  if (initialCapacity < 1) throw new IllegalArgumentException();
    	  
    	  this.size = 0;
    	  this.elements = new Object[initialCapacity];
      }
      
      public ArrayIndexedCollection(Collection other, 
    		                        int initialCapacity) {
    	  
    	  if (other == null) throw new NullPointerException();
    	  if (initialCapacity < other.size() || initialCapacity == other.size()) {
    		  this.elements = new Object[other.size()];
    		  this.addAll(other);
    	  } else{
    		  this.elements = new Object[initialCapacity];
    		  this.addAll(other);
    	  }
    	  this.size = other.size();
    	  
    	   
      }
      
      // Methods 
      
      /**
       * Metoda dodaje dan joj objekt u trenutni primjerak razreda ArrayIndexedCollection
       * 
       * @param value, predstavlja objekt koji se dodaje u primjerak razreda ArrayIndexedCollection.
       * @return NullPointerException, ako je parametar value null
       */
      public void add(Object value) {
    	  if (value == null) throw new NullPointerException();
    	  
          this.doubleSizeOfArray();
          
    	  this.elements[this.size] = value;
    	  this.size+=1;
      }
      
      /**
       * Metoda dohvaca element primjerka razreda ArrayIndexedCollection,koji je na mjestu index.
       * @param index, redni broj elementa u kolekciji. 
       * @return IndexOutOfBoundsException, ako je zadani parametar izvan kapaciteta kolekcije.
       * @return Object
       */
      public Object get(int index) {
    	 
    	  if (index > (this.elements.length-1) || index < 0) throw new IndexOutOfBoundsException(); 
    	  
    	  return this.elements[index];
    	  
      }
      
     
      
      /**
       * Metoda brise elemente iz primjerka razreda ArrayIndexedCollection.
       * Kapacitet kolekcije ostaje isti
       */
      
      public void clear() {
    	  for (int i=0;i< this.elements.length;i++) {
    		  this.elements[i] = null;
    	  }
    	  this.size = 0;
      }
      
      /**
       * Metoda ubacuje objekt u primjerak razreda ArrayIndexedCollection na zadanu poziciju(position)
       * Ako metoda primi poziciju koja je vec zauzeta,doci ce do pomaka u desno svih elemenata od te pozicije.
       * Nakon pomaka objekt(value) se ubacuje na novo napravljeno slobodno mjesto.
       * 
       * @param value, objekt koji se dodaje u primjerak razreda ArrayIndexedCollection
       * @param position, pozicija na koju se objekt ubacuje u kolekciju.
       */
      public void Inserts(Object value, int position) {
    	  
    	  if (value == null) throw new NullPointerException();
    	  this.doubleSizeOfArray();
    	  
    	  if (position > this.size+2 || position < 0) throw new IndexOutOfBoundsException();
    	  
    	  if (position < this.size) { 
    		for(int i=this.size;i>=position;i--){
    		   this.elements[i+1] = this.elements[i];
    	     }	 
    	  }
    	  
    	  this.elements[position]= value;
    	  this.size+=1;
    
      }
      
      /**
       * Metoda vraca poziciju zadanog joj objekta(value) u primjerku razreda ArrayIndexedCollection
       * @param value
       * @return -1, ako ne postoji zadani objekt u kolekciji
       * @return broj, pozicija objekta(value) u kolekciji
       */
      public int indexOf(Object value) {
    	  
    	  if (value == null) return -1;
    	  
    	  int index = -1;
    	  for(int i=0;i<(this.size-1);i++) {
    		 if (this.elements[i].equals(value)) index = i;
    	  }
    	  
    	  return index;
      }
      
      /**
       * Metoda brise element iz primjerka razreda ArrayIndexedCollection
       * @param index, oznacava mjesto u kolekciji s kojeg treba obrisati element
       */
      
      public void remove(int index) {
    	  
    	  if (index < 0 || index > this.size) throw new IndexOutOfBoundsException();
    	 
    	  this.elements[index]=null;
    	  for(int i=index;i< this.size;i++) {
    		  this.elements[i]=this.elements[i+1];
    	  }
    	  
    	  this.size-=1;
    	  
    	  
      }
      
      /**
       * Metoda poziva metodu process iz danog joj primjerka razreda Processor,za svaki element kolekcije
       * @param Processor, objekt
       */
      
      public void forEach(Processor processor) {
    	  
    	  
    	  for(int i=0;i<this.size;i++) {
    		  processor.process(this.elements[i]);
    	  }
    	  
      }
      
      /**
  	 * Metoda provjerava nalazi li se objekt u primjerku razreda ArrayIndexedCollection.
  	 * Argument moze biti bilo koji objekt.
  	 * 
  	 * @param value,predstavlja objekt koji se trazi u kolekciji
  	 * @return true,ako se objekt nalazi u kolekciji
  	 * @return false,ako se objekt ne nalazi u kolekciji
  	 */
      
      public boolean contains(Object value) {
    	  
    	  if (value == null) return false;
    	  
    	  boolean contain = false;
    	  for(int i=0;i<(this.size-1);i++) {
    		 if (this.elements[i].equals(value)) contain = true;
    	  }
    	  
    	  return contain;
      }
      
      /**
  	 * Metoda dohvaca broj elemenata u kolekciji.
  	 * 
  	 * @return broj, koji oznacava broj elemenata u kolekciji.
  	 *         broj nikada ne moze biti negativan.
  	 */
      
      public int size() {
    	  return this.size;
      }
      
      /**
  	 * Metoda stvara i vraca novi niz koji puni elementima primjerka razreda Collection.
  	 * Nikada ne vraca null.
  	 * 
  	 * @return Object[], predstavlja primjerak razreda Collection,ali u obliku niza.
  	 */
      
      public Object[] toArray() {
    	  
    	  Object[] newArray = new Object[this.size];
    	  for(int i=0;i<this.size;i++) {
    		  newArray[i] = this.elements[i];
    	  }
    	  return newArray;
      }
      
      
      /**
       * Pomocna metoda koja nam poduplava kapacitet kolekcije,ako je ona popunjen
       * 
       * @param value
       * @return NullPointerException, ako je value null
       */
      private void doubleSizeOfArray () {
    	  
    	  if (this.size == this.elements.length) {
    		  Object[] array = new Object[this.size];
    		  array = this.elements;
    		  this.elements = new Object[2*this.size];
    		  
    		  for(int i=0;i<array.length;i++) {
    			  this.elements[i] = array[i];
    		  }
    	  }
      }
      
      public void print() {
    	  for(int i=0;i<this.size;i++) {
    		 System.out.println(this.elements[i]);
    	  }
      }
      
      public static void main(String ...strings) {
    	  
    	 /* ArrayIndexedCollection col = new ArrayIndexedCollection(2);
    	  col.add(Integer.valueOf(20));
    	  col.add("New York");
    	  col.add("San Francisco"); // here the internal array is reallocated to 4
  
    	  System.out.println(col.contains("New York")); // writes: true
    	  col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
    	  System.out.println(col.get(1)); // writes: "San Francisco"
    	  System.out.println(col.size()); // writes: 2
    	  col.add("Los Angeles");*/
    	  
      }
      
      
      
      
      
      
      
      
} 
