package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;

import hr.fer.oprpp1.hw08.jnotepadpp.document.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.document.models.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.document.models.DefaultSingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.document.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

public class JNotepadPP  extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private DefaultMultipleDocumentModel model;
	private DocumentListener listener = new DocumentListener() {


		@Override
		public void insertUpdate(DocumentEvent e) {
			markIcon();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			markIcon();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			markIcon();
		}
		
		private void markIcon() {
			DefaultSingleDocumentModel model = (DefaultSingleDocumentModel) JNotepadPP.this.model.getCurrentDocument();
			if (!model.isModified()) {
				String title = JNotepadPP.this.getTitle();
			    JNotepadPP.this.setTitle("*"+title);
			    
			    int index = JNotepadPP.this.model.getSelectedIndex();
			    ButtonTabPane pane = (ButtonTabPane) JNotepadPP.this.model.getTabComponentAt(index);
			    pane.setTabTitle("*"+pane.getTabTitle());
			}
			model.setModified(true);	
		}
	};
	
	private CaretListener caretListener;
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(),this);
	private ILocalizationListener Llistener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			
			if (JNotepadPP.this.model.getNumberOfDocuments() > 0) {
				System.out.println("uso");
				Path path = JNotepadPP.this.model.getCurrentDocument().getFilePath();
				if (path != null) return;
				String title = flp.getString("unnamed");
			    String Ltitle = title;
			    if (JNotepadPP.this.model.getCurrentDocument().isModified()) {
			    	Ltitle = "*"+title+"- JNotepad++";
			    }
			    JNotepadPP.this.setTitle(Ltitle);
			}
			return;
		}
		
	};
	
	public JNotepadPP() {
		super();
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setTitle("JNotepad++");
		this.setSize(600, 600);
		this.setLocation(20,20);
		
		this.model = new DefaultMultipleDocumentModel(flp);
		
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				ModifiedDocumentCheck();	
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {

				// TODO Auto-generated method stub
				
			}

		});
		initGUI();
		
		flp.addLocalizationListener(Llistener);
	}
	
	public void initGUI() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		
		cp.add(model);
		
	          
		model.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = model.getSelectedIndex();
				if (index == -1) {
					JNotepadPP.this.setTitle("JNotepad++");
					return;
				}
				SingleDocumentModel currentDocument = model.getDocument(index);
				Path path = currentDocument.getFilePath();
				String absolutePath = "("+flp.getString("unnamed")+")";
				if (path != null) {
					absolutePath = path.toFile().getAbsolutePath();			
				}
				JNotepadPP.this.setTitle(absolutePath+" - JNotepad++");
				JNotepadPP.this.model.setToolTipTextAt(index, absolutePath);
				model.setCurrentDocument(currentDocument);
				
		        bar.setLength(currentDocument.getTextComponent().getDocument().getLength());
		        bar.setLine(0);
		        bar.setCol(0);
		        bar.setSelect(0);
			}
			
		});
		
		createActions();
		createMenus();
		createToolBar();
		createStatusBar();
		
		JButton button = new JButton(createDocumentAction);
		button.setVisible(false);
		button.doClick();
	}
	
	private StatusBar bar;
	
	private Action openDocumentAction = new LocalizableAction("Open",flp,"Open_ToolTip") {


		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("Open_File"));
			if (fc.showOpenDialog(JNotepadPP.this)!= JFileChooser.APPROVE_OPTION) {
				return;
			}
			File file = fc.getSelectedFile();
			Path path = file.toPath();
			DefaultMultipleDocumentModel copymodel = JNotepadPP.this.model;
			if (copymodel.loadDocument(path) == null) return;
			JTextArea textcmp = copymodel.getCurrentDocument().getTextComponent();
			JScrollPane cmp = new JScrollPane(textcmp);
			ButtonTabPane buttonPane = new ButtonTabPane(JNotepadPP.this.model,file.getName(),flp);
			
			copymodel.add(file.getName(),cmp);
			int index = copymodel.indexOfComponent(cmp);
			copymodel.setTabComponentAt(index, buttonPane);
			copymodel.setToolTipTextAt(index, path.toAbsolutePath().toString());
			
			copymodel.setSelectedIndex(copymodel.getNumberOfDocuments()-1);
			JNotepadPP.this.setTitle(file.getAbsolutePath()+" - JNotepad++");

			textcmp.getDocument().addDocumentListener(listener);
			
			textcmp.addCaretListener(caretListener);
		}
		
	};
	
	private Action createDocumentAction = new LocalizableAction("New",flp,"New_ToolTip") {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMultipleDocumentModel copymodel = JNotepadPP.this.model;
			model.createNewDocument();
			JComponent cmp = new JScrollPane(copymodel.getCurrentDocument().getTextComponent());
			copymodel.add(cmp);
				
			int index = copymodel.indexOfComponent(cmp);
			ButtonTabPane pane = new ButtonTabPane(JNotepadPP.this.model,"unnamed",flp);
			copymodel.setTabComponentAt(index, pane);
			copymodel.setToolTipTextAt(index, flp.getString("unnamed"));
			copymodel.setSelectedIndex(copymodel.getNumberOfDocuments()-1);
			JNotepadPP.this.setTitle("("+flp.getString("unnamed")+")"+" - JNotepad++");
			
			JTextArea textcmp = copymodel.getCurrentDocument().getTextComponent();
			textcmp.getDocument().addDocumentListener(listener);
			textcmp.addCaretListener(caretListener);
		}
		
	};
	
	private Action saveDocumentAction = new LocalizableAction("Save",flp,"Save_ToolTip") {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
		   DefaultMultipleDocumentModel copymodel = JNotepadPP.this.model;
		   SingleDocumentModel document = copymodel.getCurrentDocument();
		   if (document.getFilePath() == null) return;
		   copymodel.saveDocument(document, null);
		   
		    String newTitle = JNotepadPP.this.getTitle().substring(1,JNotepadPP.this.getTitle().length()-1);
		    JNotepadPP.this.setTitle(newTitle);
		   
		    int index = copymodel.getSelectedIndex();
		    ButtonTabPane pane = (ButtonTabPane) JNotepadPP.this.model.getTabComponentAt(index);
		    pane.setTabTitle(pane.getTabTitle().substring(1,pane.getTabTitle().length()));
		}
		
	};
	
	private Action saveAsDocumentAction = new LocalizableAction("SaveAs",flp,"SaveAs_ToolTip") {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("SaveAs"));
			if (fc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
							JNotepadPP.this,
							flp.getString("Save_Error_Nothing_Saved"),
							flp.getString("Error"),
							JOptionPane.ERROR_MESSAGE
						);
			}
			File file = fc.getSelectedFile();
			if (file == null) return;
			int option = -1;
			boolean exist = file.exists();
			if (exist) {
				option = JOptionPane.showConfirmDialog(
						JNotepadPP.this,
						file.getAbsolutePath()+" "+flp.getString("File_Already_Exsits_Warning"),
						flp.getString("Warning"),
						JOptionPane.OK_CANCEL_OPTION);
			}
			if (option == JOptionPane.OK_OPTION || !exist) {
				Path savePath = file.toPath();
			
				DefaultMultipleDocumentModel copymodel = JNotepadPP.this.model;
				
				if (copymodel.getCurrentDocument().getFilePath() == null)
					flp.removeLocalizationListener(copymodel.getLocListenerOfCurrentDocument().getListener());
				
				copymodel.saveDocument(copymodel.getCurrentDocument(), savePath);
				
				int index = copymodel.getSelectedIndex();
				ButtonTabPane tabPane = (ButtonTabPane) copymodel.getTabComponentAt(index);
				tabPane.setTabTitle(file.getName());
				JNotepadPP.this.setTitle(savePath.toAbsolutePath().toString()+" - JNotepad++");
			} else return;
			
		}
		
	};
	
	private Action cutTextAction = new LocalizableAction("Cut",flp,"Cut_ToolTip") {

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = JNotepadPP.this.model.getCurrentDocument().getTextComponent();
			editor.cut();	
		}
		
	};
	
	
	private Action copyTextAction = new LocalizableAction("Copy",flp,"Copy_ToolTip") {
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = JNotepadPP.this.model.getCurrentDocument().getTextComponent();
			editor.copy();	
		}
		
	};
	
	
	private Action pasteTextAction = new LocalizableAction("Paste",flp,"Paste_ToolTip") {

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = JNotepadPP.this.model.getCurrentDocument().getTextComponent();
			editor.paste();		
		}
		
	};

	
	private Action exitAction = new LocalizableAction("Exit",flp,"Exit_ToolTip") {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
		       JNotepadPP.this.ModifiedDocumentCheck();
		}
		
	};
	
	private Action HrLanguageAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.akcija("hr");	
		}
	};
	private Action EnLanguageAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.akcija("en");	
		}
	};
	private Action DeLanguageAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.akcija("de");	
		}
	};
	
	private void akcija(String language) {
		LocalizationProvider.getInstance().setLanguage(language);
	}
	
	private void createActions() {
		openDocumentAction.putValue(
				Action.NAME, 
				"Open");
		openDocumentAction.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_O
				);
		openDocumentAction.putValue(
				Action.SHORT_DESCRIPTION,
				flp.getString("Open_ToolTip")
				);
		
		createDocumentAction.putValue(
				Action.NAME,
				"New"
				);
		createDocumentAction.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control N")
				);
		createDocumentAction.putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_N
				);
		createDocumentAction.putValue(
				Action.SHORT_DESCRIPTION,
				flp.getString("New_ToolTip")
				);
		
		saveDocumentAction.putValue(
				Action.NAME,
				"Save"
				);
		saveDocumentAction.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control S")
				);
		saveDocumentAction.putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_S
				);
		saveDocumentAction.putValue(
				Action.SHORT_DESCRIPTION,
				flp.getString("Save_ToolTip")
				);
		
		saveAsDocumentAction.putValue(
				Action.NAME,
				"Save As");
		saveAsDocumentAction.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control alt S")
				);
		saveAsDocumentAction.putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_0
				);
		saveAsDocumentAction.putValue(
				Action.SHORT_DESCRIPTION,
				flp.getString("SaveAs_ToolTip")
				);
		
		cutTextAction.putValue(
				Action.NAME,
				"Cut"
				);
		cutTextAction.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control X")
				);
		cutTextAction.putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_X
				);
		cutTextAction.putValue(
				Action.SHORT_DESCRIPTION,
				flp.getString("Cut_ToolTip")
				);
		
		copyTextAction.putValue(
				Action.NAME,
				"Copy"
				);
		copyTextAction.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control C")
				);
		copyTextAction.putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_C
				);
		copyTextAction.putValue(
				Action.SHORT_DESCRIPTION,
				flp.getString("Copy_ToolTip")
				);
		
		pasteTextAction.putValue(
				Action.NAME,
				"Paste"
				);
		pasteTextAction.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control V")
				);
		pasteTextAction.putValue(
				Action.MNEMONIC_KEY,
				KeyEvent.VK_V
				);
		pasteTextAction.putValue(
				Action.SHORT_DESCRIPTION,
				flp.getString("Paste_ToolTip")
				);	
		exitAction.putValue(
				Action.NAME,
				"Exit"
				);
		exitAction.putValue(
				Action.SHORT_DESCRIPTION,
				flp.getString("Exit_ToolTip")
				);
		
		HrLanguageAction.putValue(
				Action.NAME,
				"hr"
				);
		
		EnLanguageAction.putValue(
				Action.NAME,
				"en"
				);
		
		DeLanguageAction.putValue(
				Action.NAME,
				"de"
				);
	}
	
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
	
		
		JMenu fileMenu = new JMenu(new LocalizableAction("File",flp,"File") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		fileMenu.add(new JMenuItem(createDocumentAction));
		fileMenu.add(new JSeparator());
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JSeparator());
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JSeparator());
		fileMenu.add(new JMenuItem(exitAction));
		
		
		JMenu editMenu = new JMenu(new LocalizableAction("Edit",flp,"Edit") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		editMenu.add(new JMenuItem(cutTextAction));
		editMenu.add(new JSeparator());
		editMenu.add(new JMenuItem(copyTextAction));
		editMenu.add(new JSeparator());
		editMenu.add(new JMenuItem(pasteTextAction));
		
		
		JMenu ToolsMenu = new JMenu(new LocalizableAction("Tools",flp,"Tools") {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JMenu ChangCaseMenu = new JMenu("Change Case");
		
		ChangCaseMenu.add(new JMenuItem("Upper Case"));
		ChangCaseMenu.add(new JMenuItem("Lower Case"));
		ChangCaseMenu.add(new JMenuItem("Change Case"));
		
		ToolsMenu.add(ChangCaseMenu);
		ToolsMenu.add(new JMenuItem("Unique"));
		
		
		
		JMenu LanguageMenu = new JMenu(new LocalizableAction("Languages",flp,"Languages") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
			}
		});
		LanguageMenu.add(new JMenuItem(HrLanguageAction));
		LanguageMenu.add(new JMenuItem(EnLanguageAction));
		LanguageMenu.add(new JMenuItem(DeLanguageAction));
		
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		//menuBar.add(ToolsMenu);
		
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(LanguageMenu);
		
		
		
		this.setJMenuBar(menuBar);
	}
	
	private void createToolBar() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);
		
		toolBar.add(new ToolButton(createDocumentAction));
		toolBar.addSeparator(new Dimension(5,5));
		toolBar.add(new ToolButton(openDocumentAction));
		toolBar.addSeparator(new Dimension(5,5));
		toolBar.add(new ToolButton(saveDocumentAction));
		toolBar.add(new ToolButton(saveAsDocumentAction));
		toolBar.addSeparator(new Dimension(10,5));
		toolBar.add(new ToolButton(cutTextAction));
		toolBar.add(new ToolButton(copyTextAction));
		toolBar.add(new ToolButton(pasteTextAction));
		
		this.getContentPane().add(toolBar,BorderLayout.PAGE_START);
	}
	
	private void createStatusBar() {
		
		this.getContentPane().add(new StatusBar(0,0,0,0,flp),BorderLayout.PAGE_END);
		this.bar = (StatusBar) JNotepadPP.this.getContentPane().getComponent(2);
		this.caretListener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea variable = (JTextArea) e.getSource();
				//System.out.println(variable.getDocument().getLength());
			    int line = 1;
			    int col = 0;
			     
			    try {
			    	int caretpos = variable.getCaretPosition();
					line = variable.getLineOfOffset(caretpos);
					col =  caretpos - variable.getLineStartOffset(line);
					
					line+=1;
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
				}
				
			    bar.setLength(variable.getDocument().getLength());
			    bar.setLine(line);
			    bar.setCol(col);
			    bar.setSelect(Math.abs(e.getDot()-e.getMark()));
			}
			
		};
		
	}
	private void ModifiedDocumentCheck() {
		DefaultMultipleDocumentModel Model = JNotepadPP.this.model;
		int option = -1;
		
		for (SingleDocumentModel doc : Model) {	
			if (doc.isModified()) {
				Path path = doc.getFilePath();
				String ispis;
				if (path == null) {
					ispis = "("+flp.getString("unnamed")+")";
				} else {
					ispis = path.toAbsolutePath().toString();
				}
				option = JOptionPane.showConfirmDialog(
						JNotepadPP.this,
						String.format(flp.getString("Save_Warning_File_Modified"), ispis),
						flp.getString("Warning"),
						JOptionPane.YES_NO_CANCEL_OPTION
						);
			}
			if (option == JOptionPane.NO_OPTION) {
				Model.closeDocument(doc);
				continue;
			} else if (option == JOptionPane.YES_OPTION) {
				if (doc.getFilePath() == null) {
					Model.setCurrentDocument(doc);
					JNotepadPP.this.saveAsDocumentAction.actionPerformed(null);
				} else {
					Model.saveDocument(doc, null);
				}
			}
			else {
				break;
			}
			
		}
		if (option != JOptionPane.CANCEL_OPTION) {
			dispose();
		}
	}
	
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeAndWait(() -> {
			JNotepadPP notepad = new JNotepadPP();
			notepad.setVisible(true);
		});
	}
	
}

