package component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import connect.ClientHandler;
import app.ClientOnlineTalk;
import entity.User;

public class FriendPanel extends JPanel {
	private List<User> userList = new ArrayList<>();
	private SingleFriendLable[] singleFriendLables;
	private User slectedFriend;
	private int choicedIndex;

	public FriendPanel() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(400, 650));
	}

	public int getChoicedIndex() {
		return choicedIndex;
	}

	public void setChoicedIndex(int choicedIndex) {
		this.choicedIndex = choicedIndex;
	}

	public void initializeFriends(List<User> userList){
		this.userList = userList;
		showFriends();
		this.repaint();
		this.doLayout();
	}
	
	public void showFriends() {
		this.setPreferredSize(new Dimension(400, userList.size() * 90));
		this.removeAll();// 移出已有状态
		LabelListener monitor = new LabelListener();
		singleFriendLables = new SingleFriendLable[userList.size()];
		for (int i = 0; i < userList.size(); i++) {
			singleFriendLables[i] = new SingleFriendLable(userList.get(i)
					.getUserName(), userList.get(i).isUserStatus());
			singleFriendLables[i].setBounds(0, i * 60 + 10, 400, 60);// 设定各个展示内容的位置
			singleFriendLables[i].addMouseListener(monitor);
			this.add(singleFriendLables[i]);
		}
	}

	class LabelListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			for (int i = 0; i < userList.size(); i++) {
				if (e.getSource() == singleFriendLables[i]) {
					slectedFriend = userList.get(i);
					choicedIndex = i;// 保存选中的标签
					//处理被选中后的事件
					ClientOnlineTalk.chart(slectedFriend);
				}
			}
		}

		public void mouseEntered(MouseEvent e) {// 创建鼠标移出的效果
			for (int i = 0; i < userList.size(); i++) {
				if (e.getSource() == singleFriendLables[i]) {
					singleFriendLables[i].setBackground(new Color(229, 243, 251));
					singleFriendLables[i].setBorder(BorderFactory.createLineBorder(
							new Color(224,102, 255), 1));
					singleFriendLables[i].setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		}

		public void mouseExited(MouseEvent e) {
			for (int i = 0; i < userList.size(); i++) {
				if (e.getSource() == singleFriendLables[i]) {
					singleFriendLables[i].setBackground(new Color(238, 238, 238));
					singleFriendLables[i].setBorder(null);
				}
			}
		}
	}
}
