package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction  extends AbstractAction {

	private String key;
	private ILocalizationProvider lp;
	
	public LocalizableAction(String key,ILocalizationProvider lp,String toolTipKey) {
		this.key = key;
		this.lp = lp;
		
		this.putValue(Action.NAME, lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LocalizableAction.this.putValue(Action.NAME, lp.getString(key));
				LocalizableAction.this.putValue(Action.SHORT_DESCRIPTION, lp.getString(toolTipKey));
			}
			
		});
	}
	
}
