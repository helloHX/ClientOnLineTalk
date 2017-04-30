package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import view.AddFriendPanel;
import view.ChartFrame;
import view.LoginPanel;
import view.OnlinePanel;
import connect.ClientHandler;
import entity.User;

public class ClientOnlineTalk {
	public static LoginPanel loginPanel;
	public static OnlinePanel onlinePanel;
	public static JFrame frame = new JFrame();
	public static JFrame onLineframe = new JFrame();
	public static AddFriendPanel addFriendPanel = new AddFriendPanel();
	public static Map<String, JFrame> chartFrameMap = new HashMap<>();

	public static void main(String[] args) {
		showView();
	}

	public static void showView() {
		frame = new JFrame();
		loginPanel = new LoginPanel();
		frame.add(loginPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("OnlineTalk");
		frame.setVisible(true);
	}

	public static void onLine() {
		onlinePanel = new OnlinePanel();
		frame.dispose();
		onLineframe.add(onlinePanel);
		onLineframe.pack();
		onLineframe.setResizable(false);
		onLineframe.setLocationRelativeTo(null);
		onLineframe.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ClientHandler.createLogout();
				System.exit(0);
			}
		});
		onLineframe.setTitle("OnlineTalk");
		onLineframe.setVisible(true);
		ClientHandler.requestFriends();
	}

	public static void chart(User friend) {
		if (!chartFrameMap.containsKey(friend.getUserID())) {
			ChartFrame charFrame = new ChartFrame(friend);
			charFrame.pack();
			charFrame.setResizable(false);
			charFrame.setLocationRelativeTo(null);
			charFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			charFrame.setTitle("OnlineTalk");
			charFrame.setVisible(true);
			chartFrameMap.put(friend.getUserID(), charFrame);
		}else{
			chartFrameMap.get(friend.getUserID()).setVisible(true);
		}
	}

	public static void addFriend() {
		JFrame addframe = new JFrame();
		addframe.add(addFriendPanel);
		addframe.pack();
		addframe.setResizable(false);
		addframe.setLocationRelativeTo(null);
		addframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addframe.setTitle("OnlineTalk");
		addframe.setVisible(true);
	}
}
