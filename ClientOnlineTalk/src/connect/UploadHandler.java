package connect;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class UploadHandler extends Thread {
	private String fileName;
	public UploadHandler(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void run() {
		Socket infoSocket;
		try {
			infoSocket = new Socket(ClientHandler.serverIp,8887);
			System.out.println("客户端准备发送文件");
			OutputStream dataStream =  
					infoSocket.getOutputStream();
			File file = new File(fileName);
			FileInputStream read = new FileInputStream(file);
			byte[] data = new byte[1024];
			int readLength = 0;
			while((readLength = read.read(data)) != -1){
				dataStream.write(data,0,readLength);
				dataStream.flush();
			}
			read.close();
			infoSocket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
