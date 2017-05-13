package component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.ChartFrame;

public class FilePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 7228723348256556867L;
	private File file;
	private JLabel fileNameL = new JLabel();
	private JButton openFileB = new JButton("打开");
	private JButton openDirB = new JButton("打开文件夹");
	
	public FilePanel(File file) {
		this.file = file;
		fileNameL.setHorizontalAlignment(SwingConstants.CENTER);
		fileNameL.setText(file.getName());
		openDirB.addActionListener(this);
		openFileB.addActionListener(this);
		this.setBackground(new Color(124,231,222));
		this.setPreferredSize(new Dimension(ChartFrame.CFWIDTH /20,ChartFrame.CFHIGHT /20));
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		this.add(fileNameL);
		this.add(openFileB);
		this.add(openDirB);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openFileB) {
			try {
				Runtime.getRuntime().exec("cmd /c start " + file.getAbsolutePath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == openDirB){
			try {
				Runtime.getRuntime().exec("cmd /c start " + file.getParent());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
