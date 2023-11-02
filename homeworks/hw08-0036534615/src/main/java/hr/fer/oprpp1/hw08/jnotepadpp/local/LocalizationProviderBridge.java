package hr.fer.oprpp1.hw08.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private ILocalizationProvider providerParent;
	private boolean connected = false;
	private String currentLanguage;
	private ILocalizationListener listener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			LocalizationProviderBridge.this.fire();	
		}
		
	};
	
	public LocalizationProviderBridge(ILocalizationProvider providerParent) {
		this.providerParent = providerParent;
		this.currentLanguage = providerParent.getLanguage();
		providerParent.addLocalizationListener(listener);
		
	}
	@Override
	public String getString(String key) {
		return providerParent.getString(key);
	}

	@Override
	public String getLanguage() {
		if (currentLanguage != providerParent.getLanguage()) 
			currentLanguage = providerParent.getLanguage();
	    return currentLanguage;
	}
	
	public void disconnect() {
		this.connected = false;
		providerParent.removeLocalizationListener(listener);// ?? 
	}
	
	public void connect() {
		if (connected == true) return;
		this.connected = true;
		providerParent.addLocalizationListener(listener); // ??
		if (this.currentLanguage != providerParent.getLanguage()) {
			currentLanguage = providerParent.getLanguage();
			this.fire();
		} 
	}

}
