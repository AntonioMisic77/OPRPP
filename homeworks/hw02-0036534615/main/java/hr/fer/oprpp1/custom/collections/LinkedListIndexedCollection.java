package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class LinkedListIndexedCollection extends List {
	 private static class ListNode{
        Object value;
        ListNode next;
        ListNode previous;
     }
	 
	 private static class LinkedListElementGetter  implements ElementsGetter{
		 
		 LinkedListIndexedCollection listCollection;
		 ListNode node;
		 long savedModificationCount;
		 
		 public LinkedListElementGetter(LinkedListIndexedCollection list) {
			 this.listCollection = list;
			 this.node = list.first;
			 this.savedModificationCount = list.modificationCount;
		 }
		 @Override
		 public boolean hasNextElement() {
			 this.throwException(savedModificationCount);
			 if (node == null) return false;
			 else return true;
		 }
		 
		 @Override
		 public Object getNextElement() {
			 this.throwException(savedModificationCount);
			 if (this.hasNextElement() == true) {
				 Object result = node.value;
				 node = node.next;
				 return result;
			 }
			 else {
				 throw new NoSuchElementException();
			 }
		 }
		 private boolean throwException(long counter) {
  		   if (counter != listCollection.modificationCount) throw new ConcurrentModificationException();
  		   else {
  			   return true;
  		   }
  	   }
		@Override
		public void processRemaining(Processor p) {
			this.throwException(savedModificationCount);
			for(var el = node;el.next != null;el = el.next) {
				p.process(el.value);
			}
			
		}
		 
	 }
	 
	 // Variables 
	 
	 private int size;
	 private ListNode first;
	 private ListNode last;
	 private long modificationCount = 0;
	 
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
		 this.modificationCount++;
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
		 this.modificationCount++;
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
		 this.modificationCount++;
		 
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
	      this.modificationCount++;
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
		 this.modificationCount++;
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
	 
	/* public void forEach(Processor processor) {
		 ListNode node = first;
		 for(int i=0;i<this.size;i++) {
			 processor.process(node.value);
			 node = node.next;
		 }
	 }*/	 
	 
	 public static void main(String ...args) {
		 
		
		/* Collection col1 = new LinkedListIndexedCollection();
		 Collection col2 = new LinkedListIndexedCollection();
		 col1.add("Ivo");
		 col1.add("Ana");
		 col1.add("Jasna");
		 col2.add("Jasmina");
		 col2.add("Štefanija");
		 col2.add("Karmela");
		 ElementsGetter getter1 = col1.createElementsGetter();
		 ElementsGetter getter2 = col1.createElementsGetter();
		 ElementsGetter getter3 = col2.createElementsGetter();
		 System.out.println("Jedan element: " + getter1.getNextElement());
		 System.out.println("Jedan element: " + getter1.getNextElement());
		 System.out.println("Jedan element: " + getter2.getNextElement());
		 System.out.println("Jedan element: " + getter3.getNextElement());
		 System.out.println("Jedan element: " + getter3.getNextElement());*/
		 
		 /*Collection col = new LinkedListIndexedCollection();
		 col.add("Ivo");
		 col.add("Ana");
		 col.add("Jasna");
		 ElementsGetter getter = col.createElementsGetter();
		 System.out.println("Jedan element: " + getter.getNextElement());
		 System.out.println("Jedan element: " + getter.getNextElement());
		 col.clear();
		 System.out.println("Jedan element: " + getter.getNextElement());*/

		/*class EvenIntegerTester implements Tester {
			 public boolean test(Object obj) {
			 if(!(obj instanceof Integer)) return false;
			 Integer i = (Integer)obj;
			 return i % 2 == 0;
			 }
			}
			Tester t = new EvenIntegerTester();
			System.out.println(t.test("Ivo"));
			System.out.println(t.test(22));
			System.out.println(t.test(3));
			
			
			Collection col2 = new LinkedListIndexedCollection();
			Collection col1 = new ArrayIndexedCollection();
			col1.add(2);
			col1.add(3);
			col1.add(4);
			col1.add(5);
			col1.add(6);
			col2.add(12);
			col2.addAllSatisfying(col1, new EvenIntegerTester());
			
			Object[] array = col2.toArray();
			
			for(int i=0;i<array.length;i++) {
				System.out.println(array[i]);
			}
			//col2.forEach(System.out::println);*/
		 
		 List col1 = new ArrayIndexedCollection();
		 List col2 = new LinkedListIndexedCollection();
		 col1.add("Ivana");
		 col2.add("Jasna");
		 Collection col3 = col1;
		 Collection col4 = col2;
		 col1.get(0);
		 col2.get(0);
		 //col3.get(0); // neće se prevesti! Razumijete li zašto?
		 //col4.get(0); // neće se prevesti! Razumijete li zašto?
		 col1.forEach(System.out::println); // Ivana
		 col2.forEach(System.out::println); // Jasna
		 col3.forEach(System.out::println); // Ivana
		 col4.forEach(System.out::println); // Jasna

	 }

	@Override
	public LinkedListElementGetter createElementsGetter() {
		// TODO Auto-generated method stub
		return new LinkedListElementGetter(this);
	}
	 
	 
	 
}
