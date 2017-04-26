package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import util.Util;
import component.MyButton;
import connect.ClientHandler;
import entity.User;

public class LoginPanel extends JPanel implements ActionListener, MouseListener {
	private JTextField idField = new JTextField();
	private JPasswordField pwdField = new JPasswordField();
	private MyButton loginButton = new MyButton("登陆", new Color(224, 102, 255),
			new Font("Courier", Font.BOLD, 18));
	private JLabel registelabel = new JLabel("注册");
	private JPanel formPanel = new JPanel();
	private ImageIcon ImageIcon = new ImageIcon("image/bgLogin.png");
	private Image image = ImageIcon.getImage();
	private MatteBorder border = new MatteBorder(2, 2, 2, 2, new Color(224,
			102, 255));

	public LoginPanel() {
		this.setPreferredSize(new Dimension(400, 600));
		initialize();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	public void initialize() {
		this.setLayout(null);
		formPanel.setLayout(null);
		idField.setBorder(border);
		pwdField.setBorder(border);
		idField.setBounds(80, 20, 240, 40);
		pwdField.setBounds(80, 80, 240, 40);
		loginButton.setBounds(100, 180, 200, 40);
		registelabel.setPreferredSize(new Dimension(100, 60));
		registelabel.setFont(new Font("宋体", Font.BOLD, 18));
		registelabel.setForeground(Color.BLUE);
		registelabel.setBounds(180, 230, 100, 60);

		formPanel.add(idField);
		formPanel.add(pwdField);
		formPanel.add(registelabel);
		formPanel.add(loginButton);
		formPanel.setBounds(0, 100, 400, 300);
		formPanel.setOpaque(false);
		this.add(formPanel);

		loginButton.addActionListener(this);
		registelabel.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			String id = idField.getText();
			String password = pwdField.getText();
			Thread connect = new Thread(new ClientHandler(id, password));
			connect.start();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			Util.openBrowse();
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
