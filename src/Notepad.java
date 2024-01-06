import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Notepad implements ActionListener {

	// GUI Important Elements
	JFrame windows;
	JTextArea ta;
	JScrollPane sp;
	JMenuBar mb;

	// Menu
	JMenu file, edit, format, view, help, zoom, font, theme;

	// File Menu
	JMenuItem NNEW, open, save, save_as, exit, autosave;
	// Edit Menu
	JMenuItem copy, paste, cut, selectall, undo, redo;
	// Help Menu
	JMenuItem about, view_help;
	// View Menu
	JMenuItem Dark, dracula, wwhite, Red, Blue, Green, hacker;
	// Format Menu
	JMenuItem wordwrap, setback, setforeg, fontconsolas, fontTimesnewroman, fontcomicsanms, fontserif, fontcourier,
			fontarial, font8, font10, font11, font12, font14, font16, font18, font20, font22, font24, font26, font28,
			font30, font100;

	String command;
	String aboutMessage;

	Boolean Wordwrapon = false;
	Boolean autosaveon = false;

	// Custom color for Theme
	Color backcolorfordracula = new Color(40, 42, 54);
	Color forgcolorfordracula = new Color(248, 248, 242);

	private Timer autosaveTimer;
	private final int AUTOSAVE_INTERVAL = 60000; // Autosave after every 60 seconds

	// declaring object to call the other files in project such as
	// Function_Format.java and Function_File_and_Edit
	Function_File_and_Edit f = new Function_File_and_Edit(this);
	Function_Format f1 = new Function_Format(this);
	UndoManager U = new UndoManager();
	ImageIcon icon;

	public static void main(String[] args) {
		Notepad N = new Notepad();
	}

	Notepad() {
		// Calling all the methods in consturctor
		window();
		textarea();
		iconfuntion();
		Menubar();
		filemenuitems();
		editmenuitems();
		formatmenuitems();
		helpmenuitems();
		viewmenuitems();
		// declaring default fonts so to prevent the error
		f1.Format_Font(14);
		f1.setFont("Segoe UI");
		f1.Format_Wordwrap();

		// autosave methods
		toggleAutosave();
		autosaveTimer.start();

		windows.setVisible(true);
	}

	public void window() {
		windows = new JFrame("Notepad");
		windows.setSize(900, 500);
		windows.setLocationRelativeTo(null);
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.getContentPane().setBackground(Color.white);
		windows.setVisible(true);
		windows.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (ta != null) {
					handleExit();
				} else if (ta == null) {
					System.exit(0);
				}
			}

			public void handleExit() {
				if (f.isTextModified()) {
					int result = JOptionPane.showConfirmDialog(windows, "Do you want to save changes to " + f.filename,
							"Notepad", JOptionPane.YES_NO_CANCEL_OPTION);

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

	public void toggleAutosave() {
		if (autosaveon) {
			autosaveon = false;
			autosave.setText("Auto Save: Off");
			if (autosaveTimer != null) {
				autosaveTimer.stop();
			}
		} else {
			autosaveon = true;
			autosave.setText("Auto Save: On");
			autosaveTimer = new Timer(AUTOSAVE_INTERVAL, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					f.File_save();
				}
			});
			autosaveTimer.start();
		}
	}

	public void iconfuntion() {
		ImageIcon i = new ImageIcon(ClassLoader.getSystemResource("icon.png"));
		windows.setIconImage(i.getImage());
	}

	public void textarea() {
		ta = new JTextArea();
		ta.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				U.addEdit(e.getEdit());
			}
		});

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

		fontarial = new JMenuItem("Arial");
		fontarial.setFont(new Font("Arial", Font.PLAIN, 12));
		fontarial.addActionListener(this);
		fontarial.setActionCommand("Arial");
		fontarial.setBackground(Color.WHITE);
		font.add(fontarial);

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

		fontcomicsanms = new JMenuItem("Comic Sans MS");
		fontcomicsanms.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		fontcomicsanms.addActionListener(this);
		fontcomicsanms.setActionCommand("Comic Sans MS");
		fontcomicsanms.setBackground(Color.WHITE);
		font.add(fontcomicsanms);

		fontserif = new JMenuItem("Serif");
		fontserif.setFont(new Font("Serif", Font.PLAIN, 12));
		fontserif.addActionListener(this);
		fontserif.setActionCommand("Serif");
		fontserif.setBackground(Color.WHITE);
		font.add(fontserif);

		fontcourier = new JMenuItem("Courier");
		fontcourier.setFont(new Font("Courier", Font.PLAIN, 12));
		fontcourier.addActionListener(this);
		fontcourier.setActionCommand("Courier");
		fontcourier.setBackground(Color.WHITE);
		font.add(fontcourier);

		zoom = new JMenu("Zoom");
		zoom.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		zoom.addActionListener(this);
		zoom.setActionCommand("Zoom");
		zoom.setBackground(Color.WHITE);
		format.add(zoom);

		font8 = new JMenuItem("8");
		font8.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font8.addActionListener(this);
		font8.setActionCommand("10");
		font8.setBackground(Color.WHITE);
		zoom.add(font8);

		font10 = new JMenuItem("10");
		font10.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font10.addActionListener(this);
		font10.setActionCommand("10");
		font10.setBackground(Color.WHITE);
		zoom.add(font10);

		font11 = new JMenuItem("11");
		font11.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font11.addActionListener(this);
		font11.setActionCommand("11");
		font11.setBackground(Color.WHITE);
		zoom.add(font11);

		font12 = new JMenuItem("12");
		font12.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font12.addActionListener(this);
		font12.setActionCommand("12");
		font12.setBackground(Color.WHITE);
		zoom.add(font12);

		font14 = new JMenuItem("14");
		font14.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font14.addActionListener(this);
		font14.setActionCommand("14");
		font14.setBackground(Color.WHITE);
		zoom.add(font14);

		font16 = new JMenuItem("16");
		font16.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font16.addActionListener(this);
		font16.setActionCommand("16");
		font16.setBackground(Color.WHITE);
		zoom.add(font16);

		font18 = new JMenuItem("18");
		font18.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font18.addActionListener(this);
		font18.setActionCommand("18");
		font18.setBackground(Color.WHITE);
		zoom.add(font18);

		font20 = new JMenuItem("20");
		font20.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font20.addActionListener(this);
		font20.setActionCommand("20");
		font20.setBackground(Color.WHITE);
		zoom.add(font20);

		font22 = new JMenuItem("22");
		font22.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font22.addActionListener(this);
		font22.setActionCommand("22");
		font22.setBackground(Color.WHITE);
		zoom.add(font22);

		font24 = new JMenuItem("24");
		font24.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font24.addActionListener(this);
		font24.setActionCommand("24");
		font24.setBackground(Color.WHITE);
		zoom.add(font24);

		font26 = new JMenuItem("26");
		font26.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font26.addActionListener(this);
		font26.setActionCommand("26");
		font26.setBackground(Color.WHITE);
		zoom.add(font26);

		font28 = new JMenuItem("28");
		font28.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font28.addActionListener(this);
		font28.setActionCommand("28");
		font28.setBackground(Color.WHITE);
		zoom.add(font28);

		font28 = new JMenuItem("28");
		font28.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font28.addActionListener(this);
		font28.setActionCommand("28");
		font28.setBackground(Color.WHITE);
		zoom.add(font28);

		font30 = new JMenuItem("30");
		font30.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font30.addActionListener(this);
		font30.setActionCommand("30");
		font30.setBackground(Color.WHITE);
		zoom.add(font30);

		font100 = new JMenuItem("100");
		font100.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		font100.addActionListener(this);
		font100.setActionCommand("100");
		font100.setBackground(Color.WHITE);
		zoom.add(font100);
	}

	public void viewmenuitems() {

		theme = new JMenu("Theme");
		theme.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		theme.addActionListener(this);
		theme.setActionCommand("Theme");
		theme.setBackground(Color.WHITE);
		view.add(theme);

		Dark = new JMenuItem("Dark++");
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

		about = new JMenuItem("About");
		about.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		about.addActionListener(this);
		about.setActionCommand("About");
		about.setBackground(Color.WHITE);
		help.add(about);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Switch case to Trigger THE FUNCTION
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

			case "AutoSave":
				toggleAutosave();
				break;
			case "On":

				break;
			case "Exit":
				if (f.isTextModified()) {
					int result = JOptionPane.showConfirmDialog(windows, "Do you want to save changes before exiting?",
							"Notepad", JOptionPane.YES_NO_CANCEL_OPTION);

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
				f.Edit_undo();
				break;
			case "Redo":
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
			case "Set Font Color":
				f1.view_setforeg();
				break;
			case "Zoom":
				break;
			case "8":
				f1.Format_Font(8);
				break;
			case "10":
				f1.Format_Font(10);
				break;
			case "11":
				f1.Format_Font(11);
				break;
			case "12":
				f1.Format_Font(12);
				break;
			case "14":
				f1.Format_Font(14);
				break;
			case "16":
				f1.Format_Font(16);
				break;
			case "18":
				f1.Format_Font(18);
				break;
			case "20":
				f1.Format_Font(20);
				break;
			case "22":
				f1.Format_Font(22);
				break;
			case "24":
				f1.Format_Font(24);
				break;
			case "26":
				f1.Format_Font(26);
				break;
			case "28":
				f1.Format_Font(28);
				break;
			case "30":
				f1.Format_Font(30);
				break;
			case "100":
				f1.Format_Font(100);
				break;
			case "Arial":
				f1.setFont(command);
				break;
			case "Comic Sans MS":
				f1.setFont(command);
				break;
			case "Consolas":
				f1.setFont(command);
				break;
			case "Times New Roman":
				f1.setFont("Times New Roman");
				break;
			case "Serif":
				f1.setFont(command);
				break;
			case "Courier":
				f1.setFont(command);
				break;

			case "++Dark++":
				ta.setBackground(Color.BLACK);
				ta.setForeground(Color.WHITE);
				ta.setCaretColor(Color.WHITE);
				break;
			case "White":
				ta.setBackground(Color.WHITE);
				ta.setForeground(Color.BLACK);
				ta.setCaretColor(Color.BLACK);
				break;
			case "Dracula":
				ta.setBackground(backcolorfordracula);
				ta.setForeground(forgcolorfordracula);
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
			case "View Help":
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/Vivek-Sonawale"));
				} catch (IOException | URISyntaxException ex) {
					ex.printStackTrace();
				}
				break;
			case "About":
				String aboutMessage = "Notepad Clone\n\n"
						+ "Version: 2.0\n"
						+ "Developer: Vivek Sonawale\n"
						+ "Description: This is Notepad Clone, using Java.\n"
						+ "Copyright © 2023  Notepad_Clone \n";
						JOptionPane.showMessageDialog(null,aboutMessage,"About",JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
