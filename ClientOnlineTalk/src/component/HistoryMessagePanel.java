package component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import entity.Message;
import entity.User;

public class HistoryMessagePanel extends JTextPane {

	private static final long serialVersionUID = -8623085291428951222L;
	private ArrayList<Message> messages = new ArrayList<Message>();
	private StyledDocument doc = null;
	private User fromUser;
	private Color infoColor = new Color(187, 182, 182);
	private Color fromColor = new Color(204, 65, 37);
	private Color toColor = new Color(59, 143, 219);
	private Font infoFont = new Font("宋体", Font.BOLD, 13);
	private User toUser;

	public HistoryMessagePanel() {
	}

	public HistoryMessagePanel(User fromUser, User toUser) {
		this.fromUser = fromUser;
		this.toUser = toUser;
		setEditable(false);
		loadMessage();
		// this.messages = messages;
		doc = this.getStyledDocument();
		 initializeMessage();
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void loadMessage() {
		// 网络请求
		Message message = new Message();
		message.setFormId("123");
		message.setToId("234");
		message.setMessageId("1");
		message.setMessage("hello");
		message.setMessageTime((new Date()).toLocaleString());
		messages.add(message);
		Message message1 = new Message();
		message1.setFormId("234");
		message1.setToId("123");
		message1.setMessageId("2");
		message1.setMessage("hello too");
		message1.setMessageTime((new Date()).toLocaleString());
		messages.add(message1);
		Message message2 = new Message();
		message2.setFormId("123");
		message2.setToId("234");
		message2.setMessageId("3");
		message2.setMessage("are you ok?");
		message2.setMessageTime((new Date()).toLocaleString());
		messages.add(message2);
		Message message3 = new Message();
		message3.setFormId("234");
		message3.setToId("123");
		message3.setMessageId("4");
		message3.setMessage("I'm fine think you!");
		message3.setMessageTime((new Date()).toLocaleString());
		messages.add(message3);
		Message message4 = new Message();
		message4.setFormId("234");
		message4.setToId("123");
		message4.setMessageId("5");
		message4.setMessage("good bye!!");
		message4.setMessageTime((new Date()).toLocaleString());
		messages.add(message4);
		Message message5 = new Message();
		message5.setFormId("123");
		message5.setToId("234");
		message5.setMessageId("1");
		message5.setMessage("你在哪");
		message5.setMessageTime((new Date()).toLocaleString());
		messages.add(message5);
		Message message6 = new Message();
		message6.setFormId("234");
		message6.setToId("123");
		message6.setMessageId("2");
		message6.setMessage("我在这");
		message6.setMessageTime((new Date()).toLocaleString());
		messages.add(message6);
		Message message7 = new Message();
		message7.setFormId("123");
		message7.setToId("234");
		message7.setMessageId("3");
		message7.setMessage("你好吗");
		message7.setMessageTime((new Date()).toLocaleString());
		messages.add(message7);
		Message message8 = new Message();
		message8.setFormId("234");
		message8.setToId("123");
		message8.setMessageId("4");
		message8.setMessage("好你妹");
		message8.setMessageTime((new Date()).toLocaleString());
		messages.add(message8);
		Message message9 = new Message();
		message9.setFormId("234");
		message9.setToId("123");
		message9.setMessageId("5");
		message9.setMessage("good bye!!");
		message9.setMessageTime((new Date()).toLocaleString());
		messages.add(message9);
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
		repaint();
		doLayout();
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	
	public void insertMessage(String message, int type) {//向页面展示一条消息
		try {
			doc.insertString(doc.getLength(), message + "\n",
					getAttribute(type));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public SimpleAttributeSet getAttribute(int type) {// 通过类型给字体加以颜色区分
		int fontSize = 18;
		Color fontColor = infoColor;
		if (type != 0)
			fontSize = 20;
		if (type == 1)
			fontColor = fromColor;
		if (type == -1)
			fontColor = toColor;
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setFontFamily(attrSet, "宋体");
		StyleConstants.setFontSize(attrSet, fontSize);
		StyleConstants.setForeground(attrSet, fontColor);
		return attrSet;
	}

	public void initializeMessage() {//初始化消息记录
		Message currentMessage = null;
		for (int i = 0; i < messages.size(); i++) {
			currentMessage = messages.get(i);
			PreAddMessage(currentMessage);
		}
	}
	
	public void appendMassge(Message currentMessage){
		this.messages.add(currentMessage);
		this.PreAddMessage(currentMessage);
	}
	
	public void PreAddMessage(Message currentMessage){//添加一条消息
		insertMessage(currentMessage.getMessageTime(), 0);
		if (currentMessage.getFormId().equals(fromUser.getUserID())) {
			insertMessage(fromUser.getUserName() +" : "+currentMessage.getMessage(), 1);
		}
		if (currentMessage.getFormId().equals(toUser.getUserID())) {
			insertMessage(toUser.getUserName() +" : "+currentMessage.getMessage(), -1);
		}
	}
	
}
