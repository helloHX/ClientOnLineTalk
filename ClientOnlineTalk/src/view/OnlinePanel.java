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

	private static final long serialVersionUID = 2905458428510260853L;
	private JLabel userNameLabel = new JLabel();
	private JComboBox<String> statusBox = new JComboBox<String>();
	public static FriendPanel friedPanel = new FriendPanel();
	private JScrollPane friendSroll = new JScrollPane(friedPanel);
	private JButton addFriendButton = new JButton("添加");
	

	public OnlinePanel() {
		this.setPreferredSize(new Dimension(400, 800));
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
		friendSroll.setPreferredSize(new Dimension(400, 650));
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
		JPanel bottomPanel = new JPanel(
				new FlowLayout(FlowLayout.RIGHT, 20, 15));
		bottomPanel.add(addFriendButton);
		userNameLabel.setFont(new Font("宋体", Font.BOLD, 50));
		titlePanel.add(userNameLabel, BorderLayout.WEST);
		titlePanel.add(statusBox, BorderLayout.EAST);
		titlePanel.setBounds(0, 0, 400, 80);
		friendSroll.setBounds(0, 80, 400, 650);
		bottomPanel.setBounds(0, 720, 400, 80);
		addFriendButton.addActionListener(this);
		this.add(titlePanel);
		this.add(friendSroll);
		this.add(bottomPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(addFriendButton == e.getSource()){
			ClientOnlineTalk.addFriend();
		}
	}
}
