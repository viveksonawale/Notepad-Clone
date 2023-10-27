import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Function_Format {
	Notepad N;
	Font arial, consolas, timesnewroman, comicsanms, serif, courier;
	String selectfont = "";
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

	public void Format_Font(int fontsize) {
		arial = new Font("Arial", Font.PLAIN, fontsize);
		consolas = new Font("Consolas", Font.PLAIN, fontsize);
		timesnewroman = new Font("Times New Roman", Font.PLAIN, fontsize);
		comicsanms = new Font("Comic Sans MS", Font.PLAIN, fontsize);
		serif = new Font("Serif", Font.PLAIN, fontsize);
		courier = new Font("Courier", Font.PLAIN, fontsize);

		setFont(selectfont);
	}

	public void setFont(String font) {
		selectfont = font;
		switch (selectfont) {
			case "Arial":
				N.ta.setFont(arial);
				break;
			case "Comic Sans MS":
				N.ta.setFont(comicsanms);
				break;
			case "Consolas":
				N.ta.setFont(consolas);
				break;
			case "Times new Roman":
				N.ta.setFont(timesnewroman);
				break;
			case "Serif":
				N.ta.setFont(serif);
				break;
			case "Courier":
				N.ta.setFont(courier);
				break;
		}
	}

	public void Format_theme() {

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
