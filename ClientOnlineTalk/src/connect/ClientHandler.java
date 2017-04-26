package connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import util.FileUtil;
import app.ClientOnlineTalk;
import entity.Message;
import entity.User;

public class ClientHandler implements Runnable{
	public static PrintWriter writer;
	private String id;
	private String password;
	public static User user;
	public static Socket socket;
	private static ClientReciver clientReciver;

	
	public ClientHandler(String id,String password){
		this.id = id;
		this.password = password;
	}
	
	@Override
	public void run(){
		try {
			CountDownLatch endSingal = new CountDownLatch(1);
			System.out.println("客户端端口打开");
			socket = new Socket("127.0.0.1", 8888);
			InputStreamReader reader = new InputStreamReader(
					socket.getInputStream());
			BufferedReader buffer_reader = new BufferedReader(reader);
			clientReciver = new ClientReciver(buffer_reader,endSingal);
			writer = new PrintWriter(socket.getOutputStream());
			ExecutorService exec = Executors.newFixedThreadPool(1);
			exec.submit(clientReciver);
			createLogin(id,password);//创建连接后登陆
			endSingal.await();
			System.out.println("被断开");
			exec.shutdownNow();
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

	public static void createMassage(Message message, String targetUserID) {
		Date currentDate = new Date();
		String messageTime = currentDate.toLocaleString();
		String messageInfo = "<message messageTime='" + messageTime
				+ "' message='" + message.getMessage() + "' from='" + user.getUserID()
				+ "' to='" + targetUserID + "'/>";
		System.out.println(messageInfo);
		sentMessage(messageInfo);
		FileUtil.saveOutMessage(message);
	}

	public static void createLogout() {
		String logoutInfo = "<logout userID='" + user.getUserID() + "'/>";
		sentMessage(logoutInfo);
	}

	public static void requestFriends() {
		String requestInfo = "<friends userID='" + user.getUserID() + "'/>";
		sentMessage(requestInfo);
	}

	public static void sentMessage(String mes) {
		System.out.println("sentMessage" + mes);
		writer.println(mes);
		writer.flush();
	}
}
