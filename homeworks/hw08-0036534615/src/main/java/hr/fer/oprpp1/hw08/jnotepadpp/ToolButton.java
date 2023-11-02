package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.JButton;

public class ToolButton extends JButton {

	private static final long serialVersionUID = 1L;

	public ToolButton(Action action) {
		super(action);
		this.setPreferredSize(new Dimension(15,15));
		this.setToolTipText((String) action.getValue(Action.SHORT_DESCRIPTION));
	}
}
