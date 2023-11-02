package hr.fer.oprpp1.hw08.jnotepadpp.document.models;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hr.fer.oprpp1.hw08.jnotepadpp.ButtonTabPane;
import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

public class DefaultMultipleDocumentModel extends  JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	private List<SingleDocumentModel> documents;
	private List<MultipleDocumentListener> listeners;
	private SingleDocumentModel currentDocument;
	private ILocalizationProvider provider;
	private SingleDocumentListener listener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index = DefaultMultipleDocumentModel.this.getIndexOfDocument(model);
			ButtonTabPane pane = (ButtonTabPane) DefaultMultipleDocumentModel.this.getTabComponentAt(index);
			pane.setIconBasedOnStatus(model.isModified());
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public DefaultMultipleDocumentModel(ILocalizationProvider provider) {
		this.documents = new ArrayList<>();
		this.listeners = new ArrayList<>();
		this.currentDocument = null;
		this.provider = provider;
		
	}
	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel model = new DefaultSingleDocumentModel(null, "");
		model.addSingleDocumentListener(listener);
		this.documents.add(model);
		this.currentDocument = model;
		return model;
		// dodaj Listenera za ikonicu.
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(
					this,
					String.format(provider.getString("File_Error_NotExist"),path.toString()),
					provider.getString("Error"),
					JOptionPane.ERROR_MESSAGE
					);
			return null;
		}
		byte[] okteti = null;
		try {
			okteti = Files.readAllBytes(path);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(
					this,
					provider.getString("File_Error_While_Reading")+" "+path.toString(),
					provider.getString("Error"),
					JOptionPane.ERROR_MESSAGE
					);
			return null;
		}
		String text = new String(okteti,StandardCharsets.UTF_8);
		SingleDocumentModel model = this.findForPath(path);
		if (model == null) {
			model = new DefaultSingleDocumentModel(path,text);
			model.addSingleDocumentListener(listener);
		}
		
		this.currentDocument = model;
		this.documents.add(model);
		return model;
		
		// Dodaj listenera za ikonice.
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null) {
			newPath = model.getFilePath();
		} else {
		  if (this.findForPath(newPath) != null) throw new IllegalArgumentException("Zadajte drugi path, ovaj je vec zauzet.");
		}
		byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		
		
		try {
			Files.write(newPath, data);
		}catch(IOException ex) {
			JOptionPane.showMessageDialog(
					 this,
					 provider.getString("File_Error_While_Writing")+newPath.toFile().getAbsolutePath(),
					 provider.getString("Error"),
					 JOptionPane.ERROR_MESSAGE
					);
			return;
		}
		model.setFilePath(newPath);
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		if (model.getFilePath() == null) {
			provider.removeLocalizationListener(this.getLocalizationListenerForDocument(model));
		}
		this.documents.remove(model);
		if (model.equals(this.currentDocument)) {
			currentDocument = null;
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.add(l);
		
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.remove(l);	
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.documents.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		if (path == null) throw new NullPointerException();
		for (SingleDocumentModel model : documents) {
			if (path.equals(model.getFilePath())) return model;
		}
		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		return documents.indexOf(doc);
	}

	@Override
	public void setCurrentDocument(SingleDocumentModel model) {
		if (getIndexOfDocument(model) != -1) {
			this.currentDocument = model;
		}else {
			throw new IllegalArgumentException();
		}
		
	}
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new Iterator<SingleDocumentModel>() {

			private int index = 0;
			@Override
			public boolean hasNext() {
				if (index < DefaultMultipleDocumentModel.this.getNumberOfDocuments()) return true;
				return false;
			}

			@Override
			public SingleDocumentModel next() {
				if (this.hasNext()) {
					SingleDocumentModel model = DefaultMultipleDocumentModel.this.documents.get(index);
					index++;
					return model;
				}
				return null;
				
			}
			
		};
	}
	
	public ButtonTabPane getLocListenerOfCurrentDocument() {
		int index = this.getIndexOfDocument(currentDocument);
		ButtonTabPane pane = (ButtonTabPane) this.getTabComponentAt(index);
		return pane;
	}
	
	public ILocalizationListener getLocalizationListenerForDocument(SingleDocumentModel model) {
		
		int index = this.getIndexOfDocument(model);
		ButtonTabPane pane = (ButtonTabPane) this.getTabComponentAt(index);
		return pane.getListener();
		
	};
	
	public int getIndexOfTabComponent(ButtonTabPane tabPane) {
		
		for(int i=0;i<this.getNumberOfDocuments();i++) {
			if (this.getTabComponentAt(i).equals(tabPane)) return i;
		}
		return -1;
	}
	
}
