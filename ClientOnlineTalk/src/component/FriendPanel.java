package component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.User;

public class FriendPanel extends JPanel {
	private ArrayList<User> userList = new ArrayList<>();
	private SingleFriendLable[] singleFriendLables;
	private User slectedFriend;
	private int choicedIndex;

	public FriendPanel() {
		this.setLayout(null);
		initializeFriends();
		showFriends();
		this.setPreferredSize(new Dimension(400, 720));
	}

	public int getChoicedIndex() {
		return choicedIndex;
	}

	public void setChoicedIndex(int choicedIndex) {
		this.choicedIndex = choicedIndex;
	}

	public void initializeFriends(){
		User user = new User();
		user.setUserName("����");
		user.setUserID("123");
		user.setUserStatus(true);
		userList.add(user);
		User friend = new User();
		friend.setUserName("����");
		friend.setUserID("234");
		friend.setUserStatus(true);
		userList.add(friend);
		User friend2 = new User();
		friend2.setUserName("����");
		friend2.setUserID("567");
		friend2.setUserStatus(true);
		userList.add(friend2);
		User friend3 = new User();
		friend3.setUserName("����");
		friend3.setUserID("789");
		friend3.setUserStatus(true);
		userList.add(friend3);
		User friend4 = new User();
		friend4.setUserName("����");
		friend4.setUserID("147");
		friend4.setUserStatus(false);
		userList.add(friend4);
	}
	
	public void showFriends() {
		this.setPreferredSize(new Dimension(400, userList.size() * 90));
		this.removeAll();// �Ƴ�����״̬
		LabelListener monitor = new LabelListener();
		singleFriendLables = new SingleFriendLable[userList.size()];
		for (int i = 0; i < userList.size(); i++) {
			singleFriendLables[i] = new SingleFriendLable(userList.get(i)
					.getUserName(), userList.get(i).isUserStatus());
			singleFriendLables[i].setBounds(0, i * 60 + 10, 400, 60);// �趨����չʾ���ݵ�λ��
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
					choicedIndex = i;// ����ѡ�еı�ǩ
					//����ѡ�к���¼�
				}
			}
		}

		public void mouseEntered(MouseEvent e) {// ��������Ƴ���Ч��
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
