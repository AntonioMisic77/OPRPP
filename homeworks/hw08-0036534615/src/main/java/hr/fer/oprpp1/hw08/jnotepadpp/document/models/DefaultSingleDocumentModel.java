package hr.fer.oprpp1.hw08.jnotepadpp.document.models;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.SingleDocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
     private JTextArea textArea;
     private Path path;
     private boolean modified;
     private List<SingleDocumentListener> listeners;
     
     public DefaultSingleDocumentModel(Path path,String textContent) {
		 this.textArea = new JTextArea(textContent);
		 this.path = path;
		 this.modified = false;
		 this.listeners = new ArrayList<>();   
	}
	
	
	
	@Override
	public JTextArea getTextComponent() {
		return this.textArea;
	}

	@Override
	public Path getFilePath() {
		return this.path;
	}

	@Override
	public void setFilePath(Path path) {
		if (path == null) throw new NullPointerException();
		this.path = path;	
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}	
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.add(l);	
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);	
	}

}
