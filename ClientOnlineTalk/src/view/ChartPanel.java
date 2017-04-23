package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import component.ChartTitle;
import component.HistoryMessagePanel;
import component.MyButton;
import entity.User;

public class ChartPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private User friend;
	private User fromUser;
//	private ArrayList<Message> messages = new ArrayList<Message>();
	private JTextPane inputPane;
	private Font font = new Font("宋体", Font.BOLD, 18);
	private MyButton sentButton = new MyButton("发送",new Color(85, 187, 255),font);
	private ChartTitle charTitle;
	public static HistoryMessagePanel historyMassagePanel;
	private JScrollPane HMScrollPane;
	public ChartPanel(){
		
	}
	
	public ChartPanel(User fromUser,User friend) {
		this.setLayout(null);
		this.friend = friend;
		this.fromUser = fromUser;
		this.setPreferredSize(new Dimension(800,800));
		initialize();
	}
	
	public void initialize(){
		charTitle = new ChartTitle(friend);
		historyMassagePanel =
				new HistoryMessagePanel(fromUser,friend);
		HMScrollPane = new JScrollPane(historyMassagePanel);
		charTitle.setBounds(0, 0,800, 100);
		inputPane = new JTextPane();
		HMScrollPane.setBounds(0, 110, 800, 330);
		inputPane.setBounds(0, 450, 800, 220);
		sentButton.setBounds(300, 710, 200, 40);
		this.add(charTitle);
		this.add(HMScrollPane);
		this.add(inputPane);
		this.add(sentButton);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		User user = new User();
		user.setUserName("张三");
		user.setUserID("123");
		User friend = new User();
		friend.setUserName("李四");
		friend.setUserID("234");
		ChartPanel loginPanel = new ChartPanel(user,friend);
		frame.add(loginPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("OnlineTalk");
		frame.setVisible(true);
	}
}
