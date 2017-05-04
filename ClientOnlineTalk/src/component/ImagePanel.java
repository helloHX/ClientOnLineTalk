package component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements MouseListener{
	private static final long serialVersionUID = -2517780360475790202L;
	
	private Image image;
	private String imgPath;
	public ImagePanel(String path){
		this.imgPath = path;
		this.setPreferredSize(new Dimension(300,300));
		ImageIcon img = new ImageIcon(path);
		this.setBackground(Color.white);
		this.image = img.getImage();
		this.addMouseListener(this);
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, 300,300, this);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			try {
				Runtime.getRuntime().exec("cmd /c start " + imgPath);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
