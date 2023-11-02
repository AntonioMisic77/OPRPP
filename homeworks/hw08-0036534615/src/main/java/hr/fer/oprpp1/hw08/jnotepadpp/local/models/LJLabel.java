package hr.fer.oprpp1.hw08.jnotepadpp.local.models;

import javax.swing.JLabel;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

public class LJLabel  extends JLabel{

	private String  text;
	
	public LJLabel(ILocalizationProvider provider,String key) {
		this.setText(provider.getString(key));
		
		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LJLabel.this.setText(provider.getString(key));
			}
			
		});
	}
}
