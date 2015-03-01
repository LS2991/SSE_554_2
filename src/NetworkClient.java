import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class NetworkClient {

	public final static int portNumber = 8189;
	public final static String IP = "192.168.1.229";
	//public final static String fileReceived = "C:/Users/Louis/Desktop/databaseR.txt";
	public final static String fileReceived = "//192.168.1.229/Users/Public/ClientSide/databaseR.txt";
	public final static int FILE_SIZE = 6022386;
	
	static int bytesRead;
	static FileOutputStream fOStream = null;
	static FileInputStream fStream = null;
	static BufferedInputStream bIStream = null;
	static BufferedOutputStream bOStream = null;
	static OutputStream oStream = null;
	static Socket s = null;
	
	
	public static void main(String[] args) throws IOException {
		getFile(fOStream, bOStream, s);
	}
	
	public static Socket connect() throws UnknownHostException, IOException {
		s = new Socket(IP, portNumber);
		return s;
	}
	
	public static void closeConnection() throws IOException {
		s.close();
	}
	
	public static File getFile(FileOutputStream fOStream, BufferedOutputStream bOStream, Socket s) throws IOException {
		
		try {
			System.out.println("Connecting...");
			s = connect();
			//receive file
			byte[] byteArray = new byte[FILE_SIZE];
			InputStream iStream = s.getInputStream();
			fOStream = new FileOutputStream(fileReceived);
			bOStream = new BufferedOutputStream (fOStream);
			bytesRead = iStream.read(byteArray, 0, byteArray.length);
			bOStream.write(byteArray, 0, bytesRead);
			bOStream.flush();
			File f = new File(fileReceived);
			System.out.println("File recieved");
			
			return f;
		}
		
		finally {
			if(fOStream != null)
				fOStream.close();
			if(bOStream != null)
				bOStream.close();
			closeConnection();
		}
	}
	
	public static void sendFile(FileInputStream fIStream, BufferedInputStream bIStream, OutputStream oStream, File f) throws IOException { //file must exist first
		
		try {
			connect();
			//s = new Socket(IP, portNumber);
			System.out.println("Sending");
			byte[] byteArray = new byte[(int) f.length()];
			fIStream = new FileInputStream(f);
			bIStream = new BufferedInputStream(fIStream);
			bIStream.read(byteArray, 0, byteArray.length);
			oStream = s.getOutputStream();
			System.out.println("Sending file");
			oStream.write(byteArray, 0, byteArray.length);
			oStream.flush();
			System.out.println("Done.");	
		}
		
		finally {
			if(bIStream != null)
				bIStream.close();
			if(oStream != null)
				oStream.close();
			closeConnection();
		}
	}

}
