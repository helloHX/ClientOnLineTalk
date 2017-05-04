package util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import connect.ClientReciver;
import entity.Message;
import entity.User;

public class FileUtil {

	public static  void saveOutMessage(Message mes) {
		saveAllMessage(mes,mes.getFormId(),mes.getToId());
	}
	
	public static  void saveInMessage(Message mes) {
		saveAllMessage(mes,mes.getToId(),mes.getFormId());
	}
	
	private static synchronized void saveAllMessage(Message mes,String dir,String fileName){
		String path = ClientReciver.filePath + dir
				+ "\\chart" + "\\" + fileName + ".dat";
		File file = new File(path);
		try {
			if (!file.exists()) {
				createFile(file);
			}
			if (file.length() < 1) {
				try (ObjectOutputStream output = new ObjectOutputStream(
						new FileOutputStream(file, true))) {
					output.writeObject(mes);
				}
			}else{
				try (MyObjectOutputStream output = new MyObjectOutputStream(
						new FileOutputStream(file, true))) {
					output.writeObject(mes);
				}
			}
			System.out.println("FileUtil_saveMessage : " + mes.getMessage() + "to"
			+ mes.getToId() + "from" + mes.getFormId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Message> readMessage(User to,User from) {
		return readMessage(to.getUserID(),from.getUserID());
	}

	public static synchronized List<Message> readMessage(String toId,String fromId) {
		String path = ClientReciver.filePath +toId.trim()+ "\\chart"
				+ "\\" + fromId.trim() + ".dat";
		File file = new File(path);
		System.out.println("File_readMessage mes:"+ path);
		List<Message> mesList = new ArrayList<>();
		try {
			if (!file.exists()) {
				createFile(file);
			}
			try (ObjectInputStream input = new ObjectInputStream(
					new FileInputStream(file))) {
				while (true) {
					Message historyMes = (Message)input.readObject();
					System.out.println("File_readMessage mes:"+ historyMes.getMessage() + "to" + historyMes.getToId());
					mesList.add(historyMes);
				}
			}
		} catch (EOFException e) {
			System.out.println("File_readMessage 历史记录加载完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mesList;
	}
	
	public static String ScanFile(){
		String path = "";
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFileChooser jdir = new JFileChooser(new File(System.getProperty("user.dir")));
			jdir.setDialogTitle("请选择路径");
			if (JFileChooser.APPROVE_OPTION == jdir.showOpenDialog(null)) {// 用户点击了确定
				path = jdir.getSelectedFile().getAbsolutePath();// 取得路径选择
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public static synchronized void createFile(File file) {
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (file.createNewFile()) {
				System.out.println("FileUtil_createFile"
						+ file.getName() + "创建成功");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
