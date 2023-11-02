package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

public class LinkedListIndexedCollection extends Collection {
	 private static class ListNode{
        Object value;
        ListNode next;
        ListNode previous;
     }
	 
	 // Variables 
	 
	 private int size;
	 private ListNode first;
	 private ListNode last;
	 
	 // Constructors
	 
	 public LinkedListIndexedCollection() {
		 this.size = 0;
		 this.first = null;
		 this.last = null;
	 }
	 
	 public LinkedListIndexedCollection(Collection other) {
		 this.addAll(other);
	 }
	 
	 // Methods
	 
	 /**
	  * Metoda dodaje predni joj objekt na kraj kolekcije,koja je primjerak razreda LinkedListIndexedCollection
	  * @param value
	  * @return NullPointerException , ako joj je predani objekt null
	  */
	 public void add(Object value) {
		 
		 if (value == null) throw new NullPointerException();
		 
		 ListNode newListNode = new ListNode();
		 
		 newListNode.value = value;
		 newListNode.next = null;
		 newListNode.previous = last;
		 
		 if(this.last == null) {
			 first = newListNode;
		 }else {
			 last.next = newListNode;
		 }
		 last = newListNode;
		 
		 this.size+=1;
	 }
	 
	 
	 /**
	  * Metoda dohvaca element kolekcije na zadanom mjestu(index).
	  * @param index
	  * @return objekt, predstavlja element koji je dohvacen s pozicije index.
	  * @return IndexOutOfBoundsException, ako index < 0 ili index > trenutne velicine kolekcije
	  */
	 public Object get(int index) {
		 
		 if (index < 0 || index > (this.size-1)) throw new IndexOutOfBoundsException();
		 
		 int middle = this.size/2;
		 
		 ListNode helpNode;
		 Object result;
		 
		 if (index < middle || index == middle) {
			 helpNode = first;
			 for(int i=0;i<index;i++) {
				 helpNode = helpNode.next;
			 }
			 result = helpNode.value;
		 }else {
			 helpNode = last;
			 
			 for(int i=0;i<(this.size-index-1);i++) {
				 helpNode = helpNode.previous;
				 System.out.print("Uso u petlju");
			 }
			 result = helpNode.value;
		 }
		 
		 return result;
	 }
	 
	 /**
	  * Metoda brise sve elemente trenutne kolekcije.
	  */
	 public void clear() {
		 first = null;
		 last = null;
		 this.size = 0;
	 }
	 
	 /**
	  * Metoda dodaje objekt u kolekciju na zadanoj joj pozicij(position)
	  * 
	  * @param value, predstavlja objekt kojeg treba ubaciti u kolekciju
	  * @param position, predstavlja poziciju na koju treba ubaciti objekt
	  * @return NullPointerException, ako je value == null
	  * @return IndexOutOfBoudnsException, ako je position neispravan.
	  */
	 
	 public void insert(Object value,int position) {
		 
		 if (value == null) throw new NullPointerException();
		 if (position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		 
		 
		 ListNode newListNode = new ListNode();
		 newListNode.value = value;
		 if (first == null) {
			 first = newListNode;
			 last = newListNode;
		 } else {
			 ListNode HelpNode = first;
			 for(int i=0;i<position;i++) {
				 HelpNode=HelpNode.next;
			 }
		 
			 if(HelpNode == null) {  // dodavanje na kraj
				 newListNode.previous = last;
				 last.next = newListNode;
				 last = newListNode;
			 } else if (HelpNode == first) { // dodavanje na pocetak
				 newListNode.next = first;
				 first.previous = newListNode;
				 first = newListNode;
			 } else {   // dodavanje u sredinu
				 newListNode.next = HelpNode;
				 newListNode.previous = HelpNode.previous;
				 HelpNode.previous.next = newListNode;
			 	 HelpNode.previous = newListNode;
			 }
		 }

		 this.size+=1; 
		 
	 }
	 
	 /**
	  * Metoda pronalazi index danog joj elementa u kolekciji, te vraca ga.
	  * @param value, objekt koji se trazi u kolekciji
	  * @return broj , index na kojem se nalazi element
	  * @return -1,  element ne postoji u kolekciji
	  */
	 
	 public int indexOf(Object value) {
		 
		 if (value == null) return -1;
		 ListNode helpNode = first;
		 for(int i=0;i<this.size;i++) {
			 if (helpNode.value.equals(value)) return i;
			 helpNode = helpNode.next;
		 }
		 
		 return -1;
	 }
	 
	 
	 public void remove(int index) {
	      if (index < 0 || index > this.size) throw new IndexOutOfBoundsException();
	      
	      ListNode helpNode = first;
	      for(int i=0;i<index;i++) {
	    	  helpNode = helpNode.next;
	      }
	      
	      if (helpNode == first) {  // brisanje s pocetka
	    	  first = helpNode.next;
	    	  first.previous = null;
	      } else if(helpNode == last) {  // brisanje s kraja
	    	  last = helpNode.previous;
	    	  last.next = null;
	      } else if (helpNode == first && helpNode == last) { // brisanje zadnjeg elementa
	    	  this.clear();
	      }
	      else {  // brisanje elementa u sredini
	    	  
	    	  helpNode.next.previous = helpNode.previous;
	    	  helpNode.previous.next = helpNode.next;
	      }
	      this.size= this.size-1;
	 }
	 
	 
	 /**
	  * Metoda ispisuje kolekciju na ekran.
	  */
	 public void print() {
		 ListNode node = first;
		 System.out.println(this.size);
		 for(int i=0;i<this.size;i++) {
			 System.out.print(node.value + " -> ");
			 node = node.next;
		 }
		 System.out.print("null");
		 System.out.println();
	 }
	 
	 /**
	  * Metoda vraca broj elemenata u kolekciji
	  * @return broj, broj elemenata u kolekciji
	  */
	 public int size() {
		 return this.size;
	 }
	 
	 
	 /**
	  * Metoda provjera nalazi li se zadani objekt u kolekciji.
	  * @param value
	  * @return true, ako se objekt nalazi u kolekciji
	  * @return false, ako se objekt ne nalazi u kolekciji
	  */
	 public boolean contains(Object value) {
		 boolean contain = false;
		 ListNode node=first;
		 for(int i=0;i<this.size;i++) {
			 if (node.value.equals(value)) {
				 contain = true;
			 }
		 }
		 return contain;
	 }
	 
	   /**
		 * Metoda pronalazi objekt koji je dobila u argumentima,te ako postoji brise ga.
		 *
		 * @param value, predstavlja objekt koji treba izbrisati iz kolekcije
		 * @return true, ako je objekt obrisan
		 * @return false,ako objekt nije pronadjen u kolekciji
		 */
	 
	 public boolean remove(Object value) {
		 
		 boolean bool;
		 int index = this.indexOf(value);
		 
		 if (index == -1) {
			 bool = false;
		 } else {
			 bool = true;
			 this.remove(index);
		 }
		 
		 return bool;
		 
	 }
	 
	 
	  /**
		* Metoda stvara i vraca novi niz koji puni elementima primjerka razreda LinkedListIndexedCollection.
		* Nikada ne vraca null.
		* 
		* @return Object[], predstavlja primjerak razreda Collection,ali u obliku niza.
		*/
	 public Object[] toArray() {
		 
		 Object[] array = new Object[this.size];
		 ListNode node = first;
		 for (int i=0;i<this.size;i++) {
			 array[i] = node.value;
			 node= node.next;
		 }
		 
		 return array;
	 }
	 
	 /**
	  * Metoda poziva metodu process iz razreda Processor, za svaki clan primjerka razreda LinkedListIndexedCollection
	  * 
	  * @param processor, predstavlja primjerak razreda Processor iz kojeg se poziva metoda process.
	  */
	 
	 public void forEach(Processor processor) {
		 ListNode node = first;
		 for(int i=0;i<this.size;i++) {
			 processor.process(node.value);
			 node = node.next;
		 }
	 }
	 
	 
	 public static void main(String ...args) {
		 /*ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		 col.add(Integer.valueOf(20));
		 col.add("New York");
		 col.add("San Francisco"); // here the internal array is reallocated to 4
		 System.out.println(col.contains("New York")); // writes: true
		 col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		 System.out.println(col.get(1)); // writes: "San Francisco"
		 System.out.println(col.size()); // writes: 2
		 col.add("Los Angeles");
		 LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		 // This is local class representing a Processor which writes objects to System.out
		 class P extends Processor {
		  public void process(Object o) {
		  System.out.println(o);
		  }
		 };
		 System.out.println("col elements:");
		 col.forEach(new P());
		 System.out.println("col elements again:");
		 System.out.println(Arrays.toString(col.toArray()));
		 System.out.println("col2 elements:");
		 col2.forEach(new P());
		 System.out.println("col2 elements again:");
		 System.out.println(Arrays.toString(col2.toArray()));
		 System.out.println(col.contains(col2.get(1))); // true
		 System.out.println(col2.contains(col.get(1))); // true
		 col.remove(Integer.valueOf(20)); // removes 20 from collection (at position 0).*/
		
		 LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		 list.add(Integer.valueOf(45));
		 list.add(Integer.valueOf(40));
		 
		 ArrayIndexedCollection array = new  ArrayIndexedCollection(list,40);
		
		 
		 array.print();
		 

	 }
	 
	 
	 
}
