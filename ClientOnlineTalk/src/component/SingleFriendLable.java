package component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.LoginPanel;

public class SingleFriendLable extends JLabel {
	private JLabel friendName = new JLabel();
	private JLabel status = new JLabel();
	private Font nameFont = new Font("宋体", Font.BOLD, 24);
	private Font statuFont = new Font("宋体", Font.BOLD, 15);
	public SingleFriendLable(){
		
	}
	
	public SingleFriendLable(String friendName,boolean status){
		initialize();
		this.friendName.setText(friendName);
		if (status) {
			this.status.setText("在线");
		}else{
			this.status.setText("离线");
		}
	}
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		SingleFriendLable loginPanel = new SingleFriendLable("某某",true);
//		frame.add(loginPanel);
//		frame.setSize(400, 600);
//		frame.setResizable(false);
//		frame.setLocationRelativeTo(null);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setTitle("OnlineTalk");
//		frame.setVisible(true);
//	}

	public void initialize(){
		this.setLayout(new FlowLayout(FlowLayout.LEFT,20,10));
		friendName.setFont(nameFont);
		status.setFont(statuFont);
		status.setForeground(Color.BLUE);
		this.add(friendName,BorderLayout.WEST);
		this.add(status,BorderLayout.EAST);
	}

	public JLabel getFriendName() {
		return friendName;
	}

	public void setFriendName(JLabel friendName) {
		this.friendName = friendName;
	}

	public JLabel getStatus() {
		return status;
	}

	public void setStatus(JLabel status) {
		this.status = status;
	}
	
	
	
}
