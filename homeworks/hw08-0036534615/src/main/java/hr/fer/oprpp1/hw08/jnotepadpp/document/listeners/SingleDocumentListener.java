package hr.fer.oprpp1.hw08.jnotepadpp.document.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.document.models.SingleDocumentModel;

public interface SingleDocumentListener {

	void documentModifyStatusUpdated(SingleDocumentModel model);
	void documentFilePathUpdated(SingleDocumentModel model);
}
