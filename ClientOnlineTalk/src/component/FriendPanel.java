package component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import view.OnlinePanel;
import app.ClientOnlineTalk;
import entity.User;

public class FriendPanel extends JPanel {
	private static final long serialVersionUID = -8213598814010616897L;
	private static List<User> userList = new ArrayList<>();
	private static SingleFriendLable[] singleFriendLables;
	public static User selectedFriend;
	private int choicedIndex = -1;

	public FriendPanel() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(OnlinePanel.OLWIDTH,
				OnlinePanel.OLHIGHT * 4 / 5));
	}

	public int getChoicedIndex() {
		return choicedIndex;
	}

	public void setChoicedIndex(int choicedIndex) {
		this.choicedIndex = choicedIndex;
	}

	public void initializeFriends(List<User> userList) {
		FriendPanel.userList = userList;
		showFriends();
		this.repaint();
		this.doLayout();
	}

	public static SingleFriendLable getFriendLable(String userID) {
		if (userList.size() == 0) {
			System.out.println("userList.size() == 0");
		}
		System.out.println("userList.size() == " + userList.size());
		for (int i = 0; i < userList.size(); i++) {
			System.out.println("userList.get(i).getUserID() = "
					+ userList.get(i).getUserID() + " userID = " + userID);
			if (userID.equals(userList.get(i).getUserID()))
				return singleFriendLables[i];
		}
		return null;
	}

	public void changeFriendState(String friendId, boolean state) {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getUserID().endsWith(friendId)) {
				userList.get(i).setUserStatus(state);
				singleFriendLables[i].setStatus(state);
			}
		}
	}

	public void showFriends() {
		this.setPreferredSize(new Dimension(OnlinePanel.OLWIDTH, userList
				.size() * OnlinePanel.OLHIGHT / 10));
		this.removeAll();// 移出已有状态
		LabelListener monitor = new LabelListener();
		singleFriendLables = new SingleFriendLable[userList.size()];
		for (int i = 0; i < userList.size(); i++) {
			singleFriendLables[i] = new SingleFriendLable(userList.get(i)
					.getUserName(), userList.get(i).isUserStatus());
			singleFriendLables[i].setBounds(0, i * OnlinePanel.OLHIGHT * 6 / 80
					+ 10, OnlinePanel.OLWIDTH, OnlinePanel.OLHIGHT * 6 / 80);// 设定各个展示内容的高度
			singleFriendLables[i].addMouseListener(monitor);
			this.add(singleFriendLables[i]);
		}
	}

	class LabelListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (choicedIndex < userList.size() && choicedIndex >= 0)
				singleFriendLables[choicedIndex].setBorder(null);// 将上次选中过的复位
			for (int i = 0; i < userList.size(); i++) {
				if (e.getSource() == singleFriendLables[i]) {
					selectedFriend = userList.get(i);
					choicedIndex = i;// 保存选中的标签
					singleFriendLables[choicedIndex].setBorder(BorderFactory
							.createLineBorder(new Color(62, 0, 223), 2));
					if (e.getClickCount() == 2) {
						ClientOnlineTalk.chart(selectedFriend);
						SingleFriendLable friendLable = FriendPanel
								.getFriendLable(userList.get(i).getUserID());
						friendLable.clearNum();
					}
				}
			}
		}

		public void mouseEntered(MouseEvent e) {// 创建鼠标移出的效果
			for (int i = 0; i < userList.size(); i++) {
				if (e.getSource() == singleFriendLables[i]) {
					if (choicedIndex > 0
							&& choicedIndex < userList.size()// 防止出现删除过后比较导致越界
							&& e.getSource() == singleFriendLables[choicedIndex])
						;
					else {
						singleFriendLables[i].setBorder(BorderFactory
								.createLineBorder(new Color(224, 102, 255), 1));
						singleFriendLables[i].setCursor(Cursor
								.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
				}
			}
		}

		public void mouseExited(MouseEvent e) {
			for (int i = 0; i < userList.size(); i++) {
				if ((choicedIndex < 0 || choicedIndex >= userList.size())
						|| (e.getSource() == singleFriendLables[i] && e
								.getSource() != singleFriendLables[choicedIndex])) {
					singleFriendLables[i].setBorder(null);
				}
			}
		}
	}
}
