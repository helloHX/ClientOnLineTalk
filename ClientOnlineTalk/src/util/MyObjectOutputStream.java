package util;
import java.io.*;

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
