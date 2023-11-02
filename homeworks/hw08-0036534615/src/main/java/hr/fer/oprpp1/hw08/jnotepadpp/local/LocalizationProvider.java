package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

	private String language;
	private static LocalizationProvider instance = new LocalizationProvider();
	private ResourceBundle bundle;
	
	private LocalizationProvider() {
		this.language = "en";
		this.bundle = ResourceBundle.getBundle(
				"hr.fer.oprpp1.hw08.jnotepadpp.locale.prijevodi", 
				Locale.forLanguageTag(language));
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
		this.bundle = ResourceBundle.getBundle(
				"hr.fer.oprpp1.hw08.jnotepadpp.locale.prijevodi", 
				Locale.forLanguageTag(language));
		this.fire();
	}
	
	public static  LocalizationProvider getInstance() {
		return instance;
	}

}
