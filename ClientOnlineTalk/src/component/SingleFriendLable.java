package component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import view.OnlinePanel;


public class SingleFriendLable extends JLabel {

	private static final long serialVersionUID = -1294092833930902653L;
	private int num;
	private JLabel friendName = new JLabel();
	private JLabel status = new JLabel();
	private Font nameFont = new Font("宋体", Font.BOLD, OnlinePanel.OLHIGHT /40);
	private Font numFont = new Font("宋体", Font.BOLD, OnlinePanel.OLHIGHT / 40);
	private Font statuFont = new Font("宋体", Font.BOLD, OnlinePanel.OLHIGHT /60);
	public JLabel messageNum = new JLabel("");
	
	public SingleFriendLable() {
		
	}

	public SingleFriendLable(String friendName, boolean status) {
		initialize();
		this.friendName.setText(friendName);
		if (status) {
			this.status.setText("在线");
		} else {
			this.status.setText("离线");
		}
	}

	public void initialize() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
		friendName.setFont(nameFont);
		status.setFont(statuFont);
		messageNum.setFont(numFont);
		status.setForeground(Color.BLUE);
		messageNum.setForeground(Color.RED);
		this.add(friendName);
		this.add(status);
		this.add(messageNum);
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

	public void setStatus(Boolean status) {
		if (status) {
			this.status.setText("在线");
		} else {
			this.status.setText("离线");
		}
	}
	
	public void addNum(){
		this.num++;
		if (num > 0 && num < 99)
			messageNum.setText(num+"");
		else{
			if(num >= 99)
				messageNum.setText(num+"");
			else
				messageNum.setText("");
		}
	}
	public void clearNum(){
		messageNum.setText("");
	}

}
