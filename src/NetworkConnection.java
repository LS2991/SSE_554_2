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

	final static int portNum1 = 8189;
	final static int portNum2 = 8190;
	static FileInputStream fIStream = null;
	static BufferedInputStream bIStream = null;
	static OutputStream oStream = null;
	static ServerSocket sendS =null;
	static ServerSocket receiveS =null;
	static Socket incomingSend = null;
	static Socket incomingReceive = null;
	static int bytesRead;
	
	static FileOutputStream fOStream = null;
	static BufferedOutputStream bOStream = null;
	
	//public final static String fileReceived = "//192.168.1.229/Users/Public/ServerSide/save_files3";
	public final static String fileReceived = "//192.168.1.229/Users/Public/ServerSide/database.txt"; //test locally
	public final static int FILE_SIZE = 6022386;
	
	public static void main (String [] args) throws IOException {
		
			File f = new File("//192.168.1.229/Users/Public/ServerSide/save_files");

			Thread sending = new Thread() {
				public void run() {
					File f = new File("//192.168.1.229/Users/Public/ServerSide/save_files");
					try {
						sendFile(fIStream, bIStream, oStream, incomingSend, f);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			
			Thread receiving = new Thread() {
				public void run() {
					try {
						receiveFile(fOStream, bOStream, incomingReceive);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			
			sending.start();
			receiving.start();
		
	}
		
	public static ServerSocket sendingConnect() throws IOException {
		sendS = new ServerSocket(portNum1);
		
		return sendS;
	}
	
	public static ServerSocket receivingConnect() throws IOException {
		receiveS = new ServerSocket(portNum2);
		
		return receiveS;
	}
	
	public static void closeSendConnection() throws IOException {
		if(sendS != null)
			sendS.close();
//		if(incoming != null)
//			incoming.close();
	}
	
	public static void closeReceiveConnection() throws IOException {
		if(receiveS != null)
			receiveS.close();
	}
	public static void sendFile(FileInputStream fIStream, BufferedInputStream bIStream, OutputStream oStream, Socket incoming, File f) throws IOException { //file must exist first
		while(true) {
				System.out.println("Waiting...");
				try {
					incoming = sendingConnect().accept();

					System.out.println("Accepted connection : " + incoming);
					//Sending a file
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
						e.printStackTrace();
					}
					System.out.println("Done.");
				}
				
				finally {
						if(bIStream != null)
							bIStream.close();
						if(oStream != null)
							oStream.close();
						closeSendConnection();
				}
		}
	}
	
	public static void receiveFile(FileOutputStream fOStream, BufferedOutputStream bOStream, Socket incoming) throws IOException {
		while(true) {
			try {
				System.out.println("Waiting to receive");
				
				incoming = receivingConnect().accept();
				
				System.out.println("Getting file");
				byte[] byteArray = new byte[FILE_SIZE];
				InputStream iStream = incoming.getInputStream();
				fOStream = new FileOutputStream(fileReceived);
				bOStream = new BufferedOutputStream (fOStream);
				bytesRead = iStream.read(byteArray, 0, byteArray.length);
				System.out.println(bytesRead);
				bOStream.write(byteArray, 0, bytesRead);
				bOStream.flush();
				System.out.println("File recieved");
			}
		
			finally {
				if(fOStream != null)
					fOStream.close();
				if(bOStream != null)
					bOStream.close();
				closeReceiveConnection();
			}
		}
	}
}
