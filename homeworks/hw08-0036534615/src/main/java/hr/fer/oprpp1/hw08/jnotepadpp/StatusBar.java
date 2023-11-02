package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.models.LJLabel;

public class StatusBar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int length;
	private int line;
	private int column;
	private int selection;
	
	private JLabel lengthLabel;
	private JPanel panel;
	
	public StatusBar(int length,int line,int column,int selection,ILocalizationProvider provider) {
		
		this.setLayout(new GridLayout(1,0));
		
		JPanel lengthPanel = new JPanel();
		lengthPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		LJLabel Llabel  = new  LJLabel(provider, "Length");
		
		lengthPanel.add(Llabel);
		
		this.lengthLabel = new JLabel(String.valueOf(length));
		
		lengthPanel.add(lengthLabel);
		
		this.add(lengthPanel);
		
		this.panel = new JPanel();
		
		panel.add(new JLabel("Ln: "+line));
		panel.add(new JLabel("Col: "+column));
		panel.add(new JLabel("Sel: "+selection));
	
		this.add(panel);
		
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		
		JLabel label = new JLabel(timeStamp);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(label);
		
	}
	
	public void setLength(int length) {
		lengthLabel.setText(String.valueOf(length));
	}
	
	public void setLine(int line) {
		JLabel label = (JLabel) this.panel.getComponent(0);
		label.setText("Ln: "+line);	
	}
	
	public void setCol(int col) {
		JLabel label = (JLabel) this.panel.getComponent(1);
		label.setText("Col: "+col);	
	}
	
	public void setSelect(int sel) {
		JLabel label = (JLabel) this.panel.getComponent(2);
		label.setText("Sel: "+sel);	
	}
	

}
