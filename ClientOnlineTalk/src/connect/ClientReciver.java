package connect;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import util.FileUtil;
import view.ChartFrame;
import view.OnlinePanel;
import app.ClientOnlineTalk;
import entity.Message;
import entity.User;

public class ClientReciver implements Runnable{
	public  BufferedReader buffer_reader;
	private final CountDownLatch endSignal;
	
	public ClientReciver(BufferedReader buffer_reader,CountDownLatch endSignal){
		 this.buffer_reader =  buffer_reader;
		 this.endSignal = endSignal;
		 
	}
	
	public static Element getData(String mes) {
		System.out.println("Client_getData" + mes);
		Element root = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder
					.parse(new InputSource(new StringReader(mes)));
			root = doc.getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return root;
	}
	
	@Override
	public void run() {
		try {
			boolean online = true;
			while (online) {
				online = dispatch(getData(buffer_reader
						.readLine()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		endSignal.countDown();
	}
	
	
	public boolean dispatch(Element root) {
		switch (root.getTagName()) {
		case "result":
			return result(root);
		case "message":
			return message(root);
		}
		return true;
	}
	
	public boolean message(Element root) {
		Message message = new Message();  
		message.setFormId(root.getAttribute("from")); 
		message.setMessage(root.getAttribute("message"));
		message.setToId(root.getAttribute("to"));
		message.setMessageTime(root.getAttribute("messageTime"));
		System.out.println("ClientR_message" + message.getMessage());
		FileUtil.saveInMessage(message);//先存入文件缓存防止聊天状态栏没有打开
		ChartFrame charFrame = (ChartFrame) ClientOnlineTalk.chartFrameMap.get(message.getFormId());
		if(charFrame != null){//如果聊天窗口已经打开则直接添加在聊天界面上
			System.out.println("charFrame_NO_null");
			charFrame.historyMassagePanel.PreAddMessage(message);
			charFrame.setVisible(true);
		}
		return true;
	}

	public boolean result(Element root) {
		String command = root.getAttribute("command");
		String state = root.getAttribute("state");
		String message = root.getAttribute("message");
		switch (command) {
		case "login":
			if(state.equals("ok")){
				dealLogin(message);
			}
			else;
				//调用登陆失败方法
			return true;
		case "friends":
			if(state.equals("ok")){
				fullFriends(message);
			}
			else
				//调用登陆失败方法
				return true;
		case "message":
			if(state.equals("ok"))
				System.out.println("发送成功");
			else
				System.out.println(message);
			return true;
		case "logout":
			if(state.equals("ok")){
				System.out.println("result_logout 断开确认收到");
				return false;
			}
			else
			    return true;
		}
		return true;
	}


	public void dealLogin(String message){
		JSONObject jsonObject =JSONObject.fromObject(message);
		User user = (User)JSONObject.toBean(jsonObject,User.class);
		ClientHandler.user = user;
		ClientOnlineTalk.onLine();
		//调用聊天界面中的添加
	}
	
	public void fullFriends(String message){
		System.out.println("Client_fullFriends" + message);
		if(!message.equals(null) && message !=""){
		  JSONArray jsonarray = JSONArray.fromObject(message);  
		  List<User> friendList = (List<User>)JSONArray.toCollection(jsonarray, User.class);  
		//调用登陆页面中的添加好友的方法
		  OnlinePanel.friedPanel.initializeFriends(friendList);;
		  }
	}

	
	
}