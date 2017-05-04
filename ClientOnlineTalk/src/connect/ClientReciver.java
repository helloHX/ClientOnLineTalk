package connect;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;
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

public class ClientReciver extends Thread {
	public BufferedReader buffer_reader;
	private final CountDownLatch endSignal;
	public static final String filePath = "D:\\测试垃圾\\onlineChart";
	public ClientReciver(BufferedReader buffer_reader, CountDownLatch endSignal) {
		this.buffer_reader = buffer_reader;
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
				online = dispatch(getData(
						new String(buffer_reader.readLine().getBytes("UTF-8"))));
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
		case "file":
			return downloadfile(root);
		case "image":
			return downloadimage(root);
		case "changeFriendState":
			return changeStateFriend(root);
		}
		return true;
	}

	private boolean changeStateFriend(Element root) {
		String friendId = root.getAttribute("friendID");
		boolean state = Boolean.parseBoolean(root.getAttribute("state"));
		OnlinePanel.friedPanel.changeFriendState(friendId, state);
		return true;
	}

	private boolean downloadimage(Element root) {
		System.out.println("准备接受图片");
		String imageSentIime = root.getAttribute("imageSentIime");
		String imageName = root.getAttribute("imageName");
		String imageLength = root.getAttribute("imageLength");
		String fromId = root.getAttribute("fromId");
		String toId = root.getAttribute("toId");
		File file = new File(imageName);
		String path = filePath + "\\"+ toId +"\\image\\"  + "\\" + fromId + "\\" + file.getName();
		
		Message message = new Message();
		message.setMessage(path);
		message.setMessageTime(imageSentIime);
		message.setMessageType("IMG");
		message.setFormId(fromId);
		message.setToId(toId);
		try {
			//等待图片下载完成后添加到聊天记录界面上
			CountDownLatch downOverSingal = new CountDownLatch(1);
			DownLoadHandler downLoadHandler =
					new DownLoadHandler(Long.parseLong(imageLength), path,downOverSingal);
			downLoadHandler.start();
			ClientHandler.preparedIMGDownload(imageName);
			downOverSingal.await();
			
			ChartFrame charFrame = (ChartFrame) ClientOnlineTalk.chartFrameMap
					.get(message.getFormId());
			if (charFrame != null) {// 如果聊天窗口已经打开则直接添加在聊天界面上
				charFrame.historyMassagePanel.PreAddMessage(message);
				charFrame.historyMassagePanel.insertIcon(new File(path));
				charFrame.setVisible(true);
			}
			FileUtil.saveInMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return true;
	}

	public boolean downloadfile(Element root) {

		String fileSentTime = root.getAttribute("fileSentTime");
		String fileName = root.getAttribute("fileName");
		String fileLength = root.getAttribute("fileLength");
		String fromId = root.getAttribute("fromId");
		String toId = root.getAttribute("toId");
		File file = new File(fileName);
		String path = filePath + "\\" + toId + "\\" + fromId + "\\"
				+ file.getName();
		Message message = new Message();
		message.setMessage(path);
		message.setMessageTime(fileSentTime);
		message.setMessageType("FILE");
		message.setFormId(fromId);
		message.setToId(toId);
		
		try {
			CountDownLatch downOverSingal = new CountDownLatch(1);
			DownLoadHandler downLoadHandler = new DownLoadHandler(
					Long.parseLong(fileLength), path,downOverSingal);
			downLoadHandler.start();
			ClientHandler.preparedFileDownload(fileName);
			downOverSingal.await();//等待文件下载完成
			
			ChartFrame charFrame = (ChartFrame) ClientOnlineTalk.chartFrameMap
					.get(message.getFormId());
			if (charFrame != null) {// 如果聊天窗口已经打开则直接添加在聊天界面上
				charFrame.historyMassagePanel.PreAddMessage(message);
				charFrame.historyMassagePanel.insertFilePanel(new File(path));
				charFrame.setVisible(true);
			}
			FileUtil.saveInMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			//回复
		}

		return true;
	}

	public boolean message(Element root) {
		Message message = new Message();
		message.setFormId(root.getAttribute("from"));
		message.setMessage(root.getAttribute("message"));
		message.setToId(root.getAttribute("to"));
		message.setMessageTime(root.getAttribute("messageTime"));
		message.setMessageType("MESSAGE");
		System.out.println("ClientR_message" + message.getMessage());
		FileUtil.saveInMessage(message);// 先存入文件缓存防止聊天状态栏没有打开
		ChartFrame charFrame = (ChartFrame) ClientOnlineTalk.chartFrameMap
				.get(message.getFormId());
		if (charFrame != null) {// 如果聊天窗口已经打开则直接添加在聊天界面上
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
			if (state.equals("ok")) {
				dealLogin(message);
			} else
				JOptionPane.showMessageDialog(null,"密码或账号错误","登陆失败", JOptionPane.ERROR_MESSAGE);
			return true;
		case "friends":
			if (state.equals("ok")) {
				fullFriends(message);
			} else;
				return true;
		case "queryUser":
			if (state.equals("ok")) {
				respUserInfo(message);
			} else
				errorRespUserInfo(message);
			return true;
		case "makeFriend":
			if (state.equals("ok")) {
				respmakeFriend(message);
			} else;
			return true;
		case "message":
			if (state.equals("ok"))
				System.out.println("发送成功");
			else
				System.out.println(message);
			return true;
		case "file":
		case "image":
			if (state.equals("ok")) {
				UploadHandler uploadHandler = new UploadHandler(message);
				uploadHandler.start();
			} else
				System.out.println(message);
			return true;
		case "logout":
			if (state.equals("ok")) {
				System.out.println("result_logout 断开确认收到");
				return false;
			} else
				return true;
		}
		return true;
	}

	private void errorRespUserInfo(String message) {
		ClientOnlineTalk.addFriendPanel.showFailResult(message);
	}

	public void respmakeFriend(String message) {
		ClientOnlineTalk.addFriendPanel.showFailResult("添加成功");
	}

	public void respUserInfo(String message) {
		JSONObject jsonObject = JSONObject.fromObject(message);
		User user = (User) JSONObject.toBean(jsonObject, User.class);
		ClientOnlineTalk.addFriendPanel.setResult(user);
	}

	public void dealLogin(String message) {
		JSONObject jsonObject = JSONObject.fromObject(message);
		User user = (User) JSONObject.toBean(jsonObject, User.class);
		ClientHandler.user = user;
		ClientOnlineTalk.onLine();
		// 调用聊天界面中的添加
	}

	public void fullFriends(String message) {
		System.out.println("Client_fullFriends" + message);
		if (!message.equals(null) && message != "") {
			JSONArray jsonarray = JSONArray.fromObject(message);
			List<User> friendList = (List<User>) JSONArray.toCollection(
					jsonarray, User.class);
			// 调用登陆页面中的添加好友的方法
			OnlinePanel.friedPanel.initializeFriends(friendList);
			;
		}
	}

}
