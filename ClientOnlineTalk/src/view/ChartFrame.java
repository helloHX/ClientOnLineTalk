package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import app.ClientOnlineTalk;

import component.ChartTitle;
import component.HistoryMessagePanel;
import component.MyButton;

import connect.ClientHandler;
import entity.Message;
import entity.User;

public class ChartFrame extends JFrame implements ActionListener {
	public static final int CFWIDTH = 800;
	public static final int CFHIGHT = 800;
	
	private static final long serialVersionUID = 1L;
	private User friend;
	private JTextPane inputPane;
	private Font font = new Font("宋体", Font.BOLD, 18);
	private MyButton sentButton = new MyButton("发送", new Color(85, 187, 255),
			font);
	private ChartTitle charTitle;
	public  HistoryMessagePanel historyMassagePanel;
	private JScrollPane HMScrollPane;

	public ChartFrame() {
	}

	public ChartFrame(User friend) {
		this.friend = friend;
		initialize();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ClientOnlineTalk.chartFrameMap.remove(getAccount().getUserID());
			}
		});
	}

	public User getAccount() {
		return this.friend;
	}

	public void initialize() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setPreferredSize(new Dimension(CFWIDTH,CFHIGHT));
		charTitle = new ChartTitle(friend);
		historyMassagePanel = new HistoryMessagePanel(friend);
		HMScrollPane = new JScrollPane(historyMassagePanel);
		charTitle.setBounds(0, 0, CFWIDTH, CFHIGHT /8);
		inputPane = new JTextPane();
		HMScrollPane.setBounds(0, CFHIGHT /8, CFWIDTH, CFHIGHT * 4/8);
		inputPane.setBounds(0, CFWIDTH * 5/8 + 10, CFWIDTH, CFHIGHT * 2/8);
		sentButton.setBounds(CFWIDTH * 3/8, CFWIDTH * 7/8 + 20, CFWIDTH / 4, CFHIGHT / 20);
		sentButton.addActionListener(this);
		contentPanel.add(charTitle);
		contentPanel.add(HMScrollPane);
		contentPanel.add(inputPane);
		contentPanel.add(sentButton);
		this.add(contentPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Message mes = new Message();
		mes.setFormId(ClientHandler.user.getUserID());
		mes.setToId(friend.getUserID());
		mes.setMessageType("MESSAGE");
		mes.setMessage(inputPane.getText().trim());
		mes.setMessageTime((new Date()).toLocaleString());
		historyMassagePanel.PreAddMessage(mes);
		ClientHandler.createMassage(mes, friend.getUserID());
		inputPane.setText("");
		// 发送消息
	}

}
