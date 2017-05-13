package connect;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import app.ClientOnlineTalk;
import util.FileUtil;
import component.FriendPanel;
import entity.Message;
import entity.User;

public class ClientHandler extends Thread {
	public static PrintWriter writer;
	private String id;
	private String password;
	public static User user;
	public static Socket socket;
	private static ClientReciver clientReciver;
	public static final String serverIp = "127.0.0.1";

	public ClientHandler(String id, String password) {
		this.id = id;
		this.password = password;
	}

	@Override
	public void run() {
		try {
			CountDownLatch endSingal = new CountDownLatch(1);
			System.out.println("客户端端口打开");
			socket = new Socket(serverIp, 8888);
			InputStreamReader reader = new InputStreamReader(
					socket.getInputStream(), "UTF-8");
			BufferedReader buffer_reader = new BufferedReader(reader);
			clientReciver = new ClientReciver(buffer_reader, endSingal);// 创建收处理器
			clientReciver.start();
			writer = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream(), "UTF-8"));
			createLogin(id, password);// 创建连接后登陆
			endSingal.await();
			System.out.println("被断开");
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createLogin(String id, String password) {
		String loginInfo = "<login userID='" + id + "'" + " password='"
				+ password + "'/>";
		sentMessage(loginInfo);
	}

	public static void readySentFile(Message message) {
		File file = new File(message.getMessage());
		String fileInfo = "<file fileSentIime = '" + message.getMessageTime()
				+ "' " + "fileName = '" + message.getMessage()
				+ "' fileLength = '" + file.length() + "' fromId = '"
				+ message.getFormId() + "' toId = '" + message.getToId()
				+ "'/>";
		sentMessage(fileInfo);
		FileUtil.saveOutMessage(message);
	}

	public static void readySentImage(Message message) {
		File file = new File(message.getMessage());
		String imageInfo = "<image imageSentIime = '"
				+ message.getMessageTime() + "' " + "imageName = '"
				+ message.getMessage() + "' imageLength = '" + file.length()
				+ "' fromId = '" + user.getUserID() + "' toId = '"
				+ message.getToId() + "'/>";
		sentMessage(imageInfo);
		FileUtil.saveOutMessage(message);
	}

	public static void createMassage(Message message, String targetUserID) {
		Date currentDate = new Date();
		String messageTime = currentDate.toLocaleString();
		String messageInfo = "<message messageTime='" + messageTime
				+ "' message='" + message.getMessage() + "' from='"
				+ user.getUserID() + "' to='" + targetUserID + "'/>";
		System.out.println(messageInfo);
		sentMessage(messageInfo);
		FileUtil.saveOutMessage(message);

	}

	public static void createLogout() {
		String logoutInfo = "<logout userID='" + user.getUserID() + "'/>";
		sentMessage(logoutInfo);
	}
	
	public static void agreeMKF(String ids){
		String mes = "<result command='makeFriend' state='ok' message='"
				+ids+ "'/>";
		sentMessage(mes);
	}
	public static void disagreeMKF(String ids){
		String mes = "<result command='makeFriend' state='error' message='"
				+ids+ "'/>";
		sentMessage(mes);
	}

	public static void preparedIMGDownload(String imageName) {
		sentMessage("<result command='image' message='" + imageName
				+ "' state='ok'/>");
	}

	public static void preparedFileDownload(String fileName) {
		sentMessage("<result command='file' message='" + fileName
				+ "' state='ok'/>");
	}

	public static void requestFriends() {
		String requestInfo = "<friends userID='" + user.getUserID() + "'/>";
		sentMessage(requestInfo);
	}

	public static void queryUser(String queryUserId) {
		String requestInfo = "<queryUser userID='" + queryUserId + "'/>";
		sentMessage(requestInfo);
	}

	public static void requestAddFriend(String friendId) {
		String requestInfo = "<makeFriend userID = '" + user.getUserID() + "' "
				+ "friendID = '" + friendId + "'/>";
		sentMessage(requestInfo);
		ClientOnlineTalk.addFriendPanel.showFailResult("请求发生成功!!");
	}

	public static void sentMessage(String mes) {
		System.out.println("sentMessage" + mes);
		writer.println(mes);
		writer.flush();
	}

	public static void deleteFriend() {
		String deleteInfo = "<deleteFriend userID = '" + user.getUserID()
				+ "' " + "friendID = '"
				+ FriendPanel.selectedFriend.getUserID() + "'/>";
		sentMessage(deleteInfo);
	}
}
