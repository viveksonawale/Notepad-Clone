import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Function_Format {
	Notepad N;
	Font arial, consolas, timesnewroman;
	String selectfont;
	Font selectedfont;
	Color c2;

	Function_Format(Notepad N) {
		this.N = N;
	}

	public void Format_Wordwrap() {
		if (N.Wordwrapon == false) {
			N.Wordwrapon = true;
			N.ta.setLineWrap(true);
			N.ta.setWrapStyleWord(true);
			N.wordwrap.setText("Word Wrap: On");
		} else if (N.Wordwrapon == true) {
			N.Wordwrapon = false;
			N.ta.setLineWrap(false);
			N.ta.setWrapStyleWord(false);
			N.wordwrap.setText("Word Wrap: Off");
		}
	}

	public void Format_Font(int zoom) {
		consolas = new Font("Consolas", Font.PLAIN, 23);
		timesnewroman = new Font("Times New Roman", Font.PLAIN, 23);

	}

	public void Format_theme(){
		switch(N.command){
			case "++Dark++":
			N.ta.setBackground(Color.BLACK);
			N.ta.setForeground(Color.WHITE);
			break;
			case "White":
			N.ta.setBackground(Color.WHITE);
			N.ta.setForeground(Color.BLACK);
			break;
			case "Dracula":
			N.ta.setBackground(Color.GRAY);
			N.ta.setForeground(Color.PINK);
			break;
			case "Red":
			N.ta.setBackground(Color.RED);
			N.ta.setForeground(Color.YELLOW);
			break;
			case "Blue":
			N.ta.setBackground(Color.BLUE);
			N.ta.setForeground(Color.WHITE);
			break;
			case "Green":
			N.ta.setBackground(Color.GREEN);
			N.ta.setForeground(Color.red);
			break;
		}
		
	}

	public void view_setback() {
		JColorChooser ccc = new JColorChooser();
		Color c2 = ccc.showDialog(N.windows, "Select Color:", Color.WHITE);
		N.ta.setBackground(c2);
	}

	public void view_setforeg() {
		JColorChooser cc = new JColorChooser();
		Color c1 = cc.showDialog(N.windows, "Select Color:", Color.WHITE);
		N.ta.setForeground(c1);
	}
}
