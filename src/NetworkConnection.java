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


public class NetworkConnection {

	final static int portNum = 8189;
	static FileInputStream fIStream = null;
	static BufferedInputStream bIStream = null;
	static OutputStream oStream = null;
	static ServerSocket s =null;
	static Socket incoming = null;
	static int bytesRead;
	
	static FileOutputStream fOStream = null;
	static BufferedOutputStream bOStream = null;
	
	public final static String fileReceived = "//192.168.1.229/Users/Public/ServerSide/databaseRR.txt";
	public final static int FILE_SIZE = 6022386;
	
	public static void main (String [] args) throws IOException {
		
		while(true) {
			File f = new File("//192.168.1.229/Users/Public/ServerSide/database.txt");
			sendFile(fIStream, bIStream, oStream, incoming, f);
			recieveFile(fOStream, bOStream, incoming);
		}
		
	}
	
	public static ServerSocket connect() throws IOException {
		s = new ServerSocket(portNum);
		//incoming = s.accept();
		
		return s;
	}
	
	public static void closeConnection() throws IOException {
		if(s != null)
			s.close();
//		if(incoming != null)
//			incoming.close();
	}
	public static void sendFile(FileInputStream fIStream, BufferedInputStream bIStream, OutputStream oStream, Socket incoming, File f) throws IOException { //file must exist first
			System.out.println("Waiting...");
			try {
				incoming = connect().accept();
				//incoming = s.accept();
				System.out.println("Accepted connection : " + incoming);
				//Sending a file
				//File f = new File("c:/database.txt");
				byte[] byteArray = new byte[(int)f.length()];
				fIStream = new FileInputStream(f);
				bIStream = new BufferedInputStream(fIStream);
				bIStream.read(byteArray, 0, byteArray.length);
				oStream = incoming.getOutputStream();
				System.out.println("Sending File");
				oStream.write(byteArray, 0, byteArray.length);
				oStream.flush();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	
	public static void recieveFile(FileOutputStream fOStream, BufferedOutputStream bOStream, Socket incoming) throws IOException {
		try {
			System.out.println("Waiting to receive");
			incoming = connect().accept();
			System.out.println("Getting file");
			byte[] byteArray = new byte[FILE_SIZE];
			InputStream iStream = incoming.getInputStream();
			fOStream = new FileOutputStream(fileReceived);
			bOStream = new BufferedOutputStream (fOStream);
			bytesRead = iStream.read(byteArray, 0, byteArray.length);
			System.out.println(bytesRead);
			bOStream.write(byteArray, 0, bytesRead);
			bOStream.flush();
			System.out.print("File recieved");
		}
	
		finally {
			if(fOStream != null)
				fOStream.close();
			if(bOStream != null)
				bOStream.close();
			closeConnection();
		}
	}
}
