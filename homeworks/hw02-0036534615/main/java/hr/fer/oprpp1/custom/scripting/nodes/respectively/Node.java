package hr.fer.oprpp1.custom.scripting.nodes.respectively;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Node {
    private boolean isAddFirstCalled = false;
    private ArrayIndexedCollection arrayCol;
	
	public void addChildNode(Node child) {
		if (child == null) throw new NullPointerException();
		if (isAddFirstCalled == false) {
			isAddFirstCalled = true;
			arrayCol = new ArrayIndexedCollection();
		}
		arrayCol.add(child);	
	}
	
	public int numberOfChildren() {
		return arrayCol.size();
	}
	
	public Node getChild(int index) {
		
		return (Node) arrayCol.get(index);
		
	}
	
	// TO DO: Dodaj provjeru na numberOfChildren i getChild za arrayCol
	
}
