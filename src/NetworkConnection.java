import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class NetworkConnection {

	final static int portNum = 8189;
	static FileInputStream fStream = null;
	static BufferedInputStream bStream = null;
	static OutputStream oStream = null;
	static ServerSocket s =null;
	static Socket incoming = null;
	
	public static void main (String [] args) throws IOException {
		s = new ServerSocket(portNum);
		connect(fStream, bStream, oStream, s, incoming);
	}
	
	public static void connect(FileInputStream fStream, BufferedInputStream bStream, OutputStream oStream, ServerSocket s, Socket incoming) throws IOException {
		try {
				System.out.println("Waiting...");
				try {
					incoming = s.accept();
					System.out.println("Accepted connection : " + incoming);
					//Sending a file
					File f = new File("c:/database.txt");
					byte[] byteArray = new byte[(int)f.length()];
					fStream = new FileInputStream(f);
					bStream = new BufferedInputStream(fStream);
					bStream.read(byteArray, 0, byteArray.length);
					oStream = incoming.getOutputStream();
					System.out.println("Sending File");
					oStream.write(byteArray, 0, byteArray.length);
					oStream.flush();
					System.out.println("Done.");	
				}
				
				finally {
					if(bStream != null)
						bStream.close();
					if(oStream != null)
						oStream.close();
					if(incoming != null)
						incoming.close();
				}
		}
		
		finally {
			if(s != null)
				s.close();
		} 
	}
}
