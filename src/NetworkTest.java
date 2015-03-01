import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;


public class NetworkTest {
	
	
	@Test
	public void fileTransferTest() throws IOException { //includes send and receiving a file from both client and server side
		final FileOutputStream fOStream = null;
		final BufferedOutputStream bOStream = null;
		ServerSocket s = null;
		final Socket incoming = null;
		//Socket clientS = null;
		final FileInputStream fIStream = null;
		final BufferedInputStream bIStream = null;
		final OutputStream oStream = null;
		
		
		
		Thread serverThread = new Thread() {
			public void run() {
				try {
					File f = new File("//192.168.1.229/Users/Public/ServerSide/database.txt");
					NetworkConnection.sendFile(fIStream, bIStream, oStream, incoming, f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		};
		
		serverThread.start();
		
		NetworkClient.getFile(fOStream, bOStream, incoming);
		
		serverThread = new Thread() {
			public void run() {
				try {
					NetworkConnection.recieveFile(fOStream, bOStream, incoming);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		};
		
		serverThread.start();
		
		File f = new File("//192.168.1.229/Users/Public/ClientSide/databaseR.txt");
		NetworkClient.sendFile(fIStream, bIStream, oStream, f);
		//assertTrue(s.isBound());
		//assertTrue(s.isClosed());
		
		
		try {
			Thread.sleep(2000); //waits for files to appear
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(f.exists());
		
		File ff = new File("//192.168.1.229/Users/Public/ServerSide/databaseRR.txt");
		
		assertTrue(ff.exists());
	}
	
//	@Test
//	public void savedFileExistsTest() throws IOException {
//		File f = new File("C:/Users/Louis/Desktop/databaseRR.txt");
//		assertTrue(f.exists());
//	}

}
