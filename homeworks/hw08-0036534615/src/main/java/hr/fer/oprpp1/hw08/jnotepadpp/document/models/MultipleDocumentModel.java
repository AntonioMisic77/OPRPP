package hr.fer.oprpp1.hw08.jnotepadpp.document.models;

import java.nio.file.Path;

import javax.swing.JComponent;

import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.MultipleDocumentListener;

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel>{
	JComponent getVisualComponent();
	SingleDocumentModel createNewDocument();
	SingleDocumentModel getCurrentDocument();
	SingleDocumentModel loadDocument(Path path);
	void saveDocument(SingleDocumentModel model,Path newPath);
	void closeDocument(SingleDocumentModel model);
	void addMultipleDocumentListener(MultipleDocumentListener l);
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	void setCurrentDocument(SingleDocumentModel model);
	int getNumberOfDocuments();
	SingleDocumentModel getDocument(int index);
	SingleDocumentModel findForPath(Path path);
	int getIndexOfDocument(SingleDocumentModel doc);
}
