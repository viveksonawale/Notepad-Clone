import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Notepad implements ActionListener {

	JFrame windows;
	JTextArea ta;
	JScrollPane sp;
	JMenuBar mb;
	JMenu file, edit, format, view, help, zoom, font, theme;
	JMenuItem NNEW, open, save, save_as, print, exit, copy, paste, cut, selectall, undo, redo, about, setback,
			setforeg, autosave, view_help, feed, wordwrap, fontconsolas, fontTimesnewroman, fontFiraCode,
			fontCascadiaCode, fontJetBrainsMono, fontHasklig, fontMonoid, Dark, dracula, wwhite, Red, Blue, Green,hacker;
	String command;
	Boolean Wordwrapon = false;
	Boolean autosaveon = false;

	Function_File_and_Edit f = new Function_File_and_Edit(this);
	Function_Format f1 = new Function_Format(this);
	ImageIcon icon;
	JPopupMenu popmenu = new JPopupMenu();

	public static void main(String[] args) {
		Notepad N = new Notepad();
	}

	Notepad() {
		window();
		textarea();
		iconfuntion();
		Menubar();
		filemenuitems();
		editmenuitems();
		formatmenuitems();
		helpmenuitems();
		viewmenuitems();

		windows.setVisible(true);
	}

	public void window() {
		windows = new JFrame("Notepad");
		windows.setSize(1000, 500);
		// windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.getContentPane().setBackground(Color.white);
		windows.setVisible(true);
		windows.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				handleExit();
			}

			public void handleExit() {
				if (f.isTextModified()) {
					int result = JOptionPane.showConfirmDialog(windows, "Do you want to save changes before exiting?",
							"Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

					if (result == JOptionPane.YES_OPTION) {
						f.File_save();
						System.exit(0);
					} else if (result == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
				} else {
					System.out.println("This is Cancel from EXIT menuitem");
				}
			}
		});

	}

	public void iconfuntion() {
		Image icon = Toolkit.getDefaultToolkit().getImage("D:\\CPP Project\\Notepad_Clone\\src\\icon.png");
		windows.setIconImage(icon);
		icon.equals(true);
	}

	public void textarea() {
		ta = new JTextArea();
		sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setBorder(BorderFactory.createEmptyBorder());
		windows.add(sp);

	}

	public void Menubar() {
		mb = new JMenuBar();
		mb.setBackground(Color.WHITE);
		windows.setJMenuBar(mb);

		file = new JMenu("File");
		file.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mb.add(file);

		edit = new JMenu("Edit");
		edit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mb.add(edit);

		format = new JMenu("Format");
		format.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mb.add(format);

		view = new JMenu("View");
		view.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mb.add(view);

		help = new JMenu("Help");
		help.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mb.add(help);

	}

	public void filemenuitems() {
		NNEW = new JMenuItem("New");
		NNEW.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		NNEW.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		NNEW.addActionListener(this);
		NNEW.setActionCommand("New");
		NNEW.setBackground(Color.WHITE);
		file.add(NNEW);

		open = new JMenuItem("Open");
		open.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		open.addActionListener(this);
		open.setActionCommand("Open");
		open.setBackground(Color.WHITE);
		file.add(open);

		save = new JMenuItem("Save");
		save.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.addActionListener(this);
		save.setActionCommand("Save");
		save.setBackground(Color.WHITE);
		file.add(save);

		save_as = new JMenuItem("Save As");
		save_as.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		save_as.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		save_as.addActionListener(this);
		save_as.setActionCommand("Save As");
		save_as.setBackground(Color.WHITE);
		file.add(save_as);

		print = new JMenuItem("Print");
		print.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		print.addActionListener(this);
		print.setActionCommand("Print");
		print.setBackground(Color.WHITE);
		file.add(print);

		autosave = new JMenuItem("Auto Save: Off");
		autosave.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		autosave.addActionListener(this);
		autosave.setActionCommand("AutoSave");
		autosave.setBackground(Color.WHITE);
		file.add(autosave);

		exit = new JMenuItem("Exit");
		exit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		exit.addActionListener(this);
		exit.setActionCommand("Exit");
		exit.setBackground(Color.WHITE);
		file.add(exit);

	}

	public void editmenuitems() {
		undo = new JMenuItem("Undo");
		undo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		undo.addActionListener(this);
		undo.setActionCommand("Undo");
		undo.setBackground(Color.WHITE);
		edit.add(undo);

		redo = new JMenuItem("Redo");
		redo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		redo.addActionListener(this);
		redo.setActionCommand("Redo");
		redo.setBackground(Color.WHITE);
		edit.add(redo);

		cut = new JMenuItem("Cut");
		cut.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		cut.addActionListener(this);
		cut.setActionCommand("Cut");
		cut.setBackground(Color.WHITE);
		edit.add(cut);

		copy = new JMenuItem("Copy");
		copy.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copy.addActionListener(this);
		copy.setActionCommand("Copy");
		copy.setBackground(Color.WHITE);
		edit.add(copy);

		paste = new JMenuItem("Paste");
		paste.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		paste.addActionListener(this);
		paste.setActionCommand("Paste");
		paste.setBackground(Color.WHITE);
		edit.add(paste);

		selectall = new JMenuItem("Select All");
		selectall.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		selectall.addActionListener(this);
		selectall.setActionCommand("Select All");
		selectall.setBackground(Color.WHITE);
		edit.add(selectall);
	}

	public void formatmenuitems() {

		wordwrap = new JMenuItem("Word Wrap: Off");
		wordwrap.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		wordwrap.addActionListener(this);
		wordwrap.setActionCommand("Word Wrap : Off");
		wordwrap.setBackground(Color.WHITE);
		format.add(wordwrap);

		font = new JMenu("Font");
		font.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font.addActionListener(this);
		font.setActionCommand("Font");
		font.setBackground(Color.WHITE);
		format.add(font);

		fontconsolas = new JMenuItem("Consolas");
		fontconsolas.setFont(new Font("Consolas", Font.PLAIN, 12));
		fontconsolas.addActionListener(this);
		fontconsolas.setActionCommand("Consolas");
		fontconsolas.setBackground(Color.WHITE);
		font.add(fontconsolas);

		fontTimesnewroman = new JMenuItem("Times New Roman");
		fontTimesnewroman.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		fontTimesnewroman.addActionListener(this);
		fontTimesnewroman.setActionCommand("Times New Roman");
		fontTimesnewroman.setBackground(Color.WHITE);
		font.add(fontTimesnewroman);
	}

	public void viewmenuitems() {
		zoom = new JMenu("Zoom");
		zoom.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		zoom.addActionListener(this);
		zoom.setActionCommand("Zoom");
		zoom.setBackground(Color.WHITE);
		view.add(zoom);

		theme = new JMenu("Theme");
		theme.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		theme.addActionListener(this);
		theme.setActionCommand("Theme");
		theme.setBackground(Color.WHITE);
		view.add(theme);

		Dark = new JMenuItem("++Dark++");
		Dark.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		Dark.addActionListener(this);
		Dark.setActionCommand("++Dark++");
		Dark.setBackground(Color.WHITE);
		theme.add(Dark);

		dracula = new JMenuItem("Dracula");
		dracula.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		dracula.addActionListener(this);
		dracula.setActionCommand("Dracula");
		dracula.setBackground(Color.WHITE);
		theme.add(dracula);

		wwhite = new JMenuItem("White");
		wwhite.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		wwhite.addActionListener(this);
		wwhite.setActionCommand("White");
		wwhite.setBackground(Color.WHITE);
		theme.add(wwhite);

		Red = new JMenuItem("Red");
		Red.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		Red.addActionListener(this);
		Red.setActionCommand("Red");
		Red.setBackground(Color.WHITE);
		theme.add(Red);

		Blue = new JMenuItem("Blue");
		Blue.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		Blue.addActionListener(this);
		Blue.setActionCommand("Blue");
		Blue.setBackground(Color.WHITE);
		theme.add(Blue);

		Green = new JMenuItem("Green");
		Green.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		Green.addActionListener(this);
		Green.setActionCommand("Green");
		Green.setBackground(Color.WHITE);
		theme.add(Green);

		hacker = new JMenuItem("Hacker");
		hacker.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		hacker.addActionListener(this);
		hacker.setActionCommand("Hacker");
		hacker.setBackground(Color.WHITE);
		theme.add(hacker);

		setback = new JMenuItem("Set Custom Background");
		setback.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		setback.addActionListener(this);
		setback.setActionCommand("Set Custom Background");
		setback.setBackground(Color.WHITE);
		view.add(setback);

		setforeg = new JMenuItem("Set Font Color");
		setforeg.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		setforeg.addActionListener(this);
		setforeg.setActionCommand("Set Font Color");
		setforeg.setBackground(Color.WHITE);
		view.add(setforeg);
	}

	public void helpmenuitems() {
		view_help = new JMenuItem("View Help");
		view_help.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		view_help.addActionListener(this);
		view_help.setActionCommand("View Help");
		view_help.setBackground(Color.WHITE);
		help.add(view_help);

		feed = new JMenuItem("Send Feedback");
		feed.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		feed.addActionListener(this);
		feed.setActionCommand("Send Feedback");
		feed.setBackground(Color.WHITE);
		help.add(feed);

		about = new JMenuItem("About");
		about.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		about.addActionListener(this);
		about.setActionCommand("About");
		about.setBackground(Color.WHITE);
		help.add(about);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		command = e.getActionCommand();

		switch (command) {

			case "New":
				f.File_new();
				break;
			case "Open":
				f.File_open();
				break;
			case "Save":
				f.File_save();
				break;
			case "Save As":
				f.File_save();
				break;
			case "Print":
				f.File_print();
				break;
			case "AutoSave":
				f.autosave();
				break;
			case "On":

				break;
			case "Exit":
				if (f.isTextModified()) {
					int result = JOptionPane.showConfirmDialog(windows, "Do you want to save changes before exiting?",
							"Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

					if (result == JOptionPane.YES_OPTION) {
						f.File_save();
						System.exit(0);
					} else if (result == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
				} else {
					System.out.println("This is Cancel from EXIT menuitem");
				}
				break;
			case "Undo":
				f.initializeUndoManager();
				f.Edit_undo();
				break;
			case "Redo":
				f.initializeUndoManager();
				f.Edit_redo();
				break;
			case "Cut":
				f.Edit_cut();

				break;
			case "Copy":
				f.Edit_copy();

				break;
			case "Paste":
				f.Edit_paste();

				break;
			case "Select All":
				f.Edit_select_all();

				break;
			case "Word Wrap : Off":
				f1.Format_Wordwrap();
				break;
			case "Consolas":
				break;
			case "Times New Roman":
				break;
			case "Set Font Color":
				f1.view_setforeg();
				break;
			case "Zoom":

				break;
			case "++Dark++":
				ta.setBackground(Color.BLACK);
				ta.setForeground(Color.WHITE);
				ta.setCaretColor(Color.WHITE);
				break;
			case "White":
				ta.setBackground(Color.WHITE);
				ta.setForeground(Color.BLACK);
				break;
			case "Dracula":
				ta.setBackground(Color.DARK_GRAY);
				ta.setForeground(Color.ORANGE);
				ta.setCaretColor(Color.WHITE);
				break;
			case "Red":
				ta.setBackground(Color.RED);
				ta.setForeground(Color.YELLOW);
				ta.setCaretColor(Color.WHITE);
				break;
			case "Blue":
				ta.setBackground(Color.BLUE);
				ta.setForeground(Color.WHITE);
				ta.setCaretColor(Color.WHITE);
				break;
			case "Green":
				ta.setBackground(Color.GREEN);
				ta.setForeground(Color.red);
				ta.setCaretColor(Color.WHITE);
				break;
				case "Hacker":
				ta.setBackground(Color.BLACK);
				ta.setForeground(Color.GREEN);
				ta.setCaretColor(Color.WHITE);
				break;
				
			case "Set Custom Background":
				f1.view_setback();
				break;
			case "Show Line No..":

				break;
			case "View Help":

				break;
			case "Send Feedback":

				break;
			case "About":

				break;
		}
	}
}
