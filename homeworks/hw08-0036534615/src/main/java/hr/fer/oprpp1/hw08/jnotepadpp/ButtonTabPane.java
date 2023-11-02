package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import hr.fer.oprpp1.hw08.jnotepadpp.document.models.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

public class ButtonTabPane extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel pane;
	private JLabel label;
	private ILocalizationListener listener;
	private ILocalizationProvider provider;
	private String title;
	
	public ButtonTabPane(DefaultMultipleDocumentModel pane,String title,ILocalizationProvider provider) {
		this.provider = provider;
		this.pane = pane;
		this.title = title;
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
	    this.setOpaque(false);
	    
	    if (title.equals("unnamed")) 
	    	title = provider.getString(title);
	    
	    this.label = new JLabel(title);
	    this.add(label);
	    label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
	    
	    ImageIcon icon = this.getIcon(false);
	    label.setIcon(icon);
	    
	    JButton button = new JButton("x");
	    button.setForeground(Color.RED);
	    button.setPreferredSize(new Dimension(12,14));
	    button.setUI(new BasicButtonUI());
	    button.setMargin(new Insets(0, -1, 0, 0));
	    button.setToolTipText("close this tab");
	    
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					int index = pane.indexOfTabComponent(ButtonTabPane.this);
					if (index != -1) {
						pane.closeDocument(pane.getDocument(index));
						pane.remove(index);
					}
			}	
		});
		
		this.add(button);
		
		if (!title.equals("unnamed")) return;
		
		this.listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				try {
				String title = provider.getString("unnamed");
				String Ltitle = title;
				if (pane.getCurrentDocument().isModified()) {
					Ltitle = "*"+title;
				}
				ButtonTabPane.this.setTabTitle(Ltitle);
				int index = pane.getIndexOfTabComponent(ButtonTabPane.this);
				
				if (index == -1) return;
				pane.setToolTipTextAt(index,Ltitle);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		
		provider.addLocalizationListener(listener);	
	}
	
	public void  setTabTitle(String title) 
	{
		if (title == null) throw new NullPointerException();
		this.label.setText(title);
	}
	
	private ImageIcon getIcon(boolean status) {
		String file = "";
		if (!status) file = "disk.png";
		else file = "diskette.png";
		
		InputStream is = this.getClass().getResourceAsStream("icons/"+file);
		if (is == null) throw new NullPointerException("Stream is null");
		
		byte[] bytes;
		try{
			bytes = is.readAllBytes();
		} catch(Exception ex) {
			System.out.println("Poslo je sve po zlu");
			return null;
		}
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ImageIcon icon = new ImageIcon(bytes);
		Image image = icon.getImage();
		
	    return new ImageIcon(image.getScaledInstance(15,15,Image.SCALE_SMOOTH));
	}
	
	public void setIconBasedOnStatus(boolean status) {
		this.label.setIcon(this.getIcon(status));
	}
	
	public ILocalizationListener getListener() {
		return this.listener;
	}
	
	public String getTabTitle() {
		return label.getText();
	}
	
	
	
	
	
	
	
	
}
