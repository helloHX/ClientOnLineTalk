package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.User;

public class AddFriendPanel extends JPanel{
	private JTextField idTextField = new JTextField(20);
	private JButton searchButton = new  JButton("搜索");
	private Font font = new Font("宋体", Font.BOLD, 40);
	private JLabel resultLable = new JLabel("请输入需要添加的账号");
	private JButton sureButtpn = new JButton("确认添加");
	public AddFriendPanel() {
		this.setPreferredSize(new Dimension(400,400));
		this.setLayout(new BorderLayout(20,10));
		JPanel titlePanel = new JPanel();
		titlePanel.add(idTextField,BorderLayout.WEST);
		titlePanel.add(searchButton,BorderLayout.EAST);
		resultLable.setFont(font);
		this.add(titlePanel,BorderLayout.NORTH);
		this.add(resultLable,BorderLayout.CENTER);
		this.add(sureButtpn,BorderLayout.SOUTH);
	}
}
