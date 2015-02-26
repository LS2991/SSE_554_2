import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class NetworkClient {

	public final static int portNumber = 8189;
	public final static String IP = "127.0.0.1";
	public final static String fileReceived = "C:/Users/Louis/Desktop/databaseR.txt";
	public final static int FILE_SIZE = 6022386;
	
	static int bytesRead;
	static int current = 0;
	static FileOutputStream oStream = null;
	static BufferedOutputStream bStream = null;
	static Socket s = null;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File f = getFile(oStream, bStream, s);
	}
	
	public static File getFile(FileOutputStream oStream, BufferedOutputStream bStream, Socket s) throws IOException {
		
		try {
			s = new Socket(IP, portNumber);
			System.out.println("Connecting...");
			
			//receive file
			byte[] byteArray = new byte[FILE_SIZE];
			InputStream iStream = s.getInputStream();
			oStream = new FileOutputStream(fileReceived);
			bStream = new BufferedOutputStream (oStream);
			bytesRead = iStream.read(byteArray, 0, byteArray.length);
			current = bytesRead;
			
			while(bytesRead > -1) {
				bytesRead = iStream.read(byteArray, 0, byteArray.length);
				if(bytesRead >= 0)
					current += bytesRead;
			}
			
			bStream.write(byteArray, 0, current);
			bStream.flush();
			File f = new File(fileReceived);
			System.out.print("File recieved");
			
			
			return f;
		}
		
		finally {
			if(oStream != null)
				oStream.close();
			if(bStream != null)
				bStream.close();
			if(s != null)
				s.close();
		}
	}

}
