package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import connect.ClientHandler;
import entity.User;

public class AddFriendPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 8898347833486165521L;
	public static String id = "";
	private JTextField idTextField = new JTextField(20);
	private JButton searchButton = new JButton("搜索");
	private Font font = new Font("宋体", Font.BOLD, 40);
	private JLabel resultLable = new JLabel("请输入需要添加的账号");
	private JButton sureButtpn = new JButton("确认添加");

	public AddFriendPanel() {
		this.setPreferredSize(new Dimension(400, 400));
		this.setLayout(new BorderLayout(20, 10));
		JPanel titlePanel = new JPanel();
		titlePanel.add(idTextField, BorderLayout.WEST);
		titlePanel.add(searchButton, BorderLayout.EAST);
		resultLable.setFont(font);
		resultLable.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(resultLable, BorderLayout.CENTER);
		this.add(sureButtpn, BorderLayout.SOUTH);
		searchButton.addActionListener(this);
		sureButtpn.addActionListener(this);
	}

	public void setResult(User friendinfo) {
		resultLable.setText(friendinfo.getUserName());
		id = friendinfo.getUserID();
		System.out.println("setResult_name" + friendinfo.getUserName());
	}

	public void showFailResult(String message){
		resultLable.setText(message);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton) {
			id = idTextField.getText();
			ClientHandler.queryFriend(id);
			// 好友查询请求
		}
		if (e.getSource() == sureButtpn) {
			// 请求好友
			if (id.equals("") || id == null) {
				// 输入账号提示
			} else {
				ClientHandler.makeFriend(id);
			}
		}
	}
}
