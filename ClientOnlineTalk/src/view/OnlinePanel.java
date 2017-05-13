package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.ClientOnlineTalk;
import component.FriendPanel;
import connect.ClientHandler;
import entity.User;

public class OnlinePanel extends JPanel implements ActionListener {
	public static final int OLWIDTH = 400;
	public static final int OLHIGHT = 700;
	
	private static final long serialVersionUID = 2905458428510260853L;
	private JLabel userNameLabel = new JLabel();
	private JComboBox<String> statusBox = new JComboBox<String>();
	public static FriendPanel friedPanel = new FriendPanel();
	private JScrollPane friendSroll = new JScrollPane(friedPanel);
	private JButton addFriendButton = new JButton("添加");
	private JButton deleteFriendButton = new JButton("删除");
	

	public OnlinePanel() {
		this.setPreferredSize(new Dimension(OLWIDTH, OLHIGHT));
		this.setLayout(null);
		statusBox.addItem("在线");
		statusBox.addItem("离线");
		initialize();
	}

	public void initialize() {
		userNameLabel.setText(ClientHandler.user.getUserName());
		if (ClientHandler.user.isUserStatus()) {
			statusBox.setSelectedIndex(0);
		} else {
			statusBox.setSelectedIndex(1);
		}
		friendSroll.setPreferredSize(new Dimension(OLWIDTH, OLHIGHT * 4/5));
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, OLHIGHT / 40, OLWIDTH /20));
		JPanel bottomPanel = new JPanel(
				new FlowLayout(FlowLayout.RIGHT, OLHIGHT / 40, OLWIDTH / 27));
		bottomPanel.add(addFriendButton);
		bottomPanel.add(deleteFriendButton);
		userNameLabel.setFont(new Font("宋体", Font.BOLD, OLHIGHT / 16));
		titlePanel.add(userNameLabel, BorderLayout.WEST);
		titlePanel.add(statusBox, BorderLayout.EAST);
		
		titlePanel.setBounds(0, 0, OLWIDTH, OLHIGHT/10);
		friendSroll.setBounds(0, OLHIGHT/10, OLWIDTH, OLHIGHT * 4/5);
		bottomPanel.setBounds(0, OLHIGHT* 9/10, OLWIDTH, OLHIGHT/10);
		
		addFriendButton.addActionListener(this);
		deleteFriendButton.addActionListener(this);
		this.add(titlePanel);
		this.add(friendSroll);
		this.add(bottomPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(addFriendButton == e.getSource()){
			ClientOnlineTalk.addFriend();
		}
		if(deleteFriendButton == e.getSource()){
			ClientHandler.deleteFriend();
		}
	}
}
