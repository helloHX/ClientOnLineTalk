package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import component.FriendPanel;

public class OnlinePanel extends JPanel {
	private JLabel userNameLabel = new JLabel("某某");
	private JComboBox statusBox=new JComboBox();  
	private FriendPanel friedPanel = new FriendPanel();
	private JScrollPane friendSroll = new JScrollPane(friedPanel);

	public OnlinePanel(){
		this.setLayout(null);
		statusBox=new JComboBox();  
		statusBox.addItem("在线");  
		statusBox.addItem("离线");  
		initialize();
	}
	

	public void initialize(){
		friendSroll.setPreferredSize(new Dimension(400,720));
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
		userNameLabel.setFont(new Font("宋体", Font.BOLD, 50));
		titlePanel.add(userNameLabel,BorderLayout.WEST);
		titlePanel.add(statusBox,BorderLayout.EAST);
		titlePanel.setBounds(0, 0, 400, 80);
		friendSroll.setBounds(0, 80, 400, 720);
		this.add(titlePanel);
		this.add(friendSroll);
	}
	
	

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		OnlinePanel loginPanel = new OnlinePanel();
		frame.add(loginPanel);
		frame.setSize(400, 800);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("OnlineTalk");
		frame.setVisible(true);
	}
}
