package component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class MyButton extends JButton{
	public MyButton() {
		
	}
	
	public MyButton(String text, Color bgColor,Font font){
		if (text != null) {
			this.setText(text);
		}
		if(bgColor != null){
			this.setBackground(bgColor);
		}
		if(font != null){
			this.setFont(font);
		}
		this.setLayout(null);
		this.setPreferredSize(new Dimension(200,40));
	}
	
	
	
	
}
