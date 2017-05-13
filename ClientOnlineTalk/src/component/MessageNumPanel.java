package component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class MessageNumPanel extends JPanel {
	private static final long serialVersionUID = -488629336001517346L;
	private int num;

	public MessageNumPanel(int num) {
		this.setPreferredSize(new Dimension(20,50));
		this.num = num;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.setFont(new Font("SanSerif", Font.BOLD, 40));
		if (num > 0 && num < 99)
			g.drawString(num + "+",getWidth() /2,getHeight()/2);
		else{
			if(num >= 99)
				g.drawString(num + "+",getWidth() /2,getHeight()/2);
		}
	}

	public void addNum(){
		this.num++;
		this.repaint();
	}
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		this.repaint();
	}

}
