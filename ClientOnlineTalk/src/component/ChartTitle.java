package component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.OnlinePanel;
import entity.User;

public class ChartTitle extends JPanel {
	private String picturePath;
	private String filepath;
	private User friend;
	private Color buttonColor =new Color(58, 173, 214);
	private Font font = new Font("宋体", Font.BOLD, 18);
	private Font infoFont = new Font("宋体", Font.BOLD, 60);
	private MyButton selectFile = new MyButton("选择文件", buttonColor, font);
	private MyButton selectPicture = new MyButton("选择图片", buttonColor, font);
	private JLabel friendinfo = new JLabel();

	public ChartTitle() {

	}

	public ChartTitle(User friend) {
		this.friend = friend;
		this.setPreferredSize(new Dimension(800, 100));
		friendinfo.setText(friend.getUserName());
		friendinfo.setFont(infoFont);
		friendinfo.setForeground(new Color(58, 173, 214));
		initialize();
	}

	public void initialize() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		this.add(friendinfo);
		this.add(selectFile);
		this.add(selectPicture);
		
	}

//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		User user = new User();
//		user.setUserName("张三");
//		ChartTitle loginPanel = new ChartTitle(user);
//		frame.add(loginPanel);
//		frame.setResizable(false);
//		frame.setLocationRelativeTo(null);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setTitle("OnlineTalk");
//		frame.setVisible(true);
//	}
}
