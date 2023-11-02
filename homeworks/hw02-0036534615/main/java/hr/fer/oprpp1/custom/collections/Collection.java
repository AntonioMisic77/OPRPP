package hr.fer.oprpp1.custom.collections;

public abstract class Collection {
    
	
	// Constructor
	
	protected Collection () {
          
	}
	
	// Methods 
	
	/**
	 *  Metoda provjerava da li je kolekcija prazna ili nije.
	 * 
	 * @return true,ako je kolekcija prazna.
	 * @return false,ako kolekcija nije prazna.
	 */
	
	public boolean isEmpty() {
		
	    if (this.size() != 0) {
	    	return true;
	    }else {
	    	return false;
	    }
	}
	
	
	/**
	 * Metoda dohvaca broj elemenata u kolekciji.
	 * 
	 * @return broj, koji oznacava broj elemenata u kolekciji.
	 *         broj nikada ne moze biti negativan.
	 */
	
	public abstract int size();
	
	/**
	 * Metoda dodaje element u kolekciju.
	 * 
	 * @param value, oznacava objekt koji se treba dodati u kolekciju
	 */
	
	public abstract void add(Object value);
	
	/**
	 * Metoda provjerava nalazi li se objekt u kolekciji.
	 * Argument moze biti bilo koji objekt ili null.
	 * 
	 * @param value,predstavlja objekt koji se trazi u kolekciji
	 * @return true,ako se objekt nalazi u kolekciji
	 * @return false,ako se objekt ne nalazi u kolekciji
	 */
	
	public abstract boolean contains(Object value);
	
	/**
	 * Metoda pronalazi objekt koji je dobila u argumentima,te ako postoji brise ga.
	 *
	 * @param value, predstavlja objekt koji treba izbrisati iz kolekcije
	 * @return true, ako je objekt obrisan
	 * @return false,ako objekt nije pronadjen u kolekciji
	 */
	
	public abstract boolean remove(Object value);
	
	/**
	 * Metoda stvara i vraca novi niz koji puni elementima primjerka razreda Collection.
	 * Nikada ne vraca null.
	 * 
	 * @return Object[], predstavlja primjerak razreda Collection,ali u obliku niza.
	 */
	
	public abstract Object[] toArray();
	
	/**
	 * Metoda poziva metodu process iz razreda Processor, za svaki clan primjerka razreda Collection
	 * 
	 * @param processor, predstavlja primjerak razreda Processor iz kojeg se poziva metoda process.
	 */
	
	public void forEach(Processor processor) {
		ElementsGetter elGetter = this.createElementsGetter();
		for(int i=0;i<this.size();i++) {
			processor.process(elGetter.getNextElement());
		}
	}
	
	/**
	 * Metoda dodaje elemente zadanog primjerka razreda Collection u trenutni primjerak razreda Collection.
	 * 
	 * @param other,primjerak razreda Collection iz kojeg se dodaju elementi u trenutni razred Collection.
	 */
	
	public void addAll(Collection other) {
		
		class AddProcessor implements Processor {
			
			 private Collection _collection;
			
			 public AddProcessor(Collection collection) {
				 this._collection = collection;
			 }
			 
			 @Override
			 public void process(Object value) {
				 _collection.add(value);
			 }
		}
		
		AddProcessor addProcessor = new AddProcessor(this);
		
		other.forEach(addProcessor);
		
	}
	
	/**
	 * Metoda brise sve elemente iz trenutnog primjerka razreda Collection.
	 */
	
	public  abstract void clear();
	
	
	public abstract ElementsGetter createElementsGetter();
	
	public void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter elGetter = col.createElementsGetter();
		Object[] array = new Object[col.size()];
		int index = 0;
		for(int i=0;i<col.size();i++) {
			Object obj = elGetter.getNextElement();
			if (tester.test(obj) == true) {
				array[index] = obj;
				index++;
			}
		}
		for (int i=0;i<index;i++) {
			this.add(array[i]);
		}
	}
	
	
	
	
	
	
	
	
}
