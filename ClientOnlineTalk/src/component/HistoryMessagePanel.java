package component;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import util.FileUtil;
import connect.ClientHandler;
import entity.Message;
import entity.User;

public class HistoryMessagePanel extends JTextPane {

	private static final long serialVersionUID = -8623085291428951222L;
	private ArrayList<Message> messages = new ArrayList<Message>();
	private StyledDocument doc = null;
	private Color infoColor = new Color(187, 182, 182);
	private Color fromColor = new Color(204, 65, 37);
	private Color toColor = new Color(59, 143, 219);
	private User toUser;

	public HistoryMessagePanel() {
	}
	
	public HistoryMessagePanel(User toUser) {
		this.toUser = toUser;
		setEditable(false);
		loadMessage();
		doc = this.getStyledDocument();
		initializeMessage();
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void loadMessage() {
		setMessages(FileUtil.readMessage(ClientHandler.user,toUser));
	}

	public void setMessages(List<Message> messages) {
		this.messages = (ArrayList<Message>) messages;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	
	 public void insertIcon(File file) {
		 System.out.println("图片");
		  this.setCaretPosition(doc.getLength()); // 设置插入位置
		  this.insertComponent(new ImagePanel(file.getPath()));
		  try {
			doc.insertString(doc.getLength(), "\n",
						getAttribute(0));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	 
	 public void insertFilePanel(File file){
		 System.out.println("插入文件");
		 this.setCaretPosition(doc.getLength()); // 设置插入位置
		  this.insertComponent(new FilePanel(file));
		  try {
			doc.insertString(doc.getLength(), "\n",
						getAttribute(0));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
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
			System.out.println("initializeMessage" + currentMessage.getMessageType());
			if(currentMessage.getMessageType().equals("IMG")){
				this.insertIcon(new File(currentMessage.getMessage()));
			}
			if(currentMessage.getMessageType().equals("FILE")){
				this.insertFilePanel(new File(currentMessage.getMessage()));
			}
		}
		this.repaint();
		this.doLayout();
	}
	
	
	public void PreAddMessage(Message currentMessage){//添加一条消息
		insertMessage(currentMessage.getMessageTime(), 0);
		if (currentMessage.getFormId().equals(ClientHandler.user.getUserID())) {
			insertMessage(ClientHandler.user.getUserName() +" : "+currentMessage.getMessage(), 1);
		}
		if (currentMessage.getFormId().equals(toUser.getUserID())) {
			insertMessage(toUser.getUserName() +" : "+currentMessage.getMessage(), -1);
		}
	}
	
}
