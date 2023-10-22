import java.awt.Color;
import java.awt.FileDialog;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class Function_File_and_Edit {

	Notepad N;
	String filename, fileaddress;
	UndoManager undoManager;

	Function_File_and_Edit(Notepad N) {
		this.N = N;
	}

	public void File_new() {
		N.ta.setText("");
		N.windows.setTitle("Untitled");
		N.ta.setBackground(Color.WHITE);
		N.ta.setForeground(Color.BLACK);
		filename = null;
		fileaddress = null;
	}

	public void File_open() {
		FileDialog fd = new FileDialog(N.windows, "Open", FileDialog.LOAD);
		fd.setVisible(true);
		// filename==null then the if block will execute
		if (fd.getFile() != null) {
			filename = fd.getFile();
			fileaddress = fd.getDirectory();
			N.windows.setTitle(filename);
		}
		System.out.println("----- File ----> Open");
		System.out.println("File Address:" + fileaddress + "\n" + "File Name:" + filename);

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileaddress + filename)); // you need the address to
																							// read the File
			N.ta.setText("");

			String line = null;

			while ((line = br.readLine()) != null) {
				N.ta.append(line + "\n");
			}
			System.out.println("File Opened Successfully!");
			br.close();
		} catch (Exception e) {
			System.out.println("File Error![File didn't opened Properly!!!]");
		}
	}

	public void File_save_as() {
		System.out.println("-------File ---> Save");
		FileDialog fd = new FileDialog(N.windows, "Save", FileDialog.SAVE);
		fd.setVisible(true);

		if (fd.getFile() != null) {
			filename = fd.getFile();
			fileaddress = fd.getDirectory();
			N.windows.setTitle(filename);

		}
		try {
			FileWriter fw = new FileWriter(fileaddress + filename);
			fw.write(N.ta.getText());
			fw.close();
			if (N.ta.getText() != null) {
				System.out.println("File Saved Successfully!");
			}
		} catch (Exception e) {
			System.out.println("Error!");
		}
	}

	public void File_save() {
		System.out.println("-------File ---> Save");
		if (filename == null) {
			File_save_as();
		} else {
			try {
				FileWriter fw = new FileWriter(fileaddress + filename);
				fw.write(N.ta.getText());
				fw.close();
				N.windows.setTitle(filename);
				System.out.println("file saved Successfully!");
			} catch (Exception e) {
				System.out.println("Error!");
			}
		}

	}

	public void File_print() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException exc) {
				System.out.println(exc);
			}
		}
	}

	public boolean isTextModified() {
		return true;
	}

	public void autosave() {
		// Pending....
		if(N.autosaveon==false){
			N.autosaveon = true;
			//................
			N.autosave.setText("Auto Save: On");
		}
		else if(N.autosaveon = true){
			N.autosaveon = false;
			N.autosave.setText("Auto Save: Off");
		}
	}

	public void initializeUndoManager() {
		undoManager = new UndoManager();
		N.ta.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
			}
		});
	}

	public void Edit_undo() {
		if (undoManager.canUndo()) {
			undoManager.undo();
		}
	}

	public void Edit_redo() {
		if (undoManager.canRedo()) {
			undoManager.redo();
		}
	}

	public void Edit_copy() {
		N.ta.copy();
	}

	public void Edit_cut() {
		N.ta.cut();
	}

	public void Edit_paste() {
		N.ta.paste();
	}

	public void Edit_select_all() {
		N.ta.selectAll();
	}

}
