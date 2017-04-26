package util;
import java.io.*;
/*Ϊ�����ظ����ļ����ļ���׷����ɶ��header
 * Ϊ�Բ���ʱ���ļ��Ķ�ȡ����IOExption
 * ��дObjectOutputstream*/
public class MyObjectOutputStream extends ObjectOutputStream{
	
	public MyObjectOutputStream() throws IOException {  
		         super(); 
		  }
	
	public MyObjectOutputStream(OutputStream out) throws IOException {
		   super(out);
		   } 
		  
		  @Override
	protected void writeStreamHeader() throws IOException { 
		     return;
		  }

}
