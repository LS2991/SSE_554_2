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

	public final static int portNumber1 = 8189;
	public final static int portNumber2 = 8190;
	public final static String IP = "192.168.1.229";
	public final static String fileReceived = "//192.168.1.229/Users/Public/ClientSide/save_files";
	public final static int FILE_SIZE = 6022386;
	
	static int bytesRead;
	static FileOutputStream fOStream = null;
	static FileInputStream fStream = null;
	static BufferedInputStream bIStream = null;
	static BufferedOutputStream bOStream = null;
	static OutputStream oStream = null;
	static Socket sendingS = null;
	static Socket receivingS = null;
	
	
	public static Socket receivingConnect() throws UnknownHostException, IOException {
		receivingS = new Socket(IP, portNumber1);
		return receivingS;
	}
	
	public static void sendingConnect() throws UnknownHostException, IOException {
		sendingS = new Socket(IP, portNumber2);
	}
	
	public static void closeSendingConnection() throws IOException {
		sendingS.close();
	}
	
	public static void closeReceivingConnection() throws IOException {
		receivingS.close();
	}
	
	public static File getFile(FileOutputStream fOStream, BufferedOutputStream bOStream, Socket s) throws IOException {
		
		try {
			System.out.println("Connecting...");
			s = receivingConnect();
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
			closeReceivingConnection();
		}
	}
	
	public static void sendFile(FileInputStream fIStream, BufferedInputStream bIStream, OutputStream oStream, File f) throws IOException { //file must exist first
		
		try {
			sendingConnect();
			System.out.println("Sending");
			byte[] byteArray = new byte[(int) f.length()];
			fIStream = new FileInputStream(f);
			bIStream = new BufferedInputStream(fIStream);
			bIStream.read(byteArray, 0, byteArray.length);
			oStream = sendingS.getOutputStream();
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
			closeSendingConnection();
		}
	}
}
